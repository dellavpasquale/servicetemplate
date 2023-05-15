package it.pdv.servicedomain.servicetemplate.domain.usecase.request;

import java.time.Instant;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryEditRequest extends PurchaseOrderGetRequest {
	private Instant expectedDeliveryAt;
	private Status status;
}
