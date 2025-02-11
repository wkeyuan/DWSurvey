-- MySQL dump 10.13  Distrib 8.0.40, for Linux (aarch64)
--
-- Host: localhost    Database: dwsurvey_oss_db
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_answer` (
  `id` varchar(55) NOT NULL,
  `answer` text,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_answer`
--

LOCK TABLES `t_an_answer` WRITE;
/*!40000 ALTER TABLE `t_an_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_cascade`
--

DROP TABLE IF EXISTS `t_an_cascade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_cascade` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `answer_level` int DEFAULT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_item_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `other_answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_cascade`
--

LOCK TABLES `t_an_cascade` WRITE;
/*!40000 ALTER TABLE `t_an_cascade` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_cascade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_checkbox`
--

DROP TABLE IF EXISTS `t_an_checkbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_checkbox` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `other_text` varchar(255) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_item_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_chen_checkbox` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_col_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_row_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_chen_fbk` (
  `id` varchar(55) NOT NULL,
  `answer_value` varchar(555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_col_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_row_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_chen_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_col_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_row_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_chen_score` (
  `id` varchar(55) NOT NULL,
  `answser_score` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_col_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_row_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_comp_chen_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_col_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_option_id` varchar(55) DEFAULT NULL,
  `qu_row_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_dfillblank` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_item_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_enumqu` (
  `id` varchar(55) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `enum_item` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_fillblank` (
  `id` varchar(55) NOT NULL,
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_order` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `ordery_num` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_row_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_radio` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `other_text` varchar(255) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_item_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_radio`
--

LOCK TABLES `t_an_radio` WRITE;
/*!40000 ALTER TABLE `t_an_radio` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_radio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_scale`
--

DROP TABLE IF EXISTS `t_an_scale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_scale` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_item_id` varchar(55) DEFAULT NULL,
  `scale_value` varchar(255) DEFAULT NULL,
  `scale_value_text` varchar(255) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_scale`
--

LOCK TABLES `t_an_scale` WRITE;
/*!40000 ALTER TABLE `t_an_scale` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_scale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_score`
--

DROP TABLE IF EXISTS `t_an_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_score` (
  `id` varchar(55) NOT NULL,
  `answser_score` varchar(255) DEFAULT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `qu_row_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `answer_score` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exportIndex` (`belong_answer_id`,`qu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_score`
--

LOCK TABLES `t_an_score` WRITE;
/*!40000 ALTER TABLE `t_an_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_uplodfile`
--

DROP TABLE IF EXISTS `t_an_uplodfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_uplodfile` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `random_code` varchar(255) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_uplodfile`
--

LOCK TABLES `t_an_uplodfile` WRITE;
/*!40000 ALTER TABLE `t_an_uplodfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_uplodfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_an_yesno`
--

DROP TABLE IF EXISTS `t_an_yesno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_an_yesno` (
  `id` varchar(55) NOT NULL,
  `belong_answer_id` varchar(55) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `yesno_answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_an_yesno`
--

LOCK TABLES `t_an_yesno` WRITE;
/*!40000 ALTER TABLE `t_an_yesno` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_an_yesno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_answer_survey_ip`
--

DROP TABLE IF EXISTS `t_answer_survey_ip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_answer_survey_ip` (
  `id` varchar(55) NOT NULL,
  `as_ip` varchar(255) DEFAULT NULL,
  `ip_end` bigint DEFAULT NULL,
  `ip_start` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_answer_survey_ip`
--

LOCK TABLES `t_answer_survey_ip` WRITE;
/*!40000 ALTER TABLE `t_answer_survey_ip` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_answer_survey_ip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cascade_data`
--

DROP TABLE IF EXISTS `t_cascade_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cascade_data` (
  `id` varchar(55) NOT NULL,
  `c_value` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `c_level` int DEFAULT NULL,
  `root_id` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `data_state` int DEFAULT NULL,
  `data_type` int DEFAULT NULL,
  `excerpt_num` int DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `up_date` datetime DEFAULT NULL,
  `count_level` int DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cascade_data`
--

LOCK TABLES `t_cascade_data` WRITE;
/*!40000 ALTER TABLE `t_cascade_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_cascade_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cascade_json`
--

DROP TABLE IF EXISTS `t_cascade_json`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cascade_json` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cascade_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `count_level` int DEFAULT NULL,
  `data_type` int DEFAULT NULL,
  `json_data` json DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `data_state` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cascade_json`
--

LOCK TABLES `t_cascade_json` WRITE;
/*!40000 ALTER TABLE `t_cascade_json` DISABLE KEYS */;
INSERT INTO `t_cascade_json` VALUES ('90b6878b-f016-4569-aadc-a1272638c96e','aawww2',4,100,'[{\"label\": \"宝马\", \"level\": 0, \"value\": \"宝马-nxqrhp\", \"children\": [{\"label\": \"X3\", \"level\": 1, \"value\": \"X3-bzktvj\", \"children\": [{\"label\": \"2014款\", \"level\": 2, \"value\": \"2014款-akggsz\", \"children\": [{\"label\": \"xDrive28i\", \"level\": 3, \"value\": \"xDrive28i-aevduh\", \"orderById\": 3}, {\"label\": \"xDrive35i 典雅型\", \"level\": 3, \"value\": \"xDrive35i 典雅型-fzufaj\", \"orderById\": 4}, {\"label\": \"xDrive35i M运动型\", \"level\": 3, \"value\": \"xDrive35i M运动型-odygjn\", \"orderById\": 5}], \"orderById\": 2}, {\"label\": \"2015款\", \"level\": 2, \"value\": \"2015款-esaxif\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-ezscwj\", \"orderById\": 7}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-kumlor\", \"orderById\": 8}], \"orderById\": 6}, {\"label\": \"2016款\", \"level\": 2, \"value\": \"2016款-fwibar\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-owoask\", \"orderById\": 10}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-zjreci\", \"orderById\": 11}], \"orderById\": 9}], \"orderById\": 1}, {\"label\": \"X5\", \"level\": 1, \"value\": \"X5-xrxhjh\", \"children\": [{\"label\": \"2014款\", \"level\": 2, \"value\": \"2014款-ygztms\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-tsfbsx\", \"orderById\": 14}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-owukaa\", \"orderById\": 15}], \"orderById\": 13}, {\"label\": \"2015款\", \"level\": 2, \"value\": \"2015款-ldytjj\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-kiitwv\", \"orderById\": 17}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-nyehvj\", \"orderById\": 18}], \"orderById\": 16}, {\"label\": \"2016款\", \"level\": 2, \"value\": \"2016款-zhmqna\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-wazxde\", \"orderById\": 20}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-wllqnl\", \"orderById\": 21}], \"orderById\": 19}], \"orderById\": 12}], \"orderById\": 0}, {\"label\": \"奔驰\", \"level\": 0, \"value\": \"奔驰-vyiozm\", \"children\": [{\"label\": \"GLC\", \"level\": 1, \"value\": \"GLC-auiwct\", \"children\": [{\"label\": \"2018款\", \"level\": 2, \"value\": \"2018款-dscvqt\", \"children\": [{\"label\": \"GLE 320 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 320 4MATIC 轿跑SUV-ubsxrh\", \"orderById\": 25}, {\"label\": \"GLE 400 4MATIC 臻藏版\", \"level\": 3, \"value\": \"GLE 400 4MATIC 臻藏版-dbzeyl\", \"orderById\": 26}], \"orderById\": 24}, {\"label\": \"2019款\", \"level\": 2, \"value\": \"2019款-masjxa\", \"children\": [{\"label\": \"GLE 400 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 400 4MATIC 轿跑SUV-aulzjp\", \"orderById\": 28}, {\"label\": \"GLE 500 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 500 4MATIC 轿跑SUV-agigeo\", \"orderById\": 29}], \"orderById\": 27}], \"orderById\": 23}, {\"label\": \"GLE\", \"level\": 1, \"value\": \"GLE-fknlwy\", \"children\": [{\"label\": \"2018款\", \"level\": 2, \"value\": \"2018款-dqyzpi\", \"children\": [{\"label\": \"GLE 320 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 320 4MATIC 轿跑SUV-hqexbx\", \"orderById\": 32}, {\"label\": \"GLE 400 4MATIC 臻藏版\", \"level\": 3, \"value\": \"GLE 400 4MATIC 臻藏版-thuvlb\", \"orderById\": 33}, {\"label\": \"GLE 400 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 400 4MATIC 轿跑SUV-spbwip\", \"orderById\": 34}], \"orderById\": 31}, {\"label\": \"2019款\", \"level\": 2, \"value\": \"2019款-pdouwv\", \"children\": [{\"label\": \"GLE 500 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 500 4MATIC 轿跑SUV-uqdoaa\", \"orderById\": 36}, {\"label\": \"GLE 300 d 4MATIC\", \"level\": 3, \"value\": \"GLE 300 d 4MATIC-eevjkp\", \"orderById\": 37}, {\"label\": \"GLE 350 d 4MATIC\", \"level\": 3, \"value\": \"GLE 350 d 4MATIC-srbsya\", \"orderById\": 38}, {\"label\": \"GLE 320 4MATIC 动感型臻藏版\", \"level\": 3, \"value\": \"GLE 320 4MATIC 动感型臻藏版-fcmqua\", \"orderById\": 39}], \"orderById\": 35}], \"orderById\": 30}], \"orderById\": 22}]','1',0,'2024-10-22 15:30:44.603000',1),('a2493734-00cd-4487-a98d-2708d9ceba59','dwsurvey-cascade-templ',4,100,'[{\"label\": \"宝马\", \"level\": 0, \"value\": \"宝马-vdbrbc\", \"children\": [{\"label\": \"X3\", \"level\": 1, \"value\": \"X3-mqused\", \"children\": [{\"label\": \"2014款\", \"level\": 2, \"value\": \"2014款-ikmxbu\", \"children\": [{\"label\": \"xDrive28i\", \"level\": 3, \"value\": \"xDrive28i-pxpbri\", \"orderById\": 3}, {\"label\": \"xDrive35i 典雅型\", \"level\": 3, \"value\": \"xDrive35i 典雅型-iruzhd\", \"orderById\": 4}, {\"label\": \"xDrive35i M运动型\", \"level\": 3, \"value\": \"xDrive35i M运动型-zbvdti\", \"orderById\": 5}], \"orderById\": 2}, {\"label\": \"2015款\", \"level\": 2, \"value\": \"2015款-ziokmr\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-zfnfku\", \"orderById\": 7}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-ibcjub\", \"orderById\": 8}], \"orderById\": 6}, {\"label\": \"2016款\", \"level\": 2, \"value\": \"2016款-kbixrg\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-hbxybd\", \"orderById\": 10}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-spuwhw\", \"orderById\": 11}], \"orderById\": 9}], \"orderById\": 1}, {\"label\": \"X5\", \"level\": 1, \"value\": \"X5-xrbvlt\", \"children\": [{\"label\": \"2014款\", \"level\": 2, \"value\": \"2014款-bjpqul\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-zbxdgr\", \"orderById\": 14}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-votuqq\", \"orderById\": 15}], \"orderById\": 13}, {\"label\": \"2015款\", \"level\": 2, \"value\": \"2015款-iocoqa\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-kzgdkh\", \"orderById\": 17}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-xvpdlv\", \"orderById\": 18}], \"orderById\": 16}, {\"label\": \"2016款\", \"level\": 2, \"value\": \"2016款-vczjnw\", \"children\": [{\"label\": \"xDrive35i 尊享型\", \"level\": 3, \"value\": \"xDrive35i 尊享型-uwvpce\", \"orderById\": 20}, {\"label\": \"xDrive35i M运动豪华型\", \"level\": 3, \"value\": \"xDrive35i M运动豪华型-ofjlyy\", \"orderById\": 21}], \"orderById\": 19}], \"orderById\": 12}], \"orderById\": 0}, {\"label\": \"奔驰\", \"level\": 0, \"value\": \"奔驰-dvvfsw\", \"children\": [{\"label\": \"GLC\", \"level\": 1, \"value\": \"GLC-qkgfvs\", \"children\": [{\"label\": \"2018款\", \"level\": 2, \"value\": \"2018款-kldnku\", \"children\": [{\"label\": \"GLE 320 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 320 4MATIC 轿跑SUV-fdtufp\", \"orderById\": 25}, {\"label\": \"GLE 400 4MATIC 臻藏版\", \"level\": 3, \"value\": \"GLE 400 4MATIC 臻藏版-qyonff\", \"orderById\": 26}], \"orderById\": 24}, {\"label\": \"2019款\", \"level\": 2, \"value\": \"2019款-psbfmx\", \"children\": [{\"label\": \"GLE 400 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 400 4MATIC 轿跑SUV-rgnxpa\", \"orderById\": 28}, {\"label\": \"GLE 500 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 500 4MATIC 轿跑SUV-sofsuf\", \"orderById\": 29}], \"orderById\": 27}], \"orderById\": 23}, {\"label\": \"GLE\", \"level\": 1, \"value\": \"GLE-esblyp\", \"children\": [{\"label\": \"2018款\", \"level\": 2, \"value\": \"2018款-vokbcr\", \"children\": [{\"label\": \"GLE 320 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 320 4MATIC 轿跑SUV-omgjgp\", \"orderById\": 32}, {\"label\": \"GLE 400 4MATIC 臻藏版\", \"level\": 3, \"value\": \"GLE 400 4MATIC 臻藏版-caacbj\", \"orderById\": 33}, {\"label\": \"GLE 400 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 400 4MATIC 轿跑SUV-yqbauc\", \"orderById\": 34}], \"orderById\": 31}, {\"label\": \"2019款\", \"level\": 2, \"value\": \"2019款-saugqi\", \"children\": [{\"label\": \"GLE 500 4MATIC 轿跑SUV\", \"level\": 3, \"value\": \"GLE 500 4MATIC 轿跑SUV-zbhrvf\", \"orderById\": 36}, {\"label\": \"GLE 300 d 4MATIC\", \"level\": 3, \"value\": \"GLE 300 d 4MATIC-ppnfxz\", \"orderById\": 37}, {\"label\": \"GLE 350 d 4MATIC\", \"level\": 3, \"value\": \"GLE 350 d 4MATIC-qpshql\", \"orderById\": 38}, {\"label\": \"GLE 320 4MATIC 动感型臻藏版\", \"level\": 3, \"value\": \"GLE 320 4MATIC 动感型臻藏版-nlqxru\", \"orderById\": 39}], \"orderById\": 35}], \"orderById\": 30}], \"orderById\": 22}]','1',0,'2024-10-22 15:30:24.874000',1);
/*!40000 ALTER TABLE `t_cascade_json` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dw_contacts_dept`
--

DROP TABLE IF EXISTS `t_dw_contacts_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dw_contacts_dept` (
  `id` varchar(55) NOT NULL,
  `admin_user_id` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `dept_code` varchar(255) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dw_contacts_dept`
--

LOCK TABLES `t_dw_contacts_dept` WRITE;
/*!40000 ALTER TABLE `t_dw_contacts_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_dw_contacts_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dw_contacts_tag`
--

DROP TABLE IF EXISTS `t_dw_contacts_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dw_contacts_tag` (
  `id` varchar(55) NOT NULL,
  `admin_user_id` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `number_contacts` int DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  `tag_type` int DEFAULT NULL,
  `term_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dw_contacts_tag`
--

LOCK TABLES `t_dw_contacts_tag` WRITE;
/*!40000 ALTER TABLE `t_dw_contacts_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_dw_contacts_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dw_nonce`
--

DROP TABLE IF EXISTS `t_dw_nonce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dw_nonce` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `nonce_str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dw_nonce`
--

LOCK TABLES `t_dw_nonce` WRITE;
/*!40000 ALTER TABLE `t_dw_nonce` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_dw_nonce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ent_app_info`
--

DROP TABLE IF EXISTS `t_ent_app_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_ent_app_info` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `descn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ent_app_info`
--

LOCK TABLES `t_ent_app_info` WRITE;
/*!40000 ALTER TABLE `t_ent_app_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_ent_app_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ent_dept`
--

DROP TABLE IF EXISTS `t_ent_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_ent_dept` (
  `id` varchar(55) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order_no` int DEFAULT NULL,
  `parent_id` varchar(55) DEFAULT NULL,
  `third_dept_id` varchar(255) DEFAULT NULL,
  `dept_code` varchar(255) DEFAULT NULL,
  `dept_level` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ent_dept`
--

LOCK TABLES `t_ent_dept` WRITE;
/*!40000 ALTER TABLE `t_ent_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_ent_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ent_user_dept`
--

DROP TABLE IF EXISTS `t_ent_user_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_ent_user_dept` (
  `id` varchar(55) NOT NULL,
  `dept_id` varchar(55) DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  `dept_ids_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ent_user_dept`
--

LOCK TABLES `t_ent_user_dept` WRITE;
/*!40000 ALTER TABLE `t_ent_user_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_ent_user_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_export_log`
--

DROP TABLE IF EXISTS `t_export_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_export_log` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `exp_up_qu` int DEFAULT NULL,
  `export_type` int NOT NULL,
  `param1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `param2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `param3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `param4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `progress` float NOT NULL,
  `thread_max_export_num` int DEFAULT NULL,
  `title_tag` int DEFAULT NULL,
  `total_time` float DEFAULT NULL,
  `exp_data_content` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_export_log`
--

LOCK TABLES `t_export_log` WRITE;
/*!40000 ALTER TABLE `t_export_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_export_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_import_error`
--

DROP TABLE IF EXISTS `t_import_error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_import_error` (
  `id` varchar(55) NOT NULL,
  `cell1value` varchar(255) DEFAULT NULL,
  `cell2value` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `db_id` varchar(55) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `row_index` int DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  `cell1_value` varchar(255) DEFAULT NULL,
  `cell2_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_mail_invite_inbox` (
  `id` varchar(55) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sendcloud_id` varchar(55) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `survey_mail_invite_id` varchar(55) DEFAULT NULL,
  `us_contacts_id` varchar(55) DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_mail_invite_inbox`
--

LOCK TABLES `t_mail_invite_inbox` WRITE;
/*!40000 ALTER TABLE `t_mail_invite_inbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_mail_invite_inbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_permission`
--

DROP TABLE IF EXISTS `t_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_permission` (
  `id` varchar(55) NOT NULL,
  `order_by_id` int DEFAULT NULL,
  `perm_code` varchar(255) DEFAULT NULL,
  `perm_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_permission`
--

LOCK TABLES `t_permission` WRITE;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
INSERT INTO `t_permission` VALUES ('100',NULL,'HT_SURVEY_GROUP_LIST','问卷分组列表'),('101',NULL,'HT_SURVEY_GROUP_CREATE','问卷分组创建'),('102',NULL,'HT_SURVEY_GROUP_EDIT','问卷分组编辑'),('103',NULL,'HT_SURVEY_GROUP_DELETE','问卷分组删除'),('104',NULL,'HT_SURVEY_MANAGER_SYS','管理系统问卷'),('105',NULL,'HT_SURVEY_MANAGER_DEPT','管理部门问卷'),('106',NULL,'HT_USER_MANAGER_SYS','管理系统用户'),('107',NULL,'HT_USER_MANAGER_DEPT','管理部门用户'),('108',NULL,'HT_DEPT_MANAGER_SYS','管理系统部门'),('109',NULL,'HT_DEPT_MANAGER_DEPT','管理部门部门'),('110',NULL,'HT_MANAGER_MENU','显示管理后台'),('111',NULL,'HT_USER_EDIT_ROLE','编辑用户角色'),('112',NULL,'HT_USER_EDIT_DEPT','编辑用户部门'),('113',NULL,'HT_DASHBOARD_ANALYSIS','控制台-分析中心'),('114',NULL,'HT_DASHBOARD_MONITOR','控制台-地区监控'),('115',NULL,'QT_SURVEY_DATA_ANALYSIS','前台数据统计'),('116',NULL,'QT_SURVEY_DATA_DISTRICT','前台地区统计'),('117',NULL,'HT_SURVEY_DATA_ANALYSIS','后台问卷数据统计概况'),('118',NULL,'HT_SURVEY_DATA_REPORT','后台问卷默认报告'),('119',NULL,'HT_SURVEY_DATA_DISTRICT','后台问卷地区统计'),('120',NULL,'HT_SURVEY_DATA_ANSWER_LIST','后台问卷答卷列表'),('121',NULL,'HT_SURVEY_DATA_ANSWER_DETAIL','管理员问卷答卷详情'),('122',NULL,'HT_SURVEY_COPY','管理员问卷复制'),('123',NULL,'HT_SURVEY_COLLECT_MENU','管理员问卷收集按钮'),('124',NULL,'HT_SURVEY_COLLECT_URL','管理员可查看收集地址'),('125',NULL,'HT_SURVEY_COLLECT_SITECOMP','管理员网站组件'),('126',NULL,'HT_SURVEY_COLLECT_IPS_LIST','管理员查看IP地址'),('127',NULL,'HT_SURVEY_COLLECT_BINDUSER_LIST','管理员查看绑定用户'),('128',NULL,'HT_SURVEY_COLLECT_FINISHGO_VIEW','管理员结束跳转配置'),('129',NULL,'HT_SURVEY_DATA_MENU','管理问卷数据统计按钮'),('130',NULL,'HT_SURVEY_PREVIEW','管理员问卷预览'),('131',NULL,'QT_SURVEY_PREVIEW','前台问卷预览'),('132',NULL,'QT_SURVEY_COPY','前台问卷复制'),('133',NULL,'QT_PDCOLLECT_MENU','前台问卷收集菜单'),('134',NULL,'QT_SURVEY_DATA_MENU','前台问卷数据菜单'),('135',NULL,'QT_SURVEY_DATA_ANSWER_EXPORT','前台问卷数据导出'),('136',NULL,'HT_SURVEY_DATA_ANSWER_EXPORT','后台问卷数据导出'),('137',NULL,'QT_SURVEY_DATA_ANSWER_DELETE','前台问卷数据删除'),('138',NULL,'HT_SURVEY_DATA_ANSWER_DELETE','后台问卷数据删除'),('402880e470950011017095064c970000',NULL,'HT_SURVEY_LIST','管理员查看问卷列表'),('402880e47095001101709507f9110001',NULL,'HT_SURVEY_EDIT','管理员编辑问卷'),('402880e47095001101709508a3800002',NULL,'HT_SURVEY_DELETE','管理员删除问卷'),('402880e4709500110170951167740016',NULL,'HT_PROTEMPLATER_LIST','管理员模板列表'),('402880e47095001101709511d1a10017',NULL,'HT_PROTEMPLATER_CREATE','创建项目模板'),('402880e470950011017095135e030018',NULL,'HT_PROTEMPLATER_EDIT','管理员编辑模板'),('402880e4709500110170951401d40019',NULL,'HT_PROTEMPLATER_DELETE','管理员删除模板'),('402880e470950011017095144638001a',NULL,'HT_PROTEMPLATER_DEV','管理员发布模板'),('402880e47095001101709516cbbc001f',NULL,'HT_CASCADEDB_LIST','级联数据源管理列表'),('402880e470950011017095173db00020',NULL,'HT_CASCADEDB_CREATE','创建级列题数据'),('402880e47095001101709517de810021',NULL,'HT_CASCADEDB_EDIT','编辑级联题数据源'),('402880e47095001101709518aac80022',NULL,'HT_CASCADEDB_DELETE','删除级联题数据源'),('402880e4709500110170951abdbb0023',NULL,'HT_USER_LIST','查看所属企业用户列表'),('402880e4709500110170951b41790024',NULL,'HT_USER_CREATE','添加所属企业的用户'),('402880e4709500110170951b8c390025',NULL,'HT_USER_EDIT','编辑所属企业的用户'),('402880e4709500110170951bda940026',NULL,'HT_USER_DELETE','删除所属企业用户'),('402880e4709500110170951c73f00027',NULL,'HT_USER_UPPWD','修改所属企业用户密码'),('402880e4709500110170951d27cc0028',NULL,'HT_USER_ADDROLE','添加所属企业用户的角色'),('402880e4709500110170951e55fb0029',NULL,'HT_USER_DIS','禁用所属企业的用户'),('402880e4709500110170951f2d6e002a',NULL,'HT_DEPT_LIST','查看所属企业部门列表'),('402880e4709500110170951f89fc002b',NULL,'HT_DEPT_CREATE','创建所属企业的部门'),('402880e47095001101709520058c002c',NULL,'HT_DEPT_EDIT','编辑所属企业部门信息'),('402880e4709500110170953a9593002d',NULL,'HT_DEPT_DELETE','删除所属企业的部门'),('402880e4709500110170953cd6e30030',NULL,'HT_USERAUDIT_LIST','账号申请信息审核列表'),('402880e4709500110170953d44c40031',NULL,'HT_USERAUDIT_EDIT','申请账号审核'),('402880e4709500110170953f5da70036',NULL,'HT_ROLE_LIST','系统角色列表'),('402880e4709500110170953fa5ea0037',NULL,'HT_ROLE_CREATE','创建新角色'),('402880e4709500110170953ff5720038',NULL,'HT_ROLE_EDIT','编辑角色内容'),('402880e470950011017095408ff60039',NULL,'HT_ROLE_EDIT_PERM','编辑角色权限'),('402880e47095001101709541066e003a',NULL,'HT_ROLE_DELETE','删除角色'),('402880e470950011017095418f1f003b',NULL,'HT_PERM_LIST','查看权限列表'),('402880e47095001101709541fe65003c',NULL,'HT_SYSTEMLOG_LIST','查看系统日志'),('402881e8708a451b01708a8bfbde0006',NULL,'QT_MYTASK_LIST','我的任务列表'),('402881e8708b9b3801708ba983de0000',NULL,'QT_PDCOLLECT_ANSWERURL','项目收集地址'),('402881e8708b9b3801708baaddac0001',NULL,'QT_PDCOLLECT_SHARE','项目社交分享'),('402881e8708b9b3801708bb295740006',NULL,'QT_PDCOLLECT_SITECOMP','网站组件'),('402881e8708b9b3801708bb3f84c0007',NULL,'QT_PDCOLLECT_WEIXIN','微信收集设置'),('402881e8708b9b3801708bb85027000c',NULL,'QT_PDCOLLECT_IPANSWER','IP地址段配置'),('402881e8708b9b3801708bb98d1c000e',NULL,'QT_PDCOLLECT_USERBIND','人事关联配置'),('402881e8708b9b3801708bba8a2d000f',NULL,'QT_PDCOLLECT_FINISH','结束导航配置'),('402881e8708c18bf01708c37bf030007',NULL,'QT_SURVEY_DEV','前台发布问卷'),('402881e9706d69dc01706d6fe0330000',NULL,'QT_SURVEY_LIST','问卷列表'),('402881e970869fbe017086c3fe69000c',NULL,'QT_TEMPLATER_LIST','模板列表'),('402881e970869fbe017086c47332000d',NULL,'QT_TEMPLATER_REF','引用模板'),('402881e970869fbe017086c51277000e',NULL,'QT_TEMPLATER_INFO','模板详情'),('402881ea707a3d6201707a40d6dc0000',NULL,'QT_SURVEY_CREATE','创建问卷'),('402881ea707a3d6201707a41316f0001',NULL,'QT_SURVEY_EDIT','修改问卷'),('402881ea707a3d6201707a417fd90002',NULL,'QT_SURVEY_DELETE','删除问卷'),('7e0080807093bb0101709418b2790000',NULL,'QT_SURVEY_DATA_REPORT','查看默认报告'),('7e0080807093bb010170942bd7200006',NULL,'QT_SURVEY_DATA_ANSWER_LIST','前台原始数据列表'),('7e0080807093bb010170942c81990007',NULL,'QT_SURVEY_DATA_ANSWER_DETAIL','前台原始数据详情');
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_cascade`
--

DROP TABLE IF EXISTS `t_qu_cascade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_cascade` (
  `id` varchar(55) NOT NULL,
  `cascade_date_id` varchar(55) DEFAULT NULL,
  `custom_qu_num` varchar(255) DEFAULT NULL,
  `is_hide` int DEFAULT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_cascade`
--

LOCK TABLES `t_qu_cascade` WRITE;
/*!40000 ALTER TABLE `t_qu_cascade` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_cascade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_checkbox`
--

DROP TABLE IF EXISTS `t_qu_checkbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_checkbox` (
  `id` varchar(55) NOT NULL,
  `check_type` int DEFAULT NULL,
  `is_note` int DEFAULT NULL,
  `is_required_fill` int DEFAULT NULL,
  `option_name` varchar(1255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `score_num` float DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_checkbox`
--

LOCK TABLES `t_qu_checkbox` WRITE;
/*!40000 ALTER TABLE `t_qu_checkbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_checkbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_chen_column`
--

DROP TABLE IF EXISTS `t_qu_chen_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_chen_column` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `col_check_type` int DEFAULT NULL,
  `is_required` int DEFAULT NULL,
  `score_num` float DEFAULT NULL,
  `col_option_text` varchar(255) DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_chen_option` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_chen_row` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_multi_fillblank` (
  `id` varchar(55) NOT NULL,
  `check_type` int DEFAULT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  `is_required` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_multi_fillblank`
--

LOCK TABLES `t_qu_multi_fillblank` WRITE;
/*!40000 ALTER TABLE `t_qu_multi_fillblank` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_multi_fillblank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_option`
--

DROP TABLE IF EXISTS `t_qu_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_option` (
  `id` varchar(55) NOT NULL,
  `check_type` int DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  `is_note` int DEFAULT NULL,
  `is_required_fill` int DEFAULT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `option_type` int DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_option`
--

LOCK TABLES `t_qu_option` WRITE;
/*!40000 ALTER TABLE `t_qu_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_orderby`
--

DROP TABLE IF EXISTS `t_qu_orderby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_orderby` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_radio` (
  `id` varchar(55) NOT NULL,
  `check_type` int DEFAULT NULL,
  `is_note` int DEFAULT NULL,
  `is_required_fill` int DEFAULT NULL,
  `option_name` varchar(1254) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `score_num` float DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_radio`
--

LOCK TABLES `t_qu_radio` WRITE;
/*!40000 ALTER TABLE `t_qu_radio` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_radio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_scale`
--

DROP TABLE IF EXISTS `t_qu_scale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_scale` (
  `id` varchar(55) NOT NULL,
  `custom_qu_num` varchar(255) DEFAULT NULL,
  `custom_title` varchar(255) DEFAULT NULL,
  `is_hide` int DEFAULT NULL,
  `left_title` varchar(255) DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `right_title` varchar(255) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_qu_scale`
--

LOCK TABLES `t_qu_scale` WRITE;
/*!40000 ALTER TABLE `t_qu_scale` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_qu_scale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_qu_score`
--

DROP TABLE IF EXISTS `t_qu_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_qu_score` (
  `id` varchar(55) NOT NULL,
  `option_name` varchar(255) DEFAULT NULL,
  `option_title` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `copy_from_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_question` (
  `id` varchar(55) NOT NULL,
  `answer_input_row` int DEFAULT NULL,
  `answer_input_width` int DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `cell_count` int DEFAULT NULL,
  `check_type` int DEFAULT NULL,
  `contacts_attr` int DEFAULT NULL,
  `contacts_field` varchar(255) DEFAULT NULL,
  `copy_from_id` varchar(55) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `hv` int DEFAULT NULL,
  `is_required` int DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `order_by_id` int DEFAULT NULL,
  `param_int01` int DEFAULT NULL,
  `param_int02` int DEFAULT NULL,
  `parent_qu_id` varchar(255) DEFAULT NULL,
  `qu_name` varchar(255) DEFAULT NULL,
  `qu_note` text,
  `qu_tag` int DEFAULT NULL,
  `qu_title` text,
  `qu_type` int DEFAULT NULL,
  `rand_order` int DEFAULT NULL,
  `tag` int DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `yesno_option` int DEFAULT NULL,
  `param_str01` varchar(255) DEFAULT NULL,
  `param03` varchar(255) DEFAULT NULL,
  `param_int03` int DEFAULT NULL,
  `param_str02` varchar(255) DEFAULT NULL,
  `field_key` varchar(255) DEFAULT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_question`
--

LOCK TABLES `t_question` WRITE;
/*!40000 ALTER TABLE `t_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_question_bank`
--

DROP TABLE IF EXISTS `t_question_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_question_bank` (
  `id` varchar(55) NOT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `bank_note` varchar(255) DEFAULT NULL,
  `bank_state` int DEFAULT NULL,
  `bank_tag` int DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `dir_type` int DEFAULT NULL,
  `excerpt_num` int DEFAULT NULL,
  `group_id1` varchar(55) DEFAULT NULL,
  `group_id2` varchar(55) DEFAULT NULL,
  `parent_id` varchar(55) DEFAULT NULL,
  `qu_num` int DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_question_logic` (
  `id` varchar(55) NOT NULL,
  `cg_qu_item_id` varchar(55) DEFAULT NULL,
  `ck_qu_id` varchar(55) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `ge_le` varchar(55) DEFAULT NULL,
  `logic_type` varchar(55) DEFAULT NULL,
  `score_num` int DEFAULT NULL,
  `sk_qu_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `sk_qu_id_end` varchar(55) DEFAULT NULL,
  `copy_form_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_question_logic`
--

LOCK TABLES `t_question_logic` WRITE;
/*!40000 ALTER TABLE `t_question_logic` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_question_logic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_question_logic_cal`
--

DROP TABLE IF EXISTS `t_question_logic_cal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_question_logic_cal` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cal_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cal_item_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cal_type` int DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `group_type` int DEFAULT NULL,
  `qu_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `target_group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `target_item_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cal_item_type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_question_logic_cal`
--

LOCK TABLES `t_question_logic_cal` WRITE;
/*!40000 ALTER TABLE `t_question_logic_cal` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_question_logic_cal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_random_code`
--

DROP TABLE IF EXISTS `t_random_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_random_code` (
  `id` varchar(55) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `rd_code` varchar(255) DEFAULT NULL,
  `rd_event` int DEFAULT NULL,
  `rd_name` varchar(255) DEFAULT NULL,
  `rd_status` int DEFAULT NULL,
  `rd_type` int DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  `scene_str` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_random_code`
--

LOCK TABLES `t_random_code` WRITE;
/*!40000 ALTER TABLE `t_random_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_random_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
  `id` varchar(55) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES ('402881ed707c9fbd01707ca04ab10000','2020-02-26 11:34:01','拥有系统所有权限','SUPER_ADMIN','超级管理员',1),('402881ed707c9fbd01707ca0bc2a0001','2020-02-26 11:34:14','拥有本机构所有权限','ENT_ADMIN','机构管理员',1),('402881ed707c9fbd01707ca0f6800002','2020-02-26 11:34:23','拥有本部门所有权限','DEPT_ADMIN','部门管理员',1),('402881ed707c9fbd01707ca156580003','2020-02-26 11:34:53','拥有本账号所有项目权限','PROJECT_ADMIN','项目管理员',1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_permission`
--

DROP TABLE IF EXISTS `t_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_permission` (
  `id` varchar(55) NOT NULL,
  `perm_id` varchar(55) DEFAULT NULL,
  `role_id` varchar(55) DEFAULT NULL,
  `perm_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_permission`
--

LOCK TABLES `t_role_permission` WRITE;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
INSERT INTO `t_role_permission` VALUES ('0a9a6d6d-e7e0-433f-b579-d234556fae1d','7e0080807093bb0101709418b2790000','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_REPORT'),('0ba585d8-b2ef-4448-ba25-d8890ac053a2','103','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_GROUP_DELETE'),('0d30184e-f6f4-45b5-9b08-688a6c5bd9eb','402881ea707a3d6201707a40d6dc0000','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_CREATE'),('0f9794a7-7fc8-4ee1-9059-b6cd61550050','110','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_MANAGER_MENU'),('10c0b4c3-da47-436f-9ebd-3c82341ccb82','402880e4709500110170951401d40019','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_PROTEMPLATER_DELETE'),('11cc90cd-d0e1-4aa1-97d1-4e84dbcc1325','123','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_COLLECT_MENU'),('13f080f5-38ab-4612-aad7-9c54ab7e2a87','111','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_EDIT_ROLE'),('185e967d-6ab8-4ba6-822d-25be1228ea76','118','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_DATA_REPORT'),('1a518ff5-7305-4adc-b24e-fb1e4c17da6a','7e0080807093bb010170942c81990007','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_ANSWER_DETAIL'),('1a90ef34-1012-442c-861a-f45658c772fb','122','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_COPY'),('20348632-5b6b-437c-a92f-cc15bc76eda3','402881e8708b9b3801708bb98d1c000e','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_PDCOLLECT_USERBIND'),('21c23ca0-68b9-49a2-bbb1-8dd2f6b103d7','402880e47095001101709520058c002c','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_DEPT_EDIT'),('220ca1d0-48fb-47a9-bed1-f1a8d3a300c5','402881e9706d69dc01706d6fe0330000','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_LIST'),('245b5af2-dd64-449e-9aa9-7ca7f576a973','138','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_DATA_ANSWER_DELETE'),('26590156-0479-4781-be80-de59b115995d','402880e4709500110170953fa5ea0037','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_ROLE_CREATE'),('2c1bdaa7-5262-43ee-9cf7-f8222e865b4f','125','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_COLLECT_SITECOMP'),('33907516-340f-4a09-8a24-cfe48fec5468','402880e47095001101709511d1a10017','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_PROTEMPLATER_CREATE'),('35b74941-8953-48ad-81d6-c7116e6db3a8','102','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_GROUP_EDIT'),('399e4218-3599-4d85-98fe-35841aca840d','402880e4709500110170951bda940026','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_DELETE'),('3b1456b9-3b21-49c6-9d52-9b768564c433','402880e4709500110170951167740016','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_PROTEMPLATER_LIST'),('3dd6b177-a083-442b-bf99-5812cbe5c222','402880e4709500110170953a9593002d','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_DEPT_DELETE'),('40d0e2cd-1a35-4b5e-aeed-e3f3955b34e7','402880e47095001101709541066e003a','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_ROLE_DELETE'),('419ff2f1-c540-442b-80f1-d46fa1e8ab38','402880e470950011017095135e030018','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_PROTEMPLATER_EDIT'),('44b553da-de2c-4163-8b62-7336e40837e8','402880e4709500110170951f2d6e002a','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_DEPT_LIST'),('494857a4-3d54-4474-be21-e23835854592','402880e4709500110170951f89fc002b','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_DEPT_CREATE'),('4c829469-a3e5-4f88-b6f2-3475107ac9bc','117','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_DATA_ANALYSIS'),('50a8a54b-dd89-4087-ae30-40b1c6dfb889','402880e47095001101709508a3800002','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_DELETE'),('540a717c-cb51-4141-8a2d-b6cf8d54a7b9','133','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_PDCOLLECT_MENU'),('5781ce59-192d-42f9-97e2-e80c0ea19b90','126','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_COLLECT_IPS_LIST'),('5c4e244f-f045-430a-a0cc-af353cd33db0','100','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_GROUP_LIST'),('60c075b0-5628-4688-b1f3-ea187d894e7b','112','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_EDIT_DEPT'),('6747a146-c885-4b55-b584-981c3a57b216','121','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_DATA_ANSWER_DETAIL'),('68a90d75-1fd4-4807-81b7-4a0fcb2523f0','402881ea707a3d6201707a41316f0001','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_EDIT'),('6ebf8ad2-fdff-4e0f-96f8-0ad7fb872c2c','402880e470950011017095144638001a','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_PROTEMPLATER_DEV'),('6eece328-540c-4986-8ced-4e30b352cc8f','402880e47095001101709517de810021','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_CASCADEDB_EDIT'),('74365efb-fd5c-43b1-928a-9f3c90fd6d60','402881e8708b9b3801708bb85027000c','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_PDCOLLECT_IPANSWER'),('7802e682-cde8-4460-b7a4-d51f169a5290','402880e47095001101709516cbbc001f','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_CASCADEDB_LIST'),('7a3e0c81-0c51-449e-bcea-ec6ecbb824b2','402880e4709500110170953f5da70036','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_ROLE_LIST'),('7d4f0880-6637-42d0-af85-d329601b8968','101','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_GROUP_CREATE'),('7efc1840-d033-4e1a-9f0a-90ab5f3c4ee7','137','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_ANSWER_DELETE'),('87096531-19e7-41e5-a1b3-2a520476900f','402880e4709500110170951b8c390025','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_EDIT'),('8951379c-df79-4e7b-b700-e12142fac49a','128','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_COLLECT_FINISHGO_VIEW'),('8d369f8a-d3ed-4233-a4df-3fa3b8ec99d1','402880e4709500110170951abdbb0023','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_LIST'),('90904400-6fd3-4513-a818-317fc22d67ca','120','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_DATA_ANSWER_LIST'),('9923880d-acfc-46ba-9dad-986d974570cb','402881e8708b9b3801708ba983de0000','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_PDCOLLECT_ANSWERURL'),('9d94e772-2e85-4bbf-9395-3343a39c1183','114','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_DASHBOARD_MONITOR'),('9f928f28-84e4-4d60-ab02-0f8a2161259e','109','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_DEPT_MANAGER_DEPT'),('9ff338c9-ca17-4b11-85a3-9eece7a5db38','7e0080807093bb010170942bd7200006','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_ANSWER_LIST'),('a130c9b5-57fc-4d47-b95b-413a0e283847','7e0080807093bb010170943700250008','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_PDSTATS_SVZHENBIE'),('aaa5f13c-c74e-47d4-8003-f57f61290cac','131','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_PREVIEW'),('b04eac1a-4d44-4522-a6d1-22d3fcb133af','402881e8708b9b3801708bb295740006','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_PDCOLLECT_SITECOMP'),('b38ceb36-7046-406f-bc6c-a9e22628a89c','402881e8708b9b3801708bba8a2d000f','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_PDCOLLECT_FINISH'),('b4438c39-a83a-4eda-a038-eb2d6c5365c5','116','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_DISTRICT'),('b5aa8b21-a36b-41cc-9ef5-a5159fd9dcab','402881e8708c18bf01708c37bf030007','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DEV'),('bb705a11-a5fa-400f-8f5b-35aff8e20b2e','107','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_MANAGER_DEPT'),('bd32204d-199a-4f0d-ac5e-a1c88c7d6163','402880e470950011017095064c970000','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_LIST'),('be3e7e10-fbc9-4b39-a034-653b44e7e23d','402880e470950011017095418f1f003b','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_PERM_LIST'),('c807456a-f633-43bc-b89b-405a73530e63','402880e470950011017095408ff60039','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_ROLE_EDIT_PERM'),('c8b70857-f224-449f-b61c-3ec2228fd85e','134','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_MENU'),('ced1f97e-f1e6-4c63-95f2-c5fa4c4d6205','402880e47095001101709518aac80022','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_CASCADEDB_DELETE'),('d12326b6-54a2-474e-9026-ac5f51301302','402880e4709500110170953ff5720038','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_ROLE_EDIT'),('d8763b74-4063-4a34-951a-7781bf2b7586','124','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_COLLECT_URL'),('d8d00a3d-63e6-40dc-a65b-c9d2750851d3','129','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_DATA_MENU'),('d9f8b07d-3073-4118-979a-20c1ead4edd4','113','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_DASHBOARD_ANALYSIS'),('e5ec81fb-fad1-4cb4-8696-0abaf98280b5','402880e4709500110170951b41790024','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_CREATE'),('eb17ce12-51e5-4798-ade2-4d0803328955','402880e47095001101709541fe65003c','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SYSTEMLOG_LIST'),('f1b5a2d6-3932-4c8d-ab5c-3beca6455a80','127','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_COLLECT_BINDUSER_LIST'),('f415b9be-1645-43ae-9747-73f3182edba7','105','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_SURVEY_MANAGER_DEPT'),('f47e6c8c-aba3-49a8-9e22-bb81cb8d3456','402880e470950011017095173db00020','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_CASCADEDB_CREATE'),('f4e4bbc6-eacf-42e9-a7b2-0c60ff8ea31f','135','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_ANSWER_EXPORT'),('f51011e0-cad7-44e7-b147-43b51aa83182','115','4f0e4741-5722-4f3d-b793-33d7b52e0f66','QT_SURVEY_DATA_ANALYSIS'),('fa82e9e0-6c92-44bf-bcc5-79a49791e1d7','402880e4709500110170951d27cc0028','4f0e4741-5722-4f3d-b793-33d7b52e0f66','HT_USER_ADDROLE');
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer`
--

DROP TABLE IF EXISTS `t_survey_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_answer` (
  `id` varchar(55) NOT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `bg_an_date` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `complete_item_num` int DEFAULT NULL,
  `complete_num` int DEFAULT NULL,
  `data_source` int DEFAULT NULL,
  `end_an_date` datetime DEFAULT NULL,
  `handle_state` int DEFAULT NULL,
  `ip_addr` varchar(255) DEFAULT NULL,
  `is_complete` int DEFAULT NULL,
  `is_effective` int DEFAULT NULL,
  `pc_mac` varchar(255) DEFAULT NULL,
  `qu_num` int DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `total_time` float DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  `bro` int DEFAULT NULL,
  `pcm` int DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `sys` int DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `pwd_code` varchar(255) DEFAULT NULL,
  `total_score` float DEFAULT NULL,
  `total_score_status` int DEFAULT NULL,
  `an_user_key` varchar(255) DEFAULT NULL,
  `break_type` int DEFAULT NULL,
  `edit_answer_id` varchar(255) DEFAULT NULL,
  `edit_user_id` varchar(255) DEFAULT NULL,
  `op` varchar(255) DEFAULT NULL,
  `third_edit_auth` int DEFAULT NULL,
  `third_edit_uid` varchar(255) DEFAULT NULL,
  `third_edit_uname` varchar(255) DEFAULT NULL,
  `version_group_id` varchar(255) DEFAULT NULL,
  `answer_json_status` int DEFAULT NULL,
  `answer_obj_status` int DEFAULT NULL,
  `answer_save_type` int DEFAULT NULL,
  `answer_save_status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`),
  KEY `survey_id_index` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer`
--

LOCK TABLES `t_survey_answer` WRITE;
/*!40000 ALTER TABLE `t_survey_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer_json`
--

DROP TABLE IF EXISTS `t_survey_answer_json`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_answer_json` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `answer_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `stats_tag` int DEFAULT NULL,
  `survey_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `answer_obj_status` int DEFAULT NULL,
  `an_random_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `json_version` int DEFAULT NULL,
  `sid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer_json`
--

LOCK TABLES `t_survey_answer_json` WRITE;
/*!40000 ALTER TABLE `t_survey_answer_json` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_answer_json` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer_log`
--

DROP TABLE IF EXISTS `t_survey_answer_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_answer_log` (
  `id` varchar(55) NOT NULL,
  `answer_id` varchar(55) DEFAULT NULL,
  `bro` int DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `dt` int DEFAULT NULL,
  `ip` varchar(55) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `sys` int DEFAULT NULL,
  `user_agent` text,
  `user_id` varchar(55) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `ip_addr` varchar(255) DEFAULT NULL,
  `last_date` datetime(6) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `total_request` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer_log`
--

LOCK TABLES `t_survey_answer_log` WRITE;
/*!40000 ALTER TABLE `t_survey_answer_log` DISABLE KEYS */;
INSERT INTO `t_survey_answer_log` VALUES ('66aa0c58-5d08-4e56-9a0a-042aa82654b6',NULL,5,'2024-10-30 23:57:30.001000',1,'192.168.1.73','A323366C06ACE817A6156999DB0A9171','b0051f52-c63f-410e-910e-f63681b78686',4,'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36','1','未知','局域网','2024-10-30 23:57:52.242000','未知',2);
/*!40000 ALTER TABLE `t_survey_answer_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer_log_copy_back`
--

DROP TABLE IF EXISTS `t_survey_answer_log_copy_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_answer_log_copy_back` (
  `id` varchar(55) NOT NULL,
  `answer_id` varchar(55) DEFAULT NULL,
  `bro` int DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `dt` int DEFAULT NULL,
  `ip` varchar(55) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `sys` int DEFAULT NULL,
  `user_agent` text,
  `user_id` varchar(55) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `ip_addr` varchar(255) DEFAULT NULL,
  `last_date` datetime(6) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `total_request` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer_log_copy_back`
--

LOCK TABLES `t_survey_answer_log_copy_back` WRITE;
/*!40000 ALTER TABLE `t_survey_answer_log_copy_back` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_answer_log_copy_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer_log_copy_new`
--

DROP TABLE IF EXISTS `t_survey_answer_log_copy_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_answer_log_copy_new` (
  `id` varchar(55) NOT NULL,
  `answer_id` varchar(55) DEFAULT NULL,
  `bro` int DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `dt` int DEFAULT NULL,
  `ip` varchar(55) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `sys` int DEFAULT NULL,
  `user_agent` text,
  `user_id` varchar(55) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `ip_addr` varchar(255) DEFAULT NULL,
  `last_date` datetime(6) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `total_request` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer_log_copy_new`
--

LOCK TABLES `t_survey_answer_log_copy_new` WRITE;
/*!40000 ALTER TABLE `t_survey_answer_log_copy_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_answer_log_copy_new` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer_log_stats`
--

DROP TABLE IF EXISTS `t_survey_answer_log_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_answer_log_stats` (
  `id` varchar(55) NOT NULL,
  `data_date` datetime(6) DEFAULT NULL,
  `log_backup_path` varchar(255) DEFAULT NULL,
  `log_stats_name` varchar(255) DEFAULT NULL,
  `log_type` int DEFAULT NULL,
  `survey_id` varchar(255) DEFAULT NULL,
  `sync_date` datetime(6) DEFAULT NULL,
  `total_pr` bigint DEFAULT NULL,
  `up_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer_log_stats`
--

LOCK TABLES `t_survey_answer_log_stats` WRITE;
/*!40000 ALTER TABLE `t_survey_answer_log_stats` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_answer_log_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_answer_version`
--

DROP TABLE IF EXISTS `t_survey_answer_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_answer_version` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `an_user_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `answer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `commit_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `commit_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `survey_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `third_edit_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `third_edit_uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `version_group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_answer_version`
--

LOCK TABLES `t_survey_answer_version` WRITE;
/*!40000 ALTER TABLE `t_survey_answer_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_answer_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_detail`
--

DROP TABLE IF EXISTS `t_survey_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_detail` (
  `id` varchar(55) NOT NULL,
  `an_item_least_num` int DEFAULT NULL,
  `an_item_most_num` int DEFAULT NULL,
  `dir_id` varchar(55) DEFAULT NULL,
  `effective` int DEFAULT NULL,
  `effective_ip` int DEFAULT NULL,
  `effective_time` int DEFAULT NULL,
  `end_num` int DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `end_type` int DEFAULT NULL,
  `mail_only` int DEFAULT NULL,
  `refresh` int DEFAULT NULL,
  `refresh_num` int DEFAULT NULL,
  `rule` int DEFAULT NULL,
  `rule_code` varchar(255) DEFAULT NULL,
  `show_answer_da` int DEFAULT NULL,
  `show_share_survey` int DEFAULT NULL,
  `survey_note` text,
  `survey_qu_num` int DEFAULT NULL,
  `yn_end_num` int DEFAULT NULL,
  `yn_end_time` int DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `yn_start_time` int DEFAULT NULL,
  `req_target` int DEFAULT NULL,
  `req_url` varchar(255) DEFAULT NULL,
  `req_url_type` int DEFAULT NULL,
  `url_text` varchar(255) DEFAULT NULL,
  `cal_sum_score` int DEFAULT NULL,
  `max_score` float DEFAULT NULL,
  `min_score` float DEFAULT NULL,
  `only_ips` int DEFAULT NULL,
  `only_wx_answer` int DEFAULT NULL,
  `rule_code_num` int DEFAULT NULL,
  `wx_answer_num` int DEFAULT NULL,
  `wx_userinfo` int DEFAULT NULL,
  `an_login` int DEFAULT NULL,
  `an_login_num` int DEFAULT NULL,
  `an_user_key_action` int DEFAULT NULL,
  `an_user_key_check` int DEFAULT NULL,
  `an_user_key_num` int DEFAULT NULL,
  `an_user_key_perm` int DEFAULT NULL,
  `breakpoint1` int DEFAULT NULL,
  `filter_go` int DEFAULT NULL,
  `only_users` int DEFAULT NULL,
  `open_receive_email` int DEFAULT NULL,
  `opoq` int DEFAULT NULL,
  `receive_email` varchar(255) DEFAULT NULL,
  `thank_text` varchar(255) DEFAULT NULL,
  `an_user_key_no` int DEFAULT NULL,
  `req_url1` varchar(255) DEFAULT NULL,
  `show_sum_score` int DEFAULT NULL,
  `other_client_check` int DEFAULT NULL,
  `survey_note_text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_detail`
--

LOCK TABLES `t_survey_detail` WRITE;
/*!40000 ALTER TABLE `t_survey_detail` DISABLE KEYS */;
INSERT INTO `t_survey_detail` VALUES ('c61d95cd-3582-45a1-835d-01e949f1a536',0,0,'b0051f52-c63f-410e-910e-f63681b78686',1,0,5,1000,NULL,1,0,1,3,1,'令牌',0,1,'非常感谢您的参与！如有涉及个人信息，我们将严格保密。',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `t_survey_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_directory`
--

DROP TABLE IF EXISTS `t_survey_directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_directory` (
  `id` varchar(55) NOT NULL,
  `an_item_least_num` int DEFAULT NULL,
  `answer_num` int DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `dir_type` int DEFAULT NULL,
  `excerpt_num` int DEFAULT NULL,
  `html_path` varchar(255) DEFAULT NULL,
  `is_share` int DEFAULT NULL,
  `parent_id` varchar(55) DEFAULT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `survey_detail_id` varchar(55) DEFAULT NULL,
  `survey_model` int DEFAULT NULL,
  `survey_name` varchar(255) DEFAULT NULL,
  `survey_qu_num` int DEFAULT NULL,
  `survey_state` int DEFAULT NULL,
  `survey_tag` int DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  `view_answer` int DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `survey_type` varchar(55) DEFAULT NULL,
  `orderby_num` int DEFAULT NULL,
  `edit_date` datetime(6) DEFAULT NULL,
  `group_id1` varchar(55) DEFAULT NULL,
  `group_id2` varchar(55) DEFAULT NULL,
  `json_path` varchar(255) DEFAULT NULL,
  `bind_id` varchar(255) DEFAULT NULL,
  `ref_id` varchar(255) DEFAULT NULL,
  `survey_name_text` varchar(255) DEFAULT NULL,
  `view_task` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_directory`
--

LOCK TABLES `t_survey_directory` WRITE;
/*!40000 ALTER TABLE `t_survey_directory` DISABLE KEYS */;
INSERT INTO `t_survey_directory` VALUES ('b0051f52-c63f-410e-910e-f63681b78686',0,1,'2024-10-30 23:57:10',2,0,NULL,1,'','coy7scq',NULL,1,'Hello DWSurvey',0,1,1,'1',0,1,'survey',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Test',NULL);
/*!40000 ALTER TABLE `t_survey_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_group`
--

DROP TABLE IF EXISTS `t_survey_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_group` (
  `id` varchar(55) NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `descn` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `group_state` int DEFAULT NULL,
  `group_type` int DEFAULT NULL,
  `orderby_num` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_group`
--

LOCK TABLES `t_survey_group` WRITE;
/*!40000 ALTER TABLE `t_survey_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_json`
--

DROP TABLE IF EXISTS `t_survey_json`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_json` (
  `id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `save_date` datetime(6) DEFAULT NULL,
  `survey_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `survey_json_text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `use_version` int DEFAULT NULL,
  `survey_json_simple` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `sid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `surveyId` (`survey_id`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_json`
--

LOCK TABLES `t_survey_json` WRITE;
/*!40000 ALTER TABLE `t_survey_json` DISABLE KEYS */;
INSERT INTO `t_survey_json` VALUES ('65a8949d-25e3-463d-8b32-e11682af637d','2025-01-21 11:57:17.128000','b0051f52-c63f-410e-910e-f63681b78686','{\"id\":\"b0051f52-c63f-410e-910e-f63681b78686\",\"sid\":\"coy7scq\",\"parentId\":\"\",\"surveyName\":\"Test\",\"surveyNameText\":\"Test\",\"userId\":\"1\",\"dirType\":2,\"surveyDetailId\":null,\"createDate\":\"2024-10-30 23:57:10\",\"surveyState\":0,\"surveyQuNum\":0,\"anItemLeastNum\":0,\"answerNum\":null,\"visibility\":1,\"surveyModel\":1,\"viewAnswer\":0,\"isShare\":1,\"excerptNum\":0,\"surveyTag\":1,\"htmlPath\":null,\"jsonPath\":null,\"surveyType\":\"survey\",\"refId\":null,\"groupId1\":null,\"groupId2\":null,\"groupName\":null,\"surveyDetail\":{\"id\":\"c61d95cd-3582-45a1-835d-01e949f1a536\",\"dirId\":\"b0051f52-c63f-410e-910e-f63681b78686\",\"effective\":1,\"effectiveTime\":5,\"effectiveIp\":0,\"refresh\":1,\"refreshNum\":3,\"rule\":1,\"ruleCode\":\"令牌\",\"endType\":1,\"endTime\":null,\"endNum\":1000,\"surveyNote\":\"非常感谢您的参与！如有涉及个人信息，我们将严格保密。\",\"surveyNoteText\":null,\"ynEndNum\":0,\"ynEndTime\":0,\"surveyQuNum\":0,\"anItemLeastNum\":0,\"anItemMostNum\":0,\"mailOnly\":0,\"showShareSurvey\":1,\"showAnswerDa\":0,\"surveyNodeObj\":{\"dwHtml\":\"非常感谢您的参与！如有涉及个人信息，我们将严格保密。\",\"dwText\":\"\",\"dwPlaceholder\":\"请输入问卷介绍\"},\"effective_model\":true,\"effectiveIp_model\":false,\"refresh_model\":true,\"rule_model\":false,\"ynEndNum_model\":false,\"endNum_model\":1000,\"ynEndTime_model\":false,\"dwId\":\"96b63244-b583-4ec3-bcd1-be49c123b85b\"},\"userName\":\"admin\",\"questions\":[{\"id\":null,\"belongId\":null,\"quName\":\"单选题\",\"quTitle\":\"单选题标题\",\"quNote\":null,\"keywords\":null,\"quType\":\"RADIO\",\"tag\":null,\"orderById\":null,\"createDate\":\"2024-10-30 23:57:11\",\"quTag\":1,\"parentQuId\":null,\"yesnoOption\":null,\"isRequired\":0,\"checkType\":null,\"paramInt01\":0,\"paramInt02\":0,\"visibility\":1,\"copyFromId\":null,\"hv\":2,\"randOrder\":0,\"cellCount\":2,\"contactsAttr\":0,\"contactsField\":null,\"answerInputWidth\":null,\"answerInputRow\":null,\"questions\":[],\"quRadios\":[{\"id\":null,\"quId\":null,\"optionTitle\":\"选项1\",\"optionName\":\"选项1\",\"isNote\":0,\"checkType\":null,\"isRequiredFill\":0,\"orderById\":null,\"visibility\":1,\"anCount\":0,\"optionTitleObj\":{\"dwHtml\":\"选项1\",\"dwText\":\"选项1\",\"dwPlaceholder\":\"请输入选项内容\",\"isRefreshValue\":true},\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":0,\"dwId\":\"7dc54663-4bca-4f55-9976-a6a921f0751d\"},{\"id\":null,\"quId\":null,\"optionTitle\":\"选项2\",\"optionName\":\"选项2\",\"isNote\":0,\"checkType\":null,\"isRequiredFill\":0,\"orderById\":null,\"visibility\":1,\"anCount\":0,\"optionTitleObj\":{\"dwHtml\":\"选项2\",\"dwText\":\"选项2\",\"dwPlaceholder\":\"请输入选项内容\",\"isRefreshValue\":true},\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":0,\"dwId\":\"72d7e96d-993e-463a-afe9-f97ef04e23e5\"}],\"quCheckboxs\":[],\"quMultiFillblanks\":[],\"quScores\":[],\"quOrderbys\":[],\"rowContent\":\"\",\"colContent\":\"\",\"optionContent\":\"\",\"removeOptionUuIds\":null,\"anAnswer\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"answer\":null,\"visibility\":1},\"anCheckboxs\":[],\"anDFillblanks\":[],\"anEnumqus\":[],\"anFillblank\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"answer\":null,\"visibility\":1},\"anRadio\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"quItemId\":null,\"otherText\":null,\"visibility\":1},\"anYesno\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"yesnoAnswer\":null,\"visibility\":1},\"anScores\":[],\"anCount\":0,\"anOrders\":[],\"anUplodFiles\":[],\"questionLogics\":[],\"statJson\":\"\",\"dwsurveyfont\":null,\"eventName\":null,\"dwQuIcon\":\"<i class=\\\"fa-regular fa-circle-dot\\\"></i>\",\"quOptions\":null,\"quRows\":null,\"quCols\":null,\"showQu\":true,\"logicIsHide\":false,\"quTitleObj\":{\"dwHtml\":\"单选题标题\",\"dwText\":\"单选题\",\"dwPlaceholder\":\"请输入题目标题\",\"isNew\":false,\"isRefreshValue\":true},\"quNoteObj\":{\"dwHtml\":null,\"dwText\":null,\"dwPlaceholder\":\"请输入题目备注\",\"isNew\":false,\"isRefreshValue\":true},\"quAttr\":{\"isRequired\":true,\"scoreAttr\":{\"maxScore\":0,\"designShowScoreNum\":false},\"showQuNote\":false},\"quTypeName\":\"单选题\",\"validateObj\":{\"errorText\":\"\",\"isOk\":true,\"isAnswerOk\":false},\"itemClick\":false,\"quFocusObj\":{\"quFocus\":false,\"quSetShow\":false,\"quLogicShow\":false,\"quMoreOptionShow\":false,\"quMoreOptionShowEdit\":false,\"quScorePopoverShow\":false,\"quScaleTextPopoverShow\":false,\"quMoreOptionColShow\":false},\"tempAttr\":{\"cascadeJsonOptions\":null},\"dwId\":\"3889be76-49e3-4cd3-917f-42b73d67478c\",\"isNew\":false,\"pageIndex\":1},{\"id\":null,\"belongId\":null,\"quName\":\"多选题\",\"quTitle\":\"多选题标题\",\"quNote\":null,\"keywords\":null,\"quType\":\"CHECKBOX\",\"tag\":null,\"orderById\":null,\"createDate\":\"2025-01-21 19:51:55\",\"quTag\":1,\"parentQuId\":null,\"yesnoOption\":null,\"isRequired\":0,\"checkType\":null,\"paramInt01\":0,\"paramInt02\":0,\"visibility\":1,\"copyFromId\":null,\"hv\":2,\"randOrder\":0,\"cellCount\":2,\"contactsAttr\":0,\"contactsField\":null,\"answerInputWidth\":null,\"answerInputRow\":null,\"questions\":[],\"quRadios\":[],\"quCheckboxs\":[{\"id\":null,\"quId\":null,\"optionTitle\":\"选项1\",\"optionName\":\"选项1\",\"isNote\":0,\"checkType\":null,\"isRequiredFill\":0,\"orderById\":null,\"visibility\":1,\"anCount\":null,\"optionTitleObj\":{\"dwHtml\":\"选项1\",\"dwText\":\"选项1\",\"dwPlaceholder\":\"请输入选项内容\",\"isRefreshValue\":true},\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":0,\"dwId\":\"6725f55e-e1d9-497c-8ed6-f3da7cad554c\"},{\"id\":null,\"quId\":null,\"optionTitle\":\"选项2\",\"optionName\":\"选项2\",\"isNote\":0,\"checkType\":null,\"isRequiredFill\":0,\"orderById\":null,\"visibility\":1,\"anCount\":null,\"optionTitleObj\":{\"dwHtml\":\"选项2\",\"dwText\":\"选项2\",\"dwPlaceholder\":\"请输入选项内容\",\"isRefreshValue\":true},\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":0,\"dwId\":\"c36d46f3-7f06-4905-9fe3-602f9ee57fc7\"}],\"quMultiFillblanks\":[],\"quScores\":[],\"quOrderbys\":[],\"rowContent\":\"\",\"colContent\":\"\",\"optionContent\":\"\",\"removeOptionUuIds\":null,\"anAnswer\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"answer\":null,\"visibility\":1},\"anCheckboxs\":[],\"anDFillblanks\":[],\"anEnumqus\":[],\"anFillblank\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"answer\":null,\"visibility\":1},\"anRadio\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"quItemId\":null,\"otherText\":null,\"visibility\":1},\"anYesno\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"yesnoAnswer\":null,\"visibility\":1},\"anScores\":[],\"anCount\":0,\"anOrders\":[],\"anUplodFiles\":[],\"questionLogics\":[],\"statJson\":\"\",\"dwsurveyfont\":null,\"eventName\":null,\"dwQuIcon\":\"<i class=\\\"fa-regular fa-square-check\\\"></i>\",\"quOptions\":null,\"quRows\":null,\"quCols\":null,\"showQu\":true,\"logicIsHide\":false,\"quTitleObj\":{\"dwHtml\":\"多选题标题\",\"dwText\":\"多选题\",\"dwPlaceholder\":\"请输入题目标题\",\"isNew\":false,\"isRefreshValue\":true},\"quNoteObj\":{\"dwHtml\":null,\"dwText\":null,\"dwPlaceholder\":\"请输入题目备注\",\"isNew\":false,\"isRefreshValue\":true},\"quAttr\":{\"isRequired\":true,\"scoreAttr\":{\"maxScore\":0,\"designShowScoreNum\":false,\"allRight\":{\"enabled\":false,\"scoreNum\":0}},\"showQuNote\":false},\"quTypeName\":\"多选题\",\"validateObj\":{\"errorText\":\"\",\"isOk\":true,\"isAnswerOk\":false},\"itemClick\":false,\"quFocusObj\":{\"quFocus\":false,\"quSetShow\":false,\"quLogicShow\":false,\"quMoreOptionShow\":false,\"quMoreOptionShowEdit\":false,\"quScorePopoverShow\":false,\"quScaleTextPopoverShow\":false,\"quMoreOptionColShow\":false},\"dwId\":\"0c92e499-d53a-468f-bd0b-d9a0436325c6\",\"isNew\":false,\"pageIndex\":1},{\"id\":null,\"belongId\":null,\"quName\":\"排序题\",\"quTitle\":\"排序题标题\",\"quNote\":null,\"keywords\":null,\"quType\":\"ORDERQU\",\"tag\":null,\"orderById\":null,\"createDate\":\"2024-10-30 23:57:11\",\"quTag\":1,\"parentQuId\":null,\"yesnoOption\":null,\"isRequired\":0,\"checkType\":null,\"paramInt01\":0,\"paramInt02\":0,\"visibility\":1,\"copyFromId\":null,\"hv\":2,\"randOrder\":0,\"cellCount\":0,\"contactsAttr\":0,\"contactsField\":null,\"answerInputWidth\":null,\"answerInputRow\":null,\"questions\":[],\"quRadios\":[],\"quCheckboxs\":[],\"quMultiFillblanks\":[],\"quScores\":[],\"quOrderbys\":[{\"id\":null,\"quId\":null,\"optionTitle\":\"选项1\",\"optionName\":\"选项1\",\"orderById\":null,\"visibility\":1,\"anOrderSum\":0,\"optionTitleObj\":{\"dwHtml\":\"选项1\",\"dwText\":\"选项1\",\"dwPlaceholder\":\"请输入选项内容\",\"isRefreshValue\":false},\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":null,\"dwId\":\"829a0d28-93a0-476a-aca8-135f335f4c45\"},{\"id\":null,\"quId\":null,\"optionTitle\":\"选项2\",\"optionName\":\"选项2\",\"orderById\":null,\"visibility\":1,\"anOrderSum\":0,\"optionTitleObj\":{\"dwHtml\":\"选项2\",\"dwText\":\"选项2\",\"dwPlaceholder\":\"请输入选项内容\",\"isRefreshValue\":false},\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":null,\"dwId\":\"5e647de8-7bc5-4feb-a2c7-fc0f2d8a4609\"},{\"id\":null,\"optionTitleObj\":{\"dwText\":\"选项内容3\",\"dwHtml\":\"选项内容3\",\"isRefreshValue\":false},\"itemClick\":false,\"dwId\":\"bc938593-a775-45b6-8918-295315e1af28\",\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":null},{\"id\":null,\"optionTitleObj\":{\"dwText\":\"选项内容4\",\"dwHtml\":\"选项内容4\",\"isRefreshValue\":false},\"itemClick\":false,\"dwId\":\"c8fda87e-e32f-4214-b58e-7346284cf054\",\"dateAttrs\":[],\"checked\":false,\"orderIndex\":0,\"inputAttr\":{\"commonAttr\":{\"checkType\":null,\"placeholder\":\"\",\"defaultValue\":\"\",\"inputRow\":1,\"minlength\":0,\"maxlength\":119,\"isRequired\":1},\"dateTimeAttr\":{\"timeRange\":{\"range\":null,\"step\":null},\"dateFormat\":null,\"attrs\":[]},\"numAttr\":{\"min\":null,\"max\":null}},\"showOptionNote\":0,\"isRequired\":1,\"otherText\":null,\"validateObj\":{\"errorText\":\"\",\"isOk\":true},\"scoreNum\":null}],\"rowContent\":\"\",\"colContent\":\"\",\"optionContent\":\"\",\"removeOptionUuIds\":null,\"anAnswer\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"answer\":null,\"visibility\":1},\"anCheckboxs\":[],\"anDFillblanks\":[],\"anEnumqus\":[],\"anFillblank\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"answer\":null,\"visibility\":1},\"anRadio\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"quItemId\":null,\"otherText\":null,\"visibility\":1},\"anYesno\":{\"id\":null,\"belongId\":null,\"belongAnswerId\":null,\"quId\":null,\"yesnoAnswer\":null,\"visibility\":1},\"anScores\":[],\"anCount\":0,\"anOrders\":[],\"anUplodFiles\":[],\"questionLogics\":[],\"statJson\":\"\",\"dwsurveyfont\":null,\"eventName\":null,\"dwQuIcon\":\"<i class=\\\"fa-solid fa-arrow-down-1-9\\\"></i>\",\"quOptions\":null,\"quRows\":null,\"quCols\":null,\"showQu\":true,\"logicIsHide\":false,\"quTitleObj\":{\"dwHtml\":\"排序题标题\",\"dwText\":\"排序题\",\"dwPlaceholder\":\"请输入题目标题\",\"isNew\":false,\"isRefreshValue\":false},\"quNoteObj\":{\"dwHtml\":null,\"dwText\":null,\"dwPlaceholder\":\"请输入题目备注\",\"isNew\":false,\"isRefreshValue\":false},\"quAttr\":{\"isRequired\":true,\"scoreAttr\":{\"maxScore\":0,\"designShowScoreNum\":false},\"showQuNote\":false,\"orderQuAttr\":{\"topNum\":0}},\"quTypeName\":\"排序题\",\"validateObj\":{\"errorText\":\"\",\"isOk\":true,\"isAnswerOk\":false},\"itemClick\":false,\"quFocusObj\":{\"quFocus\":false,\"quSetShow\":false,\"quLogicShow\":false,\"quMoreOptionShow\":false,\"quMoreOptionShowEdit\":false,\"quScorePopoverShow\":false,\"quScaleTextPopoverShow\":false,\"quMoreOptionColShow\":false},\"tempAttr\":{\"cascadeJsonOptions\":null},\"dwId\":\"7405cea7-8fc1-4177-9486-f5f797a36049\",\"isNew\":false,\"pageIndex\":1}],\"surveyAnswer\":null,\"surveyJson\":null,\"groupName1\":null,\"groupName2\":null,\"surveyAnswerUrl\":null,\"surveyNameObj\":{\"dwText\":\"Hello DWSurvey\",\"dwHtml\":\"Hello DWSurvey\"},\"surveyAttrs\":{\"anBroAttr\":{\"enabled\":false,\"anNum\":1},\"anIpAttr\":{\"enabled\":false,\"anNum\":1,\"onlyIps\":false},\"anRefreshAttr\":{\"randomCode\":true},\"anPwdAttr\":{\"enabled\":false,\"anPwdCode\":null,\"anNum\":0},\"anEndNumAttr\":{\"enabled\":false,\"endNum\":1},\"anEndTimeAttr\":{\"enabled\":false,\"endTime\":null},\"anStartTimeAttr\":{\"enabled\":false,\"startTime\":null},\"scoreAttr\":{\"enabled\":false,\"maxScore\":0,\"showSumScore\":{\"enabled\":true,\"showContent\":\"sumAndDetail\"}},\"contactAttr\":{\"enabled\":false,\"authType\":null,\"whoAn\":null,\"anNum\":1},\"endPageAttr\":{\"redirectionAttr\":{\"enabled\":false,\"redirectionType\":null,\"reqUrl\":null,\"urlText\":null},\"textAttr\":{\"submitSuccess\":{\"enabled\":false,\"text\":\"\"}}},\"opoqAttr\":{\"enabled\":false}},\"surveyTest\":\"\",\"curEditObj\":[{\"itemClick\":false}],\"tempDataType\":\"none\",\"dwId\":\"04adeda3-e97d-4159-9c36-5d92c07fa86f\",\"showSurvey\":true,\"surveyStyle\":{\"pageTopImg\":{\"enabled\":true,\"src\":\"/file/images/ddmfd256/gx429jht.jpg\",\"httpSrc\":\"/file/images/ddmfd256/gx429jht.jpg\"},\"pageBgImg\":{\"enabled\":false,\"src\":null,\"httpSrc\":null},\"logoImg\":{\"enabled\":false,\"src\":null,\"httpSrc\":null,\"position\":\"topLogoRight\"},\"pageBgColor\":\"#eeeeee\",\"pageThemeColor\":\"#064b91\",\"pageThemeColor1\":\"#033e75\",\"logoBgColor\":\"#0b72dc\",\"progressColor\":\"#0b63bd\",\"showQuNum\":true,\"showProgressbar\":true,\"showPageHeader\":true,\"showQuTypeName\":true,\"showSurveyTitle\":true,\"showSurveyNote\":true,\"showQuScoreNum\":false},\"clientBrowser\":{\"windowWidth\":0,\"matrixWidth\":0},\"designLayout\":\"TB\",\"watchEvent\":\"6c0cd00d-215a-49a8-8609-190491b4ffd0\",\"watchEventScrollToId\":\"oooww\",\"scrollToQuIndex\":null,\"surveyTypeSimpleName\":\"问卷\",\"surveyFocusObj\":{\"rightFocusTab\":\"surveySet\",\"focusQuIndex\":null},\"dwDebug\":false,\"answerMsg\":{\"showAnswerMsg\":false,\"answerMsgInfo\":null,\"noSurveyJson\":false},\"pageAttr\":{\"pageSize\":1,\"curPage\":1,\"begin\":0,\"end\":2},\"answerProgress\":{\"totalAnQu\":0,\"completeAnQu\":0,\"percentage\":0},\"surveyLogicControl\":{\"hideQus\":[]},\"dwVersion\":1737460637080}',NULL,'{\"id\":\"b0051f52-c63f-410e-910e-f63681b78686\",\"sid\":\"coy7scq\",\"parentId\":\"\",\"surveyName\":\"Test\",\"surveyNameText\":\"Test\",\"userId\":\"1\",\"dirType\":2,\"surveyDetailId\":null,\"createDate\":\"2024-10-30 23:57:10\",\"surveyState\":0,\"surveyQuNum\":0,\"anItemLeastNum\":0,\"answerNum\":null,\"visibility\":1,\"surveyModel\":1,\"viewAnswer\":0,\"isShare\":1,\"excerptNum\":0,\"surveyTag\":1,\"htmlPath\":null,\"jsonPath\":null,\"surveyType\":\"survey\",\"refId\":null,\"groupId1\":null,\"groupId2\":null,\"groupName\":null,\"surveyDetail\":{\"id\":\"c61d95cd-3582-45a1-835d-01e949f1a536\",\"dirId\":\"b0051f52-c63f-410e-910e-f63681b78686\",\"effective\":1,\"effectiveTime\":5,\"effectiveIp\":0,\"refresh\":1,\"refreshNum\":3,\"rule\":1,\"ruleCode\":\"令牌\",\"endType\":1,\"endTime\":null,\"endNum\":1000,\"surveyNote\":\"非常感谢您的参与！如有涉及个人信息，我们将严格保密。\",\"surveyNoteText\":null,\"ynEndNum\":0,\"ynEndTime\":0,\"surveyQuNum\":0,\"anItemLeastNum\":0,\"anItemMostNum\":0,\"mailOnly\":0,\"showShareSurvey\":1,\"showAnswerDa\":0,\"surveyNodeObj\":{\"dwHtml\":\"非常感谢您的参与！如有涉及个人信息，我们将严格保密。\",\"dwText\":\"\",\"dwPlaceholder\":\"请输入问卷介绍\"},\"effective_model\":true,\"effectiveIp_model\":false,\"refresh_model\":true,\"rule_model\":false,\"ynEndNum_model\":false,\"endNum_model\":1000,\"ynEndTime_model\":false,\"dwId\":\"96b63244-b583-4ec3-bcd1-be49c123b85b\"},\"userName\":\"admin\",\"questions\":[],\"surveyAnswer\":null,\"surveyJson\":null,\"groupName1\":null,\"groupName2\":null,\"surveyAnswerUrl\":null,\"surveyNameObj\":{\"dwText\":\"Hello DWSurvey\",\"dwHtml\":\"Hello DWSurvey\"},\"surveyAttrs\":{\"anBroAttr\":{\"enabled\":false,\"anNum\":1},\"anIpAttr\":{\"enabled\":false,\"anNum\":1,\"onlyIps\":false},\"anRefreshAttr\":{\"randomCode\":true},\"anPwdAttr\":{\"enabled\":false,\"anPwdCode\":null,\"anNum\":0},\"anEndNumAttr\":{\"enabled\":false,\"endNum\":1},\"anEndTimeAttr\":{\"enabled\":false,\"endTime\":null},\"anStartTimeAttr\":{\"enabled\":false,\"startTime\":null},\"scoreAttr\":{\"enabled\":false,\"maxScore\":0,\"showSumScore\":{\"enabled\":true,\"showContent\":\"sumAndDetail\"}},\"contactAttr\":{\"enabled\":false,\"authType\":null,\"whoAn\":null,\"anNum\":1},\"endPageAttr\":{\"redirectionAttr\":{\"enabled\":false,\"redirectionType\":null,\"reqUrl\":null,\"urlText\":null},\"textAttr\":{\"submitSuccess\":{\"enabled\":false,\"text\":\"\"}}},\"opoqAttr\":{\"enabled\":false}},\"surveyTest\":\"\",\"curEditObj\":[{\"itemClick\":false}],\"tempDataType\":\"none\",\"dwId\":\"04adeda3-e97d-4159-9c36-5d92c07fa86f\",\"showSurvey\":true,\"surveyStyle\":{\"pageTopImg\":{\"enabled\":true,\"src\":\"/file/images/ddmfd256/gx429jht.jpg\",\"httpSrc\":\"/file/images/ddmfd256/gx429jht.jpg\"},\"pageBgImg\":{\"enabled\":false,\"src\":null,\"httpSrc\":null},\"logoImg\":{\"enabled\":false,\"src\":null,\"httpSrc\":null,\"position\":\"topLogoRight\"},\"pageBgColor\":\"#eeeeee\",\"pageThemeColor\":\"#064b91\",\"pageThemeColor1\":\"#033e75\",\"logoBgColor\":\"#0b72dc\",\"progressColor\":\"#0b63bd\",\"showQuNum\":true,\"showProgressbar\":true,\"showPageHeader\":true,\"showQuTypeName\":true,\"showSurveyTitle\":true,\"showSurveyNote\":true,\"showQuScoreNum\":false},\"clientBrowser\":{\"windowWidth\":0,\"matrixWidth\":0},\"designLayout\":\"TB\",\"watchEvent\":\"6c0cd00d-215a-49a8-8609-190491b4ffd0\",\"watchEventScrollToId\":\"oooww\",\"scrollToQuIndex\":null,\"surveyTypeSimpleName\":\"问卷\",\"surveyFocusObj\":{\"rightFocusTab\":\"surveySet\",\"focusQuIndex\":null},\"dwDebug\":false,\"answerMsg\":{\"showAnswerMsg\":false,\"answerMsgInfo\":null,\"noSurveyJson\":false},\"pageAttr\":{\"pageSize\":1,\"curPage\":1,\"begin\":0,\"end\":2},\"answerProgress\":{\"totalAnQu\":0,\"completeAnQu\":0,\"percentage\":0},\"surveyLogicControl\":{\"hideQus\":[]},\"dwVersion\":1737460637080}','coy7scq');
/*!40000 ALTER TABLE `t_survey_json` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_mail_invite`
--

DROP TABLE IF EXISTS `t_survey_mail_invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_mail_invite` (
  `id` varchar(55) NOT NULL,
  `audit` int DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `dw_send_user_mail` varchar(255) DEFAULT NULL,
  `dw_send_user_name` varchar(255) DEFAULT NULL,
  `dw_survey_link` varchar(255) DEFAULT NULL,
  `dw_survey_name` varchar(255) DEFAULT NULL,
  `error_msg` varchar(255) DEFAULT NULL,
  `fail_num` int DEFAULT NULL,
  `inbox_num` int DEFAULT NULL,
  `send_num` int DEFAULT NULL,
  `sendcloud_msg_id` varchar(55) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `success_num` int DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_mail_invite`
--

LOCK TABLES `t_survey_mail_invite` WRITE;
/*!40000 ALTER TABLE `t_survey_mail_invite` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_mail_invite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_password`
--

DROP TABLE IF EXISTS `t_survey_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_password` (
  `id` varchar(55) NOT NULL,
  `an_num` int DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `order_by_num` int DEFAULT NULL,
  `password_code` varchar(255) DEFAULT NULL,
  `pwd_code_phone` varchar(255) DEFAULT NULL,
  `pwd_code_tag` varchar(255) DEFAULT NULL,
  `pwd_code_type` int DEFAULT NULL,
  `pwd_code_user_name` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `survey_id` varchar(255) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_password`
--

LOCK TABLES `t_survey_password` WRITE;
/*!40000 ALTER TABLE `t_survey_password` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_rel_dept_user`
--

DROP TABLE IF EXISTS `t_survey_rel_dept_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_rel_dept_user` (
  `id` varchar(55) NOT NULL,
  `answer_id` varchar(55) DEFAULT NULL,
  `answer_time` datetime(6) DEFAULT NULL,
  `belong_id` varchar(55) DEFAULT NULL,
  `click_count` int DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `dept_id` varchar(55) DEFAULT NULL,
  `rel_type` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_rel_dept_user`
--

LOCK TABLES `t_survey_rel_dept_user` WRITE;
/*!40000 ALTER TABLE `t_survey_rel_dept_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_rel_dept_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_req_url`
--

DROP TABLE IF EXISTS `t_survey_req_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_req_url` (
  `id` varchar(55) NOT NULL,
  `sid` varchar(55) DEFAULT NULL,
  `state` int DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `req_url_type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_stats` (
  `id` varchar(55) NOT NULL,
  `an_avg_time` int DEFAULT NULL,
  `an_min_time` int DEFAULT NULL,
  `answer_num` int DEFAULT NULL,
  `complete_num` int DEFAULT NULL,
  `effective_num` int DEFAULT NULL,
  `first_answer` datetime DEFAULT NULL,
  `handle_pass_num` int DEFAULT NULL,
  `handle_un_pass_num` int DEFAULT NULL,
  `import_num` int DEFAULT NULL,
  `input_num` int DEFAULT NULL,
  `is_new_data` int DEFAULT NULL,
  `last_answer` datetime DEFAULT NULL,
  `mobile_num` int DEFAULT NULL,
  `online_num` int DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `un_complete_num` int DEFAULT NULL,
  `un_effective_num` int DEFAULT NULL,
  `un_handle_num` int DEFAULT NULL,
  `an_rate` float DEFAULT NULL,
  `browser_num` int DEFAULT NULL,
  `begin_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `qu_col_id` varchar(255) DEFAULT NULL,
  `qu_id` varchar(255) DEFAULT NULL,
  `qu_item_id` varchar(255) DEFAULT NULL,
  `qu_row_id` varchar(255) DEFAULT NULL,
  `sum_count` varchar(255) DEFAULT NULL,
  `sum_score` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_stats`
--

LOCK TABLES `t_survey_stats` WRITE;
/*!40000 ALTER TABLE `t_survey_stats` DISABLE KEYS */;
INSERT INTO `t_survey_stats` VALUES ('9fb5799a-b0fa-4d7d-8b97-b8395a883204',0,0,4,4,0,'2024-10-28 02:36:18',0,0,0,0,1,'2024-10-28 06:44:21',0,4,'b688d0e0-f42e-4247-9cb0-ca4cfcab92dc',0,4,0,80,5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('b4fb52ec-5b59-4cc8-8265-a167264d6933',0,0,0,0,0,NULL,0,0,0,0,1,NULL,0,0,'0dd7e9c2-0dfe-4d3c-aed9-356293656b78',0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('cc778fca-b9ff-4b14-822d-e04f6288f30a',0,0,2,2,0,'2024-10-30 15:57:35',0,0,0,0,1,'2024-10-30 15:57:58',0,2,'b0051f52-c63f-410e-910e-f63681b78686',0,2,0,200,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `t_survey_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_style`
--

DROP TABLE IF EXISTS `t_survey_style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_style` (
  `id` varchar(55) NOT NULL,
  `body_bg_color` varchar(255) DEFAULT NULL,
  `body_bg_image` varchar(255) DEFAULT NULL,
  `show_survey_haed` int DEFAULT NULL,
  `survey_bg_color` varchar(255) DEFAULT NULL,
  `survey_bg_image` varchar(255) DEFAULT NULL,
  `survey_content_bg_colo_topr` varchar(255) DEFAULT NULL,
  `survey_content_bg_color_bottom` varchar(255) DEFAULT NULL,
  `survey_content_bg_color_middle` varchar(255) DEFAULT NULL,
  `survey_content_bg_image_bottom` varchar(255) DEFAULT NULL,
  `survey_content_bg_image_middle` varchar(255) DEFAULT NULL,
  `survey_content_bg_image_top` varchar(255) DEFAULT NULL,
  `survey_content_padding_bottom` int DEFAULT NULL,
  `survey_content_padding_left` int DEFAULT NULL,
  `survey_content_padding_right` int DEFAULT NULL,
  `survey_content_padding_top` int DEFAULT NULL,
  `survey_content_width` int DEFAULT NULL,
  `survey_head_bg_color` varchar(255) DEFAULT NULL,
  `survey_head_bg_image` varchar(255) DEFAULT NULL,
  `survey_head_height` int DEFAULT NULL,
  `survey_head_padding_bottom` int DEFAULT NULL,
  `survey_head_padding_left` int DEFAULT NULL,
  `survey_head_padding_right` int DEFAULT NULL,
  `survey_head_padding_top` int DEFAULT NULL,
  `survey_head_width` int DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `survey_padding_bottom` varchar(255) DEFAULT NULL,
  `survey_padding_left` varchar(255) DEFAULT NULL,
  `survey_padding_right` varchar(255) DEFAULT NULL,
  `survey_padding_top` varchar(255) DEFAULT NULL,
  `survey_style_type` int DEFAULT NULL,
  `survey_width` int DEFAULT NULL,
  `show_body_bi` int DEFAULT NULL,
  `show_survey_bi` int DEFAULT NULL,
  `show_survey_cbim` int DEFAULT NULL,
  `show_survey_hbgi` int DEFAULT NULL,
  `survey_content_bg_color_top` varchar(255) DEFAULT NULL,
  `show_survey_logo` int DEFAULT NULL,
  `survey_logo_image` varchar(255) DEFAULT NULL,
  `question_option_text_color` varchar(255) DEFAULT NULL,
  `question_title_text_color` varchar(255) DEFAULT NULL,
  `survey_note_text_color` varchar(255) DEFAULT NULL,
  `survey_title_text_color` varchar(255) DEFAULT NULL,
  `survey_btn_bg_color` varchar(255) DEFAULT NULL,
  `show_sur_note` int DEFAULT NULL,
  `show_sur_title` int DEFAULT NULL,
  `show_progressbar` int DEFAULT NULL,
  `show_ti_num` int DEFAULT NULL,
  `show_survey_hti` int DEFAULT NULL,
  `survey_header_top_image` varchar(255) DEFAULT NULL,
  `err_msg_fc` varchar(255) DEFAULT NULL,
  `from_item_bg_color` varchar(255) DEFAULT NULL,
  `from_item_border_color` varchar(255) DEFAULT NULL,
  `from_item_hover_bg_color` varchar(255) DEFAULT NULL,
  `from_item_hover_border_color` varchar(255) DEFAULT NULL,
  `from_item_hover_text_color` varchar(255) DEFAULT NULL,
  `from_item_text_color` varchar(255) DEFAULT NULL,
  `survey_btn_color` varchar(255) DEFAULT NULL,
  `survey_btn_hover_bg_color` varchar(255) DEFAULT NULL,
  `survey_btn_hover_color` varchar(255) DEFAULT NULL,
  `an_login_bg_img` varchar(255) DEFAULT NULL,
  `an_login_title` varchar(255) DEFAULT NULL,
  `an_login_title_note` varchar(255) DEFAULT NULL,
  `show_qu_type_name` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_style`
--

LOCK TABLES `t_survey_style` WRITE;
/*!40000 ALTER TABLE `t_survey_style` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_survey_template`
--

DROP TABLE IF EXISTS `t_survey_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_survey_template` (
  `id` varchar(55) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `create_user_id` varchar(55) DEFAULT NULL,
  `group_id1` varchar(55) DEFAULT NULL,
  `group_id2` varchar(55) DEFAULT NULL,
  `ref_num` int DEFAULT NULL,
  `survey_id` varchar(55) DEFAULT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  `template_status` int DEFAULT NULL,
  `cover_path` varchar(255) DEFAULT NULL,
  `template_note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_survey_template`
--

LOCK TABLES `t_survey_template` WRITE;
/*!40000 ALTER TABLE `t_survey_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_survey_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_db_backup`
--

DROP TABLE IF EXISTS `t_sys_db_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_db_backup` (
  `id` varchar(55) NOT NULL,
  `backup_name` varchar(255) DEFAULT NULL,
  `backup_path` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_email` (
  `id` varchar(55) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `is_check` int DEFAULT NULL,
  `post` varchar(255) DEFAULT NULL,
  `send_email` varchar(255) DEFAULT NULL,
  `state` int DEFAULT NULL,
  `stmp_pwd` varchar(255) DEFAULT NULL,
  `stmp_server` varchar(255) DEFAULT NULL,
  `stmp_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_email`
--

LOCK TABLES `t_sys_email` WRITE;
/*!40000 ALTER TABLE `t_sys_email` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_log`
--

DROP TABLE IF EXISTS `t_sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_log` (
  `id` varchar(55) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `log_name` varchar(255) DEFAULT NULL,
  `log_note` varchar(255) DEFAULT NULL,
  `log_type` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_log`
--

LOCK TABLES `t_sys_log` WRITE;
/*!40000 ALTER TABLE `t_sys_log` DISABLE KEYS */;
INSERT INTO `t_sys_log` VALUES ('02877938-f72d-45e3-9bb9-c965210646eb','2024-10-29 22:34:26.827000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('09dcbebd-ca8e-4d40-99e6-cd0cacf8cd0a','2024-10-29 22:17:36.886000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('0ecf0482-aa06-4930-93ac-cd5adbe15a8f','2024-10-28 10:54:12.424000','192.168.1.73','登录失败','B35DA34CFF2E6B2FE0E8EB1500A24F22','LOGIN-WX',2,NULL),('23854022-dfad-49b3-89e7-880f25230367','2024-10-28 14:42:07.506000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('26aaa058-fc30-438e-9d35-52a20076f23d','2024-10-22 15:20:45.172000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('2c8d8bfb-b3d9-40d0-8eb2-e02b43f75fca','2024-10-30 14:41:35.915000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('2d0bc817-bf47-4f37-a576-1f67c30e8252','2024-10-30 14:42:46.852000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('3908fcdd-959a-45a1-bcaa-643a046079f2','2024-10-30 23:37:47.087000','192.168.1.73','删除问卷','[Ljava.lang.String;@7a144ca7','DELETE-SURVEY',1,'1'),('457f76ed-5198-42b0-89c0-67bc9f5faf74','2024-10-30 23:37:42.878000','192.168.1.73','删除问卷','[Ljava.lang.String;@26c6ff66','DELETE-SURVEY',1,'1'),('5cdb4b89-9948-4bd0-a047-aeb512bc208a','2024-10-30 17:43:50.432000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('5f6e4068-7ddf-4605-baa7-40f81f3befe0','2024-10-21 22:59:07.607000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('6bf2fa01-89ef-43c0-ba4b-ddf07e747f48','2024-10-30 14:58:21.902000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('6ca144c7-8071-4c67-ac70-318d98d5670a','2024-10-30 23:57:03.675000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('6d005e1b-f64c-4a16-b74b-40e78fdd6c8d','2024-10-27 20:56:39.441000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('6d3a37be-3ac8-4f85-9489-d9516483b4d6','2024-10-22 09:51:16.867000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('714f9835-6d91-426a-93b2-342360872c42','2024-10-30 23:11:00.823000','192.168.1.73','创建问卷','c41c6ca8-05aa-4b2b-ba83-08f7c8599808','CREATE-SURVEY',1,'1'),('7220d6a2-ad21-475e-99ff-0239e7c4df91','2024-10-30 23:57:02.309000','192.168.1.73','退出登录','dwsurvey','LOGOUT',1,'1'),('73f7bd95-5300-44cb-b6e5-d7abbcf37a11','2024-10-28 09:53:25.607000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('764e471a-070c-4f47-b05f-769027030030','2024-10-22 15:07:36.253000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('77fbd4ae-f3c8-4328-a406-c7be9c24673e','2024-10-30 23:57:09.632000','192.168.1.73','创建问卷','b0051f52-c63f-410e-910e-f63681b78686','CREATE-SURVEY',1,'1'),('78049877-edb3-4594-8352-04f11854f822','2024-10-30 23:37:49.281000','192.168.1.73','删除问卷','[Ljava.lang.String;@66c1b47c','DELETE-SURVEY',1,'1'),('7a9517bf-384f-4747-9217-de38438da78c','2024-10-30 22:21:28.783000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('7ef20151-2fa1-4616-b563-ddf7a980248c','2024-10-30 23:37:16.111000','192.168.1.73','创建问卷','15f21ee8-5c78-482c-b48f-358c2b499c36','CREATE-SURVEY',1,'1'),('7fc07cf0-4124-4df3-ad43-7e3241c478c1','2024-10-22 09:50:29.323000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('819d0138-2bd4-49d9-9998-cd4d3e54f57d','2024-10-30 23:44:33.488000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('9dbf4a4b-0fe0-4840-b708-44cc136be6fd','2024-10-30 23:37:51.269000','192.168.1.73','删除问卷','[Ljava.lang.String;@4fbe3f13','DELETE-SURVEY',1,'1'),('a4e6db63-b9da-4c8f-b8b1-4505f9f7e930','2024-10-29 22:22:39.856000','192.168.1.73','创建问卷','0dd7e9c2-0dfe-4d3c-aed9-356293656b78','CREATE-SURVEY',1,'1'),('ab107333-87b9-414c-8289-b0e24141e7ef','2024-10-28 10:12:47.212000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('aeae1af9-e603-4212-9627-15a26ca72e5d','2024-10-28 10:36:43.972000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('b1e6512b-b034-4f8e-85ea-cf5073b7ce36','2024-10-30 23:37:12.201000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('b4624ecf-ac12-43bf-8500-cd4d115d2dc7','2024-10-29 22:22:29.311000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('b6e39127-ebaf-47c4-8ebf-7226f227dacc','2024-10-30 22:40:09.476000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('bbb78f43-0412-4928-bb0d-36377170ace3','2024-10-30 22:23:40.049000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('c579aadb-e3eb-42e6-8761-fcd566aac1f5','2024-10-29 22:33:02.911000','192.168.1.73','退出登录','dwsurvey','LOGOUT',1,'1'),('da8383cd-dc68-40dd-9459-33c390e96d7f','2024-10-30 23:37:44.841000','192.168.1.73','删除问卷','[Ljava.lang.String;@586a20c0','DELETE-SURVEY',1,'1'),('dbcc9233-5834-4097-b93b-e0f55634a117','2024-10-21 22:58:55.481000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('edc0ffdc-c2f0-4b3c-b30c-0573d57c2e65','2025-01-21 11:50:50.209000','172.18.0.1','登录失败','service@diaowen.net','LOGIN-PWD',2,NULL),('ef34f988-f978-47f0-b675-067f0eae5db5','2024-10-30 23:10:55.580000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('f248a63e-f042-4802-8097-74ca6f83a9a7','2024-10-30 23:44:53.537000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('f4e0c7cd-8338-4075-9819-ceac31d4e4b8','2024-10-21 22:59:05.969000','192.168.1.73','退出登录','dwsurvey','LOGOUT',1,'1'),('f697f6dc-d4a1-4979-89fa-7df6ec30262f','2024-10-30 22:45:59.350000','192.168.1.73','登录成功','dwsurvey','LOGIN-WX',1,'1'),('f971edc7-2ec7-40a3-b865-0fbe089ccdbf','2024-10-30 14:42:43.365000','192.168.1.73','退出登录','dwsurvey','LOGOUT',1,'1'),('fc75dd4a-1bd5-4386-bf9d-82a7b4c2cd0b','2024-10-30 22:53:28.220000','192.168.1.73','退出登录','dwsurvey','LOGOUT',1,'1');
/*!40000 ALTER TABLE `t_sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_property`
--

DROP TABLE IF EXISTS `t_sys_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_property` (
  `id` varchar(55) NOT NULL,
  `footer_content` varchar(255) DEFAULT NULL,
  `footer_hide` bit(1) NOT NULL,
  `logo_image_url` varchar(255) DEFAULT NULL,
  `max_up_level` varchar(255) DEFAULT NULL,
  `new_version_info` varchar(255) DEFAULT NULL,
  `site_admin_name` varchar(255) DEFAULT NULL,
  `site_icp` varchar(255) DEFAULT NULL,
  `site_mail` varchar(255) DEFAULT NULL,
  `site_name` varchar(255) DEFAULT NULL,
  `site_phone` varchar(255) DEFAULT NULL,
  `site_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_property`
--

LOCK TABLES `t_sys_property` WRITE;
/*!40000 ALTER TABLE `t_sys_property` DISABLE KEYS */;
INSERT INTO `t_sys_property` VALUES ('4260e617-5b2c-4dda-b1e2-8fa5653af7a0','Powered by DWSurvey',_binary '\0','','level1','','','','','调问网','','https://www.diaowen.net');
/*!40000 ALTER TABLE `t_sys_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` varchar(55) NOT NULL,
  `activation_code` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `cellphone` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `edu_quali` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `find_pwd_code` varchar(255) DEFAULT NULL,
  `find_pwd_last_date` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_name` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `sha_password` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `version` int DEFAULT NULL,
  `visibility` int DEFAULT NULL,
  `wwwooo` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `sha_password_temp` varchar(255) DEFAULT NULL,
  `wx_open_id` varchar(255) DEFAULT NULL,
  `servlet_session_id` varchar(255) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `third_user_id` varchar(255) DEFAULT NULL,
  `login_count` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES ('1',NULL,'2013-03-21 21:15:21','',NULL,'2013-03-21 21:15:21',1,'service@diaowen.net',NULL,NULL,'2025-01-21 11:50:52','dwsurvey','admin',NULL,1,'7c4a8d09ca3762af61e59520943dc26494f8941b',2,1,NULL,NULL,'http://localhost:8080//file/images/wi8i2ndjj/s5e7rfdnx.jpeg',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_role`
--

DROP TABLE IF EXISTS `t_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_role` (
  `id` varchar(55) NOT NULL,
  `role_id` varchar(55) DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role`
--

LOCK TABLES `t_user_role` WRITE;
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` VALUES ('7861866f-cec1-436b-9368-9b1d136cbcf6','402881ed707c9fbd01707ca0f6800002','1'),('8290f6f8-28bb-420a-ae9a-4bb33b32cc1b','402881ed707c9fbd01707ca04ab10000','1');
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_wx_userinfo`
--

DROP TABLE IF EXISTS `t_wx_userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_wx_userinfo` (
  `id` varchar(55) NOT NULL,
  `access_token_code` varchar(255) DEFAULT NULL,
  `access_token` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `expires_in` varchar(255) DEFAULT NULL,
  `headimgurl` varchar(255) DEFAULT NULL,
  `mp_account_id` varchar(55) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `privilege` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `scope` int DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `token_date` datetime(6) DEFAULT NULL,
  `unionid` varchar(255) DEFAULT NULL,
  `user_id` varchar(55) DEFAULT NULL,
  `userinfo_status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_wx_userinfo`
--

LOCK TABLES `t_wx_userinfo` WRITE;
/*!40000 ALTER TABLE `t_wx_userinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_wx_userinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tracker`
--

DROP TABLE IF EXISTS `tracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tracker` (
  `id` varchar(55) NOT NULL,
  `data_id` varchar(55) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `optime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
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

-- Dump completed on 2025-01-21 12:01:45
