package com.chinacloud.isv.component;

import java.sql.SQLException;
import java.sql.Statement;

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
import com.chinacloud.isv.service.OracleDriverService;
import com.chinacloud.isv.service.OracleUserService;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;

@Component
@Transactional
public class ActiveEvent {
	private static final Logger logger = LogManager.getLogger(ActiveEvent.class);
	@Autowired
	OracleUserService oracleUserService; 
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
		Statement statement = oracleDriverService.getStatement(valueProvider.getOracleConnectionUrl(), valueProvider.getUserName(),valueProvider.getPassword());
		if(null == statement){
			whiteholeMsg = WhiteholeFactory.getFailedMsg(valueProvider, "处理失败，错误信息： 连接oracle异常。");
			String wResult = callbackWhiteholeService.returnMsgToWhitehole(whiteholeMsg, valueProvider);
			TaskResult taskResult = MSUtil.getResultInstance(CaseProvider.FAILED_STATUS, valueProvider, wResult);
			taskStackDao.deleteTask(valueProvider.getInstanceId());
			taskResultDao.addResult(taskResult);
			return null;
		}
		String grantUserResult = oracleUserService.grantPrivileges(statement, valueProvider);
		if(null == grantUserResult){
			valueProvider.setEventDealResult(CaseProvider.SUCESS_STATUS);
			whiteholeMsg = WhiteholeFactory.getWhiteholeMessage(valueProvider);
		}else{
			valueProvider.setEventDealResult(CaseProvider.FAILED_STATUS);
			whiteholeMsg = WhiteholeFactory.getFailedMsg(valueProvider, "处理失败，错误信息： "+grantUserResult);
		}
		valueProvider.setToWhiteholeMessage(whiteholeMsg);
		String wResult = callbackWhiteholeService.returnMsgToWhitehole(whiteholeMsg, valueProvider);
		
		// create task result put it to data base
		TaskResult taskResult = null;
		if(null != wResult){// http request whitehole failed
			logger.error("suspend event,return message to whitehole failed.");
			taskResult = MSUtil.getResultInstance(CaseProvider.FAILED_STATUS, valueProvider, wResult);
		}else{
			taskResult = MSUtil.getResultInstance(CaseProvider.SUCESS_STATUS, valueProvider, valueProvider.getWhiteholeReturnedMessage());
		}
		taskStackDao.deleteTask(valueProvider.getInstanceId());
		taskResultDao.addResult(taskResult);
		if(null != statement){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void close() {
		oracleDriverService.close();
	}
	
}
