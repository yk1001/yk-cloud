package org.yk.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.yk.common.security.compoment.OAuth2FeignRequestInterceptor;

import feign.RequestInterceptor;

@Configuration
public class OAuth2FeignConfiguration {

	@Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2FeignRequestInterceptor();
    }
	
}
