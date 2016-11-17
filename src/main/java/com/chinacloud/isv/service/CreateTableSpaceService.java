package com.chinacloud.isv.service;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacloud.isv.entity.ValueProvider;

public class CreateTableSpaceService {
	private static final Logger logger = LogManager.getLogger(CreateTableSpaceService.class);
	
	@Autowired
	OracleDriverService oracleDriverService;
	@Autowired
	OracleUserService oracleUserService;
	
	public String createTableSpace(ValueProvider vp){
		//1. make a create table space sql
		String createTableSpace = "create tablespace"+vp.getTableSpaceName()+"datafile '"+vp.getTableSpaceLocation()+"/"+vp.getTableSpaceName()+".dbf ' size "+vp.getTableSpaceSize()+"M autoextend on next "+vp.getTableSpaceRiseNumber()+"M maxsize "+vp.getTableSpaceMaxSize()+"M";
		Statement statement = oracleDriverService.getStatement(vp.getOracleConnectionUrl(), vp.getUserName(),vp.getPassword());
		try {
			statement.execute(createTableSpace);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("creat table space failed, error message:"+e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		//2. create user 
		String createUserResult = oracleUserService.createUser(statement, vp);
		if(null != createUserResult){
			return createUserResult;
		}
		
		//3. give a table space to user
		String userTableSpace = "alter user "+vp.getCreateUserName()+" default tablespace "+vp.getTableSpaceName();
		try {
			statement.execute(userTableSpace);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("alert table space to "+vp.getCreateUserName()+" failed, error message:"+e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		//4. grant privileges resource connn to user
		String grantPriRes = oracleUserService.grantPrivileges(statement, vp);
		if(null != grantPriRes){
			return grantPriRes;
		}
		
		oracleDriverService.close();
		return null;
	}

}
