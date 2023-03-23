package it.pdv.servicedomain.servicetemplate.orm.service;

import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.orm.entity.PurchaseOrderEntity;
import it.pdv.servicedomain.servicetemplate.orm.mapper.PurchaseOrderMapper;
import it.pdv.servicedomain.servicetemplate.orm.repository.PurchaseOrderRepository;

@Transactional
public class PurchaseOrderPersistenceServiceImpl implements PurchaseOrderPersistenceService {

	private PurchaseOrderRepository purchaseOrderRepository;
	private PurchaseOrderMapper mapper = Mappers.getMapper(PurchaseOrderMapper.class);

	public PurchaseOrderPersistenceServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
		this.purchaseOrderRepository = purchaseOrderRepository;
	}

	public boolean createPurchaseOrder(PurchaseOrder purchaseOrder) {
		PurchaseOrderEntity purchaseOrderEntity = mapper.purchaseOrderToPurchaseOrderJPA(purchaseOrder);
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
			purchaseOrder = mapper.purchaseOrderJPAToPurchaseOrder(purchaseOrderEntity);
		}
		return purchaseOrder;
	}

	public boolean updatePurcahseOrder(PurchaseOrder purchaseOrder) {
		PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findByCode(purchaseOrder.getCode());
		if (purchaseOrderEntity != null) {
			mapper.updatePurchaseOrderJPAFromPurchaseOrder(purchaseOrder, purchaseOrderEntity);
			purchaseOrderEntity = purchaseOrderRepository.save(purchaseOrderEntity);
		}
		return purchaseOrderEntity != null;
	}

}
