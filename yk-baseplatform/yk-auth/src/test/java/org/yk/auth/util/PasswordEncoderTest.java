package org.yk.auth.util;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoderTest {

	@Test
	public void bCryptPasswordEncoder_test(){
		String pwdWord = new BCryptPasswordEncoder().encode("12345");
		log.info(pwdWord);
	}
}
