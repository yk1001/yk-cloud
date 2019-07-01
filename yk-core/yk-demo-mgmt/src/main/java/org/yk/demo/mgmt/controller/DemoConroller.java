package org.yk.demo.mgmt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.constant.ResultCode;
import org.yk.common.data.GeneralContentResult;
import org.yk.common.data.demomgmt.DemoData;

import io.swagger.annotations.ApiOperation;

@RestController
public class DemoConroller {

    @RequestMapping(value = "/authsec/demoData", method = RequestMethod.GET)
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public GeneralContentResult<DemoData> test2() {
        DemoData demoData = new DemoData();
        demoData.setId("1");
        demoData.setName("test");
        return new GeneralContentResult<DemoData>(ResultCode.OPERATE_SUCCESS,demoData);
    }
}
