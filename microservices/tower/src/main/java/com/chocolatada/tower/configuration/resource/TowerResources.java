package com.chocolatada.tower.configuration.resource;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "tower")
public class TowerResources {
    List<TowerResource> floors;
}
