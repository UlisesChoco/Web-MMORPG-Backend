package com.chocolatada.combat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fatal_combat_replay")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FatalCombatReplayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "combat_history_id", nullable = false)
    private CombatHistoryEntity combatHistory;

    @Column(name = "turn_log", nullable = false)
    private String turnLog;
}
