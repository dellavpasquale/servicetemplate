package it.pdv.servicedomain.servicetemplate.domain.usecase;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.mapper.PurchaseOrderMapper;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderEditRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdatePurchaseOrderUseCase {
	
	private final RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private final AccessControlService accessControlService;
	private final PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private final PurchaseOrderNotificationService purchaseOrderNotificationService;
	
	public PurchaseOrder update(PurchaseOrderEditRequest purchaseOrderEditRequest) throws InvalidOperationException, DomainEntityNotFoundException, InvalidDomainEntityException, ForbiddenOperationException {
		PurchaseOrder purchaseOrder = retrievePurchaseOrderUseCase.getPurchaseOrder(purchaseOrderEditRequest);
		purchaseOrder.validate();
		validateOperation(purchaseOrder);
		PurchaseOrderMapper.INSTANCE.updatePurchaseOrderFromPurchaseOrderEditRequest(purchaseOrderEditRequest, purchaseOrder);
		purchaseOrder.validate();
		updatePurchaseOrder(purchaseOrder);
		purchaseOrderNotificationService.notifyPurchaseOrderChange(purchaseOrder);
		return purchaseOrder;
	}

	private void validateOperation(PurchaseOrder purchaseOrder) throws InvalidOperationException, ForbiddenOperationException {
		if(!isAccessAllowed(purchaseOrder)) {
			throw new ForbiddenOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "UPDATE");
		}
		if(!Status.DRAFT.equals(purchaseOrder.getStatus())) {
			throw new InvalidOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "UPDATE", "status", Status.DRAFT, purchaseOrder.getStatus());
		}
	}
	
	private boolean isAccessAllowed(PurchaseOrder purchaseOrder) {
		return accessControlService.isLoggedUser(purchaseOrder.getCustomer());
	}
	
	private void updatePurchaseOrder(PurchaseOrder purchaseOrder) throws DomainEntityNotFoundException {
		boolean purchaseOrderFound = purchaseOrderPersistenceService.updatePurcahseOrder(purchaseOrder);
		if(!purchaseOrderFound) {
			throw new DomainEntityNotFoundException(PurchaseOrder.class, purchaseOrder.getCode());
		}
	}
	
}
