package it.pdv.servicedomain.servicetemplate.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.error.AccessDeniedException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderGetRequest;

class RetrievePrescriptionTest {
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private RetrievePurchaseOrderService retrievePurchaseOrderService;
	private AccessControlService accessControlService;

	@BeforeEach
	public void setUp() throws Exception {
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		accessControlService = mock(AccessControlService.class);
		retrievePurchaseOrderService = new RetrievePurchaseOrderService(purchaseOrderPersistenceService, accessControlService);
	}
	
	@Test
	void testGetPurchaseOrderFounded() throws InvalidDomainEntityException, DomainEntityNotFoundException, AccessDeniedException {
		givenThereIsPurchaseOrderWithCode("code", Status.DRAFT, "customer");
		
		PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
		purchaseOrderGetRequest.setCode("code");
		PurchaseOrder purchaseOrder = retrievePurchaseOrderService.getPurchaseOrder(purchaseOrderGetRequest);
		assertNotNull(purchaseOrder);
		assertEquals("code", purchaseOrder.getCode());
	}

	@Test
	void testGetPurchaseOrderNotFound() throws InvalidDomainEntityException {
		givenThereIsNoPurchaseOrder();
		Exception exception = Assertions.assertThrows(DomainEntityNotFoundException.class, () -> {
			PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
			purchaseOrderGetRequest.setCode("code");
			retrievePurchaseOrderService.getPurchaseOrder(purchaseOrderGetRequest);
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
		when(accessControlService.hasPermission(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
	}

	private void givenThereIsNoPurchaseOrder() throws InvalidDomainEntityException {
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(null);
		when(accessControlService.hasPermission(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
	}
}
