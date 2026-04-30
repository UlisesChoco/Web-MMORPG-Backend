package com.chocolatada.world.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "map")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "range_level", nullable = false)
    private String rangeLevel;

    @ManyToMany(mappedBy = "maps")
    private List<EnemyEntity> enemies;
}
