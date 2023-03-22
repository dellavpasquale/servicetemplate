package it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.InvalidDomainEntityException;

public abstract class DomainEntity {
	
	public abstract void validate() throws InvalidDomainEntityException;
	
}
