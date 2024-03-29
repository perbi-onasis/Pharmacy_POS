package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import com.PharmacyPOs.Pharmacy_POS.stock_management.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping("/pos")
public class PosTransactionController {
    @Autowired
    private PosTransactionService posTransactionService;
    @Autowired
    private ProductService productService;


    @PostMapping("/posTransaction")
    public ResponseEntity<PosTransaction> createPosTransaction(@RequestBody List<Map<String, Object>> items) {
        PosTransaction posTransaction = posTransactionService.createPosTransaction(items);
        return ResponseEntity.ok(posTransaction);
    }

    // Endpoint to fetch all POS transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<PosTransaction>> getAllPosTransactions() {
        List<PosTransaction> posTransactions = posTransactionService.findAllPosTransactions();
        return ResponseEntity.ok(posTransactions);
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
