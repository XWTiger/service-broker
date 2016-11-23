package com.chinacloud.isv.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chinacloud.isv.domain.TaskResult;
import com.chinacloud.isv.domain.TaskStack;
import com.chinacloud.isv.entity.ValueProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MSUtil {

	private static final Logger logger = LogManager.getLogger(MSUtil.class);
	
	private static String[] synArray = { CaseProvider.EVENT_TYPE_SUBSCRIPTION_QUERY };
	private static String[][] listEvent = { { CaseProvider.EVENT_TYPE_SUBSCRIPTION_ORDER, "申请事件" },
			{ CaseProvider.EVENT_TYPE_SUBSCRIPTION_QUERY, "查询事件" },
			{ CaseProvider.EVENT_TYPE_SUBSCRIPTION_CANCEL, "销毁事件" },
			{ CaseProvider.EVENT_TYPE_SUBSCRIPTION_SUSPEND, "挂起事件" },
			{ CaseProvider.EVENT_TYPE_SUBSCRIPTION_ACTIVE, "激活事件" },
			{ CaseProvider.EVENT_TYPE_SUBSCRIPTION_REBOOT,"重启事件"},
			{CaseProvider.EVENT_TYPE_SUBSCRIPTION_LAUNCH,"启动事件"}};

	/**
	 * distinguish Synchronization
	 * 
	 * @param caseName
	 * @return Synchronization return true
	 */
	public static final boolean isSynchronzation(String caseName) {
		boolean b = false;
		for (String name : synArray) {
			if (name.equals(caseName)) {
				b = true;
			}
		}
		return b;
	}

	public static CloseableHttpResponse httpClientPostUrl(Map<String, String> headers, String url, String data)
			throws Exception {
		HttpClientBuilder hcBuilder = HttpClients.custom();
		hcBuilder.setRedirectStrategy(new LaxRedirectStrategy());
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = hcBuilder.build();
		// httpClient.getParams().setParameter("http.protocol.allow-circular-redirects",
		// true);
		HttpPost httpPost = new HttpPost(url);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		httpPost.setEntity(new StringEntity(data));
		RequestConfig requestConfig = RequestConfig.custom()
				// 设置连接超时时间
				.setConnectTimeout(30000)
				// 设置从connect Manager获取Connection 超时时间
				.setConnectionRequestTimeout(30000)
				// 请求获取数据的超时时间
				.setSocketTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		return httpClient.execute(httpPost);
	}

	public static CloseableHttpResponse httpClientPostUrl(Map<String, String> headers, String url,
			List<NameValuePair> data) throws Exception {
		HttpClientBuilder hcBuilder = HttpClients.custom();
		hcBuilder.setRedirectStrategy(new LaxRedirectStrategy());
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = hcBuilder.build();
		// httpClient.getParams().setParameter("http.protocol.allow-circular-redirects",
		// true);
		HttpPost httpPost = new HttpPost(url);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		httpPost.setEntity(new UrlEncodedFormEntity(data));
		RequestConfig requestConfig = RequestConfig.custom()
				// 设置连接超时时间
				.setConnectTimeout(30000)
				// 设置从connect Manager获取Connection 超时时间
				.setConnectionRequestTimeout(30000)
				// 请求获取数据的超时时间
				.setSocketTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		return httpClient.execute(httpPost);
	}

	/**
	 * get the case chinese name by case type
	 * 
	 * @param caseName
	 * @return name
	 */
	public static String getChineseName(String caseName) {
		String cName = null;
		for (String[] strings : listEvent) {
			if (strings[0].equals(caseName)) {
				cName = strings[1];
			}
		}
		return cName;
	}

	public static String getFarmNameFromResult(String resultMsg) {
		String sp[] = resultMsg.split("'");
		return sp[1];
	}

	public static CloseableHttpResponse httpClientGetUrl(Map<String, String> headers, String url) throws Exception {
		HttpClientBuilder hcBuilder = HttpClients.custom();
		hcBuilder.setRedirectStrategy(new LaxRedirectStrategy());
		CloseableHttpClient httpClient = hcBuilder.build();
		HttpGet httpGet = new HttpGet(url);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		RequestConfig requestConfig = RequestConfig.custom()
				// 设置连接超时时间
				.setConnectTimeout(30000)
				// 设置从connect Manager获取Connection 超时时间
				.setConnectionRequestTimeout(30000)
				// 请求获取数据的超时时间
				.setSocketTimeout(30000).build();
		httpGet.setConfig(requestConfig);
		return httpClient.execute(httpGet);
	}

	/**
	 * generate a local result
	 * 
	 * @param type
	 *            1 success else failed
	 * @param task
	 * @return
	 */
	public static TaskResult getTaskResult(int type, TaskStack task, String r, String comebackResult,String caseType,String eventId) {
		TaskResult result = new TaskResult();
		if (1 == type) {
			result.setResultStatus("SUCCESS");
			result.setId(task.getId());
			result.setRequestMethod(task.getRequestMethod());
			result.setEventParams(r);
			result.setWhReturnedParams(comebackResult);
			result.setRequestUrl(task.getCallBackUrl());
			result.setEventId(eventId);
			result.setEventType(MSUtil.getChineseName(caseType));
			if(caseType.equals(CaseProvider.EVENT_TYPE_SUBSCRIPTION_ORDER)){
				//result.setcFarmId(clonedFarmId);
				//result.setDestinationFarmId(task.getFarmId());
			}else{
				//result.setDestinationFarmId(clonedFarmId);
			}
		} else {
			result.setResultStatus(CaseProvider.FAILED_STATUS);
			result.setId(task.getId());
			result.setRequestMethod(task.getRequestMethod());
			result.setEventParams(r);
			result.setRequestUrl(task.getCallBackUrl());
			result.setEventId(eventId);
			result.setEventType(MSUtil.getChineseName(caseType));
			result.setWhReturnedParams("call back return result to whitehole failed, errorMsg:"+ comebackResult);
		}
		return result;
	}

	/**
	 * if is a formal request url
	 * 
	 * @param url
	 * @return true if yes otherwise return false
	 */
	public static boolean httpUrlCheck(String url) {
		boolean b = true;
		String regex = "^(http|https)://([\\w-]+.)+[\\w-]+(/[\\w-./?%&=]*)?$";
		b = url.matches(regex);
		return b;
	}

	/**
	 * is test request?
	 * 
	 * @param p
	 * @return true ,if is test request
	 */
	public static boolean isTestParameter(String p) {
		boolean b = true;
		if (!p.equals("{eventUrl}")) {
			b = false;
		}
		return b;
	}

	/**
	 * encode string to ISO-8859-1
	 * 
	 * @param s
	 * @return string
	 */
	public static String encode(String s) {
		String newString = null;
		try {
			newString = new String(s.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newString;
	}
/**
 * encode utf-8
 * @param s
 * @return
 */
	public static String encodeUtf8(String s){
		String newString = null;
		try {
			newString = new String(s.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newString;
	}
	/**
	 * convert string to unicode
	 * @param string
	 * @return
	 */
	public static String string2Unicode(String string) {
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {
	        // 取出每一个字符
	        char c = string.charAt(i);
	        // 转换为unicode
	        if(Integer.toHexString(c).length()==1){
	        	unicode.append("\\u000" + Integer.toHexString(c));
	        }else if(Integer.toHexString(c).length()==2){
	        	unicode.append("\\u00" + Integer.toHexString(c));
	        }else if(Integer.toHexString(c).length()==3){
	        	unicode.append("\\u0" + Integer.toHexString(c));
	        }else{
	        	unicode.append("\\u" + Integer.toHexString(c));
	        }
	    }
	    return unicode.toString();
	}
	
	
	/**
	 * jsonStr old string , name is the key ,value is the new value
	 * 
	 * @param jsonStr
	 * @param name
	 * @param value
	 * @return new string
	 */
	public static String replaceValue(String jsonStr, String name, String value) {
		String string = null;
		String bufferHeader = null;
		String bufferTail = null;
		logger.debug("go into here?");
		int index = jsonStr.indexOf(name);
		if(index <= 0){
			//the jsonStr don't have the key value
			logger.warn("the configuration string don't have "+name);
			return jsonStr;
		}
		logger.info("replace key=======>"+name+"value====>"+value);
		// find ","
		bufferHeader = jsonStr.substring(0, index - 1);
		
		int rIndex = index +name.length();
		int rEndIndex = 0;
		for (int i = rIndex; i < jsonStr.length(); i++) {
			String content = jsonStr.substring(i, i + 1);
			if (content.equals(",")) {
				rEndIndex = i;
				break;
			}
		}
		if(0 == rEndIndex){
			logger.error("get index falied");
			return null;
		}
		bufferTail = jsonStr.substring(rEndIndex, jsonStr.length());
		string = bufferHeader +"\""+name+"\":"+value+bufferTail;
		logger.debug("final string =====>"+string);
		return string;
	}
	
	
	/**
	 * get directed value by key. not support array  
	 * @param keys
	 * @param json
	 * @return JsonNode
	 */
	public static JsonNode getDirectedValueFromJson(String [] keys,String json){
		if(keys.length <= 0){
			logger.error("when get directed value,the key is empty");
			return null;
		}
		if(null == json || json.equals("")){
			logger.error("when get directed value,the json is empty");
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = null;
		try {
			node = objectMapper.readTree(json);
			for(int i = 0; i < keys.length ; i++){
				node = node.get(keys[i]);
			}
		} catch (Exception e) {
			logger.error("when get directed value,have a exception msg:"+e.getLocalizedMessage());
			return null;
		} 
		return node;
	}
	
	/**
	 * get result instance 
	 * type not 0 success, 0 filed
	 * @return
	 */
	public static TaskResult getResultInstance(String id,String status,String requestMthod,String requestResponse,String callBackUrl,String parameters,String eventType){
		TaskResult tResult = new TaskResult();
		tResult.setResultStatus(status);
		tResult.setId(id);
		tResult.setRequestMethod(requestMthod);
		tResult.setEventParams(parameters);
		tResult.setWhReturnedParams(requestResponse);
		tResult.setEventType(eventType);
		//logger.debug("===========cFarmId=======>"+cFarmId+"===destinationFarmId==>"+destinationFarmId);
		
		tResult.setRequestUrl(callBackUrl);
		return tResult;
	}
	
	/**
	 * get result instance 
	 * 
	 * @return
	 */
	public static TaskResult getResultInstance(String status,ValueProvider valueProvider,String whiteholeResultInfo){
		TaskResult tResult = new TaskResult();
		tResult.setResultStatus(status);
		tResult.setId(valueProvider.getInstanceId());
		tResult.setRequestMethod(valueProvider.getRequsetMethod());
		tResult.setEventParams(valueProvider.getEventParams());
		tResult.setWhReturnedParams(whiteholeResultInfo);
		tResult.setToWhParams(valueProvider.getToWhiteholeMessage());
		tResult.setEventId(valueProvider.getEventId());
		tResult.setEventType(valueProvider.getEventType());
		tResult.setOracleConnUrl(valueProvider.getOracleConnectionUrl());
		tResult.setOracleDBAPassword(valueProvider.getPassword());
		tResult.setUserName(valueProvider.getCreateUserName());
		tResult.setUserPassword(valueProvider.getCreateUserPassword());
		tResult.setTableSpaceMaxSize(String.valueOf(valueProvider.getTableSpaceMaxSize()));
		tResult.setTableSpaceName(valueProvider.getTableSpaceName());
		tResult.setTableSpaceRiseNumber(String.valueOf(valueProvider.getTableSpaceRiseNumber()));
		tResult.setTableSpaceSize(String.valueOf(valueProvider.getTableSpaceSize()));
		tResult.setTableSpaceLocation(valueProvider.getTableSpaceLocation());
		tResult.setRequestUrl(valueProvider.getCallBackUrl());
		return tResult;
	}
	
}
