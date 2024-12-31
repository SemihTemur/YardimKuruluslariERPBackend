package com.semih.controller;

import com.semih.dto.request.CategoryRequest;
import com.semih.dto.response.CategoryResponse;
import com.semih.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/saveCategory")
    public String saveCategory(@RequestBody CategoryRequest categoryRequest) {
        if(categoryService.saveCategory(categoryRequest)) {
            return "success";
        }
        return "fail";
    }

    @GetMapping(path="/getCategoryList")
    public List<CategoryResponse> getCategoryList() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path="/getCategoryById/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping(path="/updateCategoryById/{id}")
    public String updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        if(categoryService.updateCategoryById(id, categoryRequest)) {
            return "success";
        }
        return "fail";
    }

    @DeleteMapping(path="/deleteCategoryById/{id}")
    public String deleteCategoryById(@PathVariable Long id) {
        if(categoryService.deleteCategoryById(id)) {
            return "success";
        }
        return "fail";
    }

}
