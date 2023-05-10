package it.pdv.servicedomain.servicetemplate.domain.service;

import java.time.Instant;
import java.util.UUID;

import it.pdv.servicedomain.servicetemplate.domain.ValidationUtil;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderRequest;

public class CreatePurchaseOrderService {
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	
	public CreatePurchaseOrderService(PurchaseOrderPersistenceService purchaseOrderPersistenceService, PurchaseOrderNotificationService purchaseOrderNotificationService) {
		this.purchaseOrderPersistenceService = purchaseOrderPersistenceService;
		this.purchaseOrderNotificationService = purchaseOrderNotificationService;
	}
	
	public PurchaseOrder createPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest) throws InvalidDomainEntityException, DomainEntityAlreadyExistsException {
		PurchaseOrder purchaseOrder = buildPurchaseOrder(purchaseOrderRequest);
		purchaseOrder.validate();
		boolean purchaseOrderCreated = purchaseOrderPersistenceService.createPurchaseOrder(purchaseOrder);
		if(!purchaseOrderCreated) {
			throw new DomainEntityAlreadyExistsException(PurchaseOrder.class, purchaseOrder.getCode());
		}
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

}
