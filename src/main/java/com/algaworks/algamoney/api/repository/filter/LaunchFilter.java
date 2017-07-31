package com.algaworks.algamoney.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LaunchFilter {
	
	private String description;
	
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate dueDateof;
	
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate dueDateUntil;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDueDateof() {
		return dueDateof;
	}
	public void setDueDateof(LocalDate dueDateof) {
		this.dueDateof = dueDateof;
	}
	public LocalDate getDueDateUntil() {
		return dueDateUntil;
	}
	public void setDueDateUntil(LocalDate dueDateUntil) {
		this.dueDateUntil = dueDateUntil;
	}
	
	

}
