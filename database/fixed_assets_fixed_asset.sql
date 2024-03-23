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
  `useful_year` int DEFAULT NULL,
  PRIMARY KEY (`asset_id`),
  KEY `asset_type_id` (`asset_type_id`),
  CONSTRAINT `fixed_asset_ibfk_1` FOREIGN KEY (`asset_type_id`) REFERENCES `asset_type` (`asset_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixed_asset`
--

LOCK TABLES `fixed_asset` WRITE;
/*!40000 ALTER TABLE `fixed_asset` DISABLE KEYS */;
INSERT INTO `fixed_asset` VALUES (2,14,'Mac笔记本电脑','2020-02-09',12000.00,'报废','http://localhost:8080/fixedAssets/files/1709456731167-63.png',10),(10,1,'打印机','2024-03-02',7999.00,'报废','http://localhost:8080/fixedAssets/files/1709457025632-942.jpg',15),(11,7,'联想笔记本电脑','2024-03-05',10010.00,'使用中','http://localhost:8080/fixedAssets/files/1710062763550-655.jpg',10),(14,7,'投影仪','2018-03-05',11470.00,'使用中','http://localhost:8080/fixedAssets/files/1709810588739-668.jpg',20),(15,7,'联想笔记本电脑','2018-03-05',8888.00,'使用中','http://localhost:8080/fixedAssets/files/1709896402887-874.jpg',10),(16,7,'华为手机 P40','2022-01-10',8999.00,'闲置','http://localhost:8080/fixedAssets/files/1710059980190-602.jpg',10),(17,1,'打印机',NULL,7999.00,'闲置','http://localhost:8080/fixedAssets/files/1710060022074-103.jpg',20),(18,7,'联想笔记本电脑','2021-03-15',12899.00,'使用中','http://localhost:8080/fixedAssets/files/1710062774431-760.jpg',10),(19,9,'泛微OA系统','2019-03-04',210000.00,'闲置','http://localhost:8080/fixedAssets/files/1710061643304-452.png',5),(20,8,'办公椅','2022-03-11',532.00,'使用中','http://localhost:8080/fixedAssets/files/1710062029102-391.jpg',5),(21,8,'办公椅','2021-01-02',162.00,'闲置','http://localhost:8080/fixedAssets/files/1710062181287-746.jpg',5),(22,8,'办公灯','2022-03-07',68.00,'使用中','http://localhost:8080/fixedAssets/files/1710062288126-585.jpg',10),(23,8,'办公灯','2020-03-01',68.00,'使用中','http://localhost:8080/fixedAssets/files/1710062367423-80.jpg',10),(24,8,'办公灯','2023-03-07',78.00,'闲置','http://localhost:8080/fixedAssets/files/1710062390701-468.jpg',10),(26,14,'联想笔记本电脑','2023-03-01',5600.00,'使用中','http://localhost:8080/fixedAssets/files/1710062835868-213.jpg',3),(27,3,'商务车','2020-02-03',240000.00,'使用中','http://localhost:8080/fixedAssets/files/1710065129072-829.jpg',15),(28,3,'商务车','2022-03-15',490000.00,'使用中','http://localhost:8080/fixedAssets/files/1710065157741-670.jpg',15),(29,8,'办公洽谈沙发','2022-03-01',7609.00,'闲置','http://localhost:8080/fixedAssets/files/1710347551923-330.jpg',20),(30,8,'办公椅','2020-03-03',130.00,'闲置','http://localhost:8080/fixedAssets/files/1710811326335-674.jpg',10),(31,1,'复印机','2017-03-06',8908.00,'闲置','http://localhost:8080/fixedAssets/files/1710811410690-615.jpg',20),(32,7,'笔记本电脑','2019-03-05',8876.00,'闲置','http://localhost:8080/fixedAssets/files/1710815010342-212.jpg',5),(33,7,'联想笔记本电脑','2018-03-12',5676.00,'闲置','http://localhost:8080/fixedAssets/files/1710815092296-130.jpg',5),(34,7,'联想笔记本电脑','2020-03-09',9876.00,'闲置','http://localhost:8080/fixedAssets/files/1710815100181-565.jpg',5),(35,3,'商务车','2017-03-06',500000.00,'使用中','http://localhost:8080/fixedAssets/files/1710815149264-175.jpg',20),(36,3,'商务车','2020-03-03',360000.00,'闲置','http://localhost:8080/fixedAssets/files/1710815190773-369.jpg',20),(37,1,'办公椅','2024-03-19',120.00,'闲置','http://localhost:8080/fixedAssets/files/1710816025885-707.jpg',10);
/*!40000 ALTER TABLE `fixed_asset` ENABLE KEYS */;
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
