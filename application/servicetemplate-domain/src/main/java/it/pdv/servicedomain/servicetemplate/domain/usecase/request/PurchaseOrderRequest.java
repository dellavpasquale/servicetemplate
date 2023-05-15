package it.pdv.servicedomain.servicetemplate.domain.usecase.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderRequest extends PurchaseOrderGetRequest {
	private String customer;
}
