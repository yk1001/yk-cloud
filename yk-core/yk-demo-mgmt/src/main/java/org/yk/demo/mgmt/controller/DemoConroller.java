package org.yk.demo.mgmt.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.data.demomgmt.DemoData;

import io.swagger.annotations.ApiOperation;

@RestController
public class DemoConroller {

	@RequestMapping(value="/noauth/demoData",method = RequestMethod.GET)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test(@RequestBody DemoData demoData){
		return demoData;
	}
	
	@RequestMapping(value="/authsec/demoData",method = RequestMethod.GET)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test2(@RequestBody DemoData demoData){
		return demoData;
	}
}
