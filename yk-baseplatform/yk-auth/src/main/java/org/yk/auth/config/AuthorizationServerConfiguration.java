package org.yk.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailsService myUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("yk")
                .secret("secret")
                .authorizedGrantTypes("password","refresh_token","client_credentials")
                .scopes("read","write","trust")
//                .authorities("ROLE_CLIENT","ROLE_TRUSTED_CLIENT")
//                .accessTokenValiditySeconds(300)
//                .refreshTokenValiditySeconds(300)
        ;

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        	.userDetailsService(myUserDetailsService)
        	.tokenStore(new InMemoryTokenStore())
        	.reuseRefreshTokens(true)
        	.authenticationManager(authenticationManager)
        ;
    }
    
    @Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
        ;
    	security.allowFormAuthenticationForClients();
	}
}