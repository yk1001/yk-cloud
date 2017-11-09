package org.yk.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.data.demomgmt.DemoData;
import org.yk.demo.api.client.DemoMgmtClient;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DemoConroller {

	@Autowired
	private DemoMgmtClient demoMgmtClient;
	
	@RequestMapping(value="/noauth/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test(@RequestBody DemoData demoData){
		log.info("接收到参数：{}",JSONObject.toJSONString(demoData));
		return demoMgmtClient.test(demoData);
	}
	
	@RequestMapping(value="/authsec/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test2(@RequestBody DemoData demoData){
		log.info("接收到参数：{}",JSONObject.toJSONString(demoData));
		return demoMgmtClient.test2(demoData);
	}
	
}
