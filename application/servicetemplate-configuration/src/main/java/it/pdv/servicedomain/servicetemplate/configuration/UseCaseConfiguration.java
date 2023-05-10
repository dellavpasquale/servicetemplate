package it.pdv.servicedomain.servicetemplate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.service.CreatePurchaseOrderService;

@Configuration
public class UseCaseConfiguration {

	@Bean 
	public CreatePurchaseOrderService getCreatePrescriptionUseCase(PurchaseOrderPersistenceService purchaseOrderPersistenceService, PurchaseOrderNotificationService purchaseOrderNotificationService) {
		return new CreatePurchaseOrderService(purchaseOrderPersistenceService, purchaseOrderNotificationService);
	}

}
