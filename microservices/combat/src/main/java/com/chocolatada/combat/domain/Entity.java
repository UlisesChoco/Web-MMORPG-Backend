package com.chocolatada.combat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Entity {
    private Float critRate;

    private Float critDamage;

    private Integer hp;

    private Integer atk;

    private Integer def;

    private Integer stamina;

    private Integer accuracy;

    private Integer evasion;

    private Integer maxHp;

    private Integer maxStamina;

    private Integer maxAccuracy;

    private Integer maxEvasion;

    @Builder
    public Entity(Float critRate, Float critDamage, Integer hp, Integer atk, Integer def, Integer stamina, Integer accuracy, Integer evasion) {
        this.critRate = critRate;
        this.critDamage = critDamage;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.stamina = stamina;
        this.accuracy = accuracy;
        this.evasion = evasion;
        this.maxHp = hp;
        this.maxStamina = stamina;
        this.maxAccuracy = accuracy;
        this.maxEvasion = evasion;
    }

    public void incrementStats(Entity other) {
        this.hp += other.getHp();
        this.atk += other.getAtk();
        this.def += other.getDef();
        this.stamina += other.getStamina();
        this.accuracy += other.getAccuracy();
        this.evasion += other.getEvasion();
        this.critRate += other.getCritRate();
        this.critDamage += other.getCritDamage();
        this.maxHp += other.getHp();
        this.maxStamina += other.getStamina();
        this.maxAccuracy += other.getAccuracy();
        this.maxEvasion += other.getEvasion();
    }
}
