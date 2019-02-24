package org.yk.schedule.job.component.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpJob  implements Job,ApplicationContextAware{

	public final static String JOBDATA_URL = "url";
	public final static String JOBDATA_METHOD = "method";
	public final static String JOBDATA_CONTENT = "content";
	
	public enum Method{
		GET,POST,PUT,DELETE,PATCH
	}
	
	private RestTemplate restTemplate;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		restTemplate = (RestTemplate) applicationContext.getBean("restTemplate");
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		log.info("httpJob start key:{},data :{}",context.getJobDetail().getKey(),JSONObject.toJSONString(context.getJobDetail().getJobDataMap()));
		Method method = Method.valueOf(jobDataMap.getString(JOBDATA_METHOD));
		String url = jobDataMap.getString(JOBDATA_URL);
		Object content =  jobDataMap.get(JOBDATA_CONTENT);
		switch (method) {
			case GET:
				{
					ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
				}
				break;
			case POST:
				{
					ResponseEntity<String> resp = restTemplate.postForEntity(url, content, String.class);
				}
				break;
			case PUT:
				{
					restTemplate.put(url, content);
				}
				break;
			case DELETE:
				{
					restTemplate.delete(url);
				}
				break;
			case PATCH:
				{
					String resp = restTemplate.patchForObject(url, content, String.class);
				}
				break;
			default:
				break;
		}
	}
	
}
