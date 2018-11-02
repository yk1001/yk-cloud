package org.yk.demo.api;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = { "org.yk.demo.api.client" })
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public GracefulShutdown gracefulShutdown() {
		return new GracefulShutdown();
	}

	/**
	 * 配置tomcat
	 * @return
	 */
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addConnectorCustomizers(gracefulShutdown());
		return tomcat;
	}

	/**
	 * 优雅关闭 Spring Boot。容器必须是 tomcat
	 */
	private class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
		private volatile Connector connector;
		private final int waitTime = 60*2;

		@Override
		public void customize(Connector connector) {
			this.connector = connector;
		}

		@Override
		public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
			log.info("接收到停机事件,正在执行停机...");
			this.connector.pause();
			Executor executor = this.connector.getProtocolHandler().getExecutor();
			if (executor instanceof ThreadPoolExecutor) {
				try {
					ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
					threadPoolExecutor.shutdown();
					if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
						log.warn("Tomcat 进程在" + waitTime + " 秒内无法结束，尝试强制结束");
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
