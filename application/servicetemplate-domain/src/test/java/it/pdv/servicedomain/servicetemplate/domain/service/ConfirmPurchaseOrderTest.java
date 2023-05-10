package it.pdv.servicedomain.servicetemplate.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.error.AccessDeniedException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderGetRequest;

class ConfirmPurchaseOrderTest {

	private RetrievePurchaseOrderService retrievePurchaseOrderService;
	private AccessControlService accessControlService;
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private ConfirmPurchaseOrderService confirmPurchaseOrderService;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		retrievePurchaseOrderService = mock(RetrievePurchaseOrderService.class);
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		accessControlService = mock(AccessControlService.class);
		confirmPurchaseOrderService = new ConfirmPurchaseOrderService(retrievePurchaseOrderService, accessControlService, purchaseOrderPersistenceService, purchaseOrderNotificationService);
		
		purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
	}

	@Test
	void testOrder() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		when(retrievePurchaseOrderService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		purchaseOrder.setProduct("product");
		
		PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
		purchaseOrderGetRequest.setCode("code");
		purchaseOrder = confirmPurchaseOrderService.confirm(purchaseOrderGetRequest);
		assertEquals(Status.ORDERED, purchaseOrder.getStatus());
	}

	@Test
	void testOrderPurchaseOrderAlreadyOrdered() throws DomainEntityNotFoundException, AccessDeniedException {
		when(retrievePurchaseOrderService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		purchaseOrder.setProduct("product");
		purchaseOrder.setStatus(Status.ORDERED);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
			purchaseOrderGetRequest.setCode("code");
			purchaseOrder = confirmPurchaseOrderService.confirm(purchaseOrderGetRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CONFIRM>, 'status': <ORDERED>, 'expected': <DRAFT>",
				exception.getMessage());
	}
	
	@Test
	void testOrderPurchaseOrderWithoutProduct() throws DomainEntityNotFoundException, AccessDeniedException {
		when(retrievePurchaseOrderService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
			purchaseOrderGetRequest.setCode("code");
			purchaseOrder = confirmPurchaseOrderService.confirm(purchaseOrderGetRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CONFIRM>, 'product is not empty': <false>",
				exception.getMessage());
	}

}
