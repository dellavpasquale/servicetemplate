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
import it.pdv.servicedomain.servicetemplate.domain.usecase.CancelPurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.RetrievePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderGetRequest;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderRequest;

class CancelPurchaseOrderTest {

	private RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private AccessControlService accessControlService;
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private CancelPurchaseOrderUseCase cancelPurchaseOrderUseCase;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		retrievePurchaseOrderUseCase = mock(RetrievePurchaseOrderUseCase.class);
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		accessControlService = mock(AccessControlService.class);
		cancelPurchaseOrderUseCase = new CancelPurchaseOrderUseCase(retrievePurchaseOrderUseCase, accessControlService, purchaseOrderPersistenceService, purchaseOrderNotificationService);
		
		purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
	}

	@Test
	void testCancel() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		
		PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
		purchaseOrderGetRequest.setCode("code");
		purchaseOrder = cancelPurchaseOrderUseCase.cancel(purchaseOrderGetRequest);
		assertEquals(Status.CANCELLED, purchaseOrder.getStatus());
	}

	@Test
	void testOrderPurchaseOrderAlreadyInProgress() throws DomainEntityNotFoundException, AccessDeniedException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		purchaseOrder.setProduct("product");
		purchaseOrder.setStatus(Status.IN_PROGRESS);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
			purchaseOrderGetRequest.setCode("code");
			purchaseOrder = cancelPurchaseOrderUseCase.cancel(purchaseOrderGetRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CANCEL>, 'status': <IN_PROGRESS>, 'expected': <(DRAFT, ORDERED)>",
				exception.getMessage());
	}
	
	@Test
	void tesForbiddenOperation() throws DomainEntityNotFoundException, AccessDeniedException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(false);
		Exception exception = Assertions.assertThrows(ForbiddenOperationException.class, () -> {
			PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest();
			purchaseOrderRequest.setCode("code");
			purchaseOrderRequest.setCustomer("customer");
			cancelPurchaseOrderUseCase.cancel(purchaseOrderRequest);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CANCEL>", exception.getMessage());
	}
}
