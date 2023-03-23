package it.pdv.servicedomain.servicetemplate.orm.service;

import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import it.pdv.servicedomain.servicetemplate.domain.adapter.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.orm.entity.PurchaseOrderJPA;
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
		PurchaseOrderJPA purchaseOrderJPA = mapper.purchaseOrderToPurchaseOrderJPA(purchaseOrder);
		try {
			purchaseOrderJPA = purchaseOrderRepository.save(purchaseOrderJPA);
		} catch (DataIntegrityViolationException e) {
			purchaseOrderJPA = null;
		}
		return purchaseOrderJPA != null;

	}

	public PurchaseOrder getPurchaseOrder(String code) {
		PurchaseOrder purchaseOrder = null;
		PurchaseOrderJPA purchaseOrderJPA = purchaseOrderRepository.findByCode(code);
		if (purchaseOrderJPA != null) {
			purchaseOrder = mapper.purchaseOrderJPAToPurchaseOrder(purchaseOrderJPA);
		}
		return purchaseOrder;
	}

	public boolean updatePurcahseOrder(PurchaseOrder purchaseOrder) {
		PurchaseOrderJPA purchaseOrderJPA = purchaseOrderRepository.findByCode(purchaseOrder.getCode());
		if (purchaseOrderJPA != null) {
			mapper.updatePurchaseOrderJPAFromPurchaseOrder(purchaseOrder, purchaseOrderJPA);
			purchaseOrderJPA = purchaseOrderRepository.save(purchaseOrderJPA);
		}
		return purchaseOrderJPA != null;
	}

}
