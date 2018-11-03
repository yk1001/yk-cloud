package org.yk.demo.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yk.common.data.GeneralContentResult;
import org.yk.common.data.demomgmt.DemoData;

import io.swagger.annotations.ApiOperation;

@FeignClient(name="demo-mgmt")
//@FeignClient(name="demo-mgmt",url="http://127.0.0.1:9101")
public interface DemoMgmtClient {

	@RequestMapping(value="/authsec/demoData",method = RequestMethod.GET)
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public GeneralContentResult<DemoData> test2();
}
