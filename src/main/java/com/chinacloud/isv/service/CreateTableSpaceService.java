package com.chinacloud.isv.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacloud.isv.entity.ValueProvider;
import com.jcraft.jsch.JSchException;
@Service
public class CreateTableSpaceService {
	private static final Logger logger = LogManager.getLogger(CreateTableSpaceService.class);
	
	@Autowired
	OracleDriverService oracleDriverService;
	@Autowired
	OracleUserService oracleUserService;
	@Autowired
	SSHService sshService;
	
	/**
	 * 
	 * @param vp
	 * @return null is success otherwise it is error message
	 */
	public String createTableSpace(ValueProvider vp){
		 
		//1. make a create table space sql
		String createTableSpace = null;
		createTableSpace = "create tablespace "+vp.getTableSpaceName();
		logger.debug("create table space SQL====>"+createTableSpace);
		Statement statement = oracleDriverService.getStatement(vp.getOracleConnectionUrl(), vp.getUserName(),vp.getPassword());
		logger.debug(vp.getOracleConnectionUrl());
		logger.debug(vp.getUserName());
		logger.debug(vp.getPassword());
		if(null == statement){
			return "connect oracle failed";
		}
		
		//2. query the table space is already exist
		String queryTableSpace = "select tablespace_name, file_id,file_name from dba_data_files where tablespace_name='"+vp.getTableSpaceName().toUpperCase()+"'  order by tablespace_name";
		logger.debug("query table space SQL=====> "+queryTableSpace);
		try {
			ResultSet rs = statement.executeQuery(queryTableSpace);
			if(rs.next()){
				logger.debug(rs.getString("tablespace_name"));
			}
			logger.debug("the  row ============>"+rs.getRow());
			if(rs.getRow() > 0){
				return "the table space name is already exist";
			}
		} catch (SQLException e) {
			logger.error("query table space failed, error message:"+e.getLocalizedMessage());
			e.printStackTrace();
			return "when query table space failed, error message: "+e.getLocalizedMessage();
		}
		try {
			statement.execute(createTableSpace);
			logger.info("=============== finished create table space ================");
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
		logger.info("=============== finished create user ================");
		
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
		logger.info("=============== finished grant user ================");
		if(null != statement){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		oracleDriverService.close();
		return null;
	}
	
	public String dropTableSpace(ValueProvider vp){
		String dropTableSpace = "DROP TABLESPACE "+vp.getTableSpaceName()+" including contents and datafiles";
		Statement statement = oracleDriverService.getStatement(vp.getOracleConnectionUrl(), vp.getUserName(), vp.getPassword());
		try {
			statement.execute(dropTableSpace);
			logger.info("=============== finished drop table space ================");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("drop table space  "+vp.getTableSpaceName()+" failed, error message:"+e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
		
		if(null != statement){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//delte oracle database 
		String deleteComand = "dbca -silent -deleteDatabase -sourceDB "+vp.getGdbname()+" -sysDBAUserName "+vp.getUserName()+" -sysDBAPassword "+vp.getPassword();
		String shell_result = null;
		try {
			shell_result = sshService.execCmd(deleteComand,vp.getHostUser(),vp.getOracleHostPassword(),vp.getOracleHost());
			logger.info("dbca 的执行结果shell_result:===========>>"+shell_result);
		} catch (JSchException e) {
			e.printStackTrace();
			logger.error("ssh service error, error message: "+e.getLocalizedMessage());
		}
		if(null != shell_result){
			return shell_result;
		}
		oracleDriverService.close();
		return null;
	}

}
