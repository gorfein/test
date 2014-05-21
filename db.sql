DROP TABLE IF EXISTS `world`.`pinyin`;
CREATE  TABLE `world`.`pinyin` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `hz` VARCHAR(45) NULL ,
  `py` VARCHAR(45) NULL ,
  `zm` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );


DROP TABLE IF EXISTS `world`.`english_name`;
CREATE  TABLE `world`.`english_name` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `col1` VARCHAR(45) NULL ,
  `col2` TINYINT(1) NULL ,
  `col3` TINYINT(1) NULL ,
  `col4` TINYINT(1) NULL ,
  `col5` INT NULL ,
  PRIMARY KEY (`id`) );


load data local infile 'D:/xinDocCommon/myBaby/name/namedb-7.x-1.0-beta2/namedb/data/data.dat' into table english_name fields terminated by ','
  lines terminated by '\n'
    (name, col1, col2, col3, col4, col5);

-- Query OK, 129036 rows affected (3.75 sec)
-- Records: 129036  Deleted: 0  Skipped: 0  Warnings: 0

source D:/xinDocCommon/myBaby/name/py/py.sql
