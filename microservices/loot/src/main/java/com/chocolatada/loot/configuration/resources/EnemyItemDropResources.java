package com.chocolatada.loot.configuration.resources;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "loot-tables")
public class EnemyItemDropResources {
    private List<EnemyItemDropResource> enemyItemDrops;
}
