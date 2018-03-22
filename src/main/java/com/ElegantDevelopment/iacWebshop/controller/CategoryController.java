package com.ElegantDevelopment.iacWebshop.controller;


import com.ElegantDevelopment.iacWebshop.exception.ResourceNotFoundException;
import com.ElegantDevelopment.iacWebshop.model.Category;
import com.ElegantDevelopment.iacWebshop.repository.CategoryRepo;
import com.ElegantDevelopment.iacWebshop.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rest/category")
public class CategoryController {

    @Autowired
    CategoryRepo categoryRepo;

    // Get All Notes
    @GetMapping("")
    public List<Category> getAllCategories() {
        System.out.println(categoryRepo.findAll());
        return categoryRepo.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }

    @PostMapping("")
    public Category createCategory(@Valid @
            RequestBody Category category) {
        return categoryRepo.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable(value = "id") Long categoryId,
                                 @Valid @RequestBody Category categoryDetails) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setImage(categoryDetails.getImage());

        return categoryRepo.save(category);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepo.delete(category);

        return ResponseEntity.ok().build();

    }
}
