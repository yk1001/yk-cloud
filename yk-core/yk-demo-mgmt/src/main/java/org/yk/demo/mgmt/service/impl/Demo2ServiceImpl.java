package org.yk.demo.mgmt.service.impl;

import org.springframework.stereotype.Service;
import org.yk.demo.mgmt.service.Demo2Service;

@Service
public class Demo2ServiceImpl implements Demo2Service {

	@Override
	public String method1() {
		return "fail";
	}

	@Override
	public String method2() {
		return "fail";
	}

}
