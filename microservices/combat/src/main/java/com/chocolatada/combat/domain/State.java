package com.chocolatada.combat.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class State {
    private Integer hp;

    private Integer stamina;

    private Integer accuracy;

    private Integer evasion;
}
