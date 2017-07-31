package com.algaworks.algamoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Launch.class)
public abstract class Launch_ {

	public static volatile SingularAttribute<Launch, String> note;
	public static volatile SingularAttribute<Launch, Long> code;
	public static volatile SingularAttribute<Launch, Person> person;
	public static volatile SingularAttribute<Launch, LocalDate> dueDate;
	public static volatile SingularAttribute<Launch, String> description;
	public static volatile SingularAttribute<Launch, LocalDate> paymentDate;
	public static volatile SingularAttribute<Launch, Launchtype> type;
	public static volatile SingularAttribute<Launch, Category> category;
	public static volatile SingularAttribute<Launch, BigDecimal> value;

}

