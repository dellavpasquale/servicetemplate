package it.pdv.servicedomain.servicetemplate.domain.usecase;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.ServiceUnavailableException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderGetRequest;
import it.pdv.servicedomain.servicetemplate.domain.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CancelPurchaseOrderUseCase {
	
	private final RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private final AccessControlService accessControlService;
	private final PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private final PurchaseOrderNotificationService purchaseOrderNotificationService;

	public PurchaseOrder cancel(PurchaseOrderGetRequest purchaseOrderGetRequest) throws InvalidOperationException, DomainEntityNotFoundException, InvalidDomainEntityException, ForbiddenOperationException, ServiceUnavailableException {
		PurchaseOrder purchaseOrder = retrievePurchaseOrderUseCase.getPurchaseOrder(purchaseOrderGetRequest);
		validateOperation(purchaseOrder);
		purchaseOrder.setStatus(Status.CANCELLED);
		updatePurchaseOrder(purchaseOrder);
		purchaseOrderNotificationService.notifyPurchaseOrderChange(purchaseOrder);
		return purchaseOrder;
	}

	private void validateOperation(PurchaseOrder purchaseOrder) throws InvalidOperationException, ForbiddenOperationException {
		if(!isAccessAllowed(purchaseOrder)) {
			throw new ForbiddenOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "CANCEL");
		}
		if(ValidationUtil.enumNotIn(purchaseOrder.getStatus(), Status.DRAFT, Status.ORDERED)) {
			throw new InvalidOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "CANCEL", "status", "(DRAFT, ORDERED)", purchaseOrder.getStatus());
		}
	}
	
	private boolean isAccessAllowed(PurchaseOrder purchaseOrder) {
		return accessControlService.isLoggedUser(purchaseOrder.getCustomer());
	}
	
	private void updatePurchaseOrder(PurchaseOrder purchaseOrder) throws DomainEntityNotFoundException, ServiceUnavailableException {
		boolean purchaseOrderFound = purchaseOrderPersistenceService.updatePurcahseOrder(purchaseOrder);
		if(!purchaseOrderFound) {
			throw new DomainEntityNotFoundException(PurchaseOrder.class, purchaseOrder.getCode());
		}
	}
	
}
