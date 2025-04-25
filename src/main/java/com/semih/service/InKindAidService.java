package com.semih.service;

import com.semih.dto.request.InKindAidRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.CategoryResponse;
import com.semih.dto.response.InKindAidResponse;
import com.semih.exception.InsufficientInventoryException;
import com.semih.exception.NotFoundException;
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

    private InKindAid mapToEntity(InKindAidRequest inKindAidRequest) {
        return new InKindAid(
                inKindAidRequest.quantity(),
                inKindAidRequest.period(),
                inKindAidRequest.duration(),
                categoryService.getCategoryByItemName(inKindAidRequest.category().itemName()),
                familyService.getFamilyByName(inKindAidRequest.familyName())
        );
    }

    private InKindAidResponse mapToResponse(InKindAid inKindAid) {
        return new InKindAidResponse(
                new BaseResponse(inKindAid.getId(),
                        inKindAid.getCreatedDate(),
                        inKindAid.getModifiedDate()),
                inKindAid.getFamily().getFamilyName(),
                mapToCategoryResponse(inKindAid.getCategory()),
                inKindAid.getQuantity(),
                inKindAid.getPeriod(),
                inKindAid.getDuration(),
                inKindAid.getTotalDistributedQuantity(),
                inKindAid.getStartingDate(),
                inKindAid.getEndingDate()
        );
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return new CategoryResponse(
                new BaseResponse(category.getId(),
                        category.getCreatedDate(),
                        category.getModifiedDate()),
                category.getItemName(),
                category.getUnit()
        );
    }

    private Inventory mapToInventory(InKindAid inKindAid, int totalQuantity) {
        return new Inventory(
                inKindAid.getCategory().getItemName(),
                totalQuantity,
                inKindAid.getCategory().getUnit()
        );
    }

    @Transactional
    public InKindAidResponse saveInKindAid(InKindAidRequest inKindAidRequest) {
        InKindAid inKindAid = mapToEntity(inKindAidRequest);
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
            throw new InsufficientInventoryException("İşlem gerçekleştirilemedi. Envanterdeki ürün miktarı yetersiz!!!");
        }

        inKindAid.setTotalDistributedQuantity(totalIncomingQuantity);

        LocalDate startingDate = LocalDate.now();
        inKindAid.setStartingDate(startingDate);

        String period = String.valueOf(inKindAid.getPeriod());
        LocalDate endingDate = helperUtils.determineEndingDate(period, duration, startingDate);
        inKindAid.setEndingDate(endingDate);

        InKindAid savedInKindAid = inKindAidRepository.save(inKindAid);
        return mapToResponse(savedInKindAid);
    }

    public List<InKindAidResponse> getInKindAidList() {
        return inKindAidRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InKindAidResponse updateInKindAidById(Long id, InKindAidRequest inKindAidRequest) {
        InKindAid inKindAid = inKindAidRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Ayni bağış bulunamadı!!!" + id));
        Integer totalDistributedQuantity = inKindAid.getQuantity();

        Integer incomingAmount = inKindAidRequest.quantity();
        Integer incomingDuration = inKindAidRequest.duration();
        Integer totalIncomingQuantity = helperUtils.calculateTotalDonatedQuantity(incomingAmount, incomingDuration);

        Inventory inventory = inventoryService.getInventory(inKindAid.getCategory().getItemName());

        // eğer null gelirse demekki envanterden ürün silinmiş tamamı bağışlandığı için.
        // bu yüzden gelen değere göre güncelleme yapıcağım
        if (inventory == null && totalDistributedQuantity <= totalIncomingQuantity) {
            throw new InsufficientInventoryException("İşlem gerçekleştirilemedi. " +
                    "Envanterdeki ürün miktarı yetersiz!!!");

        }

        int totalQuantity = inventory.getQuantity();

        int difference = totalDistributedQuantity - totalIncomingQuantity;

        if (totalQuantity + difference > 0) {
            inventory.setQuantity(totalQuantity + difference);
            inventoryService.updateInventory(inventory);
        } else if (difference == 0) {
            inventoryService.deleteInventory(inventory);
        } else {
            throw new InsufficientInventoryException("İşlem gerçekleştirilemedi. Envanterdeki ürün miktarı yetersiz!!!");
        }

        InKindAid updatedInKindAid = mapToEntity(inKindAidRequest);
        updatedInKindAid.setId(inKindAid.getId());

        //toplam parayı ekle
        updatedInKindAid.setTotalDistributedQuantity(totalIncomingQuantity);

        // başlangıc ve bitiş tarihlerini guncelle
        updatedInKindAid.setCreatedDate(inKindAid.getCreatedDate());
        updatedInKindAid.setStartingDate(inKindAid.getStartingDate());
        LocalDate endingDate = helperUtils
                .determineEndingDate(String.valueOf(updatedInKindAid.getPeriod()),
                        incomingDuration, inKindAid.getStartingDate());
        updatedInKindAid.setEndingDate(endingDate);

        updatedInKindAid = inKindAidRepository.save(updatedInKindAid);
        return mapToResponse(updatedInKindAid);
    }

    public InKindAidResponse deleteInKindAidById(Long id) {
        InKindAid deletedInKindAid = inKindAidRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ayni yardım bulunamadı!!!" + id));

        Integer totalDistributedQuantity = deletedInKindAid.getTotalDistributedQuantity();

        Inventory inventory = inventoryService.getInventory(deletedInKindAid.getCategory().getItemName());

        // eğer  null değilse  bağışlanan mıktarı gerı alıp uzerıne ekle
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + totalDistributedQuantity);
        }
        // eğer null ise yeni bir nesne olusturup bagıslanan degerın bazı bılgılerını atıyorum
        else {
            inventory = mapToInventory(deletedInKindAid, totalDistributedQuantity);
        }

        inventoryService.updateInventory(inventory);
        inKindAidRepository.delete(deletedInKindAid);
        return mapToResponse(deletedInKindAid);
    }


}
