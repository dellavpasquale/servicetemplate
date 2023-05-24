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

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.ServiceUnavailableException;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.CreatePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderRequest;

class CreatePurchaseOrderTest {

	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private CreatePurchaseOrderUseCase createPurchaseOrderUseCase;
	private AccessControlService accessControlService;
	
	@BeforeEach
	public void setUp() throws Exception {
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		accessControlService = mock(AccessControlService.class);
		createPurchaseOrderUseCase = new CreatePurchaseOrderUseCase(purchaseOrderPersistenceService, purchaseOrderNotificationService, accessControlService);
	}

	@Test
	void testCreatePurchaseOrder() throws InvalidDomainEntityException, DomainEntityAlreadyExistsException, ForbiddenOperationException, ServiceUnavailableException {
		when(purchaseOrderPersistenceService.createPurchaseOrder(any())).thenReturn(true);
		when(accessControlService.hasPermission(any())).thenReturn(true);

		PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest();
		purchaseOrderRequest.setCustomer("customer");
		PurchaseOrder purchaseOrder = createPurchaseOrderUseCase.createPurchaseOrder(purchaseOrderRequest);
		assertNotNull(purchaseOrder);
		assertEquals("customer", purchaseOrder.getCustomer());
		assertEquals(Status.DRAFT, purchaseOrder.getStatus());
		assertNotNull(purchaseOrder.getCreatedAt());
	}

	@Test
	void tesPurchaseOrderInvalid() {
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			createPurchaseOrderUseCase.createPurchaseOrder(null);
		});
		assertTrue(exception.getMessage().endsWith("'customer is not blank': <false>"));
	}
	
	@Test
	void tesForbiddenOperation() {
		when(accessControlService.hasPermission(any())).thenReturn(false);
		Exception exception = Assertions.assertThrows(ForbiddenOperationException.class, () -> {
			PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest();
			purchaseOrderRequest.setCode("code");
			purchaseOrderRequest.setCustomer("customer");
			createPurchaseOrderUseCase.createPurchaseOrder(purchaseOrderRequest);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'operation': <CREATE>", exception.getMessage());
	}

	@Test
	void tesPurchaseOrderAlreadyExists() throws ServiceUnavailableException {
		when(purchaseOrderPersistenceService.createPurchaseOrder(any())).thenReturn(false);
		when(accessControlService.hasPermission(any())).thenReturn(true);

		PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest();
		purchaseOrderRequest.setCode("code");
		purchaseOrderRequest.setCustomer("customer");
		Exception exception = Assertions.assertThrows(DomainEntityAlreadyExistsException.class, () -> {
			createPurchaseOrderUseCase.createPurchaseOrder(purchaseOrderRequest);
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>", exception.getMessage());
	}

}
