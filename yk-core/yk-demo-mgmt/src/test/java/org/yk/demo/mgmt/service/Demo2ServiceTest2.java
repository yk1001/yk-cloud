package org.yk.demo.mgmt.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.yk.demo.mgmt.service.impl.Demo1ServiceImpl;
import org.yk.demo.mgmt.service.impl.Demo2ServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class Demo2ServiceTest2 {
	
	@Mock
	private Demo2ServiceImpl demo2Service;
	@InjectMocks
	private Demo1ServiceImpl demo1Service;
	
	@Test
	public void method1_test() throws InterruptedException{
		Mockito.when(demo2Service.method1()).thenReturn("method1");
		Mockito.when(demo2Service.method2()).thenReturn("method2");
		Thread.sleep(1000*1);
		Assert.assertEquals("success", demo1Service.method1());
	}
	
	@Test
	public void method2_test() throws InterruptedException{
		Mockito.when(demo2Service.method1()).thenReturn("method1");
		Mockito.when(demo2Service.method2()).thenReturn("method2");
		Thread.sleep(1000*2);
		Assert.assertEquals("success", demo1Service.method1());
	}
}
