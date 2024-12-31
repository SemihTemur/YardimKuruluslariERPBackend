package com.semih.service;

import com.semih.dto.request.CategoryRequest;
import com.semih.dto.response.CategoryResponse;
import com.semih.model.Category;
import com.semih.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final InventoryService inventoryService;

    public CategoryService(CategoryRepository categoryRepository, InventoryService inventoryService) {
        this.categoryRepository = categoryRepository;
        this.inventoryService = inventoryService;
    }

    private Category mapDtoToEntity(CategoryRequest categoryRequest) {
        return new Category(
                categoryRequest.getItemName(),
                categoryRequest.getUnit()
        );
    }

    private CategoryResponse mapEntityToDto(Category category) {
        return new CategoryResponse(
                category.getItemName(),
                category.getUnit()
        );
    }

    public boolean saveCategory(CategoryRequest categoryRequest) {
        try {
            categoryRepository.save(mapDtoToEntity(categoryRequest));
            return true;
        } catch (Exception e) {
            System.err.println("Kategori kaydedilirken bir hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().
                map(this::mapEntityToDto).collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository.findById(id).map(this::mapEntityToDto).
                orElseThrow(() -> new RuntimeException("Kategori bulunamadi"));

    }

    public Category getCategoryByItemName(String itemName) {
        return categoryRepository.findByItemName(itemName);
    }

    public boolean updateCategoryById(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Kategori bulunamadi"));
        category = mapDtoToEntity(categoryRequest);
        category.setId(id);
        categoryRepository.save(category);
        return true;
    }

    public boolean deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            inventoryService.deleteInventoryByProductName(category.get().getItemName());
            categoryRepository.deleteById(id);
            return true;
        }

        return false;
    }


}
