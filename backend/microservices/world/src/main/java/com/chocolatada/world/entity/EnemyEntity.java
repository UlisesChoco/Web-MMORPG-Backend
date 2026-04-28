package com.chocolatada.world.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "enemy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnemyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EnemyType type;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Column(name = "gold", nullable = false)
    private Integer gold;

    @Column(name = "crit_rate", nullable = false)
    private Float critRate;

    @Column(name = "crit_damage", nullable = false)
    private Float critDamage;

    @Column(name = "hp", nullable = false)
    private Integer hp;

    @Column(name = "atk", nullable = false)
    private Integer atk;

    @Column(name = "def", nullable = false)
    private Integer def;

    @Column(name = "stamina", nullable = false)
    private Integer stamina;

    @Column(name = "accuracy", nullable = false)
    private Integer accuracy;

    @Column(name = "evasion", nullable = false)
    private Integer evasion;

    @ManyToMany
    @JoinTable(
            name = "enemy_in_map",
            joinColumns = @JoinColumn(name = "enemy_id"),
            inverseJoinColumns = @JoinColumn(name = "map_id")
    )
    private List<MapEntity> maps;
}
