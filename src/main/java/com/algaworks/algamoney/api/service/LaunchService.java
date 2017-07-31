package com.algaworks.algamoney.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.LaunchRepository;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.exception.NoOrInactivePersonException;



@Service
public class LaunchService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private LaunchRepository launchRepository;
	
	public Launch save(Launch launch) {
		Person person = personRepository.findOne(launch.getPerson().getCode());
		if(person == null || person.isInactive()) {
			throw new NoOrInactivePersonException();
		}
		
		return launchRepository.save(launch);
	}
}
