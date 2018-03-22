package com.ElegantDevelopment.iacWebshop.controller;


import com.ElegantDevelopment.iacWebshop.exception.ResourceNotFoundException;
import com.ElegantDevelopment.iacWebshop.model.Category;
import com.ElegantDevelopment.iacWebshop.model.Product;
import com.ElegantDevelopment.iacWebshop.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rest/product")
public class ProductController {
    @Autowired
    ProductRepo productRepo;

    // Get All Notes
    @GetMapping("")
    public List<Product> getAllProducts() {
        System.out.println(productRepo.findAll());
        return productRepo.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    @PostMapping("")
    public Product createProduct(@Valid @

            RequestBody Product product) {
        List<Category> Lijstje = new ArrayList<Category>();
        Category cat1 = new Category();
        cat1.setName("Nieuw");
        Lijstje.add(cat1);
        product.setCategories(Lijstje);
        return productRepo.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable(value = "id") Long productId,
                                   @Valid @RequestBody Product productDetails) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());

        product.setImage(productDetails.getImage());
        product.setCategories(productDetails.getCategories());
        product.setOrderLine(productDetails.getOrderLine());
        product.setSale(productDetails.getSale());

        Product updatedProduct = productRepo.save(product);
        return updatedProduct;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        productRepo.delete(product);

        return ResponseEntity.ok().build();
    }
}
