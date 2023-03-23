package it.pdv.servicedomain.servicetemplate.domain.service;

import java.time.Instant;
import java.util.UUID;

import it.pdv.servicedomain.servicetemplate.domain.ValidationUtil;
import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;

public class CreatePurchaseOrderService {
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	
	public CreatePurchaseOrderService(PurchaseOrderPersistenceService purchaseOrderPersistenceService, PurchaseOrderNotificationService purchaseOrderNotificationService) {
		this.purchaseOrderPersistenceService = purchaseOrderPersistenceService;
		this.purchaseOrderNotificationService = purchaseOrderNotificationService;
	}
	
	public PurchaseOrder createPurchaseOrder(String customer, String code) throws InvalidDomainEntityException, DomainEntityAlreadyExistsException {
		PurchaseOrder purchaseOrder = buildPurchaseOrder(customer, code);
		purchaseOrder.validate();
		boolean purchaseOrderCreated = purchaseOrderPersistenceService.createPurchaseOrder(purchaseOrder);
		if(!purchaseOrderCreated) {
			throw new DomainEntityAlreadyExistsException(PurchaseOrder.class, purchaseOrder.getCode());
		}
		purchaseOrderNotificationService.notifyNewPurchaseOrder(purchaseOrder);
		return purchaseOrder;
	}

	private PurchaseOrder buildPurchaseOrder(String customer, String code) {
		String effectiveCode = ValidationUtil.isNotBlank(code)? code: UUID.randomUUID().toString();
		return new PurchaseOrder(effectiveCode, Status.DRAFT, customer, Instant.now());
	}

}
