package org.yk.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.constant.ResultCode;
import org.yk.common.data.GeneralContentResult;
import org.yk.common.data.demomgmt.DemoData;
import org.yk.demo.api.client.DemoMgmtClient;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DemoConroller {

	@Autowired
	private DemoMgmtClient demoMgmtClient;
	
	@RequestMapping(value="/noauth/demoData",method = RequestMethod.GET)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public GeneralContentResult<DemoData> test(){
		log.info("接收到参数");
		log.error("error log test");
		// mock data
		try {
			Thread.sleep(1000*30);
		} catch (InterruptedException e) {
			log.error(e.getMessage(),e);
		}
		DemoData demoData = new DemoData();
		demoData.setId("1");
		demoData.setName("test");
		GeneralContentResult<DemoData> result = new GeneralContentResult<DemoData>();
		result.setResultCode(ResultCode.OPERATE_SUCCESS);
		result.setDetailDescription("success");
		result.setResultContent(demoData);
		return result;
	}
	
	@RequestMapping(value="/authsec/demoData",method = RequestMethod.GET)
	@ApiOperation(value = "测试接口", notes = "测试接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer") })
	public GeneralContentResult<DemoData> test2(){
		log.info("接收到参数");
		try {
			return demoMgmtClient.test2();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			GeneralContentResult<DemoData> result = new GeneralContentResult<DemoData> ();
			result.setResultCode(ResultCode.SERVER_UNAVALIABLE);
			return result;
		}
		
	}
	
}
