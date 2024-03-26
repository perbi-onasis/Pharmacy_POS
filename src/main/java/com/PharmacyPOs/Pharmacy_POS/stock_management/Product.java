package com.PharmacyPOs.Pharmacy_POS.stock_management;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private float costPrice;
    private float sellingPrice;
    private int quantityInStock;
    private LocalDate expiryDate;
    private String categoryId;
    private int version;

    public Product() {

    }


    public Product(String name, float costPrice, float sellingPrice, int quantityInStock, LocalDate expiryDate, String categoryId) {
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantityInStock = quantityInStock;
        this.expiryDate = expiryDate;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


//    public LocalDate getExpiryDate() {
//        return expiryDate;
//    }
//
//    public void setExpiryDate(LocalDate expiryDate) {
//        this.expiryDate = expiryDate;
//    }
//
//    public int getQuantityInStock() {
//        return quantityInStock;
//    }
//
//    public void setQuantityInStock(int quantityInStock) {
//        this.quantityInStock = quantityInStock;
//    }
//
//    public int getVersion() {
//        return version;
//    }
//
//    public void setVersion(int version) {
//        this.version = version;
//    }
}
