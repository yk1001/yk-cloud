package org.yk.scheduler.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yk.common.data.schedulejob.CronHttpJobCreateReq;
import org.yk.common.data.schedulejob.CronHttpJobCreateResp;
import org.yk.common.data.schedulejob.CronServiceJobCreateReq;
import org.yk.common.data.schedulejob.CronServiceJobCreateResp;
import org.yk.common.data.schedulejob.JobCancleReq;
import org.yk.scheduler.component.job.HttpJob;
import org.yk.scheduler.service.JobService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobServiceImpl implements JobService{
	
	private final static String TRIGGER_PREFIX = "trigger-";
	
	@Autowired
	private Scheduler  clusteredScheduler;
	
	@Override
	public CronHttpJobCreateResp createCronHttpJob(CronHttpJobCreateReq cronHttpJobCreateReq) throws SchedulerException {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(HttpJob.JOBDATA_URL,cronHttpJobCreateReq.getUrl());
		jobDataMap.put(HttpJob.JOBDATA_METHOD,cronHttpJobCreateReq.getMethod());
		jobDataMap.put(HttpJob.JOBDATA_CONTENT,cronHttpJobCreateReq.getContent());
		JobDetail jobDetail = JobBuilder.newJob(HttpJob.class).usingJobData(jobDataMap)
				.withIdentity(cronHttpJobCreateReq.getName(), cronHttpJobCreateReq.getGroup()).build();
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronHttpJobCreateReq.getCronExpression()); 
		CronTrigger cronTrigger = TriggerBuilder.newTrigger()
				.withIdentity(TRIGGER_PREFIX + cronHttpJobCreateReq.getName(), cronHttpJobCreateReq.getGroup())
				.withSchedule(cronScheduleBuilder).build();
		clusteredScheduler.scheduleJob(jobDetail, cronTrigger);
		CronHttpJobCreateResp cronHttpJobCreateResp= new CronHttpJobCreateResp();
		cronHttpJobCreateResp.setName(cronHttpJobCreateReq.getName());
		cronHttpJobCreateResp.setGroup(cronHttpJobCreateReq.getGroup());
		return cronHttpJobCreateResp;
	}

	@Override
	public CronServiceJobCreateResp createCronServiceJob(CronServiceJobCreateReq cronServiceJobCreateReq)
			throws SchedulerException {
		// TODO 待扩展
		return null;
	}
	
	@Override
	public boolean cancelJob(JobCancleReq jobCancleReq) throws SchedulerException {
		JobKey key = new JobKey(jobCancleReq.getName(), jobCancleReq.getGroup());
		return clusteredScheduler.deleteJob(key);
	}

}
