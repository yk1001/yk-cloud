package org.yk.demo.api.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yk.common.data.demomgmt.DemoData;

import io.swagger.annotations.ApiOperation;

@FeignClient(name="demo-mgmt")
//@FeignClient(name="demo-mgmt",url="http://127.0.0.1:9101")
public interface DemoMgmtClient {

	@RequestMapping(value="/noauth/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test(@RequestBody DemoData demoData);
	
	@RequestMapping(value="/authsec/demoData",method = RequestMethod.POST)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public DemoData test2(@RequestBody DemoData demoData);
}
