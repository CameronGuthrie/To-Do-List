package com.qa.tdl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Disabled
	@Test
	void contextTest() {
		Application.main( new String[] {} );
	}

}
