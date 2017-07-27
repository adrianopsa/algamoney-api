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
import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;


@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher; 
	
	@GetMapping
	public List<Category> find() {
		return categoryRepository.findAll();
	}
	
	
	@PostMapping	
	public ResponseEntity<Category> createNewCategory(@Valid @RequestBody Category category, HttpServletResponse response) {
		 Category categorySaved = categoryRepository.save(category);
		
		 publisher.publishEvent(new CreatedResourceEvent(this, response, categorySaved.getCode()));
		 
		 return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
				 
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Category> searchForId(@PathVariable Long code) {
		Category category = categoryRepository.findOne(code);		
		return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
	}
	
	
}
