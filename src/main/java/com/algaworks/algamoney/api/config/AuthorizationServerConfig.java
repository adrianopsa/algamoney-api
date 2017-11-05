package com.algaworks.algamoney.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.algaworks.algamoney.api.config.token.CustomTokenEnhancer;

@Profile("oauth-security")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final static int SECONDS = 1800;//1800
	private final static int SECONDS_REFRESH_TOKEN = 3600;//3600
	private final static int DAYS_IN_HOURS = 1;
	
	@Autowired
	private AuthenticationManager authenticationManager;	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory()
			.withClient("angular")
			.secret("@ngul@r")
			.scopes("read","write")
			.authorizedGrantTypes("password","refresh_token")
			.accessTokenValiditySeconds(SECONDS)
			.refreshTokenValiditySeconds(SECONDS_REFRESH_TOKEN * DAYS_IN_HOURS)
		.and()
			.withClient("mobile")
			.secret("m0b1l3")
			.scopes("read")
			.authorizedGrantTypes("password","refresh_token")
			.accessTokenValiditySeconds(SECONDS)
			.refreshTokenValiditySeconds(SECONDS_REFRESH_TOKEN * DAYS_IN_HOURS);
		
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
			TokenEnhancerChain tokenEnchancerChain = new TokenEnhancerChain();
			tokenEnchancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),accessTokenConverter()));
			
			endpoints
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnchancerChain)
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);	
		
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore(){
		return new JwtTokenStore(accessTokenConverter());
	}
	@Bean
	public TokenEnhancer tokenEnhancer() {
	    return new CustomTokenEnhancer();
	}
}
