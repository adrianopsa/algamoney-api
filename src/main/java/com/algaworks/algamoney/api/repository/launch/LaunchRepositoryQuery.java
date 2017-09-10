package com.algaworks.algamoney.api.repository.launch;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.filter.LaunchFilter;
import com.algaworks.algamoney.api.repository.projection.SummaryRelease;

public interface LaunchRepositoryQuery {

	public Page<Launch> filter(LaunchFilter launchFilter, Pageable pageable);
	
	public Page<SummaryRelease> summarize(LaunchFilter launchFilter, Pageable pageable);
	
}
