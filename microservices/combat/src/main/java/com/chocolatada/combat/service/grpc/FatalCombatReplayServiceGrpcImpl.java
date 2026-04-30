package com.chocolatada.combat.service.grpc;

import com.chocolatada.combat.domain.CombatTurn;
import com.chocolatada.combat.dto.FatalCombatReplayDTO;
import com.chocolatada.combat.exception.FatalCombatReplayException;
import com.chocolatada.combat.grpc.CombatTurnGrpc;
import com.chocolatada.combat.grpc.FatalCombatReplayServiceGrpc;
import com.chocolatada.combat.grpc.FatalityEntry;
import com.chocolatada.combat.grpc.GetFatalCombatReplayRequest;
import com.chocolatada.combat.grpc.GetFatalCombatReplayResponse;
import com.chocolatada.combat.grpc.GetRecentFatalitiesRequest;
import com.chocolatada.combat.grpc.GetRecentFatalitiesResponse;
import com.chocolatada.combat.mapper.FatalCombatReplayMapper;
import com.chocolatada.combat.service.jpa.IFatalCombatReplayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FatalCombatReplayServiceGrpcImpl extends FatalCombatReplayServiceGrpc.FatalCombatReplayServiceImplBase {
    private final IFatalCombatReplayService fatalCombatReplayService;
    private final ObjectMapper objectMapper;

    @Override
    public void getFatalCombatReplay(GetFatalCombatReplayRequest request,
                                     StreamObserver<GetFatalCombatReplayResponse> responseObserver) {
        try {
            Long combatId = Long.parseLong(request.getCombatId());
            String turnLogJson = fatalCombatReplayService.getTurnLogByCombatHistoryId(combatId);

            List<CombatTurn> combatTurns = objectMapper.readValue(turnLogJson, new TypeReference<>() {}); //revisar bien despues
            List<CombatTurnGrpc> turnsGrpc = FatalCombatReplayMapper.toCombatTurnGrpcList(combatTurns);

            GetFatalCombatReplayResponse response = GetFatalCombatReplayResponse.newBuilder()
                    .addAllTurns(turnsGrpc)
                    .build();

            log.info("Replay de combate fatal obtenida exitosamente para el combate con ID: " + combatId);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException e) {
            log.warn("ID de combate inválido: " + request.getCombatId());
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("El ID de combate debe ser un número válido")
                    .asRuntimeException());
        } catch (FatalCombatReplayException e) {
            log.warn("No existe replay fatal para el combate con ID: " + request.getCombatId());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (JsonProcessingException e) {
            log.error("Error al parsear el JSON del turnLog para el combate con ID: " + request.getCombatId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error al procesar los datos de la replay")
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno del servidor al obtener la replay del combate con ID: " + request.getCombatId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }

    @Override
    public void getRecentFatalities(GetRecentFatalitiesRequest request,
                                    StreamObserver<GetRecentFatalitiesResponse> responseObserver) {
        try {
            int limit = request.getLimit();
            List<FatalCombatReplayDTO> fatalities = fatalCombatReplayService.getRecentFatalities(limit);
            List<FatalityEntry> fatalityEntries = FatalCombatReplayMapper.toFatalityEntryList(fatalities);

            GetRecentFatalitiesResponse response = GetRecentFatalitiesResponse.newBuilder()
                    .addAllFatalities(fatalityEntries)
                    .build();

            log.info("Muertes recientes obtenidas exitosamente. Limite: " + limit);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno del servidor al obtener las muertes recientes", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }
}
