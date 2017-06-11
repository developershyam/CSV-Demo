
CREATE DATABASE csvdemo CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Table structure for table `accumulate_ordering_currency`
DROP TABLE IF EXISTS `accumulate_ordering_currency`;
CREATE TABLE `accumulate_ordering_currency` (
  `from_currency_iso_code` varchar(255) NOT NULL,
  `total_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`from_currency_iso_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table structure for table `invalid_deal`
DROP TABLE IF EXISTS `invalid_deal`;
CREATE TABLE `invalid_deal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) DEFAULT NULL,
  `deal_time_stamp` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `from_currency_iso_code` varchar(255) DEFAULT NULL,
  `from_currency_iso_code_amount` float DEFAULT NULL,
  `to_currency_iso_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table structure for table `valid_deal`
DROP TABLE IF EXISTS `valid_deal`;
CREATE TABLE `valid_deal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) DEFAULT NULL,
  `deal_time_stamp` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `from_currency_iso_code` varchar(255) DEFAULT NULL,
  `from_currency_iso_code_amount` float DEFAULT NULL,
  `to_currency_iso_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

