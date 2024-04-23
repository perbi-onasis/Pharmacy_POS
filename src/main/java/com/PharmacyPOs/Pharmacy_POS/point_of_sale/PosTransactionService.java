package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import com.PharmacyPOs.Pharmacy_POS.stock_management.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import com.PharmacyPOs.Pharmacy_POS.stock_management.Product;
import org.w3c.dom.css.Counter;
//import com.PharmacyPOs.Pharmacy_POS.stock_management.ProductService;
import java.time.LocalDate;


import java.util.*;

@Service
public class PosTransactionService {

    @Autowired
    private PosTransactionRepository posTransactionRepository;

    @Autowired
    private ProductService productService;

//    Create POS Transaction service
/*    public PosTransaction createPosTransaction(List<Map<String, Object>> items) {

        String id = UUID.randomUUID().toString();
        List<Map<String, Object>> updatedItems = new ArrayList<>();

        for (Map<String, Object> item : items){
            String productId = (String) item.get("id");
            int quantity = (int) item.get("quantity");
            double sellingPrice = (double) item.get("sellingPrice"); // Fetch the selling price

            Product product = productService.findById(productId);



            if (product == null) {
                throw new IllegalArgumentException("Product not found with ID: " + productId);
            }

            if (product.getQuantityInStock() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product with ID: " + productId);
            }


            // Update the product quantity
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
            productService.update(productId, product);



            // Add the item to the list of updated items
            updatedItems.add(item);

        }

        // Save the PosTransaction to the database
        PosTransaction posTransaction = new PosTransaction(id, LocalDate.now(), updatedItems);
        return posTransactionRepository.save(posTransaction);

    }

    */

    public PosTransaction createPosTransaction(List<Map<String, Object>> items) {

        String id = generateShortID();
        List<Map<String, Object>>  updatedItems = new ArrayList<>();

        for (Map<String, Object> item : items){
            String productId = (String) item.get("id");
            int quantity = (int) item.get("quantity");
            double sellingPrice = ((Number) item.get("sellingPrice")).doubleValue(); // Correct way to handle conversion
            double totalAmount = ((Number) item.get("totalAmount")).doubleValue(); // Correct way to handle conversion

            Product product = productService.findById(productId);

            // Create a PosTransactionItem object and populate it with the details
            Map<String, Object> transactionItem = new HashMap<>();
            transactionItem.put("productId", productId);
            transactionItem.put("productName", product.getproductName());
            transactionItem.put("quantity", quantity);
            transactionItem.put("sellingPrice", sellingPrice);
            transactionItem.put("totalAmount", totalAmount);

            if (product.getQuantityInStock() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product with ID: " + productId);
            }

            // Update the product quantity
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
            productService.update(productId, product);

            // Add the item to the list of updated items
            updatedItems.add(transactionItem);
        }


        // Save the PosTransaction to the database
        PosTransaction posTransaction = new PosTransaction(id, LocalDate.now(), updatedItems);
        posTransaction = posTransactionRepository.save(posTransaction);

        // Set the id in the response object
//        posTransaction.setId(id);

        return posTransaction;
    }

    // Method to generate a 6-character alphanumeric ID
    private String generateShortID() {
        String id = UUID.randomUUID().toString().replace("-", ""); // Generate UUID
        return id.substring(0, 6).toUpperCase(); // Return the first 6 characters
    }


    ////    return POS Trarnsactions by Id
    public PosTransaction findPosTransactionById(UUID id) {
       Optional<PosTransaction> optionalPosTransaction = posTransactionRepository.findById(id);
        return optionalPosTransaction.orElse(null);
    }

    ////    return all POS Transactions
    public List<PosTransaction> findAllPosTransactions() {
         return posTransactionRepository.findAll();
    }

    // Method to fetch total sales volume
    public double fetchSalesVolume() {
        List<PosTransaction> transactions = posTransactionRepository.findAll();
        double totalSalesVolume = 0;
        for (PosTransaction transaction : transactions) {
            for (Map<String, Object> item : transaction.getItems()) {
                Object priceObj = item.get("price");
            if (priceObj != null && priceObj instanceof Double) {
                double price = (double) priceObj;
                int quantity = (int) item.get("quantity");
                totalSalesVolume += price * quantity;
            } else {
                // Log or handle the case where the price is null or not a double
            }
            }
        }
        return totalSalesVolume;
    }

    // Method to fetch total items sold
    public int fetchItemsSold() {
        List<PosTransaction> transactions = posTransactionRepository.findAll();
        int totalItemsSold = 0;
        for (PosTransaction transaction : transactions) {
            for (Map<String, Object> item : transaction.getItems()) {
                int quantity = (int) item.get("quantity");
                totalItemsSold += quantity;
            }
        }
        return totalItemsSold;
    }

    // Method to fetch total orders count
    public long fetchTotalOrdersCount() {
        return posTransactionRepository.count();
    }

}

