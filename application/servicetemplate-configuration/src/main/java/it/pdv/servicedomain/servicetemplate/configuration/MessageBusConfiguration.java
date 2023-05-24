package it.pdv.servicedomain.servicetemplate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;

@Configuration
public class MessageBusConfiguration {

	@Bean 
	public PurchaseOrderNotificationService getPurchaseOrderNotificationService() {
		return new PurchaseOrderNotificationService() {
			
			@Override
			public void notifyPurchaseOrderChange(PurchaseOrder purchaseOrder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void notifyNewPurchaseOrder(PurchaseOrder purchaseOrder) {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
