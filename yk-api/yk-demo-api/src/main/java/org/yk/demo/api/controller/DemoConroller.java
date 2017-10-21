package org.yk.demo.api.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.demo.api.data.DemoData;

@RestController
public class DemoConroller {

	@RequestMapping(value="/demo/demoData",method = RequestMethod.POST)
	public DemoData test(@RequestBody DemoData demoData){
		return demoData;
	}
}
