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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_allocation`
--

LOCK TABLES `asset_allocation` WRITE;
/*!40000 ALTER TABLE `asset_allocation` DISABLE KEYS */;
INSERT INTO `asset_allocation` VALUES (2,2,13,'人事部','2024-03-03','2024-03-14','demo','审核通过',NULL),(3,3,13,'人事部','2024-03-03','2024-03-14','demo','审核不通过','信息不完整');
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
  `depreciation_amount` decimal(10,2) DEFAULT NULL,
  `accumulated_depreciation` decimal(10,2) DEFAULT NULL,
  `book_value` decimal(10,2) DEFAULT NULL,
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
  `department_id` int DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `book_quantity` int DEFAULT NULL,
  `book_value` decimal(10,2) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_type`
--

LOCK TABLES `asset_type` WRITE;
/*!40000 ALTER TABLE `asset_type` DISABLE KEYS */;
INSERT INTO `asset_type` VALUES (1,'办公设备','包括计算机、打印机、复印机等用于日常办公的设备。 折旧年限: 5年'),(2,'建筑物','公司所有的建筑物，如办公楼、仓库。 折旧年限: 30年'),(3,'交通工具','用于商务活动的交通工具，如公司车辆。 折旧年限: 4年'),(6,'生产设备','用于生产过程中的机器和设备。折旧年限: 10年'),(7,'电子设备','包括服务器、网络设备等IT基础设施。 折旧年限: 3年'),(8,'家具和装置','包括办公室家具、灯具等装饰性和功能性物品。 折旧年限: 7年'),(9,'软件和信息系统','用于业务运营的软件和信息系统，包括购买的许可软件和定制开发的软件。 折旧年限: 3-5年'),(10,'土地改良','对土地进行的改良，如排水系统、围栏、铺设道路等，以提高土地的使用效率或价值。 折旧年限: 15-20年（土地本身通常不折旧）'),(11,'租赁改良','对租赁物业所作的改良或增加的设施，以满足特定的业务需求。 折旧年限: 改良的预计使用寿命或租赁期限，取二者中较短的一个'),(12,'专利权和版权','获得的专利权和版权，为企业提供独家经营权或使用权。 折旧年限: 根据实际有效期折旧，专利权通常为20年，版权根据法律规定可能更长'),(13,'无形资产','如商誉、品牌名称、商标等无形的资产。 折旧年限: 根据资产的预期使用寿命折旧，若难以合理估计，则每年进行减值测试而不直接折旧'),(14,'研发设备','专门用于研究和开发活动的设备和工具。 折旧年限: 5-10年');
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (2,'人事部','负责人事调动、入职等'),(3,'财务部','固定资产审核、盘点'),(8,'市场营销部','负责市场研究、品牌管理、广告、促销活动和其他营销策略的制定与实施，以提升公司产品或服务的市场份额和品牌知名度。'),(9,'研发部','负责新产品或服务的研究和开发，创新技术，以及改进现有产品。');
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (5,'wyz',2),(6,'wyz2',2);
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
  `img_dir` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`asset_id`),
  KEY `asset_type_id` (`asset_type_id`),
  CONSTRAINT `fixed_asset_ibfk_1` FOREIGN KEY (`asset_type_id`) REFERENCES `asset_type` (`asset_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixed_asset`
--

LOCK TABLES `fixed_asset` WRITE;
/*!40000 ALTER TABLE `fixed_asset` DISABLE KEYS */;
INSERT INTO `fixed_asset` VALUES (2,1,'Laptop','2023-02-12',1200.00,'使用中','https://cdn.pixabay.com/photo/2017/08/07/23/43/nature-2609259_1280.jpg'),(3,1,'Laptop','2023-02-11',1200.00,'闲置','https://cdn.pixabay.com/photo/2017/08/07/23/43/nature-2609259_1280.jpg'),(9,12,'手抓饭','2024-03-03',0.03,'闲置','http://localhost:8080/fixedAssets/files/1709356057992-485.jpg');
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
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_pk` (`username`),
  KEY `role_id` (`role_id`),
  KEY `user_ibfk_2` (`employee_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5,'test','202cb962ac59075b964b07152d234b70',1,NULL),(12,'老王','202cb962ac59075b964b07152d234b70',1,NULL),(13,'user','202cb962ac59075b964b07152d234b70',2,6),(18,'wyz','2a75448b4a4ddab6226a207e7a3a8447',2,5);
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

-- Dump completed on 2024-03-04 17:08:55
