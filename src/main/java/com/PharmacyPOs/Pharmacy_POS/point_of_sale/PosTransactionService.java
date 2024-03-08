package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import com.PharmacyPOs.Pharmacy_POS.stock_management.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.PharmacyPOs.Pharmacy_POS.stock_management.Product;
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
    public PosTransaction createPosTransaction(List<Map<String, Object>> items) {

        String id = UUID.randomUUID().toString();
        List<Map<String, Object>> updatedItems = new ArrayList<>();

        for (Map<String, Object> item : items){
            String productId = (String) item.get("productId");
            int quantity = (int) item.get("quantity");

            Product product = productService.findById(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not found with ID: " + productId);
            }

            if (product.getQuantityInStock() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product with ID: " + productId);
            }


            updatedItems.add(item);

            product.setQuantityInStock(product.getQuantityInStock() - quantity);
            productService.update(productId, product);

        }

        PosTransaction posTransaction = new PosTransaction(id, LocalDate.now(), updatedItems);
        return posTransactionRepository.save(posTransaction);

    }

     ////    return POS Transactions by Id
    public PosTransaction findPosTransactionById(UUID id) {
       Optional<PosTransaction> optionalPosTransaction = posTransactionRepository.findById(id);
        return optionalPosTransaction.orElse(null);
    }

    ////    return all POS Transactions
    public List<PosTransaction> findAllPosTransactions() {
         return posTransactionRepository.findAll();
    }

}
