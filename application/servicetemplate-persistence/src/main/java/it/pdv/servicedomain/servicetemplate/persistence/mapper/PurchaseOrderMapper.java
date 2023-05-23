package it.pdv.servicedomain.servicetemplate.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.persistence.entity.PurchaseOrderEntity;

@Mapper
public interface PurchaseOrderMapper {
	
	PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

	PurchaseOrderEntity purchaseOrderToPurchaseOrderJPA(PurchaseOrder purchaseOrder);
	
	PurchaseOrder purchaseOrderJPAToPurchaseOrder(PurchaseOrderEntity purchaseOrderEntity);
	
	void updatePurchaseOrderJPAFromPurchaseOrder(PurchaseOrder purchaseOrder, @MappingTarget PurchaseOrderEntity purchaseOrderEntity);
	
}
