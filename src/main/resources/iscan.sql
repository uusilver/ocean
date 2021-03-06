/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost
 Source Database       : iscan

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : utf-8

 Date: 05/03/2016 23:55:02 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `M_USER_QRCODE`
-- ----------------------------
DROP TABLE IF EXISTS `M_USER_QRCODE`;
CREATE TABLE `M_USER_QRCODE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` varchar(40) DEFAULT NULL,
  `product_batch` varchar(40) DEFAULT NULL,
  `qr_query_string` varchar(120) NOT NULL,
  `query_times` int(11) DEFAULT NULL,
  `query_date` varchar(20) DEFAULT NULL,
  `active_flag` varchar(3) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  `query_match` varchar(30) DEFAULT NULL,
  `vistor_ip_addr` varchar(18) DEFAULT NULL,
  `vistor_phy_addr` varchar(25) DEFAULT NULL,
  `ip_check_flag` varchar(2) DEFAULT NULL,
  `website_query_times` int(11) DEFAULT NULL,
  `website_query_date` varchar(20) DEFAULT NULL,
  `cache_flag` char(1) DEFAULT NULL,
  `lottery_flag` char(1) DEFAULT NULL,
  `lottery_desc` varchar(14) DEFAULT NULL,
  `get_lottery_flag` char(1) DEFAULT NULL,
  `delete_flag` char(1) DEFAULT NULL,
  `lottery_check_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=241607 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `M_USER_QRCODE`
-- ----------------------------
BEGIN;
INSERT INTO `M_USER_QRCODE` VALUES ('241572', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501s5MSrK9U', '0', null, 'N', '2016-05-01 20:28:27', '00120160501s5MSrK9U', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241573', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501xBlq7Anv', '0', null, 'N', '2016-05-01 20:28:27', '00120160501xBlq7Anv', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241574', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501luWDAweT', '7', '2016-05-03 23:44:990', 'N', '2016-05-01 20:28:27', '00120160501luWDAweT', '0:0:0:0:0:0:0:1', null, 'N', null, null, 'Y', 'N', null, '\0', 'Y', 'Y'), ('241575', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501BHMEGxIS', '0', null, 'N', '2016-05-01 20:28:27', '00120160501BHMEGxIS', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241576', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501OmpB0L7z', '0', null, 'N', '2016-05-01 20:28:27', '00120160501OmpB0L7z', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241577', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501mx0RTNRd', '0', null, 'N', '2016-05-01 20:28:27', '00120160501mx0RTNRd', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241578', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?001201605013iMcFXzR', '0', null, 'N', '2016-05-01 20:28:27', '001201605013iMcFXzR', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241579', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501iAZxva0e', '0', null, 'N', '2016-05-01 20:28:27', '00120160501iAZxva0e', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241580', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501BPSWooRN', '0', null, 'N', '2016-05-01 20:28:27', '00120160501BPSWooRN', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'Y', 'N'), ('241581', '1', 'RuojhTWR', '201601', 'http://a.315kc.com/m/r/a/i.htm?00120160501VbE62fGu', '1', '2016-05-03 23:45:892', 'N', '2016-05-01 20:28:27', '00120160501VbE62fGu', '0:0:0:0:0:0:0:1', null, 'N', null, null, 'Y', 'N', null, '\0', 'Y', 'Y'), ('241582', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?001201605019VoMscgf', '0', null, 'N', '2016-05-01 20:45:15', '001201605019VoMscgf', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241583', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160501uDSWnsIn', '0', null, 'N', '2016-05-01 20:45:15', '00120160501uDSWnsIn', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241584', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160501XsFUTq4z', '0', null, 'N', '2016-05-01 20:45:15', '00120160501XsFUTq4z', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241585', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160501HWqpzv3l', '0', null, 'N', '2016-05-01 20:45:15', '00120160501HWqpzv3l', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241586', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160501yZXeSdrg', '0', null, 'N', '2016-05-01 20:45:15', '00120160501yZXeSdrg', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241587', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?001201605013FPJrYrW', '0', null, 'N', '2016-05-01 20:45:15', '001201605013FPJrYrW', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241588', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160501QpUf549Y', '28', '2016-05-03 23:39:941', 'N', '2016-05-01 20:45:15', '00120160501QpUf549Y', '0:0:0:0:0:0:0:1', null, 'N', null, null, 'N', 'Y', '恭喜你中奖:二等奖', '\0', 'N', 'Y'), ('241589', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?0012016050134b1PAKw', '0', null, 'N', '2016-05-01 20:45:15', '0012016050134b1PAKw', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241590', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160501BB8TCHxA', '0', null, 'N', '2016-05-01 20:45:15', '00120160501BB8TCHxA', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241591', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?001201605010hWPcTbW', '0', null, 'N', '2016-05-01 20:45:15', '001201605010hWPcTbW', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241592', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501cJCbAiwV', '0', null, 'N', '2016-05-01 20:51:45', '00120160501cJCbAiwV', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241593', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501slBSQ1ny', '0', null, 'N', '2016-05-01 20:51:45', '00120160501slBSQ1ny', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241594', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501UYw63AGB', '0', null, 'N', '2016-05-01 20:51:45', '00120160501UYw63AGB', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241595', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501DpNgPoRB', '0', null, 'N', '2016-05-01 20:51:45', '00120160501DpNgPoRB', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241596', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501P5q495Si', '0', null, 'N', '2016-05-01 20:51:45', '00120160501P5q495Si', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241597', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501HtsQpf6Y', '0', null, 'N', '2016-05-01 20:51:45', '00120160501HtsQpf6Y', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241598', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501aHdJZaIg', '0', null, 'N', '2016-05-01 20:51:45', '00120160501aHdJZaIg', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241599', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?001201605012hyjHnp8', '0', null, 'N', '2016-05-01 20:51:45', '001201605012hyjHnp8', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241600', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501NhSg8yoh', '0', null, 'N', '2016-05-01 20:51:45', '00120160501NhSg8yoh', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241601', '1', 'RuojhTWR', '201603', 'http://a.315kc.com/m/r/a/i.htm?00120160501RNBk6rl2', '0', null, 'N', '2016-05-01 20:51:45', '00120160501RNBk6rl2', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241602', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160503hhUc0HMc', '0', null, 'N', '2016-05-03 23:53:11', '00120160503hhUc0HMc', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241603', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160503G2AJrV7N', '0', null, 'N', '2016-05-03 23:53:11', '00120160503G2AJrV7N', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241604', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160503JvoSrL2M', '0', null, 'N', '2016-05-03 23:53:11', '00120160503JvoSrL2M', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241605', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?00120160503ckgBgxn8', '0', null, 'N', '2016-05-03 23:53:11', '00120160503ckgBgxn8', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N'), ('241606', '1', 'RuojhTWR', '201602', 'http://a.315kc.com/m/r/a/i.htm?001201605032jC582RP', '0', null, 'N', '2016-05-03 23:53:11', '001201605032jC582RP', null, null, 'N', null, null, 'Y', 'N', '\0', '\0', 'N', 'N');
COMMIT;

-- ----------------------------
--  Table structure for `Test`
-- ----------------------------
DROP TABLE IF EXISTS `Test`;
CREATE TABLE `Test` (
  `a` char(1) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `User`
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `query_qrcode_table` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `user_type` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `user_email` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `user_telno` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `user_factory_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `user_factory_address` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `user_contact_person_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `active_flag` int(1) DEFAULT NULL,
  `agency_id` int(10) DEFAULT NULL,
  `create_date` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `expire_date` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `lottery_ability_flag` char(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `User`
-- ----------------------------
BEGIN;
INSERT INTO `User` VALUES ('1', 'admin', 'X03MO1qnZdYdgyfeuILPmQ==', 'M_USER_QRCODE', 'A类:a:y', '179012331@qq.com', '139119403132', '井冈山蜜柚公司', '井冈山', '王经理', '1', '1', '2015-12-12', '2016-12-12', 'Y');
COMMIT;

-- ----------------------------
--  Table structure for `agency_user`
-- ----------------------------
DROP TABLE IF EXISTS `agency_user`;
CREATE TABLE `agency_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(35) DEFAULT NULL,
  `hold_amount` int(11) DEFAULT NULL,
  `lst_login_date` varchar(20) DEFAULT NULL,
  `active_flag` int(11) DEFAULT NULL,
  `province` varchar(10) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `telno` varchar(11) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `agency_user`
