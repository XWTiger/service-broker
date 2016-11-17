package com.chinacloud.isv.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Test {

	@Autowired
	JdbcTemplate jdbcTemplate;
	ResultSet rs = null;  
	  Statement stmt = null;  
	  Connection conn = null; 
	public void test(){
		
	}

	public void  query(){
		jdbcTemplate.execute("create tablespace DMS datafile '/home/oracle/app/oradata/orcl/xiaweihu.dbf' size 10M autoextend on next 5M maxsize 3000M");
		java.util.List<Map<String, Object>> list =  jdbcTemplate.queryForList("select tablespace_name, file_id,file_name,round(bytes/(1024*1024),0) total_space from dba_data_files order by tablespace_name");
		System.out.println(list.get(list.size()-1).get("file_name").toString());
		//jdbcTemplate.execute("select tablespace_name, file_id,file_name,round(bytes/(1024*1024),0) total_space from dba_data_files order by tablespace_name");
	}
	
}
