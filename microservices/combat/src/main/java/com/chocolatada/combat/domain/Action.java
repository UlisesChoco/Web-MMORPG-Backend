package com.chocolatada.combat.domain;

import com.chocolatada.combat.constant.TurnAction;
import com.chocolatada.combat.constant.TurnResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Action {
    private TurnAction turnAction;

    private TurnResult turnResult;

    private Integer damage;

    private Boolean critical;
}
