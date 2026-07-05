package com.chocolatada.playerclass.service.jpa.impl;

import com.chocolatada.playerclass.dto.BonusStatsDTO;
import com.chocolatada.playerclass.dto.PlayerStatsDTO;
import org.springframework.stereotype.Service;

import com.chocolatada.playerclass.entity.PlayerClassEntity;
import com.chocolatada.playerclass.entity.PlayerClassModifierEntity;
import com.chocolatada.playerclass.exception.InvalidPlayerClassDataException;
import com.chocolatada.playerclass.service.formula.ScalateStatFormula;
import com.chocolatada.playerclass.service.jpa.IPlayerClassModifierService;
import com.chocolatada.playerclass.service.jpa.IPlayerClassService;
import com.chocolatada.playerclass.service.jpa.IPlayerClassStatsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerClassStatsServiceImpl implements IPlayerClassStatsService {

    private final IPlayerClassService playerClassService;
    private final IPlayerClassModifierService playerClassModifierService;

    @Override
    public PlayerStatsDTO getScaledPlayerClassStats(Long classId, BonusStatsDTO bonus, int level) throws InvalidPlayerClassDataException {
        PlayerClassEntity playerClass = playerClassService.findById(classId);

        PlayerClassModifierEntity classModifier = playerClassModifierService.findByPlayerClassId(classId);

        float critRate = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getCritRate(), 
            classModifier.getCritRateModifier(),
            0,
            level
        );
        float critDamage = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getCritDamage(), 
            classModifier.getCritDamageModifier(),
            0,
            level
        );
        int hp = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getHp(), 
            classModifier.getHpModifier(),
            bonus.getHp(),
            level
        );
        int atk = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getAtk(), 
            classModifier.getAtkModifier(),
            bonus.getAtk(),
            level
        );
        int def = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getDef(), 
            classModifier.getDefModifier(),
            bonus.getDef(),
            level
        );
        int stamina = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getStamina(), 
            classModifier.getStaminaModifier(),
            bonus.getStamina(),
            level
        );
        int accuracy = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getAccuracy(), 
            classModifier.getAccuracyModifier(),
            bonus.getAccuracy(),
            level
        );
        int evasion = ScalateStatFormula.calculateStatAtLevel(
            playerClass.getEvasion(), 
            classModifier.getEvasionModifier(),
            bonus.getEvasion(),
            level
        );

        return PlayerStatsDTO.builder()
            .critRate(critRate)
            .critDamage(critDamage)
            .hp(hp)
            .atk(atk)
            .def(def)
            .stamina(stamina)
            .accuracy(accuracy)
            .evasion(evasion)
            .build();
    }
}
