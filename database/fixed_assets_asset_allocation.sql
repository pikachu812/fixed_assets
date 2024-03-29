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
-- Table structure for table `asset_allocation`
--

DROP TABLE IF EXISTS `asset_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_allocation` (
  `allocation_id` int NOT NULL AUTO_INCREMENT,
  `asset_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `allocation_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `allocation_description` varchar(200) DEFAULT NULL,
  `status` varchar(50) DEFAULT '待审核',
  `reason` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`allocation_id`),
  KEY `asset_id` (`asset_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `asset_allocation_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `fixed_asset` (`asset_id`),
  CONSTRAINT `asset_allocation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_allocation`
--

LOCK TABLES `asset_allocation` WRITE;
/*!40000 ALTER TABLE `asset_allocation` DISABLE KEYS */;
INSERT INTO `asset_allocation` VALUES (13,10,19,'研发部','2024-03-10','2024-03-19','日常办公打印文件使用','已归还',NULL),(14,14,19,'研发部','2024-03-11','2024-03-13','开会用','已归还',NULL),(15,27,22,'后勤保障部','2024-03-12',NULL,'接送重要贵宾使用','审核通过',NULL),(16,28,22,'后勤保障部','2024-03-12',NULL,'接送重要贵宾使用','审核通过',NULL),(17,11,13,'市场营销部','2024-03-12',NULL,'数据分析用','审核通过',NULL),(18,20,18,'人事部','2024-03-12','2024-03-14','日常办公用','已归还',NULL),(19,21,18,'人事部','2024-03-12','2024-03-14','日常办公用','已归还',NULL),(20,15,19,'研发部','2024-03-14','2024-03-19','日常办公使用','已归还',NULL),(21,17,18,'人事部','2024-03-14','2024-03-14','日常办公使用','已归还',NULL),(22,22,19,'研发部','2024-03-14','2024-03-19','日常办公使用','已归还',NULL),(28,35,19,'研发部','2024-03-19',NULL,'接待贵客','待审核',NULL),(29,20,19,'研发部','2024-03-19',NULL,'日常办公使用','待审核',NULL);
/*!40000 ALTER TABLE `asset_allocation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-19 12:23:42
