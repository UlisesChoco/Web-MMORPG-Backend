package com.example.demo.validator;

import com.example.demo.entity.PlayerClassModifierEntity;
import com.example.demo.exception.InvalidPlayerClassModifierDataException;

public class PlayerClassModifierValidator {
    public static boolean isValidModifier(PlayerClassModifierEntity modifier) throws InvalidPlayerClassModifierDataException {
        float critRateModifier = modifier.getCritRateModifier();
        float critDamageModifier = modifier.getCritDamageModifier();
        float hpModifier = modifier.getHpModifier();
        float atkModifier = modifier.getAtkModifier();
        float defModifier = modifier.getDefModifier();
        float staminaModifier = modifier.getStaminaModifier();
        float accuracyModifier = modifier.getAccuracyModifier();
        float evasionModifier = modifier.getEvasionModifier();

        if (critRateModifier < 1.0f || critDamageModifier < 1.0f || hpModifier < 1.0f || atkModifier < 1.0f ||
            defModifier < 1.0f || staminaModifier < 1.0f || accuracyModifier < 1.0f || evasionModifier < 1.0f) {
            throw new InvalidPlayerClassModifierDataException("Los modificadores no pueden ser menores que 1.");
        }

        return true;
    }
}
