CREATE DATABASE IF NOT EXISTS `world`;
USE `world`;

CREATE TABLE IF NOT EXISTS `enemy` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    type ENUM('normal', 'elite', 'miniboss', 'boss', 'legendary') NOT NULL,
    level INT NOT NULL,
    experience INT NOT NULL,
    gold INT NOT NULL,
    crit_rate DECIMAL(10,2) NOT NULL,
    crit_damage DECIMAL(10,2) NOT NULL,
    hp INT NOT NULL,
    atk INT NOT NULL,
    def INT NOT NULL,
    stamina INT NOT NULL,
    accuracy INT NOT NULL,
    evasion INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS `map` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    range_level VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `enemy_in_map` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enemy_id BIGINT NOT NULL,
    map_id BIGINT NOT NULL,
    FOREIGN KEY (enemy_id) REFERENCES enemy(id),
    FOREIGN KEY (map_id) REFERENCES map(id)
);

CREATE TABLE IF NOT EXISTS `npc` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    type ENUM('merchant', 'quest_giver', 'trainer', 'banker', 'blacksmith') NOT NULL
);

CREATE TABLE IF NOT EXISTS `npc_item` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    npc_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (npc_id) REFERENCES npc(id)
);