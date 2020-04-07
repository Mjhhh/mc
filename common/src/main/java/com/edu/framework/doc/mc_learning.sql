/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : mc_learning

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/04/2020 22:25:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mc_collection
-- ----------------------------
DROP TABLE IF EXISTS `mc_collection`;
CREATE TABLE `mc_collection`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `course_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mc_learning_course
-- ----------------------------
DROP TABLE IF EXISTS `mc_learning_course`;
CREATE TABLE `mc_learning_course`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费规则',
  `price` float(8, 2) NULL DEFAULT NULL COMMENT '课程价格',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效性',
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选课状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xc_learning_list_unique`(`course_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_learning_course
-- ----------------------------
INSERT INTO `mc_learning_course` VALUES ('297ec72c710a6fa701710a78cc8b0004', '297ec72c70fba29f0170fbbe79980002', '297ec72c7100072001710010c6d80000', '203001', 99.00, '204001', '2020-04-03 22:07:55', '2020-12-03 22:07:59', '501001');
INSERT INTO `mc_learning_course` VALUES ('297ec72c712678b6017126e0c2710000', '297ec72c7126792d017126a9bfc1000c', '297ec72c7100072001710010c6d80000', '203001', NULL, '204002', '2020-03-29 23:19:56', '2020-03-29 00:00:00', '501001');

-- ----------------------------
-- Table structure for mc_task_his
-- ----------------------------
DROP TABLE IF EXISTS `mc_task_his`;
CREATE TABLE `mc_task_his`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `task_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
  `mq_exchange` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交换机名称',
  `mq_routingkey` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'routingkey',
  `request_body` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务请求的内容',
  `version` int(10) NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `errormsg` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_task_his
-- ----------------------------
INSERT INTO `mc_task_his` VALUES ('4028858162959ce5016295b604ba0000', '2018-04-05 20:09:17', '2018-04-05 20:09:18', '2018-04-05 20:09:21', 'add_choosecourse', 'ex_learning_addchoosecourse', 'addchoosecourse', '{\"courseId\":\"4028e58161bcf7f40161bcf8b77c0000,\",\"userId\":\"49\"}', NULL, '10201', NULL);

SET FOREIGN_KEY_CHECKS = 1;
