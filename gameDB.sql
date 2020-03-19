-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema games
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema games
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `games` DEFAULT CHARACTER SET utf8 ;
USE `games` ;

-- -----------------------------------------------------
-- Table `games`.`Player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`Player` (
  `id` BIGINT NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `join_date` DATE NULL,
  `email` VARCHAR(45) NULL,
  `creditCard` BIGINT NULL,
  PRIMARY KEY (`id`),
  INDEX `creditCard_idx` (`creditCard` ASC) VISIBLE,
  CONSTRAINT `creditCard`
    FOREIGN KEY (`creditCard`)
    REFERENCES `games`.`CreditCard` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`CreditCard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`CreditCard` (
  `id` BIGINT NOT NULL,
  `playerID` BIGINT NULL,
  `ccName` VARCHAR(45) NULL,
  `ccNumber` VARCHAR(45) NULL,
  `securityCode` INT NULL,
  `expDate` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `playerID_idx` (`playerID` ASC) VISIBLE,
  CONSTRAINT `playerID`
    FOREIGN KEY (`playerID`)
    REFERENCES `games`.`Player` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`Game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`Game` (
  `id` BIGINT NOT NULL,
  `title` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `releaseDate` DATE NULL,
  `version` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`GamesPlayed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`GamesPlayed` (
  `id` BIGINT NOT NULL,
  `playerID` BIGINT NULL,
  `gameID` BIGINT NULL,
  `timeFinished` DATE NULL,
  `score` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `gameID_idx` (`gameID` ASC) VISIBLE,
  CONSTRAINT `GPplayerID`
    FOREIGN KEY (`playerID`)
    REFERENCES `games`.`Player` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `GPgameID`
    FOREIGN KEY (`gameID`)
    REFERENCES `games`.`Game` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `games`.`GamesOwned`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `games`.`GamesOwned` (
  `id` BIGINT NOT NULL,
  `playerID` BIGINT NULL,
  `gameID` BIGINT NULL,
  `purchaseDate` DATE NULL,
  `purchasePrice` DECIMAL NULL,
  PRIMARY KEY (`id`),
  INDEX `playerID_idx` (`playerID` ASC) VISIBLE,
  INDEX `gameID_idx` (`gameID` ASC) VISIBLE,
  CONSTRAINT `GOplayerID`
    FOREIGN KEY (`playerID`)
    REFERENCES `games`.`Player` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `GOgameID`
    FOREIGN KEY (`gameID`)
    REFERENCES `games`.`Game` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
