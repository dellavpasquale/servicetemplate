package it.pdv.servicedomain.servicetemplate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.persistence.repository.PurchaseOrderRepository;
import it.pdv.servicedomain.servicetemplate.persistence.service.PurchaseOrderPersistenceServiceImpl;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public PurchaseOrderPersistenceService getPrescriptionDataProvider(PurchaseOrderRepository purchaseOrderRepository) {
		return new PurchaseOrderPersistenceServiceImpl(purchaseOrderRepository);
    }

}
