package com.chocolatada.tower.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tower")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "level_range", nullable = false)
    private String levelRange;
}
