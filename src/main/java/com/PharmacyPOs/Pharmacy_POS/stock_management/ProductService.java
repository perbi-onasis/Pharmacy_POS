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


    public Product update(String id,  Product updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        product.setName(updatedProduct.getName());
        product.setCostPrice(updatedProduct.getCostPrice());
        product.setSellingPrice(updatedProduct.getSellingPrice());
        product.setQuantityInStock(updatedProduct.getQuantityInStock());
        product.setExpiryDate(updatedProduct.getExpiryDate());

        // Save the updated product to the database
        return productRepository.save(product);

    }

    public Product updateExpiryDateAndQuantityInStock(String id, LocalDate expiryDate, int quantityInStock) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        product.setExpiryDate(expiryDate);
        product.setQuantityInStock(product.getQuantityInStock() + quantityInStock);
        product.setVersion(product.getVersion() + 1);
        return productRepository.save(product);

    }

    @Autowired
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }


    public boolean deleteById(String productId){
        try{
            productRepository.deleteById(productId);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
