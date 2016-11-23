package com.chinacloud.isv.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacloud.isv.domain.TaskResult;
import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.factory.WhiteholeFactory;
import com.chinacloud.isv.persistance.TaskResultDao;
import com.chinacloud.isv.persistance.TaskStackDao;
import com.chinacloud.isv.service.CallbackWhiteholeService;
import com.chinacloud.isv.service.CreateTableSpaceService;
import com.chinacloud.isv.service.OracleDriverService;
import com.chinacloud.isv.service.OracleUserService;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;
@Component
@Transactional
public class CancelEvent{
	private static final Logger logger = LogManager.getLogger(CancelEvent.class);
	@Autowired
	OracleUserService oracleUserService; 
	@Autowired
	CreateTableSpaceService createTableSpaceService;
	@Autowired
	OracleDriverService oracleDriverService;
	@Autowired
	CallbackWhiteholeService callbackWhiteholeService;
	@Autowired
	TaskResultDao taskResultDao;
	@Autowired
	TaskStackDao taskStackDao;
	
	
	public String go(ValueProvider valueProvider) {
		String whiteholeMsg = null;
		String result = createTableSpaceService.dropTableSpace(valueProvider);
		if(null == result){
			valueProvider.setEventDealResult(CaseProvider.SUCESS_STATUS);
			// the message is return to whitehole
			whiteholeMsg = WhiteholeFactory.getWhiteholeMessage(valueProvider);
		}else{
		// return failed message to withhole	
			valueProvider.setEventDealResult(CaseProvider.FAILED_STATUS);
			whiteholeMsg = WhiteholeFactory.getFailedMsg(valueProvider, "处理失败，错误信息： "+result);
		}
		valueProvider.setToWhiteholeMessage(whiteholeMsg);
		String wResult = callbackWhiteholeService.returnMsgToWhitehole(whiteholeMsg, valueProvider);
		valueProvider.setWhiteholeReturnedMessage(wResult);
		// create task result put it to data base
		TaskResult taskResult = null;
		if(null != wResult){
			logger.error("order event,return message to whitehole failed.");
			taskResult = MSUtil.getResultInstance(CaseProvider.FAILED_STATUS, valueProvider, wResult);
		}else{
			taskResult = MSUtil.getResultInstance(CaseProvider.SUCESS_STATUS, valueProvider, valueProvider.getWhiteholeReturnedMessage());
		}
		taskStackDao.deleteTask(valueProvider.getInstanceId());
		taskResultDao.addResult(taskResult);
		return result;
	}

	
	public void close() {
		// TODO Auto-generated method stub
		
	}

	

}
