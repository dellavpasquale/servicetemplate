package it.pdv.servicedomain.servicetemplate.domain.purchaseorder.persistence;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder;

public interface PurchaseOrderPersistenceService {

	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder);
	
	public PurchaseOrder getPurchaseOrder(String code);
	
	public boolean updatePurcahseOrder(PurchaseOrder purchaseOrder);

}
