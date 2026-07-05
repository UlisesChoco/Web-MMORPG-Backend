package com.chocolatada.combat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "combat_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CombatHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Column(name = "enemy_id", nullable = false)
    private Long enemyId;

    @Column(name = "total_turns", nullable = false)
    private Integer totalTurns;

    @Column(name = "was_fatal", nullable = false)
    private Boolean wasFatal;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
