package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.http.ProblemDetail;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.restapi.exception.OpenAPIException;

public class ProblemMapperImpl implements ProblemMapper {

	private ProblemMapperDelegate delegate;

	public ProblemMapperImpl(ProblemMapperDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public ProblemDetail toProblem(Exception e) {
		Method mapperMethod = resolveMapper(e);

		return invokeMapper(mapperMethod, e);
	}

	private Method resolveMapper(Exception e) {
		Class<?> parameterType = e.getClass();
		String methodName = "toProblem";
		try {
			return delegate.getClass().getMethod(methodName, parameterType);
		} catch (NoSuchMethodException | SecurityException e1) {
			try {
				if (DomainException.class.isInstance(e)) {
					return delegate.getClass().getMethod(methodName, DomainException.class);
				} else {
					return delegate.getClass().getMethod(methodName, Exception.class);
				}
			} catch (NoSuchMethodException | SecurityException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				return null;
			}
		}
	}

	private ProblemDetail invokeMapper(Method mapperMethod, Exception e) {
		if (mapperMethod != null) {
			try {
				return (ProblemDetail) mapperMethod.invoke(delegate, e);
			} catch (IllegalAccessException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		ProblemMapperImpl m = new ProblemMapperImpl(new ProblemMapperDelegate());
		OpenAPIException ex = new OpenAPIException(new ForbiddenOperationException(PurchaseOrder.class, "code", "op"));
		m.toProblem(ex);
	}
}
