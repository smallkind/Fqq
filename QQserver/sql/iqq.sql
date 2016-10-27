/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50524
Source Host           : localhost:3306
Source Database       : iqq

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2015-04-11 20:22:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `chatinfo`
-- ----------------------------
DROP TABLE IF EXISTS `chatinfo`;
CREATE TABLE `chatinfo` (
  `cno` int(5) NOT NULL,
  `csendqq` varchar(11) NOT NULL,
  `creceiveqq` varchar(11) NOT NULL,
  `cdate` varchar(19) DEFAULT NULL,
  `tno` int(5) NOT NULL,
  PRIMARY KEY (`cno`),
  KEY `chatinfo_fk1` (`csendqq`),
  KEY `chatinfo_fk2` (`creceiveqq`),
  KEY `chatinfo_fk3` (`tno`),
  CONSTRAINT `chatinfo_fk1` FOREIGN KEY (`csendqq`) REFERENCES `userinfo` (`qq`),
  CONSTRAINT `chatinfo_fk2` FOREIGN KEY (`creceiveqq`) REFERENCES `userinfo` (`qq`),
  CONSTRAINT `chatinfo_fk3` FOREIGN KEY (`tno`) REFERENCES `text` (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chatinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `friends`
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `fno` int(5) NOT NULL AUTO_INCREMENT,
  `fqq` varchar(11) NOT NULL,
  `remark` varchar(10) NOT NULL,
  `fsno` int(5) NOT NULL,
  `fdate` varchar(19) DEFAULT NULL,
  `fstatus` varchar(5) NOT NULL,
  `qq` varchar(11) NOT NULL,
  PRIMARY KEY (`fno`),
  KEY `friends_fqq` (`fqq`),
  KEY `friends_fsno` (`fsno`),
  KEY `friends_qq` (`qq`),
  CONSTRAINT `friends_fqq` FOREIGN KEY (`fqq`) REFERENCES `userinfo` (`qq`),
  CONSTRAINT `friends_fsno` FOREIGN KEY (`fsno`) REFERENCES `subgroup` (`sno`),
  CONSTRAINT `friends_qq` FOREIGN KEY (`qq`) REFERENCES `userinfo` (`qq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friends
-- ----------------------------
INSERT INTO `friends` VALUES ('1', '434461234', '啊啊啊', '3', '2014-12-15', 'no', '935672344');
INSERT INTO `friends` VALUES ('2', '935672344', 'null', '1', '2014-12-15', 'no', '434461234');

-- ----------------------------
-- Table structure for `grouptable`
-- ----------------------------
DROP TABLE IF EXISTS `grouptable`;
CREATE TABLE `grouptable` (
  `gno` int(5) NOT NULL,
  `gname` varchar(10) NOT NULL,
  `gdate` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`gno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grouptable
-- ----------------------------
INSERT INTO `grouptable` VALUES ('1', '社会大学1203', '2014-12-16');

-- ----------------------------
-- Table structure for `login`
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `lno` int(11) NOT NULL AUTO_INCREMENT,
  `lip` varchar(20) NOT NULL,
  `lport` varchar(5) NOT NULL,
  `ldate` varchar(19) DEFAULT NULL,
  `lstatus` varchar(5) NOT NULL,
  `lqq` varchar(255) NOT NULL,
  PRIMARY KEY (`lno`),
  KEY `login_fk` (`lqq`),
  CONSTRAINT `login_fk` FOREIGN KEY (`lqq`) REFERENCES `userinfo` (`qq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('1', '127.0.0.1', '65085', '2015-03-21 11:36', 'no', '434461234');
INSERT INTO `login` VALUES ('2', '127.0.0.1', '65059', '2015-03-21 11:34', 'no', '935672344');

-- ----------------------------
-- Table structure for `subgroup`
-- ----------------------------
DROP TABLE IF EXISTS `subgroup`;
CREATE TABLE `subgroup` (
  `sno` int(5) NOT NULL,
  `sname` varchar(10) NOT NULL,
  `sdate` varchar(19) DEFAULT NULL,
  `qq` varchar(11) NOT NULL,
  PRIMARY KEY (`sno`),
  KEY `subgroup` (`qq`),
  CONSTRAINT `subgroup` FOREIGN KEY (`qq`) REFERENCES `userinfo` (`qq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subgroup
-- ----------------------------
INSERT INTO `subgroup` VALUES ('1', '我的好友', '2014-12-15', '434461234');
INSERT INTO `subgroup` VALUES ('2', '黑名单', '2014-12-15', '434461234');
INSERT INTO `subgroup` VALUES ('3', '我的好友', '2014-12-15', '935672344');
INSERT INTO `subgroup` VALUES ('4', '黑名单', '2014-12-15', '935672344');

-- ----------------------------
-- Table structure for `text`
-- ----------------------------
DROP TABLE IF EXISTS `text`;
CREATE TABLE `text` (
  `tno` int(5) NOT NULL,
  `tcontext` varchar(200) NOT NULL,
  `tfonttype` varchar(10) DEFAULT NULL,
  `tfontsize` varchar(5) DEFAULT NULL,
  `tfontcolor` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of text
-- ----------------------------

-- ----------------------------
-- Table structure for `user_group`
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `ugno` int(5) NOT NULL,
  `qq` varchar(11) NOT NULL,
  `gno` int(5) NOT NULL,
  PRIMARY KEY (`ugno`),
  KEY `user_group_fk1` (`qq`),
  KEY `user_group_fk2` (`gno`),
  CONSTRAINT `user_group_fk1` FOREIGN KEY (`qq`) REFERENCES `userinfo` (`qq`),
  CONSTRAINT `user_group_fk2` FOREIGN KEY (`gno`) REFERENCES `grouptable` (`gno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES ('1', '434461234', '1');
INSERT INTO `user_group` VALUES ('2', '935672344', '1');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `qq` varchar(11) NOT NULL,
  `pwd` varchar(15) NOT NULL,
  `sign` varchar(30) DEFAULT NULL,
  `photoid` varchar(5) NOT NULL DEFAULT '001',
  `nickname` varchar(10) NOT NULL,
  `sex` varchar(5) NOT NULL,
  `birthday` varchar(19) DEFAULT NULL,
  `constellation` varchar(5) DEFAULT NULL,
  `bloodtype` varchar(5) DEFAULT NULL,
  `diploma` varchar(5) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `mbg` varchar(10) NOT NULL DEFAULT 'qqLogin',
  `bg` varchar(10) NOT NULL DEFAULT 'qqLogin',
  PRIMARY KEY (`qq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('434461234', '666666', '我很温柔，我很萌。哇卡卡', '003', '薛定谔的猫啦~', '女', null, null, null, null, null, null, null, 'qqLogin', 'qqLogin');
INSERT INTO `userinfo` VALUES ('935672344', '666666', null, '002', '、小样', '男', null, null, null, null, null, null, null, 'qqLogin', '11');
