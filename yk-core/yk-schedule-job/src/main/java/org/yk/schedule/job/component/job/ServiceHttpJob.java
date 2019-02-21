package org.yk.schedule.job.component.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;

@Slf4j
public class ServiceHttpJob  implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("job data is :{}",JSONObject.toJSONString(context.getJobDetail().getJobDataMap()));
	}

}
