/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : mc_user

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/04/2020 22:25:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mc_company
-- ----------------------------
DROP TABLE IF EXISTS `mc_company`;
CREATE TABLE `mc_company`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `linkname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人名称',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `intro` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `logo` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logo',
  `identitypic` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证照片',
  `worktype` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工具性质',
  `businesspic` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '营业执照',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_company
-- ----------------------------
INSERT INTO `mc_company` VALUES ('297ec72c710172200171017561c60000', '华软搬运工', '阿进', '18973456789', '17486348789@qq.com', '广州大学华软软件学院（简称华软学院）成立于2002年，2006年经国家教育部批准为实施本科层次教育的全日制普通高等学校，是全国300多家独立学院中以IT人才培养为特色的唯一一所软件学院，是国家发展和改革委员会国际合作中心合作示范学院', 'group1/M00/00/00/rBg6yF53KEqABHyLAAANUQ9gZm0722.jpg', NULL, NULL, NULL, '1');

-- ----------------------------
-- Table structure for mc_company_user
-- ----------------------------
DROP TABLE IF EXISTS `mc_company_user`;
CREATE TABLE `mc_company_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xc_company_user_unique`(`company_id`, `user_id`) USING BTREE,
  INDEX `FK_xc_company_user_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_company_user
-- ----------------------------
INSERT INTO `mc_company_user` VALUES ('297ec72c7105754501710577e8af0001', '297ec72c710172200171017561c60000', '297ec72c7100072001710010c6d80000', '1');
INSERT INTO `mc_company_user` VALUES ('297ec72c714840bc0171489fa4c60001', '297ec72c710172200171017561c60000', '297ec72c711aefbb01711af13e220000', '1');

