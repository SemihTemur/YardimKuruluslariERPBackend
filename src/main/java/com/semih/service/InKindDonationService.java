package com.semih.service;

import com.semih.dto.request.InKindDonationRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.CategoryUnitItemResponse;
import com.semih.dto.response.InKindDonationResponse;
import com.semih.exception.NegativeStockException;
import com.semih.exception.NotFoundException;
import com.semih.model.Auditable;
import com.semih.model.Category;
import com.semih.model.InKindDonation;
import com.semih.model.Inventory;
import com.semih.repository.InKindDonationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InKindDonationService {

    private final InKindDonationRepository inKindDonationRepository;
    private final DonorService donorService;
    private final CategoryService categoryService;
    private final InventoryService inventoryService;

    public InKindDonationService(InKindDonationRepository inKindDonationRepository, DonorService donorService, CategoryService categoryService, InventoryService inventoryService) {
        this.inKindDonationRepository = inKindDonationRepository;
        this.donorService = donorService;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
    }

    private InKindDonation mapToEntity(InKindDonationRequest inKindDonationRequest) {
        return new InKindDonation(
                donorService.getDonorByFirstNameAndLastName(inKindDonationRequest.donorFirstName(), inKindDonationRequest.donorLastName()),
                categoryService.getCategoryByItemName(inKindDonationRequest.category().itemName()),
                inKindDonationRequest.quantity()
        );
    }

    private InKindDonationResponse mapToResponse(InKindDonation inKindDonation) {
        return new InKindDonationResponse(
                new BaseResponse(inKindDonation.getId(),
                        inKindDonation.getCreatedDate(),
                        inKindDonation.getModifiedDate()),
                inKindDonation.getDonor().getFirstName(),
                inKindDonation.getDonor().getLastName(),
                mapToCategoryResponse(inKindDonation.getCategory()),
                inKindDonation.getQuantity()
        );
    }

    private CategoryUnitItemResponse mapToCategoryResponse(Category category) {
        return new CategoryUnitItemResponse(
                category.getItemName(),
                category.getUnit()
        );
    }

    private Inventory mapToInventory(InKindDonation inKindDonation) {
        return new Inventory(
                inKindDonation.getCategory().getItemName(),
                inKindDonation.getQuantity(),
                inKindDonation.getCategory().getUnit()
        );
    }

    @Auditable(actionType = "Ekledi", targetEntity = "Ayni Bağış")
    public InKindDonationResponse saveInKindDonation(InKindDonationRequest inKindDonationRequest) {
        InKindDonation inKindDonation = mapToEntity(inKindDonationRequest);
        Inventory inventory = mapToInventory(inKindDonation);
        inventoryService.saveInventory(inventory);
        inKindDonation = inKindDonationRepository.save(inKindDonation);
        return mapToResponse(inKindDonation);
    }

    public List<InKindDonationResponse> getInKindDonationList() {
        return inKindDonationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Ayni Bağış")
    public InKindDonationResponse updateInKindDonationById(Long id, InKindDonationRequest inKindDonationRequest) {
        InKindDonation savedInKindDonation = inKindDonationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ayni bağış bulunamadı!!!"));

        // Envanteri getir
        Inventory inventory = inventoryService.getInventory(savedInKindDonation.getCategory().getItemName());

        int currentQuantity;

        if (inventory != null) {
            // mevcut miktarı al
            currentQuantity = inventory.getQuantity();
        } else {
            currentQuantity = 0;
        }

        // kayıtlı miktarı al
        Integer quantity = savedInKindDonation.getQuantity();

        // gelen miktarı al
        Integer receivedQuantity = inKindDonationRequest.quantity();

        //farkı al
        int difference = receivedQuantity - quantity;

        // güncellenmiş değeri al
        int updatedQuantity = currentQuantity + difference;

        if (updatedQuantity > 0) {
            if (inventory != null) {
                inventory.setQuantity(updatedQuantity);
                inventoryService.updateInventory(inventory);
            }
            // null ise yani önceden silinmiş sonra değerı arttırılmıssa urununn envanterde yenıden olustur dıyorum
            else {
                Inventory newInventory = mapToInventory(savedInKindDonation);
                newInventory.setQuantity(updatedQuantity);
                inventoryService.saveInventory(newInventory);
            }

        } else if (currentQuantity + difference == 0) {
            inventoryService.deleteInventory(inventory);
        } else {
            throw new NotFoundException("Envanter bulunamadı!!!");
        }

        InKindDonation updatedInKindDonation = mapToEntity(inKindDonationRequest);
        updatedInKindDonation.setQuantity(receivedQuantity);
        updatedInKindDonation.setId(id);
        updatedInKindDonation.setCreatedDate(savedInKindDonation.getCreatedDate());
        updatedInKindDonation = inKindDonationRepository.save(updatedInKindDonation);
        return mapToResponse(updatedInKindDonation);
    }


    @Auditable(actionType = "Sildi", targetEntity = "Ayni Bağış")
    @Transactional
    public InKindDonationResponse deleteInKindDonationById(Long id) {
        InKindDonation deletedInKindDonation = inKindDonationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ayni bağış bulunamadi!!!" + id));

        String itemName = deletedInKindDonation.getCategory().getItemName();

        // Envanter nesnesini al.
        Inventory inventory = inventoryService.getInventory(itemName);

        // eğer null değilse envanterdeki o item 0 ise sil degılse guncelle
        if (inventory != null) {
            Integer totalQuantity = inventory.getQuantity();

            // Miktarı al
            Integer quantity = deletedInKindDonation.getQuantity();

            // değeri güncelle
            int updatedQuantity = totalQuantity - quantity;

            // Envanteri güncelle
            inventory.setQuantity(updatedQuantity);

            // eğer son değer 0 ise
            if (updatedQuantity == 0) {
                // Envanterdende sil dıyorum.
                inventoryService.deleteInventory(inventory);
            } else if (updatedQuantity < 0) {
                throw new NegativeStockException("Stoktaki ürün miktarı 0'ın altına düşemez!!!");
            }

            //değer pozitif ise sadece güncelle
            else {
                inventoryService.updateInventory(inventory);
            }
        }

        inKindDonationRepository.delete(deletedInKindDonation);
        return mapToResponse(deletedInKindDonation);
    }

}
