package org.yk.schedule.job.service;

import org.quartz.SchedulerException;
import org.yk.common.data.schedulejob.CronHttpJobCreateReq;
import org.yk.common.data.schedulejob.CronHttpJobCreateResp;
import org.yk.common.data.schedulejob.JobCancleReq;

public interface JobService {

	public CronHttpJobCreateResp createCronHttpJob(CronHttpJobCreateReq cronHttpJobCreateReq) throws SchedulerException;
	
	public boolean cancelJob(JobCancleReq jobCancleReq)  throws SchedulerException ;
}
