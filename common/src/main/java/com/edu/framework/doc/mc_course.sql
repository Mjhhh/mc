/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : mc_course

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/04/2020 22:25:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `label` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类标签默认和名称一样',
  `parentid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父结点id',
  `isshow` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否显示',
  `orderby` int(4) NULL DEFAULT NULL COMMENT '排序字段',
  `isleaf` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否叶子',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '根结点', '根结点', '0', '1', 1, '0');
INSERT INTO `category` VALUES ('1-1', '前端开发', '前端开发', '1', '1', 1, '0');
INSERT INTO `category` VALUES ('1-1-1', 'HTML/CSS', 'HTML/CSS', '1-1', '1', 1, '1');
INSERT INTO `category` VALUES ('1-1-10', '其它', '其它', '1-1', '1', 10, '1');
INSERT INTO `category` VALUES ('1-1-2', 'JavaScript', 'JavaScript', '1-1', '1', 2, '1');
INSERT INTO `category` VALUES ('1-1-3', 'jQuery', 'jQuery', '1-1', '1', 3, '1');
INSERT INTO `category` VALUES ('1-1-4', 'ExtJS', 'ExtJS', '1-1', '1', 4, '1');
INSERT INTO `category` VALUES ('1-1-5', 'AngularJS', 'AngularJS', '1-1', '1', 5, '1');
INSERT INTO `category` VALUES ('1-1-6', 'ReactJS', 'ReactJS', '1-1', '1', 6, '1');
INSERT INTO `category` VALUES ('1-1-7', 'Bootstrap', 'Bootstrap', '1-1', '1', 7, '1');
INSERT INTO `category` VALUES ('1-1-8', 'Node.js', 'Node.js', '1-1', '1', 8, '1');
INSERT INTO `category` VALUES ('1-1-9', 'Vue', 'Vue', '1-1', '1', 9, '1');
INSERT INTO `category` VALUES ('1-10', '研发管理', '研发管理', '1', '1', 10, '0');
INSERT INTO `category` VALUES ('1-10-1', '敏捷开发', '敏捷开发', '1-10', '1', 1, '1');
INSERT INTO `category` VALUES ('1-10-2', '软件设计', '软件设计', '1-10', '1', 2, '1');
INSERT INTO `category` VALUES ('1-10-3', '软件测试', '软件测试', '1-10', '1', 3, '1');
INSERT INTO `category` VALUES ('1-10-4', '研发管理', '研发管理', '1-10', '1', 4, '1');
INSERT INTO `category` VALUES ('1-10-5', '其它', '其它', '1-10', '1', 5, '1');
INSERT INTO `category` VALUES ('1-11', '系统运维', '系统运维', '1', '1', 11, '0');
INSERT INTO `category` VALUES ('1-11-1', 'Linux', 'Linux', '1-11', '1', 1, '1');
INSERT INTO `category` VALUES ('1-11-10', '其它', '其它', '1-11', '1', 10, '1');
INSERT INTO `category` VALUES ('1-11-2', 'Windows', 'Windows', '1-11', '1', 2, '1');
INSERT INTO `category` VALUES ('1-11-3', 'UNIX', 'UNIX', '1-11', '1', 3, '1');
INSERT INTO `category` VALUES ('1-11-4', 'Mac OS', 'Mac OS', '1-11', '1', 4, '1');
INSERT INTO `category` VALUES ('1-11-5', '网络技术', '网络技术', '1-11', '1', 5, '1');
INSERT INTO `category` VALUES ('1-11-6', '路由协议', '路由协议', '1-11', '1', 6, '1');
INSERT INTO `category` VALUES ('1-11-7', '无线网络', '无线网络', '1-11', '1', 7, '1');
INSERT INTO `category` VALUES ('1-11-8', 'Ngnix', 'Ngnix', '1-11', '1', 8, '1');
INSERT INTO `category` VALUES ('1-11-9', '邮件服务器', '邮件服务器', '1-11', '1', 9, '1');
INSERT INTO `category` VALUES ('1-12', '产品经理', '产品经理', '1', '1', 12, '0');
INSERT INTO `category` VALUES ('1-12-1', '交互设计', '交互设计', '1-12', '1', 1, '1');
INSERT INTO `category` VALUES ('1-12-2', '产品设计', '产品设计', '1-12', '1', 2, '1');
INSERT INTO `category` VALUES ('1-12-3', '原型设计', '原型设计', '1-12', '1', 3, '1');
INSERT INTO `category` VALUES ('1-12-4', '用户体验', '用户体验', '1-12', '1', 4, '1');
INSERT INTO `category` VALUES ('1-12-5', '需求分析', '需求分析', '1-12', '1', 5, '1');
INSERT INTO `category` VALUES ('1-12-6', '其它', '其它', '1-12', '1', 6, '1');
INSERT INTO `category` VALUES ('1-13', '企业/办公/职场', '企业/办公/职场', '1', '1', 13, '0');
INSERT INTO `category` VALUES ('1-13-1', '运营管理', '运营管理', '1-13', '1', 1, '1');
INSERT INTO `category` VALUES ('1-13-2', '企业信息化', '企业信息化', '1-13', '1', 2, '1');
INSERT INTO `category` VALUES ('1-13-3', '网络营销', '网络营销', '1-13', '1', 3, '1');
INSERT INTO `category` VALUES ('1-13-4', 'Office/WPS', 'Office/WPS', '1-13', '1', 4, '1');
INSERT INTO `category` VALUES ('1-13-5', '招聘/面试', '招聘/面试', '1-13', '1', 5, '1');
INSERT INTO `category` VALUES ('1-13-6', '电子商务', '电子商务', '1-13', '1', 6, '1');
INSERT INTO `category` VALUES ('1-13-7', 'CRM', 'CRM', '1-13', '1', 7, '1');
INSERT INTO `category` VALUES ('1-13-8', 'ERP', 'ERP', '1-13', '1', 8, '1');
INSERT INTO `category` VALUES ('1-13-9', '其它', '其它', '1-13', '1', 9, '1');
INSERT INTO `category` VALUES ('1-14', '信息安全', '信息安全', '1', '1', 14, '0');
INSERT INTO `category` VALUES ('1-14-1', '密码学/加密/破解', '密码学/加密/破解', '1-14', '1', 1, '1');
INSERT INTO `category` VALUES ('1-14-10', '其它', '其它', '1-14', '1', 10, '1');
INSERT INTO `category` VALUES ('1-14-2', '渗透测试', '渗透测试', '1-14', '1', 2, '1');
INSERT INTO `category` VALUES ('1-14-3', '社会工程', '社会工程', '1-14', '1', 3, '1');
INSERT INTO `category` VALUES ('1-14-4', '漏洞挖掘与利用', '漏洞挖掘与利用', '1-14', '1', 4, '1');
INSERT INTO `category` VALUES ('1-14-5', '云安全', '云安全', '1-14', '1', 5, '1');
INSERT INTO `category` VALUES ('1-14-6', '防护加固', '防护加固', '1-14', '1', 6, '1');
INSERT INTO `category` VALUES ('1-14-7', '代码审计', '代码审计', '1-14', '1', 7, '1');
INSERT INTO `category` VALUES ('1-14-8', '移动安全', '移动安全', '1-14', '1', 8, '1');
INSERT INTO `category` VALUES ('1-14-9', '病毒木马', '病毒木马', '1-14', '1', 9, '1');
INSERT INTO `category` VALUES ('1-2', '移动开发', '移动开发', '1', '1', 2, '0');
INSERT INTO `category` VALUES ('1-2-1', '微信开发', '微信开发', '1-2', '1', 1, '1');
INSERT INTO `category` VALUES ('1-2-2', 'iOS', 'iOS', '1-2', '1', 2, '1');
INSERT INTO `category` VALUES ('1-2-3', '手游开发', '手游开发', '1-2', '1', 3, '1');
INSERT INTO `category` VALUES ('1-2-4', 'Swift', 'Swift', '1-2', '1', 4, '1');
INSERT INTO `category` VALUES ('1-2-5', 'Android', 'Android', '1-2', '1', 5, '1');
INSERT INTO `category` VALUES ('1-2-6', 'ReactNative', 'ReactNative', '1-2', '1', 6, '1');
INSERT INTO `category` VALUES ('1-2-7', 'Cordova', 'Cordova', '1-2', '1', 7, '1');
INSERT INTO `category` VALUES ('1-2-8', '其它', '其它', '1-2', '1', 8, '1');
INSERT INTO `category` VALUES ('1-3', '编程开发', '编程开发', '1', '1', 3, '0');
INSERT INTO `category` VALUES ('1-3-1', 'C/C++', 'C/C++', '1-3', '1', 1, '1');
INSERT INTO `category` VALUES ('1-3-2', 'Java', 'Java', '1-3', '1', 2, '1');
INSERT INTO `category` VALUES ('1-3-3', '.NET', '.NET', '1-3', '1', 3, '1');
INSERT INTO `category` VALUES ('1-3-4', 'Objective-C', 'Objective-C', '1-3', '1', 4, '1');
INSERT INTO `category` VALUES ('1-3-5', 'Go语言', 'Go语言', '1-3', '1', 5, '1');
INSERT INTO `category` VALUES ('1-3-6', 'Python', 'Python', '1-3', '1', 6, '1');
INSERT INTO `category` VALUES ('1-3-7', 'Ruby/Rails', 'Ruby/Rails', '1-3', '1', 7, '1');
INSERT INTO `category` VALUES ('1-3-8', '其它', '其它', '1-3', '1', 8, '1');
INSERT INTO `category` VALUES ('1-4', '数据库', '数据库', '1', '1', 4, '0');
INSERT INTO `category` VALUES ('1-4-1', 'Oracle', 'Oracle', '1-4', '1', 1, '1');
INSERT INTO `category` VALUES ('1-4-2', 'MySQL', 'MySQL', '1-4', '1', 2, '1');
INSERT INTO `category` VALUES ('1-4-3', 'SQL Server', 'SQL Server', '1-4', '1', 3, '1');
INSERT INTO `category` VALUES ('1-4-4', 'DB2', 'DB2', '1-4', '1', 4, '1');
INSERT INTO `category` VALUES ('1-4-5', 'NoSQL', 'NoSQL', '1-4', '1', 5, '1');
INSERT INTO `category` VALUES ('1-4-6', 'Mongo DB', 'Mongo DB', '1-4', '1', 6, '1');
INSERT INTO `category` VALUES ('1-4-7', 'Hbase', 'Hbase', '1-4', '1', 7, '1');
INSERT INTO `category` VALUES ('1-4-8', '数据仓库', '数据仓库', '1-4', '1', 8, '1');
INSERT INTO `category` VALUES ('1-4-9', '其它', '其它', '1-4', '1', 9, '1');
INSERT INTO `category` VALUES ('1-5', '人工智能', '人工智能', '1', '1', 5, '0');
INSERT INTO `category` VALUES ('1-5-1', '机器学习', '机器学习', '1-5', '1', 1, '1');
INSERT INTO `category` VALUES ('1-5-2', '深度学习', '深度学习', '1-5', '1', 2, '1');
INSERT INTO `category` VALUES ('1-5-3', '语音识别', '语音识别', '1-5', '1', 3, '1');
INSERT INTO `category` VALUES ('1-5-4', '计算机视觉', '计算机视觉', '1-5', '1', 4, '1');
INSERT INTO `category` VALUES ('1-5-5', 'NLP', 'NLP', '1-5', '1', 5, '1');
INSERT INTO `category` VALUES ('1-5-6', '强化学习', '强化学习', '1-5', '1', 6, '1');
INSERT INTO `category` VALUES ('1-5-7', '其它', '其它', '1-5', '1', 7, '1');
INSERT INTO `category` VALUES ('1-6', '云计算/大数据', '云计算/大数据', '1', '1', 6, '0');
INSERT INTO `category` VALUES ('1-6-1', 'Spark', 'Spark', '1-6', '1', 1, '1');
INSERT INTO `category` VALUES ('1-6-2', 'Hadoop', 'Hadoop', '1-6', '1', 2, '1');
INSERT INTO `category` VALUES ('1-6-3', 'OpenStack', 'OpenStack', '1-6', '1', 3, '1');
INSERT INTO `category` VALUES ('1-6-4', 'Docker/K8S', 'Docker/K8S', '1-6', '1', 4, '1');
INSERT INTO `category` VALUES ('1-6-5', '云计算基础架构', '云计算基础架构', '1-6', '1', 5, '1');
INSERT INTO `category` VALUES ('1-6-6', '虚拟化技术', '虚拟化技术', '1-6', '1', 6, '1');
INSERT INTO `category` VALUES ('1-6-7', '云平台', '云平台', '1-6', '1', 7, '1');
INSERT INTO `category` VALUES ('1-6-8', 'ELK', 'ELK', '1-6', '1', 8, '1');
INSERT INTO `category` VALUES ('1-6-9', '其它', '其它', '1-6', '1', 9, '1');
INSERT INTO `category` VALUES ('1-7', 'UI设计', 'UI设计', '1', '1', 7, '0');
INSERT INTO `category` VALUES ('1-7-1', 'Photoshop', 'Photoshop', '1-7', '1', 1, '1');
INSERT INTO `category` VALUES ('1-7-10', 'InDesign', 'InDesign', '1-7', '1', 10, '1');
INSERT INTO `category` VALUES ('1-7-11', 'Pro/Engineer', 'Pro/Engineer', '1-7', '1', 11, '1');
INSERT INTO `category` VALUES ('1-7-12', 'Cinema 4D', 'Cinema 4D', '1-7', '1', 12, '1');
INSERT INTO `category` VALUES ('1-7-13', '3D Studio', '3D Studio', '1-7', '1', 13, '1');
INSERT INTO `category` VALUES ('1-7-14', 'After Effects（AE）', 'After Effects（AE）', '1-7', '1', 14, '1');
INSERT INTO `category` VALUES ('1-7-15', '原画设计', '原画设计', '1-7', '1', 15, '1');
INSERT INTO `category` VALUES ('1-7-16', '动画制作', '动画制作', '1-7', '1', 16, '1');
INSERT INTO `category` VALUES ('1-7-17', 'Dreamweaver', 'Dreamweaver', '1-7', '1', 17, '1');
INSERT INTO `category` VALUES ('1-7-18', 'Axure', 'Axure', '1-7', '1', 18, '1');
INSERT INTO `category` VALUES ('1-7-19', '其它', '其它', '1-7', '1', 19, '1');
INSERT INTO `category` VALUES ('1-7-2', '3Dmax', '3Dmax', '1-7', '1', 2, '1');
INSERT INTO `category` VALUES ('1-7-3', 'Illustrator', 'Illustrator', '1-7', '1', 3, '1');
INSERT INTO `category` VALUES ('1-7-4', 'Flash', 'Flash', '1-7', '1', 4, '1');
INSERT INTO `category` VALUES ('1-7-5', 'Maya', 'Maya', '1-7', '1', 5, '1');
INSERT INTO `category` VALUES ('1-7-6', 'AUTOCAD', 'AUTOCAD', '1-7', '1', 6, '1');
INSERT INTO `category` VALUES ('1-7-7', 'UG', 'UG', '1-7', '1', 7, '1');
INSERT INTO `category` VALUES ('1-7-8', 'SolidWorks', 'SolidWorks', '1-7', '1', 8, '1');
INSERT INTO `category` VALUES ('1-7-9', 'CorelDraw', 'CorelDraw', '1-7', '1', 9, '1');
INSERT INTO `category` VALUES ('1-8', '游戏开发', '游戏开发', '1', '1', 8, '0');
INSERT INTO `category` VALUES ('1-8-1', 'Cocos', 'Cocos', '1-8', '1', 1, '1');
INSERT INTO `category` VALUES ('1-8-2', 'Unity3D', 'Unity3D', '1-8', '1', 2, '1');
INSERT INTO `category` VALUES ('1-8-3', 'Flash', 'Flash', '1-8', '1', 3, '1');
INSERT INTO `category` VALUES ('1-8-4', 'SpriteKit 2D', 'SpriteKit 2D', '1-8', '1', 4, '1');
INSERT INTO `category` VALUES ('1-8-5', 'Unreal', 'Unreal', '1-8', '1', 5, '1');
INSERT INTO `category` VALUES ('1-8-6', '其它', '其它', '1-8', '1', 6, '1');
INSERT INTO `category` VALUES ('1-9', '智能硬件/物联网', '智能硬件/物联网', '1', '1', 9, '0');
INSERT INTO `category` VALUES ('1-9-1', '无线通信', '无线通信', '1-9', '1', 1, '1');
INSERT INTO `category` VALUES ('1-9-10', '物联网技术', '物联网技术', '1-9', '1', 10, '1');
INSERT INTO `category` VALUES ('1-9-11', '其它', '其它', '1-9', '1', 11, '1');
INSERT INTO `category` VALUES ('1-9-2', '电子工程', '电子工程', '1-9', '1', 2, '1');
INSERT INTO `category` VALUES ('1-9-3', 'Arduino', 'Arduino', '1-9', '1', 3, '1');
INSERT INTO `category` VALUES ('1-9-4', '体感技术', '体感技术', '1-9', '1', 4, '1');
INSERT INTO `category` VALUES ('1-9-5', '智能硬件', '智能硬件', '1-9', '1', 5, '1');
INSERT INTO `category` VALUES ('1-9-6', '驱动/内核开发', '驱动/内核开发', '1-9', '1', 6, '1');
INSERT INTO `category` VALUES ('1-9-7', '单片机/工控', '单片机/工控', '1-9', '1', 7, '1');
INSERT INTO `category` VALUES ('1-9-8', 'WinCE', 'WinCE', '1-9', '1', 8, '1');
INSERT INTO `category` VALUES ('1-9-9', '嵌入式', '嵌入式', '1-9', '1', 9, '1');

-- ----------------------------
-- Table structure for course_answer
-- ----------------------------
DROP TABLE IF EXISTS `course_answer`;
CREATE TABLE `course_answer`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `question_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题主键',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户主键',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_answer
-- ----------------------------
INSERT INTO `course_answer` VALUES ('297ec72c7152709a01715273978c0006', '297ec72c7152709a01715271f34c0000', '123', 'string', '2020-04-07 10:23:59');
INSERT INTO `course_answer` VALUES ('297ec72c7152709a0171527399140007', '297ec72c7152709a01715271f34c0000', '123', 'string', '2020-04-07 10:24:00');
INSERT INTO `course_answer` VALUES ('297ec72c71549c0b017154ae355e0001', '297ec72c71549c0b017154ae26300000', '297ec72c714840bc0171485f6b180000', 'asdasdasda', '2020-04-07 20:47:15');
INSERT INTO `course_answer` VALUES ('297ec72c71549c0b017154ae66410002', '297ec72c71549c0b017154ae26300000', '297ec72c711aefbb01711af13e220000', 'dsada', '2020-04-07 20:47:28');

