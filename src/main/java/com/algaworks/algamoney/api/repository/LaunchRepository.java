package com.algaworks.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.launch.LaunchRepositoryQuery;

public interface LaunchRepository  extends JpaRepository<Launch, Long>,LaunchRepositoryQuery{

}
