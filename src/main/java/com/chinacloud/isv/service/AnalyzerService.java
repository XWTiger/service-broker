package com.chinacloud.isv.service;

import org.springframework.stereotype.Service;

import com.chinacloud.isv.domain.TaskResult;
import com.chinacloud.isv.domain.TaskStack;
import com.chinacloud.isv.entity.Params;
import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;

@Service
public class AnalyzerService {

	/**
	 * get values from params 
	 * @param edtionCode
	 * @param params
	 */
	public void getOrclValueByEditonCode(String edtionCode,ValueProvider valueProvider){
		System.out.println("Edition code =====>"+edtionCode);
		String url[] = {"oracle_url"};
		String port[] = {"oracle_port"};
		String createUserName[] = {"db_tags_user"};
		String createUserPassword[] = {"db_tags_password"};
		String tableSpaceLocation[] = {"db_dbf"};
		String tableSpaceSize[] = {"db_tags"};
		String tableSpaceRiseNumber[] = {"db_increase_step"};
		String tableSpaceName[] = {"db_tags_name"};
		String tableSpaceMaxSize[] = {"db_tags_max"};
		String userName[] = {"username"};
		String password[] = {"password"};
		
		String oracleUrl = MSUtil.getDirectedValueFromJson(url, edtionCode).asText();
		String orclPort = MSUtil.getDirectedValueFromJson(port, edtionCode).asText();
		String cUserName = MSUtil.getDirectedValueFromJson(createUserName, edtionCode).asText();
		String cUserPassword = MSUtil.getDirectedValueFromJson(createUserPassword, edtionCode).asText();
		String tSpaceLocation = MSUtil.getDirectedValueFromJson(tableSpaceLocation, edtionCode).asText();
		Integer tSpaceSize = MSUtil.getDirectedValueFromJson(tableSpaceSize, edtionCode).asInt();
		Integer tSpaceRiseNumber = MSUtil.getDirectedValueFromJson(tableSpaceRiseNumber, edtionCode).asInt();
		String tSpaceName = MSUtil.getDirectedValueFromJson(tableSpaceName, edtionCode).asText();
		Integer tSpaceMaxSize = MSUtil.getDirectedValueFromJson(tableSpaceMaxSize, edtionCode).asInt();
		String uName = MSUtil.getDirectedValueFromJson(userName, edtionCode).asText();
		String uPassword = MSUtil.getDirectedValueFromJson(password, edtionCode).asText();
		
		if(null == tSpaceSize || "".equals(tSpaceSize)){
			tSpaceSize = 100;
		}
		if(null == tSpaceRiseNumber || "".equals(tSpaceRiseNumber)){
			tSpaceRiseNumber = 10;
		}
		if(null == tSpaceMaxSize || "".equals(tSpaceMaxSize)){
			tSpaceMaxSize = 1000;
		}
		if(null == tSpaceName || "".equals(tSpaceName)){
			tSpaceName = cUserName;
		}
		valueProvider.setOracleConnectionUrl("jdbc:oracle:thin:@"+oracleUrl+":"+orclPort+":orcl");
		valueProvider.setCreateUserName(cUserName);
		valueProvider.setCreateUserPassword(cUserPassword);
		valueProvider.setTableSpaceLocation(tSpaceLocation);
		valueProvider.setTableSpaceName(tSpaceName);
		valueProvider.setTableSpaceSize(tSpaceSize);
		valueProvider.setTableSpaceRiseNumber(tSpaceRiseNumber);
		valueProvider.setTableSpaceMaxSize(tSpaceMaxSize);
		valueProvider.setUserName(uName);
		valueProvider.setPassword(uPassword);
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
