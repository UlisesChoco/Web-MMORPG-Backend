package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "player_class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "crit_rate", nullable = false)
    private float critRate;

    @Column(name = "crit_damage", nullable = false)
    private float critDamage;

    @Column(name = "hp", nullable = false)
    private int hp;

    @Column(name = "atk", nullable = false)
    private int atk;

    @Column(name = "def", nullable = false)
    private int def;

    @Column(name = "stamina", nullable = false)
    private int stamina;

    @Column(name = "accuracy", nullable = false)
    private int accuracy;

    @Column(name = "evasion", nullable = false)
    private int evasion;
}
