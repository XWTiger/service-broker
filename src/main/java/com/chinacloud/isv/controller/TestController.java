package com.chinacloud.isv.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinacloud.isv.component.EventDriver;
import com.chinacloud.isv.persistance.TaskStackDao;
import com.chinacloud.isv.service.SSHService;
import com.jcraft.jsch.JSchException;



@RestController
public class TestController {
	private static final Logger logger = LogManager.getLogger(TestController.class);
	@Autowired
	TaskStackDao riskStackDao;
	@Autowired
	EventDriver queryEvent;
	@Autowired
	SSHService sshService;
	
	String Json = "{ \"status\": 200,\"data\": {\"type\": \"SUBSCRIPTION_ORDER\",\"eventId\": \"324e8a16-6b06-465b-84c1-be762dd9fea0\",\"marketplace\": {\"baseUrl\": \"http://www.baidu.com\",\"partner\": \"whitehole\"},\"creator\": {\"id\": \"cfc46de5-05c4-4774-aae6-6839d03113cb\",\"email\": \"zjw186@qq.com\",\"firstName\": \"zhang\",\"lastName\": \"san\"},\"payload\": {\"tenant\": {\"name\": \"whitehole_tenant\",\"id\": \"ee32f443-6c65-4c0b-a0ae-d69fd92eeb56\"},\"order\": {\"editionCode\": \"m.tiny\"}}, \"callBackUrl\": \"http://172.16.80.170:8080/business/order/tasks/callback\"}}";
	
	
	
	@RequestMapping(value="/callback",method=RequestMethod.POST)
	@ResponseBody
	public String greeting(){
		logger.info("----------hello world------------");
		logger.error("-------------error test--------------");
		return Json;
	}
	
	@RequestMapping("/test_exception")
	public void exceptionTest(){
		logger.info("----------what hanppend------------");
		try {
			sshService.execCmd("wget http://172.16.70.22:8080/images/kvm/centos7/CentOS-7.2-5G.cloud.qcow2.xz", "root", "huacloud", "172.16.64.48");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*int i = 0;
		while(true){
			
			System.out.println("loop："+i);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}*/
	}

}
