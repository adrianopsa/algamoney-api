package com.algaworks.algamoney.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UserSystem  extends User{
	
	
	private static final long serialVersionUID = 1L;

	private com.algaworks.algamoney.api.model.User user;

	public UserSystem(com.algaworks.algamoney.api.model.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.user = user;
	}

	public com.algaworks.algamoney.api.model.User getUser() {
		return user;
	}

}
