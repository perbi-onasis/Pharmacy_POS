package com.PharmacyPOs.Pharmacy_POS.stock_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/saveProduct")
    public ResponseEntity<Product> createProduct(
            @RequestParam String name, @RequestParam float costPrice,
            @RequestParam float sellingPrice, @RequestParam int quantityInStock,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate,
            @RequestParam String categoryId)
    {
        Product product = new Product(name, costPrice, sellingPrice, quantityInStock, expiryDate, categoryId);
        Product savedProduct = productService.save(product);

        return ResponseEntity.ok(savedProduct);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Product>> createProducts(@RequestParam("csvFile") MultipartFile csvFile) {

        List<Product> products = new ArrayList<>();
        // Check if the CSV file is empty
        if (csvFile.isEmpty()) {
            return null;
        }

        // Parse the CSV file and create Product objects
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the CSV line by comma
                String[] data = line.split(",");
                if (data.length != 6) {
                    // Skip invalid lines
                    continue;
                }

                // Extract data from CSV line
                String name = data[0];
                float costPrice = Float.parseFloat(data[1]);
                float sellingPrice = Float.parseFloat(data[2]);
                int quantityInStock = Integer.parseInt(data[3]);
                LocalDate expiryDate = LocalDate.parse(data[4]);
                String categoryId = data[5];

                // Create Product object and add to list
                Product product = new Product(name, costPrice, sellingPrice, quantityInStock, expiryDate, categoryId);
                Product savedProduct = productService.save(product);

                System.out.println(savedProduct);
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            // return new ResponseEntity<>("Error processing CSV file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the list of products
        List<Product> savedProducts = productService.saveAll(products);
        return ResponseEntity.ok(savedProducts);

    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable String categoryId) {
        List<Product> products = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    //    Update Product Details
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.update(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Get All Products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") String id){
        boolean deleted = productService.deleteById(id);

        if(deleted){
            return ResponseEntity.ok("Product deleted successfully");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product");
        }
    }

}
