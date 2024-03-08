package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import com.PharmacyPOs.Pharmacy_POS.stock_management.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class PosTransactionController {
    @Autowired
    private PosTransactionService posTransactionService;


    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<PosTransaction> createPosTransaction(@RequestBody List<Map<String, Object>> items) {
        PosTransaction posTransaction = posTransactionService.createPosTransaction(items);
        return ResponseEntity.ok(posTransaction);
    }

    @GetMapping
    public ResponseEntity<List<PosTransaction>> getAllPosTransactions() {
        List<PosTransaction> posTransactions = posTransactionService.findAllPosTransactions();
        return ResponseEntity.ok(posTransactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosTransaction> getPosTransactionById(@PathVariable("id") String id) {
        PosTransaction posTransaction = posTransactionService.findPosTransactionById(UUID.fromString(id));
        if (posTransaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posTransaction);
    }

}