-- ----------------------------
-- Table structure for mc_menu
-- ----------------------------
DROP TABLE IF EXISTS `mc_menu`;
CREATE TABLE `mc_menu`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `p_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父菜单ID',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `is_menu` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否是菜单',
  `level` int(11) NOT NULL COMMENT '菜单层级',
  `sort` int(11) NULL DEFAULT NULL COMMENT '菜单排序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态 1-启用 0-停用',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `FK_CODE`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_menu
-- ----------------------------
INSERT INTO `mc_menu` VALUES ('000000000000000000', '0', 'root', '系统根目录', NULL, '0', 0, 1, '1', NULL, '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('111111111111111111', '000000000000000000', 'mc_sysmanager', '系统管理', NULL, '1', 1, 10, '1', '', '2020-03-18 14:26:00', '2020-03-19 13:11:48');
INSERT INTO `mc_menu` VALUES ('222222222222222222', '000000000000000000', 'mc_teachmanager', '教学管理', NULL, '1', 1, 2, '1', NULL, '2020-03-18 14:26:00', '2020-04-05 22:01:23');
INSERT INTO `mc_menu` VALUES ('893288715881807872', '111111111111111111', 'mc_sysmanager_user', '用户管理', NULL, '1', 2, 1, '1', '', '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('893304960282787840', '893288715881807872', 'mc_sysmanager_user_add', '添加用户', NULL, '1', 3, 1, '1', '', '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('894396523532517376', '893288715881807872', 'mc_sysmanager_user_edit', '用户修改', NULL, '0', 3, 1, '1', '', '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('894473486712438784', '893288715881807872', 'mc_sysmanager_user_view', '用户列表', NULL, '1', 3, 2, '1', '', '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('894473651837992960', '893288715881807872', 'mc_sysmanager_user_delete', '用户删除', NULL, '0', 3, 4, '1', '', '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('894477851082883072', '111111111111111111', 'mc_sysmanager_doc', '文档查询', NULL, '1', 2, 9, '1', '', '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('903459378655395840', '893288715881807872', 'mc_sysmanager_user_resetpwd', '密码重置', NULL, '1', 3, 2, '1', '', '2020-03-18 14:26:00', '2020-03-18 14:26:00');
INSERT INTO `mc_menu` VALUES ('903459378655395841', '222222222222222222', 'mc_teachmanager_course', '课程管理', NULL, '1', 2, 1, '1', NULL, '2020-03-18 14:26:00', '2020-03-19 10:20:57');
INSERT INTO `mc_menu` VALUES ('903459378655395842', '903459378655395841', 'mc_teachmanager_course_add', '添加课程', NULL, '1', 3, 1, '1', NULL, '2020-03-18 14:26:00', '2020-03-19 10:20:55');
INSERT INTO `mc_menu` VALUES ('903459378655395843', '903459378655395841', 'mc_teachmanager_course_del', '删除课程', NULL, '0', 3, NULL, '1', NULL, '2020-03-18 14:26:00', '2020-03-18 20:08:57');
INSERT INTO `mc_menu` VALUES ('903459378655395845', '903459378655395841', 'mc_teachmanager_course_market', '编辑课程营销信息', NULL, '0', 3, NULL, '1', NULL, '2020-03-18 14:26:00', '2020-03-18 20:08:57');
INSERT INTO `mc_menu` VALUES ('903459378655395846', '903459378655395841', 'mc_teachmanager_course_base', '编辑课程基础信息', NULL, '0', 3, NULL, '1', NULL, '2020-03-18 14:26:00', '2020-03-18 20:08:58');
INSERT INTO `mc_menu` VALUES ('903459378655395847', '903459378655395841', 'mc_teachmanager_course_plan', '编辑课程计划', NULL, '0', 3, NULL, '1', NULL, '2020-03-18 14:26:00', '2020-03-18 20:08:58');
INSERT INTO `mc_menu` VALUES ('903459378655395848', '903459378655395841', 'mc_teachmanager_course_publish', '发布课程', NULL, '0', 3, NULL, '1', NULL, '2020-03-18 14:26:00', '2020-03-18 20:08:59');
INSERT INTO `mc_menu` VALUES ('903459378655395849', '903459378655395841', 'mc_teachmanager_course_list', '我的课程', NULL, '0', 2, NULL, '1', NULL, '2020-03-18 14:26:00', '2020-03-18 20:08:59');

-- ----------------------------
-- Table structure for mc_permission
-- ----------------------------
DROP TABLE IF EXISTS `mc_permission`;
CREATE TABLE `mc_permission`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xu_permission_unique`(`role_id`, `menu_id`) USING BTREE,
  INDEX `fk_xc_permission_menu_id`(`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_permission
-- ----------------------------
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3f981610000', '297ec72c70e32baf0170e32bfa960000', '893288715881807872', '2020-03-16 23:32:25');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3f981690001', '297ec72c70e32baf0170e32bfa960000', '893304960282787840', '2020-03-16 23:32:25');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3f9816a0002', '297ec72c70e32baf0170e32bfa960000', '894396523532517376', '2020-03-16 23:32:25');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3f9816a0003', '297ec72c70e32baf0170e32bfa960000', '894473486712438784', '2020-03-16 23:32:25');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3f9816a0004', '297ec72c70e32baf0170e32bfa960000', '894473651837992960', '2020-03-16 23:32:25');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3f9816a0005', '297ec72c70e32baf0170e32bfa960000', '903459378655395840', '2020-03-16 23:32:25');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca20054', '297ec72c70e32baf0170e3309586002a', '222222222222222222', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca30055', '297ec72c70e32baf0170e3309586002a', '903459378655395841', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca30056', '297ec72c70e32baf0170e3309586002a', '903459378655395842', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca30057', '297ec72c70e32baf0170e3309586002a', '903459378655395843', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca30058', '297ec72c70e32baf0170e3309586002a', '903459378655395845', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca30059', '297ec72c70e32baf0170e3309586002a', '903459378655395846', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca3005a', '297ec72c70e32baf0170e3309586002a', '903459378655395847', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca3005b', '297ec72c70e32baf0170e3309586002a', '903459378655395848', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca3005c', '297ec72c70e32baf0170e3309586002a', '903459378655395849', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70e3f7750170e3fb0ca3005d', '297ec72c70e32baf0170e3309586002a', '903459378655395850', '2020-03-16 23:34:06');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb60003', '297ec72c70f08c230170f09761540000', '222222222222222222', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb60004', '297ec72c70f08c230170f09761540000', '903459378655395841', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb60005', '297ec72c70f08c230170f09761540000', '903459378655395842', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb60006', '297ec72c70f08c230170f09761540000', '903459378655395843', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb70007', '297ec72c70f08c230170f09761540000', '903459378655395845', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb70008', '297ec72c70f08c230170f09761540000', '903459378655395846', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb70009', '297ec72c70f08c230170f09761540000', '903459378655395847', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb7000a', '297ec72c70f08c230170f09761540000', '903459378655395848', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb7000b', '297ec72c70f08c230170f09761540000', '903459378655395849', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c70f08c230170f0985bb7000c', '297ec72c70f08c230170f09761540000', '903459378655395850', '2020-03-19 10:21:22');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb5f0019', '297ec72c714300570171433ca0dd0004', '222222222222222222', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb5f001a', '297ec72c714300570171433ca0dd0004', '903459378655395841', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb5f001b', '297ec72c714300570171433ca0dd0004', '903459378655395842', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb5f001c', '297ec72c714300570171433ca0dd0004', '903459378655395843', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb60001d', '297ec72c714300570171433ca0dd0004', '903459378655395845', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb60001e', '297ec72c714300570171433ca0dd0004', '903459378655395846', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb60001f', '297ec72c714300570171433ca0dd0004', '903459378655395847', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb600020', '297ec72c714300570171433ca0dd0004', '903459378655395848', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb600021', '297ec72c714300570171433ca0dd0004', '903459378655395849', '2020-04-04 11:29:54');
INSERT INTO `mc_permission` VALUES ('297ec72c714300570171433cdb600022', '297ec72c714300570171433ca0dd0004', '903459378655395850', '2020-04-04 11:29:54');

-- ----------------------------
-- Table structure for mc_role
-- ----------------------------
DROP TABLE IF EXISTS `mc_role`;
CREATE TABLE `mc_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编号',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户状态  103001-正常 103002-暂停 103003-注销',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_role_name`(`role_name`) USING BTREE,
  UNIQUE INDEX `unique_role_value`(`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_role
-- ----------------------------
INSERT INTO `mc_role` VALUES ('297ec72c70e32baf0170e32bfa960000', '超级管理员', 'super', '拥有系统管理员', '2020-03-16 19:47:55', '2020-03-29 15:49:33', '1');
INSERT INTO `mc_role` VALUES ('297ec72c70e32baf0170e32de16f001f', '管理员', 'admin', '网站的管理员，拥有除授权模块以外的所有权限', '2020-03-16 19:50:00', '2020-03-18 20:08:23', '1');
INSERT INTO `mc_role` VALUES ('297ec72c70e32baf0170e3309586002a', '教学管理员', 'teachermanage', '教学平台的资源的提供者，拥有教学模块的权限', '2020-03-16 19:52:57', '2020-03-16 23:34:06', '1');
INSERT INTO `mc_role` VALUES ('297ec72c70f08c230170f09761540000', '教师', 'teacher', '负责授课的角色', '2020-03-19 10:20:18', '2020-03-19 10:21:22', '1');
INSERT INTO `mc_role` VALUES ('297ec72c714300570171433ca0dd0004', '学生', 'student', '网站的普通用户，没有后台权限', '2020-04-04 11:29:39', '2020-04-04 11:29:54', '1');

-- ----------------------------
-- Table structure for mc_user
-- ----------------------------
DROP TABLE IF EXISTS `mc_user`;
CREATE TABLE `mc_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(96) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `userpic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `utype` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户类型 101001-学生 101002-教学管理员 101003-系统管理员',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'qq号码',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户状态  103001-正常 103002-暂停 103003-注销',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_user_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_user
-- ----------------------------
INSERT INTO `mc_user` VALUES ('297ec72c7100072001710010c6d80000', 'aa123456', '$2a$10$rkWnjdVYwltmp/o3HMzrr.wUJjT/W7FlcVOvP4t1riFMCoHyhhU2y', NULL, '李大爷', 'group1/M00/00/00/rBg6yF58b9qAHcgxAAAYeKzFDQ8184.jpg', '101003', '1997-05-09 00:00:00', '1', '523651128@qq.com', '13542816968', '52536546879', '1', '2020-03-22 00:00:00', '2020-04-04 11:26:56');
INSERT INTO `mc_user` VALUES ('297ec72c711aefbb01711af13e220000', 'bb123456', '$2a$10$LK7k8Z5WMck9TDLjUQdyO.rSyG9cFDNHELXHDS7zd33xzewS8fD0W', NULL, '刘老师', 'group1/M00/00/00/rBg6yF59roCAEs2fAAANUQ9gZm0865.jpg', '101002', '1995-06-30 00:00:00', '1', '523651128@qq.com', '13646979984', '5468792345', '1', '2020-03-27 00:00:00', '2020-04-04 11:27:29');
INSERT INTO `mc_user` VALUES ('297ec72c713394ae017134ad45720000', 'cc123456', '$2a$10$uomsF7XJj0H.o.RXHmQTCuovcbwHEMuSWVA1kirSagA1WAuWCj9ku', NULL, '王五', 'group1/M00/00/00/rBg6yF6ES0mAVK-XAAEB6hFNSH0824.jpg', '101001', '1995-07-12 00:00:00', '1', '523651128@qq.com', '15638744348', '4179612368', '1', '2020-04-01 00:00:00', '2020-04-04 11:30:40');
INSERT INTO `mc_user` VALUES ('297ec72c714840bc0171485f6b180000', 'dd123456', '$2a$10$y.vvVlb/4LFkSB55oonoPO8Musuk7CyB5OMML7vgC.jH4aU0j/MA.', '123456', '张三', NULL, '101002', '2020-04-05 00:00:00', '1', '7984561123@qq.com', '15632184313', NULL, '1', '2020-04-05 00:00:00', '2020-04-05 20:42:25');

-- ----------------------------
-- Table structure for mc_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mc_user_role`;
CREATE TABLE `mc_user_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_xc_user_role_user_id`(`user_id`) USING BTREE,
  INDEX `fk_xc_user_role_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `fk_xc_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `mc_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_xc_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `mc_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mc_user_role
-- ----------------------------
INSERT INTO `mc_user_role` VALUES ('297ec72c714300570171433a23b80001', '297ec72c7100072001710010c6d80000', '297ec72c70e32baf0170e32bfa960000', '2020-04-04 11:26:56');
INSERT INTO `mc_user_role` VALUES ('297ec72c714300570171433aa2fb0002', '297ec72c711aefbb01711af13e220000', '297ec72c70e32baf0170e3309586002a', '2020-04-04 11:27:29');
INSERT INTO `mc_user_role` VALUES ('297ec72c714300570171433d8e200023', '297ec72c713394ae017134ad45720000', '297ec72c714300570171433ca0dd0004', '2020-04-04 11:30:40');
INSERT INTO `mc_user_role` VALUES ('297ec72c714840bc01714a5d11430002', '297ec72c714840bc0171485f6b180000', '297ec72c70f08c230170f09761540000', '2020-04-05 20:42:25');

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clientId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expiresAt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `lastModifiedAt` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('app', NULL, 'app', 'app', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('XcWebApp', NULL, '$2a$10$9bEpZ/hWRQxyr5hn5wHUj.jxFpIrnOmBcWlE/g/0Zp3uNxt9QTh/S', 'app', 'authorization_code,password,refresh_token,client_credentials', NULL, NULL, 43200, 43200, NULL, NULL);

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------
INSERT INTO `oauth_refresh_token` VALUES ('b96e057b4e1c4920428e833db48d4c15', 0xEFBFBDEFBFBD00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FEFBFBD4763EFBFBDEFBFBDC9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73EFBFBD0E0A6354EFBFBD5E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002462303132643438372D613930382D343361662D613865662D3533633533393963386264367372000E6A6176612E7574696C2E44617465686AEFBFBD014B59741903000078707708000001613B74C98E78, 0xEFBFBDEFBFBD0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EEFBFBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374EFBFBD0F2531EFBFBDEFBFBD100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E194200EFBFBDEFBFBD5EEFBFBD1E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C69737478EFBFBDEFBFBD1DEFBFBDEFBFBD61EFBFBD03000149000473697A65787000000009770400000009737200426F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E617574686F726974792E53696D706C654772616E746564417574686F7269747900000000000001EFBFBD0200014C0004726F6C657400124C6A6176612F6C616E672F537472696E673B787074000A524F4C455F61646D696E7371007E000D740006617069646F637371007E000D74000C64617461626173652F6C6F677371007E000D74000673797374656D7371007E000D740008757365722F6164647371007E000D74000B757365722F64656C6574657371007E000D740009757365722F656469747371007E000D740009757365722F766965777371007E000D740008757365724C6973747871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B726564697265637455726971007E000E4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0024787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EEFBFBD7169EFBFBD0200034C0008636C69656E74496471007E000E4C001172657175657374506172616D657465727371007E00224C000573636F706571007E00247870740006776562417070737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170EFBFBDEFBFBD74EFBFBD07420200014C00016D71007E00227870737200116A6176612E7574696C2E486173684D61700507EFBFBDEFBFBDEFBFBD1660EFBFBD03000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000037708000000040000000274000A6772616E745F7479706574000870617373776F7264740008757365726E616D6574000561646D696E78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574EFBFBD1DEFBFBDD18FEFBFBDEFBFBD550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574EFBFBD6CEFBFBD5AEFBFBDEFBFBD2A1E020000787200116A6176612E7574696C2E48617368536574EFBFBD44EFBFBDEFBFBDEFBFBDEFBFBDEFBFBD340300007870770C000000103F4000000000000174000361707078017371007E0033770C000000103F40000000000000787371007E002A3F40000000000000770800000010000000007870707371007E0033770C000000103F40000000000000787371007E0033770C000000103F40000000000000787372004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000001EFBFBD0200024C000B63726564656E7469616C7371007E00054C00097072696E636970616C71007E00057871007E0003017371007E00077371007E000B0000000977040000000971007E000F71007E001171007E001371007E001571007E001771007E001971007E001B71007E001D71007E001F7871007E003D737200176A6176612E7574696C2E4C696E6B6564486173684D617034EFBFBD4E5C106CEFBFBDEFBFBD0200015A000B6163636573734F726465727871007E002A3F400000000000067708000000080000000271007E002C71007E002D71007E002E71007E002F780070737200326F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E7573657264657461696C732E5573657200000000000001EFBFBD0200075A00116163636F756E744E6F6E457870697265645A00106163636F756E744E6F6E4C6F636B65645A001563726564656E7469616C734E6F6E457870697265645A0007656E61626C65644C000B617574686F72697469657371007E00244C000870617373776F726471007E000E4C0008757365726E616D6571007E000E7870010101017371007E0030737200116A6176612E7574696C2E54726565536574DD9850EFBFBDEFBFBDEFBFBD5B0300007870737200466F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E7573657264657461696C732E5573657224417574686F72697479436F6D70617261746F7200000000000001EFBFBD020000787077040000000971007E000F71007E001171007E001371007E001571007E001771007E001971007E001B71007E001D71007E001F787074000561646D696E);

SET FOREIGN_KEY_CHECKS = 1;
