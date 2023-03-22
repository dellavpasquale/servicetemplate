package it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.DomainEntity;

public class DomainEntityAlreadyExistsException extends DomainException {
	private static final long serialVersionUID = 8959846190729399392L;
	
	public DomainEntityAlreadyExistsException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode) {
		super(domainEntityClass, domainEntityCode, "");
	}
}
