package org.yk.scheduler.service;

import org.quartz.SchedulerException;
import org.yk.common.data.schedulejob.CronHttpJobCreateReq;
import org.yk.common.data.schedulejob.CronHttpJobCreateResp;
import org.yk.common.data.schedulejob.CronServiceJobCreateReq;
import org.yk.common.data.schedulejob.CronServiceJobCreateResp;
import org.yk.common.data.schedulejob.JobCancleReq;

public interface JobService {

	public CronHttpJobCreateResp createCronHttpJob(CronHttpJobCreateReq cronHttpJobCreateReq) throws SchedulerException;
	
	public CronServiceJobCreateResp createCronServiceJob(CronServiceJobCreateReq cronServiceJobCreateReq) throws SchedulerException;
	
	public boolean cancelJob(JobCancleReq jobCancleReq)  throws SchedulerException ;
}
