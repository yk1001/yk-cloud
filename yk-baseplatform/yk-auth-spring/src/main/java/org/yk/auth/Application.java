package org.yk.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
public class Application {
	
    public static void main( String[] args ){
    	SpringApplication.run(Application.class, args);
    }
    
}
