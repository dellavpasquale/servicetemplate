package it.pdv.servicedomain.servicetemplate.restapi.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class OpenAPIExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request content.");
		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(Exception.class)
    ErrorResponse handleException(Exception e) {
        return ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
                .title("Internal Server Error")
                .build();
    }

}
