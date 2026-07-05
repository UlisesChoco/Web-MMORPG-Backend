package com.chocolatada.world.service.grpc;

import com.chocolatada.world.dto.EnemyDTO;
import com.chocolatada.world.exception.EnemyNotFoundException;
import com.chocolatada.world.mapper.MapMapper;
import com.chocolatada.world.service.jpa.IEnemyService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnemyServiceGrpcImpl extends com.chocolatada.world.grpc.EnemyServiceGrpc.EnemyServiceImplBase {

    private final IEnemyService enemyService;

    @Override
    public void getEnemyById(com.chocolatada.world.grpc.GetEnemyByIdRequest request,
                             StreamObserver<com.chocolatada.world.grpc.GetEnemyResponse> responseObserver) {
        try {
            long enemyId = request.getEnemyId();
            log.info("Solicitud recibida: obtener enemigo con ID: " + enemyId);

            EnemyDTO enemy = enemyService.findById(enemyId);

            com.chocolatada.world.grpc.GetEnemyResponse response = com.chocolatada.world.grpc.GetEnemyResponse.newBuilder()
                    .setEnemy(MapMapper.toGrpcEnemy(enemy))
                    .build();

            log.info("Enemigo encontrado y enviado correctamente. ID: " + enemy.getId() + ", Nombre: " + enemy.getName());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (EnemyNotFoundException e) {
            log.error("Enemigo no encontrado con ID " + request.getEnemyId() + ": " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno al obtener enemigo con ID " + request.getEnemyId() + ": " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al obtener el enemigo")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
