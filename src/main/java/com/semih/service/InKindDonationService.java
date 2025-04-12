package com.semih.service;

import com.semih.dto.request.InKindDonationRequest;
import com.semih.dto.response.CategoryUnitItemResponse;
import com.semih.dto.response.InKindDonationResponse;
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

    private InKindDonation mapDtoToEntity(InKindDonationRequest inKindDonationRequest) {
        return new InKindDonation(
                donorService.getDonorByFirstNameAndLastName(inKindDonationRequest.getDonorFirstName(), inKindDonationRequest.getDonorLastName()),
                categoryService.getCategoryByItemName(inKindDonationRequest.getCategory().getItemName()),
                inKindDonationRequest.getQuantity()
        );
    }

    private InKindDonationResponse mapEntityToResponse(InKindDonation inKindDonation) {
        return new InKindDonationResponse(
                inKindDonation.getId(),
                inKindDonation.getCreatedDate(),
                inKindDonation.getModifiedDate(),
                inKindDonation.getDonor().getFirstName(),
                inKindDonation.getDonor().getLastName(),
                mapCategoryToCategoryResponse(inKindDonation.getCategory()),
                inKindDonation.getQuantity()
        );
    }

    private CategoryUnitItemResponse mapCategoryToCategoryResponse(Category category) {
        return new CategoryUnitItemResponse(
                category.getItemName(),
                category.getUnit()
        );
    }

    private Inventory mapEntityToInventory(InKindDonation inKindDonation) {
        return new Inventory(
                inKindDonation.getCategory().getItemName(),
                inKindDonation.getQuantity(),
                inKindDonation.getCategory().getUnit()
        );
    }

    public void saveInKindDonation(InKindDonationRequest inKindDonationRequest) {
        InKindDonation inKindDonation = mapDtoToEntity(inKindDonationRequest);
        Inventory inventory = mapEntityToInventory(inKindDonation);
        inventoryService.saveInventory(inventory);
        inKindDonationRepository.save(inKindDonation);
    }

    public List<InKindDonationResponse> getInKindDonations() {
        return inKindDonationRepository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public void updateInKindDonationById(Long id, InKindDonationRequest inKindDonationRequest) {
        InKindDonation savedInKindDonation = inKindDonationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bağış Bulunamadi"));

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
        Integer receivedQuantity = inKindDonationRequest.getQuantity();

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
                Inventory newInventory = mapEntityToInventory(savedInKindDonation);
                newInventory.setQuantity(updatedQuantity);
                inventoryService.saveInventory(newInventory);
            }

        } else if (currentQuantity + difference == 0) {
            inventoryService.deleteInventory(inventory);
        } else {
            throw new RuntimeException("Envanter yok");
        }

        InKindDonation updatedInKindDonation = mapDtoToEntity(inKindDonationRequest);
        updatedInKindDonation.setQuantity(receivedQuantity);
        updatedInKindDonation.setId(id);
        updatedInKindDonation.setCreatedDate(savedInKindDonation.getCreatedDate());
        inKindDonationRepository.save(updatedInKindDonation);

    }

    @Transactional
    public void deleteInKindDonationById(Long id) {
        InKindDonation inKindDonation = inKindDonationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bulunamadi"));

        String itemName = inKindDonation.getCategory().getItemName();

        // Envanter nesnesini al.
        Inventory inventory = inventoryService.getInventory(itemName);

        // eğer null değilse envanterdeki o item 0 ise sil degılse guncelle
        if (inventory != null) {
            Integer totalQuantity = inventory.getQuantity();

            // Miktarı al
            Integer quantity = inKindDonation.getQuantity();

            // değeri güncelle
            int updatedQuantity = totalQuantity - quantity;

            // Envanteri güncelle
            inventory.setQuantity(updatedQuantity);

            // eğer son değer 0 ise
            if (updatedQuantity == 0) {
                // Envanterdende sil dıyorum.
                inventoryService.deleteInventory(inventory);
            } else if (updatedQuantity < 0) {
                throw new RuntimeException("Silinemedi");
            }

            //değer pozitif ise sadece güncelle
            else {
                inventoryService.updateInventory(inventory);
            }
        }

        inKindDonationRepository.delete(inKindDonation);
    }

}
