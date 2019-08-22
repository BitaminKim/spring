/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : spring

 Target Server Type    : MySQL
 Target Server Version : 50799
 File Encoding         : 65001

 Date: 22/08/2019 17:32:41
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dept_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `hobby` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_account_department`(`dept_id`) USING BTREE,
  CONSTRAINT `fk_account_department` FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` (`id`, `dept_id`, `username`, `realname`, `email`, `password`, `sex`, `hobby`, `remark`, `birthday`, `status`, `gmt_create`, `gmt_modified`) VALUES (1, NULL, 'a', NULL, NULL, 'a', NULL, NULL, NULL, NULL, 1, '2019-08-18 22:24:43', '2019-08-18 22:24:43'), (2, NULL, 'adminn12', NULL, NULL, 'admin', NULL, NULL, NULL, NULL, 1, '2019-08-18 22:27:10', '2019-08-18 22:27:52'), (3, NULL, 'username', NULL, NULL, 'password', NULL, NULL, NULL, NULL, 1, '2019-08-18 22:37:24', '2019-08-18 22:37:24');
COMMIT;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO `department` (`id`, `dept_name`, `status`, `gmt_create`, `gmt_modified`) VALUES (1, 'A', 1, '2019-08-19 16:46:44', '2019-08-19 16:46:44');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
