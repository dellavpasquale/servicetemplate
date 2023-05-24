package it.pdv.servicedomain.servicetemplate.restapi.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityLockedException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityObsoleteException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.ServiceUnavailableException;

@ControllerAdvice
public class OpenAPIDomainExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DomainEntityAlreadyExistsException.class)
	public ErrorResponse handleDomainEntityAlreadyExistsException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.CONFLICT, e.getMessage())
                .title("Conflict")
                .build();
	}
	
	@ExceptionHandler(DomainEntityLockedException.class)
	public ErrorResponse handleDomainEntityLockedException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.LOCKED, e.getMessage())
                .title("Locked")
                .build();
	}
	
	@ExceptionHandler(DomainEntityNotFoundException.class)
	public ErrorResponse handleDomainEntityNotFoundException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage())
                .title("Not Found")
                .build();
	}
	
	@ExceptionHandler(DomainEntityObsoleteException.class)
	public ErrorResponse handleDomainEntityObsoleteException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.PRECONDITION_FAILED, e.getMessage())
                .title("Precondition Failed")
                .build();
	}
	
	@ExceptionHandler(ForbiddenOperationException.class)
	public ErrorResponse handleForbiddenOperationException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.FORBIDDEN, e.getMessage())
                .title("Forbidden")
                .build();
	}
	
	@ExceptionHandler(InvalidDomainEntityException.class)
	public ErrorResponse handleInvalidDomainEntityException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, "Invalid request content.")
                .title("Invalid Request")
                .build();
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	public ErrorResponse handleInvalidOperationException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .title("Invalid Operation")
                .build();
	}
	
	@ExceptionHandler(ServiceUnavailableException.class)
	public ErrorResponse handleServiceUnavailableException(DomainException e, WebRequest request) throws Exception {
		return ErrorResponse.builder(e, HttpStatus.SERVICE_UNAVAILABLE, e.getMessage())
                .title("Service Unavailable")
                .build();
	}
	

}