-- ----------------------------
BEGIN;
INSERT INTO `agency_user` VALUES ('1', 'jiangsu', 'X03MO1qnZdYdgyfeuILPmQ==', '1000000', null, '1', '江苏', '南京', '13851483034', '南京', '李先生');
COMMIT;

-- ----------------------------
--  Table structure for `m_factory_order`
-- ----------------------------
DROP TABLE IF EXISTS `m_factory_order`;
CREATE TABLE `m_factory_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_content` varchar(200) DEFAULT NULL,
  `receive_person` varchar(20) DEFAULT NULL,
  `telno` varchar(18) DEFAULT NULL,
  `receive_address` varchar(50) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `send_flag` varchar(2) DEFAULT NULL,
  `send_to_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_factory_order`
-- ----------------------------
BEGIN;
INSERT INTO `m_factory_order` VALUES ('24', '客户:123订购价格为258的柚子:1箱,客户地址是:123,客户的电话是:213,客户备注内容: 123。', '123', '213', '123', ' 123', 'Y', '0'), ('25', '客户:订购价格为258的柚子:1箱,客户地址是:,客户的电话是:,客户备注内容: 。', '', '', '', ' ', 'Y', '1');
COMMIT;

-- ----------------------------
--  Table structure for `m_product_show_info`
-- ----------------------------
DROP TABLE IF EXISTS `m_product_show_info`;
CREATE TABLE `m_product_show_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` varchar(40) DEFAULT NULL,
  `batch_no` varchar(10) DEFAULT NULL,
  `produce_date` varchar(15) DEFAULT NULL,
  `produce_address` varchar(40) DEFAULT NULL,
  `sell_area` varchar(15) DEFAULT NULL,
  `sell_author` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_product_show_info`
-- ----------------------------
BEGIN;
INSERT INTO `m_product_show_info` VALUES ('4', '1', '6ebe7af5-437c-4999-9a1c-84181089889b', 'ttt', '2015-12-08', '中国', '中国1', '南京经销商');
COMMIT;

-- ----------------------------
--  Table structure for `m_user_account`
-- ----------------------------
DROP TABLE IF EXISTS `m_user_account`;
CREATE TABLE `m_user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `account` int(11) DEFAULT NULL,
  `currency` decimal(10,2) DEFAULT NULL,
  `qr_total_user` varchar(30) DEFAULT NULL,
  `scan_total_user` varchar(30) DEFAULT NULL,
  `warning_qr_code_no` varchar(30) DEFAULT NULL,
  `user_vistor_report` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_user_account`
-- ----------------------------
BEGIN;
INSERT INTO `m_user_account` VALUES ('1', '1', '758594', '859.00', '1000', '500', '15', null);
COMMIT;

-- ----------------------------
--  Table structure for `m_user_account_opt`
-- ----------------------------
DROP TABLE IF EXISTS `m_user_account_opt`;
CREATE TABLE `m_user_account_opt` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `account_purchase` int(10) DEFAULT NULL,
  `current_left` decimal(10,2) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `reason` varchar(10) DEFAULT NULL,
  `account_consume` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_user_account_opt`
