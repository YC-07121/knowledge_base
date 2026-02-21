CREATE DATABASE  IF NOT EXISTS `knowledge_base` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `knowledge_base`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: knowledge_base
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'說明書',NULL,1),(2,'耳機',1,1),(3,'食譜',NULL,1),(4,'中餐',3,1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` longtext,
  `user_id` int NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (1,2,'WH-1000XM5','Bluetooth連接\n步驟 1\n第一次將購買的耳機與裝置配對時，或在初始化耳機之後（耳機內沒有配對資訊），開啟耳機電源。耳機便會自動進入配對模式。在此情況下，請進行步驟 2 。\n\n配對第二個或之後的裝置時（耳機內已有其他裝置的配對資訊），按住（電源）按鈕約5秒可手動進入配對模式。\n\n步驟 2\n在Bluetooth裝置上執行配對程序，以搜尋本耳機。\nBluetooth裝置螢幕上的偵測到的裝置清單上將顯示[WH-1000XM5]。\n\n如果未出現，請從步驟 1 開始重複程序。\n選擇Bluetooth裝置畫面上顯示的[WH-1000XM5]以便配對。\n若需要輸入金鑰（*），請輸入“0000”。\n\n觸控面板操控\n播放/暫停：快速輕按觸控感應器控制面板兩下（間隔約0.4秒）。\n跳至下一首曲目的開頭：向前滑動然後放開。\n跳至下一首曲目的開頭：向前滑動然後放開。\n向前快轉：向前滑動然後停住。（需要一點時間才會開始向前快轉。）到所需的播放點時放開。\n快速倒轉：向後滑動然後停住。（需要一點時間才會開始快速倒轉。）到所需的播放點時放開。\n放大音量：重複向上滑動，直到調整到所需音量為止。\n降低音量：重複向下滑動，直到調整到所需音量為止。\n連續變更音量：向上或向下滑動並停留。到所需的音量時放開。',1,'2026-02-21 10:03:45'),(4,4,'糖醋排骨','糖醋排骨食譜\n食材\n排骨 400克\n青椒 1/2顆\n太白粉15克\n排骨醃料\n烏醋 10克\n細砂糖 5克\n蒜末 適量\n鹽 少許\n糖醋醬汁\n番茄醬45克\n細砂糖 5克\n烏醋 10克\n鹽 少許\n\n做法\n1. 排骨洗淨後，以排骨醃料醃製30～60分鐘備用。\n2. 青椒切小塊備用。\n3. 蕃茄醬、砂糖、烏醋、鹽少許，再加上少許的水攪拌均勻做成糖醋醬汁。\n4. 醃好的排骨，均勻裹上太白粉之後，下鍋油炸；炸至金黃色時，將青椒入鍋拌炒。\n5. 倒入調配好的醬汁，均勻翻炒直到收汁後，起鍋盛盤。',1,'2026-02-21 10:12:10'),(5,4,'宮保雞丁','簡易宮保雞丁（2-3人份）\n材料：雞胸肉或雞腿肉 300g、花生（熟）適量、乾辣椒 10-15根（切段）、蒜末 1大匙、薑末 1茶匙、蔥段適量、花椒粒 1茶匙。\n雞肉醃料：醬油 1大匙、米酒 1茶匙、太白粉 1茶匙、少許油。\n宮保醬汁（先混勻）：醬油 1.5大匙、糖 1大匙、烏醋 1大匙、水 2大匙、太白粉 1/2茶匙。 \n\n作法步驟\n處理雞肉：雞肉切成約2公分小丁，加入醃料抓勻，醃製10-15分鐘。\n煎雞肉：平底鍋倒熱油，將雞丁放入煎至兩面金黃，約8分熟後先撈出備用。\n爆香：利用鍋內餘油，小火爆香花椒粒、乾辣椒，接著放入蒜末、薑末炒出香氣。\n快炒：將雞丁倒回鍋中，倒入調好的「宮保醬汁」，轉中大火快速翻炒，讓醬汁濃稠收汁。\n起鍋：放入花生和蔥段，快速拌炒均勻即完成。 \n\n零失敗訣竅\n爆香火侯：乾辣椒與花椒需小火爆香，以免燒焦發苦。\n醬汁比例：宮保料理強調「酸、甜、辣、香」，糖與醋的比例可依喜好調整。\n雞肉口感：雞胸肉可用雞腿肉代替更嫩，醃製時加少許油可鎖住水分。',1,'2026-02-21 10:13:56'),(6,2,'Razer Barracuda X Chroma','充電\n充電中閃爍紅光\n充飽電閃爍綠光\n\n開啟/關閉耳麥電源\n長按電源鍵2秒\n\n2.4HZ模式\n請將接收器插上裝置\n\nBluetooth模式\n初次使用:\n短按switch按鍵2下\n閃爍藍光代表配對中\n恆亮藍光代表已配對\n\n配對新的裝置:\n長按5秒switch按鍵\n閃爍藍光代表配對中\n恆亮藍光代表已配對\n\n雙模切換\n短按switch按鍵2下\n\n切換RGB發光\n短按switch按鍵3下\n',1,'2026-02-21 10:16:10');
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aaaa','aaaa');
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

-- Dump completed on 2026-02-21 19:27:07
