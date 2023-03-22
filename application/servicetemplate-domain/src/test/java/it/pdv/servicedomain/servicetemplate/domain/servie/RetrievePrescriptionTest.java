package it.pdv.servicedomain.servicetemplate.domain.servie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.persistence.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.service.RetrievePurchaseOrderService;

class RetrievePrescriptionTest {
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private RetrievePurchaseOrderService retrievePurchaseOrderService;

	@BeforeEach
	public void setUp() throws Exception {
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		retrievePurchaseOrderService = new RetrievePurchaseOrderService(purchaseOrderPersistenceService);
	}
	
	@Test
	void testGetPurchaseOrderFounded() throws InvalidDomainEntityException, DomainEntityNotFoundException {
		givenThereIsPurchaseOrderWithCode("code", Status.DRAFT, "customer");
		PurchaseOrder purchaseOrder = retrievePurchaseOrderService.getPurchaseOrder("code");
		assertNotNull(purchaseOrder);
		assertEquals("code", purchaseOrder.getCode());
	}

	@Test
	void testGetPurchaseOrderNotFound() throws InvalidDomainEntityException {
		givenThereIsNoPurchaseOrder();
		Exception exception = Assertions.assertThrows(DomainEntityNotFoundException.class, () -> {
			retrievePurchaseOrderService.getPurchaseOrder("code");
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>", exception.getMessage());
	}

	@Test
	void testGetPurchaseOrderNotFoundWithInvalidInput() throws InvalidDomainEntityException {
		givenThereIsNoPurchaseOrder();
		Exception exception = Assertions.assertThrows(DomainEntityNotFoundException.class, () -> {
			retrievePurchaseOrderService.getPurchaseOrder(null);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <null>", exception.getMessage());
	}

	private void givenThereIsPurchaseOrderWithCode(String code, Status status, String customer)
			throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder(code, status, customer, Instant.now());
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
	}

	private void givenThereIsNoPurchaseOrder() throws InvalidDomainEntityException {
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(null);
	}
}
