CREATE DATABASE IF NOT EXISTS `class`;
USE `class`;

CREATE TABLE IF NOT EXISTS `player_class` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    crit_rate DECIMAL(10,2) NOT NULL,
    crit_damage DECIMAL(10,2) NOT NULL,
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
    crit_rate_modifier DECIMAL(10,2) NOT NULL,
    crit_damage_modifier DECIMAL(10,2) NOT NULL,
    hp_modifier DECIMAL(10,2) NOT NULL,
    atk_modifier DECIMAL(10,2) NOT NULL,
    def_modifier DECIMAL(10,2) NOT NULL,
    stamina_modifier DECIMAL(10,2) NOT NULL,
    accuracy_modifier DECIMAL(10,2) NOT NULL,
    evasion_modifier DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (player_class_id) REFERENCES player_class(id)
);