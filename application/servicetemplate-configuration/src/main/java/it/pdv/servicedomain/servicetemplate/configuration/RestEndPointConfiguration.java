package it.pdv.servicedomain.servicetemplate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.pdv.servicedomain.servicetemplate.domain.service.CreatePurchaseOrderService;
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
	public PurchaseorderApiDelegate getPurchaseorderApiDelegate(CreatePurchaseOrderService createPurchaseOrderService) {
		return new PurchaseOrderRestApiImpl(createPurchaseOrderService);
	}
	
}
