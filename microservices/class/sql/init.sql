CREATE DATABASE IF NOT EXISTS `class`;
USE `class`;

CREATE TABLE IF NOT EXISTS `player_class` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    crit_rate FLOAT NOT NULL,
    crit_damage FLOAT NOT NULL,
    hp INT NOT NULL,
    atk INT NOT NULL,
    def INT NOT NULL,
    stamina INT NOT NULL,
    accuracy INT NOT NULL,
    evasion INT NOT NULL
);

CREATE TABLE IF NOT EXISTS `player_class_modifier` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_class_id BIGINT NOT NULL,
    crit_rate_modifier FLOAT NOT NULL,
    crit_damage_modifier FLOAT NOT NULL,
    hp_modifier FLOAT NOT NULL,
    atk_modifier FLOAT NOT NULL,
    def_modifier FLOAT NOT NULL,
    stamina_modifier FLOAT NOT NULL,
    accuracy_modifier FLOAT NOT NULL,
    evasion_modifier FLOAT NOT NULL,
    FOREIGN KEY (player_class_id) REFERENCES player_class(id)
);