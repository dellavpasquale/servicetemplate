package it.pdv.servicedomain.servicetemplate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.CreatePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.restapi.PurchaseorderApiController;
import it.pdv.servicedomain.servicetemplate.restapi.PurchaseorderApiDelegate;
import it.pdv.servicedomain.servicetemplate.restapi.service.PurchaseOrderRestApiImpl;

@Configuration
public class RestEndPointConfiguration {

	@Bean
	public PurchaseorderApiController getPurchaseorderApiController(PurchaseorderApiDelegate purchaseorderApiDelegate) {
		return new PurchaseorderApiController(purchaseorderApiDelegate);
	}
	
	@Bean
	public PurchaseorderApiDelegate getPurchaseorderApiDelegate(CreatePurchaseOrderUseCase createPurchaseOrderUseCase) {
		return new PurchaseOrderRestApiImpl(createPurchaseOrderUseCase);
	}
	
	@Bean
	public AccessControlService getAccessControlService() {
		return new AccessControlService() {
			
			@Override
			public boolean isLoggedUser(String userName) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean hasPermission(String permissionName) {
				// TODO Auto-generated method stub
				return true;
			}
		};
	}
	
}
