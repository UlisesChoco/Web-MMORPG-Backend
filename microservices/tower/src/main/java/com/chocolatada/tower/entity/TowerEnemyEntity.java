package com.chocolatada.tower.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tower_enemy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TowerEnemyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tower_id", nullable = false)
    private TowerEntity tower;

    @Column(name = "enemy_id", nullable = false)
    private Long enemyId;
}
