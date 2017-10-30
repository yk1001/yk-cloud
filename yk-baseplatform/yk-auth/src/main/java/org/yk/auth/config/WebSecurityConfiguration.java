package org.yk.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService myUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	private DaoAuthenticationProvider daoAuthenticationProvider;
	 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.daoAuthenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public DaoAuthenticationProvider getDaoAuthProvider(){
        DaoAuthenticationProvider tempProvider = new DaoAuthenticationProvider();
        tempProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        tempProvider.setUserDetailsService(this.userDetailsService);
        return tempProvider;
    }
	
}
