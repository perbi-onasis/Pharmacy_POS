package com.PharmacyPOs.Pharmacy_POS.stock_management;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {


    List<Product> findByCategoryId(String categoryId);
    @Query(value = "{ 'productName' : ?0 }", exists = true)
    boolean existsByName(String productName);

    Product findByProductName(String productName);
}