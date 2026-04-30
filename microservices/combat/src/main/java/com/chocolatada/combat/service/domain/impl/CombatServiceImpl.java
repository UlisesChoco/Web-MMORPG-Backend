package com.chocolatada.combat.service.domain.impl;

import com.chocolatada.combat.constant.TurnAction;
import com.chocolatada.combat.constant.TurnResult;
import com.chocolatada.combat.domain.*;
import com.chocolatada.combat.grpc.*;
import com.chocolatada.combat.mapper.StateMapper;
import com.chocolatada.combat.service.domain.ICombatService;
import com.chocolatada.combat.service.domain.formula.Formula;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CombatServiceImpl implements ICombatService {
    private final Random random;

    @Override
    public Combat processCombat(Entity player, Entity enemy) throws StatusRuntimeException {
        Combat combat = new Combat();

        int turnCounter = 1;
        while(noOneWins(player, enemy)) {
            Action playerAction = processTurn(player, enemy);
            Action enemyAction = processTurn(enemy, player);

            updateEntityAfterTurn(player, enemyAction);
            updateEntityAfterTurn(enemy, playerAction);

            State playerStateAfter = StateMapper.toState(player);
            State enemyStateAfter = StateMapper.toState(enemy);

            CombatTurn combatTurn = CombatTurn.builder()
                    .turnNumber(turnCounter)
                    .playerAction(playerAction)
                    .enemyAction(enemyAction)
                    .playerStateAfter(playerStateAfter)
                    .enemyStateAfter(enemyStateAfter)
                    .build();

            combat.getTurns().add(combatTurn);

            turnCounter++;
        }

        combat.setTotalTurns(turnCounter - 1);

        if(playerWon(enemy))
            combat.setWasFatal(false);

        if(enemyWon(player)) {
            combat.setWasFatal(true);
            combat.setLoot(null);
        }

        return combat;
    }

    @Override
    public void logCombatTurns(Combat combat) {
        List<CombatTurn> turns = combat.getTurns();

        log.info("----- Registro de combate -----");

        for(CombatTurn turn : turns) {
            log.info("\n");

            log.info("Turno " + turn.getTurnNumber() + ":");
            log.info("  Acción del jugador: " + turn.getPlayerAction().getTurnAction() +
                    " - Resultado: " + turn.getPlayerAction().getTurnResult() +
                    (turn.getPlayerAction().getTurnResult() != TurnResult.MISS ? " - Daño: " + turn.getPlayerAction().getDamage() : ""));
            log.info("  Estado del jugador después del turno: HP=" + turn.getPlayerStateAfter().getHp() +
                    ", Stamina=" + turn.getPlayerStateAfter().getStamina());
            log.info("  Acción del enemigo: " + turn.getEnemyAction().getTurnAction() +
                    " - Resultado: " + turn.getEnemyAction().getTurnResult() +
                    (turn.getEnemyAction().getTurnResult() != TurnResult.MISS ? " - Daño: " + turn.getEnemyAction().getDamage() : ""));
            log.info("  Estado del enemigo después del turno: HP=" + turn.getEnemyStateAfter().getHp() +
                    ", Stamina=" + turn.getEnemyStateAfter().getStamina());

            log.info("\n");
        }

        log.info("----- Fin del registro de combate -----");
    }

    private boolean noOneWins(Entity player, Entity enemy) {
        return player.getHp() > 0 && enemy.getHp() > 0;
    }

    private Action processTurn(Entity source, Entity target) {
        float hitChance = Formula.calculateHitChance(source, target);
        float roll = random.nextFloat();

        boolean hit = roll < hitChance;
        boolean critical = false;
        int damage = 0;
        TurnResult turnResult = TurnResult.MISS;

        if(hit) {
            damage = Formula.calculateDamage(source, target);
            turnResult = TurnResult.HIT;

            float critChance = Formula.calculateEffectiveCritRate(source);
            float critRoll = random.nextFloat();

            critical = critRoll < critChance;

            if(critical) {
                float critMultiplier = Formula.calculateEffectiveCritDamage(source);
                damage = Math.round(damage * critMultiplier);
                turnResult = TurnResult.CRITICAL_HIT;
            }
        }

        int newStamina = (source.getStamina() < 0) ? 1 : source.getStamina() - Formula.calculateStaminaCost(source);
        source.setStamina(newStamina);

        return Action.builder()
                .turnAction(TurnAction.BASIC_ATTACK)
                .turnResult(turnResult)
                .damage(damage)
                .critical(critical)
                .build();
    }

    public static void updateEntityAfterTurn(Entity entity, Action action) {
        if(action.getTurnResult().equals(TurnResult.MISS))
            return;

        int newHp = entity.getHp() - action.getDamage();
        entity.setHp(Math.max(newHp, 0));
    }

    private boolean playerWon(Entity enemy) {
        return enemy.getHp() <= 0;
    }

    private boolean enemyWon(Entity player) {
        return player.getHp() <= 0;
    }
}
