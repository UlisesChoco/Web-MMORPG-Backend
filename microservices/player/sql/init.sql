CREATE DATABASE IF NOT EXISTS `player`;
USE `player`;

CREATE TABLE IF NOT EXISTS `player_class` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    alive BOOLEAN NOT NULL DEFAULT 1,
    gold INT NOT NULL,
    level INT NOT NULL,
    experience INT NOT NULL,
    experience_limit INT NOT NULL,
    free_stat_points INT NOT NULL,
    hp_bonus INT NOT NULL,
    atk_bonus INT NOT NULL,
    def_bonus INT NOT NULL,
    stamina_bonus INT NOT NULL,
    accuracy_bonus INT NOT NULL,
    evasion_bonus INT NOT NULL
);