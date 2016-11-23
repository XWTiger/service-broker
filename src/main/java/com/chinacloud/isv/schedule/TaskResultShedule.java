package com.chinacloud.isv.schedule;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacloud.isv.domain.TaskResult;
import com.chinacloud.isv.domain.TaskStack;
import com.chinacloud.isv.persistance.TaskResultDao;
import com.chinacloud.isv.persistance.TaskStackDao;
import com.chinacloud.isv.util.CaseProvider;
import com.chinacloud.isv.util.MSUtil;

@Service
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "service")
@Transactional
public class TaskResultShedule {

	@Autowired
	TaskResultDao taskResultDao;
	@Autowired
	TaskStackDao taskStackDao;

	@Value("${service.taskResultMaxNumber}")
	private int taskResultNumber;
	@Value("${service.deleteTime}")
	private int Time;
	private static final Logger logger = LogManager.getLogger(TaskResultShedule.class);

	@Scheduled(fixedRate = 50000)
	private void manageTaskResult() {
		logger.debug("this is  locked task scan");
		int number = taskResultDao.getCount();
		if (number > taskResultNumber) {
			logger.info("real number --->" + number);
			logger.info("delete max number --->" + taskResultNumber);
			taskResultDao.deleteResult(Time);
		}
		ArrayList<TaskStack> taskList = taskStackDao.getTasksByTime(Time * 2);
		if (null != taskList && 0 != taskList.size()) {
			for (TaskStack taskStack : taskList) {
				TaskResult taskResult =	MSUtil.getResultInstance(taskStack.getId(), CaseProvider.FAILED_STATUS, "post", "==========The task be dead locked========", taskStack.getCallBackUrl(), taskStack.getParams(), taskStack.getEventType());
			    taskStackDao.deleteTask(taskStack.getId());
			    taskResultDao.addResult(taskResult);
			}
		}
		taskList = null;
	}

}
