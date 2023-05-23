package it.pdv.servicedomain.servicetemplate.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.DeliveryEditRequest;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderEditRequest;

@Mapper
public interface PurchaseOrderMapper {
	
	PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

	void updatePurchaseOrderFromPurchaseOrderEditRequest(PurchaseOrderEditRequest purchaseOrderEditRequest, @MappingTarget PurchaseOrder purchaseOrder);

	void updatePurchaseOrderFromDeliveryEditRequest(DeliveryEditRequest deliveryEditRequest, @MappingTarget PurchaseOrder purchaseOrder);
	
}
