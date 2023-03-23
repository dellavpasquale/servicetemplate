package it.pdv.servicedomain.servicetemplate.domain.adapter;

import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;

public interface PurchaseOrderPersistenceService {

	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder);
	
	public PurchaseOrder getPurchaseOrder(String code);
	
	public boolean updatePurcahseOrder(PurchaseOrder purchaseOrder);

}
