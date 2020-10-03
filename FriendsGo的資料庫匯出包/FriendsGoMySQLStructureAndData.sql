CREATE DATABASE  IF NOT EXISTS `friendsgo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `friendsgo`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: friendsgo
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `帳號`
--

DROP TABLE IF EXISTS `帳號`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `帳號` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(10) NOT NULL,
  `Password` varchar(10) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  UNIQUE KEY `UserID_UNIQUE` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `帳號`
--

LOCK TABLES `帳號` WRITE;
/*!40000 ALTER TABLE `帳號` DISABLE KEYS */;
INSERT INTO `帳號` VALUES (1,'123','123'),(2,'gogo','eewq'),(3,'12ss3','123'),(4,'12sdds3','123'),(5,'jimmy','123');
/*!40000 ALTER TABLE `帳號` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `集合點`
--

DROP TABLE IF EXISTS `集合點`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `集合點` (
  `集合點ID` varchar(10) NOT NULL,
  `名稱` varchar(30) NOT NULL,
  `緯度` decimal(11,8) NOT NULL,
  `經度` decimal(11,8) NOT NULL,
  PRIMARY KEY (`集合點ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `集合點`
--

LOCK TABLES `集合點` WRITE;
/*!40000 ALTER TABLE `集合點` DISABLE KEYS */;
INSERT INTO `集合點` VALUES ('0000000001','逢甲大學體育館',24.18164100,120.64877800),('0000000002','逢甲大學圖書館',24.17866000,120.64896600),('0000000003','欣都專業影印',24.17990200,120.64612400);
/*!40000 ALTER TABLE `集合點` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `餐廳`
--

DROP TABLE IF EXISTS `餐廳`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `餐廳` (
  `餐廳ID` varchar(10) NOT NULL,
  `餐廳名稱` varchar(30) NOT NULL,
  `緯度` decimal(11,8) NOT NULL,
  `經度` decimal(11,8) NOT NULL,
  PRIMARY KEY (`餐廳ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `餐廳`
--

LOCK TABLES `餐廳` WRITE;
/*!40000 ALTER TABLE `餐廳` DISABLE KEYS */;
INSERT INTO `餐廳` VALUES ('0000000001','腦子有丼',24.18227000,120.64625900),('0000000002','早安有喜',24.17620800,120.65002000),('0000000003','麥當勞',24.17905300,120.64523300),('0000000123','無名麻辣鴨血',24.18123900,120.64634400);
/*!40000 ALTER TABLE `餐廳` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `餐點`
--

DROP TABLE IF EXISTS `餐點`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `餐點` (
  `餐廳名稱` char(30) NOT NULL,
  `餐名` char(45) NOT NULL,
  `價格` int(11) NOT NULL,
  PRIMARY KEY (`餐廳名稱`,`餐名`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `餐點`
--

LOCK TABLES `餐點` WRITE;
/*!40000 ALTER TABLE `餐點` DISABLE KEYS */;
INSERT INTO `餐點` VALUES ('早安有喜','培根蛋餅',35),('早安有喜','巧克力吐司',25),('早安有喜','草莓吐司',25),('早安有喜','起司蛋餅',35),('早安有喜','鐵板麵',60),('早安有喜','鐵板麵和紅茶',70),('無名麻辣鴨血','麻辣鴨血意麵',70),('無名麻辣鴨血','麻辣鴨血王子麵',70),('無名麻辣鴨血','麻辣鴨血米粉',70),('腦子有丼','不吃',0),('腦子有丼','宣儒套餐',120),('腦子有丼','豬肉丼飯',80),('麥當勞','1+1套餐:法式香雞堡和雪碧',50),('麥當勞','可樂',40),('麥當勞','薯條',55);
/*!40000 ALTER TABLE `餐點` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-23 20:56:13
