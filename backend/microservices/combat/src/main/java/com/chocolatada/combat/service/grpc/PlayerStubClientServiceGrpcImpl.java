package com.chocolatada.combat.service.grpc;

import com.chocolatada.combat.domain.Entity;
import com.chocolatada.combat.grpc.*;
import com.chocolatada.combat.mapper.EntityMapper;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerStubClientServiceGrpcImpl {
    private final PlayerServiceGrpc.PlayerServiceBlockingStub playerStub;

    private final PlayerClassServiceGrpc.PlayerClassServiceBlockingStub playerClassStub;

    public Player getPlayerResponse(Long playerId) throws StatusRuntimeException {
        GetPlayerByIdRequest request = com.chocolatada.combat.grpc.GetPlayerByIdRequest.newBuilder()
                .setPlayerId(playerId)
                .build();

        return playerStub.getPlayerById(request).getPlayer();
    }

    public Entity getPlayer(Long playerId) throws StatusRuntimeException {
        Player player = getPlayerResponse(playerId);

        BonusStats bonusStats = BonusStats.newBuilder()
                .setHp(player.getHpBonus())
                .setAtk(player.getAtkBonus())
                .setDef(player.getDefBonus())
                .setStamina(player.getStaminaBonus())
                .setAccuracy(player.getAccuracyBonus())
                .setEvasion(player.getEvasionBonus())
                .build();

        GetScaledClassStatsRequest request = GetScaledClassStatsRequest.newBuilder()
                .setClassId(player.getClassId())
                .setBonus(bonusStats)
                .setLevel(player.getLevel())
                .build();

        GetScaledClassStatsResponse response = playerClassStub.getScaledClassStats(request);
        ScaledStats scaledStats = response.getScaledStats();

        return EntityMapper.toEntity(scaledStats);
    }

    public void updatePlayerData(long playerId, int gold, int experience) {
        PlayerUpdateData data = PlayerUpdateData.newBuilder()
                .setGold(gold)
                .setExperience(experience)
                .build();

        UpdatePlayerRequest request = UpdatePlayerRequest.newBuilder()
                .setPlayerId(playerId)
                .setUpdates(data)
                .build();

        playerStub.updatePlayer(request);
    }

    public void markPlayerAsDead(Long playerId) throws StatusRuntimeException {
        MarkPlayerAsDeadRequest request = MarkPlayerAsDeadRequest.newBuilder()
                .setPlayerId(playerId)
                .build();
        playerStub.markPlayerAsDead(request);
    }
}
