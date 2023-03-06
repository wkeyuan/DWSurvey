CREATE DATABASE  IF NOT EXISTS `dwsurvey` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `dwsurvey`;
/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost
 Source Database       : dwsurvey_oss

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : utf-8

 Date: 12/31/2021 09:51:23 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_an_answer`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_answer`;
CREATE TABLE `t_an_answer` (
  `id` varchar(55) NOT NULL,
  `answer` text,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_checkbox`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_checkbox`;
CREATE TABLE `t_an_checkbox` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `other_text` varchar(3255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_item_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_an_checkbox`
-- ----------------------------
BEGIN;
INSERT INTO `t_an_checkbox` VALUES ('78a0fc8f-21fd-4ada-b58e-1d8bece01188', '2a793ef9-28d9-4e99-afd5-a009c6356269', '58960016-ff92-4de7-9585-9ac27f961247', null, '69fe0041-757b-4a91-91e3-f990e1abd38d', '60fd78c7-964f-4318-822d-b9179d1ae3c7', '1'), ('e8f976a9-8224-44b1-879a-9a0b31ae3f55', '2a793ef9-28d9-4e99-afd5-a009c6356269', '58960016-ff92-4de7-9585-9ac27f961247', null, '69fe0041-757b-4a91-91e3-f990e1abd38d', '60515dd8-bb01-414d-adb2-2e0aec0031e4', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_an_chen_checkbox`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_chen_checkbox`;
CREATE TABLE `t_an_chen_checkbox` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_chen_fbk`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_chen_fbk`;
CREATE TABLE `t_an_chen_fbk` (
  `id` varchar(55) NOT NULL,
  `answer_value` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_chen_radio`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_chen_radio`;
CREATE TABLE `t_an_chen_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_chen_score`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_chen_score`;
CREATE TABLE `t_an_chen_score` (
  `id` varchar(55) NOT NULL,
  `answser_score` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_comp_chen_radio`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_comp_chen_radio`;
CREATE TABLE `t_an_comp_chen_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_option_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_dfillblank`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_dfillblank`;
CREATE TABLE `t_an_dfillblank` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(3255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_item_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_enumqu`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_enumqu`;
CREATE TABLE `t_an_enumqu` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `enum_item` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_fillblank`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_fillblank`;
CREATE TABLE `t_an_fillblank` (
  `id` varchar(55) NOT NULL,
  `answer` text,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_order`;
CREATE TABLE `t_an_order` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `ordery_num` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_radio`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_radio`;
CREATE TABLE `t_an_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `other_text` varchar(3255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_item_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_an_radio`
-- ----------------------------
BEGIN;
INSERT INTO `t_an_radio` VALUES ('afb58f3c-c983-4b13-859f-1d4f06d01dea', '2a793ef9-28d9-4e99-afd5-a009c6356269', '58960016-ff92-4de7-9585-9ac27f961247', '', '20a9ede2-7ebd-4a02-b359-55cb1c8490c8', '4384cfef-689a-480e-95e8-0ce981d3e96f', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_an_score`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_score`;
CREATE TABLE `t_an_score` (
  `id` varchar(55) NOT NULL,
  `answser_score` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_an_yesno`
-- ----------------------------
DROP TABLE IF EXISTS `t_an_yesno`;
CREATE TABLE `t_an_yesno` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `yesno_answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_import_error`
-- ----------------------------
DROP TABLE IF EXISTS `t_import_error`;
CREATE TABLE `t_import_error` (
  `id` varchar(55) NOT NULL,
  `cell1value` varchar(255) DEFAULT NULL,
  `cell2value` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `db_id` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `row_index` int(11) DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `cell1_value` varchar(255) DEFAULT NULL,
  `cell2_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_mail_invite_inbox`
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_invite_inbox`;
CREATE TABLE `t_mail_invite_inbox` (
  `id` varchar(55) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sendcloud_id` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `survey_mail_invite_id` varchar(255) DEFAULT NULL,
  `us_contacts_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_qu_checkbox`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_checkbox`;
CREATE TABLE `t_qu_checkbox` (
  `id` varchar(55) NOT NULL,
  `check_type` int(11) DEFAULT NULL,
  `is_note` int(11) DEFAULT NULL,
  `is_required_fill` int(11) DEFAULT NULL,
  `option_name` text,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_qu_checkbox`
-- ----------------------------
BEGIN;
INSERT INTO `t_qu_checkbox` VALUES ('1c3cc2ad-0ebf-48d6-aa1f-0c26cf030fb4', '0', '0', '0', '选项1', null, '0', '69fe0041-757b-4a91-91e3-f990e1abd38d', '1'), ('60515dd8-bb01-414d-adb2-2e0aec0031e4', '0', '0', '0', '选项2', null, '1', '69fe0041-757b-4a91-91e3-f990e1abd38d', '1'), ('60fd78c7-964f-4318-822d-b9179d1ae3c7', '0', '0', '0', '选项3', null, '2', '69fe0041-757b-4a91-91e3-f990e1abd38d', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_qu_chen_column`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_chen_column`;
CREATE TABLE `t_qu_chen_column` (
  `id` varchar(55) NOT NULL,
  `option_name` text,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_qu_chen_option`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_chen_option`;
CREATE TABLE `t_qu_chen_option` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_qu_chen_row`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_chen_row`;
CREATE TABLE `t_qu_chen_row` (
  `id` varchar(55) NOT NULL,
  `option_name` text,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_qu_multi_fillblank`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_multi_fillblank`;
CREATE TABLE `t_qu_multi_fillblank` (
  `id` varchar(55) NOT NULL,
  `check_type` int(11) DEFAULT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_qu_orderby`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_orderby`;
CREATE TABLE `t_qu_orderby` (
  `id` varchar(55) NOT NULL,
  `option_name` text,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_qu_radio`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_radio`;
CREATE TABLE `t_qu_radio` (
  `id` varchar(55) NOT NULL,
  `check_type` int(11) DEFAULT NULL,
  `is_note` int(11) DEFAULT NULL,
  `is_required_fill` int(11) DEFAULT NULL,
  `option_name` text,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_qu_radio`
-- ----------------------------
BEGIN;
INSERT INTO `t_qu_radio` VALUES ('4384cfef-689a-480e-95e8-0ce981d3e96f', '0', '0', '0', '选项1', null, '0', '20a9ede2-7ebd-4a02-b359-55cb1c8490c8', '1'), ('e6946cfc-567f-4fcc-8673-193601a2b349', '0', '0', '0', '选项2', null, '1', '20a9ede2-7ebd-4a02-b359-55cb1c8490c8', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_qu_score`
-- ----------------------------
DROP TABLE IF EXISTS `t_qu_score`;
CREATE TABLE `t_qu_score` (
  `id` varchar(55) NOT NULL,
  `option_name` text,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_question`
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `id` varchar(55) NOT NULL,
  `answer_input_row` int(11) DEFAULT NULL,
  `answer_input_width` int(11) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `cell_count` int(11) DEFAULT NULL,
  `check_type` int(11) DEFAULT NULL,
  `contacts_attr` int(11) DEFAULT NULL,
  `contacts_field` varchar(255) DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `hv` int(11) DEFAULT NULL,
  `is_required` int(11) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `param_int01` int(11) DEFAULT NULL,
  `param_int02` int(11) DEFAULT NULL,
  `parent_qu_id` varchar(255) DEFAULT NULL,
  `qu_name` varchar(255) DEFAULT NULL,
  `qu_note` text,
  `qu_tag` int(11) DEFAULT NULL,
  `qu_title` text,
  `qu_type` int(11) DEFAULT NULL,
  `rand_order` int(11) DEFAULT NULL,
  `tag` int(11) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `yesno_option` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_question`
-- ----------------------------
BEGIN;
INSERT INTO `t_question` VALUES ('20a9ede2-7ebd-4a02-b359-55cb1c8490c8', null, null, '58960016-ff92-4de7-9585-9ac27f961247', '0', null, '0', '0', null, '2021-12-31 09:47:03', '2', '1', null, '1', '3', '10', null, null, null, '1', '题标题？', '1', '0', '2', '1', null), ('69fe0041-757b-4a91-91e3-f990e1abd38d', null, null, '58960016-ff92-4de7-9585-9ac27f961247', '0', null, '0', '0', null, '2021-12-31 09:47:03', '2', '1', null, '2', '3', '10', null, null, null, '1', '题标题？', '2', '0', '2', '1', null);
COMMIT;

-- ----------------------------
--  Table structure for `t_question_bank`
-- ----------------------------
DROP TABLE IF EXISTS `t_question_bank`;
CREATE TABLE `t_question_bank` (
  `id` varchar(55) NOT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `bank_note` varchar(255) DEFAULT NULL,
  `bank_state` int(11) DEFAULT NULL,
  `bank_tag` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `dir_type` int(11) DEFAULT NULL,
  `excerpt_num` int(11) DEFAULT NULL,
  `group_id1` varchar(255) DEFAULT NULL,
  `group_id2` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `qu_num` int(11) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_question_logic`
-- ----------------------------
DROP TABLE IF EXISTS `t_question_logic`;
CREATE TABLE `t_question_logic` (
  `id` varchar(55) NOT NULL,
  `cg_qu_item_id` varchar(255) DEFAULT NULL,
  `ck_qu_id` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `ge_le` varchar(255) DEFAULT NULL,
  `logic_type` varchar(255) DEFAULT NULL,
  `score_num` int(11) DEFAULT NULL,
  `sk_qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `sk_qu_id_end` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_survey_answer`
-- ----------------------------
DROP TABLE IF EXISTS `t_survey_answer`;
CREATE TABLE `t_survey_answer` (
  `id` varchar(55) NOT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `bg_an_date` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `complete_item_num` int(11) DEFAULT NULL,
  `complete_num` int(11) DEFAULT NULL,
  `data_source` int(11) DEFAULT NULL,
  `end_an_date` datetime DEFAULT NULL,
  `handle_state` int(11) DEFAULT NULL,
  `ip_addr` varchar(255) DEFAULT NULL,
  `is_complete` int(11) DEFAULT NULL,
  `is_effective` int(11) DEFAULT NULL,
  `pc_mac` varchar(255) DEFAULT NULL,
  `qu_num` int(11) DEFAULT NULL,
  `survey_id` varchar(255) DEFAULT NULL,
  `total_time` float DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`),
  KEY `survey_id_index` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_survey_answer`
-- ----------------------------
BEGIN;
INSERT INTO `t_survey_answer` VALUES ('2a793ef9-28d9-4e99-afd5-a009c6356269', null, null, null, '2', null, '0', '2021-12-31 09:47:16', '0', '192.168.3.2', '1', '1', null, null, '58960016-ff92-4de7-9585-9ac27f961247', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_survey_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_survey_detail`;
CREATE TABLE `t_survey_detail` (
  `id` varchar(55) NOT NULL,
  `an_item_least_num` int(11) DEFAULT NULL,
  `an_item_most_num` int(11) DEFAULT NULL,
  `dir_id` varchar(255) DEFAULT NULL,
  `effective` int(11) DEFAULT NULL,
  `effective_ip` int(11) DEFAULT NULL,
  `effective_time` int(11) DEFAULT NULL,
  `end_num` int(11) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `end_type` int(11) DEFAULT NULL,
  `mail_only` int(11) DEFAULT NULL,
  `refresh` int(11) DEFAULT NULL,
  `refresh_num` int(11) DEFAULT NULL,
  `rule` int(11) DEFAULT NULL,
  `rule_code` varchar(255) DEFAULT NULL,
  `show_answer_da` int(11) DEFAULT NULL,
  `show_share_survey` int(11) DEFAULT NULL,
  `survey_note` text,
  `survey_qu_num` int(11) DEFAULT NULL,
  `yn_end_num` int(11) DEFAULT NULL,
  `yn_end_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_survey_detail`
-- ----------------------------
BEGIN;
INSERT INTO `t_survey_detail` VALUES ('d78e1615-7b60-4751-bfc3-659e6dbbb550', '0', '0', '58960016-ff92-4de7-9585-9ac27f961247', '1', '0', '5', '1000', null, '1', '0', '1', '3', '1', '令牌', '0', '1', '非常感谢您的参与！如有涉及个人信息，我们将严格保密。', '0', '0', '0');
COMMIT;

-- ----------------------------
--  Table structure for `t_survey_directory`
-- ----------------------------
DROP TABLE IF EXISTS `t_survey_directory`;
CREATE TABLE `t_survey_directory` (
  `id` varchar(55) NOT NULL,
  `an_item_least_num` int(11) DEFAULT NULL,
  `answer_num` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `dir_type` int(11) DEFAULT NULL,
  `excerpt_num` int(11) DEFAULT NULL,
  `html_path` varchar(255) DEFAULT NULL,
  `is_share` int(11) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `survey_detail_id` varchar(255) DEFAULT NULL,
  `survey_model` int(11) DEFAULT NULL,
  `survey_name` varchar(255) DEFAULT NULL,
  `survey_qu_num` int(11) DEFAULT NULL,
  `survey_state` int(11) DEFAULT NULL,
  `survey_tag` int(11) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `view_answer` int(11) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `survey_type` int(11) DEFAULT NULL,
  `orderby_num` int(11) DEFAULT NULL,
  `json_path` varchar(255) DEFAULT NULL,
  `survey_name_text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `t_survey_directory`
-- ----------------------------
BEGIN;
INSERT INTO `t_survey_directory` VALUES ('58960016-ff92-4de7-9585-9ac27f961247', '0', '1', '2021-12-31 09:46:57', '2', '0', null, '1', '', '4jfgcquk8', null, '1', '第一份测试问卷', '2', '1', '1', '1', '0', '1', null, null, '/file/survey/4jfgcquk8/4jfgcquk8.json', '第一份测试问卷');
COMMIT;

-- ----------------------------
--  Table structure for `t_survey_mail_invite`
-- ----------------------------
DROP TABLE IF EXISTS `t_survey_mail_invite`;
CREATE TABLE `t_survey_mail_invite` (
  `id` varchar(55) NOT NULL,
  `audit` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `dw_send_user_mail` varchar(255) DEFAULT NULL,
  `dw_send_user_name` varchar(255) DEFAULT NULL,
  `dw_survey_link` varchar(255) DEFAULT NULL,
  `dw_survey_name` varchar(255) DEFAULT NULL,
  `error_msg` varchar(255) DEFAULT NULL,
  `fail_num` int(11) DEFAULT NULL,
  `inbox_num` int(11) DEFAULT NULL,
  `send_num` int(11) DEFAULT NULL,
  `sendcloud_msg_id` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `success_num` int(11) DEFAULT NULL,
  `survey_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_survey_req_url`
-- ----------------------------
DROP TABLE IF EXISTS `t_survey_req_url`;
CREATE TABLE `t_survey_req_url` (
  `id` varchar(55) NOT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `survey_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_survey_stats`
-- ----------------------------
DROP TABLE IF EXISTS `t_survey_stats`;
CREATE TABLE `t_survey_stats` (
  `id` varchar(55) NOT NULL,
  `an_avg_time` int(11) DEFAULT NULL,
  `an_min_time` int(11) DEFAULT NULL,
  `answer_num` int(11) DEFAULT NULL,
  `complete_num` int(11) DEFAULT NULL,
  `effective_num` int(11) DEFAULT NULL,
  `first_answer` datetime DEFAULT NULL,
  `handle_pass_num` int(11) DEFAULT NULL,
  `handle_un_pass_num` int(11) DEFAULT NULL,
  `import_num` int(11) DEFAULT NULL,
  `input_num` int(11) DEFAULT NULL,
  `is_new_data` int(11) DEFAULT NULL,
  `last_answer` datetime DEFAULT NULL,
  `mobile_num` int(11) DEFAULT NULL,
  `online_num` int(11) DEFAULT NULL,
  `survey_id` varchar(255) DEFAULT NULL,
  `un_complete_num` int(11) DEFAULT NULL,
  `un_effective_num` int(11) DEFAULT NULL,
  `un_handle_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_survey_style`
-- ----------------------------
DROP TABLE IF EXISTS `t_survey_style`;
CREATE TABLE `t_survey_style` (
  `id` varchar(55) NOT NULL,
  `body_bg_color` varchar(255) DEFAULT NULL,
  `body_bg_image` varchar(255) DEFAULT NULL,
  `show_survey_haed` int(11) DEFAULT NULL,
  `survey_bg_color` varchar(255) DEFAULT NULL,
  `survey_bg_image` varchar(255) DEFAULT NULL,
  `survey_content_bg_colo_topr` varchar(255) DEFAULT NULL,
  `survey_content_bg_color_bottom` varchar(255) DEFAULT NULL,
  `survey_content_bg_color_middle` varchar(255) DEFAULT NULL,
  `survey_content_bg_image_bottom` varchar(255) DEFAULT NULL,
  `survey_content_bg_image_middle` varchar(255) DEFAULT NULL,
  `survey_content_bg_image_top` varchar(255) DEFAULT NULL,
  `survey_content_padding_bottom` int(11) DEFAULT NULL,
  `survey_content_padding_left` int(11) DEFAULT NULL,
  `survey_content_padding_right` int(11) DEFAULT NULL,
  `survey_content_padding_top` int(11) DEFAULT NULL,
  `survey_content_width` int(11) DEFAULT NULL,
  `survey_head_bg_color` varchar(255) DEFAULT NULL,
  `survey_head_bg_image` varchar(255) DEFAULT NULL,
  `survey_head_height` int(11) DEFAULT NULL,
  `survey_head_padding_bottom` int(11) DEFAULT NULL,
  `survey_head_padding_left` int(11) DEFAULT NULL,
  `survey_head_padding_right` int(11) DEFAULT NULL,
  `survey_head_padding_top` int(11) DEFAULT NULL,
  `survey_head_width` int(11) DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `survey_padding_bottom` varchar(255) DEFAULT NULL,
  `survey_padding_left` varchar(255) DEFAULT NULL,
  `survey_padding_right` varchar(255) DEFAULT NULL,
  `survey_padding_top` varchar(255) DEFAULT NULL,
  `survey_style_type` int(11) DEFAULT NULL,
  `survey_width` int(11) DEFAULT NULL,
  `show_body_bi` int(11) DEFAULT NULL,
  `show_survey_bi` int(11) DEFAULT NULL,
  `show_survey_cbim` int(11) DEFAULT NULL,
  `show_survey_hbgi` int(11) DEFAULT NULL,
  `survey_content_bg_color_top` varchar(255) DEFAULT NULL,
  `show_survey_logo` int(11) DEFAULT NULL,
  `survey_logo_image` varchar(255) DEFAULT NULL,
  `question_option_text_color` varchar(255) DEFAULT NULL,
  `question_title_text_color` varchar(255) DEFAULT NULL,
  `survey_note_text_color` varchar(255) DEFAULT NULL,
  `survey_title_text_color` varchar(255) DEFAULT NULL,
  `survey_btn_bg_color` varchar(255) DEFAULT NULL,
  `show_sur_note` int(11) DEFAULT NULL,
  `show_sur_title` int(11) DEFAULT NULL,
  `show_progressbar` int(11) DEFAULT NULL,
  `show_ti_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_sys_db_backup`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_db_backup`;
CREATE TABLE `t_sys_db_backup` (
  `id` varchar(55) NOT NULL,
  `backup_name` varchar(255) DEFAULT NULL,
  `backup_path` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_sys_email`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_email`;
CREATE TABLE `t_sys_email` (
  `id` varchar(55) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `is_check` int(11) DEFAULT NULL,
  `post` varchar(255) DEFAULT NULL,
  `send_email` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `stmp_pwd` varchar(255) DEFAULT NULL,
  `stmp_server` varchar(255) DEFAULT NULL,
  `stmp_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(55) NOT NULL,
  `activation_code` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `cellphone` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `edu_quali` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `find_pwd_code` varchar(255) DEFAULT NULL,
  `find_pwd_last_date` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_name` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `sha_password` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `sha_password_temp` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `wwwooo` varchar(255) DEFAULT NULL,
  `wx_open_id` varchar(255) DEFAULT NULL,
  `casdoor_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', null, '2013-03-21 21:15:21', null, null, '2013-03-21 21:15:21', '1', 'service@diaowen.net', null, null, '2021-12-31 09:46:37', 'dwsurvey', '柯远', null, '1', '7c4a8d09ca3762af61e59520943dc26494f8941b', '2', '1', null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `tracker`
-- ----------------------------
DROP TABLE IF EXISTS `tracker`;
CREATE TABLE `tracker` (
  `id` varchar(55) NOT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `optime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
