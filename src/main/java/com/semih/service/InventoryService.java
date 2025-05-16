package com.semih.service;

import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.InventoryResponse;
import com.semih.model.Auditable;
import com.semih.model.Inventory;
import com.semih.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return new InventoryResponse(
                new BaseResponse(inventory.getId(),
                        inventory.getCreatedDate(),
                        inventory.getModifiedDate()),
                inventory.getProductName(),
                inventory.getQuantity(),
                inventory.getUnit()
        );
    }

    @Auditable(actionType = "Ekledi", targetEntity = "Envanter")
    public void saveInventory(Inventory inventory) {
        Optional<Inventory> savedInventory = inventoryRepository.findByProductName(inventory.getProductName());

        // Eğer boyle bır kayıt varsa uzeırne ekleme yap
        if (savedInventory.isPresent()) {
            savedInventory.get().setQuantity(inventory.getQuantity() + savedInventory.get().getQuantity());
            inventoryRepository.save(savedInventory.get());
        }
        // yoksa gelen degerı kaydedıp kayıt olustur
        else {
            inventoryRepository.save(inventory);
        }
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Envanter")
    public void updateInventory(Inventory updatedInventory) {
        inventoryRepository.save(updatedInventory);
    }

    public List<InventoryResponse> getInventoryList() {
        return inventoryRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public Inventory getInventory(String productName) {
        return inventoryRepository.findByProductName(productName)
                .orElse(null);
    }

    @Auditable(actionType = "Sildi", targetEntity = "Envanter")
    public void deleteInventory(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }
    
    // tür silindiğinde o türe bağlı envanterdekı kaydın sılınmesını saglıyo
    public void deleteInventoryByProductName(String productName) {
        inventoryRepository.deleteByProductName(productName);
    }


}
