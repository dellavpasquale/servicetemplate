package it.pdv.servicedomain.servicetemplate.restapi.service;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.service.CreatePurchaseOrderService;
import it.pdv.servicedomain.servicetemplate.domain.service.request.PurchaseOrderRequest;
import it.pdv.servicedomain.servicetemplate.restapi.PurchaseorderApiDelegate;
import it.pdv.servicedomain.servicetemplate.restapi.exception.OpenAPIException;
import it.pdv.servicedomain.servicetemplate.restapi.mapper.PurchaseOrderMapper;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderOpenAPI;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderRequestOpenAPI;

public class PurchaseOrderRestApiImpl implements PurchaseorderApiDelegate {

	private PurchaseOrderMapper mapper = Mappers.getMapper(PurchaseOrderMapper.class);
	private CreatePurchaseOrderService createPurchaseOrderService;
	
	public PurchaseOrderRestApiImpl(CreatePurchaseOrderService createPurchaseOrderService) {
		this.createPurchaseOrderService = createPurchaseOrderService;
	}
	
	@Override
	public ResponseEntity<PurchaseOrderOpenAPI> createPurchaseOrder(
			PurchaseOrderRequestOpenAPI purchaseOrderRequestOpenAPI) {
		PurchaseOrderRequest purchaseOrderRequest = mapper.purchaseOrderRequestOpenAPIToPurchaseOrderRequest(purchaseOrderRequestOpenAPI);
		try {
			PurchaseOrder purchaseOrder = createPurchaseOrderService.createPurchaseOrder(purchaseOrderRequest);
			PurchaseOrderOpenAPI result = mapper.purchaseOrderToPurchaseOrderOpenAPI(purchaseOrder);
			return new ResponseEntity<PurchaseOrderOpenAPI>(result, HttpStatus.OK);
		} catch (InvalidDomainEntityException | DomainEntityAlreadyExistsException e) {
			throw new OpenAPIException(e);
		}
	}
	
}
