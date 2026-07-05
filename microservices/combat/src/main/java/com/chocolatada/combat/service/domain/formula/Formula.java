package com.chocolatada.combat.service.domain.formula;

import com.chocolatada.combat.domain.Entity;

public class Formula {
    public static final int MIN_DAMAGE = 1;
    public static final float STAMINA_DAMAGE_BASE = 0.7f;
    public static final float STAMINA_DAMAGE_WEIGHT = 0.3f;

    public static final float STAMINA_MIN_FACTOR = 0.4f;
    public static final float STAMINA_WEIGHT = 0.6f;

    public static final float EVASION_BASE_FACTOR = 0.6f;
    public static final float EVASION_STAMINA_WEIGHT = 0.4f;

    public static final int BASE_STAMINA_COST = 4;
    public static final float ATK_STAMINA_DIVISOR = 25f;

    public static final float MIN_HIT_CHANCE = 0.05f;
    public static final float MAX_HIT_CHANCE = 0.95f;

    public static int calculateDamage(Entity source, Entity target) {
        float atk = source.getAtk();
        float def = target.getDef();

        float baseDamage = atk * (atk / (atk + def));

        float staminaFactor = calculateStaminaFactor(source);

        float staminaDamageFactor = STAMINA_DAMAGE_BASE + STAMINA_DAMAGE_WEIGHT * staminaFactor;

        float finalDamage = baseDamage * staminaDamageFactor;

        return Math.max(MIN_DAMAGE, Math.round(finalDamage));
    }


    private static float calculateStaminaFactor(Entity entity) {
        float staminaRatio = (float) entity.getStamina() / entity.getMaxStamina();
        staminaRatio = Math.max(0f, Math.min(1f, staminaRatio));
        return STAMINA_MIN_FACTOR + STAMINA_WEIGHT * (float) Math.sqrt(staminaRatio);
    }

    private static float calculateEffectiveAccuracy(Entity entity) {
        return entity.getAccuracy() * calculateStaminaFactor(entity);
    }

    public static float calculateEffectiveEvasion(Entity entity) {
        return entity.getEvasion() * (EVASION_BASE_FACTOR + EVASION_STAMINA_WEIGHT * calculateStaminaFactor(entity));
    }

    public static float calculateHitChance(Entity source, Entity target) {
        float effectiveAccuracy = calculateEffectiveAccuracy(source);
        float effectiveEvasion = calculateEffectiveEvasion(target);

        float hitChance = effectiveAccuracy / (effectiveAccuracy + effectiveEvasion);

        if(hitChance >= MIN_HIT_CHANCE && hitChance <= MAX_HIT_CHANCE)
            return hitChance;

        return (hitChance < MIN_HIT_CHANCE) ? MIN_HIT_CHANCE : MAX_HIT_CHANCE;
    }

    public static int calculateStaminaCost(Entity entity) {
        return Math.round(BASE_STAMINA_COST + (entity.getAtk() / ATK_STAMINA_DIVISOR));
    }

    public static float calculateEffectiveCritRate(Entity entity) {
        return (entity.getCritRate() / 100f) * calculateStaminaFactor(entity);
    }

    public static float calculateEffectiveCritDamage(Entity entity) {
        return 1f + (entity.getCritDamage() / 100f);
    }
}
