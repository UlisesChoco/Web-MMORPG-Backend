package com.chocolatada.inventory.service.grpc;

import com.chocolatada.inventory.dto.InventoryItemDTO;
import com.chocolatada.inventory.entity.ItemSlot;
import com.chocolatada.inventory.mapper.ItemMapper;
import com.chocolatada.inventory.service.jpa.IItemService;
import com.chocolatada.inventory.grpc.ItemServiceGrpc;
import com.chocolatada.inventory.grpc.GetItemsByRequiredLevelRequest;
import com.chocolatada.inventory.grpc.GetItemsByRequiredLevelResponse;
import com.chocolatada.inventory.grpc.GetItemsByRequiredLevelAndSlotRequest;
import com.chocolatada.inventory.grpc.GetItemsByRequiredLevelAndSlotResponse;
import com.chocolatada.inventory.grpc.SlotType;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItemServiceGrpcImpl extends ItemServiceGrpc.ItemServiceImplBase {
    private final IItemService itemService;

    @Override
    public void getItemsByRequiredLevel(GetItemsByRequiredLevelRequest request,
                                        StreamObserver<GetItemsByRequiredLevelResponse> responseObserver) {
        try {
            int requiredLevel = (int) request.getRequiredLevel();
            boolean onlyHigherThan = request.getOnlyHigherThan();

            List<InventoryItemDTO> items = itemService.findByRequiredLevel(requiredLevel, onlyHigherThan);

            GetItemsByRequiredLevelResponse response = GetItemsByRequiredLevelResponse.newBuilder()
                    .addAllItems(ItemMapper.toProtoItems(items))
                    .setMessage("Success")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno del servidor: "+ e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getItemsByRequiredLevelAndSlot(GetItemsByRequiredLevelAndSlotRequest request,
                                               StreamObserver<GetItemsByRequiredLevelAndSlotResponse> responseObserver) {
        try {
            int requiredLevel = (int) request.getRequiredLevel();
            boolean onlyHigherThan = request.getOnlyHigherThan();
            SlotType protoSlot = request.getSlot();
            ItemSlot slot = ItemMapper.toJavaItemSlot(protoSlot);

            List<InventoryItemDTO> items = itemService.findByRequiredLevelAndSlot(requiredLevel, onlyHigherThan, slot);

            GetItemsByRequiredLevelAndSlotResponse response = GetItemsByRequiredLevelAndSlotResponse.newBuilder()
                    .addAllItems(ItemMapper.toProtoItems(items))
                    .setMessage("Success")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno del servidor: "+ e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}
