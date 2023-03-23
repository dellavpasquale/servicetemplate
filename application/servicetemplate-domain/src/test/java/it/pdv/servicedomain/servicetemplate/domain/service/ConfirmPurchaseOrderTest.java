package it.pdv.servicedomain.servicetemplate.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;

class ConfirmPurchaseOrderTest {

	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private ConfirmPurchaseOrderService confirmPurchaseOrderService;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		confirmPurchaseOrderService = new ConfirmPurchaseOrderService(purchaseOrderPersistenceService, purchaseOrderNotificationService);
		
		purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
	}

	@Test
	void testOrder() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException {
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);

		purchaseOrder = confirmPurchaseOrderService.confirm(purchaseOrder);
		assertEquals(Status.ORDERED, purchaseOrder.getStatus());
	}

	@Test
	void testOrderPurchaseOrderAlreadyOrdered() {
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		purchaseOrder.setStatus(Status.ORDERED);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			purchaseOrder = confirmPurchaseOrderService.confirm(purchaseOrder);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CONFIRM>, 'status': <ORDERED>, 'expected': <DRAFT>",
				exception.getMessage());
	}

	@Test
	void testOrderPurchaseOrderNotFound() {
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(false);

		Exception exception = Assertions.assertThrows(DomainEntityNotFoundException.class, () -> {
			purchaseOrder = confirmPurchaseOrderService.confirm(purchaseOrder);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>", exception.getMessage());
	}

}
