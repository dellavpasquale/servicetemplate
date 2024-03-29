package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.entity.DomainEntity;
import it.pdv.servicedomain.servicetemplate.domain.util.LoggingUtil;

public class InvalidOperationException extends DomainException {
	private static final long serialVersionUID = 9064901584751127932L;

	public InvalidOperationException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode, String operation, String attributeName, Object expectedValue, Object currentValue) {
		super(domainEntityClass, domainEntityCode, LoggingUtil.formatKeyValuePairs("operation", operation, attributeName, currentValue, "expected", expectedValue));
	}
	
	public InvalidOperationException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode, String operation, String booleanExpressionViolated) {
		super(domainEntityClass, domainEntityCode, LoggingUtil.formatKeyValuePairs("operation", operation, booleanExpressionViolated, "false"));
	}
}
