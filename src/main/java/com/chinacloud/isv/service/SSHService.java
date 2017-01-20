package com.chinacloud.isv.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service
public class SSHService {
	private static final Logger logger = LogManager.getLogger(SSHService.class);
	private  JSch jsch;  
    private  Session session;  
    /** 
     * 连接到指定的IP 
     *  
     * @throws JSchException 
     */  
    public  void connect(String user, String passwd, String host) throws JSchException {  
        jsch = new JSch();  
/*        MyUserInfo userInfo = new MyUserInfo();  
        com.jcraft.jsch.Logger logger = new SettleLogger();
        JSch.setLogger(logger);*/
        session = jsch.getSession(user, host, 22);  
        session.setPassword(passwd);  
        //session.setUserInfo(userInfo);
          
        java.util.Properties config = new java.util.Properties();  
        config.put("StrictHostKeyChecking", "no");  
        config.put("userauth.gssapi-with-mic", "no"); 
        session.setConfig(config);        
        session.connect();  
    }  
  
    /** 
     * 执行相关的命令 
     * @throws JSchException  
     */  
    public  String execCmd(String command, String user, String passwd, String host) throws JSchException {  
        connect(user, passwd, host);  
          
        BufferedReader reader = null;  
        Channel channel = null;  
  
        try {  
        	logger.info("==========ssh time=======");
            if (command != null) {  
                channel = session.openChannel("exec");  
                ((ChannelExec) channel).setCommand(command);
                channel.setInputStream(null);  
                ((ChannelExec) channel).setErrStream(System.err); 
                InputStream in = channel.getInputStream();
                channel.connect();  
                int status = 0;
                int completeStatus = 0;
                if(channel.isClosed()){ 
                	status = channel.getExitStatus();
                }
                logger.info("exit-status: "+status);
              
                reader = new BufferedReader(new InputStreamReader(in));  
               
                String buf = null;  
                logger.info("ssh execute shell  message: ");
                while ((buf = reader.readLine()) != null) {  
                    System.out.println(buf);  
                    logger.info(buf);
                    if(buf.contains("100% complete")){
                    	completeStatus = 1;
                    }
                }  
               
                logger.info("finished_status====================>"+completeStatus);
                if(1 != completeStatus){
                	return "如果是申请，则数据库名已存在，如果是删除，则数据库有可能已经被删除。或者数据库系统出错,请排查！";
                }
                
                if(-1 == status){
                	 try {  
                         reader.close();  
                     } catch (IOException e) {  
                         e.printStackTrace();  
                     }  
                     channel.disconnect();  
                     session.disconnect();  
                	return "dbca shell executed failed";
                }
            }else{
            	logger.error("ssh command is empty");
            }
            
        } catch (IOException e) {  
            e.printStackTrace();  
            logger.error("ssh service IOException, error message:"+e.getLocalizedMessage());
        } catch (JSchException e) {  
            e.printStackTrace();  
            logger.error("ssh service JSchException, error message:"+e.getLocalizedMessage());
        } finally {  
            try {  
                reader.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            channel.disconnect();  
            session.disconnect();  
        }  
        return null;
    }  

}
