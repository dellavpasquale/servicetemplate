package it.pdv.servicedomain.servicetemplate.domain.port;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;

public interface PurchaseOrderPersistenceService {

	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder);
	
	public PurchaseOrder getPurchaseOrder(String code);
	
	public boolean updatePurcahseOrder(PurchaseOrder purchaseOrder);

}
