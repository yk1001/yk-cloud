package org.yk.demo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@EnableFeignClients(basePackages = {"org.yk.demo.api.client"})
public class Application
{
    	public static void main( String[] args )
        {
        	SpringApplication.run(Application.class, args);
        }
}