-- ----------------------------
BEGIN;
INSERT INTO `m_user_account_opt` VALUES ('1', '1', '100', '800.00', '2015-11-29 16:07:57', '充值', null), ('2', '1', '100', '880.00', '2015-11-29 16:10:20', '充值', null), ('3', '1', '100', '870.00', '2015-11-29 16:10:29', '充值', null), ('9', '1', '100', '860.00', '2015-11-29 16:20:51', '充值', null), ('11', '1', '100', '849.00', '2015-11-29 16:21:04', '充值', null), ('12', '1', null, '859.00', '2015-11-29 16:52:48', '二维码生产', '10'), ('13', '1', null, '859.00', '2015-11-29 17:08:42', '二维码生产', '10'), ('14', '1', null, '859.00', '2015-11-29 17:19:05', '二维码生产', '5'), ('15', '1', null, '859.00', '2015-11-29 17:19:55', '二维码生产', '5'), ('16', '1', null, '859.00', '2015-11-29 17:20:54', '二维码生产', '3'), ('17', '1', null, '859.00', '2015-12-04 23:57:27', '二维码生产', '10'), ('18', '1', null, '859.00', '2015-12-05 00:03:53', '二维码生产', '10'), ('19', '1', null, '859.00', '2015-12-05 00:29:57', '二维码生产', '1'), ('20', '1', null, '859.00', '2015-12-05 00:34:49', '二维码生产', '1'), ('21', '1', null, '859.00', '2015-12-06 21:38:26', '二维码生产', '1'), ('22', '1', null, '859.00', '2015-12-06 23:06:45', '二维码生产', '1'), ('23', '1', null, '859.00', '2015-12-06 23:08:48', '二维码生产', '1'), ('24', '1', null, '859.00', '2015-12-12 14:46:09', '二维码生产', '2'), ('25', '1', null, '859.00', '2015-12-12 22:47:29', '二维码生产', '2'), ('26', '1', null, '859.00', '2015-12-12 23:44:02', '二维码生产', '1'), ('27', '1', null, '859.00', '2015-12-13 13:02:48', '二维码生产', '2'), ('28', '1', null, '859.00', '2015-12-13 23:29:00', '二维码生产', '2'), ('29', '1', null, '859.00', '2015-12-13 23:29:17', '二维码生产', '2'), ('30', '1', null, '859.00', '2015-12-16 13:29:27', '二维码生产', '1'), ('31', '1', null, '859.00', '2015-12-16 13:29:59', '二维码生产', '1'), ('32', '1', null, '859.00', '2015-12-16 13:51:37', '二维码生产', '1'), ('33', '1', null, '859.00', '2015-12-16 23:53:14', '二维码生产', '1'), ('34', '1', null, '859.00', '2015-12-16 23:55:41', '二维码生产', '2'), ('35', '1', null, '859.00', '2015-12-17 00:01:40', '二维码生产', '1'), ('36', '1', null, '859.00', '2015-12-17 00:01:49', '二维码生产', '2'), ('37', '1', null, '859.00', '2015-12-17 00:03:39', '二维码生产', '2'), ('38', '1', null, '859.00', '2015-12-17 00:05:46', '二维码生产', '1'), ('39', '1', null, '859.00', '2015-12-17 00:05:52', '二维码生产', '3'), ('40', '1', null, '859.00', '2015-12-17 09:45:28', '二维码生产', '2'), ('41', '1', null, '859.00', '2015-12-17 14:19:36', '二维码生产', '1'), ('42', '1', null, '859.00', '2015-12-17 14:46:32', '二维码生产', '2'), ('43', '1', null, '859.00', '2015-12-17 14:46:48', '二维码生产', '3'), ('44', '1', null, '859.00', '2015-12-17 23:14:01', '二维码生产', '1'), ('45', '1', null, '859.00', '2015-12-22 23:03:27', '二维码生产', '100'), ('46', '1', null, '859.00', '2015-12-22 23:04:45', '二维码生产', '100'), ('47', '1', null, '859.00', '2015-12-22 23:24:25', '二维码生产', '50000'), ('48', '1', null, '859.00', '2015-12-22 23:24:28', '二维码生产', '50000'), ('49', '1', null, '859.00', '2015-12-22 23:24:28', '二维码生产', '50000'), ('50', '1', null, '859.00', '2015-12-22 23:29:06', '二维码生产', '1000'), ('51', '1', null, '859.00', '2015-12-22 23:30:12', '二维码生产', '10000'), ('52', '1', null, '859.00', '2015-12-22 23:32:51', '二维码生产', '50000'), ('53', '1', null, '859.00', '2015-12-22 23:53:46', '二维码生产', '10000'), ('54', '1', null, '859.00', '2015-12-22 23:55:32', '二维码生产', '10000'), ('55', '1', null, '859.00', '2015-12-23 22:10:49', '二维码生产', '9999'), ('56', '1', null, '859.00', '2015-12-26 00:06:07', '二维码生产', '1'), ('57', '1', null, '859.00', '2015-12-26 13:18:50', '二维码生产', '1'), ('58', '1', null, '859.00', '2015-12-26 13:21:59', '二维码生产', '1'), ('59', '1', null, '859.00', '2015-12-26 13:25:11', '二维码生产', '1'), ('60', '1', null, '859.00', '2015-12-26 13:26:42', '二维码生产', '1'), ('61', '1', null, '859.00', '2015-12-26 13:28:48', '二维码生产', '12'), ('62', '1', null, '859.00', '2015-12-26 13:29:59', '二维码生产', '1'), ('63', '1', null, '859.00', '2015-12-26 13:31:02', '二维码生产', '1'), ('64', '1', null, '859.00', '2015-12-26 13:32:34', '二维码生产', '2'), ('65', '1', null, '859.00', '2015-12-26 13:34:56', '二维码生产', '1'), ('66', '1', null, '859.00', '2015-12-26 15:23:19', '二维码生产', '50'), ('67', '1', null, '859.00', '2015-12-26 19:41:35', '二维码生产', '3'), ('68', '1', null, '859.00', '2015-12-26 19:42:11', '二维码生产', '1'), ('69', '1', null, '859.00', '2015-12-26 19:45:02', '二维码生产', '1'), ('70', '1', null, '859.00', '2015-12-26 19:46:37', '二维码生产', '1'), ('71', '1', null, '859.00', '2015-12-26 19:47:18', '二维码生产', '1'), ('72', '1', null, '859.00', '2015-12-26 19:51:57', '二维码生产', '1'), ('73', '1', null, '859.00', '2015-12-26 19:54:01', '二维码生产', '1'), ('74', '1', null, '859.00', '2015-12-26 21:59:18', '二维码生产', '20'), ('75', '1', null, '859.00', '2015-12-26 22:23:25', '二维码生产', '100'), ('76', '1', null, '859.00', '2015-12-30 13:54:53', '二维码生产', '100'), ('77', '1', null, '859.00', '2016-01-01 09:33:46', '二维码生产', '20'), ('78', '1', null, '859.00', '2016-01-01 14:33:45', '二维码生产', '3'), ('79', '1', null, '859.00', '2016-03-19 12:01:39', '二维码生产', '1'), ('80', '1', null, '859.00', '2016-03-19 12:24:03', '二维码生产', '10'), ('81', '1', null, '859.00', '2016-04-02 17:51:42', '二维码生产', '10'), ('82', '1', null, '859.00', '2016-04-02 23:23:43', '二维码生产', '1'), ('83', '1', null, '859.00', '2016-04-19 20:07:15', '二维码生产', '10'), ('84', '1', null, '859.00', '2016-04-19 20:07:42', '二维码生产', '1'), ('85', '1', null, '859.00', '2016-04-25 22:51:40', '二维码生产', '10'), ('86', '1', null, '859.00', '2016-04-28 22:04:25', '二维码生产', '5'), ('87', '1', null, '859.00', '2016-05-01 20:28:27', '二维码生产', '10'), ('88', '1', null, '859.00', '2016-05-01 20:45:15', '二维码生产', '10'), ('89', '1', null, '859.00', '2016-05-01 20:51:45', '二维码生产', '10'), ('90', '1', null, '859.00', '2016-05-03 23:53:11', '二维码生产', '5');
COMMIT;

