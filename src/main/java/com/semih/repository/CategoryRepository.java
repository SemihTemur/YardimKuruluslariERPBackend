package com.semih.repository;

import com.semih.dto.response.CategoryUnitItemResponse;
import com.semih.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByItemName(String itemName);

    @Query("SELECT new com.semih.dto.response.CategoryUnitItemResponse(c.itemName,c.unit) from Category c")
    List<CategoryUnitItemResponse> getCategoryUnitItemList();

}
