package com.algaworks.algamoney.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UserSystem  extends User{
	
	
	private static final long serialVersionUID = 1L;

	private com.algaworks.algamoney.api.model.User usuario;

	public UserSystem(com.algaworks.algamoney.api.model.User usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getPassword(), authorities);
		this.usuario = usuario;
	}

	public com.algaworks.algamoney.api.model.User getUser() {
		return usuario;
	}

}
