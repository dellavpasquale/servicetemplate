package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.LoggingUtil;
import it.pdv.servicedomain.servicetemplate.domain.ValidationUtil;
import it.pdv.servicedomain.servicetemplate.domain.model.DomainEntity;

public abstract class DomainException extends Exception {

	private static final long serialVersionUID = 5400304538035739028L;

	protected DomainException(Class<? extends DomainEntity> domainEntityClass, String domainEntityCode, String message) {
		super(LoggingUtil.formatKeyValuePairs("entity", getDomainEntityName(domainEntityClass), "code", domainEntityCode) + getMessage(message));
	}

	protected DomainException(Class<? extends DomainEntity> domainEntityClass, String message) {
		super(LoggingUtil.formatKeyValuePairs("entity", getDomainEntityName(domainEntityClass)) + getMessage(message));
	}

	private static String getDomainEntityName(Class<? extends DomainEntity> domainEntityClass) {
		String result = "NA";
		if (domainEntityClass != null) {
			result = domainEntityClass.getSimpleName();
		}
		return result;
	}
	
	private static String getMessage(String message) {
		return ValidationUtil.isNotBlank(message)? ", " + message : "";
	}
	
	
}
