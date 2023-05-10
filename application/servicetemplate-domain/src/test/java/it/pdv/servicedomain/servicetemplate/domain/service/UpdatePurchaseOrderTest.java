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
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderEditRequest;

class UpdatePurchaseOrderTest {

	private RetrievePurchaseOrderService retrievePurchaseOrderService;
	private AccessControlService accessControlService;
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrderNotificationService purchaseOrderNotificationService;
	private UpdatePurchaseOrderService updatePurchaseOrderService;
	private PurchaseOrder purchaseOrder;

	@BeforeEach
	public void setUp() throws Exception {
		retrievePurchaseOrderService = mock(RetrievePurchaseOrderService.class);
		purchaseOrderPersistenceService = mock(PurchaseOrderPersistenceService.class);
		purchaseOrderNotificationService = mock(PurchaseOrderNotificationService.class);
		accessControlService = mock(AccessControlService.class);
		updatePurchaseOrderService = new UpdatePurchaseOrderService(retrievePurchaseOrderService, accessControlService, purchaseOrderPersistenceService, purchaseOrderNotificationService);
		
		purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
	}

	@Test
	void testOrder() throws DomainEntityNotFoundException, InvalidOperationException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		when(retrievePurchaseOrderService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);

		PurchaseOrderEditRequest purchaseOrderEditRequest = new PurchaseOrderEditRequest();
		purchaseOrderEditRequest.setCode("code");
		purchaseOrderEditRequest.setProduct("product");
		purchaseOrderEditRequest.setAmount(BigDecimal.TEN);
		purchaseOrder = updatePurchaseOrderService.update(purchaseOrderEditRequest);
		assertEquals(BigDecimal.TEN, purchaseOrder.getAmount());
		assertEquals("product", purchaseOrder.getProduct());
	}
	
	@Test
	void testOrderPurchaseOrderAlreadyOrdered() throws DomainEntityNotFoundException, AccessDeniedException {
		purchaseOrder.setProduct("product");
		purchaseOrder.setStatus(Status.ORDERED);
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());
		when(retrievePurchaseOrderService.getPurchaseOrder(any())).thenReturn(purchaseOrder);
		when(purchaseOrderPersistenceService.updatePurcahseOrder(any())).thenReturn(true);
		when(accessControlService.isLoggedUser(any())).thenReturn(true);

		PurchaseOrderEditRequest purchaseOrderEditRequest = new PurchaseOrderEditRequest();
		purchaseOrderEditRequest.setCode("code");
		purchaseOrderEditRequest.setProduct("product");
		purchaseOrderEditRequest.setAmount(BigDecimal.TEN);

		Exception exception = Assertions.assertThrows(InvalidOperationException.class, () -> {
			purchaseOrder = updatePurchaseOrderService.update(purchaseOrderEditRequest);
		});
		assertEquals(
				"'entity': <PurchaseOrder>, 'code': <code>, 'operation': <UPDATE>, 'status': <ORDERED>, 'expected': <DRAFT>",
				exception.getMessage());
	}

}
