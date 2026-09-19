CREATE DATABASE IF NOT EXISTS `mmorpg`;
USE `mmorpg`;

CREATE TABLE IF NOT EXISTS `users` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status ENUM('PENDING', 'ACTIVE', 'BANNED') NOT NULL DEFAULT 'PENDING',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

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

CREATE TABLE IF NOT EXISTS `item` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    gold INT NOT NULL,
    required_level INT NOT NULL,
    type ENUM('WEAPON', 'ARMOR', 'ACCESSORY', 'CONSUMABLE', 'MATERIAL') NOT NULL,
    slot ENUM(
        'HEAD',
        'CHEST',
        'LEGS',
        'FEET',
        'HANDS',
        'MAIN_HAND',
        'OFF_HAND',
        'RING',
        'NECKLACE',
        'NONE'
    ) NOT NULL,
    hp_bonus INT NOT NULL,
    atk_bonus INT NOT NULL,
    def_bonus INT NOT NULL,
    stamina_bonus INT NOT NULL,
    accuracy_bonus INT NOT NULL,
    evasion_bonus INT NOT NULL,
    crit_rate_bonus FLOAT NOT NULL,
    crit_damage_bonus FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS `players` (
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
    evasion_bonus INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (class_id) REFERENCES player_class(id)
);

CREATE TABLE IF NOT EXISTS `enemy` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    type ENUM(
        'NORMAL',
        'ELITE',
        'MINIBOSS',
        'BOSS',
        'LEGENDARY'
    ) NOT NULL,
    level INT NOT NULL,
    experience INT NOT NULL,
    gold INT NOT NULL,
    crit_rate FLOAT NOT NULL,
    crit_damage FLOAT NOT NULL,
    hp INT NOT NULL,
    atk INT NOT NULL,
    def INT NOT NULL,
    stamina INT NOT NULL,
    accuracy INT NOT NULL,
    evasion INT NOT NULL
);

CREATE TABLE IF NOT EXISTS `tower` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    floor INT NOT NULL,
    level_range VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `map` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    range_level VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `npc` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    type ENUM(
        'MERCHANT',
        'QUEST_GIVER',
        'TRAINER',
        'BANKER',
        'BLACKSMITH'
    ) NOT NULL
);

CREATE TABLE IF NOT EXISTS `combat_history` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    enemy_id BIGINT NOT NULL,
    total_turns INT NOT NULL,
    was_fatal BOOLEAN NOT NULL,
    date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES players(id),
    FOREIGN KEY (enemy_id) REFERENCES enemy(id)
);

CREATE TABLE IF NOT EXISTS `fatal_combat_replay` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    combat_history_id BIGINT NOT NULL,
    turn_log LONGTEXT NOT NULL,
    FOREIGN KEY (combat_history_id) REFERENCES combat_history(id)
);

CREATE TABLE IF NOT EXISTS `player_inventory_item` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    equipped BOOLEAN NOT NULL,
    FOREIGN KEY (player_id) REFERENCES players(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE IF NOT EXISTS `enemy_item_drop` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enemy_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    probability FLOAT NOT NULL,
    FOREIGN KEY (enemy_id) REFERENCES enemy(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE IF NOT EXISTS `tower_enemy` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tower_id BIGINT NOT NULL,
    enemy_id BIGINT NOT NULL,
    FOREIGN KEY (tower_id) REFERENCES tower(id),
    FOREIGN KEY (enemy_id) REFERENCES enemy(id)
);

CREATE TABLE IF NOT EXISTS `tower_player_progress` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tower_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    FOREIGN KEY (tower_id) REFERENCES tower(id),
    FOREIGN KEY (player_id) REFERENCES players(id)
);

CREATE TABLE IF NOT EXISTS `enemy_in_map` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enemy_id BIGINT NOT NULL,
    map_id BIGINT NOT NULL,
    FOREIGN KEY (enemy_id) REFERENCES enemy(id),
    FOREIGN KEY (map_id) REFERENCES map(id)
);

CREATE TABLE IF NOT EXISTS `npc_item` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    npc_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (npc_id) REFERENCES npc(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);
