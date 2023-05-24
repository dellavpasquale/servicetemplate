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

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.ServiceUnavailableException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.RetrievePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderGetRequest;

class RetrievePrescriptionTest {
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private AccessControlService accessControlService;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		accessControlService = mock(AccessControlService.class);
		retrievePurchaseOrderUseCase = new RetrievePurchaseOrderUseCase(purchaseOrderPersistenceService, accessControlService);
		
		purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
	}
	
	@Test
	void testGetPurchaseOrderFounded() throws InvalidDomainEntityException, DomainEntityNotFoundException, ForbiddenOperationException, ServiceUnavailableException {
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(accessControlService.hasPermission(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		
		PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
		purchaseOrderGetRequest.setCode("code");
		PurchaseOrder result = retrievePurchaseOrderUseCase.getPurchaseOrder(purchaseOrderGetRequest);
		assertNotNull(result);
		assertEquals("code", result.getCode());
	}

	@Test
	void testGetPurchaseOrderNotFound() throws InvalidDomainEntityException, ServiceUnavailableException {
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(null);
		when(accessControlService.hasPermission(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		
		Exception exception = Assertions.assertThrows(DomainEntityNotFoundException.class, () -> {
			PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
			purchaseOrderGetRequest.setCode("code");
			retrievePurchaseOrderUseCase.getPurchaseOrder(purchaseOrderGetRequest);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>", exception.getMessage());
	}

	@Test
	void testGetPurchaseOrderNotFoundWithInvalidInput() throws InvalidDomainEntityException, ServiceUnavailableException {
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(null);
		when(accessControlService.hasPermission(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		
		Exception exception = Assertions.assertThrows(DomainEntityNotFoundException.class, () -> {
			retrievePurchaseOrderUseCase.getPurchaseOrder(null);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <null>", exception.getMessage());
	}
	
	@Test
	void testGetPurchaseOrderByOwner() throws InvalidDomainEntityException, DomainEntityNotFoundException, ForbiddenOperationException, ServiceUnavailableException {
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(accessControlService.hasPermission(any())).thenReturn(false);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);
		
		PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
		purchaseOrderGetRequest.setCode("code");
		PurchaseOrder result = retrievePurchaseOrderUseCase.getPurchaseOrder(purchaseOrderGetRequest);
		assertNotNull(result);
		assertEquals("code", result.getCode());
	}
	
	@Test
	void testGetPurchaseOrderWithoutPermission() throws InvalidDomainEntityException, ServiceUnavailableException {
		when(purchaseOrderPersistenceService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(accessControlService.hasPermission(any())).thenReturn(false);
		when(accessControlService.isLoggedUser(any())).thenReturn(false);
		
		PurchaseOrderGetRequest purchaseOrderGetRequest = new PurchaseOrderGetRequest();
		purchaseOrderGetRequest.setCode("code");
		Exception exception = Assertions.assertThrows(ForbiddenOperationException.class, () -> {
			retrievePurchaseOrderUseCase.getPurchaseOrder(purchaseOrderGetRequest);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'operation': <GET>", exception.getMessage());
	}

}
