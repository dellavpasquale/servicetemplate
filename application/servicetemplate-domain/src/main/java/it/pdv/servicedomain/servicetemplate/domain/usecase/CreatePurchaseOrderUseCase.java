package it.pdv.servicedomain.servicetemplate.domain.usecase;

import java.time.Instant;
import java.util.UUID;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderRequest;
import it.pdv.servicedomain.servicetemplate.domain.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePurchaseOrderUseCase {
	
	private final PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private final PurchaseOrderNotificationService purchaseOrderNotificationService;
	private final AccessControlService accessControlService;
	
	public PurchaseOrder createPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest) throws InvalidDomainEntityException, DomainEntityAlreadyExistsException, ForbiddenOperationException {
		PurchaseOrder purchaseOrder = buildPurchaseOrder(purchaseOrderRequest);
		purchaseOrder.validate();
		validateOperation(purchaseOrder);
		createPurchaseOrder(purchaseOrder);
		purchaseOrderNotificationService.notifyNewPurchaseOrder(purchaseOrder);
		return purchaseOrder;
	}

	private PurchaseOrder buildPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest) {
		String code = UUID.randomUUID().toString();
		String customer = null;
		if(purchaseOrderRequest != null) {
			if(ValidationUtil.isNotBlank(purchaseOrderRequest.getCode())) {
				code = purchaseOrderRequest.getCode();
			} 
			customer = purchaseOrderRequest.getCustomer();
		}
		return new PurchaseOrder(code, Status.DRAFT, customer, Instant.now());
	}
	
	private void validateOperation(PurchaseOrder purchaseOrder) throws ForbiddenOperationException {
		if(!isAccessAllowed()) {
			throw new ForbiddenOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "CREATE");
		}
	}
	
	private boolean isAccessAllowed() {
		return accessControlService.hasPermission("create");
	}
	
	private void createPurchaseOrder(PurchaseOrder purchaseOrder) throws DomainEntityAlreadyExistsException {
		boolean purchaseOrderCreated = purchaseOrderPersistenceService.createPurchaseOrder(purchaseOrder);
		if(!purchaseOrderCreated) {
			throw new DomainEntityAlreadyExistsException(PurchaseOrder.class, purchaseOrder.getCode());
		}
	}

}
