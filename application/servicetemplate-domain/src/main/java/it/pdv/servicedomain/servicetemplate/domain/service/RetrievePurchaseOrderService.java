package it.pdv.servicedomain.servicetemplate.domain.service;

import it.pdv.servicedomain.servicetemplate.domain.error.AccessDeniedException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderGetRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrievePurchaseOrderService {
	
	private final PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private final AccessControlService accessControlService;

	public PurchaseOrder getPurchaseOrder(PurchaseOrderGetRequest purchaseOrderGetRequest) throws DomainEntityNotFoundException, AccessDeniedException {
		PurchaseOrder purchaseOrder = null;
		String code = null;
		if(isInputValid(purchaseOrderGetRequest)) {
			code = purchaseOrderGetRequest.getCode();
			purchaseOrder = purchaseOrderPersistenceService.getPurchaseOrder(code);
		}
		if(purchaseOrder == null) {
			throw new DomainEntityNotFoundException(PurchaseOrder.class, code);
		} else {
			if(!isAccessAllowed(purchaseOrder)){
				throw new AccessDeniedException(PurchaseOrder.class, code);
			}
			return purchaseOrder;
		}
	}
	
	private boolean isInputValid(PurchaseOrderGetRequest purchaseOrderGetRequest) {
		return purchaseOrderGetRequest != null && purchaseOrderGetRequest.getCode() != null;
	}
	
	private boolean isAccessAllowed(PurchaseOrder purchaseOrder) {
		return accessControlService.hasPermission("viewAll") || accessControlService.isLoggedUser(purchaseOrder.getCustomer());
	}


}
