CREATE DATABASE IF NOT EXISTS `inventory`;
USE `inventory`;

CREATE TABLE IF NOT EXISTS `item` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    gold INT NOT NULL,
    required_level INT NOT NULL,
    type ENUM('weapon', 'armor', 'accessory', 'consumable', 'material') NOT NULL,
    slot ENUM('head', 'chest', 'legs', 'feet', 'hands', 'main_hand', 'off_hand', 'ring', 'necklace', 'none') NOT NULL,
    hp_bonus INT NOT NULL,
    atk_bonus INT NOT NULL,
    def_bonus INT NOT NULL,
    stamina_bonus INT NOT NULL,
    accuracy_bonus INT NOT NULL,
    evasion_bonus INT NOT NULL,
    crit_rate_bonus FLOAT NOT NULL,
    crit_damage_bonus FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS `player_inventory_item`(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    equipped BOOLEAN NOT NULL,
    FOREIGN KEY (item_id) REFERENCES item(id)
);