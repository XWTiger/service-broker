package com.chinacloud.isv.domain;

public class TaskStack {

	private String id;
	private String requestUrl;
	private String eventType;
	private String requestMethod;
	private String params;
	private String callBackUrl;
	private String addTime;
	private int lockTask;
	private int repeatTimes;
	private String eventId;
	
	public int getLock() {
		return lockTask;
	}
	public void setLock(int lock) {
		this.lockTask = lock;
	}
	
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	public int getLockTask() {
		return lockTask;
	}
	public void setLockTask(int lockTask) {
		this.lockTask = lockTask;
	}
	public int getRepeatTimes() {
		return repeatTimes;
	}
	public void setRepeatTimes(int repeatTimes) {
		this.repeatTimes = repeatTimes;
	}
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
}
