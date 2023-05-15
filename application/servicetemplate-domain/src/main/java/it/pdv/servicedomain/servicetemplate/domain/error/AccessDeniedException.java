package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.entity.DomainEntity;

public class AccessDeniedException extends DomainException {

	private static final long serialVersionUID = -2588390994993360895L;

	public AccessDeniedException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode) {
		super(domainEntityClass, domainEntityCode, "");
	}
}
