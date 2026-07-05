package com.chocolatada.inventory.service.jpa.impl;

import com.chocolatada.inventory.configuration.resources.ItemsResource;
import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.exception.InvalidItemDataException;
import com.chocolatada.inventory.exception.ItemNotFoundException;
import com.chocolatada.inventory.mapper.ItemMapper;
import com.chocolatada.inventory.repository.IItemRepository;
import com.chocolatada.inventory.service.jpa.IItemService;
import com.chocolatada.inventory.validator.ItemResourceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemServiceImpl implements IItemService {

    private final IItemRepository itemRepository;

    private final ItemsResource itemsResource;

    @Override
    public ItemEntity findById(Long itemId) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.error("Objeto no encontrado con ID: {}", itemId);
                    return new ItemNotFoundException("Objeto no encontrado");
                });

        log.info("Objeto encontrado con ID: "+ itemId);
        return item;
    }

    @Override
    public boolean existsById(Long itemId) {
        boolean exists = itemRepository.existsById(itemId);

        log.debug("Objeto existe con ID : "+ itemId);
        return exists;
    }

    @Override
    public List<InventoryItemDTO> findByRequiredLevel(Integer requiredLevel, boolean onlyHigherThan) {
        List<ItemEntity> items;

        if (onlyHigherThan) {
            items = itemRepository.findByRequiredLevelGreaterThanEqual(requiredLevel);
            log.info("Buscando items con nivel >= "+ requiredLevel +". Encontrados: "+ items.size());
        } else {
            items = itemRepository.findByRequiredLevelLessThanEqual(requiredLevel);
            log.info("Buscando items con nivel <= "+ requiredLevel +". Encontrados: "+ items.size());
        }

        return ItemMapper.toInventoryItemDTOs(items);
    }

    @Override
    public List<InventoryItemDTO> findByRequiredLevelAndSlot(Integer requiredLevel, boolean onlyHigherThan, ItemSlot slot) {
        List<ItemEntity> items;

        if (onlyHigherThan) {
            items = itemRepository.findByRequiredLevelGreaterThanEqualAndSlot(requiredLevel, slot);
            log.info("Buscando ítems con nivel >= {} y slot {}. Encontrados: {}", requiredLevel, slot, items.size());
        } else {
            items = itemRepository.findByRequiredLevelLessThanEqualAndSlot(requiredLevel, slot);
            log.info("Buscando ítems con nivel <= {} y slot {}. Encontrados: {}", requiredLevel, slot, items.size());
        }

        return ItemMapper.toInventoryItemDTOs(items);
    }

    @Override
    @Transactional
    public void loadItemsFromResources() throws InvalidItemDataException {
        List<ItemEntity> items = ItemMapper.toItemEntities(itemsResource.getItems());

        for(ItemEntity item : items)
            ItemResourceValidator.validate(item);

        itemRepository.saveAll(items);

        log.info("Cargados "+ items.size() +" items desde recursos.");
    }
}
