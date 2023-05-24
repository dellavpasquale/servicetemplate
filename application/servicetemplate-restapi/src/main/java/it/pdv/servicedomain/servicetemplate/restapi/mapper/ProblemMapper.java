package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import org.springframework.http.ProblemDetail;

public interface ProblemMapper {

	ProblemDetail toProblem(Exception ex);

}
