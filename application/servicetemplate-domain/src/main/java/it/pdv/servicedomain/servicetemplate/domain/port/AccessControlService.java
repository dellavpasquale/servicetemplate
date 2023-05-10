package it.pdv.servicedomain.servicetemplate.domain.port;

public interface AccessControlService {

	public boolean hasPermission(String permissionName);

	public boolean isLoggedUser(String userName);

}
