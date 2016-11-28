package com.chinacloud.isv.service;

import org.springframework.stereotype.Service;

import com.chinacloud.isv.domain.TaskResult;
import com.chinacloud.isv.domain.TaskStack;
import com.chinacloud.isv.entity.Params;
import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.util.CaseProvider;

@Service
public class AnalyzerService {

	/**
	 * get values from params 
	 * @param edtionCode
	 * @param params
	 */
	public void getOrclValueByEditonCode(String edtionCode,ValueProvider valueProvider){
		valueProvider.setOracleConnectionUrl("jdbc:oracle:thin:@172.16.64.247:1521:orcl");
		valueProvider.setCreateUserName("XTiger");
		valueProvider.setCreateUserPassword("123456");
		valueProvider.setTableSpaceLocation("/home/oracle/app/oradata/orcl");
		valueProvider.setTableSpaceName("xtiger");
		valueProvider.setTableSpaceSize(10);
		valueProvider.setTableSpaceRiseNumber(5);
		valueProvider.setTableSpaceMaxSize(200);
		valueProvider.setUserName("system");
		valueProvider.setPassword("system123");
	}
	/**
	 * event id, instance id ,call back url,event type
	 * @param params
	 * @param valueProvider
	 */
	public void getEventInfoFromParams(Params params,TaskStack taskStack, ValueProvider valueProvider){
		valueProvider.setEventId(params.getData().getEventId());
		if(params.equals(CaseProvider.EVENT_TYPE_SUBSCRIPTION_ORDER))
		valueProvider.setInstanceId(taskStack.getId());//instance id
		valueProvider.setCallBackUrl(taskStack.getCallBackUrl());
		valueProvider.setEventType(params.getData().getType());
		
	}
	/**
	 * get orcl info from order task result
	 * @param valueProvider
	 * @param taskResult
	 */
	public void  getOrclValueByTaskResult(ValueProvider valueProvider,TaskResult taskResult){
		valueProvider.setOracleConnectionUrl(taskResult.getOracleConnUrl());
		valueProvider.setUserName(taskResult.getOracleDBAUser());
		valueProvider.setPassword(taskResult.getOracleDBAPassword());
		valueProvider.setCreateUserName(taskResult.getUserName());
		valueProvider.setCreateUserPassword(taskResult.getUserPassword());
		valueProvider.setTableSpaceName(taskResult.getTableSpaceName());
	}
}
