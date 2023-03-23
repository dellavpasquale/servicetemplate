package it.pdv.servicedomain.servicetemplate.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;

class CreatePurchaseOrderTest {

	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private CreatePurchaseOrderService createPurchaseOrderService;
	
	@BeforeEach
	public void setUp() throws Exception {
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		createPurchaseOrderService = new CreatePurchaseOrderService(purchaseOrderPersistenceService, purchaseOrderNotificationService);
	}

	@Test
	void testCreatePurchaseOrder() throws InvalidDomainEntityException, DomainEntityAlreadyExistsException {
		when(purchaseOrderPersistenceService.createPurchaseOrder(any())).thenReturn(true);

		PurchaseOrder purchaseOrder = createPurchaseOrderService.createPurchaseOrder("customer", "");
		assertNotNull(purchaseOrder);
		assertEquals("customer", purchaseOrder.getCustomer());
		assertEquals(Status.DRAFT, purchaseOrder.getStatus());
		assertNotNull(purchaseOrder.getCreatedAt());
	}

	@Test
	void tesPurchaseOrderInvalid() {
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			createPurchaseOrderService.createPurchaseOrder(" ", "");
		});
		assertTrue(exception.getMessage().endsWith("'customer is not blank': <false>"));
	}

	@Test
	void tesPurchaseOrderAlreadyExists() {
		when(purchaseOrderPersistenceService.createPurchaseOrder(any())).thenReturn(false);

		Exception exception = Assertions.assertThrows(DomainEntityAlreadyExistsException.class, () -> {
			createPurchaseOrderService.createPurchaseOrder("customer", "code");
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>", exception.getMessage());
	}

}
