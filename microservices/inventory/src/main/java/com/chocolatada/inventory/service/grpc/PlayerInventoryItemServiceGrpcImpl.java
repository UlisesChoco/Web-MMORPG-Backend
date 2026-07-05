package com.chocolatada.inventory.service.grpc;

import com.chocolatada.inventory.dto.EquipmentStatBonusDTO;
import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemEntity;
import com.chocolatada.inventory.exception.InventoryItemNotFoundException;
import com.chocolatada.inventory.exception.ItemNotFoundException;
import com.chocolatada.inventory.mapper.ItemMapper;
import com.chocolatada.inventory.service.jpa.IItemService;
import com.chocolatada.inventory.service.jpa.IPlayerInventoryItemService;
import com.chocolatada.inventory.grpc.InventoryServiceGrpc;
import com.chocolatada.inventory.grpc.AddItemToInventoryRequest;
import com.chocolatada.inventory.grpc.AddItemToInventoryResponse;
import com.chocolatada.inventory.grpc.EquipItemRequest;
import com.chocolatada.inventory.grpc.EquipItemResponse;
import com.chocolatada.inventory.grpc.GetEquippedItemsRequest;
import com.chocolatada.inventory.grpc.GetEquippedItemsResponse;
import com.chocolatada.inventory.grpc.GetEquipmentStatBonusRequest;
import com.chocolatada.inventory.grpc.GetEquipmentStatBonusResponse;
import com.chocolatada.inventory.grpc.GetPlayerInventoryRequest;
import com.chocolatada.inventory.grpc.GetPlayerInventoryResponse;
import com.chocolatada.inventory.grpc.Item;
import com.chocolatada.inventory.grpc.RemoveItemFromInventoryRequest;
import com.chocolatada.inventory.grpc.RemoveItemFromInventoryResponse;
import com.chocolatada.inventory.grpc.UnequipItemRequest;
import com.chocolatada.inventory.grpc.UnequipItemResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PlayerInventoryItemServiceGrpcImpl extends InventoryServiceGrpc.InventoryServiceImplBase {
    private final IPlayerInventoryItemService playerInventoryItemService;

    private final IItemService itemService;

    @Override
    public void getPlayerInventory(GetPlayerInventoryRequest request,
                                   StreamObserver<GetPlayerInventoryResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            log.info("Obteniendo inventario del jugador con ID: " + playerId);
            List<InventoryItemDTO> items = playerInventoryItemService.getPlayerInventory(playerId);
            List<Item> protoItems = ItemMapper.toProtoItems(items);

            GetPlayerInventoryResponse response = GetPlayerInventoryResponse.newBuilder()
                    .setMessage("Success")
                    .addAllItems(protoItems)
                    .build();

            log.info("Inventario obtenido exitosamente para el jugador con ID: " + playerId +
                    ". Total de items: " + items.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno: " + e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getEquippedItems(GetEquippedItemsRequest request,
                                 StreamObserver<GetEquippedItemsResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            log.info("Obteniendo items equipados del jugador con ID: " + playerId);
            List<InventoryItemDTO> equippedItems = playerInventoryItemService.getEquippedItems(playerId);
            List<Item> protoItems = ItemMapper.toProtoItems(equippedItems);

            GetEquippedItemsResponse response = GetEquippedItemsResponse.newBuilder()
                    .setMessage("Success")
                    .addAllItems(protoItems)
                    .build();

            log.info("items equipados obtenidos exitosamente para el jugador con ID: " + playerId +
                    ". Total de items equipados: " + equippedItems.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno: " + e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void addItemToInventory(AddItemToInventoryRequest request,
                                   StreamObserver<AddItemToInventoryResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            Long itemId = request.getItemId();
            log.info("Agregando item con ID: " + itemId + " al inventario del jugador con ID: " + playerId);
            ItemEntity item = itemService.findById(itemId);
            Long inventoryItemId = playerInventoryItemService.addItemToInventory(playerId, item);

            AddItemToInventoryResponse response = AddItemToInventoryResponse.newBuilder()
                    .setMessage("Success")
                    .setInventoryItemId(inventoryItemId)
                    .build();

            log.info("Item con ID: " + itemId + " agregado exitosamente al inventario del jugador con ID: " + playerId +
                    ". ID de item de inventario: " + inventoryItemId);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ItemNotFoundException e) {
            log.error("Item no encontrado: " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno: " + e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void removeItemFromInventory(RemoveItemFromInventoryRequest request,
                                        StreamObserver<RemoveItemFromInventoryResponse> responseObserver) {
        try {
            Long inventoryItemId = request.getInventoryItemId();
            log.info("Eliminando ítem de inventario con ID: " + inventoryItemId);
            playerInventoryItemService.removeItemFromInventory(inventoryItemId);

            RemoveItemFromInventoryResponse response = RemoveItemFromInventoryResponse.newBuilder()
                    .setMessage("Success")
                    .build();

            log.info("Item de inventario con ID: " + inventoryItemId +
                    " eliminado exitosamente");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InventoryItemNotFoundException e) {
            log.error("Item de inventario no encontrado: " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno: " + e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void equipItem(EquipItemRequest request,
                          StreamObserver<EquipItemResponse> responseObserver) {
        try {
            Long inventoryItemId = request.getInventoryItemId();
            Integer playerLevel = (int) request.getPlayerLevel();
            log.info("Equipando ítem de inventario con ID: " + inventoryItemId);

            playerInventoryItemService.equipItem(inventoryItemId, playerLevel);

            EquipItemResponse response = EquipItemResponse.newBuilder()
                    .setMessage("Success")
                    .build();

            log.info("Item de inventario con ID: " + inventoryItemId +
                    " equipado exitosamente");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InventoryItemNotFoundException e) {
            log.error("Item de inventario no encontrado: " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno: " + e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void unequipItem(UnequipItemRequest request,
                            StreamObserver<UnequipItemResponse> responseObserver) {
        try {
            Long inventoryItemId = request.getInventoryItemId();
            log.info("Desequipando item de inventario con ID: " + inventoryItemId);
            playerInventoryItemService.unequipItem(inventoryItemId);

            UnequipItemResponse response = UnequipItemResponse.newBuilder()
                    .setMessage("Success")
                    .build();

            log.info("Item de inventario con ID: " + inventoryItemId +
                    " desequipado exitosamente");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InventoryItemNotFoundException e) {
            log.error("Item de inventario no encontrado: " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno: " + e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getEquipmentStatBonus(GetEquipmentStatBonusRequest request,
                                      StreamObserver<GetEquipmentStatBonusResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            log.info("Obteniendo bonus de estadisticas del equipo del jugador con ID: " + playerId);
            EquipmentStatBonusDTO statBonus = playerInventoryItemService.getEquipmentStatBonus(playerId);

            GetEquipmentStatBonusResponse response = GetEquipmentStatBonusResponse.newBuilder()
                    .setAtk(statBonus.getAtk())
                    .setDef(statBonus.getDef())
                    .setStamina(statBonus.getStamina())
                    .setAccuracy(statBonus.getAccuracy())
                    .setEvasion(statBonus.getEvasion())
                    .setCritRate(statBonus.getCritRate())
                    .setCritDamage(statBonus.getCritDamage())
                    .build();

            log.info("Bonus de estadisticas obtenidos exitosamente para el jugador con ID: " + playerId +
                    ". ATK: " + statBonus.getAtk() +
                    ", DEF: " + statBonus.getDef() +
                    ", Stamina: " + statBonus.getStamina() +
                    ", Accuracy: " + statBonus.getAccuracy() +
                    ", Evasion: " + statBonus.getEvasion() +
                    ", Crit Rate: " + statBonus.getCritRate() +
                    ", Crit Damage: " + statBonus.getCritDamage());

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno: " + e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}
