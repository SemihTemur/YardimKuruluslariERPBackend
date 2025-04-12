package com.semih.service;

import com.semih.dto.request.CategoryRequest;
import com.semih.dto.response.CategoryResponse;
import com.semih.dto.response.CategoryUnitItemResponse;
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

    private CategoryResponse mapEntityToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getCreatedDate(),
                category.getModifiedDate(),
                category.getItemName(),
                category.getUnit()
        );
    }

    public void saveCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(mapDtoToEntity(categoryRequest));
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    // Only itemName and unit
    public List<CategoryUnitItemResponse> getCategoryUnitItemList() {
        return categoryRepository.getCategoryUnitItemList();
    }

    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::mapEntityToResponse)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadi"));
    }

    public Category getCategoryByItemName(String itemName) {
        return categoryRepository
                .findByItemName(itemName)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadi"));
    }

    public void updateCategoryById(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı!"));

        // 2. Gelen DTO'yu Entity'ye dönüştür
        Category updatedCategory = mapDtoToEntity(categoryRequest);

        // 3. ID'yi aynı tut ve dönüşümden sonra kayıt güncelle
        updatedCategory.setId(existingCategory.getId());
        updatedCategory.setCreatedDate(existingCategory.getCreatedDate());
        categoryRepository.save(updatedCategory);
    }

    public void deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        inventoryService.deleteInventoryByProductName(category.get().getItemName());
        categoryRepository.deleteById(id);
    }

}
