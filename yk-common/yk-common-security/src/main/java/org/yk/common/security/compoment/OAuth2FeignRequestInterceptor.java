package org.yk.common.security.compoment;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    /**
     * The authorization header name.
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * The {@code Bearer} token type.
     */
    private static final String BEARER_TOKEN_TYPE = "Bearer ";

	public void apply(feign.RequestTemplate template) {
		try {
            if (template.headers().containsKey(AUTHORIZATION_HEADER)) {
                log.warn("The Authorization token has been already set.");
                return;
            }
        	Authentication  authentication  = SecurityContextHolder.getContext().getAuthentication();
        	if(authentication == null){
        		return;
        	}
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            if(details == null){
        		return;
        	}
            String token = details.getTokenValue();
            template.header(AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE+token);
        } catch(Exception ex){
            log.info("Exception occurred while set the OAuth2 token: {}", ex.getMessage());
        }
		
	}

}
