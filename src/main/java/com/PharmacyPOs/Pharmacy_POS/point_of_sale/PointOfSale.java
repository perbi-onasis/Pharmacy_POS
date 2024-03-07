package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import com.PharmacyPOs.Pharmacy_POS.stock_management.Product;
import com.PharmacyPOs.Pharmacy_POS.stock_management.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointOfSale {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public void createSale(String productId, int quantity) {
        Product product = getProductById(productId);

        if (product != null) {
            if (product.getQuantityInStock() >= quantity) {
                product.setQuantityInStock(product.getQuantityInStock() - quantity);

                // Save the updated product to the database
                productRepository.save(product);

                // Create a new product sale
                ProductSale productSale = new ProductSale(productId, product.getName(), quantity, product.getPrice(), new Date().toString());

                // Save the product sale to the database
                productSaleRepository.save(productSale);
            } else {
                throw new RuntimeException("Not enough quantity in stock");
            }
        } else {
            throw new RuntimeException("Product not found");
        }

    }

}
