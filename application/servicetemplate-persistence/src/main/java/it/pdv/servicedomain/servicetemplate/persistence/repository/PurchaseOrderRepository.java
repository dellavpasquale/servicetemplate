package it.pdv.servicedomain.servicetemplate.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.pdv.servicedomain.servicetemplate.persistence.entity.PurchaseOrderEntity;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrderEntity, Long> {

	PurchaseOrderEntity findByCode(String code);

}
