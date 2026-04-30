package com.chocolatada.tower.service.grpc;

import com.chocolatada.tower.dto.TowerEnemyDTO;
import com.chocolatada.tower.exception.InvalidTowerDataException;
import com.chocolatada.tower.mapper.TowerEnemyMapper;
import com.chocolatada.tower.service.jpa.ITowerEnemyService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TowerEnemyServiceGrpcImpl extends com.chocolatada.tower_enemy.grpc.TowerEnemyServiceGrpc.TowerEnemyServiceImplBase {
    private final ITowerEnemyService towerEnemyService;

    @Override
    public void listTowerEnemies(
            com.chocolatada.tower_enemy.grpc.ListTowerEnemiesRequest request,
            StreamObserver<com.chocolatada.tower_enemy.grpc.ListTowerEnemiesResponse> responseObserver
    ) {
        try {
            Long towerId = request.getTowerId();

            List<TowerEnemyDTO> enemies = towerEnemyService.findByTowerId(towerId);
            List<com.chocolatada.tower_enemy.grpc.TowerEnemy> towerEnemies = TowerEnemyMapper.toTowerEnemies(enemies);

            com.chocolatada.tower_enemy.grpc.ListTowerEnemiesResponse response = com.chocolatada.tower_enemy.grpc.ListTowerEnemiesResponse.newBuilder()
                            .addAllEnemies(towerEnemies)
                            .build();

            log.info("Lista de enemigos obtenida exitosamente para la torre con ID: " + towerId + ". Total de enemigos: " + enemies.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidTowerDataException exception) {
            log.error("Error al obtener la lista de enemigos de la torre: " + exception.getMessage());
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(exception.getMessage())
                            .asRuntimeException()
            );
        } catch (Exception exception) {
            log.error("Error interno del servidor al obtener la lista de enemigos de la torre: " + exception.getMessage());
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Error interno del servidor al obtener la lista de enemigos de la torre")
                            .asRuntimeException()
            );
        }
    }
}
