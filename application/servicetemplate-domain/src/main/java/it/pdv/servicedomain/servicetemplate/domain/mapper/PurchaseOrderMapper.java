package it.pdv.servicedomain.servicetemplate.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.service.request.DeliveryEditRequest;
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderEditRequest;

@Mapper
public interface PurchaseOrderMapper {

	void updatePurchaseOrderFromPurchaseOrderEditRequest(PurchaseOrderEditRequest purchaseOrderEditRequest, @MappingTarget PurchaseOrder purchaseOrder);

	void updatePurchaseOrderFromDeliveryEditRequest(DeliveryEditRequest deliveryEditRequest, @MappingTarget PurchaseOrder purchaseOrder);
	
}
