package com.PharmacyPOs.Pharmacy_POS.stock_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public List<Product> findByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    public Product update(String id,  Product product) {
        product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        product.setName(product.getName());
        product.setCostPrice(product.getCostPrice());
        product.setSellingPrice(product.getSellingPrice());
        product.setQuantityInStock(product.getQuantityInStock());
        product.setExpiryDate(product.getExpiryDate());
        return productRepository.save(product);

    }

    public Product updateExpiryDateAndQuantityInStock(String id, LocalDate expiryDate, int quantityInStock) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        product.setExpiryDate(expiryDate);
        product.setQuantityInStock(product.getQuantityInStock() + quantityInStock);
        product.setVersion(product.getVersion() + 1);
        return productRepository.save(product);

    }
}
