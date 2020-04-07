/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : mc_order

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/04/2020 22:25:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mc_order_refund
-- ----------------------------
DROP TABLE IF EXISTS `mc_order_refund`;
CREATE TABLE `mc_order_refund`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `pay_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付系统订单号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易状态',
  `pay_system` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付系统',
  `refund_amount` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '退款金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mc_orders
-- ----------------------------
DROP TABLE IF EXISTS `mc_orders`;
CREATE TABLE `mc_orders`  (
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `initial_price` float(8, 2) NULL DEFAULT NULL COMMENT '定价',
  `price` float(8, 2) NULL DEFAULT NULL COMMENT '交易价',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `course_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程有效性',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '课程开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '课程结束时间',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易状态',
  `details` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单明细',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_orders
-- ----------------------------
INSERT INTO `mc_orders` VALUES ('297ec72c71117dad0171118701090000', 199.00, 0.01, '297ec72c7100072001710010c6d80000', '297ec72c70fba29f0170fbbe79980002', 'Jmeter 接口自动化脚本结构进阶', '204001', '2020-03-25 19:49:53', '2020-03-25 19:59:53', '401002', NULL);
INSERT INTO `mc_orders` VALUES ('297ec72c712678ab017126ddc1700000', 59.00, 89.00, '297ec72c7100072001710010c6d80000', '297ec72c7126792d017126a9bfc1000c', '二进制与Java中的基本数据类型', '204002', '2020-03-29 23:18:41', '2020-03-29 23:28:41', '401001', NULL);
INSERT INTO `mc_orders` VALUES ('297ec72c714840e1017148aa54720000', 36.00, 6.00, '297ec72c7100072001710010c6d80000', '297ec72c7148403d017148a558360017', 'React Native开发播放器', '204001', '2020-04-05 12:47:35', '2020-04-05 12:57:35', '401001', NULL);

-- ----------------------------
-- Table structure for mc_orders_pay
-- ----------------------------
DROP TABLE IF EXISTS `mc_orders_pay`;
CREATE TABLE `mc_orders_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `pay_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付系统订单号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易状态',
  `pay_system` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付系统',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xc_orders_pay_order_number_unique`(`order_id`) USING BTREE,
  UNIQUE INDEX `xc_orders_pay_pay_number_unique`(`pay_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_orders_pay
-- ----------------------------
INSERT INTO `mc_orders_pay` VALUES ('297ec72c71117dad01711187727f0001', '297ec72c71117dad0171118701090000', '2020032522001401450501192062', '402002', '1');

-- ----------------------------
-- Table structure for mc_task
-- ----------------------------
DROP TABLE IF EXISTS `mc_task`;
CREATE TABLE `mc_task`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `task_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
  `mq_exchange` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交换机名称',
  `mq_routingkey` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'routingkey',
  `request_body` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务请求的内容',
  `version` int(10) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `errormsg` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务错误信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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

SET FOREIGN_KEY_CHECKS = 1;
