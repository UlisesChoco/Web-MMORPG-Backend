package com.chocolatada.combat.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CombatTurn {
    private Integer turnNumber;

    private Action playerAction;

    private Action enemyAction;

    private State playerStateAfter;

    private State enemyStateAfter;
}