-- ----------------------------
-- Table structure for course_base
-- ----------------------------
DROP TABLE IF EXISTS `course_base`;
CREATE TABLE `course_base`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '适用人群',
  `mt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程大分类',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `studymodel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习模式',
  `teachmode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授课模式',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程介绍',
  `st` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程小分类',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程状态',
  `company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教育机构',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_base
-- ----------------------------
INSERT INTO `course_base` VALUES ('297ec72c70fba29f0170fbbe79980002', 'Jmeter 接口自动化脚本结构进阶', '有一定Jmeter使用经验和Java编程基础', '1-3', '200002', '201001', NULL, '结合实际项目展示Jmeter在接口自动化测试中的应用，详细展示Jmeter脚本结构优化方案，并针对这套脚本结构方案中的各个模块的设计思路及实现方式进行详细讲解，切实有效提升通过Jmeter进行接口自动化的能力和效率。', '1-3-2', '202002', '297ec72c710172200171017561c60000', NULL);
INSERT INTO `course_base` VALUES ('297ec72c7126792d017126a9bfc1000c', '二进制与Java中的基本数据类型', '需要了解Java的基本语法。', '1-3', '200001', '201001', NULL, '我们都知道，计算机中都是使用2进制进行存储的。学习好二进制，对理解计算机如何处理数据以及Java中的数据类型在计算机中如何存储，都有实质性的帮助。本课程将从二进制的历史开始讲解位值制计数法、进制转换、小数的二进制化等二进制相关知识。并由此拓展介绍了Java中的整型、浮点型以及字符型。在这里还可以看到BigInteger、BigDecimal如何使用、ASCII与unicode关系等', '1-3-2', '202002', '297ec72c710172200171017561c60000', NULL);
INSERT INTO `course_base` VALUES ('297ec72c7140af6d017140e21f2d0000', 'Python3入门人工智能 掌握机器学习+深度学习', '只要你对人工智能感兴趣，想在这个领域发展，或是数据分析从业者\n（商业、金融行业等），希望掌握AI这个工具，那本课程非常适合你', '1-3', '200001', '201001', NULL, 'Flare老师帮你全面梳理人工智能核心知识，使用流行的Python3语言手把手带你完成AI实战项目，课程囊括机器学习与深度学习，监督与无监督学习，独有综合多项技术的混合算法，为你学习AI打下扎实基础。课程采用sklearn与keras框架（底层调用tensorflow），针对模型优化、数据分析与预处理展开详细讲解，帮你实现能力的全面提升。课程大项目综合数据增强、降维、分离，图像识别，机器与深度学习，监督与无监督学习，让你使用AI工具得心应手。讲师赵辛为福布斯中国U30科技上榜者，孔雀人才，全奖海归博士', '1-3-6', '202002', '297ec72c710172200171017561c60000', NULL);
INSERT INTO `course_base` VALUES ('297ec72c7148403d01714869bf900000', 'vuex基础入门', '具备一定的vue基础', '1-1', '200002', '201001', NULL, 'vuex的基本原理和基本使用以及vuex的组成', '1-1-9', '202002', '297ec72c710172200171017561c60000', NULL);
INSERT INTO `course_base` VALUES ('297ec72c7148403d0171487b20090007', '玩转Bootstrap（基础）', '本教程适合具有一定前端基础的人员，对HTML和CSS有一定的了解，对于定制Bootstrap的同学需要具备LESS和Sass基础知识。', '1-1', '200001', '201001', NULL, '简介：本Bootstrap教程能够让您了解到，Bootstrap框架是一个非常受欢迎的前端开发框架，他能让后端程序员和不懂设计的前端人员制作出优美的Web页面或Web应用程序。在这个Bootstrap教程中，将带领大家了解Bootstrap框架以及如何使用Bootstrap框架，通过本教程学习能够独立定制出适合自己的Bootstrap。', '1-1-7', '202002', '297ec72c710172200171017561c60000', NULL);
INSERT INTO `course_base` VALUES ('297ec72c7148403d017148a075b20011', '玩转MySQL8.0新特性', '1、一定的MySQL 基础知识。\n2、了解基本的数据库操作。', '1-4', '200001', '201001', NULL, '简介：无论是强大的通用表表达式和窗口函数，还是增强的账户安全和方便的账户管理，又或是大量的代码重构，以及由此带来的许多 InnoDB 存储引擎增强，各种查询优化器的改进，以及对NoSQL 的进一步支持，都在提示我们，MySQL 8.0是一个非常具有里程碑意义的版本。好东西当然应该大家一起分享，所以有了这门课程。本课程不只有概念，更注重实战，所有内容均有实际操作，欢迎来看。', '1-4-2', '202002', '297ec72c710172200171017561c60000', NULL);
INSERT INTO `course_base` VALUES ('297ec72c7148403d017148a558360017', 'React Native开发播放器', 'JavaScript基础', '1-1', '200002', '201001', NULL, '简介：本课程使用react native实现一个视频播放器，播放器同时兼容ios和安卓，播放器的主要功能包括调节倍速播放、全屏的设置，分辨率的适配，视频的播放暂停、播放进度的调节，动画的效果制作等,同时构建出一个完整的类似qq的导航框架', '1-1-2', '202002', '297ec72c710172200171017561c60000', NULL);

