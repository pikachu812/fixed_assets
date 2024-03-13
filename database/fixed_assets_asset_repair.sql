-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fixed_assets
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asset_repair`
--

DROP TABLE IF EXISTS `asset_repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_repair` (
  `repair_id` int NOT NULL AUTO_INCREMENT,
  `asset_id` int DEFAULT NULL,
  `repair_date` date DEFAULT NULL,
  `status` varchar(20) DEFAULT '待维修' COMMENT '当前维修记录状态',
  `cost` decimal(10,2) DEFAULT NULL,
  `details` text,
  PRIMARY KEY (`repair_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `asset_repair_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `fixed_asset` (`asset_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_repair`
--

LOCK TABLES `asset_repair` WRITE;
/*!40000 ALTER TABLE `asset_repair` DISABLE KEYS */;
INSERT INTO `asset_repair` VALUES (6,10,'2024-03-10','已维修',0.60,'卡纸导致无法使用'),(7,14,'2024-03-13','已维修',NULL,'不慎从会议桌摔落地'),(8,10,'2024-03-05','已维修',NULL,'bgjilhk'),(9,10,'2024-03-13','待维修',NULL,'不慎掉落地面摔坏镜头');
/*!40000 ALTER TABLE `asset_repair` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-13 23:19:11
