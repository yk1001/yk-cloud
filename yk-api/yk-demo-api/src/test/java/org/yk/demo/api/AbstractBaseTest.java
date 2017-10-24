package org.yk.demo.api;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTest.class})
public abstract class AbstractBaseTest {
	
	protected final static String token = "4b8175f5-05f0-4722-a1e4-3bf509468138";
}
