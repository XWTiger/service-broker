package com.chinacloud.isv.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chinacloud.isv.entity.ValueProvider;

@Service
public class OracleUserService {
	private static final Logger logger = LogManager.getLogger(OracleUserService.class);
	
	
	/**
	 * create user if it not exist
	 * 
	 * @author tiger
	 * @return
	 */
	public String createUser(Statement statement,ValueProvider vp) {
		// 2. if already exist user
		try {
			ResultSet rs = statement.executeQuery("select username,account_status from dba_users where username='"
					+ vp.getCreateUserName().toUpperCase() + "'");
			if(0 == rs.getRow()){
				// 3. create user and grant privileges
				String createUser = "create user " + vp.getCreateUserName() + " identified by " + vp.getCreateUserPassword();
				try {
					statement.execute(createUser);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("creat table space failed, error message:" + e.getLocalizedMessage());
					return e.getLocalizedMessage();
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			logger.error("query table dba_users failed, error message:" + e1.getLocalizedMessage());
			return e1.getLocalizedMessage();
		}
		return null;
	}
	/**
	 * grant connnect resource privileges
	 * @param statement
	 * @param vp
	 * @return null if it is success or error message
	 */
	public  String grantPrivileges(Statement statement,ValueProvider vp){
		String grantPri = "GRANT CONNECT,RESOURCE TO "+vp.getCreateUserName();
		try {
			statement.execute(grantPri);
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
	}
	/**
	 * revoke connnect resource privileges
	 * @param statement
	 * @param vp
	 * @return
	 */
	public String revokePrivileges(Statement statement,ValueProvider vp){
		String revokePri = "REVOKE CONNECT,RESOURCE FROM "+vp.getCreateUserName();
		try {
			statement.execute(revokePri);
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
	}
}
