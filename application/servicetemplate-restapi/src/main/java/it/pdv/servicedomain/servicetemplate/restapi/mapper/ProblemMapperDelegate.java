package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import org.springframework.web.bind.MethodArgumentNotValidException;

import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.restapi.model.ProblemOpenAPI;

public class ProblemMapperDelegate {

	public ProblemOpenAPI toProblem(DomainException e) {
		ProblemOpenAPI problem = new ProblemOpenAPI();
		problem.setType("/problem/generic");
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemOpenAPI toProblem(InvalidDomainEntityException e) {
		ProblemOpenAPI problem = new ProblemOpenAPI();
		problem.setType("/problem/generic");
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemOpenAPI toProblem(DomainEntityAlreadyExistsException e) {
		ProblemOpenAPI problem = new ProblemOpenAPI();
		problem.setType("/problem/generic");
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemOpenAPI toProblem(ForbiddenOperationException e) {
		ProblemOpenAPI problem = new ProblemOpenAPI();
		problem.setType("/problem/generic");
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemOpenAPI toProblem(Exception e) {
		ProblemOpenAPI problem = new ProblemOpenAPI();
		problem.setType("/problem/generic");
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
	
	public ProblemOpenAPI toProblem(MethodArgumentNotValidException e) {
		ProblemOpenAPI problem = new ProblemOpenAPI();
		problem.setType("/problem/generic");
		problem.setTitle("Generic Error");
		problem.setStatus(500);
		return problem;
	}
}
