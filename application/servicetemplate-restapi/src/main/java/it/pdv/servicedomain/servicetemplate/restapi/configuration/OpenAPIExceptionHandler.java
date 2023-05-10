package it.pdv.servicedomain.servicetemplate.restapi.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.pdv.servicedomain.servicetemplate.restapi.exception.OpenAPIException;
import it.pdv.servicedomain.servicetemplate.restapi.mapper.ProblemMapper;
import it.pdv.servicedomain.servicetemplate.restapi.mapper.ProblemMapperDelegate;
import it.pdv.servicedomain.servicetemplate.restapi.mapper.ProblemMapperImpl;
import it.pdv.servicedomain.servicetemplate.restapi.model.ProblemOpenAPI;

@ControllerAdvice
public class OpenAPIExceptionHandler extends ResponseEntityExceptionHandler {

	private ProblemMapper mapper = new ProblemMapperImpl(new ProblemMapperDelegate()); 
	
	@ExceptionHandler (OpenAPIException.class)
    protected ResponseEntity<Object> handleOpenAPIException(OpenAPIException ex) {
		ProblemOpenAPI problem = mapper.toProblem(ex);
		return new ResponseEntity<Object>(problem, HttpStatus.valueOf(problem.getStatus()));
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		OpenAPIException ex = new OpenAPIException(e);
		ProblemOpenAPI problem = mapper.toProblem(ex);
		return super.handleExceptionInternal(ex, problem, headers, statusCode, request);
	}

}
