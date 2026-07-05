package com.chocolatada.tower.service.grpc;

import com.chocolatada.tower.entity.TowerEntity;
import com.chocolatada.tower.exception.InvalidTowerDataException;
import com.chocolatada.tower.mapper.TowerMapper;
import com.chocolatada.tower.service.jpa.ITowerService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TowerServiceGrpcImpl extends com.chocolatada.tower.grpc.TowerServiceGrpc.TowerServiceImplBase {
    private final ITowerService towerService;

    @Override
    public void getTowerFloor(
            com.chocolatada.tower.grpc.GetTowerFloorRequest request,
            StreamObserver<com.chocolatada.tower.grpc.GetTowerFloorResponse> responseObserver
    ) {
        try {
            TowerEntity entity = towerService.findByFloor((int) request.getFloor());
            com.chocolatada.tower.grpc.Tower floor = TowerMapper.toTowerGrpc(entity);

            com.chocolatada.tower.grpc.GetTowerFloorResponse response = com.chocolatada.tower.grpc.GetTowerFloorResponse.newBuilder()
                    .setTower(floor)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch(InvalidTowerDataException exception) {
            log.error("Error al obtener el piso de la torre: " + exception.getMessage());
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(exception.getMessage())
                            .asRuntimeException()
            );
        } catch(Exception exception) {
            log.error("Error interno del servidor al obtener el piso de la torre: " + exception.getMessage());
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(exception.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void listTowerFloors(
            com.chocolatada.tower.grpc.ListTowerFloorsRequest request,
            StreamObserver<com.chocolatada.tower.grpc.ListTowerFloorsResponse> responseObserver
    ) {
        List<TowerEntity> floors = towerService.findAllOrderedByFloor();
        List<com.chocolatada.tower.grpc.Tower> towers = TowerMapper.toTowersGrpc(floors);

        com.chocolatada.tower.grpc.ListTowerFloorsResponse response = com.chocolatada.tower.grpc.ListTowerFloorsResponse.newBuilder()
                .addAllFloors(towers)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
