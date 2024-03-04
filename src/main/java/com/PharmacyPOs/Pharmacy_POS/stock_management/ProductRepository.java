package com.PharmacyPOs.Pharmacy_POS.stock_management;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {


    List<Product> findByCategoryId(String categoryId);

}