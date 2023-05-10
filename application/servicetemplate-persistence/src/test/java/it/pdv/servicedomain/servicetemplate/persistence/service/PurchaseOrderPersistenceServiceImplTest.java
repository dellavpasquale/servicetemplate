package it.pdv.servicedomain.servicetemplate.persistence.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.persistence.repository.PurchaseOrderRepository;
import it.pdv.servicedomain.servicetemplate.persistence.service.PurchaseOrderPersistenceServiceImpl;

@Transactional(propagation = Propagation.NEVER)
@DataJpaTest(showSql = true)
class PurchaseOrderPersistenceServiceImplTest {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	private PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private PurchaseOrder purchaseOrder;
	
	@BeforeEach
	void init() {
		purchaseOrderPersistenceService = new PurchaseOrderPersistenceServiceImpl(purchaseOrderRepository);
		purchaseOrder = new PurchaseOrder(UUID.randomUUID().toString(), Status.DRAFT, "customer", Instant.now());
		purchaseOrderPersistenceService.createPurchaseOrder(purchaseOrder);
	}
	
	@Test
	void testCreatePurchaseOrder() {
		PurchaseOrder newPurchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
		boolean result = purchaseOrderPersistenceService.createPurchaseOrder(newPurchaseOrder);
		
		assertTrue(result);
	}
	
	@Test
	void testCreateDuplicatedPurchaseOrder() {
		boolean result = purchaseOrderPersistenceService.createPurchaseOrder(purchaseOrder);
		
		assertFalse(result);
	}
	
	@Test
	void testGetPurchaseOrder() {
		PurchaseOrder retrievedPurchaseOrder = purchaseOrderPersistenceService.getPurchaseOrder(purchaseOrder.getCode());
		assertNotNull(retrievedPurchaseOrder);
		assertEquals(purchaseOrder.getCode(), retrievedPurchaseOrder.getCode());
		assertEquals(purchaseOrder.getCustomer(), retrievedPurchaseOrder.getCustomer());
		assertEquals(purchaseOrder.getStatus(), retrievedPurchaseOrder.getStatus());
	}
	
	@Test
	void testGetNoPurchaseOrder() {
		PurchaseOrder retrievedPurchaseOrder = purchaseOrderPersistenceService.getPurchaseOrder("nocode");
		assertNull(retrievedPurchaseOrder);
	}
	
	@Test
	void testUpdatePurchaseOrder() {
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());
		purchaseOrder.setStatus(Status.DELIVERED);
		boolean result = purchaseOrderPersistenceService.updatePurcahseOrder(purchaseOrder);
		
		assertTrue(result);
		PurchaseOrder retrievedPurchaseOrder = purchaseOrderPersistenceService.getPurchaseOrder(purchaseOrder.getCode());
		assertNotNull(retrievedPurchaseOrder);
		assertEquals(purchaseOrder.getCode(), retrievedPurchaseOrder.getCode());
		assertEquals(purchaseOrder.getCustomer(), retrievedPurchaseOrder.getCustomer());
		assertEquals(purchaseOrder.getStatus(), retrievedPurchaseOrder.getStatus());
		assertNotNull(retrievedPurchaseOrder.getOrderedAt());
		assertNotNull(retrievedPurchaseOrder.getExpectedDeliveryAt());
	}
	
	@Test
	void testUpdateNoPurchaseOrder() {
		purchaseOrder = new PurchaseOrder("updatecode", Status.DELIVERED, "customer", Instant.now());
		purchaseOrder.setOrderedAt(Instant.now());
		purchaseOrder.setExpectedDeliveryAt(Instant.now());
		boolean result = purchaseOrderPersistenceService.updatePurcahseOrder(purchaseOrder);
		
		assertFalse(result);
		PurchaseOrder retrievedPurchaseOrder = purchaseOrderPersistenceService.getPurchaseOrder("updatecode");
		assertNull(retrievedPurchaseOrder);
	}

}
