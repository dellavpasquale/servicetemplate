package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.restapi.model.ProblemOpenAPI;

public class ProblemMapperDelegate {

	public ProblemOpenAPI toProblem(InvalidDomainEntityException e) {
		return new ProblemOpenAPI();
	}
	
	public ProblemOpenAPI toProblem(DomainEntityAlreadyExistsException e) {
		return new ProblemOpenAPI();
	}
	
	public ProblemOpenAPI toProblem(DomainException e) {
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
}
