CREATE DATABASE IF NOT EXISTS `combat`;
USE `combat`;

CREATE TABLE IF NOT EXISTS `combat_history` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    enemy_id BIGINT NOT NULL,
    total_turns INT NOT NULL,
    was_fatal TINYINT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `fatal_combat_replay` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    combat_history_id BIGINT NOT NULL,
    turn_log LONGTEXT NOT NULL,
    FOREIGN KEY (combat_history_id) REFERENCES combat_history(id)
);
