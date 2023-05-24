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
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.ServiceUnavailableException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.ConfirmPurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.RetrievePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderGetRequest;

class ConfirmPurchaseOrderTest {

	private RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private AccessControlService accessControlService;
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private ConfirmPurchaseOrderUseCase confirmPurchaseOrderUseCase;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		retrievePurchaseOrderUseCase = mock(RetrievePurchaseOrderUseCase.class);
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		accessControlService = mock(AccessControlService.class);
		confirmPurchaseOrderUseCase = new ConfirmPurchaseOrderUseCase(retrievePurchaseOrderUseCase, accessControlService, purchaseOrderPersistenceService, purchaseOrderNotificationService);
		
		purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
	}

	@Test
	void testOrder() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, ForbiddenOperationException, ServiceUnavailableException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		purchaseOrder.setProduct("product");
		
		PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
		purchaseOrderGetRequest.setCode("code");
		purchaseOrder = confirmPurchaseOrderUseCase.confirm(purchaseOrderGetRequest);
		assertEquals(Status.ORDERED, purchaseOrder.getStatus());
	}

	@Test
	void testOrderPurchaseOrderAlreadyOrdered() throws DomainEntityNotFoundException, ForbiddenOperationException, ServiceUnavailableException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		purchaseOrder.setProduct("product");
		purchaseOrder.setStatus(Status.ORDERED);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
			purchaseOrderGetRequest.setCode("code");
			purchaseOrder = confirmPurchaseOrderUseCase.confirm(purchaseOrderGetRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CONFIRM>, 'status': <ORDERED>, 'expected': <DRAFT>",
				exception.getMessage());
	}
	
	@Test
	void testOrderPurchaseOrderWithoutProduct() throws DomainEntityNotFoundException, ForbiddenOperationException, ServiceUnavailableException {
		when(retrievePurchaseOrderUseCase.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
			purchaseOrderGetRequest.setCode("code");
			purchaseOrder = confirmPurchaseOrderUseCase.confirm(purchaseOrderGetRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CONFIRM>, 'product is not empty': <false>",
				exception.getMessage());
	}

}
