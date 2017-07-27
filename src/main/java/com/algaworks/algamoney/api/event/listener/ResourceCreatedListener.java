package com.algaworks.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.algaworks.algamoney.api.event.CreatedResourceEvent;

public class ResourceCreatedListener implements ApplicationListener<CreatedResourceEvent>{

	@Override
	public void onApplicationEvent(CreatedResourceEvent createdResourceEvent) {
		HttpServletResponse response = createdResourceEvent.getResponse();
		
		Long code = createdResourceEvent.getCode();		
		 addHeaderLocation(response, code);
		
		
	}

	private void addHeaderLocation(HttpServletResponse response, Long code) {
		URI uri =ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
				 .buildAndExpand(code).toUri();
		 response.setHeader("Location",uri.toASCIIString());
	}
	
}
