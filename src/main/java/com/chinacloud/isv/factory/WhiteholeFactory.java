package com.chinacloud.isv.factory;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chinacloud.isv.entity.Params;
import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.entity.callbackparams.Data;
import com.chinacloud.isv.entity.callbackparams.Process;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class WhiteholeFactory {
	private static final Logger logger = LogManager.getLogger(WhiteholeFactory.class);
	
	/**
	 * convert json string to instance
	 * @param Class 
	 * @return
	 * @author Tiger
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public <T> T  getEntity(Class<?> obj,String params) throws JsonParseException, JsonMappingException, IOException{
		if(null == params || "".equals(params) ){
			logger.error("the json string is empty");
			return null;
		}else{
			String cTail = this.getClassName(obj.getName());
			if(cTail.contains("Params")){
				//add analyze method
			}else if(cTail.contains("CallbackParams")){
				
			}
			ObjectMapper om = new ObjectMapper();
			return (T) om.readValue(params, obj);
		}
		
	}
	
	/**
	 * return asyn message
	 * @param eventId
	 * @param caseName
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String getAsynReturnJson(String eventId,String caseName) throws JsonProcessingException{
		String jsonObj = null;
		Data data = new Data();
		data.setSuccess(true);
		com.chinacloud.isv.entity.callbackparams.Process process = new com.chinacloud.isv.entity.callbackparams.Process();
		process.setEventId(eventId);
		process.setStatus(CaseProvider.EVENT_TYPE_WAIT_FOR_RESULT);
		data.setErrorCode("");
		String name = MSUtil.getChineseName(caseName);
		if(null == name){
			logger.warn("the case name "+caseName+" don't exist");
		}
		data.setMessage(name+CaseProvider.EVENT_TYPE_WAIT_FOR_RESULT_MESSAGE);
		HashMap<String, Object> metadata = new HashMap<>();
		metadata.put("type", "Oracle");
		process.setMetadata(metadata);
		data.setProcess(process);
		HashMap<String, Object> att_map = new HashMap<>();
		process.setInstance(att_map);
		jsonObj = getJsonString(data);
		return jsonObj;
	}
	
	/**
	 * get call back failed result
	 * @param p
	 * @param type
	 * @return string
	 */
	public static String getFailedMsg(ValueProvider valueProvider,String msgTail){
		Data data = new Data();
		Process process = new Process();
		String result = null;
		data.setSuccess(false);
		data.setErrorCode(CaseProvider.ERROR_CODE);
		//data.setMessage(MSUtil.getChineseName(CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL)+"处理失败,原因是删除应用堆栈SSH KEY 失败。");
		data.setMessage(MSUtil.getChineseName(valueProvider.getEventType())+msgTail);
		process.setEventId(valueProvider.getEventId());
		logger.debug("the case===>"+valueProvider.getEventType());
		process.setInstanceId(valueProvider.getInstanceId());
		process.setStatus(CaseProvider.FAILED_STATUS);
		data.setProcess(process);
		HashMap<String, Object> metadata = new HashMap<>();
		metadata.put("type", "Oracle");
		process.setMetadata(metadata);
		HashMap<String, Object> att_map = new HashMap<>();
		process.setInstance(att_map);
		try {
			result = WhiteholeFactory.getJsonString(data);
		} catch (JsonProcessingException e) {
			logger.error("convert failed info to json failed \n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * get call back failed result
	 * @param p
	 * @param type
	 * @return string
	 */
	public static String getFailedMsg(Params p,String msgTail,String caseType){
		Data data = new Data();
		Process process = new Process();
		String result = null;
		data.setSuccess(false);
		data.setErrorCode(CaseProvider.ERROR_CODE);
		//data.setMessage(MSUtil.getChineseName(CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL)+"处理失败,原因是删除应用堆栈SSH KEY 失败。");
		data.setMessage(MSUtil.getChineseName(caseType)+msgTail);
		process.setEventId(p.getData().getEventId());
		logger.debug("the case===>"+caseType);
		if(!caseType.equals(CaseProvider.EVENT_TYPE_SUBSCRIPTION_ORDER))
		process.setInstanceId(p.getData().getPayload().getInstance().getInstanceId());
		process.setStatus(CaseProvider.FAILED_STATUS);
		data.setProcess(process);
		HashMap<String, Object> att_map = new HashMap<>();
		process.setInstance(att_map);
		try {
			result = WhiteholeFactory.getJsonString(data);
		} catch (JsonProcessingException e) {
			logger.error("convert failed info to json failed \n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * get success message that is returned to white hole
	 * @param valueProvider
	 * @return
	 */

	public static String getWhiteholeMessage(ValueProvider valueProvider){
		Data data = new Data();
		Process process = new Process();
		HashMap<String, Object> metadata = new HashMap<>();
		metadata.put("type", "Oracle");
		process.setMetadata(metadata);
		HashMap<String, Object> instance = new HashMap<>();
		if(valueProvider.getEventType().equals(CaseProvider.EVENT_TYPE_SUBSCRIPTION_ORDER)||
				valueProvider.getEventType().equals(CaseProvider.EVENT_TYPE_SUBSCRIPTION_QUERY)){
			instance.put("ORCL用户名", valueProvider.getCreateUserName());
			instance.put("ORCL密码", valueProvider.getCreateUserPassword());
		}
		data.setErrorCode("");
		data.setSuccess(true);
		data.setMessage(MSUtil.getChineseName(valueProvider.getEventType())+"处理成功。");
		process.setInstanceId(valueProvider.getInstanceId());
		process.setEventId(valueProvider.getEventId());
		process.setStatus(valueProvider.getEventDealResult());
		process.setInstance(instance);
		data.setProcess(process);
		String toWhiteholeJson = null;
		try {
			toWhiteholeJson = WhiteholeFactory .getJsonString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return toWhiteholeJson;
	}
	
	public static String getJsonString(Object obj) throws JsonProcessingException{
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(obj);
	}
	
	
	
	private String  getClassName(String fullName){
		StringBuilder stringBuilder = new StringBuilder(fullName);
		String cTail = stringBuilder.substring(stringBuilder.lastIndexOf(".")+1,stringBuilder.length());
		return cTail;
	}
	
	
}
