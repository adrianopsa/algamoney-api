package com.algaworks.algamoney.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile SingularAttribute<Person, Long> code;
	public static volatile SingularAttribute<Person, Address> address;
	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, Boolean> active;

}

