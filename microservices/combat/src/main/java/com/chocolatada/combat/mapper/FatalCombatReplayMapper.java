package com.chocolatada.combat.mapper;

import com.chocolatada.combat.domain.Action;
import com.chocolatada.combat.domain.CombatTurn;
import com.chocolatada.combat.domain.State;
import com.chocolatada.combat.dto.FatalCombatReplayDTO;
import com.chocolatada.combat.entity.FatalCombatReplayEntity;
import com.chocolatada.combat.grpc.ActionGrpc;
import com.chocolatada.combat.grpc.CombatTurnGrpc;
import com.chocolatada.combat.grpc.FatalityEntry;
import com.chocolatada.combat.grpc.StateGrpc;
import com.chocolatada.combat.grpc.TurnActionGrpc;
import com.chocolatada.combat.grpc.TurnResultGrpc;

import java.util.ArrayList;
import java.util.List;

public class FatalCombatReplayMapper {
    public static FatalCombatReplayDTO toFatalCombatReplayDTO(FatalCombatReplayEntity entity) {
        return FatalCombatReplayDTO.builder()
                .playerId(entity.getCombatHistory().getPlayerId())
                .enemyId(entity.getCombatHistory().getEnemyId())
                .date(entity.getCombatHistory().getDate())
                .totalTurns(entity.getCombatHistory().getTotalTurns())
                .build();
    }

    public static List<FatalCombatReplayDTO> toFatalCombatReplayDTOList(List<FatalCombatReplayEntity> entities) {
        List<FatalCombatReplayDTO> dtoList = new ArrayList<>();

        for (FatalCombatReplayEntity entity : entities)
            dtoList.add(toFatalCombatReplayDTO(entity));

        return dtoList;
    }

    public static CombatTurnGrpc toCombatTurnGrpc(CombatTurn combatTurn) {
        return CombatTurnGrpc.newBuilder()
                .setTurnNumber(combatTurn.getTurnNumber())
                .setPlayerAction(toActionGrpc(combatTurn.getPlayerAction()))
                .setEnemyAction(toActionGrpc(combatTurn.getEnemyAction()))
                .setPlayerStateAfter(toStateGrpc(combatTurn.getPlayerStateAfter()))
                .setEnemyStateAfter(toStateGrpc(combatTurn.getEnemyStateAfter()))
                .build();
    }

    public static List<CombatTurnGrpc> toCombatTurnGrpcList(List<CombatTurn> combatTurns) {
        List<CombatTurnGrpc> grpcList = new ArrayList<>();

        for (CombatTurn combatTurn : combatTurns)
            grpcList.add(toCombatTurnGrpc(combatTurn));

        return grpcList;
    }

    public static ActionGrpc toActionGrpc(Action action) {
        return ActionGrpc.newBuilder()
                .setTurnAction(TurnActionGrpc.valueOf(action.getTurnAction().name()))
                .setTurnResult(TurnResultGrpc.valueOf(action.getTurnResult().name()))
                .setDamage(action.getDamage())
                .setCritical(action.getCritical())
                .build();
    }

    public static StateGrpc toStateGrpc(State state) {
        return StateGrpc.newBuilder()
                .setHp(state.getHp())
                .setStamina(state.getStamina())
                .setAccuracy(state.getAccuracy())
                .setEvasion(state.getEvasion())
                .build();
    }

    public static FatalityEntry toFatalityEntry(FatalCombatReplayDTO dto) {
        return FatalityEntry.newBuilder()
                .setPlayerId(dto.getPlayerId())
                .setEnemyId(dto.getEnemyId())
                .setDate(dto.getDate().toString())
                .build();
    }

    public static List<FatalityEntry> toFatalityEntryList(List<FatalCombatReplayDTO> dtos) {
        List<FatalityEntry> entryList = new ArrayList<>();

        for (FatalCombatReplayDTO dto : dtos)
            entryList.add(toFatalityEntry(dto));

        return entryList;
    }
}
