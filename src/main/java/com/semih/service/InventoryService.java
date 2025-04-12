package com.semih.service;

import com.semih.dto.response.InventoryResponse;
import com.semih.model.Inventory;
import com.semih.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
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

    private InventoryResponse mapEntityToResponse(Inventory inventory){
        return new InventoryResponse(
                inventory.getId(),
                inventory.getProductName(),
                inventory.getQuantity(),
                inventory.getUnit()
        );
    }

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

    public void updateInventory(Inventory updatedInventory) {
        inventoryRepository.save(updatedInventory);
    }

    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

    public Inventory getInventory(String productName) {
        return inventoryRepository.findByProductName(productName).orElse(null);
    }

    public void deleteInventory(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }

    // tür silindiğinde o türe bağlı envanterdekı kaydın sılınmesını saglıyo
    public void deleteInventoryByProductName(String productName) {
        inventoryRepository.deleteByProductName(productName);
    }


}
