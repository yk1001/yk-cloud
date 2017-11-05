package org.yk.demo.api.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.data.demoapi.Download;
import org.yk.common.data.demomgmt.DemoData;
import org.yk.demo.api.client.DemoMgmtClient;

import io.swagger.annotations.ApiOperation;

@RestController
public class DemoConroller {

	@Autowired
	private DemoMgmtClient demoMgmtClient;
	
	@RequestMapping(value="/noauth/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test(@RequestBody DemoData demoData){
		return demoMgmtClient.test(demoData);
	}
	
	@RequestMapping(value="/authsec/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test2(@RequestBody DemoData demoData){
		return demoMgmtClient.test2(demoData);
	}
	
}
