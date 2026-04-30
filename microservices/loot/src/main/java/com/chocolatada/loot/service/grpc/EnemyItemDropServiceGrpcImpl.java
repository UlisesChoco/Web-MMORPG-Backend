package com.chocolatada.loot.service.grpc;

import com.chocolatada.loot.dto.EnemyItemDropDTO;
import com.chocolatada.loot.dto.LootDTO;
import com.chocolatada.loot.mapper.EnemyItemDropMapper;
import com.chocolatada.loot.service.jpa.IEnemyItemDropService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnemyItemDropServiceGrpcImpl extends com.chocolatada.loot.grpc.LootServiceGrpc.LootServiceImplBase {
    private final IEnemyItemDropService enemyItemDropService;

    @Override
    public void getEnemyDropTable(com.chocolatada.loot.grpc.GetEnemyDropTableRequest request,
                                  StreamObserver<com.chocolatada.loot.grpc.GetEnemyDropTableResponse> responseObserver) {
        try {
            long enemyId = request.getEnemyId();
            log.info("Solicitud recibida: obtener tabla de drops para enemigo con ID: "+ enemyId);

            List<EnemyItemDropDTO> dropTable = enemyItemDropService.findEnemyDropTableByEnemyId(enemyId);

            com.chocolatada.loot.grpc.GetEnemyDropTableResponse response = com.chocolatada.loot.grpc.GetEnemyDropTableResponse.newBuilder()
                    .addAllDropTable(EnemyItemDropMapper.toLootItems(dropTable))
                    .build();

            log.info("Tabla de drops enviada correctamente para enemigo de ID : "+ enemyId +". Total items: "+ dropTable.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error al obtener tabla de drops para enemigo de ID "+ request.getEnemyId() +": "+ e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al obtener la tabla de drops")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void rollEnemyLoot(com.chocolatada.loot.grpc.RollEnemyLootRequest request,
                              StreamObserver<com.chocolatada.loot.grpc.RollEnemyLootResponse> responseObserver) {
        try {
            long enemyId = request.getEnemyId();
            log.info("Solicitud recibida: calcular loot para enemigo con ID: "+ enemyId);

            LootDTO lootResult = enemyItemDropService.rollEnemyLoot(enemyId);

            com.chocolatada.loot.grpc.RollEnemyLootResponse response = EnemyItemDropMapper.toRollEnemyLootResponse(lootResult);

            if(response.getDropped()) log.info("Loot obtenido para enemigo de ID: "+ enemyId +". Item ID: "+ lootResult.getItemId());
            else log.info("No se obtuvo loot para enemigo de ID: "+ enemyId);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error al calcular loot para enemigo de ID: "+ request.getEnemyId() +". "+ e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al calcular el loot del enemigo")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
