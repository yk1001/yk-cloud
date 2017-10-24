/**
 * Project Name:liz-groupadmin-api
 * File Name:AbstractControllerTest.java
 * Package Name:com.gemii.lizcloud.api.group.admin
 * Date:2017年6月1日上午9:44:45
 * Copyright (c) 2017, yukang All Rights Reserved.
 *
*/

package org.yk.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

/**
 * ClassName:AbstractControllerTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年6月1日 上午9:44:45 <br/>
 * @author   yk
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@AutoConfigureMockMvc
public abstract class AbstractControllerTest extends AbstractBaseTest{

	@Autowired
	protected MockMvc mockMvc;
	
}

