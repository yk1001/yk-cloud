package org.yk.scheduler.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.constant.ResultCode;
import org.yk.common.data.GeneralContentResult;
import org.yk.common.data.GeneralResult;
import org.yk.common.data.schedulejob.CronHttpJobCreateReq;
import org.yk.common.data.schedulejob.CronHttpJobCreateResp;
import org.yk.common.data.schedulejob.JobCancleReq;
import org.yk.scheduler.service.JobService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class JobController {

	@Autowired
	private JobService jobService;
	
	@RequestMapping(value="/noauth/cronHttpJob",method = RequestMethod.POST)
	@ApiOperation(value = "创建任务", notes = "创建任务")
	public GeneralContentResult<CronHttpJobCreateResp> createCronHttpJob(@RequestBody CronHttpJobCreateReq cronHttpJobCreateReq){
		GeneralContentResult<CronHttpJobCreateResp> result = new GeneralContentResult<CronHttpJobCreateResp>();
		try {
			CronHttpJobCreateResp resp = jobService.createCronHttpJob(cronHttpJobCreateReq);
			result.setResultCode(ResultCode.OPERATE_SUCCESS);
			result.setResultContent(resp);
			return result;
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SCHEDULEJOB_SCHEDULE_ERROR);
			result.setDetailDescription(e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SERVER_UNAVALIABLE);
			result.setDetailDescription(e.getMessage());
			return result;
		}
	}
	
	@RequestMapping(value="/noauth/job",method = RequestMethod.DELETE)
	@ApiOperation(value = "取消任务", notes = "取消任务")
	public GeneralResult cancelJob(@RequestBody JobCancleReq jobCancleReq){
		GeneralResult result = new GeneralResult();
		try {
			boolean cancelResult = jobService.cancelJob(jobCancleReq);
			if(cancelResult){
				result.setResultCode(ResultCode.OPERATE_SUCCESS);
				return result;
			}else{
				result.setResultCode(ResultCode.OPERATE_FAILED);
				return result;
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SCHEDULEJOB_SCHEDULE_ERROR);
			result.setDetailDescription(e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SERVER_UNAVALIABLE);
			result.setDetailDescription(e.getMessage());
			return result;
		}
	}
	
}
