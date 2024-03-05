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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-05 21:04:38
