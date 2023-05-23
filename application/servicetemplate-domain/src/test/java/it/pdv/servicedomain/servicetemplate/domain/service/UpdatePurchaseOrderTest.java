package it.pdv.servicedomain.servicetemplate.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.RetrievePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.UpdatePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderEditRequest;

class UpdatePurchaseOrderTest {

	private RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private AccessControlService accessControlService;
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		retrievePurchaseOrderUseCase = mock(RetrievePurchaseOrderUseCase.class);
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		accessControlService = mock(AccessControlService.class);
		updatePurchaseOrderUseCase = new UpdatePurchaseOrderUseCase(retrievePurchaseOrderUseCase, accessControlService, purchaseOrderPersistenceService, purchaseOrderNotificationService);
		
		purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
	}

	@Test
	void testOrder() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, ForbiddenOperationException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);

		PurchaseOrderEditRequest purchaseOrderEditRequest = new PurchaseOrderEditRequest();
		purchaseOrderEditRequest.setCode("code");
		purchaseOrderEditRequest.setProduct("product");
		purchaseOrderEditRequest.setAmount(BigDecimal.TEN);
		purchaseOrder = updatePurchaseOrderUseCase.update(purchaseOrderEditRequest);
		assertEquals(BigDecimal.TEN, purchaseOrder.getAmount());
		assertEquals("product", purchaseOrder.getProduct());
	}
	
	@Test
	void testOrderPurchaseOrderAlreadyOrdered() throws DomainEntityNotFoundException, ForbiddenOperationException {
		purchaseOrder.setProduct("product");
		purchaseOrder.setStatus(Status.ORDERED);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);

		PurchaseOrderEditRequest purchaseOrderEditRequest = new PurchaseOrderEditRequest();
		purchaseOrderEditRequest.setCode("code");
		purchaseOrderEditRequest.setProduct("product");
		purchaseOrderEditRequest.setAmount(BigDecimal.TEN);

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			purchaseOrder = updatePurchaseOrderUseCase.update(purchaseOrderEditRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <UPDATE>, 'status': <ORDERED>, 'expected': <DRAFT>",
				exception.getMessage());
	}
	
	@Test
	void testOrderPurchaseOrderForbidden() throws DomainEntityNotFoundException, ForbiddenOperationException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(false);

		PurchaseOrderEditRequest purchaseOrderEditRequest = new PurchaseOrderEditRequest();
		purchaseOrderEditRequest.setCode("code");
		purchaseOrderEditRequest.setProduct("product");
		purchaseOrderEditRequest.setAmount(BigDecimal.TEN);

		Exception exception = Assertions.assertThrows(ForbiddenOperationException.class, () -> {
			purchaseOrder = updatePurchaseOrderUseCase.update(purchaseOrderEditRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <UPDATE>",
				exception.getMessage());
	}

}
