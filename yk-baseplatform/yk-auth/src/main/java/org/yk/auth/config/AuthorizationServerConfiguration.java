package org.yk.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	@Lazy
    private PasswordEncoder passwordEncoder;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("yk").secret(passwordEncoder.encode("secret"))
				.authorizedGrantTypes("implicit","password", "refresh_token", "client_credentials")
				.scopes("read", "write", "trust");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.tokenStore(new InMemoryTokenStore()).reuseRefreshTokens(true);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				// 开启/oauth/token_key验证端口无权限访问
				.tokenKeyAccess("permitAll()")
				// 开启/oauth/check_token验证端口认证权限访问
				.checkTokenAccess("isAuthenticated()");
	}
	
}