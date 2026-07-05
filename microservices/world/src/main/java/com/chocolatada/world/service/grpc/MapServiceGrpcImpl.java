package com.chocolatada.world.service.grpc;

import com.chocolatada.world.dto.EnemyDTO;
import com.chocolatada.world.dto.MapDTO;
import com.chocolatada.world.dto.MapEnemyDTO;
import com.chocolatada.world.exception.MapNotFoundException;
import com.chocolatada.world.mapper.MapMapper;
import com.chocolatada.world.service.jpa.IMapService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapServiceGrpcImpl extends com.chocolatada.world.grpc.MapServiceGrpc.MapServiceImplBase {

    private final IMapService mapService;

    @Override
    public void getMapById(com.chocolatada.world.grpc.GetMapByIdRequest request,
                           StreamObserver<com.chocolatada.world.grpc.GetMapResponse> responseObserver) {
        try {
            long mapId = request.getMapId();
            log.info("Solicitud recibida: obtener mapa con ID: " + mapId);

            MapDTO map = mapService.findById(mapId);

            com.chocolatada.world.grpc.GetMapResponse response = com.chocolatada.world.grpc.GetMapResponse.newBuilder()
                    .setMap(MapMapper.toGrpcMap(map))
                    .build();

            log.info("Mapa encontrado y enviado correctamente. ID: " + map.getId() + ", Nombre: " + map.getName());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (MapNotFoundException e) {
            log.error("Mapa no encontrado con ID " + request.getMapId() + ": " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno al obtener mapa con ID " + request.getMapId() + ": " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al obtener el mapa")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void listMaps(com.chocolatada.world.grpc.ListMapsRequest request,
                         StreamObserver<com.chocolatada.world.grpc.ListMapsResponse> responseObserver) {
        try {
            log.info("Solicitud recibida: listar todos los mapas");

            List<MapDTO> maps = mapService.findAll();

            com.chocolatada.world.grpc.ListMapsResponse response = com.chocolatada.world.grpc.ListMapsResponse.newBuilder()
                    .addAllMaps(MapMapper.toGrpcMaps(maps))
                    .build();

            log.info("Lista de mapas enviada correctamente. Total: " + maps.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno al listar mapas: " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al listar los mapas")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void listMapEnemies(com.chocolatada.world.grpc.ListMapEnemiesRequest request,
                               StreamObserver<com.chocolatada.world.grpc.ListMapEnemiesResponse> responseObserver) {
        try {
            long mapId = request.getMapId();
            log.info("Solicitud recibida: obtener pool de enemigos del mapa con ID: " + mapId);

            List<MapEnemyDTO> enemies = mapService.findMapEnemies(mapId);

            com.chocolatada.world.grpc.ListMapEnemiesResponse response = com.chocolatada.world.grpc.ListMapEnemiesResponse.newBuilder()
                    .addAllPool(MapMapper.toGrpcMapEnemies(enemies))
                    .build();

            log.info("Pool de enemigos enviada correctamente. Mapa ID: " + mapId + ", Total enemigos: " + enemies.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (MapNotFoundException e) {
            log.error("Mapa no encontrado con ID " + request.getMapId() + ": " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno al obtener pool de enemigos del mapa con ID " + request.getMapId() + ": " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al obtener la pool de enemigos del mapa")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void getExpandedMapEnemyPool(com.chocolatada.world.grpc.GetExpandedMapEnemyPoolRequest request,
                                        StreamObserver<com.chocolatada.world.grpc.GetExpandedMapEnemyPoolResponse> responseObserver) {
        try {
            long mapId = request.getMapId();
            log.info("Solicitud recibida: obtener pool expandida de enemigos del mapa con ID: " + mapId);

            List<EnemyDTO> enemies = mapService.findExpandedMapEnemyPool(mapId);

            com.chocolatada.world.grpc.GetExpandedMapEnemyPoolResponse response = com.chocolatada.world.grpc.GetExpandedMapEnemyPoolResponse.newBuilder()
                    .addAllEnemies(MapMapper.toGrpcEnemies(enemies))
                    .build();

            log.info("Pool expandida de enemigos enviada correctamente. Mapa ID: " + mapId + ", Total enemigos: " + enemies.size());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (MapNotFoundException e) {
            log.error("Mapa no encontrado con ID " + request.getMapId() + ": " + e.getMessage());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno al obtener pool expandida de enemigos del mapa con ID " + request.getMapId() + ": " + e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor al obtener la pool expandida de enemigos del mapa")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
