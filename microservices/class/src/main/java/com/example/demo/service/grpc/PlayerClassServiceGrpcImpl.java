package com.example.demo.service.grpc;

import com.example.demo.dto.BonusStatsDTO;
import com.example.demo.dto.PlayerStatsDTO;
import com.example.demo.entity.PlayerClassEntity;
import com.example.demo.exception.InvalidPlayerClassDataException;
import com.example.demo.grpc.*;
import com.example.demo.mapper.BonusStatsMapper;
import com.example.demo.mapper.PlayerClassMapper;
import com.example.demo.mapper.PlayerClassStatsMapper;
import com.example.demo.service.jpa.IPlayerClassService;
import com.example.demo.service.jpa.IPlayerClassStatsService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerClassServiceGrpcImpl extends PlayerClassServiceGrpc.PlayerClassServiceImplBase {
    private final IPlayerClassService playerClassService;

    private final IPlayerClassStatsService playerClassStatsService;

    @Override
    public void getClassById(GetClassByIdRequest request, StreamObserver<GetClassByIdResponse> responseObserver) {
        try {
            Long classId = request.getClassId();
            PlayerClassEntity playerClass = playerClassService.findById(classId);
            ClassData classData = PlayerClassMapper.toClassData(playerClass);

            GetClassByIdResponse response = GetClassByIdResponse.newBuilder()
                    .setMessage("Success")
                    .setData(classData)
                    .build();

            log.info("Clase obtenida: {}", classData.getName());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerClassDataException e) {
            log.error("Error al obtener la clase: {}", e.getMessage());
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asException()
            );
        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Error interno del servidor")
                            .asException()
            );
        }
    }

    @Override
    public void getScaledClassStats(GetScaledClassStatsRequest request, StreamObserver<GetScaledClassStatsResponse> responseObserver) {
        try {
            Long classId = request.getClassId();
            BonusStatsDTO bonus = BonusStatsMapper.toBonusStatsDto(request.getBonus());
            int level = request.getLevel();
            PlayerStatsDTO playerStatsDto = playerClassStatsService.getScaledPlayerClassStats(classId, bonus, level);
            com.example.demo.grpc.ScaledStats scaledStatsGrpc = PlayerClassStatsMapper.toScaledStatsGrpc(playerStatsDto);

            GetScaledClassStatsResponse response = GetScaledClassStatsResponse.newBuilder()
                    .setMessage("Success")
                    .setScaledStats(scaledStatsGrpc)
                    .build();

            log.info("Estadísticas escaladas obtenidas para la clase ID {}: nivel {}, bonus {}", classId, level, bonus);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerClassDataException e) {
            log.error("Error al obtener las estadísticas escaladas: {}", e.getMessage());
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asException()
            );
        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Error interno del servidor")
                            .asException()
            );
        }
    }

    @Override
    public void listClasses(ListClassesRequest request, StreamObserver<ListClassesResponse> responseObserver) {
        List<PlayerClassEntity> playerClasses = playerClassService.findAll();
        List<ClassData> classesData = PlayerClassMapper.toClassesData(playerClasses);

        ListClassesResponse response = ListClassesResponse.newBuilder()
                .setMessage("Success")
                .addAllClasses(classesData)
                .build();

        log.info("Listado de clases obtenido, total: {}", classesData.size());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
