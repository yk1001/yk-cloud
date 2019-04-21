package org.yk.scheduler.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.yk.scheduler.AbstractTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestTemplateTest extends AbstractTest{

	@Autowired
	private RestTemplate restTemplate;
	
	@Test
	public void requestTest(){
		String url = "http://www.baidu.com";
		ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
	}
	
}
