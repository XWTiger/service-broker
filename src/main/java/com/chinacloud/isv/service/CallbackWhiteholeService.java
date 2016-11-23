package com.chinacloud.isv.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.util.MSUtil;

@Service
public class CallbackWhiteholeService {
	private static final Logger logger = LogManager.getLogger(CallbackWhiteholeService.class);
	
	public String returnMsgToWhitehole(String result,ValueProvider valueProvider){
		try {
			logger.info(valueProvider.getEventType()+", call back params---->"+result);
			Map<String,String> map = new HashMap<String,String >();
			map.put("Content-Type", "application/json");
			logger.info("call back url: "+valueProvider.getCallBackUrl());
			String newResult = MSUtil.encode(result);
			logger.info("the order case result==========>"+result);
			CloseableHttpResponse response = MSUtil.httpClientPostUrl(map, valueProvider.getCallBackUrl(), newResult);
			HttpEntity entity = response.getEntity();
			String callBackResponse = EntityUtils.toString(entity);
			logger.info("response entity content--->"+callBackResponse);
			valueProvider.setWhiteholeReturnedMessage(callBackResponse);
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(valueProvider.getEventType()+",eventId: "+valueProvider.getEventId()+",instance id :"+valueProvider.getInstanceId()+", call back params failed. errorMsg:"+e.getLocalizedMessage());
			return valueProvider.getEventType()+", call back params failed. errorMsg:"+e.getLocalizedMessage();
		} 
		return null;
	}

}
