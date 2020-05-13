-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: seckill
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sk_goods`
--

DROP TABLE IF EXISTS `sk_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext COMMENT '商品介绍详情',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sk_goods`
--

LOCK TABLES `sk_goods` WRITE;
/*!40000 ALTER TABLE `sk_goods` DISABLE KEYS */;
INSERT INTO `sk_goods` VALUES (1,' iPhone X',' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动','/img/iphone.jpg',' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动',7268.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20'),(2,'xiaomi 8',' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se','/img/xiaomi.jpg',' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se',2799.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20'),(3,'荣耀 10',' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy','/img/rongyao.jpg',' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy',2699.00,1000,'2018-07-12 19:06:20','2018-07-12 22:32:20'),(4,'oppo find x',' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x','/img/oppo.jpg',' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x',4999.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20');
/*!40000 ALTER TABLE `sk_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sk_goods_seckill`
--

DROP TABLE IF EXISTS `sk_goods_seckill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_goods_seckill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '秒杀数量',
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `version` int(255) DEFAULT NULL COMMENT '并发版本控制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sk_goods_seckill`
--

LOCK TABLES `sk_goods_seckill` WRITE;
/*!40000 ALTER TABLE `sk_goods_seckill` DISABLE KEYS */;
INSERT INTO `sk_goods_seckill` VALUES (1,1,6888.00,92,'2018-07-12 19:06:20','2021-05-08 23:06:20',1),(2,2,2699.00,95,'2018-07-17 22:32:20','2018-08-15 19:06:20',0),(3,3,2599.00,93,'2018-07-14 00:59:20','2018-08-15 19:06:20',0),(4,4,4999.00,97,'2020-06-06 09:06:20','2021-05-08 23:06:20',0);
/*!40000 ALTER TABLE `sk_goods_seckill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sk_order`
--

DROP TABLE IF EXISTS `sk_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sk_order`
--

LOCK TABLES `sk_order` WRITE;
/*!40000 ALTER TABLE `sk_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `sk_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sk_order_info`
--

DROP TABLE IF EXISTS `sk_order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `delivery_addr_id` bigint(20) DEFAULT NULL,
  `goods_name` varchar(30) DEFAULT NULL,
  `goods_count` int(11) DEFAULT NULL,
  `goods_price` decimal(10,2) DEFAULT NULL,
  `order_channel` tinyint(4) DEFAULT NULL COMMENT '订单渠道，1在线，2android，3ios',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `create_date` datetime DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sk_order_info`
--

LOCK TABLES `sk_order_info` WRITE;
/*!40000 ALTER TABLE `sk_order_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sk_order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sk_user`
--

DROP TABLE IF EXISTS `sk_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt',
  `salt` varchar(10) DEFAULT NULL COMMENT '混淆盐',
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sk_user`
--

LOCK TABLES `sk_user` WRITE;
/*!40000 ALTER TABLE `sk_user` DISABLE KEYS */;
INSERT INTO `sk_user` VALUES (16605165649,'tan','b7797cce01b4b131b433b6acf4add449','1a2b3c4d',NULL,'2018-05-21 21:10:21','2018-05-21 21:10:25',1);
/*!40000 ALTER TABLE `sk_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-13 16:58:05
