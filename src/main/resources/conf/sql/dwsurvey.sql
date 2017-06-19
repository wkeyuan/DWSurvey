CREATE DATABASE  IF NOT EXISTS `dwsurvey` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `dwsurvey`;
-- MySQL dump 10.13  Distrib 5.6.22, for osx10.8 (x86_64)
--
-- Host: localhost    Database: dwsurvey
-- ------------------------------------------------------
-- Server version	5.1.63

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_an_answer`
--

DROP TABLE IF EXISTS `t_an_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_answer` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_answer`
--

LOCK TABLES `t_an_answer` WRITE;
/*!40000 ALTER TABLE `t_an_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_checkbox`
--

DROP TABLE IF EXISTS `t_an_checkbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_checkbox` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `other_text` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_item_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_checkbox`
--

LOCK TABLES `t_an_checkbox` WRITE;
/*!40000 ALTER TABLE `t_an_checkbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_checkbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_chen_checkbox`
--

DROP TABLE IF EXISTS `t_an_chen_checkbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_chen_checkbox` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_chen_checkbox`
--

LOCK TABLES `t_an_chen_checkbox` WRITE;
/*!40000 ALTER TABLE `t_an_chen_checkbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_chen_checkbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_chen_fbk`
--

DROP TABLE IF EXISTS `t_an_chen_fbk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_chen_fbk` (
  `id` varchar(55) NOT NULL,
  `answer_value` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_chen_fbk`
--

LOCK TABLES `t_an_chen_fbk` WRITE;
/*!40000 ALTER TABLE `t_an_chen_fbk` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_chen_fbk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_chen_radio`
--

DROP TABLE IF EXISTS `t_an_chen_radio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_chen_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_chen_radio`
--

LOCK TABLES `t_an_chen_radio` WRITE;
/*!40000 ALTER TABLE `t_an_chen_radio` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_chen_radio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_chen_score`
--

DROP TABLE IF EXISTS `t_an_chen_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_chen_score` (
  `id` varchar(55) NOT NULL,
  `answser_score` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_chen_score`
--

LOCK TABLES `t_an_chen_score` WRITE;
/*!40000 ALTER TABLE `t_an_chen_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_chen_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_comp_chen_radio`
--

DROP TABLE IF EXISTS `t_an_comp_chen_radio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_comp_chen_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_option_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_comp_chen_radio`
--

LOCK TABLES `t_an_comp_chen_radio` WRITE;
/*!40000 ALTER TABLE `t_an_comp_chen_radio` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_comp_chen_radio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_dfillblank`
--

DROP TABLE IF EXISTS `t_an_dfillblank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_dfillblank` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_item_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_dfillblank`
--

LOCK TABLES `t_an_dfillblank` WRITE;
/*!40000 ALTER TABLE `t_an_dfillblank` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_dfillblank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_enumqu`
--

DROP TABLE IF EXISTS `t_an_enumqu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_enumqu` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `enum_item` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_enumqu`
--

LOCK TABLES `t_an_enumqu` WRITE;
/*!40000 ALTER TABLE `t_an_enumqu` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_enumqu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_fillblank`
--

DROP TABLE IF EXISTS `t_an_fillblank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_fillblank` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_fillblank`
--

LOCK TABLES `t_an_fillblank` WRITE;
/*!40000 ALTER TABLE `t_an_fillblank` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_fillblank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_order`
--

DROP TABLE IF EXISTS `t_an_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_order` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `ordery_num` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_order`
--

LOCK TABLES `t_an_order` WRITE;
/*!40000 ALTER TABLE `t_an_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_radio`
--

DROP TABLE IF EXISTS `t_an_radio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `other_text` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_item_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_radio`
--

LOCK TABLES `t_an_radio` WRITE;
/*!40000 ALTER TABLE `t_an_radio` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_radio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_score`
--

DROP TABLE IF EXISTS `t_an_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_score` (
  `id` varchar(55) NOT NULL,
  `answser_score` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_score`
--

LOCK TABLES `t_an_score` WRITE;
/*!40000 ALTER TABLE `t_an_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_yesno`
--

DROP TABLE IF EXISTS `t_an_yesno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_an_yesno` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(255) DEFAULT NULL,
  `belong_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `yesno_answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_yesno`
--

LOCK TABLES `t_an_yesno` WRITE;
/*!40000 ALTER TABLE `t_an_yesno` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_yesno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_import_error`
--

DROP TABLE IF EXISTS `t_import_error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_import_error`
--

LOCK TABLES `t_import_error` WRITE;
/*!40000 ALTER TABLE `t_import_error` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_import_error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_mail_invite_inbox`
--

DROP TABLE IF EXISTS `t_mail_invite_inbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_mail_invite_inbox`
--

LOCK TABLES `t_mail_invite_inbox` WRITE;
/*!40000 ALTER TABLE `t_mail_invite_inbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_mail_invite_inbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_checkbox`
--

DROP TABLE IF EXISTS `t_qu_checkbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_checkbox` (
  `id` varchar(55) NOT NULL,
  `check_type` int(11) DEFAULT NULL,
  `is_note` int(11) DEFAULT NULL,
  `is_required_fill` int(11) DEFAULT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_checkbox`
--

LOCK TABLES `t_qu_checkbox` WRITE;
/*!40000 ALTER TABLE `t_qu_checkbox` DISABLE KEYS */;
INSERT INTO `t_qu_checkbox` VALUES ('402880e55cb9c629015cb9e366880009',0,0,0,'选项1',NULL,0,'402880e55cb9c629015cb9e366880008',1),('402880e55cb9c629015cb9e36688000a',0,0,0,'选项2',NULL,1,'402880e55cb9c629015cb9e366880008',1),('402880e55cb9c629015cb9e36688000b',0,0,0,'选项3',NULL,2,'402880e55cb9c629015cb9e366880008',1);
/*!40000 ALTER TABLE `t_qu_checkbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_chen_column`
--

DROP TABLE IF EXISTS `t_qu_chen_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_chen_column` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_chen_column`
--

LOCK TABLES `t_qu_chen_column` WRITE;
/*!40000 ALTER TABLE `t_qu_chen_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_chen_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_chen_option`
--

DROP TABLE IF EXISTS `t_qu_chen_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_chen_option` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_chen_option`
--

LOCK TABLES `t_qu_chen_option` WRITE;
/*!40000 ALTER TABLE `t_qu_chen_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_chen_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_chen_row`
--

DROP TABLE IF EXISTS `t_qu_chen_row`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_chen_row` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_chen_row`
--

LOCK TABLES `t_qu_chen_row` WRITE;
/*!40000 ALTER TABLE `t_qu_chen_row` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_chen_row` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_multi_fillblank`
--

DROP TABLE IF EXISTS `t_qu_multi_fillblank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_multi_fillblank` (
  `id` varchar(55) NOT NULL,
  `check_type` int(11) DEFAULT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_multi_fillblank`
--

LOCK TABLES `t_qu_multi_fillblank` WRITE;
/*!40000 ALTER TABLE `t_qu_multi_fillblank` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_multi_fillblank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_orderby`
--

DROP TABLE IF EXISTS `t_qu_orderby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_orderby` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_orderby`
--

LOCK TABLES `t_qu_orderby` WRITE;
/*!40000 ALTER TABLE `t_qu_orderby` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_orderby` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_radio`
--

DROP TABLE IF EXISTS `t_qu_radio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_radio` (
  `id` varchar(55) NOT NULL,
  `check_type` int(11) DEFAULT NULL,
  `is_note` int(11) DEFAULT NULL,
  `is_required_fill` int(11) DEFAULT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_radio`
--

LOCK TABLES `t_qu_radio` WRITE;
/*!40000 ALTER TABLE `t_qu_radio` DISABLE KEYS */;
INSERT INTO `t_qu_radio` VALUES ('402880e55cb9c629015cb9dbd75d0003',0,0,0,'选项1',NULL,0,'402880e55cb9c629015cb9dbd75d0002',1),('402880e55cb9c629015cb9dbd75d0004',0,0,0,'选项2',NULL,1,'402880e55cb9c629015cb9dbd75d0002',1),('402880e55cb9c629015cb9dbd75e0005',0,0,0,'332423',NULL,2,'402880e55cb9c629015cb9dbd75d0002',1);
/*!40000 ALTER TABLE `t_qu_radio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_score`
--

DROP TABLE IF EXISTS `t_qu_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_qu_score` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int(11) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_score`
--

LOCK TABLES `t_qu_score` WRITE;
/*!40000 ALTER TABLE `t_qu_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_question`
--

DROP TABLE IF EXISTS `t_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `qu_note` varchar(255) DEFAULT NULL,
  `qu_tag` int(11) DEFAULT NULL,
  `qu_title` varchar(255) DEFAULT NULL,
  `qu_type` int(11) DEFAULT NULL,
  `rand_order` int(11) DEFAULT NULL,
  `tag` int(11) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `yesno_option` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_question`
--

LOCK TABLES `t_question` WRITE;
/*!40000 ALTER TABLE `t_question` DISABLE KEYS */;
INSERT INTO `t_question` VALUES ('402880e55cb9c629015cb9dbd75d0002',NULL,NULL,'402880e55cb9c629015cb9d31eff0000',0,NULL,0,'0',NULL,'2017-06-18 14:20:11',2,1,NULL,1,3,10,NULL,NULL,NULL,1,'题标题？222',1,0,2,1,NULL),('402880e55cb9c629015cb9e366880008',NULL,NULL,'402880e55cb9c629015cb9d31eff0000',0,NULL,0,'0',NULL,'2017-06-18 14:28:26',2,1,NULL,2,3,10,NULL,NULL,NULL,1,'题标题？',2,0,2,1,NULL);
/*!40000 ALTER TABLE `t_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_question_bank`
--

DROP TABLE IF EXISTS `t_question_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_question_bank`
--

LOCK TABLES `t_question_bank` WRITE;
/*!40000 ALTER TABLE `t_question_bank` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_question_bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_question_logic`
--

DROP TABLE IF EXISTS `t_question_logic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_question_logic`
--

LOCK TABLES `t_question_logic` WRITE;
/*!40000 ALTER TABLE `t_question_logic` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_question_logic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer`
--

DROP TABLE IF EXISTS `t_survey_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer`
--

LOCK TABLES `t_survey_answer` WRITE;
/*!40000 ALTER TABLE `t_survey_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_detail`
--

DROP TABLE IF EXISTS `t_survey_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `survey_note` varchar(255) DEFAULT NULL,
  `survey_qu_num` int(11) DEFAULT NULL,
  `yn_end_num` int(11) DEFAULT NULL,
  `yn_end_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_detail`
--

LOCK TABLES `t_survey_detail` WRITE;
/*!40000 ALTER TABLE `t_survey_detail` DISABLE KEYS */;
INSERT INTO `t_survey_detail` VALUES ('402880e55cb9c629015cb9d31f160001',0,0,'402880e55cb9c629015cb9d31eff0000',0,0,5,1000,'2017-06-18 14:28:34',1,0,1,3,0,'令牌',0,1,'非常感谢您的参与！如有涉及个人信息，我们将严格保密。',0,0,0);
/*!40000 ALTER TABLE `t_survey_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_directory`
--

DROP TABLE IF EXISTS `t_survey_directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_directory`
--

LOCK TABLES `t_survey_directory` WRITE;
/*!40000 ALTER TABLE `t_survey_directory` DISABLE KEYS */;
INSERT INTO `t_survey_directory` VALUES ('402880e55cb9c629015cb9d31eff0000',0,NULL,'2017-06-18 14:10:39',2,0,'WEB-INF/wjHtml/2017/06/18/402880e55cb9c629015cb9d31eff0000.html',1,'','2p4tsrc5cvz',NULL,1,'第一份测试问卷',2,1,1,'1',0,1);
/*!40000 ALTER TABLE `t_survey_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_mail_invite`
--

DROP TABLE IF EXISTS `t_survey_mail_invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_mail_invite`
--

LOCK TABLES `t_survey_mail_invite` WRITE;
/*!40000 ALTER TABLE `t_survey_mail_invite` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_mail_invite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_req_url`
--

DROP TABLE IF EXISTS `t_survey_req_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_survey_req_url` (
  `id` varchar(55) NOT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `survey_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_req_url`
--

LOCK TABLES `t_survey_req_url` WRITE;
/*!40000 ALTER TABLE `t_survey_req_url` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_req_url` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_stats`
--

DROP TABLE IF EXISTS `t_survey_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_stats`
--

LOCK TABLES `t_survey_stats` WRITE;
/*!40000 ALTER TABLE `t_survey_stats` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_style`
--

DROP TABLE IF EXISTS `t_survey_style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_style`
--

LOCK TABLES `t_survey_style` WRITE;
/*!40000 ALTER TABLE `t_survey_style` DISABLE KEYS */;
INSERT INTO `t_survey_style` VALUES ('402880e54794e4a4014794e522060000','#ffffff','/file/images/rydbnwol4n62lw6.jpg',NULL,NULL,NULL,NULL,NULL,'#ffffff',NULL,'/file/images/2gi029de7g1521x.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#ffffff','/file/images/01w071czz9yl5za.jpg',NULL,0,NULL,NULL,0,NULL,'402880ea4675ac62014675ac7b3a0000',NULL,NULL,NULL,NULL,0,900,1,0,0,1,NULL,0,'/images/1279780388A5rVCG.jpg','#2f363b','#18191a','#0b0c0d','#1a1b1c',NULL,NULL,NULL,NULL,NULL),('402880e55c733da2015c7340eda10005','#E8E9EB','/file/images/r4vs89f647uvnyb.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#b5ba1a','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402881e858e6a24f0158e74968c30006',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/logo/108-108.jpg','#000000','#000000','#d5dfe6','#13171a','#12b0ee',1,1,1,1),('402880e55ca74afc015ca768373e0000','#E8E9EB','/file/images/fm6s7rin7g9zryy.png',NULL,NULL,NULL,NULL,NULL,'#f0f3f5',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#063f5a','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402881e858e6a24f0158e6a3811d0000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#17191a','#0e0f0f','#f2f4f5','#f0f3f5','#7EA800',0,0,0,0),('402880e55cb9c629015cb9e0fa5b0007','#E8E9EB','/file/images/fm6s7rin7g9zryy.png',NULL,NULL,NULL,NULL,NULL,'#f0f3f5',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#063f5a','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402880e55cb9c629015cb9d31eff0000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/logo/108-108.jpg','#17191a','#0e0f0f','#f2f4f5','#f0f3f5','#7EA800',1,1,1,1),('402880e654c939790154c93a76fa0000','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402880e654ae3ec40154ae54c9f70000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402880e74df7133e014df733e1d20000','#E8E9EB','/file/images/3pv3u4ns5by2uhd.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#b6ccdb','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402880e54da29a5b014da2b6481c0011',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/logo/108-108.jpg','#1c1f21','#121314','#101214','#101314','#12b0ee',1,1,NULL,NULL),('402880e74df770a9014df7731e950001','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402880e54d38852e014d38863f010000',NULL,NULL,NULL,NULL,0,900,0,0,1,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,0,0),('402880e849f0f5e70149f104e47d0000','#E8E9EB','/file/images/9fb98sbm9147pm8.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#159e3e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#0d1012','#070b0d','#0b1114','#0a0d0f','#12b0ee',NULL,NULL,NULL,NULL),('402880e849f0f5e70149f1090d600001','#E8E9EB','/file/images/twc94he4j7m0gj5.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#dfe4e8','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#0c0f12','#040608','#090b0d','#0b0e0f',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f10c0c7e0002','#E8E9EB','/file/images/t7c69ulxn9zi830.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#202426','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#0a0c0d','#06090a','#a2b0ba','#cbd2d6',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f10edd0c0003','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f1121f320004','#E8E9EB','/file/images/qfsv0ui7nyxmtmu.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#111c36','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#16181a','#131517','#dadfe3','#dde5eb',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f1156b3d0005','#191e21','/file/images/zop3vsgn0gjny9r.jpg',NULL,NULL,NULL,NULL,NULL,'#ffffff',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#191c1f','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#FFFFFF','#E8E9EB','#e6edf2','#d5dfe6',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f117022d0006','#191e21','/file/images/zop3vsgn0gjny9r.jpg',NULL,NULL,NULL,NULL,NULL,'#ffffff',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#191c1f','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#131414','#1b1d1f','#e6edf2','#d5dfe6',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f13f18ba0007','#E8E9EB','/file/images/r4vs89f647uvnyb.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#b5ba1a','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#000000','#000000','#d5dfe6','#13171a',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f141f3700008','#E8E9EB','/file/images/uj0dawpti7yd6nt.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#bac746','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#0b0c0d','#0e1012','#0d0e0f','#111314',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f1441b000009','#E8E9EB','/file/images/j29vaz5uyjmymwa.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#08090a','#020203','#7EA800','#0c0e0f',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f1461613000a','#E8E9EB','/file/images/3pv3u4ns5by2uhd.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#b6ccdb','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#1c1f21','#121314','#101214','#101314',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f147530d000b','#E8E9EB','/file/images/qfm55md6vp3jdoz.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'','',NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#0b0c0d','#131414','#0b0c0d','#101112',NULL,NULL,NULL,NULL,NULL),('402880e849f0f5e70149f1879a9f0029','#191e21','/file/images/iayamhh3qpnybc1.jpg',NULL,NULL,NULL,NULL,NULL,'#ffffff',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#191c1f','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#131414','#1b1d1f','#e6edf2','#d5dfe6',NULL,NULL,NULL,NULL,NULL),('402880e849fa446b0149fb1ef12d0001','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,0,1000,0,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#333333','#333333','#333333','#222222',NULL,NULL,NULL,NULL,NULL),('402880e84a0622a7014a062613280000','#E8E9EB','/file/images/qfsv0ui7nyxmtmu.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#111c36','/file/images/ld6ut7c39l060yl.jpg',NULL,0,NULL,NULL,0,NULL,'402880eb49a36bbe0149a3fb3efb0000',NULL,NULL,NULL,NULL,0,900,1,0,0,1,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#16181a','#131517','#dadfe3','#dde5eb','#2b70a1',1,1,1,1),('402880e94cacc77b014cacdf77730056','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402880e94cacc77b014cacd11afa0041',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#12b0ee',1,1,NULL,NULL),('402880e94e0e8e67014e0eaf4f08000a','#E8E9EB','/file/images/8g7ofg67wqpt88o.jpg',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/file/images/ap4z1952ch4x73a.gif',NULL,150,NULL,NULL,0,NULL,'402880e74df792d8014df79d0ae40002',NULL,NULL,NULL,NULL,0,950,1,0,0,1,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',0,0,1,0),('402880e94e2b1769014e2b63a44f0001','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f40f2727e0140f29bd1a50055',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402880e94e5443fa014e5444d5ff0000','','',NULL,NULL,NULL,NULL,NULL,'',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,NULL,'402880e94e54296f014e5429fdbb0000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','rgb(99, 101, 102)','color: rgb(85, 87, 89)','rgb(112, 114, 115)','rgb(34, 34, 34)','#7EA800',1,1,1,1),('402880e94e582ea9014e582f31710005','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402880e94e582ea9014e582eee740000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402880e94e640383014e640849de0000','#E8E9EB','/file/images/t7c69ulxn9zi830.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#202426','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402880e94e5e90b1014e5e918cf60000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/logo/108-108.jpg','#0a0c0d','#06090a','#a2b0ba','#cbd2d6','#7EA800',1,1,1,1),('402880ea484021d10148402e42070000','#E8E9EB','/file/images/fm6s7rin7g9zryy.png',NULL,NULL,NULL,NULL,NULL,'#f0f3f5',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#063f5a','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#17191a','#0e0f0f','#f2f4f5','#f0f3f5',NULL,NULL,NULL,NULL,NULL),('402880ea484d83a801484e5a30bf0000','#E8E9EB','/file/images/grebumvosj3424f.jpg',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8ab20e8f412e65b601412e719c8c001c',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#16181a','#0d0e0f','#7EA800','#1f2224',NULL,NULL,NULL,NULL,NULL),('402880eb49a435870149a45b3d19002e','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402880eb49a3598d0149a3673b2d0018',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,0,1),('402880eb49dcabfd0149dcb359370000','#E8E9EB','/file/images/6x4otsq9t2uo7yp.png',NULL,NULL,NULL,NULL,NULL,'#f0f3f5',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#13396b','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#17191a','#0e0f0f','#f2f4f5','#f0f3f5',NULL,NULL,NULL,NULL,NULL),('402880eb49dcabfd0149dcbe2db10002','#E8E9EB','/file/images/dvnnttlj4g2vpon.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#e8eaeb','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'',NULL,NULL,NULL,NULL,1,900,1,0,0,0,NULL,0,'/images/1279780388A5rVCG.jpg','#12191f','#020405','#182229','#0f151a',NULL,NULL,NULL,NULL,NULL),('402880f34abaedac014abaf0e2610005','#E8E9EB','/file/images/ci3jgo1tyz76t0o.jpg',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402880f34abaedac014abaee7e6a0000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',NULL,NULL,NULL,NULL),('402881e45605583a0156082c41060009','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402880e6548acdf001548ace786c0000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402881e655a6f14f0155a6f1e1770000','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402881e655828e930155859dc7460001',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402881e655aba9d80155abaa78510000','#E8E9EB','/file/images/kfe1375aus0sbih.jpg',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402881e94aa9d3e8014aaa00c1740008',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402881e655aba9d80155abac2f2e0002','#E8E9EB','/file/images/9fb98sbm9147pm8.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#159e3e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402880eb49bd9b6a0149bda109eb0000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#0d1012','#070b0d','#0b1114','#0a0d0f','#12b0ee',1,1,1,1),('402881e755fe5e6e0156003b28f60002','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402881e755fe5cbc0155fe5d68940000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402881e858b5c0790158b5cb4b870000','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402881e858b097330158b097cb5b0000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402881e858c4767d0158c477c2a20000','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'402881e858b05b770158b064bd050000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,1,1),('402881e94aa4d823014aa53e5d5f0002','#E8E9EB','/file/images/pj0ryqe045w1i6w.PNG',NULL,NULL,NULL,NULL,NULL,'#f0f3f5',NULL,'/file/images/f3lv18p1q2vq3al.JPG',NULL,NULL,NULL,NULL,NULL,NULL,'#13396b','/file/images/ua46z3eslje245h.jpg',NULL,0,NULL,NULL,20,NULL,'402880eb4a86d272014a86f02bb30009',NULL,NULL,NULL,NULL,0,900,1,0,0,1,NULL,0,'/diaowen3.0/images/logo/108-108.jpg','#17191a','#0e0f0f','#f2f4f5','#f0f3f5','#12b0ee',1,1,0,0),('4028c6864fda26dc014fda2cc82f0008','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'ff8080814fd9ab99014fd9abe2490000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/logo/108-108.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,1,1),('8a290d5e4d1a11a2014d1a13651a0000','#E8E9EB','/file/images/fm6s7rin7g9zryy.png',NULL,NULL,NULL,NULL,NULL,'#f0f3f5',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#063f5a','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8ab20e8f41044872014106068b620000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#17191a','#0e0f0f','#f2f4f5','#f0f3f5','#7EA800',1,1,NULL,NULL),('8a290d5e4d1a9a35014d1cbd780b0000','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f424b7b2801424c24c389004b',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('8a290d5e4d1d7b7c014d1d7ccbc60000','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8ab20e8f424b7b2801424c3414da0075',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a290d5e4d1d8735014d1d88acde0000','#E8E9EB','/file/images/twc94he4j7m0gj5.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#dfe4e8','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8ab20e8f424b7b2801424c1e413e0039',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#0c0f12','#040608','#090b0d','#0b0e0f','#7EA800',1,1,NULL,NULL),('8a290d5e4d1d9cf8014d1d9dd6f90004','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d1a1ce8014d1a436441001c',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('8a290d5e4d1db174014d1e27049e0001','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d1db388014d1e2679670000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a290d5e4d2997f8014d299eeaa30000','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f42081a3c01420914f6a500e7',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('8a290d5e4d5caf0e014d7aea29d40016','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d4d20c3014d507067750006',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('8a290d5e4d95b6c7014d95bb44be000c','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8a290d5e4d95b6c7014d95baa60e0002',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a290d5e4d9af3f3014da94ccafd0015','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#3badcc','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f42404933014240b4c84300de',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#fafbfc','#ffffff','#7EA800',1,1,NULL,NULL),('8a290d5e4d9af3f3014da95061300016','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#f7d383','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f424049330142406672de0000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('8a290d5e4d9af3f3014da95205300017','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#256a9c','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f4226fccc014228287dfc009f',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#000000','#dfe7ed','#cad4db','#7EA800',1,1,NULL,NULL),('8a290d5e4d9af3f3014da95374e90018','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#10b529','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f42224aa601422318a66000dd',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#ffffff','#edf5fa','#7EA800',1,1,NULL,NULL),('8a290d5e4d9af3f3014da954fb1f0019','#07728a','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#1094c4','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f420365ac014204053529009b',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#fafbfc','#f7f9fa','#7EA800',1,1,NULL,NULL),('8a290d5e4db9e5c2014dbedf254e0021','#e3e6e8','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#ffffff',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#1b8dde','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f420365ac014204177db600e3',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#f5fbff','#7EA800',1,1,NULL,NULL),('8a290d5e4db9e5c2014dbee0e72f0022','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#2b9ef0','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f4226fccc014228190db9005e',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#fafafa','#ffffff','#7EA800',1,1,NULL,NULL),('8a290d5e4db9e5c2014dbee26c390023','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#22ba6e','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f42224aa60142230b81d700a9',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#3c4952','#485259','#7EA800',1,1,NULL,NULL),('8a290d5e4db9e5c2014dbee519e10024','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#11cf60','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f42224aa6014222f351bf005d',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#e4e8eb','#d1d8de','#7EA800',1,1,NULL,NULL),('8a29dbf24d14debf014d17e885700038','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d14debf014d1595e6540000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a29dbf24d14debf014d189e09000039','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8ab20e8f4104487201410626f8bf0014',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a29dbf24d1a1ce8014d1a2125b90019','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d1a1ce8014d1a2026f50000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a29dbf24d1da1a8014d1da2ec490000','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#9fcc25','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8ab20e8f42081a3c014208e0a71b008c',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('8a29dbf24d5cb07b014d75c9a66f0069','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d5cb07b014d75c95561003b',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a29dbf24d8bc1d0014d8bc59b890010','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d8bc1d0014d8bc549370000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('8a29dbf24d8bff42014d8e4ab43b0034','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'8a29dbf24d8bff42014d8e4a07280007',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,NULL,NULL),('8a29dbf24d9af187014d9f1c92f9000d','#E8E9EB','/images/style-model/5629512728352684315.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/style-model/1123/29153737.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#FFFFFF','/images/style-model/5629512728352684315.png',NULL,0,NULL,NULL,0,NULL,'8a290d5e4d437bdb014d46bcb5920002',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/style-model/6608914805422388314.jpg','#333333','#333333','#333333','#222222','#7EA800',1,1,NULL,NULL),('ff8080814f598672014f59990c710000','#E8E9EB','/file/images/6x4otsq9t2uo7yp.png',NULL,NULL,NULL,NULL,NULL,'#f0f3f5',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#13396b','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'402880e64f53adb1014f53b003a20000',NULL,NULL,NULL,NULL,0,900,1,0,0,0,NULL,0,'/diaowen_pro/images/logo/108-108.jpg','#17191a','#0e0f0f','#f2f4f5','#f0f3f5','#7EA800',1,1,1,1),('ff8080815a5f028c015a5f490046000b','#E8E9EB','/file/images/6x1ml28olol8bo3.png',NULL,NULL,NULL,NULL,NULL,'#FFFFFF',NULL,'/images/1279780388A5rVCG.jpg',NULL,NULL,NULL,NULL,NULL,NULL,'#262a2e','/images/1279780388A5rVCG.jpg',NULL,0,NULL,NULL,0,NULL,'ff8080815a5f028c015a5f47165f0000',NULL,NULL,NULL,NULL,0,900,0,0,0,0,NULL,0,'/images/logo/108-108.jpg','#111314','#0d0e0f','#c5d5e0','#bac4cc','#7EA800',1,1,1,1);
/*!40000 ALTER TABLE `t_survey_style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_db_backup`
--

DROP TABLE IF EXISTS `t_sys_db_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_db_backup` (
  `id` varchar(55) NOT NULL,
  `backup_name` varchar(255) DEFAULT NULL,
  `backup_path` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_db_backup`
--

LOCK TABLES `t_sys_db_backup` WRITE;
/*!40000 ALTER TABLE `t_sys_db_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_db_backup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_email`
--

DROP TABLE IF EXISTS `t_sys_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_email`
--

LOCK TABLES `t_sys_email` WRITE;
/*!40000 ALTER TABLE `t_sys_email` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES ('1',NULL,'2013-03-21 21:15:21',NULL,NULL,'2013-03-21 21:15:21',1,'service@diaowen.net',NULL,NULL,'2013-03-21 21:15:34','dwsurvey','柯远',NULL,1,'7c4a8d09ca3762af61e59520943dc26494f8941b',1,1);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tracker`
--

DROP TABLE IF EXISTS `tracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tracker` (
  `id` varchar(55) NOT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `optime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tracker`
--

LOCK TABLES `tracker` WRITE;
/*!40000 ALTER TABLE `tracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `tracker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-18 14:30:13
