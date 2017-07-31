package com.algaworks.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.CreatedResourceEvent;
import com.algaworks.algamoney.api.exceptionHandler.AlgamoneyExceptionHandler.Error;
import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.LaunchRepository;
import com.algaworks.algamoney.api.repository.filter.LaunchFilter;
import com.algaworks.algamoney.api.service.exception.NoOrInactivePersonException;

@RestController
@RequestMapping("/releases")
public class LaunchResource {
	
	@Autowired
	private LaunchRepository launchRepository;

	@Autowired
	private ApplicationEventPublisher publisher; 
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public Page<Launch> search(LaunchFilter launchFilter, Pageable pageable) {
		return launchRepository.filter(launchFilter, pageable);
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
	

	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long code){
		launchRepository.delete(code);
		
	}
	
	
	
	@ExceptionHandler({NoOrInactivePersonException.class})
	public ResponseEntity<Object> handleNoOrInactivePersonException(NoOrInactivePersonException ex) {
		String userMessage = messageSource.getMessage("person.noOrInactive", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Error> errors = Arrays.asList(new Error(userMessage, developerMessage));
		
		return ResponseEntity.badRequest().body(errors);
	}

}
