package it.pdv.servicedomain.servicetemplate.domain.service.request;

import lombok.Data;

@Data
public class PurchaseOrderRequest extends PurchaseOrderGetRequest {
	private String customer;
}
