package com.chocolatada.combat.service.grpc;

import com.chocolatada.combat.domain.Combat;
import com.chocolatada.combat.domain.CombatTurn;
import com.chocolatada.combat.domain.Entity;
import com.chocolatada.combat.entity.CombatHistoryEntity;
import com.chocolatada.combat.entity.FatalCombatReplayEntity;
import com.chocolatada.combat.grpc.*;
import com.chocolatada.combat.mapper.EntityMapper;
import com.chocolatada.combat.mapper.FatalCombatReplayMapper;
import com.chocolatada.combat.service.domain.ICombatService;
import com.chocolatada.combat.service.jpa.ICombatHistoryService;
import com.chocolatada.combat.service.jpa.IFatalCombatReplayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CombatServiceGrpcImpl extends CombatServiceGrpc.CombatServiceImplBase {
    private final ICombatService combatService;

    private final ICombatHistoryService combatHistoryService;

    private final IFatalCombatReplayService fatalCombatReplayService;

    private final PlayerStubClientServiceGrpcImpl playerStubClientServiceGrpc;

    private final EnemyStubClientServiceGrpcImpl enemyStubClientServiceGrpc;

    private final LootStubClientServiceGrpcImpl lootStubClientServiceGrpc;

    private final InventoryStubClientServiceGrpcImpl inventoryStubClientServiceGrpc;

    private final ObjectMapper objectMapper;

    @Override
    public void processCombat(
            ProcessCombatRequest request,
            StreamObserver<ProcessCombatResponse> responseObserver
    ) {
        try {
            Long playerId = request.getPlayerId();
            Long enemyId = request.getEnemyId();

            log.info("Procesando combate entre jugador de ID "+ playerId +" y enemigo de ID "+ enemyId);

            Entity player = playerStubClientServiceGrpc.getPlayer(playerId);
            Entity equipmentStats = inventoryStubClientServiceGrpc.getEquipmentStatBonus(playerId);
            player.incrementStats(equipmentStats);

            Enemy enemyGrpc = enemyStubClientServiceGrpc.getEnemy(enemyId);
            Entity enemy = EntityMapper.toEntity(enemyGrpc);

            Combat combat = combatService.processCombat(player, enemy);

            CombatHistoryEntity combatHistoryEntity = CombatHistoryEntity.builder()
                    .playerId(playerId)
                    .enemyId(enemyId)
                    .totalTurns(combat.getTotalTurns())
                    .wasFatal(combat.getWasFatal())
                    .date(LocalDate.now())
                    .build();

            combatHistoryEntity = combatHistoryService.save(combatHistoryEntity);

            combat.setCombatHistoryId(combatHistoryEntity.getId());

            List<CombatTurn> turns = combat.getTurns();
            List<CombatTurnGrpc> turnsGrpc = FatalCombatReplayMapper.toCombatTurnGrpcList(turns);

            combatService.logCombatTurns(combat); //esto lo mantengo solo para debuggear. puedo sacarlo despues porque genera muchos logs

            LootGrpc loot = LootGrpc.newBuilder()
                    .setGold(0)
                    .setExperience(0)
                    .build();

            if (combat.getWasFatal()) {
                FatalCombatReplayEntity fatalCombatReplayEntity = FatalCombatReplayEntity.builder()
                        .combatHistory(combatHistoryEntity)
                        .turnLog(objectMapper.writeValueAsString(turns))
                        .build();
                fatalCombatReplayService.save(fatalCombatReplayEntity);
                log.info("Combate procesado. El jugador fue asesinado. Replay del combate fatal guardada.");
            } else {
                loot = lootStubClientServiceGrpc.roll(enemyGrpc);
                inventoryStubClientServiceGrpc.addItemToInventory(playerId, loot.getItemId());
                playerStubClientServiceGrpc.updatePlayerData(playerId, enemyGrpc.getGold(), enemyGrpc.getExperience());
                log.info("Combate procesado. El jugador sobrevivió. Botín generado.");
            }

            ProcessCombatResponse response = ProcessCombatResponse.newBuilder()
                    .setCombatId(combat.getCombatHistoryId())
                    .setWasFatal(combat.getWasFatal())
                    .setTotalTurns(combat.getTotalTurns())
                    .addAllTurns(turnsGrpc)
                    .setLoot(loot)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            log.error("Error al procesar combate: " + e.getMessage(), e);
            responseObserver.onError(e);
        } catch (Exception e) {
            log.error("Error interno del servidor: " + e.getMessage(), e);
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Error interno del servidor")
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }
}
