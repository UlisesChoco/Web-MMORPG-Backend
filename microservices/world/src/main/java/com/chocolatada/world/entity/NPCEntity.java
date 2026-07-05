package com.chocolatada.world.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "npc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NPCEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NPCType type;

    @OneToMany(mappedBy = "npc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NPCItemEntity> items;
}
