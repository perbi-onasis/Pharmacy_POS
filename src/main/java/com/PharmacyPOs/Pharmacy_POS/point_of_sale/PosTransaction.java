package com.PharmacyPOs.Pharmacy_POS.point_of_sale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Document(collection = "pos_transactions")
public class PosTransaction {

    @Id
    private String id;
    private LocalDate date;
    private List<Map<String, Object>> items;

    public PosTransaction() {
    }

    public PosTransaction(String id, LocalDate date, List<Map<String, Object>> items) {
        this.id = id;
        this.date = date;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

}
