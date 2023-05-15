package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.entity.DomainEntity;
import it.pdv.servicedomain.servicetemplate.domain.util.LoggingUtil;

public class InvalidDomainEntityException extends DomainException {
	private static final long serialVersionUID = -598770441868840904L;

	public InvalidDomainEntityException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode, String attributeName, Object expectedValue, Object currentValue) {
		super(domainEntityClass, domainEntityCode, LoggingUtil.formatKeyValuePairs(attributeName, currentValue, "expected", expectedValue));
	}
	
	public InvalidDomainEntityException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode, String booleanExpressionViolated) {
		super(domainEntityClass, domainEntityCode, LoggingUtil.formatKeyValuePairs(booleanExpressionViolated, "false"));
	}
}
