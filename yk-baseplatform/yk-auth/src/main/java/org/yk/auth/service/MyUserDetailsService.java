package org.yk.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

	private final static String PASSWORD_DEFAULT = "$2a$10$tuH.C7kuP9/zoVgVd4B1jebLtIhN8grXJdJdpV/Owosw0Cz46bvDu";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		UserDetails userDetails = new User(username, PASSWORD_DEFAULT, authorities);
		return userDetails;
	}

}
