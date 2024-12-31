package com.semih.service;

import com.semih.model.Inventory;
import com.semih.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public InventoryService(InventoryRepository inventoryRepository, ModelMapper modelMapper) {
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }

    // nakdi gider ve gelir eklerken
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

    // nakdi gider eklerken ve guncellerken envanterı guncelleme aynı zamanda nakdi gelir guncellerken.
    public void updateInventory(Inventory inKindInventory) {
        inventoryRepository.save(inKindInventory);
    }

    // nakdi gider eklerken eklemeden once kontrol etmemızı saglıyo
    public Inventory getInventory(String productName) {
        return inventoryRepository.findByProductName(productName).orElseThrow(()-> new RuntimeException("Kişi Bulunamadi"));
    }

    // tür silindiğinde o türe bağlı envanterdekı kaydın sılınmesını saglıyo
    public void deleteInventoryByProductName(String productName) {
        inventoryRepository.deleteByProductName(productName);
    }


}
