package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.entity.DomainEntity;

public class DomainEntityLockedException extends DomainException {
	
	private static final long serialVersionUID = 3296024529252997777L;

	public DomainEntityLockedException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode) {
		super(domainEntityClass, domainEntityCode, "");
	}
}
