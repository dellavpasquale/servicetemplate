package it.pdv.servicedomain.servicetemplate.domain.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;

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
	@Test
	void testPurchaseOrderAmountNull() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer",  Instant.now());
		purchaseOrder.setAmount(null);
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'amount is not null': <false>", exception.getMessage());
	}
	@Test
	void testPurchaseOrderAmountNegative() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer",  Instant.now());
		purchaseOrder.setAmount(BigDecimal.valueOf(-10.5));
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'amount is not negative': <false>", exception.getMessage());
	}
	@Test
	void testPurchaseOrderInvalidOrderedAt() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", Status.DELIVERED, "customer",  Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'orderedAt is not null': <false>", exception.getMessage());
	}
	@Test
	void testPurchaseOrderInvalidExpectedDeliveryAt() throws InvalidDomainEntityException {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", Status.DELIVERED, "customer",  Instant.now());
		purchaseOrder.setOrderedAt(Instant.now());
		Exception exception = Assertions.assertThrows(InvalidDomainEntityException.class, () -> {
			purchaseOrder.validate();
		});
		assertEquals("'entity': <PurchaseOrder>, 'code': <code>, 'expectedDeliveryAt is not null': <false>", exception.getMessage());
	}

}
