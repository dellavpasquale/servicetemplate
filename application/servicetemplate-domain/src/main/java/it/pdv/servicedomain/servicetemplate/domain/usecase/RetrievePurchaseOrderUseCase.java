package it.pdv.servicedomain.servicetemplate.domain.usecase;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.error.AccessDeniedException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderGetRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrievePurchaseOrderUseCase {

	private final PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private final AccessControlService accessControlService;

	public PurchaseOrder getPurchaseOrder(PurchaseOrderGetRequest purchaseOrderGetRequest)
			throws DomainEntityNotFoundException, AccessDeniedException {
		String code = getOurchaseOrderCode(purchaseOrderGetRequest);
		PurchaseOrder purchaseOrder = purchaseOrderPersistenceService.getPurchaseOrder(code);
		if (purchaseOrder == null) {
			throw new DomainEntityNotFoundException(PurchaseOrder.class, code);
		} else if (!isAccessAllowed(purchaseOrder)) {
			throw new AccessDeniedException(PurchaseOrder.class, code);
		}
		return purchaseOrder;
	}

	private String getOurchaseOrderCode(PurchaseOrderGetRequest purchaseOrderGetRequest) {
		return purchaseOrderGetRequest != null? purchaseOrderGetRequest.getCode() : null;
	}

	private boolean isAccessAllowed(PurchaseOrder purchaseOrder) {
		return accessControlService.hasPermission("viewAll")
				|| accessControlService.isLoggedUser(purchaseOrder.getCustomer());
	}

}
