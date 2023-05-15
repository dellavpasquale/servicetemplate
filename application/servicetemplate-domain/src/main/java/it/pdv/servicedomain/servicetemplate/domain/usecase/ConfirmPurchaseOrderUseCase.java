package it.pdv.servicedomain.servicetemplate.domain.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.AccessDeniedException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderGetRequest;
import it.pdv.servicedomain.servicetemplate.domain.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConfirmPurchaseOrderUseCase {
	private static final int DEFAULT_EXPECTED_DELIVERY_DAYS = 3;
	
	private final RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private final AccessControlService accessControlService;
	private final PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private final PurchaseOrderNotificationService purchaseOrderNotificationService;

	public PurchaseOrder confirm(PurchaseOrderGetRequest purchaseOrderGetRequest) throws InvalidOperationException, DomainEntityNotFoundException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		PurchaseOrder purchaseOrder = retrievePurchaseOrderUseCase.getPurchaseOrder(purchaseOrderGetRequest);
		validateOperation(purchaseOrder);
		purchaseOrder.setStatus(Status.ORDERED);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(calculateExpectedDeliveryDate(purchaseOrder));
		updatePurchaseOrder(purchaseOrder);
		purchaseOrderNotificationService.notifyPurchaseOrderChange(purchaseOrder);
		return purchaseOrder;
	}

	private void validateOperation(PurchaseOrder purchaseOrder) throws InvalidOperationException, ForbiddenOperationException {
		if(!isAccessAllowed(purchaseOrder)) {
			throw new ForbiddenOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "CONFIRM");
		}
		if(!Status.DRAFT.equals(purchaseOrder.getStatus())) {
			throw new InvalidOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "CONFIRM", "status", Status.DRAFT, purchaseOrder.getStatus());
		}
		if(ValidationUtil.isBlank(purchaseOrder.getProduct())) {
			throw new InvalidOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "CONFIRM", "product is not empty");
		}
	}
	
	private boolean isAccessAllowed(PurchaseOrder purchaseOrder) {
		return accessControlService.isLoggedUser(purchaseOrder.getCustomer());
	}
	
	private Instant calculateExpectedDeliveryDate(PurchaseOrder purchaseOrder) {
		int days = DEFAULT_EXPECTED_DELIVERY_DAYS;
		if(ChronoUnit.HOURS.between(purchaseOrder.getOrderedAt(),purchaseOrder.getCreatedAt()) < 24) {
			days = 1;
		}
		return Instant.now().plus(days, ChronoUnit.DAYS);
	}
	
	private void updatePurchaseOrder(PurchaseOrder purchaseOrder) throws DomainEntityNotFoundException {
		boolean purchaseOrderFound = purchaseOrderPersistenceService.updatePurcahseOrder(purchaseOrder);
		if(!purchaseOrderFound) {
			throw new DomainEntityNotFoundException(PurchaseOrder.class, purchaseOrder.getCode());
		}
	}
	
}
