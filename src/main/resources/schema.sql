DROP TABLE IF EXISTS stock_quote;

CREATE TABLE `stock_quote` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `symbol` VARCHAR(45) NULL,
  `price` DECIMAL(16) NULL,
  `volume` INT NULL,
  `date` DATETIME NULL,
  `year` VARCHAR(45) NULL,
  `month` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));