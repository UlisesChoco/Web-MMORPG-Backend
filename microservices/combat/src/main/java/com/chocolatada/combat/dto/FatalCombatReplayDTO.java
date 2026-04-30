package com.chocolatada.combat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FatalCombatReplayDTO {
    private Long playerId;

    private Long enemyId;

    private LocalDate date;

    private Integer totalTurns;
}
