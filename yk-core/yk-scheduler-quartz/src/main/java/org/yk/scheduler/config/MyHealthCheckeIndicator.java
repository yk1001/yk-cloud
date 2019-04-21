package org.yk.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.netflix.discovery.EurekaClient;

@Component
public class MyHealthCheckeIndicator implements HealthIndicator{
	
	private boolean up = true;
	
	@Autowired
	private EurekaClient eurekaClient;
	
    @Override
    public Health health() {
        if (up) {
            return new Health.Builder().up().build();
        } else {
            return new Health.Builder().down().build();
        }
 
    }
 
    public boolean isUp() {
        return up;
    }
 
    public void setUp(boolean up) {
    	if(!up){
    		eurekaClient.shutdown();
    	}
        this.up = up;
    }
}
