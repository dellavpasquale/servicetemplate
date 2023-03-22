package it.pdv.servicedomain.servicetemplate.domain.purchaseorder.notification;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder;

public interface PurchaseOrderNotificationService {
	
	public void notifyNewPurchaseOrder(PurchaseOrder purchaseOrder);
	
	public void notifyPurchaseOrderChange(PurchaseOrder purchaseOrder);

}
