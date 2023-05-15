package it.pdv.servicedomain.servicetemplate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.CreatePurchaseOrderUseCase;

@Configuration
public class UseCaseConfiguration {

	@Bean 
	public CreatePurchaseOrderUseCase getCreatePrescriptionUseCase(PurchaseOrderPersistenceService purchaseOrderPersistenceService, PurchaseOrderNotificationService purchaseOrderNotificationService, AccessControlService accessControlService) {
		return new CreatePurchaseOrderUseCase(purchaseOrderPersistenceService, purchaseOrderNotificationService, accessControlService);
	}

}
