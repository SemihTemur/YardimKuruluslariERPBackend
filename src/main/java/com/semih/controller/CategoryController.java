package com.semih.controller;

import com.semih.dto.request.CategoryRequest;
import com.semih.dto.response.CategoryResponse;
import com.semih.dto.response.CategoryUnitItemResponse;
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
    public void saveCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.saveCategory(categoryRequest);
    }

    @GetMapping(path = "/getCategoryList")
    public List<CategoryResponse> getCategoryList() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/getCategoryItemNameAndUnitList")
    public List<CategoryUnitItemResponse> getCategoryUnitItemList() {
        return categoryService.getCategoryUnitItemList();
    }

    @GetMapping(path = "/getCategoryById/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping(path = "/updateCategoryById/{id}")
    public void updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategoryById(id, categoryRequest);
    }

    @DeleteMapping(path = "/deleteCategoryById/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

}
