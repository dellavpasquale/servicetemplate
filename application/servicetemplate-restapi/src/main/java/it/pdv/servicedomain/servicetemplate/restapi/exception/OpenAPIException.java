package it.pdv.servicedomain.servicetemplate.restapi.exception;

import it.pdv.servicedomain.servicetemplate.domain.error.DomainException;

public class OpenAPIException extends RuntimeException {

	private static final long serialVersionUID = 594838126734490357L;

	public OpenAPIException(DomainException e) {
		super(e);
	}

	public OpenAPIException(Exception e) {
		super(e);
	}

	public DomainException getDomainException() {
		if (getCause() instanceof DomainException) {
			return (DomainException) getCause();
		} else {
			return null;
		}
	}

}
