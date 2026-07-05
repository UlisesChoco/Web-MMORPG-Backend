package com.chocolatada.world.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "npc_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NPCItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "npc_id", nullable = false)
    private NPCEntity npc;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "price", nullable = false)
    private Integer price;
}