-- ----------------------------
-- Table structure for course_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `course_evaluate`;
CREATE TABLE `course_evaluate`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `score` double(5, 1) NOT NULL COMMENT '评分',
  `create_time` datetime(0) NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_evaluate
-- ----------------------------
INSERT INTO `course_evaluate` VALUES ('297ec72c711f826601712009d8880019', '297ec72c70fba29f0170fbbe79980002', '297ec72c7100072001710010c6d80000', '很好很强大', 5.0, '2020-03-28 15:27:28');
INSERT INTO `course_evaluate` VALUES ('297ec72c711f82660171200a08cb001a', '297ec72c70fba29f0170fbbe79980002', '297ec72c7100072001710010c6d80000', '讲的不够深入', 3.8, '2020-03-28 15:27:41');
INSERT INTO `course_evaluate` VALUES ('297ec72c711f82660171200aaad3001b', '297ec72c70fba29f0170fbbe79980002', '297ec72c7100072001710010c6d80000', '老师声音太小了，没吃饭？', 3.8, '2020-03-28 15:28:22');
INSERT INTO `course_evaluate` VALUES ('297ec72c71205b360171205d1a2e0000', '297ec72c70fba29f0170fbbe79980002', '297ec72c7100072001710010c6d80000', '路过，打个酱油', 3.9, '2020-03-28 16:58:25');
INSERT INTO `course_evaluate` VALUES ('297ec72c71205b3601712063ad830001', '297ec72c70fba29f0170fbbe79980002', '297ec72c7100072001710010c6d80000', '混入其中', 4.5, '2020-03-28 17:05:36');
INSERT INTO `course_evaluate` VALUES ('297ec72c7152709a0171529752f80008', '297ec72c70fba29f0170fbbe79980002', '297ec72c714840bc0171485f6b180000', 'a\'s\'d', 3.6, '2020-04-07 11:03:01');

-- ----------------------------
-- Table structure for course_market
-- ----------------------------
DROP TABLE IF EXISTS `course_market`;
CREATE TABLE `course_market`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费规则，对应数据字典',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效性，对应数据字典',
  `expires` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '过期时间',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询qq',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `price_old` float(10, 2) NULL DEFAULT NULL COMMENT '原价',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '课程有效期-开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '课程有效期-结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_market
