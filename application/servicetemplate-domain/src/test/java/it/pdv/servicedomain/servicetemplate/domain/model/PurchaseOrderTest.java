package it.pdv.servicedomain.servicetemplate.domain.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.purchaseorder.model.PurchaseOrder.Status;

class PurchaseOrderTest {

	@Test
	void testPurchaseOrder() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", Status.DRAFT,"customer",  Instant.now());
		purchaseOrder.validate();
		assertNotNull(purchaseOrder);
		assertEquals("code", purchaseOrder.getCode());
		assertEquals("customer", purchaseOrder.getCustomer());
		assertEquals(Status.DRAFT, purchaseOrder.getStatus());
	}
	@Test
	void testPurchaseOrderInvalidCode() {
		PurchaseOrder purchaseOrder = new PurchaseOrder(null, Status.DRAFT,"customer",  Instant.now());
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <null>, 'code is not blank': <false>", exception.getMessage());
	}
	@Test
	void testPurchaseOrderInvalidStatus() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", null, "customer",  Instant.now());
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'status is not null': <false>", exception.getMessage());
	}
	@Test
	void testPurchaseOrderInvalidCustomer() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", null, " ",  Instant.now());
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'status is not null': <false>", exception.getMessage());
	}
	@Test
	void testPurchaseOrderInvalidCreatedAt() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer",  null);
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'createdAt is not null': <false>", exception.getMessage());
	}

}
