package com.example.demo.configuration.resources.definition.player_class;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "game")
@Validated
public class ClassesConfigurationProperties {
    private List<ClassDefinition> classes;
}
