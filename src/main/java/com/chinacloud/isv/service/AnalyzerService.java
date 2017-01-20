package com.chinacloud.isv.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.chinacloud.isv.domain.TaskResult;
import com.chinacloud.isv.domain.TaskStack;
import com.chinacloud.isv.entity.Params;
import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.factory.WhiteholeFactory;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		String tableSpaceLocation[] = {"db_dbf"};//aa
		String tableSpaceSize[] = {"db_tags"};
		String tableSpaceRiseNumber[] = {"db_increase_step"};//aa
		String tableSpaceName[] = {"db_tags_name"};
		String tableSpaceMaxSize[] = {"db_tags_max"};//aa
		String dbCharSet[] = {"db_unicode"};
		String userName[] = {"username"};
		String password[] = {"password"};
		String sid[] = {"sid"};
		String responseFile[] = {"responseFile"};
		//String RACOneNodeSerbiceName[] = {"RACOneNodeServiceName"};
		String serverPoolName[] = {"serverPoolName"};
		String sysPassword[] = {"sysPassword"};
		String systemPassword[] = {"systemPassword"};
		String emConfiguration[] = {"emConfiguration"};
		String dbsnmpPassword[] = {"dbsnmpPassword"};
		String sysmanPassword[] = {"sysmanPassword"};
		String storageType[] ={"storageType"};
		String asmsnmpPassword[] = {"asmsnmpPassword"};
		String diskGroupName[] = {"diskGroupName"};
		String sampleSchema[] = {"sampleSchema"};
		String redoLogFileSize[] = {"redoLogFileSize"};
		String automaticMemoryManagement[] = {"automaticMemoryManagement"};
		String initParams[] = {"initParams"};
		String totalMemory[] = {"totalMemory"};
		String databaseType[] = {"databaseType"};
		String templateName[] = {"templateName"};
		
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
		
		
		//for oracle dbca
		String dbca_dbCharSet = MSUtil.getDirectedValueFromJson(dbCharSet, edtionCode).asText();
		String dbca_sid = MSUtil.getDirectedValueFromJson(sid, edtionCode).asText();
		String dbca_responseFile = MSUtil.getDirectedValueFromJson(responseFile, edtionCode).asText();
		//String dbca_RACOneNodeSerbiceName = MSUtil.getDirectedValueFromJson(RACOneNodeSerbiceName, edtionCode).asText();
		String dbca_serverPoolName = MSUtil.getDirectedValueFromJson(serverPoolName, edtionCode).asText();
		String dbca_sysPassword = MSUtil.getDirectedValueFromJson(sysPassword, edtionCode).asText();
		String dbca_systemPassword = MSUtil.getDirectedValueFromJson(systemPassword, edtionCode).asText();
		String dbca_emConfiguration = MSUtil.getDirectedValueFromJson(emConfiguration, edtionCode).asText();
		String dbca_dbsnmpPassword = MSUtil.getDirectedValueFromJson(dbsnmpPassword, edtionCode).asText();
		String dbca_sysmanPassword = MSUtil.getDirectedValueFromJson(sysmanPassword, edtionCode).asText();
		String dbca_storageType = MSUtil.getDirectedValueFromJson(storageType, edtionCode).asText();
		String dbca_asmsnmpPassword = MSUtil.getDirectedValueFromJson(asmsnmpPassword, edtionCode).asText();
		String dbca_diskGroupName = MSUtil.getDirectedValueFromJson(diskGroupName, edtionCode).asText();
		String dbca_sampleSchema = MSUtil.getDirectedValueFromJson(sampleSchema, edtionCode).asText();
		String dbca_redoLogFileSize = MSUtil.getDirectedValueFromJson(redoLogFileSize, edtionCode).asText();
		String dbca_automaticMemoryManagement = MSUtil.getDirectedValueFromJson(automaticMemoryManagement, edtionCode).asText();
		String dbca_initParams = MSUtil.getDirectedValueFromJson(initParams, edtionCode).asText();
		String dbca_totalMemory = MSUtil.getDirectedValueFromJson(totalMemory, edtionCode).asText();
		String dbca_databaseType = MSUtil.getDirectedValueFromJson(databaseType, edtionCode).asText();
		String dbca_templateName = MSUtil.getDirectedValueFromJson(templateName, edtionCode).asText();
	
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
		
		valueProvider.setHostUser(uName);
		valueProvider.setOracleHostPassword(uPassword);
		valueProvider.setOracleHost(oracleUrl);
		System.out.println("oracleUrl===>"+oracleUrl);
		System.out.println("uName =========>"+uName);
		System.out.println("uPassword=======>"+uPassword);
		valueProvider.setTemplateName(dbca_templateName);
		valueProvider.setGdbname(dbca_sid);
		valueProvider.setSid(dbca_sid);
		valueProvider.setResponseFile(dbca_responseFile);
		valueProvider.setrACOneNodeServiceName(dbca_sid+"_S");
		valueProvider.setCharacterSet(dbca_dbCharSet);
		valueProvider.setServerPoolName(dbca_serverPoolName);
		valueProvider.setSysPassword(dbca_sysPassword);
		valueProvider.setSystemPassword(dbca_systemPassword);
		valueProvider.setEmConfiguration(dbca_emConfiguration);
		valueProvider.setDbsnmpPassword(dbca_dbsnmpPassword);
		valueProvider.setSysmanPassword(dbca_sysmanPassword);
		valueProvider.setStorageType(dbca_storageType);
		valueProvider.setAsmsnmpPassword(dbca_asmsnmpPassword);
		valueProvider.setDiskGroupName(dbca_diskGroupName);
		valueProvider.setSampleSchema(dbca_sampleSchema);
		valueProvider.setRedoLogFileSize(dbca_redoLogFileSize );
		valueProvider.setAutomaticMemoryManagement(dbca_automaticMemoryManagement);
		valueProvider.setInitParams(dbca_initParams);
		valueProvider.setTotalMemory(dbca_totalMemory);
		valueProvider.setDatabaseType(dbca_databaseType);
		
		valueProvider.setOracleConnectionUrl("jdbc:oracle:thin:@"+"(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = "+oracleUrl+")(PORT = "+orclPort+"))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = "+valueProvider.getSid()+")))");
		
		valueProvider.setCreateUserName(cUserName);
		valueProvider.setCreateUserPassword(cUserPassword);
		valueProvider.setTableSpaceLocation(tSpaceLocation);
		valueProvider.setTableSpaceName(tSpaceName);
		valueProvider.setTableSpaceSize(tSpaceSize);
		valueProvider.setTableSpaceRiseNumber(tSpaceRiseNumber);
		valueProvider.setTableSpaceMaxSize(tSpaceMaxSize);
		valueProvider.setUserName("system");
		valueProvider.setPassword(valueProvider.getSysPassword());
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
		try {
			Params params = new WhiteholeFactory().getEntity(Params.class, taskResult.getEventParams());
			ObjectMapper om = new ObjectMapper();
			String editionCode = om.writeValueAsString(params.getData().getPayload().getOrder().getEditionCode());
			String userName[] = {"username"};
			String password[] = {"password"};
			String sid[] = {"sid"};
			String url[] = {"oracle_url"};
			valueProvider.setHostUser(MSUtil.getDirectedValueFromJson(userName, editionCode).asText());
			valueProvider.setOracleHostPassword(MSUtil.getDirectedValueFromJson(password, editionCode).asText());
			valueProvider.setGdbname(MSUtil.getDirectedValueFromJson(sid, editionCode).asText());
			valueProvider.setOracleHost(MSUtil.getDirectedValueFromJson(url, editionCode).asText());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
