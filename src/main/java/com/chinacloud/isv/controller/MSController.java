package com.chinacloud.isv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSController {

	
	@RequestMapping("/event_request")
	public String eventRequest(@RequestParam String url){
		System.out.println("this is callback url ---->"+url);
		return url;
	
	}

}
