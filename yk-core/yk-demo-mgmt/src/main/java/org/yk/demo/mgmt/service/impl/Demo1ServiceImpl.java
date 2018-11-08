package org.yk.demo.mgmt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yk.demo.mgmt.service.Demo1Service;
import org.yk.demo.mgmt.service.Demo2Service;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;

@Service
@Slf4j
public class Demo1ServiceImpl implements Demo1Service {

    @Autowired
    private Demo2Service demo2Service;

    @Override
    public String method1() {
        log.info(demo2Service.method1());
        log.info(demo2Service.method2());
        return "success";
    }

}
