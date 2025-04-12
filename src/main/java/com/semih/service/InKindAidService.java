package com.semih.service;

import com.semih.dto.request.InKindAidRequest;
import com.semih.dto.response.CategoryResponse;
import com.semih.dto.response.InKindAidResponse;
import com.semih.model.Category;
import com.semih.model.InKindAid;
import com.semih.model.Inventory;
import com.semih.repository.InKindAidRepository;
import com.semih.utils.HelperUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InKindAidService {

    private final InKindAidRepository inKindAidRepository;
    private final InventoryService inventoryService;
    private final FamilyService familyService;
    private final CategoryService categoryService;
    private final HelperUtils helperUtils;

    public InKindAidService(InKindAidRepository inKindAidRepository, InventoryService inventoryService, FamilyService familyService, CategoryService categoryService, HelperUtils helperUtils) {
        this.inKindAidRepository = inKindAidRepository;
        this.inventoryService = inventoryService;
        this.familyService = familyService;
        this.categoryService = categoryService;
        this.helperUtils = helperUtils;
    }

    private InKindAid mapDtoToEntity(InKindAidRequest inKindAidRequest) {
        return new InKindAid(
                inKindAidRequest.getQuantity(),
                inKindAidRequest.getPeriod(),
                inKindAidRequest.getDuration(),
                categoryService.getCategoryByItemName(inKindAidRequest.getCategory().getItemName()),
                familyService.getFamilyByName(inKindAidRequest.getFamilyName())
        );
    }

    private InKindAidResponse mapEntityToResponse(InKindAid inKindAid) {
        return new InKindAidResponse(
                inKindAid.getId(),
                inKindAid.getCreatedDate(),
                inKindAid.getModifiedDate(),
                inKindAid.getFamily().getFamilyName(),
                mapCategoryToCategoryResponse(inKindAid.getCategory()),
                inKindAid.getQuantity(),
                inKindAid.getPeriod(),
                inKindAid.getDuration(),
                inKindAid.getTotalDistributedQuantity(),
                inKindAid.getStartingDate(),
                inKindAid.getEndingDate()
        );
    }

    private CategoryResponse mapCategoryToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getItemName(),
                category.getUnit()
        );
    }

    private Inventory mapEntityToInventory(InKindAid inKindAid, int totalQuantity) {
        return new Inventory(
                inKindAid.getCategory().getItemName(),
                totalQuantity,
                inKindAid.getCategory().getUnit()
        );
    }

    @Transactional
    public void saveInKindAid(InKindAidRequest inKindAidRequest) {
        InKindAid inKindAid = mapDtoToEntity(inKindAidRequest);
        Integer incomingAmount = inKindAid.getQuantity();
        Integer duration = inKindAid.getDuration();

        Integer totalIncomingQuantity = helperUtils.calculateTotalDonatedQuantity(incomingAmount, duration);

        // Envanter al
        Inventory inventory = inventoryService.getInventory(inKindAid.getCategory().getItemName());
        Integer totalQuantity = inventory.getQuantity();

        int difference = totalQuantity - totalIncomingQuantity;

        if (difference > 0) {
            inventory.setQuantity(difference);
            inventoryService.updateInventory(inventory);
        } else if (difference == 0) {
            inventoryService.deleteInventory(inventory);
        } else {
            throw new RuntimeException("Yetersiz Envanter!!!");
        }

        inKindAid.setTotalDistributedQuantity(totalIncomingQuantity);

        LocalDate startingDate = LocalDate.now();
        inKindAid.setStartingDate(startingDate);

        String period = String.valueOf(inKindAid.getPeriod());
        LocalDate endingDate = helperUtils.determineEndingDate(period, duration, startingDate);
        inKindAid.setEndingDate(endingDate);

        inKindAidRepository.save(inKindAid);
    }

    public List<InKindAidResponse> getAllInKindAid() {
        return inKindAidRepository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public void updateInKindAidById(Long id, InKindAidRequest inKindAidRequest) {
        InKindAid inKindAid = inKindAidRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Bulunamadi!!!"));
        Integer totalDistributedQuantity = inKindAid.getQuantity();

        Integer incomingAmount = inKindAidRequest.getQuantity();
        Integer incomingDuration = inKindAidRequest.getDuration();
        Integer totalIncomingQuantity = helperUtils.calculateTotalDonatedQuantity(incomingAmount, incomingDuration);

        Inventory inventory = inventoryService.getInventory(inKindAid.getCategory().getItemName());
        int totalQuantity = inventory.getQuantity();

        int difference = totalDistributedQuantity - totalIncomingQuantity;

        if (totalQuantity + difference > 0) {
            inventory.setQuantity(totalQuantity + difference);
            inventoryService.updateInventory(inventory);
        } else if (difference == 0) {
            inventoryService.deleteInventory(inventory);
        } else {
            throw new RuntimeException("Yetersiz Envanter!!!");
        }

        InKindAid updatedInKindAid = mapDtoToEntity(inKindAidRequest);
        updatedInKindAid.setId(inKindAid.getId());

        //toplam parayı ekle
        updatedInKindAid.setTotalDistributedQuantity(totalIncomingQuantity);

        // başlangıc ve bitiş tarihlerini guncelle
        updatedInKindAid.setCreatedDate(inKindAid.getCreatedDate());
        updatedInKindAid.setStartingDate(inKindAid.getStartingDate());
        LocalDate endingDate = helperUtils.determineEndingDate(String.valueOf(updatedInKindAid.getPeriod()), incomingDuration, inKindAid.getStartingDate());
        updatedInKindAid.setEndingDate(endingDate);

        inKindAidRepository.save(updatedInKindAid);
    }

    public void deleteInKindAidById(Long id) {
        InKindAid inKindAid = inKindAidRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bulunamadi"));

        Integer totalDistributedQuantity = inKindAid.getTotalDistributedQuantity();

        Inventory inventory = inventoryService.getInventory(inKindAid.getCategory().getItemName());

        // eğer  null değilse  bağışlanan mıktarı gerı alıp uzerıne ekle
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + totalDistributedQuantity);
        }
        // eğer null ise yeni bir nesne olusturup bagıslanan degerın bazı bılgılerını atıyorum
        else {
            inventory = mapEntityToInventory(inKindAid, totalDistributedQuantity);
        }

        inventoryService.updateInventory(inventory);
        inKindAidRepository.delete(inKindAid);
    }


}