-- ----------------------------
INSERT INTO `course_market` VALUES ('297ec72c70fba29f0170fbbe79980002', '203002', '204001', '2020-03-28 22:29:16', '7894521645', 100.00, 199.00, '2020-03-21 00:00:00', '2021-03-27 00:00:00');
INSERT INTO `course_market` VALUES ('297ec72c7126792d017126a9bfc1000c', '203001', '204002', '2020-03-29 22:27:29', '7893452154', NULL, NULL, '2020-03-28 00:00:00', '2022-03-30 00:00:00');
INSERT INTO `course_market` VALUES ('297ec72c7140af6d017140e21f2d0000', '203002', '204002', '2020-04-04 00:33:21', '35467894654', 399.00, 448.00, '2020-04-03 00:00:00', '2022-03-31 00:00:00');
INSERT INTO `course_market` VALUES ('297ec72c7148403d01714869bf900000', '203001', '204002', '2020-04-05 11:38:07', '213546897', NULL, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297ec72c7148403d0171487b20090007', '203001', '204001', '2020-04-05 11:56:45', '85246789312', NULL, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297ec72c7148403d017148a075b20011', '203001', '204001', '2020-04-05 12:37:34', '967634689', NULL, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297ec72c7148403d017148a558360017', '203002', '204001', '2020-04-05 12:43:06', '124893469', 6.00, 36.00, NULL, NULL);

-- ----------------------------
-- Table structure for course_pic
-- ----------------------------
DROP TABLE IF EXISTS `course_pic`;
CREATE TABLE `course_pic`  (
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
  `pic` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片id',
  PRIMARY KEY (`courseid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_pic
-- ----------------------------
INSERT INTO `course_pic` VALUES ('297ec72c70fba29f0170fbbe79980002', 'group1/M00/00/00/rBg6yF51s5CAaZeiAAQaaN62xyI723.jpg');
INSERT INTO `course_pic` VALUES ('297ec72c7126792d017126a9bfc1000c', 'group1/M00/00/00/rBg6yF6AsDSATBQjAAAOu3TLGs4299.jpg');
INSERT INTO `course_pic` VALUES ('297ec72c7140af6d017140e21f2d0000', 'group1/M00/00/00/rBg6yF6HZTKAU4KjAAAkzP--tAw572.jpg');
INSERT INTO `course_pic` VALUES ('297ec72c7148403d01714869bf900000', 'group1/M00/00/00/rBg6yF6JUo6AQG-eAAAPzo_E3jU948.jpg');
INSERT INTO `course_pic` VALUES ('297ec72c7148403d0171487b20090007', 'group1/M00/00/00/rBg6yF6JVvCAVmF1AAAfUmGsxIo602.jpg');
INSERT INTO `course_pic` VALUES ('297ec72c7148403d017148a075b20011', 'group1/M00/00/00/rBg6yF6JYISAa6aVAAAU3GtLpHA415.jpg');
INSERT INTO `course_pic` VALUES ('297ec72c7148403d017148a558360017', 'group1/M00/00/00/rBg6yF6JYciAD3BtAABR5e6UHIM367.png');

-- ----------------------------
-- Table structure for course_pub
-- ----------------------------
DROP TABLE IF EXISTS `course_pub`;
CREATE TABLE `course_pub`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '适用人群',
  `mt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类',
  `st` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `studymodel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习模式',
  `teachmode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教育模式',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程介绍',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳logstash使用',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费规则，对应数据字典',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效性，对应数据字典',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询qq',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `price_old` float(10, 2) NULL DEFAULT NULL COMMENT '原价格',
  `expires` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过期时间',
  `start_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程有效期-开始时间',
  `end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程有效期-结束时间',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程图片',
  `teachplan` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划',
  `pub_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_pub
-- ----------------------------
INSERT INTO `course_pub` VALUES ('297ec72c70fba29f0170fbbe79980002', 'Jmeter 接口自动化脚本结构进阶', '有一定Jmeter使用经验和Java编程基础', '1-3', '1-3-2', '200002', '201001', NULL, '结合实际项目展示Jmeter在接口自动化测试中的应用，详细展示Jmeter脚本结构优化方案，并针对这套脚本结构方案中的各个模块的设计思路及实现方式进行详细讲解，切实有效提升通过Jmeter进行接口自动化的能力和效率。', '2020-03-29 22:41:45', '203002', '204001', '7894521645', 100.00, 199.00, '2020-03-28 22:29:16', '2020-03-21 00:00:00', '2021-03-27 00:00:00', 'group1/M00/00/00/rBg6yF51s5CAaZeiAAQaaN62xyI723.jpg', '{\"children\":[{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c71051fe10171058013d20000\",\"mediaFileOriginalName\":\"lucene.avi\",\"mediaId\":\"c5c75d70f382e6016d2f506d134eee11\",\"pname\":\"1-1 内容安排\"},{\"grade\":\"3\",\"id\":\"297ec72c71051fe10171058084420001\",\"mediaFileOriginalName\":\"solr.avi\",\"mediaId\":\"5fbb79a2016c0eb609ecd0cd3dc48016\",\"pname\":\"1-2 目标群体及技能要求\"},{\"grade\":\"3\",\"id\":\"297ec72c71051fe101710580c6360002\",\"pname\":\"1-3 课程收获\"}],\"description\":\"本章主要介绍课程内容，让大家对整个课程有初步的整体了解。\",\"grade\":\"2\",\"id\":\"297ec72c70fba29f0170fc051c5c0004\",\"pname\":\"第1章 课程介绍\"},{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269a5bcc0001\",\"pname\":\"2-1 Jmeter接口自动化优劣说明\"}],\"description\":\"本章主要介绍通过学习本课程可以获得哪些收获，对Jmeter这个工具做个简单介绍以及对Jmeter进行接口自动化的优势和劣势与其他接口自动化工具做个对比说明，加强大家对Jmeter工具的理解。\",\"grade\":\"2\",\"id\":\"297ec72c70fba29f0170fc05f70b0005\",\"pname\":\"第2章 认识Jmeter\"},{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126792d01712699f39a0000\",\"pname\":\"3-1 整体脚本结构方案展示\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269b1a730002\",\"pname\":\"3-2  全局参数配置模块实战讲解（上）\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269cb0560003\",\"pname\":\"3-3 全局参数配置模块实战讲解（下）\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269d2a890004\",\"pname\":\"3-4  通用模块库实战讲解（上）\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269d89830005\",\"pname\":\"3-5 通用模块库实战讲解（下）\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269e0eba0006\",\"pname\":\"3-6 用例模块实战讲解（上）\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269e5a300007\",\"pname\":\"3-7 用例模块实战讲解（下）\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269ebf7f0008\",\"pname\":\"3-8 测试结果模块实战讲解\"}],\"description\":\"本章主要从实际工作中遇到的问题出发，解说设计本套Jmeter接口自动化脚本结构方案的原因。通过演示实际工作中的项目，展示这套自动化脚本结构方案，让大家对这套脚本结构方案有个初步整体认识。结合实际项目，演示和讲解通过Jmeter哪些元件来构建全局参数配置模块，并讲解各个元件的作用。\",\"grade\":\"2\",\"id\":\"297ec72c70fba29f0170fc0c3d220006\",\"pname\":\"第3章 Jmeter接口自动化脚本结构方案详解\"},{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269f3ecf0009\",\"pname\":\"4-1 接口用例设计思路分享\"},{\"grade\":\"3\",\"id\":\"297ec72c7126792d0171269fcfc8000a\",\"pname\":\"4-2 常用断言方式\"}],\"description\":\"本章展示在进行接口自动化时，是如何划分接口测试角度，设计较为全面的接口自动化测试用例，并介绍如何将这套用例设计思路用在上面章节介绍的脚本结构方案中。最后介绍常用的几种断言方式，并结合实际工作中的案例，边演示边讲解。\",\"grade\":\"2\",\"id\":\"297ec72c70fba29f0170fc0de1b00007\",\"pname\":\"第4章 接口用例设计思路\"},{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126792d017126a168bf000b\",\"pname\":\"5-1 重点内容回顾总结\"}],\"description\":\"本章带领大家再回顾一下本课程的重点和难点，强化一下学习效果。\",\"grade\":\"2\",\"id\":\"297ec72c70fba29f0170fc0e38cf0008\",\"pname\":\"第5章 课程总结\"}],\"grade\":\"1\",\"id\":\"297ec72c70fba29f0170fc051c5b0003\",\"pname\":\"Jmeter 接口自动化脚本结构进阶\"}', '2020-03-29 22:41:45');
INSERT INTO `course_pub` VALUES ('297ec72c7126792d017126a9bfc1000c', '二进制与Java中的基本数据类型', '需要了解Java的基本语法。', '1-3', '1-3-2', '200001', '201001', NULL, '我们都知道，计算机中都是使用2进制进行存储的。学习好二进制，对理解计算机如何处理数据以及Java中的数据类型在计算机中如何存储，都有实质性的帮助。本课程将从二进制的历史开始讲解位值制计数法、进制转换、小数的二进制化等二进制相关知识。并由此拓展介绍了Java中的整型、浮点型以及字符型。在这里还可以看到BigInteger、BigDecimal如何使用、ASCII与unicode关系等', '2020-04-04 00:52:18', '203001', '204002', '7893452154', NULL, NULL, '2020-03-29 22:27:29', '2020-03-28 00:00:00', '2022-03-30 00:00:00', 'group1/M00/00/00/rBg6yF6AsDSATBQjAAAOu3TLGs4299.jpg', '{\"children\":[{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b22d7a0002\",\"pname\":\"1-1 二进制的前世今生\"}],\"description\":\"本章带领大家认识二进制的基本概念，发展历史，使用场景，对二进制的优缺点进行分析，对全部课程做一个介绍\",\"grade\":\"2\",\"id\":\"297ec72c7126ade1017126b12c000001\",\"pname\":\"第1章 认识计算机中的数学基础——二进制\"},{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b4712f0006\",\"pname\":\"2-1 位值制计数法\"},{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b4cece0007\",\"pname\":\"2-2 Java中的进制\"},{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b527d90008\",\"pname\":\"2-3 位运算\"}],\"description\":\"本章介绍二进制与其他进制的基础——位值制计数法。以及Java中各种进制之间的转换。位运算就是直接对内存中的二进制位进行操作，讲解这种基本运算方式，体验位运算的效率\",\"grade\":\"2\",\"id\":\"297ec72c7126ade1017126b372d10003\",\"pname\":\"第2章 计算机中的进制\"},{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b616220009\",\"pname\":\"3-1 Java中的整数类型\"},{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b6602d000a\",\"pname\":\"3-2 IEEE754及BigDecimal\"},{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b73f22000b\",\"pname\":\" 3-3 小数的二进制化\"},{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b79f81000c\",\"pname\":\"3-4 Java中的字符型和布尔类型\"}],\"description\":\"本章介绍比特与字节的概念，对java中的 .class文件与Jvm进行了基本介绍。基本数据类型部分，主要介绍了整形和浮点型：整型的储存，多字节大小端问题、符号问题，补码问题。补码的加减移位运算；小数的二进制化，浮点数的存储原理 IEEE754。以及Java中解决两个问题：整型取值范围问题引入的BigInteger，和精度丢失问题引入的BigDecimal。此外还介绍了ASCII与unicode关系以及boolean的存储\",\"grade\":\"2\",\"id\":\"297ec72c7126ade1017126b3b7bd0004\",\"pname\":\"第3章 基本数据类型的存储\"},{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7126ade1017126b7fcbb000d\",\"pname\":\"4-1 回顾与总结\"}],\"description\":\"将本课程提到的所有内容进行一个回顾与总结\",\"grade\":\"2\",\"id\":\"297ec72c7126ade1017126b3eeea0005\",\"pname\":\"第4章 回顾与总结\"}],\"grade\":\"1\",\"id\":\"297ec72c7126ade1017126b12bff0000\",\"pname\":\"二进制与Java中的基本数据类型\"}', '2020-04-04 00:52:18');
INSERT INTO `course_pub` VALUES ('297ec72c7140af6d017140e21f2d0000', 'Python3入门人工智能 掌握机器学习+深度学习', '只要你对人工智能感兴趣，想在这个领域发展，或是数据分析从业者\n（商业、金融行业等），希望掌握AI这个工具，那本课程非常适合你', '1-3', '1-3-6', '200001', '201001', NULL, 'Flare老师帮你全面梳理人工智能核心知识，使用流行的Python3语言手把手带你完成AI实战项目，课程囊括机器学习与深度学习，监督与无监督学习，独有综合多项技术的混合算法，为你学习AI打下扎实基础。课程采用sklearn与keras框架（底层调用tensorflow），针对模型优化、数据分析与预处理展开详细讲解，帮你实现能力的全面提升。课程大项目综合数据增强、降维、分离，图像识别，机器与深度学习，监督与无监督学习，让你使用AI工具得心应手。讲师赵辛为福布斯中国U30科技上榜者，孔雀人才，全奖海归博士', '2020-04-04 00:52:35', '203002', '204002', '35467894654', 399.00, 448.00, '2020-04-04 00:33:21', '2020-04-03 00:00:00', '2022-03-31 00:00:00', 'group1/M00/00/00/rBg6yF6HZTKAU4KjAAAkzP--tAw572.jpg', '{\"children\":[{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7140af6d017140e5f8b20007\",\"pname\":\"1-1 课程导学\"}],\"description\":\"本章将和大家介绍课程目标与内容概要，和大家分享人工智能的核心概念：人工智能定义、主要方法、现状。我们会完成开发环境的搭建及工具的学习、使用，具体工具包括：python、anaconda、jupyter notebook、pandas、numpy、matplotlib。\",\"grade\":\"2\",\"id\":\"297ec72c7140af6d017140e4353a0002\",\"pname\":\"第1章 人工智能时代，人人都应该学会利用AI这个工具\"},{\"children\":[],\"description\":\"本章将给大家讲解机器学习及线性回归。机器学习部分会涵盖应用场景与概念的介绍、三大学习方法（监督、无监督、强化学习）的对比。线性回归部分则包含回归分析案例、线性回归模型、模型求解，及建立模型实现房价预测的实战。本章还会教大家完成sklearn的配置\",\"grade\":\"2\",\"id\":\"297ec72c7140af6d017140e478140003\",\"pname\":\"第2章 机器学习之线性回归\"},{\"children\":[],\"description\":\"本章将围绕分类问题及逻辑回归技术进行讲解，通过案例介绍、及与回归问题的对比，让大家理解分类模型。本章会和大家介绍sigmoid方程，并分享逻辑回归模型的求解过程。实战案例包含：考试通过预测（线性边界分类）、芯片质量预测（非线性分类）\",\"grade\":\"2\",\"id\":\"297ec72c7140af6d017140e4bd3d0004\",\"pname\":\"第3章 机器学习之逻辑回归\"},{\"children\":[],\"description\":\"本章会学习不需要标签数据的无监督学习及其最常用的聚类分析方法。针对聚类问题，我们会学习KMeans、Meanshift、DBSCAN算法，并且将其与监督学习的KNN算法进行对比。实战案例将建立多个模型完成数据簇的划分。\",\"grade\":\"2\",\"id\":\"297ec72c7140af6d017140e4fd7c0005\",\"pname\":\"第4章 机器学习之聚类\"},{\"children\":[],\"description\":\"本章将和大家讲解三个常用技术：逻辑回归、异常检测、PCA主成分分析，针对每个技术都会介绍核心概念及原理。本章还会向大家介绍iris鸢尾花经典数据集，并针对三项技术分别进行实战讲解。本章还会教大家完成keras的配置。\",\"grade\":\"2\",\"id\":\"297ec72c7140af6d017140e5380e0006\",\"pname\":\"第5章 机器学习其他常用技术\"}],\"grade\":\"1\",\"id\":\"297ec72c7140af6d017140e4353a0001\",\"pname\":\"Python3入门人工智能 掌握机器学习+深度学习\"}', '2020-04-04 00:52:34');
INSERT INTO `course_pub` VALUES ('297ec72c7148403d01714869bf900000', 'vuex基础入门', '具备一定的vue基础', '1-1', '1-1-9', '200002', '201001', NULL, 'vuex的基本原理和基本使用以及vuex的组成', '2020-04-05 11:51:39', '203001', '204002', '213546897', NULL, NULL, '2020-04-05 11:38:07', NULL, NULL, 'group1/M00/00/00/rBg6yF6JUo6AQG-eAAAPzo_E3jU948.jpg', '{\"children\":[{\"children\":[{\"grade\":\"3\",\"id\":\"297ec72c7148403d0171486be8360003\",\"pname\":\"1-1 课程讲解\"}],\"description\":\"本课程主要讲解vuex是什么，怎么用，作为扩展知识进行了解，本课程不深入讲解vuex 2、本课程适合0基础小白学习\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171486b56970002\",\"pname\":\"第1章 课程简介\"},{\"children\":[],\"description\":\"教你如何安装vue\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171486c59d50004\",\"pname\":\"第2章 vuex的安装\"},{\"children\":[],\"description\":\"vue在实战中的应用\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171486cb57a0005\",\"pname\":\"第3章 vuex案例实操\"},{\"children\":[],\"description\":\"本章节主要总结、梳理课程案例的实现思路\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171486cec210006\",\"pname\":\"第4章 案例总结\"}],\"grade\":\"1\",\"id\":\"297ec72c7148403d0171486b56970001\",\"pname\":\"vuex基础入门\"}', '2020-04-05 11:51:38');
INSERT INTO `course_pub` VALUES ('297ec72c7148403d0171487b20090007', '玩转Bootstrap（基础）', '本教程适合具有一定前端基础的人员，对HTML和CSS有一定的了解，对于定制Bootstrap的同学需要具备LESS和Sass基础知识。', '1-1', '1-1-7', '200001', '201001', NULL, '简介：本Bootstrap教程能够让您了解到，Bootstrap框架是一个非常受欢迎的前端开发框架，他能让后端程序员和不懂设计的前端人员制作出优美的Web页面或Web应用程序。在这个Bootstrap教程中，将带领大家了解Bootstrap框架以及如何使用Bootstrap框架，通过本教程学习能够独立定制出适合自己的Bootstrap。', '2020-04-05 12:02:02', '203001', '204001', '85246789312', NULL, NULL, '2020-04-05 11:56:45', NULL, NULL, 'group1/M00/00/00/rBg6yF6JVvCAVmF1AAAfUmGsxIo602.jpg', '{\"children\":[{\"children\":[],\"description\":\"本章主要介绍了Bootstrap框架的历史、要怎么获取Bootstrap以及Bootstrap的文件结构等。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487c10f40009\",\"pname\":\"第1章 Bootstrap简介\"},{\"children\":[],\"description\":\"Bootstrap通过覆写元素的默认样式，实现对页面排版的优化，让页面在用户面前呈现的更佳完美。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487c4e64000a\",\"pname\":\"第2章 排版\"},{\"children\":[],\"description\":\"本章介绍Bootstrap框架核心部分之一：表单，通过本章节的学习，可以轻松的使用Bootstrap框架制作出适合自己需求的表单。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487c9659000b\",\"pname\":\"第3章 表单\"},{\"children\":[],\"description\":\"本章介绍Bootstrap框架的网格系统，通过本章的学习，可以使用网格系统完美的实现Web的布局。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487ccc09000c\",\"pname\":\"第4章 网格系统\"},{\"children\":[],\"description\":\"菜单（导航菜单）是一个Web网站或者Web应用程序必不可少的一个组件。在Bootstrap框架中也提供了这样的组件。在这一节中，我们主要和大家探讨下拉菜单组件的使用。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487d0ac8000d\",\"pname\":\"第5章 菜单、按钮及导航\"},{\"children\":[],\"description\":\"导航条和上一章介绍的导航，就相差一个字，多了一个“条”字。其实在Bootstrap框架中他们还是明显的区别。在导航条中有一个背景色、而且导航条可以是纯链接（类似导航），也可以是表单，还有就是表单和导航一起结合等多种形式。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487d503f000e\",\"pname\":\"第6章 导航条、分页导航\"},{\"children\":[],\"description\":\"在网页制作中还有许多有用的小组件，如列表框、缩略图等，这一章就将给大家尾尾道来。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487d8908000f\",\"pname\":\"第7章 其它内置组件\"},{\"children\":[],\"description\":\"本章节讲Bootstrap支持的JavaScript插件,包括选顶卡、弹出框、提示框。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d0171487dcae00010\",\"pname\":\"第8章 Bootstrap支持的JavaScript插件\"}],\"grade\":\"1\",\"id\":\"297ec72c7148403d0171487c10f30008\",\"pname\":\"玩转Bootstrap（基础）\"}', '2020-04-05 12:02:02');
INSERT INTO `course_pub` VALUES ('297ec72c7148403d017148a075b20011', '玩转MySQL8.0新特性', '1、一定的MySQL 基础知识。\n2、了解基本的数据库操作。', '1-4', '1-4-2', '200001', '201001', NULL, '简介：无论是强大的通用表表达式和窗口函数，还是增强的账户安全和方便的账户管理，又或是大量的代码重构，以及由此带来的许多 InnoDB 存储引擎增强，各种查询优化器的改进，以及对NoSQL 的进一步支持，都在提示我们，MySQL 8.0是一个非常具有里程碑意义的版本。好东西当然应该大家一起分享，所以有了这门课程。本课程不只有概念，更注重实战，所有内容均有实际操作，欢迎来看。', '2020-04-05 12:39:50', '203001', '204001', '967634689', NULL, NULL, '2020-04-05 12:37:34', NULL, NULL, 'group1/M00/00/00/rBg6yF6JYISAa6aVAAAU3GtLpHA415.jpg', '{\"children\":[{\"children\":[],\"description\":\"MySQL 8.0 版本新特性、新功能内容简介。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a1b4c90013\",\"pname\":\"第1章 课程介绍\"},{\"children\":[],\"description\":\"MySQL 8.0 让你的账户更加安全，管理更加方便。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a1e7280014\",\"pname\":\"第2章 账户与安全\"},{\"children\":[],\"description\":\"介绍 MySQL 8.0 中的三种新的索引方式：隐藏索引、降序索引、函数索引。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a22d750015\",\"pname\":\"第3章 优化器索引\"},{\"children\":[],\"description\":\"通用表表达式，让 SQL 从此不同。\",\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a269ea0016\",\"pname\":\"第4章 通用表表达式\"}],\"grade\":\"1\",\"id\":\"297ec72c7148403d017148a1b4c90012\",\"pname\":\"玩转MySQL8.0新特性\"}', '2020-04-05 12:39:50');
INSERT INTO `course_pub` VALUES ('297ec72c7148403d017148a558360017', 'React Native开发播放器', 'JavaScript基础', '1-1', '1-1-2', '200002', '201001', NULL, '简介：本课程使用react native实现一个视频播放器，播放器同时兼容ios和安卓，播放器的主要功能包括调节倍速播放、全屏的设置，分辨率的适配，视频的播放暂停、播放进度的调节，动画的效果制作等,同时构建出一个完整的类似qq的导航框架', '2020-04-05 12:48:20', '203002', '204001', '124893469', 6.00, 36.00, '2020-04-05 12:43:06', NULL, NULL, 'group1/M00/00/00/rBg6yF6JYciAD3BtAABR5e6UHIM367.png', '{\"children\":[{\"children\":[],\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a675e20019\",\"pname\":\"第1章 课程介绍\"},{\"children\":[],\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a697f8001a\",\"pname\":\"第2章 播放器的实现\"},{\"children\":[],\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a6b433001b\",\"pname\":\"第3章 全屏的设计与实现\"},{\"children\":[],\"grade\":\"2\",\"id\":\"297ec72c7148403d017148a6ce6a001c\",\"pname\":\"第4章 课程总结\"}],\"grade\":\"1\",\"id\":\"297ec72c7148403d017148a675e20018\",\"pname\":\"React Native开发播放器\"}', '2020-04-05 12:48:19');

-- ----------------------------
-- Table structure for course_question
-- ----------------------------
DROP TABLE IF EXISTS `course_question`;
CREATE TABLE `course_question`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `course_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程主键',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_question
-- ----------------------------
INSERT INTO `course_question` VALUES ('297ec72c7152bfe20171538682470001', '297ec72c70fba29f0170fbbe79980002', '297ec72c714840bc0171485f6b180000', '怎么才能躺着赚钱', '如题', '2020-04-07 15:24:16');
INSERT INTO `course_question` VALUES ('297ec72c7153e14b01715471d5870005', '297ec72c70fba29f0170fbbe79980002', '297ec72c711aefbb01711af13e220000', '别动不动就问，去百度啊', '别动不动就问，去百度啊', '2020-04-07 19:41:19');
INSERT INTO `course_question` VALUES ('297ec72c71549c0b017154ae26300000', '297ec72c70fba29f0170fbbe79980002', '297ec72c714840bc0171485f6b180000', 'zxc', 'asd', '2020-04-07 20:47:11');

-- ----------------------------
-- Table structure for course_teacher
-- ----------------------------
DROP TABLE IF EXISTS `course_teacher`;
CREATE TABLE `course_teacher`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '讲师姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `description` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_teacher
-- ----------------------------
INSERT INTO `course_teacher` VALUES ('297ec72c70fba29f0170fbbe79980002', '司马川', 'group1/M00/00/00/rBg6yF52LJiAIhX4AAAYeKzFDQ8356.jpg', '讲师曾任职于Google，担任高级软件工程师，Tech Leader，拥有十余年后端开发经验，精通C++，Java，Go，分布式系统开发等，精通分布式系统设计，从Go语言早期就开始关注和使用Go语言，对Go语言背后的实现及设计理念有独到的见解。');
INSERT INTO `course_teacher` VALUES ('297ec72c7126792d017126a9bfc1000c', '舞马', 'group1/M00/00/00/rBg6yF6Asr2AfoFyAAANyh-8_Cg301.jpg', '高级软件工程师，IT行业从业8年，多年项目开发经验，熟悉C、Java、Python等语言，喜爱钻研新技术，乐于分享知识。');
INSERT INTO `course_teacher` VALUES ('297ec72c7140af6d017140e21f2d0000', 'flare_zhao', 'group1/M00/00/00/rBg6yF6HZguAS9SMAAART0_YASY706.jpg', '人工智能算法科学家、全额奖学金海归博士、2019年福布斯30位U30精英榜科技上榜者、深圳市海外高层次人才，原深圳市微埃智能科技有限公司联合创始人 ，国际SCI收录学术文章十篇。');
INSERT INTO `course_teacher` VALUES ('297ec72c7148403d01714869bf900000', '楼下', 'group1/M00/00/00/rBg6yF6JU16AKp5nAAAoLE1_-dk116.jpg', '高级前端工程师。对移动端开发和工程化开发有丰富经验。曾任职e签宝，现在在大搜车担任高级前端工程师，对vuejs，node以及小程序有深入研究。');
INSERT INTO `course_teacher` VALUES ('297ec72c7148403d0171487b20090007', '大漠', 'group1/M00/00/00/rBg6yF6JV6uAGG4LAAEdqkOdlPY363.jpg', 'W3CPlus创始人，目前就职于手淘。对CSS3和Sass等前端脚本语言有深入的认识和丰富的实践经验。CSS3、Sass和Drupal中国布道者，2014年出版《图解CSS3：核心技术与案例实战》。');
INSERT INTO `course_teacher` VALUES ('297ec72c7148403d017148a075b20011', '董旭阳', 'group1/M00/00/00/rBg6yF6JYQWAZFlzAAARi4Z8mrQ373.jpg', 'Tony.Dong，任职于全球性的互联网博彩公司，负责后台数据库的架构设计和开发工作。十年工作经验，擅长各种数据库管理与 SQL 开发，包括Oracle、MySQL、PostgreSQL等。目前正在研');
INSERT INTO `course_teacher` VALUES ('297ec72c7148403d017148a558360017', '天天敲代码', 'group1/M00/00/00/rBg6yF6JYjqAY-5iAAAZrWSk7pU998.jpg', '十年经验，互联网金融公司总监,架构师,全栈工程师 目前工作 前端：react项目架构研发，H5小游戏研发（phaser.js、three.js、egret、cocos） 后端：java金融分布式系统架');

-- ----------------------------
-- Table structure for teachplan
-- ----------------------------
DROP TABLE IF EXISTS `teachplan`;
CREATE TABLE `teachplan`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parentid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级，分为1、2、3级',
  `ptype` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程类型:1视频、2文档',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章节及课程时介绍',
  `timelength` double(5, 2) NULL DEFAULT NULL COMMENT '时长，单位分钟',
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程id',
  `orderby` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序字段',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态：未发布、已发布',
  `trylearn` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否试学',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan
-- ----------------------------
INSERT INTO `teachplan` VALUES ('297ec72c70fba29f0170fc051c5b0003', 'Jmeter 接口自动化脚本结构进阶', '0', '1', NULL, NULL, NULL, '297ec72c70fba29f0170fbbe79980002', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c70fba29f0170fc051c5c0004', '第1章 课程介绍', '297ec72c70fba29f0170fc051c5b0003', '2', '1', '本章主要介绍课程内容，让大家对整个课程有初步的整体了解。', 100.00, '297ec72c70fba29f0170fbbe79980002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c70fba29f0170fc05f70b0005', '第2章 认识Jmeter', '297ec72c70fba29f0170fc051c5b0003', '2', '2', '本章主要介绍通过学习本课程可以获得哪些收获，对Jmeter这个工具做个简单介绍以及对Jmeter进行接口自动化的优势和劣势与其他接口自动化工具做个对比说明，加强大家对Jmeter工具的理解。', NULL, '297ec72c70fba29f0170fbbe79980002', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c70fba29f0170fc0c3d220006', '第3章 Jmeter接口自动化脚本结构方案详解', '297ec72c70fba29f0170fc051c5b0003', '2', '1', '本章主要从实际工作中遇到的问题出发，解说设计本套Jmeter接口自动化脚本结构方案的原因。通过演示实际工作中的项目，展示这套自动化脚本结构方案，让大家对这套脚本结构方案有个初步整体认识。结合实际项目，演示和讲解通过Jmeter哪些元件来构建全局参数配置模块，并讲解各个元件的作用。', NULL, '297ec72c70fba29f0170fbbe79980002', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c70fba29f0170fc0de1b00007', '第4章 接口用例设计思路', '297ec72c70fba29f0170fc051c5b0003', '2', '1', '本章展示在进行接口自动化时，是如何划分接口测试角度，设计较为全面的接口自动化测试用例，并介绍如何将这套用例设计思路用在上面章节介绍的脚本结构方案中。最后介绍常用的几种断言方式，并结合实际工作中的案例，边演示边讲解。', NULL, '297ec72c70fba29f0170fbbe79980002', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c70fba29f0170fc0e38cf0008', '第5章 课程总结', '297ec72c70fba29f0170fc051c5b0003', '2', '1', '本章带领大家再回顾一下本课程的重点和难点，强化一下学习效果。', NULL, '297ec72c70fba29f0170fbbe79980002', '5', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c71051fe10171058013d20000', '1-1 内容安排', '297ec72c70fba29f0170fc051c5c0004', '3', '1', NULL, 5.30, '297ec72c70fba29f0170fbbe79980002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c71051fe10171058084420001', '1-2 目标群体及技能要求', '297ec72c70fba29f0170fc051c5c0004', '3', '1', NULL, 5.18, '297ec72c70fba29f0170fbbe79980002', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c71051fe101710580c6360002', '1-3 课程收获', '297ec72c70fba29f0170fc051c5c0004', '3', '1', NULL, 5.18, '297ec72c70fba29f0170fbbe79980002', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d01712699f39a0000', '3-1 整体脚本结构方案展示', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 21.43, '297ec72c70fba29f0170fbbe79980002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269a5bcc0001', '2-1 Jmeter接口自动化优劣说明', '297ec72c70fba29f0170fc05f70b0005', '3', '1', NULL, 15.10, '297ec72c70fba29f0170fbbe79980002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269b1a730002', '3-2  全局参数配置模块实战讲解（上）', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 16.23, '297ec72c70fba29f0170fbbe79980002', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269cb0560003', '3-3 全局参数配置模块实战讲解（下）', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 12.01, '297ec72c70fba29f0170fbbe79980002', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269d2a890004', '3-4  通用模块库实战讲解（上）', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 18.12, '297ec72c70fba29f0170fbbe79980002', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269d89830005', '3-5 通用模块库实战讲解（下）', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 12.58, '297ec72c70fba29f0170fbbe79980002', '5', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269e0eba0006', '3-6 用例模块实战讲解（上）', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 17.46, '297ec72c70fba29f0170fbbe79980002', '6', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269e5a300007', '3-7 用例模块实战讲解（下）', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 17.14, '297ec72c70fba29f0170fbbe79980002', '7', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269ebf7f0008', '3-8 测试结果模块实战讲解', '297ec72c70fba29f0170fc0c3d220006', '3', '1', NULL, 10.06, '297ec72c70fba29f0170fbbe79980002', '8', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269f3ecf0009', '4-1 接口用例设计思路分享', '297ec72c70fba29f0170fc0de1b00007', '3', '1', NULL, 8.58, '297ec72c70fba29f0170fbbe79980002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d0171269fcfc8000a', '4-2 常用断言方式', '297ec72c70fba29f0170fc0de1b00007', '3', '1', NULL, 10.08, '297ec72c70fba29f0170fbbe79980002', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126792d017126a168bf000b', '5-1 重点内容回顾总结', '297ec72c70fba29f0170fc0e38cf0008', '3', '1', NULL, 9.54, '297ec72c70fba29f0170fbbe79980002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b12bff0000', '二进制与Java中的基本数据类型', '0', '1', NULL, NULL, NULL, '297ec72c7126792d017126a9bfc1000c', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b12c000001', '第1章 认识计算机中的数学基础——二进制', '297ec72c7126ade1017126b12bff0000', '2', '1', '本章带领大家认识二进制的基本概念，发展历史，使用场景，对二进制的优缺点进行分析，对全部课程做一个介绍', NULL, '297ec72c7126792d017126a9bfc1000c', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b22d7a0002', '1-1 二进制的前世今生', '297ec72c7126ade1017126b12c000001', '3', '1', '', 826.00, '297ec72c7126792d017126a9bfc1000c', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b372d10003', '第2章 计算机中的进制', '297ec72c7126ade1017126b12bff0000', '2', '1', '本章介绍二进制与其他进制的基础——位值制计数法。以及Java中各种进制之间的转换。位运算就是直接对内存中的二进制位进行操作，讲解这种基本运算方式，体验位运算的效率', NULL, '297ec72c7126792d017126a9bfc1000c', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b3b7bd0004', '第3章 基本数据类型的存储', '297ec72c7126ade1017126b12bff0000', '2', '1', '本章介绍比特与字节的概念，对java中的 .class文件与Jvm进行了基本介绍。基本数据类型部分，主要介绍了整形和浮点型：整型的储存，多字节大小端问题、符号问题，补码问题。补码的加减移位运算；小数的二进制化，浮点数的存储原理 IEEE754。以及Java中解决两个问题：整型取值范围问题引入的BigInteger，和精度丢失问题引入的BigDecimal。此外还介绍了ASCII与unicode关系以及boolean的存储', NULL, '297ec72c7126792d017126a9bfc1000c', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b3eeea0005', '第4章 回顾与总结', '297ec72c7126ade1017126b12bff0000', '2', '1', '将本课程提到的所有内容进行一个回顾与总结', NULL, '297ec72c7126792d017126a9bfc1000c', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b4712f0006', '2-1 位值制计数法', '297ec72c7126ade1017126b372d10003', '3', '1', '', 12.14, '297ec72c7126792d017126a9bfc1000c', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b4cece0007', '2-2 Java中的进制', '297ec72c7126ade1017126b372d10003', '3', '1', '', 15.55, '297ec72c7126792d017126a9bfc1000c', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b527d90008', '2-3 位运算', '297ec72c7126ade1017126b372d10003', '3', '1', '', 12.17, '297ec72c7126792d017126a9bfc1000c', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b616220009', '3-1 Java中的整数类型', '297ec72c7126ade1017126b3b7bd0004', '3', '1', NULL, 10.36, '297ec72c7126792d017126a9bfc1000c', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b6602d000a', '3-2 IEEE754及BigDecimal', '297ec72c7126ade1017126b3b7bd0004', '3', '1', NULL, 12.17, '297ec72c7126792d017126a9bfc1000c', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b73f22000b', ' 3-3 小数的二进制化', '297ec72c7126ade1017126b3b7bd0004', '3', '1', NULL, 1.28, '297ec72c7126792d017126a9bfc1000c', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b79f81000c', '3-4 Java中的字符型和布尔类型', '297ec72c7126ade1017126b3b7bd0004', '3', '1', NULL, 9.24, '297ec72c7126792d017126a9bfc1000c', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7126ade1017126b7fcbb000d', '4-1 回顾与总结', '297ec72c7126ade1017126b3eeea0005', '3', '1', NULL, 5.08, '297ec72c7126792d017126a9bfc1000c', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7140af6d017140e4353a0001', 'Python3入门人工智能 掌握机器学习+深度学习', '0', '1', NULL, NULL, NULL, '297ec72c7140af6d017140e21f2d0000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7140af6d017140e4353a0002', '第1章 人工智能时代，人人都应该学会利用AI这个工具', '297ec72c7140af6d017140e4353a0001', '2', '1', '本章将和大家介绍课程目标与内容概要，和大家分享人工智能的核心概念：人工智能定义、主要方法、现状。我们会完成开发环境的搭建及工具的学习、使用，具体工具包括：python、anaconda、jupyter notebook、pandas、numpy、matplotlib。', NULL, '297ec72c7140af6d017140e21f2d0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7140af6d017140e478140003', '第2章 机器学习之线性回归', '297ec72c7140af6d017140e4353a0001', '2', '1', '本章将给大家讲解机器学习及线性回归。机器学习部分会涵盖应用场景与概念的介绍、三大学习方法（监督、无监督、强化学习）的对比。线性回归部分则包含回归分析案例、线性回归模型、模型求解，及建立模型实现房价预测的实战。本章还会教大家完成sklearn的配置', NULL, '297ec72c7140af6d017140e21f2d0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7140af6d017140e4bd3d0004', '第3章 机器学习之逻辑回归', '297ec72c7140af6d017140e4353a0001', '2', '1', '本章将围绕分类问题及逻辑回归技术进行讲解，通过案例介绍、及与回归问题的对比，让大家理解分类模型。本章会和大家介绍sigmoid方程，并分享逻辑回归模型的求解过程。实战案例包含：考试通过预测（线性边界分类）、芯片质量预测（非线性分类）', NULL, '297ec72c7140af6d017140e21f2d0000', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7140af6d017140e4fd7c0005', '第4章 机器学习之聚类', '297ec72c7140af6d017140e4353a0001', '2', '1', '本章会学习不需要标签数据的无监督学习及其最常用的聚类分析方法。针对聚类问题，我们会学习KMeans、Meanshift、DBSCAN算法，并且将其与监督学习的KNN算法进行对比。实战案例将建立多个模型完成数据簇的划分。', NULL, '297ec72c7140af6d017140e21f2d0000', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7140af6d017140e5380e0006', '第5章 机器学习其他常用技术', '297ec72c7140af6d017140e4353a0001', '2', '1', '本章将和大家讲解三个常用技术：逻辑回归、异常检测、PCA主成分分析，针对每个技术都会介绍核心概念及原理。本章还会向大家介绍iris鸢尾花经典数据集，并针对三项技术分别进行实战讲解。本章还会教大家完成keras的配置。', NULL, '297ec72c7140af6d017140e21f2d0000', '5', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7140af6d017140e5f8b20007', '1-1 课程导学', '297ec72c7140af6d017140e4353a0002', '3', '1', NULL, 10.00, '297ec72c7140af6d017140e21f2d0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171486b56970001', 'vuex基础入门', '0', '1', NULL, NULL, NULL, '297ec72c7148403d01714869bf900000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171486b56970002', '第1章 课程简介', '297ec72c7148403d0171486b56970001', '2', '1', '本课程主要讲解vuex是什么，怎么用，作为扩展知识进行了解，本课程不深入讲解vuex 2、本课程适合0基础小白学习', NULL, '297ec72c7148403d01714869bf900000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171486be8360003', '1-1 课程讲解', '297ec72c7148403d0171486b56970002', '3', '1', '', 1.44, '297ec72c7148403d01714869bf900000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171486c59d50004', '第2章 vuex的安装', '297ec72c7148403d0171486b56970001', '2', '1', '教你如何安装vue', NULL, '297ec72c7148403d01714869bf900000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171486cb57a0005', '第3章 vuex案例实操', '297ec72c7148403d0171486b56970001', '2', '1', 'vue在实战中的应用', NULL, '297ec72c7148403d01714869bf900000', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171486cec210006', '第4章 案例总结', '297ec72c7148403d0171486b56970001', '2', '1', '本章节主要总结、梳理课程案例的实现思路', NULL, '297ec72c7148403d01714869bf900000', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487c10f30008', '玩转Bootstrap（基础）', '0', '1', NULL, NULL, NULL, '297ec72c7148403d0171487b20090007', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487c10f40009', '第1章 Bootstrap简介', '297ec72c7148403d0171487c10f30008', '2', '1', '本章主要介绍了Bootstrap框架的历史、要怎么获取Bootstrap以及Bootstrap的文件结构等。', NULL, '297ec72c7148403d0171487b20090007', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487c4e64000a', '第2章 排版', '297ec72c7148403d0171487c10f30008', '2', '1', 'Bootstrap通过覆写元素的默认样式，实现对页面排版的优化，让页面在用户面前呈现的更佳完美。', NULL, '297ec72c7148403d0171487b20090007', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487c9659000b', '第3章 表单', '297ec72c7148403d0171487c10f30008', '2', '1', '本章介绍Bootstrap框架核心部分之一：表单，通过本章节的学习，可以轻松的使用Bootstrap框架制作出适合自己需求的表单。', NULL, '297ec72c7148403d0171487b20090007', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487ccc09000c', '第4章 网格系统', '297ec72c7148403d0171487c10f30008', '2', '1', '本章介绍Bootstrap框架的网格系统，通过本章的学习，可以使用网格系统完美的实现Web的布局。', NULL, '297ec72c7148403d0171487b20090007', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487d0ac8000d', '第5章 菜单、按钮及导航', '297ec72c7148403d0171487c10f30008', '2', '1', '菜单（导航菜单）是一个Web网站或者Web应用程序必不可少的一个组件。在Bootstrap框架中也提供了这样的组件。在这一节中，我们主要和大家探讨下拉菜单组件的使用。', NULL, '297ec72c7148403d0171487b20090007', '5', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487d503f000e', '第6章 导航条、分页导航', '297ec72c7148403d0171487c10f30008', '2', '1', '导航条和上一章介绍的导航，就相差一个字，多了一个“条”字。其实在Bootstrap框架中他们还是明显的区别。在导航条中有一个背景色、而且导航条可以是纯链接（类似导航），也可以是表单，还有就是表单和导航一起结合等多种形式。', NULL, '297ec72c7148403d0171487b20090007', '6', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487d8908000f', '第7章 其它内置组件', '297ec72c7148403d0171487c10f30008', '2', '1', '在网页制作中还有许多有用的小组件，如列表框、缩略图等，这一章就将给大家尾尾道来。', NULL, '297ec72c7148403d0171487b20090007', '7', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d0171487dcae00010', '第8章 Bootstrap支持的JavaScript插件', '297ec72c7148403d0171487c10f30008', '2', '1', '本章节讲Bootstrap支持的JavaScript插件,包括选顶卡、弹出框、提示框。', NULL, '297ec72c7148403d0171487b20090007', '8', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a1b4c90012', '玩转MySQL8.0新特性', '0', '1', NULL, NULL, NULL, '297ec72c7148403d017148a075b20011', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a1b4c90013', '第1章 课程介绍', '297ec72c7148403d017148a1b4c90012', '2', '1', 'MySQL 8.0 版本新特性、新功能内容简介。', NULL, '297ec72c7148403d017148a075b20011', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a1e7280014', '第2章 账户与安全', '297ec72c7148403d017148a1b4c90012', '2', '1', 'MySQL 8.0 让你的账户更加安全，管理更加方便。', NULL, '297ec72c7148403d017148a075b20011', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a22d750015', '第3章 优化器索引', '297ec72c7148403d017148a1b4c90012', '2', '1', '介绍 MySQL 8.0 中的三种新的索引方式：隐藏索引、降序索引、函数索引。', NULL, '297ec72c7148403d017148a075b20011', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a269ea0016', '第4章 通用表表达式', '297ec72c7148403d017148a1b4c90012', '2', '1', '通用表表达式，让 SQL 从此不同。', NULL, '297ec72c7148403d017148a075b20011', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a675e20018', 'React Native开发播放器', '0', '1', NULL, NULL, NULL, '297ec72c7148403d017148a558360017', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a675e20019', '第1章 课程介绍', '297ec72c7148403d017148a675e20018', '2', '1', NULL, NULL, '297ec72c7148403d017148a558360017', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a697f8001a', '第2章 播放器的实现', '297ec72c7148403d017148a675e20018', '2', '1', NULL, NULL, '297ec72c7148403d017148a558360017', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a6b433001b', '第3章 全屏的设计与实现', '297ec72c7148403d017148a675e20018', '2', '1', NULL, NULL, '297ec72c7148403d017148a558360017', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('297ec72c7148403d017148a6ce6a001c', '第4章 课程总结', '297ec72c7148403d017148a675e20018', '2', '1', NULL, NULL, '297ec72c7148403d017148a558360017', '4', '0', NULL);

-- ----------------------------
-- Table structure for teachplan_media
-- ----------------------------
DROP TABLE IF EXISTS `teachplan_media`;
CREATE TABLE `teachplan_media`  (
  `teachplan_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划id',
  `media_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件id',
  `media_fileoriginalname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件的原始名称',
  `media_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件访问地址',
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程Id',
  PRIMARY KEY (`teachplan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan_media
-- ----------------------------
INSERT INTO `teachplan_media` VALUES ('297ec72c71051fe10171058013d20000', 'c5c75d70f382e6016d2f506d134eee11', 'lucene.avi', 'c/5/c5c75d70f382e6016d2f506d134eee11/hls/c5c75d70f382e6016d2f506d134eee11.m3u8', '297ec72c70fba29f0170fbbe79980002');
INSERT INTO `teachplan_media` VALUES ('297ec72c71051fe10171058084420001', '5fbb79a2016c0eb609ecd0cd3dc48016', 'solr.avi', '5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8', '297ec72c70fba29f0170fbbe79980002');

-- ----------------------------
-- Table structure for teachplan_media_pub
-- ----------------------------
DROP TABLE IF EXISTS `teachplan_media_pub`;
CREATE TABLE `teachplan_media_pub`  (
  `teachplan_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划id',
  `media_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件id',
  `media_fileoriginalname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件的原始名称',
  `media_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件访问地址',
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程Id',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'logstash使用',
  PRIMARY KEY (`teachplan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan_media_pub
-- ----------------------------
INSERT INTO `teachplan_media_pub` VALUES ('297ec72c71051fe10171058013d20000', 'c5c75d70f382e6016d2f506d134eee11', 'lucene.avi', 'c/5/c5c75d70f382e6016d2f506d134eee11/hls/c5c75d70f382e6016d2f506d134eee11.m3u8', '297ec72c70fba29f0170fbbe79980002', '2020-03-29 22:41:45');
INSERT INTO `teachplan_media_pub` VALUES ('297ec72c71051fe10171058084420001', '5fbb79a2016c0eb609ecd0cd3dc48016', 'solr.avi', '5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8', '297ec72c70fba29f0170fbbe79980002', '2020-03-29 22:41:45');

SET FOREIGN_KEY_CHECKS = 1;