-- ----------------------------
--  Table structure for `m_user_advice_template`
-- ----------------------------
DROP TABLE IF EXISTS `m_user_advice_template`;
CREATE TABLE `m_user_advice_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `template_name` varchar(30) DEFAULT NULL,
  `template_label` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_user_advice_template`
-- ----------------------------
BEGIN;
INSERT INTO `m_user_advice_template` VALUES ('1', '1', 'y', '柚子模版'), ('3', '1', 'a', '通用模版'), ('4', '1', 'j', '酒类模版'), ('5', '1', 't', '桃子模版');
COMMIT;

-- ----------------------------
--  Table structure for `m_user_category`
-- ----------------------------
DROP TABLE IF EXISTS `m_user_category`;
CREATE TABLE `m_user_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `category_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_user_category`
-- ----------------------------
BEGIN;
INSERT INTO `m_user_category` VALUES ('14', '1', '国产黄酒'), ('31', '1', '国产白酒'), ('32', '1', '白酒'), ('33', '1', '蜜柚'), ('34', '1', '水蜜桃');
COMMIT;

-- ----------------------------
--  Table structure for `m_user_params`
-- ----------------------------
DROP TABLE IF EXISTS `m_user_params`;
CREATE TABLE `m_user_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL,
  `param_value` varchar(50) DEFAULT NULL,
  `remarks1` varchar(50) DEFAULT NULL,
  `remarks2` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_user_params`
-- ----------------------------
BEGIN;
INSERT INTO `m_user_params` VALUES ('1', 'ud', '产品生产时间', null, null, '1'), ('2', 'sl', '经销商信息', null, null, '1'), ('3', 'lqd', '上次查询时间', null, null, '1');
COMMIT;

-- ----------------------------
--  Table structure for `m_user_product`
-- ----------------------------
DROP TABLE IF EXISTS `m_user_product`;
CREATE TABLE `m_user_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` varchar(100) NOT NULL,
  `relate_batch` varchar(30) DEFAULT NULL,
  `qrcode_total_no` int(11) DEFAULT NULL,
  `update_time` varchar(30) DEFAULT NULL,
  `advice_temp` varchar(20) DEFAULT NULL,
  `batch_params` varchar(255) DEFAULT NULL,
  `sellArthor` varchar(50) DEFAULT NULL,
  `lottery_info` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_user_product`
