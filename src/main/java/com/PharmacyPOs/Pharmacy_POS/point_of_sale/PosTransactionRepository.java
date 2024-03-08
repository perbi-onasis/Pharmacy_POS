package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PosTransactionRepository extends MongoRepository<PosTransaction, String> {
    List<PosTransaction> findAll();
    Optional<PosTransaction> findById(UUID id);
    List<PosTransaction> findAll(Sort sort);
}


