package it.pdv.servicedomain.servicetemplate.orm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.orm.entity.PurchaseOrderJPA;

@Mapper
public interface PurchaseOrderMapper {

	PurchaseOrderJPA purchaseOrderToPurchaseOrderJPA(PurchaseOrder purchaseOrder);
	
	PurchaseOrder purchaseOrderJPAToPurchaseOrder(PurchaseOrderJPA purchaseOrderJPA);
	
	void updatePurchaseOrderJPAFromPurchaseOrder(PurchaseOrder purchaseOrder, @MappingTarget PurchaseOrderJPA purchaseOrderJPA);
	
}
