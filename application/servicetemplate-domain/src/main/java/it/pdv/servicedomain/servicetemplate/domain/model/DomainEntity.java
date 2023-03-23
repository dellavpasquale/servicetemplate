package it.pdv.servicedomain.servicetemplate.domain.model;

import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;

public abstract class DomainEntity {
	
	public abstract void validate() throws InvalidDomainEntityException;
	
}
