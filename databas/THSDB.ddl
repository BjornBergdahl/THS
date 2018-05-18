-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`processLead`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`processLead` (
  `processLeadNo` INT NOT NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  PRIMARY KEY (`processLeadNo`),
  UNIQUE INDEX `processLeadID_UNIQUE` (`processLeadNo` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`supportPersonnel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`supportPersonnel` (
  `staffNo` INT NOT NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `competence` VARCHAR(45) NULL,
  PRIMARY KEY (`staffNo`),
  UNIQUE INDEX `staffID_UNIQUE` (`staffNo` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ticket` (
  `tktNo` INT NOT NULL AUTO_INCREMENT,
  `processLeadNo` INT NULL,
  `staffNo` INT NULL,
  `name` VARCHAR(45) NULL,
  `status` VARCHAR(45) NULL DEFAULT 'unassigned',
  `category` VARCHAR(45) NULL,
  PRIMARY KEY (`tktNo`),
  UNIQUE INDEX `tktNo_UNIQUE` (`tktNo` ASC),
  INDEX `processLeadID_idx` (`processLeadNo` ASC),
  INDEX `staffNo_idx` (`staffNo` ASC),
  CONSTRAINT `processLeadNo`
    FOREIGN KEY (`processLeadNo`)
    REFERENCES `mydb`.`processLead` (`processLeadNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `staffNo`
    FOREIGN KEY (`staffNo`)
    REFERENCES `mydb`.`supportPersonnel` (`staffNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`comment` (
  `commentNo` INT NOT NULL AUTO_INCREMENT,
  `tktNoCom` INT NULL,
  `text` VARCHAR(180) NULL,
  PRIMARY KEY (`commentNo`),
  INDEX `tktNo_idx` (`tktNoCom` ASC),
  CONSTRAINT `tktNoCom`
    FOREIGN KEY (`tktNoCom`)
    REFERENCES `mydb`.`ticket` (`tktNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`task` (
  `tskNo` INT NOT NULL AUTO_INCREMENT,
  `tktNoTsk` INT NULL,
  `name` VARCHAR(45) NULL,
  `text` VARCHAR(180) NULL,
  `timeBudgetMinutes` INT NULL,
  `timeSpentMinutes` INT NULL,
  PRIMARY KEY (`tskNo`),
  INDEX `tktNo_idx` (`tktNoTsk` ASC),
  CONSTRAINT `tktNoTsk`
    FOREIGN KEY (`tktNoTsk`)
    REFERENCES `mydb`.`ticket` (`tktNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
