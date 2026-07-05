package com.chocolatada.player.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerUpdateDTO {
    Optional<String> name;
    Optional<Integer> gold;
    Optional<Integer> level;
    Optional<Integer> experience;
    Optional<Integer> experienceLimit;
    Optional<Integer> freeStatPoints;
    Optional<Integer> hpBonus;
    Optional<Integer> atkBonus;
    Optional<Integer> defBonus;
    Optional<Integer> staminaBonus;
    Optional<Integer> accuracyBonus;
    Optional<Integer> evasionBonus;
}
