package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.entity.DomainEntity;

public class DomainEntityObsoleteException extends DomainException {
	
	private static final long serialVersionUID = -2936152755781411358L;

	public DomainEntityObsoleteException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode) {
		super(domainEntityClass, domainEntityCode, "");
	}
}
