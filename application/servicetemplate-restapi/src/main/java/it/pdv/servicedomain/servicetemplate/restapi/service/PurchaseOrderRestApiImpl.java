package it.pdv.servicedomain.servicetemplate.restapi.service;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityAlreadyExistsException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.usecase.CreatePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.PurchaseOrderRequest;
import it.pdv.servicedomain.servicetemplate.restapi.PurchaseorderApiDelegate;
import it.pdv.servicedomain.servicetemplate.restapi.exception.OpenAPIException;
import it.pdv.servicedomain.servicetemplate.restapi.mapper.PurchaseOrderMapper;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderOpenAPI;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderRequestOpenAPI;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseOrderRestApiImpl implements PurchaseorderApiDelegate {

	private final CreatePurchaseOrderUseCase createPurchaseOrderUseCase;
	
	@Override
	public ResponseEntity<PurchaseOrderOpenAPI> createPurchaseOrder(
			PurchaseOrderRequestOpenAPI purchaseOrderRequestOpenAPI) {
		PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderMapper.INSTANCE.purchaseOrderRequestOpenAPIToPurchaseOrderRequest(purchaseOrderRequestOpenAPI);
		try {
			PurchaseOrder purchaseOrder = createPurchaseOrderUseCase.createPurchaseOrder(purchaseOrderRequest);
			PurchaseOrderOpenAPI result = PurchaseOrderMapper.INSTANCE.purchaseOrderToPurchaseOrderOpenAPI(purchaseOrder);
			return new ResponseEntity<PurchaseOrderOpenAPI>(result, HttpStatus.OK);
		} catch (InvalidDomainEntityException | DomainEntityAlreadyExistsException | ForbiddenOperationException e) {
			throw new OpenAPIException(e);
		}
	}
	
}
