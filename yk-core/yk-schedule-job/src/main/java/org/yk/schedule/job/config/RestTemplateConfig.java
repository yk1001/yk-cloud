package org.yk.schedule.job.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RestTemplateConfig {
	
	private final int CONNECT_TIMEOUT = 10000;
    private final int SOCKET_TIMEOUT = 10000;
    /**
     * 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，等待时间过长将是灾难性的
     */
    private final int CONNECTIONREQUEST_TIMEOUT = 200;
    /**
     * 最大并发
     */
    private final int MAX_TOTAL = 1000;
    /**
     * 同路由的并发数
     */
    private final int MAX_PERROUTE = new Double(MAX_TOTAL * 0.8).intValue();

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		/**
		 * StringHttpMessageConverter 默认使用ISO-8859-1编码，此处修改为UTF-8
		 */
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
			}
		}
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LogClientHttpRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
		/**
		 * Spring使用；两种方式实现http请求
		 * 1.SimpleClientHttpRequestFactory，使用J2SE提供的方式（既java.net包提供的方式）
		 * 创建底层的Http请求连接
		 * 2.HttpComponentsClientHttpRequestFactory，底层使用HttpClient访问远程的Http服务，
		 * 使用HttpClient可以配置连接池和证书等信息
		 */
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Bean
	public HttpClient httpClient() {
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
		connectionManager.setMaxTotal(MAX_TOTAL);
		connectionManager.setDefaultMaxPerRoute(MAX_PERROUTE);
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(SOCKET_TIMEOUT)	// 读取超时时间
				.setConnectTimeout(CONNECT_TIMEOUT) 	// 连接超时时间
				.setConnectionRequestTimeout(CONNECTIONREQUEST_TIMEOUT)
				.build();
		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig)
				.disableAutomaticRetries()
		// 设置重试次数，默认三次未开启
		//				.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
				.build();
		return httpClient;
	}
	
	private class LogClientHttpRequestInterceptor implements  ClientHttpRequestInterceptor{

		@Override
		public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution)
				throws IOException {
			log.info("------------------------rest request----------------------------");
            log.info("request url:" + httpRequest.getURI());
            log.info("request header:" + httpRequest.getHeaders().toString());
            log.info("request method:" + httpRequest.getMethodValue());
            log.info("request body:" + new String(bytes));
            log.info("----------------------------------------------------------------");
 
            ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
            byte[] bys = new byte[0];
            if(response.getStatusCode() == HttpStatus.OK)
                bys = new byte[response.getBody().available()];
 
            log.info("-----------------------rest response----------------------------");
            log.info("response code:" + response.getStatusCode());
            log.info("response code msg:" + response.getStatusText());
            log.info("response headers:" + response.getHeaders().toString());
            log.info("response body:" + new String(bys, "utf-8"));
            log.info("----------------------------------------------------------------");
            return response;
		}
	}

}
