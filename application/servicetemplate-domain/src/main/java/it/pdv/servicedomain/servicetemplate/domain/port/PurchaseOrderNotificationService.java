package it.pdv.servicedomain.servicetemplate.domain.port;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;

public interface PurchaseOrderNotificationService {
	
	public void notifyNewPurchaseOrder(PurchaseOrder purchaseOrder);
	
	public void notifyPurchaseOrderChange(PurchaseOrder purchaseOrder);

}
