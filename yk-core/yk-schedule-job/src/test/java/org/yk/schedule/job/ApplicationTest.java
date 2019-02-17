package org.yk.schedule.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

/**
 * ClassName: Application <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Sep 5, 2016 1:50:28 PM <br/>
 *
 * @author yukang
 * @version
 * @since JDK 1.8
 */
@EnableDiscoveryClient(autoRegister=false)
@SpringBootApplication
@ComponentScan(excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = SpringBootApplication.class) })
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
}
