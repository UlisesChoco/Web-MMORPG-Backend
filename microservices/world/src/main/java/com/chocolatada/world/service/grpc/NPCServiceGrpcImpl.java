package com.chocolatada.world.service.grpc;

import com.chocolatada.world.dto.NPCDTO;
import com.chocolatada.world.exception.NPCNotFoundException;
import com.chocolatada.world.mapper.NPCMapper;
import com.chocolatada.world.service.jpa.INPCService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NPCServiceGrpcImpl extends com.chocolatada.world.grpc.NPCServiceGrpc.NPCServiceImplBase {

    private final INPCService npcService;

    @Override
    public void getNPCById(com.chocolatada.world.grpc.GetNPCByIdRequest request,
                           StreamObserver<com.chocolatada.world.grpc.GetNPCResponse> responseObserver) {
        try {
            long npcId = request.getNpcId();
            log.info("Solicitud recibida: obtener NPC con ID: " + npcId);

            NPCDTO npc = npcService.findById(npcId);

            com.chocolatada.world.grpc.GetNPCResponse response = com.chocolatada.world.grpc.GetNPCResponse.newBuilder()
                    .setNpc(NPCMapper.toGrpcNPC(npc))
                    .build();

            log.info("NPC encontrado y enviado correctamente. ID: " + npc.getId() + ", Nombre: " + npc.getName());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NPCNotFoundException e) {
            log.error("NPC no encontrado con ID " + request.getNpcId() + ": " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno al obtener NPC con ID " + request.getNpcId() + ": " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al obtener el NPC")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void listNPCs(com.chocolatada.world.grpc.ListNPCsRequest request,
                         StreamObserver<com.chocolatada.world.grpc.ListNPCsResponse> responseObserver) {
        try {
            log.info("Solicitud recibida: listar todos los NPCs");

            List<NPCDTO> npcs = npcService.findAll();

            com.chocolatada.world.grpc.ListNPCsResponse response = com.chocolatada.world.grpc.ListNPCsResponse.newBuilder()
                    .addAllNpcs(NPCMapper.toGrpcNPCs(npcs))
                    .build();

            log.info("Lista de NPCs enviada correctamente. Total: " + npcs.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno al listar NPCs: " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al listar los NPCs")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
