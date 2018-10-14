package org.yk.auth.util;

import java.util.Base64;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base64Test {

	@Test
	public void encode_test(){
		String src = "yk:secret";
		String encodedStr = Base64.getEncoder().encodeToString(src.getBytes());
		log.info("src = {} encodedStr = {}",src,encodedStr);
	}
}
