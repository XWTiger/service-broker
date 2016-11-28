package com.chinacloud.isv.component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacloud.isv.entity.ValueProvider;
import com.chinacloud.isv.factory.WhiteholeFactory;
import com.chinacloud.isv.service.OracleDriverService;
import com.chinacloud.isv.util.CaseProvider;

@Component
public class QueryEvent implements EventDriver{
	private static final Logger logger = LogManager.getLogger(QueryEvent.class);
	
	@Autowired
	OracleDriverService oracleDriverService;
	
	@Override
	public String go(ValueProvider valueProvider) {
		logger.debug("一只穿云箭，千军万马来相见");
		Statement statement = oracleDriverService.getStatement(valueProvider.getOracleConnectionUrl(), valueProvider.getUserName(),valueProvider.getPassword());
		if(null == statement){
			return "connect oracle failed";
		}
		
		String queryTableSpace = "select tablespace_name, file_id,file_name from dba_data_files where tablespace_name='"+valueProvider.getTableSpaceName().toUpperCase()+"'  order by tablespace_name";
		logger.debug("query table space SQL=====> "+queryTableSpace);
		try {
			ResultSet rs = statement.executeQuery(queryTableSpace);
			if(rs.next()){
				logger.debug("query case, table space  name ==========>"+rs.getString("tablespace_name"));
			}
			logger.debug("the  row ============>"+rs.getRow());
			if(rs.getRow() > 0){
				valueProvider.setEventDealResult(CaseProvider.SUCESS_STATUS);
				return WhiteholeFactory.getWhiteholeMessage(valueProvider);
			}else{
				valueProvider.setEventDealResult(CaseProvider.SUCESS_STATUS);
				return WhiteholeFactory.getFailedMsg(valueProvider, "表空间不存在");
			}
		} catch (SQLException e) {
			logger.error("query table space failed, error message:"+e.getLocalizedMessage());
			e.printStackTrace();
			return "when query table space failed, error message: "+e.getLocalizedMessage();
		}
		
	
	}

	@Override
	public void close() {
		
	}

	

}
