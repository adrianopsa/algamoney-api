package com.algaworks.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	public Person updatePerson(Long code , Person person){
		Person personSaved = findPersonForCode(code);
		
		BeanUtils.copyProperties(person, personSaved,"code");
		return personRepository.save(personSaved);
		
	}

	public void updatePropertyActive(Long code, Boolean active) {
		Person personSaved = findPersonForCode(code);
		personSaved.setActive(active);
		personRepository.save(personSaved);
	}
	
	private Person findPersonForCode(Long code) {
		Person personSaved = personRepository.findOne(code);
		
		if(personSaved == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return personSaved;
	}
}
