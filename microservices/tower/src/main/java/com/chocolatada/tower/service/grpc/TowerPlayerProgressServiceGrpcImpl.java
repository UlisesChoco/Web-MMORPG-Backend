package com.chocolatada.tower.service.grpc;

import com.chocolatada.tower.dto.TowerDTO;
import com.chocolatada.tower.entity.TowerEntity;
import com.chocolatada.tower.exception.InvalidTowerDataException;
import com.chocolatada.tower.mapper.TowerMapper;
import com.chocolatada.tower.mapper.TowerPlayerProgressMapper;
import com.chocolatada.tower.service.jpa.ITowerPlayerProgressService;
import com.chocolatada.tower.service.jpa.ITowerService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TowerPlayerProgressServiceGrpcImpl extends com.chocolatada.tower_player_progress.grpc.TowerPlayerProgressServiceGrpc.TowerPlayerProgressServiceImplBase {
    private final ITowerPlayerProgressService towerPlayerProgressService;
    private final ITowerService towerService;

    @Override
    public void getPlayerTowerProgress(
            com.chocolatada.tower_player_progress.grpc.GetPlayerTowerProgressRequest request,
            StreamObserver<com.chocolatada.tower_player_progress.grpc.GetPlayerTowerProgressResponse> responseObserver
    ) {
        try {
            Long playerId = request.getPlayerId();

            TowerDTO towerDTO = towerPlayerProgressService.getMaxFloorReached(playerId);

            com.chocolatada.tower_player_progress.grpc.TowerPlayerProgress progress = TowerPlayerProgressMapper.toTowerRPC(towerDTO);

            com.chocolatada.tower_player_progress.grpc.GetPlayerTowerProgressResponse response = com.chocolatada.tower_player_progress.grpc.GetPlayerTowerProgressResponse.newBuilder()
                    .setProgress(progress)
                    .build();

            log.info("Progreso de torre obtenido exitosamente para el jugador con ID: " + playerId);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidTowerDataException exception) {
            log.error("Error al obtener el progreso de torre del jugador: " + exception.getMessage());
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(exception.getMessage())
                            .asRuntimeException()
            );
        } catch (Exception exception) {
            log.error("Error interno del servidor al obtener el progreso de torre: " + exception.getMessage());
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Error interno del servidor al obtener el progreso de torre")
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void registerPlayerTowerProgress(
            com.chocolatada.tower_player_progress.grpc.RegisterPlayerTowerProgressRequest request,
            StreamObserver<com.chocolatada.tower_player_progress.grpc.RegisterPlayerTowerProgressResponse> responseObserver
    ) {
        try {
            Long towerId = request.getTowerId();
            Long playerId = request.getPlayerId();

            TowerDTO towerDTO = towerService.findById(towerId);
            TowerEntity towerEntity = TowerMapper.toEntity(towerDTO);
            towerEntity.setId(towerId);
            towerPlayerProgressService.registerPlayerProgress(towerEntity, playerId);

            com.chocolatada.tower_player_progress.grpc.RegisterPlayerTowerProgressResponse response = com.chocolatada.tower_player_progress.grpc.RegisterPlayerTowerProgressResponse.newBuilder()
                    .setMessage("Progreso de torre registrado exitosamente para el jugador con ID: " + playerId)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidTowerDataException exception) {
            log.error("Error al registrar el progreso de torre del jugador: " + exception.getMessage());
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(exception.getMessage())
                            .asRuntimeException()
            );
        } catch (Exception exception) {
            log.error("Error interno del servidor al registrar el progreso de torre: " + exception.getMessage());
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Error interno del servidor al registrar el progreso de torre")
                            .asRuntimeException()
            );
        }
    }
}
