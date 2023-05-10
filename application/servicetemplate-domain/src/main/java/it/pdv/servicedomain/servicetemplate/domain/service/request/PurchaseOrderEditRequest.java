package it.pdv.servicedomain.servicetemplate.domain.service.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PurchaseOrderEditRequest extends PurchaseOrderGetRequest {
	private String product;
	private BigDecimal amount;
}
