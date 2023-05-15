package it.pdv.servicedomain.servicetemplate.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import it.pdv.servicedomain.servicetemplate.domain.usecase.RetrievePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.UpdateDeliveryUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.DeliveryEditRequest;

class UpdateDeliveryTest {

	private RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private AccessControlService accessControlService;
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private UpdateDeliveryUseCase updateDeliveryUseCase;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		retrievePurchaseOrderUseCase = mock(RetrievePurchaseOrderUseCase.class);
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		accessControlService = mock(AccessControlService.class);
		updateDeliveryUseCase = new UpdateDeliveryUseCase(retrievePurchaseOrderUseCase, accessControlService, purchaseOrderPersistenceService, purchaseOrderNotificationService);
		
		purchaseOrder = new PurchaseOrder("code", Status.ORDERED, "customer", Instant.now());
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());
		purchaseOrder.setProduct("product");
	}

	@Test
	void testUpdateDelivery() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.hasPermission(any())).thenReturn(true);
		
		DeliveryEditRequest deliveryEditRequest = new DeliveryEditRequest();
		deliveryEditRequest.setCode("code");
		deliveryEditRequest.setStatus(Status.IN_PROGRESS);
		purchaseOrder = updateDeliveryUseCase.updateDelivery(deliveryEditRequest);
		assertEquals(Status.IN_PROGRESS, purchaseOrder.getStatus());
	}
	
	@Test
	void testUpdateDeliveryInvalidOperation() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.hasPermission(any())).thenReturn(true);
		purchaseOrder.setStatus(Status.CANCELLED);
		
		DeliveryEditRequest deliveryEditRequest = new DeliveryEditRequest();
		deliveryEditRequest.setCode("code");
		deliveryEditRequest.setStatus(Status.IN_PROGRESS);
		
		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			updateDeliveryUseCase.updateDelivery(deliveryEditRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <UPDATE DELIVERY>, 'status': <CANCELLED>, 'expected': <not in (draft, cancelled)>",
				exception.getMessage());
	}
	
	@Test
	void testUpdateDeliveryForbiddenOperation() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.hasPermission(any())).thenReturn(false);
		
		DeliveryEditRequest deliveryEditRequest = new DeliveryEditRequest();
		deliveryEditRequest.setCode("code");
		deliveryEditRequest.setStatus(Status.IN_PROGRESS);
		
		Exception exception = Assertions.assertThrows(ForbiddenOperationException.class, () -> {
			updateDeliveryUseCase.updateDelivery(deliveryEditRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <UPDATE DELIVERY>",
				exception.getMessage());
	}

	

}
