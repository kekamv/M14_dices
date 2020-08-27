DROP SCHEMA IF EXISTS dices_game;
CREATE SCHEMA IF NOT EXISTS dices_game DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE dices_game;

DROP TABLE IF EXISTS players;

CREATE TABLE IF NOT EXISTS players(
`id` INT AUTO_INCREMENT,
player_name VARCHAR(90) NULL DEFAULT "Anonymous",
entry_date DATE DEFAULT (CURRENT_DATE),
PRIMARY KEY (id))
ENGINE = InnoDB;

DROP TABLE IF EXISTS games;

CREATE TABLE IF NOT EXISTS games(
id INT AUTO_INCREMENT,
game_number INT,
dice1_value INT NOT NULL,
dice2_value INT NOT NULL CHECK (dice2_value BETWEEN 1 AND 6),
game_score INT NOT NULL,
player_id INT NOT NULL,
PRIMARY KEY (id),
INDEX fk_players1_idx (player_id ASC),
CONSTRAINT fk_players1
	FOREIGN KEY (player_id)
	REFERENCES dices_game.players(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
CONSTRAINT dice1_value
	CHECK (dice1_value BETWEEN 1 AND 6))
ENGINE = InnoDB;



