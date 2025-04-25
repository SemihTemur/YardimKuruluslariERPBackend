package com.semih.controller;

import com.semih.dto.request.CategoryRequest;
import com.semih.dto.response.CategoryResponse;
import com.semih.dto.response.CategoryUnitItemResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RestResponse<CategoryResponse>> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse savedCategoryResponse = categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(RestResponse.of(savedCategoryResponse), HttpStatus.OK);
    }

    @GetMapping(path = "/getCategoryList")
    public ResponseEntity<RestResponse<List<CategoryResponse>>> getCategoryList() {
        List<CategoryResponse> categoryResponseList = categoryService.getCategoryList();
        return new ResponseEntity<>(RestResponse.of(categoryResponseList), HttpStatus.OK);
    }

    @GetMapping(path = "/getCategoryItemNameAndUnitList")
    public List<CategoryUnitItemResponse> getCategoryUnitItemList() {
        return categoryService.getCategoryUnitItemList();
    }

    @GetMapping(path = "/getCategoryById/{id}")
    public ResponseEntity<RestResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return new ResponseEntity<>(RestResponse.of(categoryResponse), HttpStatus.OK);
    }

    @PutMapping(path = "/updateCategoryById/{id}")
    public ResponseEntity<RestResponse<CategoryResponse>> updateCategoryById(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategoryResponse = categoryService.updateCategoryById(id, categoryRequest);
        return new ResponseEntity<>(RestResponse.of(updatedCategoryResponse), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteCategoryById/{id}")
    public ResponseEntity<RestResponse<CategoryResponse>> deleteCategoryById(@PathVariable Long id) {
        CategoryResponse deletedCategoryResponse = categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(RestResponse.of(deletedCategoryResponse), HttpStatus.OK);
    }

}
