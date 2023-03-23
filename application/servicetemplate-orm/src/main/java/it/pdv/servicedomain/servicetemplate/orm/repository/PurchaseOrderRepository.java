package it.pdv.servicedomain.servicetemplate.orm.repository;

import org.springframework.data.repository.CrudRepository;

import it.pdv.servicedomain.servicetemplate.orm.entity.PurchaseOrderEntity;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrderEntity, Long> {

	PurchaseOrderEntity findByCode(String code);

}
