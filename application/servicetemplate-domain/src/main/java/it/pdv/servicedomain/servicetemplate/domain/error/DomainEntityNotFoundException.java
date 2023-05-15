package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.entity.DomainEntity;

public class DomainEntityNotFoundException extends DomainException {
	private static final long serialVersionUID = 8959846190729399392L;
	
	public DomainEntityNotFoundException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode) {
		super(domainEntityClass, domainEntityCode, "");
	}
}
