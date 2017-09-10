package com.algaworks.algamoney.api.resource;


import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algamoney.api.event.CreatedResourceEvent;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.PersonService;

@RestController
@RequestMapping("/people")
public class PersonResource {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher; 
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public List<Person> findAll() {
		return personRepository.findAll();
	}
	
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Person> createNewPerson(@Valid @RequestBody Person person, HttpServletResponse response) {
		 Person personSaved = personRepository.save(person);		
		 publisher.publishEvent(new CreatedResourceEvent(this,response,personSaved.getCode()));
		 return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
				 
	}
	
	@GetMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Person> searchForId(@PathVariable Long code) {
		Person person = personRepository.findOne(code);		
		return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long code){
		personRepository.delete(code);
		
	}
	
	@PutMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Person> updatePerson(@PathVariable Long code, @Valid @RequestBody Person person) {
		Person personSaved = personService.updatePerson(code, person);
		return ResponseEntity.ok(personSaved);
	}
	
	@PutMapping("/{code}/active")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePropertyActive(@PathVariable Long code , @RequestBody Boolean active) {
		personService.updatePropertyActive(code,active);
	}
	
}
