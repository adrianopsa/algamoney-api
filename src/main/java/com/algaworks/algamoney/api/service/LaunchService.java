package com.algaworks.algamoney.api.service;

import org.springframework.beans.BeanUtils;
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
		validatePerson(launch);		
		return launchRepository.save(launch);
	}

	public Launch update(Long code, Launch launch) {
		
		Launch launchSave = searchLaunchExist(code);
		if(launch.getPerson().equals(launchSave.getPerson())) {
			validatePerson(launch);
		}
		
		BeanUtils.copyProperties(launch, launchSave, "code");
		
		return launchRepository.save(launchSave);
	}
	
	
	private Launch searchLaunchExist(Long code) {
		Launch launchSave = launchRepository.findOne(code);
		if(launchSave == null) {
			throw new IllegalArgumentException();
		}
		return launchSave;
	}

	private void validatePerson(Launch launch) {
		Person person = personRepository.findOne(launch.getPerson().getCode());
		if(person == null || person.isInactive()) {
			throw new NoOrInactivePersonException();
		}
	}
	
	
}
