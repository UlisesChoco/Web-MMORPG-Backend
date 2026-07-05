package com.chocolatada.inventory;

import com.chocolatada.inventory.service.jpa.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryApplicationRunner implements CommandLineRunner {
    private final IItemService itemService;

    @Override
    public void run(String... args) throws Exception {
        itemService.loadItemsFromResources();
    }
}
