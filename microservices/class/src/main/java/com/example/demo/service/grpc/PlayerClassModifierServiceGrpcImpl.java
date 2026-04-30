package com.example.demo.service.grpc;

import com.example.demo.entity.PlayerClassModifierEntity;
import com.example.demo.exception.InvalidPlayerClassDataException;
import com.example.demo.grpc.GetClassModifiersRequest;
import com.example.demo.grpc.GetClassModifiersResponse;
import com.example.demo.grpc.ModifierData;
import com.example.demo.grpc.PlayerClassModifierServiceGrpc;
import com.example.demo.mapper.PlayerClassModifierMapper;
import com.example.demo.service.jpa.IPlayerClassModifierService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerClassModifierServiceGrpcImpl extends PlayerClassModifierServiceGrpc.PlayerClassModifierServiceImplBase {
    private final IPlayerClassModifierService playerClassModifierService;

    @Override
    public void getClassModifiers(GetClassModifiersRequest request, StreamObserver<GetClassModifiersResponse> responseObserver) {
        try {
            Long classId = request.getClassId();
            PlayerClassModifierEntity modifiers = playerClassModifierService.findByPlayerClassId(classId);
            ModifierData modifierData = PlayerClassModifierMapper.toModifierData(modifiers);

            GetClassModifiersResponse response = GetClassModifiersResponse.newBuilder()
                    .setMessage("Success")
                    .setModifiers(modifierData)
                    .build();

            log.info("Modificadores de clase obtenidos para la clase ID: {}", classId);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerClassDataException e) {
            log.error("Error al obtener los modificadores de la clase: {}", e.getMessage());
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
}
