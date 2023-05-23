package it.pdv.servicedomain.servicetemplate.domain.error;

import it.pdv.servicedomain.servicetemplate.domain.util.LoggingUtil;

public class ServiceUnavailableException extends Exception {
	
	private static final long serialVersionUID = -2557127970720723397L;

	protected ServiceUnavailableException(String servicename, String message) {
		super(LoggingUtil.formatKeyValuePairs("service", servicename, "details", message));
	}
}
