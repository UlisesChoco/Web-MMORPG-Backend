CREATE DATABASE IF NOT EXISTS `tower`;
USE `tower`;

CREATE TABLE IF NOT EXISTS `tower` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    floor INT NOT NULL,
    level_range VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tower_enemy` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tower_id BIGINT NOT NULL,
    enemy_id BIGINT NOT NULL,
    FOREIGN KEY (tower_id) REFERENCES tower(id)
);

CREATE TABLE IF NOT EXISTS `tower_player_progress` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tower_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    FOREIGN KEY (tower_id) REFERENCES tower(id)
);