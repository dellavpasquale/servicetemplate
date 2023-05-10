package it.pdv.servicedomain.servicetemplate.domain.service.request;

import java.time.Instant;

import it.pdv.servicedomain.servicetemplate.domain.model.PurchaseOrder.Status;
import lombok.Data;

@Data
public class DeliveryEditRequest extends PurchaseOrderGetRequest {
	private Instant expectedDeliveryAt;
	private Status status;
}
