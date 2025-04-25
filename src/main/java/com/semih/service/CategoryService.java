package com.semih.service;

import com.semih.dto.request.CategoryRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.CategoryResponse;
import com.semih.dto.response.CategoryUnitItemResponse;
import com.semih.exception.NotFoundException;
import com.semih.model.Category;
import com.semih.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final InventoryService inventoryService;

    public CategoryService(CategoryRepository categoryRepository, InventoryService inventoryService) {
        this.categoryRepository = categoryRepository;
        this.inventoryService = inventoryService;
    }

    private Category mapToEntity(CategoryRequest categoryRequest) {
        return new Category(
                categoryRequest.itemName(),
                categoryRequest.unit()
        );
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                new BaseResponse(category.getId(),
                        category.getCreatedDate(),
                        category.getModifiedDate()),
                category.getItemName(),
                category.getUnit()
        );
    }

    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        Category savedCategory = categoryRepository.save(mapToEntity(categoryRequest));
        return mapToResponse(savedCategory);
    }

    public List<CategoryResponse> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Only itemName and unit
    public List<CategoryUnitItemResponse> getCategoryUnitItemList() {
        return categoryRepository.getCategoryUnitItemList();
    }

    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Kategori bulunamadı!!!" + id));
    }

    public Category getCategoryByItemName(String itemName) {
        return categoryRepository
                .findByItemName(itemName)
                .orElseThrow(() -> new NotFoundException("Kategori bulunamadı!!!" + itemName));
    }

    public CategoryResponse updateCategoryById(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı!!!" + id));

        // 2. Gelen DTO'yu Entity'ye dönüştür
        Category updatedCategory = mapToEntity(categoryRequest);

        // 3. ID'yi aynı tut ve dönüşümden sonra kayıt güncelle
        updatedCategory.setId(existingCategory.getId());
        updatedCategory.setCreatedDate(existingCategory.getCreatedDate());
        updatedCategory = categoryRepository.save(updatedCategory);
        return mapToResponse(updatedCategory);
    }

    @Transactional
    public CategoryResponse deleteCategoryById(Long id) {
        Category deletedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kategori bulunamadı!!!" + id));
        inventoryService.deleteInventoryByProductName(deletedCategory.getItemName());
        categoryRepository.delete(deletedCategory);
        return mapToResponse(deletedCategory);
    }

}
