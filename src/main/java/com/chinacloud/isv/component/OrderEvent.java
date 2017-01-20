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
import com.chinacloud.isv.service.SSHService;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;
import com.jcraft.jsch.JSchException;
@Component
@Transactional
public class OrderEvent {
	private static final Logger logger = LogManager.getLogger(OrderEvent.class);
	@Autowired
	CreateTableSpaceService createTableSpaceService;
	@Autowired
	CallbackWhiteholeService callbackWhiteholeService;
	@Autowired
	TaskResultDao taskResultDao;
	@Autowired
	TaskStackDao taskStackDao;
	@Autowired
	SSHService sshService;

	
	public void close() {
		
	}


	public String  go(ValueProvider valueProvider) {
		//create a oracle database
		String command = MSUtil.getDBCAComand(valueProvider);
		logger.debug("the command ====> "+command);
		
		String shell_result = null;
		String whiteholeMsg = null;
		try {
			shell_result = sshService.execCmd(command,valueProvider.getHostUser(),valueProvider.getOracleHostPassword(),valueProvider.getOracleHost());
			logger.info("dbca 的执行结果shell_result:===========>>"+shell_result);
		} catch (JSchException e) {
			e.printStackTrace();
			logger.error("ssh service error, error message: "+e.getLocalizedMessage());
		}
		String result = null;
		if(null != shell_result){
			valueProvider.setEventDealResult(CaseProvider.FAILED_STATUS);
			whiteholeMsg = WhiteholeFactory.getFailedMsg(valueProvider, "处理失败，错误信息： "+shell_result);
		}else{
			logger.info("已经进入 createTableSpace:==========>>");
			result = createTableSpaceService.createTableSpace(valueProvider);
			logger.info("创建表空间的执行结果 createTableSpace:==========>>"+result);
			//1. return success message to withhole
			if(null == result){
				valueProvider.setEventDealResult(CaseProvider.SUCESS_STATUS);
				whiteholeMsg = WhiteholeFactory.getWhiteholeMessage(valueProvider);
			}else{
			//2. return failed message to withhole	
				valueProvider.setEventDealResult(CaseProvider.FAILED_STATUS);
				whiteholeMsg = WhiteholeFactory.getFailedMsg(valueProvider, "处理失败，错误信息： "+result);
			}
		}
		valueProvider.setToWhiteholeMessage(whiteholeMsg);
		String wResult = callbackWhiteholeService.returnMsgToWhitehole(whiteholeMsg, valueProvider);
		
		//3. create task result put it to data base
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

}
