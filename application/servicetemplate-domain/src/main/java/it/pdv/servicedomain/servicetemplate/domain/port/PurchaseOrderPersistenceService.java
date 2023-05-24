package it.pdv.servicedomain.servicetemplate.domain.port;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.error.ServiceUnavailableException;

public interface PurchaseOrderPersistenceService {

	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder) throws ServiceUnavailableException;
	
	public PurchaseOrder getPurchaseOrder(String code) throws ServiceUnavailableException;
	
	public boolean updatePurcahseOrder(PurchaseOrder purchaseOrder) throws ServiceUnavailableException;

}
