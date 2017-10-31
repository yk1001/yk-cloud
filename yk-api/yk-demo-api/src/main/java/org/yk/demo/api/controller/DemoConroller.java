package org.yk.demo.api.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.demo.api.data.DemoData;

import io.swagger.annotations.ApiOperation;

@RestController
public class DemoConroller {

	@RequestMapping(value="/noauth/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test(@RequestBody DemoData demoData){
		return demoData;
	}
	
	@RequestMapping(value="/authsec/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test2(@RequestBody DemoData demoData){
		return demoData;
	}
}
