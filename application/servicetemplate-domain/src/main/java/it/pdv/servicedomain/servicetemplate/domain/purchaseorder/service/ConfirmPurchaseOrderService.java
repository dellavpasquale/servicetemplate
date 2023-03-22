package it.pdv.servicedomain.servicetemplate.domain.purchaseorder.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.notification.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.persistence.PurchaseOrderPersistenceService;

public class ConfirmPurchaseOrderService {
	private static final int DEFAULT_EXPECTED_DELIVERY_DAYS = 3;
	
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	
	public ConfirmPurchaseOrderService(PurchaseOrderPersistenceService purchaseOrderPersistenceService, PurchaseOrderNotificationService purchaseOrderNotificationService) {
		this.purchaseOrderPersistenceService = purchaseOrderPersistenceService;
		this.purchaseOrderNotificationService = purchaseOrderNotificationService;
	}
	
	public PurchaseOrder confirm(PurchaseOrder purchaseOrder) throws InvalidOperationException, DomainEntityNotFoundException, InvalidDomainEntityException {
		validatePurchaseOrder(purchaseOrder);
		purchaseOrder.setStatus(Status.ORDERED);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(calculateExpectedDeliveryDate(purchaseOrder));
		updatePurchaseOrder(purchaseOrder);
		purchaseOrderNotificationService.notifyPurchaseOrderChange(purchaseOrder);
		return purchaseOrder;
	}

	private void validatePurchaseOrder(PurchaseOrder purchaseOrder) throws InvalidOperationException, InvalidDomainEntityException {
		purchaseOrder.validate();
		if(!Status.DRAFT.equals(purchaseOrder.getStatus())) {
			throw new InvalidOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "CONFIRM", "status", Status.DRAFT, purchaseOrder.getStatus());
		}
	}
	
	public Instant calculateExpectedDeliveryDate(PurchaseOrder purchaseOrder) {
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
