package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.entity.DomainEntity;
import it.pdv.servicedomain.servicetemplate.domain.util.LoggingUtil;

public class ForbiddenOperationException extends DomainException {

	private static final long serialVersionUID = -2588390994993360895L;

	public ForbiddenOperationException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode, String operation) {
		super(domainEntityClass, domainEntityCode, LoggingUtil.formatKeyValuePairs("operation", operation));
	}
}
