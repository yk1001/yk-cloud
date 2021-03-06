package org.yk.demo.api.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 优雅停机配置
 * shutdown 请求后，会立即更新该服务在注册中心状态为不可用，使得其他服务在服务发现时，该服务不可用
 * 然后等待30秒后，设置web容器不再接收请求，
 * 最后，当所有线程回收到线程池后，服务停止
 * */
@Component
@Slf4j
public class GracefulShutdownConfig {

	@Autowired
	@Lazy
	private MyHealthCheckeIndicator myHealthCheckeIndicator;
	
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
			myHealthCheckeIndicator.setUp(false);
			try {
				Thread.sleep(1000*30);
			} catch (InterruptedException e) {
				log.error(e.getMessage(),e);
			}
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
