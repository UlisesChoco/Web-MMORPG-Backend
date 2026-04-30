package com.chocolatada.combat.service.grpc;

import com.chocolatada.combat.domain.Entity;
import com.chocolatada.combat.grpc.AddItemToInventoryRequest;
import com.chocolatada.combat.grpc.GetEquipmentStatBonusRequest;
import com.chocolatada.combat.grpc.GetEquipmentStatBonusResponse;
import com.chocolatada.combat.grpc.InventoryServiceGrpc;
import com.chocolatada.combat.mapper.EntityMapper;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryStubClientServiceGrpcImpl {
    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    public void addItemToInventory(Long playerId, Long itemId) throws StatusRuntimeException {
        AddItemToInventoryRequest request = AddItemToInventoryRequest.newBuilder()
                .setPlayerId(playerId)
                .setItemId(itemId)
                .build();

        inventoryStub.addItemToInventory(request);
    }

    public Entity getEquipmentStatBonus(Long playerId) throws StatusRuntimeException {
        GetEquipmentStatBonusRequest request = GetEquipmentStatBonusRequest.newBuilder()
                .setPlayerId(playerId)
                .build();

        GetEquipmentStatBonusResponse response = inventoryStub.getEquipmentStatBonus(request);

        return EntityMapper.toEntity(response);
    }
}
