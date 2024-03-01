-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: fixed_assets
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `asset_allocation`
--

DROP TABLE IF EXISTS `asset_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_allocation` (
  `allocation_id` int NOT NULL AUTO_INCREMENT,
  `asset_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `allocation_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  PRIMARY KEY (`allocation_id`),
  KEY `asset_id` (`asset_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `asset_allocation_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `fixed_asset` (`asset_id`),
  CONSTRAINT `asset_allocation_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_allocation`
--

LOCK TABLES `asset_allocation` WRITE;
/*!40000 ALTER TABLE `asset_allocation` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_allocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_depreciation`
--

DROP TABLE IF EXISTS `asset_depreciation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_depreciation` (
  `depreciation_id` int NOT NULL AUTO_INCREMENT,
  `asset_id` int DEFAULT NULL,
  `depreciation_date` date DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`depreciation_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `asset_depreciation_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `fixed_asset` (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_depreciation`
--

LOCK TABLES `asset_depreciation` WRITE;
/*!40000 ALTER TABLE `asset_depreciation` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_depreciation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_inventory`
--

DROP TABLE IF EXISTS `asset_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_inventory` (
  `inventory_id` int NOT NULL AUTO_INCREMENT,
  `asset_id` int DEFAULT NULL,
  `inventory_date` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`inventory_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `asset_inventory_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `fixed_asset` (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_inventory`
--

LOCK TABLES `asset_inventory` WRITE;
/*!40000 ALTER TABLE `asset_inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_inventory` ENABLE KEYS */;
UNLOCK TABLES;

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
  `cost` decimal(10,2) DEFAULT NULL,
  `details` text,
  PRIMARY KEY (`repair_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `asset_repair_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `fixed_asset` (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_repair`
--

LOCK TABLES `asset_repair` WRITE;
/*!40000 ALTER TABLE `asset_repair` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_repair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_scrap`
--

DROP TABLE IF EXISTS `asset_scrap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_scrap` (
  `scrap_id` int NOT NULL AUTO_INCREMENT,
  `asset_id` int DEFAULT NULL,
  `scrap_date` date DEFAULT NULL,
  `reason` text,
  PRIMARY KEY (`scrap_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `asset_scrap_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `fixed_asset` (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_scrap`
--

LOCK TABLES `asset_scrap` WRITE;
/*!40000 ALTER TABLE `asset_scrap` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_scrap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_type`
--

DROP TABLE IF EXISTS `asset_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_type` (
  `asset_type_id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`asset_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_type`
--

LOCK TABLES `asset_type` WRITE;
/*!40000 ALTER TABLE `asset_type` DISABLE KEYS */;
INSERT INTO `asset_type` VALUES (1,'Updated Name','Description of Vehicle'),(2,'Vehicle','Description of Vehicle'),(3,'Vehicle','Description of Vehicle');
/*!40000 ALTER TABLE `asset_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixed_asset`
--

DROP TABLE IF EXISTS `fixed_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fixed_asset` (
  `asset_id` int NOT NULL AUTO_INCREMENT,
  `asset_type_id` int DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`asset_id`),
  KEY `asset_type_id` (`asset_type_id`),
  CONSTRAINT `fixed_asset_ibfk_1` FOREIGN KEY (`asset_type_id`) REFERENCES `asset_type` (`asset_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixed_asset`
--

LOCK TABLES `fixed_asset` WRITE;
/*!40000 ALTER TABLE `fixed_asset` DISABLE KEYS */;
INSERT INTO `fixed_asset` VALUES (2,1,'Laptop','2023-02-15',1200.00,'Available'),(3,1,'Laptop','2023-02-15',1200.00,'Available');
/*!40000 ALTER TABLE `fixed_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `permission_id` int NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'创建用户','允许用户创建新用户'),(2,'查看用户','允许用户查看用户信息'),(3,'更新用户','允许用户更新用户信息'),(4,'删除用户','允许用户删除用户');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1),(1,2),(2,2),(1,3),(1,4);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_pk` (`username`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5,'test','098f6bcd4621d373cade4e832627b4f6',1),(11,'test2','e10adc3949ba59abbe56e057f20f883e',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-01 21:05:39
