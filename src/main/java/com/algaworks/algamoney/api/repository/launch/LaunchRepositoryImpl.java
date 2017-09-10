package com.algaworks.algamoney.api.repository.launch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.algaworks.algamoney.api.model.Category_;
import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.model.Launch_;
import com.algaworks.algamoney.api.model.Person_;
import com.algaworks.algamoney.api.repository.filter.LaunchFilter;
import com.algaworks.algamoney.api.repository.projection.SummaryRelease;

public class LaunchRepositoryImpl implements LaunchRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Launch> filter(LaunchFilter launchFilter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Launch> criteria = builder.createQuery(Launch.class);
		Root<Launch> root = criteria.from(Launch.class);

		// criar as restrições
		Predicate[] predicates = createRestrictions(launchFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<Launch> query = manager.createQuery(criteria);
		
		addRestrictionsOnPagination(query,pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(launchFilter)) ;
	}

	@Override
	public Page<SummaryRelease> summarize(LaunchFilter launchFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<SummaryRelease> criteria = builder.createQuery(SummaryRelease.class);
		Root<Launch> root = criteria.from(Launch.class);
		
		criteria.select(builder.construct(SummaryRelease.class, 
				root.get(Launch_.code),
				root.get(Launch_.description),
				root.get(Launch_.dueDate),
				root.get(Launch_.paymentDate),
				root.get(Launch_.value),
				root.get(Launch_.type),
				root.get(Launch_.category).get(Category_.name),
				root.get(Launch_.person).get(Person_.name)));
		
		// criar as restrições
		Predicate[] predicates = createRestrictions(launchFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<SummaryRelease> query = manager.createQuery(criteria);
		
		addRestrictionsOnPagination(query,pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(launchFilter)) ;
			
		
	}

	private Predicate[] createRestrictions(LaunchFilter launchFilter, CriteriaBuilder builder, Root<Launch> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(launchFilter.getDescription())) {
			predicates.add(builder.like(builder.lower(root.get(Launch_.description)), "%" + launchFilter.getDescription().toLowerCase()+"%"));	
		}
		if (launchFilter.getDueDateof() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Launch_.dueDate), launchFilter.getDueDateof()));
		}
		if(launchFilter.getDueDateUntil() !=null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Launch_.dueDate), launchFilter.getDueDateUntil()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addRestrictionsOnPagination(TypedQuery<?> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRegistryPerPage= pageable.getPageSize();
		int firstPageRegistration = currentPage * totalRegistryPerPage;
		
		query.setFirstResult(firstPageRegistration);
		query.setMaxResults(totalRegistryPerPage);
		
	}
	private Long total(LaunchFilter launchFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Launch> root = criteria.from(Launch.class);
		
		Predicate[] predicates = createRestrictions(launchFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}


}
