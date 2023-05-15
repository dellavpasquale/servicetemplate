package it.pdv.servicedomain.servicetemplate.domain.usecase.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderEditRequest extends PurchaseOrderGetRequest {
	private String product;
	private BigDecimal amount;
}
