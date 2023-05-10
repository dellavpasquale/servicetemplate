package it.pdv.servicedomain.servicetemplate.restapi.mapper;

import org.mapstruct.Mapper;

import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderRequest;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderOpenAPI;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderRequestOpenAPI;

@Mapper
public interface PurchaseOrderMapper {
	
	PurchaseOrderRequest purchaseOrderRequestOpenAPIToPurchaseOrderRequest(PurchaseOrderRequestOpenAPI purchaseOrderRequestOpenAPI);

	PurchaseOrderOpenAPI purchaseOrderToPurchaseOrderOpenAPI(PurchaseOrder purchaseOrder);
	
	PurchaseOrder purchaseOrderOpenAPIToPurchaseOrder(PurchaseOrderOpenAPI purchaseOrderOpenAPI);
	
}