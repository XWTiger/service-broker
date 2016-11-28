package com.chinacloud.isv.schedule;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chinacloud.isv.component.ActiveEvent;
import com.chinacloud.isv.component.CancelEvent;
import com.chinacloud.isv.component.OrderEvent;
import com.chinacloud.isv.component.SuspendEvent;
import com.chinacloud.isv.domain.TaskResult;
import com.chinacloud.isv.domain.TaskStack;
import com.chinacloud.isv.entity.Params;
import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.factory.WhiteholeFactory;
import com.chinacloud.isv.persistance.TaskResultDao;
import com.chinacloud.isv.persistance.TaskStackDao;
import com.chinacloud.isv.service.AnalyzerService;
import com.chinacloud.isv.service.CallbackWhiteholeService;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;

@Component
public class TaskConsumeSchedule {
	private static final Logger logger = LogManager.getLogger(TaskConsumeSchedule.class);
	@Autowired
	TaskStackDao riskStackDao;
	@Autowired
	TaskResultDao taskResultDao;
	@Autowired
	AnalyzerService analyzerService;
	@Autowired
	CallbackWhiteholeService callbackWhiteholeService;
	@Autowired
	OrderEvent orderEvent;
	@Autowired
	SuspendEvent suspendEvent;
	@Autowired
	ActiveEvent activeEvent;
	@Autowired
	CancelEvent cancelEvent;

	
	@Scheduled(fixedRate = 1000)
	public void riskRunning(){
		
		WhiteholeFactory whiteholeFactory = new WhiteholeFactory();
		ArrayList<TaskStack> RiskList = riskStackDao.getTasks();
		// lock task item
		//logger.debug("-------the tast size:"+RiskList.size()+"----------");
		//consume task and insert result
		for (TaskStack taskStack : RiskList) {
		
			//lock
			Integer status = riskStackDao.lockTask(taskStack.getId());
			logger.debug("task id ===============>"+taskStack.getId());
			if(null != status && 1 == status ){
				//consume
				try {
					Params params = whiteholeFactory.getEntity(Params.class, taskStack.getParams());
					TaskResult taskResult = new TaskResult();
					ValueProvider  valueProvider = new ValueProvider();
					valueProvider.setEventParams(taskStack.getParams());
					analyzerService.getEventInfoFromParams(params,taskStack, valueProvider);
					switch (params.getData().getType()) {
						case CaseProvider.EVENT_TYPE_SUBSCRIPTION_ORDER:{
							String editionCode = params.getData().getPayload().getOrder().getEditionCode();
							//analyze the edition code
							valueProvider.setTaskId(taskStack.getId());
							valueProvider.setInstanceId(taskStack.getId());
							analyzerService.getOrclValueByEditonCode(editionCode, valueProvider);
							orderEvent.go(valueProvider);
							valueProvider = null;
							break;
						}
						case CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL:{
							String instanceId = params.getData().getPayload().getInstance().getInstanceId();
							logger.debug("CANCEL　CASE: the instance id---->"+instanceId);
							valueProvider.setInstanceId(instanceId);
							valueProvider.setTaskId(taskStack.getId());
							TaskResult tr = taskResultDao.getOrderTaskResultById(instanceId);
							if(null == tr || tr.getResultStatus().equals(CaseProvider.FAILED_STATUS)){
								logger.error("when do cancle case,get instance order result failed because of database return null");
								String  Response = WhiteholeFactory.getFailedMsg(params, "处理失败,原因是该实例申请事件并未执行成功。", CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL);
								logger.info("cancle case,get instanceid is null or order envent not success,call back return result:"+Response);
								String whiteholeResult = callbackWhiteholeService.returnMsgToWhitehole(Response, valueProvider);
								if(null != whiteholeResult){
									taskResult = MSUtil.getTaskResult(0, taskStack, taskStack.getParams(), valueProvider.getWhiteholeReturnedMessage(),CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL,params.getData().getEventId());
									riskStackDao.deleteTask(taskStack.getId());
									taskResultDao.addResult(taskResult);
								}
							}
							analyzerService.getOrclValueByTaskResult(valueProvider, tr);
							cancelEvent.go(valueProvider);
							valueProvider = null;
							break;
						}
						case CaseProvider.EVENT_TYPE_SUBSCRIPTION_SUSPEND:{
							String instanceId = params.getData().getPayload().getInstance().getInstanceId();
							logger.debug("SUSPEND　CASE: the instance id---->"+instanceId);
							valueProvider.setInstanceId(instanceId);
							valueProvider.setTaskId(taskStack.getId());
							TaskResult tr = taskResultDao.getOrderTaskResultById(instanceId);
							if(null == tr || tr.getResultStatus().equals(CaseProvider.FAILED_STATUS)){
								logger.error("when do cancle case,get instance order result failed or order event not success.");
								String  Response = WhiteholeFactory.getFailedMsg(params, "处理失败,原因是该实例申请事件并未执行成功。", CaseProvider.EVENT_TYPE_SUBSCRIPTION_SUSPEND);
								logger.info("cancle case,get instanceid is null or order envent not success,call back return result:"+Response);
								String whiteholeResult = callbackWhiteholeService.returnMsgToWhitehole(Response, valueProvider);
								if(null != whiteholeResult){
									taskResult = MSUtil.getTaskResult(0, taskStack, taskStack.getParams(), valueProvider.getWhiteholeReturnedMessage(),CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL,params.getData().getEventId());
									riskStackDao.deleteTask(taskStack.getId());
									taskResultDao.addResult(taskResult);
								}
							}
							analyzerService.getOrclValueByTaskResult(valueProvider, tr);
							suspendEvent.go(valueProvider);
							suspendEvent.close();
							valueProvider = null;
							break;
						}
						case CaseProvider.EVENT_TYPE_SUBSCRIPTION_QUERY:{
							break;
						}
						case CaseProvider.EVENT_TYPE_SUBSCRIPTION_ACTIVE:{
							String instanceId = params.getData().getPayload().getInstance().getInstanceId();
							valueProvider.setInstanceId(instanceId);
							valueProvider.setTaskId(taskStack.getId());
							logger.debug("ACTIVE VITRUAL MACHINE　CASE: the instance id---->"+instanceId);
							TaskResult tr = taskResultDao.getOrderTaskResultById(instanceId);
							if(null == tr || tr.getResultStatus().equals(CaseProvider.FAILED_STATUS)){
								logger.error("when do cancle case,get instance order result failed or order event not success.");
								String  Response = WhiteholeFactory.getFailedMsg(params, "处理失败,原因是该实例申请事件并未执行成功。", CaseProvider.EVENT_TYPE_SUBSCRIPTION_SUSPEND);
								logger.info("cancle case,get instanceid is null or order envent not success,call back return result:"+Response);
								String whiteholeResult = callbackWhiteholeService.returnMsgToWhitehole(Response, valueProvider);
								if(null != whiteholeResult){
									taskResult = MSUtil.getTaskResult(0, taskStack, taskStack.getParams(), valueProvider.getWhiteholeReturnedMessage(),CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL,params.getData().getEventId());
									riskStackDao.deleteTask(taskStack.getId());
									taskResultDao.addResult(taskResult);
								}
							}
							analyzerService.getOrclValueByTaskResult(valueProvider, tr);
							activeEvent.go(valueProvider);
							activeEvent.close();
							valueProvider = null;
							break;
						}
						default:{
							logger.warn("case type don't exist");
							riskStackDao.deleteTask(taskStack.getId());
							throw new IllegalArgumentException("case type don't exist");
							
						}
					}
				} catch (Exception e) {
					logger.error("when consume task ,have a exception, errorMsg:"+e.getLocalizedMessage());
					e.printStackTrace();
				} 
			}
		}
	}

}
