package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import com.PharmacyPOs.Pharmacy_POS.stock_management.Product;
import com.PharmacyPOs.Pharmacy_POS.stock_management.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping
public class PosTransactionController {
    @Autowired
    private PosTransactionService posTransactionService;
    @Autowired
    private ProductService productService;


    @PostMapping("/posTransaction")
    public ResponseEntity<String> createPosTransaction(@RequestBody List<Map<String, Object>> items) {
        // Check if the available quantity in stock is sufficient for each item
    for (Map<String, Object> item : items) {
        String productId = (String) item.get("id");
        int quantity = (int) item.get("quantity");
        Product product = productService.findById(productId);
        if (product == null || product.getQuantityInStock() < quantity) {
            // If the product is not found or available quantity is insufficient, return a specific response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Insufficient stock for product with ID: " + productId);
        }
    }

        try {
            PosTransaction posTransaction = posTransactionService.createPosTransaction(items);
            // Convert PosTransaction object to string representation
            String posTransactionString = posTransaction.toString();
            return ResponseEntity.ok(posTransactionString);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to fetch all POS transactions
    /*@GetMapping("/transactions")
    public ResponseEntity<List<PosTransaction>> getAllPosTransactions() {
        List<PosTransaction> posTransactions = posTransactionService.findAllPosTransactions();
        return ResponseEntity.ok(posTransactions);
    }*/

    @GetMapping("/transactions")
    public ResponseEntity<List<PosTransaction>> getAllTransactions() {
        List<PosTransaction> transactions = posTransactionService.findAllPosTransactions();
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if there are no transactions
        } else {
            return ResponseEntity.ok(transactions); // Return 200 OK with the list of transactions
        }
    }


    // Endpoint to fetch a specific POS transaction by ID
    @GetMapping("transaction/{id}")
    public ResponseEntity<PosTransaction> getPosTransactionById(@PathVariable("id") String id) {
        PosTransaction posTransaction = posTransactionService.findPosTransactionById(UUID.fromString(id));
        if (posTransaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posTransaction);
    }

    // Endpoint to fetch total sales volume
    @GetMapping("/analytics/sales-volume")
    public ResponseEntity<Double> getTotalSalesVolume() {
        double totalSalesVolume = posTransactionService.fetchSalesVolume();
        return ResponseEntity.ok(totalSalesVolume);
    }

    // Endpoint to fetch total items sold
    @GetMapping("/analytics/items-sold")
    public ResponseEntity<Integer> getTotalItemsSold() {
        int totalItemsSold = posTransactionService.fetchItemsSold();
        return ResponseEntity.ok(totalItemsSold);
    }

    // Endpoint to fetch total orders count
    @GetMapping("/analytics/total-orders")
    public ResponseEntity<Long> getTotalOrdersCount() {
        long totalOrdersCount = posTransactionService.fetchTotalOrdersCount();
        return ResponseEntity.ok(totalOrdersCount);
    }

}
