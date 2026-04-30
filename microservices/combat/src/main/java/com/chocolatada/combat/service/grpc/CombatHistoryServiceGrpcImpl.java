package com.chocolatada.combat.service.grpc;

import com.chocolatada.combat.dto.CombatHistoryDTO;
import com.chocolatada.combat.grpc.*;
import com.chocolatada.combat.mapper.CombatHistoryMapper;
import com.chocolatada.combat.service.jpa.ICombatHistoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CombatHistoryServiceGrpcImpl extends CombatHistoryServiceGrpc.CombatHistoryServiceImplBase {
    private final ICombatHistoryService combatHistoryService;

    @Override
    public void getCombatHistoryByPlayer(
            GetCombatHistoryByPlayerRequest request,
            StreamObserver<GetCombatHistoryByPlayerResponse> responseObserver
    ) {
        Long playerId = request.getPlayerId();

        List<CombatHistoryDTO> dtos = combatHistoryService.getCombatHistoryByPlayerId(playerId);
        List<CombatHistory> combatHistories = CombatHistoryMapper.toGrpcCombatHistories(dtos);

        GetCombatHistoryByPlayerResponse response = GetCombatHistoryByPlayerResponse.newBuilder()
                .addAllCombats(combatHistories)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getRecentCombats(
            GetRecentCombatsRequest request,
            StreamObserver<GetRecentCombatsResponse> responseObserver
    ) {
        int limit = request.getLimit();

        List<CombatHistoryDTO> dtos = combatHistoryService.getRecentCombats(limit);
        List<CombatHistory> combatHistories = CombatHistoryMapper.toGrpcCombatHistories(dtos);

        GetRecentCombatsResponse response = GetRecentCombatsResponse.newBuilder()
                .addAllCombats(combatHistories)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPlayerWinCount(
            GetPlayerWinCountRequest request,
            StreamObserver<GetPlayerWinCountResponse> responseObserver
    ) {
        Long playerId = request.getPlayerId();

        Long winCount = combatHistoryService.getPlayerWinCount(playerId);

        GetPlayerWinCountResponse response = GetPlayerWinCountResponse.newBuilder()
                .setWinCount(winCount)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
