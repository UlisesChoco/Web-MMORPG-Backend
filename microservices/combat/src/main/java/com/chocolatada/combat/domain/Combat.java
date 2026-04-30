package com.chocolatada.combat.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@ToString
public class Combat {
    private Long combatHistoryId;

    private Boolean wasFatal;

    private Integer totalTurns;

    private List<CombatTurn> turns;

    private Loot loot;

    public Combat() {
        this.turns = new ArrayList<>();
    }
}