-- ----------------------------
BEGIN;
INSERT INTO `m_user_product` VALUES ('69', '1', 'RuojhTWR', '201601', '10', '2016-05-01 20:21:35', 'a', '[\"ud\"]', '', '一等奖:0-1|10000'), ('70', '1', 'RuojhTWR', '201602', '15', '2016-05-01 20:44:47', 'a', '[\"ud\"]', '', '一等奖:0-1&二等奖:1-40&三等奖:40-50|10'), ('71', '1', 'RuojhTWR', '201603', '10', '2016-05-01 20:51:35', 'a', '[]', '', null);
COMMIT;

-- ----------------------------
--  Table structure for `m_user_product_meta`
-- ----------------------------
DROP TABLE IF EXISTS `m_user_product_meta`;
CREATE TABLE `m_user_product_meta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(30) DEFAULT NULL,
  `product_category` varchar(30) DEFAULT NULL,
  `qrcode_total_no` int(11) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `product_desc` varchar(50) DEFAULT NULL,
  `user_id` varchar(11) DEFAULT NULL,
  `product_id` varchar(20) DEFAULT NULL,
  `advice_temp` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `m_user_product_meta`
-- ----------------------------
BEGIN;
INSERT INTO `m_user_product_meta` VALUES ('33', '洋河蓝色经典', '白酒', '35', '2016-05-01 20:21:09', '测试白酒', '1', 'RuojhTWR', 'default');
COMMIT;

-- ----------------------------
--  Table structure for `system_meta_table`
-- ----------------------------
DROP TABLE IF EXISTS `system_meta_table`;
CREATE TABLE `system_meta_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_message` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `system_meta_table`
-- ----------------------------
BEGIN;
INSERT INTO `system_meta_table` VALUES ('3', '315快查会员系统1.0发布');
COMMIT;

-- ----------------------------
--  Table structure for `user_statistics`
-- ----------------------------
DROP TABLE IF EXISTS `user_statistics`;
CREATE TABLE `user_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_visting_statistic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
