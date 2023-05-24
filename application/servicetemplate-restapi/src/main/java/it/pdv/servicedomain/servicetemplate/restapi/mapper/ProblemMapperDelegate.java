package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;

import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;

public class ProblemMapperDelegate {

	public ProblemDetail toProblem(DomainException e) {
		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problem.setType(URI.create("/problem/generic"));
		problem.setTitle("Generic Error");
		return problem;
	}
	
	public ProblemDetail toProblem(InvalidDomainEntityException e) {
		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problem.setType(URI.create("/problem/generic"));
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemDetail toProblem(DomainEntityAlreadyExistsException e) {
		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problem.setType(URI.create("/problem/generic"));
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemDetail toProblem(ForbiddenOperationException e) {
		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problem.setType(URI.create("/problem/generic"));
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemDetail toProblem(Exception e) {
		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problem.setType(URI.create("/problem/generic"));
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemDetail toProblem(MethodArgumentNotValidException e) {
		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problem.setType(URI.create("/problem/generic"));
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
}
