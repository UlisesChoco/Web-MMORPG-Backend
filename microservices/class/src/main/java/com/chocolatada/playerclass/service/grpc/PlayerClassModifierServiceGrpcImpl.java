package com.chocolatada.playerclass.service.grpc;

import com.chocolatada.playerclass.entity.PlayerClassModifierEntity;
import com.chocolatada.playerclass.exception.InvalidPlayerClassDataException;
import com.chocolatada.playerclass.grpc.GetClassModifiersRequest;
import com.chocolatada.playerclass.grpc.GetClassModifiersResponse;
import com.chocolatada.playerclass.grpc.ModifierData;
import com.chocolatada.playerclass.grpc.PlayerClassModifierServiceGrpc;
import com.chocolatada.playerclass.mapper.PlayerClassModifierMapper;
import com.chocolatada.playerclass.service.jpa.IPlayerClassModifierService;
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
