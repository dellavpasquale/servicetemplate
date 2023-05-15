package it.pdv.servicedomain.servicetemplate.domain.usecase;

import org.mapstruct.factory.Mappers;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.error.AccessDeniedException;
import it.pdv.servicedomain.servicetemplate.domain.error.DomainEntityNotFoundException;
import it.pdv.servicedomain.servicetemplate.domain.error.ForbiddenOperationException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidOperationException;
import it.pdv.servicedomain.servicetemplate.domain.mapper.PurchaseOrderMapper;
import it.pdv.servicedomain.servicetemplate.domain.port.AccessControlService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderNotificationService;
import it.pdv.servicedomain.servicetemplate.domain.port.PurchaseOrderPersistenceService;
import it.pdv.servicedomain.servicetemplate.domain.usecase.request.DeliveryEditRequest;
import it.pdv.servicedomain.servicetemplate.domain.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateDeliveryUseCase {
	
	private final RetrievePurchaseOrderUseCase retrievePurchaseOrderUseCase;
	private final AccessControlService accessControlService;
	private final PurchaseOrderPersistenceService purchaseOrderPersistenceService;
	private final PurchaseOrderNotificationService purchaseOrderNotificationService;
	
	private PurchaseOrderMapper mapper = Mappers.getMapper(PurchaseOrderMapper.class);

	public PurchaseOrder updateDelivery(DeliveryEditRequest deliveryEditRequest) throws InvalidOperationException, DomainEntityNotFoundException, InvalidDomainEntityException, AccessDeniedException, ForbiddenOperationException {
		PurchaseOrder purchaseOrder = retrievePurchaseOrderUseCase.getPurchaseOrder(deliveryEditRequest);
		validateOperation(purchaseOrder);
		mapper.updatePurchaseOrderFromDeliveryEditRequest(deliveryEditRequest, purchaseOrder);
		updatePurchaseOrder(purchaseOrder);
		purchaseOrderNotificationService.notifyPurchaseOrderChange(purchaseOrder);
		return purchaseOrder;
	}

	private void validateOperation(PurchaseOrder purchaseOrder) throws InvalidOperationException, ForbiddenOperationException {
		if(!isAccessAllowed(purchaseOrder)) {
			throw new ForbiddenOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "UPDATE DELIVERY");
		}
		if(ValidationUtil.enumIn(purchaseOrder.getStatus(), Status.DRAFT, Status.CANCELLED)) {
			throw new InvalidOperationException(PurchaseOrder.class, purchaseOrder.getCode(), "UPDATE DELIVERY", "status", "not in (draft, cancelled)", purchaseOrder.getStatus());
		}
	}
	
	private boolean isAccessAllowed(PurchaseOrder purchaseOrder) {
		return accessControlService.hasPermission("updateDelivery");
	}
	
	private void updatePurchaseOrder(PurchaseOrder purchaseOrder) throws DomainEntityNotFoundException {
		boolean purchaseOrderFound = purchaseOrderPersistenceService.updatePurcahseOrder(purchaseOrder);
		if(!purchaseOrderFound) {
			throw new DomainEntityNotFoundException(PurchaseOrder.class, purchaseOrder.getCode());
		}
	}
	
}
