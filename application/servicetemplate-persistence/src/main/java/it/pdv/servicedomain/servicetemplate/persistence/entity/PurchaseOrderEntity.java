package it.pdv.servicedomain.servicetemplate.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity(name = "PurchaseOrder")
@Table(name = "purchase_order", uniqueConstraints = @UniqueConstraint(columnNames = "code") )
public class PurchaseOrderEntity extends ORMEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	private String code;
	private String status;
	private String customer;
	private String product;
	private BigDecimal amount;
	private Instant orderedAt;
	private Instant expectedDeliveryAt;
	
}
