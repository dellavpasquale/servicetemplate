package it.pdv.servicedomain.servicetemplate.domain.port;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.error.ServiceUnavailableException;

public interface PurchaseOrderNotificationService {
	
	public void notifyNewPurchaseOrder(PurchaseOrder purchaseOrder) throws ServiceUnavailableException;
	
	public void notifyPurchaseOrderChange(PurchaseOrder purchaseOrder) throws ServiceUnavailableException;

}
