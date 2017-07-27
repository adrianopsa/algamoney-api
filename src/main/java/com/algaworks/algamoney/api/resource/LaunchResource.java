package com.algaworks.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.CreatedResourceEvent;
import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.LaunchRepository;

@RestController
@RequestMapping("/releases")
public class LaunchResource {
	
	@Autowired
	private LaunchRepository launchRepository;

	@Autowired
	private ApplicationEventPublisher publisher; 
	
	@GetMapping
	public List<Launch> find() {
		return launchRepository.findAll();
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Launch> searchForId(@PathVariable Long code) {
		Launch launch = launchRepository.findOne(code);		
		return launch != null ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build(); 
	}
	
	
	@PostMapping
	public ResponseEntity<Launch> createNewLaunch(@Valid @RequestBody Launch launch, HttpServletResponse response) {
		
		Launch launchSaved= launchRepository.save(launch);		
		publisher.publishEvent(new CreatedResourceEvent(this, response, launchSaved.getCode()));		 
		return ResponseEntity.status(HttpStatus.CREATED).body(launchSaved);
				 
	}

}
