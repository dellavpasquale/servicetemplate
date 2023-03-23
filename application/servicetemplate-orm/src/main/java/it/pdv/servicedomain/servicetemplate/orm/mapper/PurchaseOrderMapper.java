package it.pdv.servicedomain.servicetemplate.orm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.orm.entity.PurchaseOrderEntity;

@Mapper
public interface PurchaseOrderMapper {

	PurchaseOrderEntity purchaseOrderToPurchaseOrderJPA(PurchaseOrder purchaseOrder);
	
	PurchaseOrder purchaseOrderJPAToPurchaseOrder(PurchaseOrderEntity purchaseOrderEntity);
	
	void updatePurchaseOrderJPAFromPurchaseOrder(PurchaseOrder purchaseOrder, @MappingTarget PurchaseOrderEntity purchaseOrderEntity);
	
}
