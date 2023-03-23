package it.pdv.servicedomain.servicetemplate.domain.model;

import java.time.Instant;

import it.pdv.servicedomain.servicetemplate.domain.ValidationUtil;
import it.pdv.servicedomain.servicetemplate.domain.error.InvalidDomainEntityException;

public class PurchaseOrder extends DomainEntity {
	public enum Status {
		DRAFT, ORDERED, RECEIVED, CLOSED
	}
	private String code;
	private Status status;
	private String customer;
	private Instant createdAt;
	private Instant orderedAt;
	private Instant expectedDeliveryAt;

	public PurchaseOrder(String code, Status status, String customer, Instant createdAt) {
		this.code = code;
		this.customer = customer;
		this.createdAt = createdAt;
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getCustomer() {
		return customer;
	}
	public Instant getOrderedAt() {
		return orderedAt;
	}
	public void setOrderedAt(Instant orderedAt) {
		this.orderedAt = orderedAt;
	}
	public Instant getExpectedDeliveryAt() {
		return expectedDeliveryAt;
	}
	public void setExpectedDeliveryAt(Instant expectedDeliveryAt) {
		this.expectedDeliveryAt = expectedDeliveryAt;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	@Override
	public void validate() throws InvalidDomainEntityException {
		if(ValidationUtil.isBlank(getCode())) {
			throw new InvalidDomainEntityException(this.getClass(), getCode(), "code is not blank");
		}
		if(getStatus() == null) {
			throw new InvalidDomainEntityException(this.getClass(), getCode(), "status is not null");
		}
		if(ValidationUtil.isBlank(getCustomer())) {
			throw new InvalidDomainEntityException(this.getClass(), getCode(), "customer is not blank");
		}
		if(getCreatedAt() == null) {
			throw new InvalidDomainEntityException(this.getClass(), getCode(), "createdAt is not null");
		}
		if(ValidationUtil.enumNotIn(getStatus(), Status.DRAFT) && getOrderedAt() == null) {
			throw new InvalidDomainEntityException(this.getClass(), getCode(), "orderedAt is not null");
		}
		if(ValidationUtil.enumNotIn(getStatus(), Status.DRAFT) && getExpectedDeliveryAt() == null) {
			throw new InvalidDomainEntityException(this.getClass(), getCode(), "expectedDeliveryAt is not null");
		}
	}
}
