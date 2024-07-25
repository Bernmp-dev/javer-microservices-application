package com.bernmpdev.javerpersistenceservice.model.repository;

import com.bernmpdev.javerpersistenceservice.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Boolean existsByCpf(String cpf);
}
