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
	
	//about ssh shell
	private String hostUser;
	private String OracleHost;
	private String OracleHostPassword;
	
	//about DBCA parameters
	private String templateName;
	private String gdbname;
	private String sid;
	private String responseFile = "NO_VALUE";
	private String rACOneNode;
	private String rACOneNodeServiceName;
	private String characterSet = "ZHS16GBK";
	private String serverPoolName;
	private String sysPassword;
	private String systemPassword;
	private String emConfiguration = "NONE";
	private String dbsnmpPassword;
	private String sysmanPassword;
	private String storageType;
	private String asmsnmpPassword;
	private String diskGroupName;
	private String sampleSchema;
	private String redoLogFileSize;
	private String automaticMemoryManagement;
	private String initParams ="open_cursors=500 ";
	private String totalMemory ;
	private String databaseType;
	
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
	public String getOracleHost() {
		return OracleHost;
	}
	public void setOracleHost(String oracleHost) {
		OracleHost = oracleHost;
	}
	public String getOracleHostPassword() {
		return OracleHostPassword;
	}
	public void setOracleHostPassword(String oracleHostPassword) {
		OracleHostPassword = oracleHostPassword;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getGdbname() {
		return gdbname;
	}
	public void setGdbname(String gdbname) {
		this.gdbname = gdbname;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getResponseFile() {
		return responseFile;
	}
	public void setResponseFile(String responseFile) {
		this.responseFile = responseFile;
	}
	public String getrACOneNode() {
		return rACOneNode;
	}
	public void setrACOneNode(String rACOneNode) {
		this.rACOneNode = rACOneNode;
	}
	public String getrACOneNodeServiceName() {
		return rACOneNodeServiceName;
	}
	public void setrACOneNodeServiceName(String rACOneNodeServiceName) {
		this.rACOneNodeServiceName = rACOneNodeServiceName;
	}
	public String getCharacterSet() {
		return characterSet;
	}
	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}
	public String getServerPoolName() {
		return serverPoolName;
	}
	public void setServerPoolName(String serverPoolName) {
		this.serverPoolName = serverPoolName;
	}
	public String getSysPassword() {
		return sysPassword;
	}
	public void setSysPassword(String sysPassword) {
		this.sysPassword = sysPassword;
	}
	public String getSystemPassword() {
		return systemPassword;
	}
	public void setSystemPassword(String systemPassword) {
		this.systemPassword = systemPassword;
	}
	public String getEmConfiguration() {
		return emConfiguration;
	}
	public void setEmConfiguration(String emConfiguration) {
		this.emConfiguration = emConfiguration;
	}
	public String getDbsnmpPassword() {
		return dbsnmpPassword;
	}
	public void setDbsnmpPassword(String dbsnmpPassword) {
		this.dbsnmpPassword = dbsnmpPassword;
	}
	public String getSysmanPassword() {
		return sysmanPassword;
	}
	public void setSysmanPassword(String sysmanPassword) {
		this.sysmanPassword = sysmanPassword;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	public String getAsmsnmpPassword() {
		return asmsnmpPassword;
	}
	public void setAsmsnmpPassword(String asmsnmpPassword) {
		this.asmsnmpPassword = asmsnmpPassword;
	}
	public String getDiskGroupName() {
		return diskGroupName;
	}
	public void setDiskGroupName(String diskGroupName) {
		this.diskGroupName = diskGroupName;
	}
	public String getSampleSchema() {
		return sampleSchema;
	}
	public void setSampleSchema(String sampleSchema) {
		this.sampleSchema = sampleSchema;
	}
	public String getRedoLogFileSize() {
		return redoLogFileSize;
	}
	public void setRedoLogFileSize(String redoLogFileSize) {
		this.redoLogFileSize = redoLogFileSize;
	}
	public String getAutomaticMemoryManagement() {
		return automaticMemoryManagement;
	}
	public void setAutomaticMemoryManagement(String automaticMemoryManagement) {
		this.automaticMemoryManagement = automaticMemoryManagement;
	}
	public String getInitParams() {
		return initParams;
	}
	public void setInitParams(String initParams) {
		this.initParams = initParams;
	}
	public String getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public String getHostUser() {
		return hostUser;
	}
	public void setHostUser(String hostUser) {
		this.hostUser = hostUser;
	}
	
	
	
}
