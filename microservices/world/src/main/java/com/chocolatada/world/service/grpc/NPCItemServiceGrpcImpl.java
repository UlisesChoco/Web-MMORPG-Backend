package com.chocolatada.world.service.grpc;

import com.chocolatada.world.dto.NPCItemDTO;
import com.chocolatada.world.mapper.NPCMapper;
import com.chocolatada.world.service.jpa.INPCItemService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NPCItemServiceGrpcImpl extends com.chocolatada.world.grpc.NPCItemServiceGrpc.NPCItemServiceImplBase {

    private final INPCItemService npcItemService;

    @Override
    public void listNPCVendorItems(com.chocolatada.world.grpc.ListNPCVendorItemsRequest request,
                                   StreamObserver<com.chocolatada.world.grpc.ListNPCVendorItemsResponse> responseObserver) {
        try {
            long npcId = request.getNpcId();
            log.info("Solicitud recibida: obtener items del NPC vendedor con ID: " + npcId);

            List<NPCItemDTO> items = npcItemService.findByNpcId(npcId);

            com.chocolatada.world.grpc.ListNPCVendorItemsResponse response = com.chocolatada.world.grpc.ListNPCVendorItemsResponse.newBuilder()
                    .addAllItems(NPCMapper.toGrpcNPCItems(items))
                    .build();

            log.info("Items del NPC vendedor enviados correctamente. NPC ID: " + npcId + ", Total items: " + items.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno al obtener items del NPC vendedor con ID " + request.getNpcId() + ": " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al obtener los items del NPC vendedor")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
