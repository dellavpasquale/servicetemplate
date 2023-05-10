package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import it.pdv.servicedomain.servicetemplate.restapi.exception.OpenAPIException;
import it.pdv.servicedomain.servicetemplate.restapi.model.ProblemOpenAPI;

public interface ProblemMapper {

	ProblemOpenAPI toProblem(OpenAPIException ex);

}
