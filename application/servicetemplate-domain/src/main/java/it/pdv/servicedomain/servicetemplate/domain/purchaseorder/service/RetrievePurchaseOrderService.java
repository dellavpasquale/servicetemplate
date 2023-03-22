package it.pdv.servicedomain.servicetemplate.domain.purchaseorder.service;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.persistence.PurchaseOrderPersistenceService;

public class RetrievePurchaseOrderService {
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;

	public RetrievePurchaseOrderService(PurchaseOrderPersistenceService purchaseOrderPersistenceService) {
		this.purchaseOrderPersistenceService = purchaseOrderPersistenceService;
	}

	public PurchaseOrder getPurchaseOrder(String code) throws DomainEntityNotFoundException {
		PurchaseOrder purchaseOrder = null;
		if(isInputValid(code)) {
			purchaseOrder = purchaseOrderPersistenceService.getPurchaseOrder(code);
		}
		if(purchaseOrder == null) {
			throw new DomainEntityNotFoundException(PurchaseOrder.class, code);
		} else {
			return purchaseOrder;
		}
	}

	private boolean isInputValid(String code) {
		return code != null;
	}

}
