package org.yk.schedule.job.component.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpJob implements Job{

	public final static String JOBDATA_URL = "url";
	public final static String JOBDATA_METHOD = "method";
	public final static String JOBDATA_CONTENTTYPE = "contentType";
	public final static String JOBDATA_CONTENT = "content";
	
	public enum Method{
		GET,POST,PUT,DELETE,PATCH
	}
	public enum ContentType{
		FORM,JSON
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("job data is :{}",JSONObject.toJSONString(context.getJobDetail().getJobDataMap()));
	}

}
