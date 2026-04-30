package com.chocolatada.player.service.grpc;

import com.chocolatada.player.dto.PlayerTopLevelDTO;
import com.chocolatada.player.dto.PlayerUpdateDTO;
import com.chocolatada.player.entity.PlayerEntity;
import com.chocolatada.player.exception.InvalidPlayerDataException;
import com.chocolatada.player.mapper.PlayerMapper;
import com.chocolatada.player.service.jpa.IPlayerService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.chocolatada.player.grpc.PlayerServiceGrpc;
import com.chocolatada.player.grpc.GetPlayerByIdRequest;
import com.chocolatada.player.grpc.PlayerResponse;
import com.chocolatada.player.grpc.GetPlayerByUserIdRequest;
import com.chocolatada.player.grpc.TopPlayersRequest;
import com.chocolatada.player.grpc.TopPlayersResponse;
import com.chocolatada.player.grpc.UpdatePlayerRequest;
import com.chocolatada.player.grpc.UpdatePlayerResponse;
import com.chocolatada.player.grpc.DeletePlayerRequest;
import com.chocolatada.player.grpc.DeletePlayerResponse;
import com.chocolatada.player.grpc.MarkPlayerAsDeadRequest;
import com.chocolatada.player.grpc.MarkPlayerAsDeadResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceGrpcImpl extends PlayerServiceGrpc.PlayerServiceImplBase {
    private final IPlayerService playerService;

    @Override
    public void getPlayerById(GetPlayerByIdRequest request, StreamObserver<PlayerResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            PlayerEntity player = playerService.findById(playerId);
            PlayerResponse response = PlayerMapper.toPlayerResponse(player);

            log.info("Jugador de ID: " + playerId + " obtenido exitosamente.");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerDataException e) {
            log.warn("No existe un jugador con ID: " + request.getPlayerId());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno del servidor al obtener el jugador con ID: " + request.getPlayerId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }

    @Override
    public void getPlayerByUserId(GetPlayerByUserIdRequest request, StreamObserver<PlayerResponse> responseObserver) {
        try {
            Long userId = request.getUserId();
            PlayerEntity player = playerService.findByUserId(userId);
            PlayerResponse response = PlayerMapper.toPlayerResponse(player);

            log.info("Jugador obtenido exitosamente para userId: {}", userId);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerDataException e) {
            log.error("No existe un jugador con UserID: " + request.getUserId());
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno del servidor al obtener el jugador con UserID: " + request.getUserId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }

    @Override
    public void getTopPlayersByLevel(TopPlayersRequest request, StreamObserver<TopPlayersResponse> responseObserver) {
        try {
            int limit = request.getLimit();
            Boolean alive = request.hasAlive() ? request.getAlive() : null;
            List<PlayerTopLevelDTO> topPlayers = playerService.findTopByLevel(alive, limit);
            TopPlayersResponse response = PlayerMapper.toTopPlayersResponse(topPlayers);

            log.info("Mejores jugadores obtenidos exitosamente. Limite: "+ limit + ", Vivo: " + alive);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error interno del servidor al obtener los mejores jugadores", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }

    @Override
    public void updatePlayer(UpdatePlayerRequest request, StreamObserver<UpdatePlayerResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            PlayerUpdateDTO updateDTO = PlayerMapper.toPlayerUpdateDTO(request.getUpdates());
            playerService.update(playerId, updateDTO);

            UpdatePlayerResponse response = UpdatePlayerResponse.newBuilder()
                    .setMessage("Success")
                    .build();

            log.info("Jugador con ID: " + playerId + " actualizado exitosamente.");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerDataException e) {
            log.error("Datos inválidos proporcionados para actualizar el jugador con ID: " + request.getPlayerId(), e);
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno del servidor al actualizar el jugador con ID: " + request.getPlayerId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }

    @Override
    public void deletePlayer(DeletePlayerRequest request, StreamObserver<DeletePlayerResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            playerService.deleteById(playerId);

            DeletePlayerResponse response = DeletePlayerResponse.newBuilder()
                    .setMessage("Success")
                    .build();

            log.info("Jugador con ID: " + playerId + " eliminado exitosamente.");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerDataException e) {
            log.error("No existe un jugador con ID: " + request.getPlayerId(), e);
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno del servidor al eliminar el jugador con ID: " + request.getPlayerId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }

    @Override
    public void markPlayerAsDead(MarkPlayerAsDeadRequest request, StreamObserver<MarkPlayerAsDeadResponse> responseObserver) {
        try {
            Long playerId = request.getPlayerId();
            boolean marked = playerService.markPlayerAsDead(playerId);

            MarkPlayerAsDeadResponse response = MarkPlayerAsDeadResponse.newBuilder()
                    .setMessage("Success")
                    .build();

            log.info("Jugador con ID: "+ playerId +" marcado como muerto exitosamente.");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidPlayerDataException e) {
            log.error("No existe un jugador con ID: " + request.getPlayerId(), e);
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error interno del servidor al marcar como muerto el jugador con ID: " + request.getPlayerId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asRuntimeException());
        }
    }
}