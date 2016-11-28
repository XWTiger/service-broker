package com.chinacloud.isv.entity;

public class ValueProvider {

	//oracle info
	private String oracleConnectionUrl;
	private String userName;
	private String password;
	private String tableSpaceName;
	private String tableSpaceLocation;
	private int tableSpaceSize;
	private int tableSpaceRiseNumber;
	private int tableSpaceMaxSize;
	private String createUserName;
	private String createUserPassword;
	
	//about white hole
	private String toWhiteholeMessage;
	private String callBackUrl;
	private String whiteholeReturnedMessage;
	private String requsetMethod = "post";
	
	//about event info
	private String eventParams;
	private String eventId;
	private String eventType;
	private String eventDealResult;
	
	
	//about instance info
	private String taskId;
	private String instanceId;
	
	
	
	
	
	public String getOracleConnectionUrl() {
		return oracleConnectionUrl;
	}
	public void setOracleConnectionUrl(String oracleConnectionUrl) {
		this.oracleConnectionUrl = oracleConnectionUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTableSpaceName() {
		return tableSpaceName;
	}
	public void setTableSpaceName(String tableSpaceName) {
		this.tableSpaceName = tableSpaceName;
	}
	public String getTableSpaceLocation() {
		return tableSpaceLocation;
	}
	public void setTableSpaceLocation(String tableSpaceLocation) {
		this.tableSpaceLocation = tableSpaceLocation;
	}
	public int getTableSpaceSize() {
		return tableSpaceSize;
	}
	public void setTableSpaceSize(int tableSpaceSize) {
		this.tableSpaceSize = tableSpaceSize;
	}
	public int getTableSpaceRiseNumber() {
		return tableSpaceRiseNumber;
	}
	public void setTableSpaceRiseNumber(int tableSpaceRiseNumber) {
		this.tableSpaceRiseNumber = tableSpaceRiseNumber;
	}
	public int getTableSpaceMaxSize() {
		return tableSpaceMaxSize;
	}
	public void setTableSpaceMaxSize(int tableSpaceMaxSize) {
		this.tableSpaceMaxSize = tableSpaceMaxSize;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserPassword() {
		return createUserPassword;
	}
	public void setCreateUserPassword(String createUserPassword) {
		this.createUserPassword = createUserPassword;
	}
	
	public String getToWhiteholeMessage() {
		return toWhiteholeMessage;
	}
	public void setToWhiteholeMessage(String toWhiteholeMessage) {
		this.toWhiteholeMessage = toWhiteholeMessage;
	}
	public String getEventParams() {
		return eventParams;
	}
	public void setEventParams(String eventParams) {
		this.eventParams = eventParams;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getWhiteholeReturnedMessage() {
		return whiteholeReturnedMessage;
	}
	public void setWhiteholeReturnedMessage(String whiteholeReturnedMessage) {
		this.whiteholeReturnedMessage = whiteholeReturnedMessage;
	}
	public String getRequsetMethod() {
		return requsetMethod;
	}
	public void setRequsetMethod(String requsetMethod) {
		this.requsetMethod = requsetMethod;
	}
	public String getEventDealResult() {
		return eventDealResult;
	}
	public void setEventDealResult(String eventDealResult) {
		this.eventDealResult = eventDealResult;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
	
}
