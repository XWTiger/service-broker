package com.chinacloud.isv.service;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class OracleDriverService {
	private static final Logger logger = LogManager.getLogger(OracleDriverService.class);
	Statement stmt = null;
	Connection conn = null;
	public Statement getStatement(String connectUrl,String userName,String password) {
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(connectUrl,userName, password);
			stmt = conn.createStatement();
		} catch (Exception e) {
			logger.error("connect oracle failed, error msg =====>"+e.getLocalizedMessage());
			return null;
		}
		return stmt;
	}

	public void close(){
		if(null != stmt){
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		if(null != conn){
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
	}
}
