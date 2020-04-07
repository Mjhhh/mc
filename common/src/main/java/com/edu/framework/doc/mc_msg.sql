/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : mc_msg

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/04/2020 22:25:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mc_chat_msg
-- ----------------------------
DROP TABLE IF EXISTS `mc_chat_msg`;
CREATE TABLE `mc_chat_msg`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `send_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送者的id',
  `send_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送者的名称',
  `accept_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接收者的id',
  `accept_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收者的名称',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送的内容',
  `sign_flag` int(1) NOT NULL COMMENT '是否签收 0-未签收 1-已签收',
  `create_time` datetime(0) NOT NULL COMMENT '发送时间',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送的图片地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_chat_msg
-- ----------------------------
INSERT INTO `mc_chat_msg` VALUES ('297ec72c714d309401714d85845d0000', '297ec72c711aefbb01711af13e220000', '刘老师', '297ec72c7100072001710010c6d80000', '李大爷', '你好\n', 1, '2020-04-06 11:25:28', '');
INSERT INTO `mc_chat_msg` VALUES ('297ec72c714d309401714d85b6210001', '297ec72c7100072001710010c6d80000', '李大爷', '297ec72c711aefbb01711af13e220000', '刘老师', 'hello\n', 1, '2020-04-06 11:25:41', '');
INSERT INTO `mc_chat_msg` VALUES ('297ec72c714d309401714d85e8b70002', '297ec72c711aefbb01711af13e220000', '刘老师', '297ec72c7100072001710010c6d80000', '李大爷', '我现在开始聊天吧\n', 1, '2020-04-06 11:25:54', '');

-- ----------------------------
-- Table structure for mc_message
-- ----------------------------
DROP TABLE IF EXISTS `mc_message`;
CREATE TABLE `mc_message`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `receiver` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接受者:0-通知所有用户、其他数字通知单用户',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型:message-消息通知,invite-邀请消息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_message
-- ----------------------------
INSERT INTO `mc_message` VALUES ('297ec72c712b8dcd01712b8ff6660000', '测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！', '0', 'message', '2020-03-30 21:09:47', '2020-03-30 21:09:47');
INSERT INTO `mc_message` VALUES ('297ec72c712b8dcd01712b919f4d0001', '测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！测试公告！！！！！！', '297ec72c711aefbb01711af13e220000', 'message', '2020-03-30 21:11:36', '2020-03-30 21:11:36');

-- ----------------------------
-- Table structure for mc_user_message
-- ----------------------------
DROP TABLE IF EXISTS `mc_user_message`;
CREATE TABLE `mc_user_message`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户、会员id',
  `message_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息id',
  `read_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '阅读状态：0-未读，1-已读',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
