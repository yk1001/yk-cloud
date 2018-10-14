package org.yk.auth.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoderTest {

	@Test
	public void bCryptPasswordEncoder_test(){
		String rawPwd = "12345";
		PasswordEncoder passwordEncode = new BCryptPasswordEncoder(6);
		String encodedPwd = passwordEncode.encode(rawPwd);
		log.info("encodedPwd = {}",encodedPwd);
		Assert.assertTrue(passwordEncode.matches(rawPwd, encodedPwd));
	}
}
