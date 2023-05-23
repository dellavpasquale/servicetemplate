package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderRequest;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderOpenAPI;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderRequestOpenAPI;

@Mapper
public interface PurchaseOrderMapper {
	
	PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

	PurchaseOrderRequest purchaseOrderRequestOpenAPIToPurchaseOrderRequest(PurchaseOrderRequestOpenAPI purchaseOrderRequestOpenAPI);

	PurchaseOrderOpenAPI purchaseOrderToPurchaseOrderOpenAPI(PurchaseOrder purchaseOrder);
	
	PurchaseOrder purchaseOrderOpenAPIToPurchaseOrder(PurchaseOrderOpenAPI purchaseOrderOpenAPI);
	
}
