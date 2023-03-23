package it.pdv.servicedomain.servicetemplate.orm.repository;

import org.springframework.data.repository.CrudRepository;

import it.pdv.servicedomain.servicetemplate.orm.entity.PurchaseOrderJPA;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrderJPA, Long> {

	PurchaseOrderJPA findByCode(String code);

}
