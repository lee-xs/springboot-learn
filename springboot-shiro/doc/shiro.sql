/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-08-27 17:29:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '登录权限', '登录后台');
INSERT INTO `permission` VALUES ('2', '添加用户', '后台添加用户角色');
INSERT INTO `permission` VALUES ('3', '更新用户', '更新用户信息');
INSERT INTO `permission` VALUES ('4', '删除用户', '删除用户数据');
INSERT INTO `permission` VALUES ('5', '查看用户', '查看用户详细信息');
INSERT INTO `permission` VALUES ('6', '用户列表', '查看用户列表信息');
INSERT INTO `permission` VALUES ('7', '搜索用户', '搜索用户数据');
INSERT INTO `permission` VALUES ('8', '权限管理', '修改用户的权限信息');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '拥有所有的管理权限', '超级管理员');
INSERT INTO `role` VALUES ('2', '拥有增删改查权限', '管理员');
INSERT INTO `role` VALUES ('3', '拥有查看用户信息权限', '游客');

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `permission_id` int(32) NOT NULL,
  `role_id` int(32) NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('1', '3');
INSERT INTO `role_permission` VALUES ('2', '1');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('3', '1');
INSERT INTO `role_permission` VALUES ('3', '2');
INSERT INTO `role_permission` VALUES ('4', '1');
INSERT INTO `role_permission` VALUES ('4', '2');
INSERT INTO `role_permission` VALUES ('5', '1');
INSERT INTO `role_permission` VALUES ('5', '2');
INSERT INTO `role_permission` VALUES ('5', '3');
INSERT INTO `role_permission` VALUES ('6', '1');
INSERT INTO `role_permission` VALUES ('6', '2');
INSERT INTO `role_permission` VALUES ('6', '3');
INSERT INTO `role_permission` VALUES ('7', '1');
INSERT INTO `role_permission` VALUES ('7', '2');
INSERT INTO `role_permission` VALUES ('7', '3');
INSERT INTO `role_permission` VALUES ('8', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(256) NOT NULL,

  `email` varchar(128) DEFAULT NULL,
  `salt` varchar(256) NOT NULL,
  `valid` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '8c04b42930a2a7cd0fe9ce723e3b8220', 'admin@qq.com', 'b192d79e2fb048238192d5d340c5a532', '1');
INSERT INTO `user` VALUES ('2', 'root', 'd45d7b99c60d2118f16f85262fad689e', 'root@qq.com', 'fdc96d132b7e4270bf5125283773f4ae', '1');
INSERT INTO `user` VALUES ('3', 'zhangsan', '52870c846b711c3bf7a6040270338b3d', 'zhangsan@qq.com', 'eacbb3f9cbbc49eba3c7ed57dae57325', '1');
INSERT INTO `user` VALUES ('4', 'lisi', '41a0526a43844e5e738e9162663c2352', 'lisi@qq.com', '414f22be9a9547cb9a420d6f36716a1e', '1');
INSERT INTO `user` VALUES ('5', 'wangwu', 'd2c7261fe6615f5d84a115018583365b', 'wangwu@qq.com', '8ea85231f5be4b00a2178001f9104934', '1');
INSERT INTO `user` VALUES ('6', 'zhaoliu', 'd7d65c09052a13d1e957c4a7e85c91e9', 'zhaoliu@qq.com', 'c4d3ddf63a93489384e43da5dce56e3b', '1');
INSERT INTO `user` VALUES ('7', 'xiaoqi', '72c51d4d3eb870ec512cab86b2261c1a', 'xiaoqi@qq.com', '966ca5b4d4d940c1bbde2f5881020544', '1');
INSERT INTO `user` VALUES ('8', 'fugui', '2cf4099fbad20200f549481ce2c3109c', 'fugui@qq.com', '033673df2b6c4b0e8460ae29ac92cd32', '1');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` int(32) NOT NULL,
  `role_id` int(32) NOT NULL,
  PRIMARY KEY (`uid`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('3', '3');
INSERT INTO `user_role` VALUES ('4', '2');
INSERT INTO `user_role` VALUES ('5', '3');
INSERT INTO `user_role` VALUES ('6', '3');
INSERT INTO `user_role` VALUES ('7', '3');
INSERT INTO `user_role` VALUES ('8', '3');
