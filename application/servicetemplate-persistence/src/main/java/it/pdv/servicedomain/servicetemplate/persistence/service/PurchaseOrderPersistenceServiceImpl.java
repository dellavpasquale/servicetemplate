package it.pdv.servicedomain.servicetemplate.persistence.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.persistence.entity.PurchaseOrderEntity;
import it.pdv.servicedomain.servicetemplate.persistence.mapper.PurchaseOrderMapper;
import it.pdv.servicedomain.servicetemplate.persistence.repository.PurchaseOrderRepository;

@Transactional
public class PurchaseOrderPersistenceServiceImpl implements PurchaseOrderPersistenceService {

	private PurchaseOrderRepository purchaseOrderRepository;

	public PurchaseOrderPersistenceServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
		this.purchaseOrderRepository = purchaseOrderRepository;
	}

	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder) {
		PurchaseOrderEntity purchaseOrderEntity = PurchaseOrderMapper.INSTANCE.purchaseOrderToPurchaseOrderJPA(purchaseOrder);
		try {
			purchaseOrderEntity = purchaseOrderRepository.save(purchaseOrderEntity);
		} catch (DataIntegrityViolationException e) {
			purchaseOrderEntity = null;
		}
		return purchaseOrderEntity != null;

	}

	public PurchaseOrder getPurchaseOrder(String code) {
		PurchaseOrder purchaseOrder = null;
		PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findByCode(code);
		if (purchaseOrderEntity != null) {
			purchaseOrder = PurchaseOrderMapper.INSTANCE.purchaseOrderJPAToPurchaseOrder(purchaseOrderEntity);
		}
		return purchaseOrder;
	}

	public boolean updatePurcahseOrder(PurchaseOrder purchaseOrder) {
		PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findByCode(purchaseOrder.getCode());
		if (purchaseOrderEntity != null) {
			PurchaseOrderMapper.INSTANCE.updatePurchaseOrderJPAFromPurchaseOrder(purchaseOrder, purchaseOrderEntity);
			purchaseOrderEntity = purchaseOrderRepository.save(purchaseOrderEntity);
		}
		return purchaseOrderEntity != null;
	}

}
