-- MySQL dump 10.13  Distrib 8.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: bdbuscalicenca
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `t_aviso`
--

DROP TABLE IF EXISTS `t_aviso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_aviso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `mensagem` text NOT NULL,
  `data_inicio` date NOT NULL,
  `data_fim` date NOT NULL,
  `ativo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_aviso`
--

LOCK TABLES `t_aviso` WRITE;
/*!40000 ALTER TABLE `t_aviso` DISABLE KEYS */;
INSERT INTO `t_aviso` VALUES (1,'Nova atualiza├º├úo','Nova vers├úo dispon├¡vel em https://ephesos.com.br','2025-05-12','2025-06-11',1);
/*!40000 ALTER TABLE `t_aviso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_registro`
--

DROP TABLE IF EXISTS `t_registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_registro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `docusuario` varchar(50) NOT NULL,
  `chave` varchar(255) NOT NULL,
  `vencimento` date NOT NULL,
  `acesso` varchar(20) NOT NULL,
  `infoacesso` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_registro`
--

LOCK TABLES `t_registro` WRITE;
/*!40000 ALTER TABLE `t_registro` DISABLE KEYS */;
INSERT INTO `t_registro` VALUES (1,'12345789','59ccf8a5-3353-11f0-920f-40b076226f4a','2025-08-17','VALIDO',NULL);
/*!40000 ALTER TABLE `t_registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_serialhd`
--

DROP TABLE IF EXISTS `t_serialhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_serialhd` (
  `id` int NOT NULL AUTO_INCREMENT,
  `documento` varchar(50) NOT NULL,
  `serialhd` varchar(100) NOT NULL,
  `inserido` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_serialhd`
--

LOCK TABLES `t_serialhd` WRITE;
/*!40000 ALTER TABLE `t_serialhd` DISABLE KEYS */;
INSERT INTO `t_serialhd` VALUES (1,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-04-29 19:02:04'),(2,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-04-29 19:04:26'),(3,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-22 11:45:14'),(4,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-22 11:49:01'),(5,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-22 11:49:27'),(6,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:13:51'),(7,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:15:11'),(8,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:23:10'),(9,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:24:57'),(10,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:28:25'),(11,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:32:11'),(12,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:45:31'),(13,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:48:31'),(14,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:49:36'),(15,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:52:09'),(16,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:52:26'),(17,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:54:11'),(18,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:55:26'),(19,'12345789','000070100007250026B768262A144WD-WCC6Y5ZHNXY4','2025-05-24 02:56:17');
/*!40000 ALTER TABLE `t_serialhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_usuario`
--

DROP TABLE IF EXISTS `t_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `documento` varchar(20) NOT NULL,
  `data_criacao` date NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_usuario`
--

LOCK TABLES `t_usuario` WRITE;
/*!40000 ALTER TABLE `t_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_usuario_aviso`
--

DROP TABLE IF EXISTS `t_usuario_aviso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_usuario_aviso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_aviso` int NOT NULL,
  `documento_usuario` varchar(50) NOT NULL,
  `ocultado` tinyint(1) DEFAULT '0',
  `visualizado_em` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_aviso` (`id_aviso`),
  CONSTRAINT `t_usuario_aviso_ibfk_1` FOREIGN KEY (`id_aviso`) REFERENCES `t_aviso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_usuario_aviso`
--

LOCK TABLES `t_usuario_aviso` WRITE;
/*!40000 ALTER TABLE `t_usuario_aviso` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_usuario_aviso` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-02 18:05:38
