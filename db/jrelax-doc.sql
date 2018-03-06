/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : jrelax-doc

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-03-06 09:43:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_black_list
-- ----------------------------
DROP TABLE IF EXISTS `sys_black_list`;
CREATE TABLE `sys_black_list` (
  `id` varchar(32) NOT NULL,
  `rules` varchar(100) NOT NULL COMMENT '规则',
  `remarks` varchar(200) DEFAULT NULL COMMENT '描述',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `create_time` varchar(20) NOT NULL COMMENT '创建人',
  `create_user` varchar(32) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='黑名单';

-- ----------------------------
-- Records of sys_black_list
-- ----------------------------
INSERT INTO `sys_black_list` VALUES ('40289f395969bfde015969c8bec80001', '192.168.31.*', '测试模糊匹配规则', '1', '2017-01-04 22:01:20', '超级管理员');
INSERT INTO `sys_black_list` VALUES ('40289f395969bfde015969d1a4530004', '^10\\.0\\.0', '测试正则表达式规则', '1', '2017-01-04 22:11:03', '超级管理员');
INSERT INTO `sys_black_list` VALUES ('4028b88159687dbc01596882ab2d0001', '192.168.1.1 - 192.168.1.298', '测试范围匹配规则', '1', '2017-01-04 16:05:11', '超级管理员');
INSERT INTO `sys_black_list` VALUES ('4028b88159687dbc01596884eeb00002', '192.168.1.222', '测试单个匹配规则', '1', '2017-01-04 16:07:39', '超级管理员');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` varchar(50) NOT NULL,
  `k` varchar(255) NOT NULL,
  `v` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL COMMENT '1启用 0不启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `k` (`k`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('402847195b28b900015b28bd31a80003', 'system.login.verify', 'false', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e73e0007', 'system.default.pwd', '', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e74f0008', 'upload.file.name', 'date', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e76e0009', 'system.captcha.charts', '', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e773000a', 'system.captcha.length', '', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e778000b', 'system.captcha.style.random', 'true', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e77f000c', 'system.captcha.style.single', 'false', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e78c000d', 'system.unit.role', 'false', '1');
INSERT INTO `sys_config` VALUES ('4028568161f8f71d0161f8f8e791000e', 'system.perm.force', 'false', '1');
INSERT INTO `sys_config` VALUES ('402881225e566db0015e56d811da0007', 'system.logo', '', '1');
INSERT INTO `sys_config` VALUES ('402881225e566db0015e56d8126b0008', 'system.login.theme', 'style1', '1');
INSERT INTO `sys_config` VALUES ('4028b8815885ab62015885c969b2000f', 'system.login.unique', 'false', '1');
INSERT INTO `sys_config` VALUES ('4028b8815aac0f34015aac0fe0770003', 'system.index.page', 'index-tabs', '1');
INSERT INTO `sys_config` VALUES ('4028b8815aac0f34015aac0fe3e60004', 'system.admin.title.unit', 'false', '1');
INSERT INTO `sys_config` VALUES ('4028b8815adb7dca015adb7e5cb20003', 'system.backup.exec', 'D:\\Program Files\\MySQL\\MySQL Server 5.7\\bin', '1');
INSERT INTO `sys_config` VALUES ('4028b8815b034cb3015b03513ec40006', 'system.unit.res', 'false', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab86690003', 'system.login.faceRate', '0.93', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab86690004', 'sms.url', 'http://61.147.98.117:9001/servlet/UserServiceAPI?isLongSms=0&username=17730011432&password=Y2hlbWltaTUyMA&smstype=1&extenno=&method=sendSMS&', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab867b0005', 'sms.method', 'POST', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab86860006', 'sms.field.content', 'content', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab868f0007', 'sms.field.mobile', 'mobile', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0008', 'sms.encoding', 'UTF-8', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0009', 'system.debug', 'true', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0010', 'system.app', 'jrelax-doc', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0011', 'system.respath', '', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0013', 'system.file.folder.root', '${project.baseDir}/resources/application', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0014', 'system.page.rows', '10', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0015', 'system.login.title', 'JRelax 文档中心', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0016', 'system.admin.title', '开发文档中心', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0017', 'upload.folder.root', '${project.baseDir}/resources/upload/', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0018', 'upload.remote.enabled', 'false', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0019', 'upload.remote.rmi', 'rmi://115.28.243.153:10000/fs', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0020', 'upload.remote.view', 'http://115.28.243.153:8080/file', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0021', 'cache.memcached.url', '192.168.0.109', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0022', 'cache.memcached.port', '11211', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0023', 'cache.memcached.weight', '3', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0024', 'cache.memcached.initConn', '10', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0025', 'cache.memcached.maxConn', '10', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0026', 'cache.memcached.minConn', '10', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0027', 'cache.memcached.maxIdle', '3600000', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0028', 'cache.memcached.maintSleep', '60', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0029', 'mail.smtp.host', '', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0030', 'mail.smtp.port', '465', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0031', 'mail.smtp.socketFactory.port', '465', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0032', 'mail.smtp.auth', 'true', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0033', 'mail.smtp.socketFactory.class', 'javax.net.ssl.SSLSocketFactory', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0034', 'mail.smtp.socketFactory.fallback', 'true', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0035', 'mail.smtp.username', '', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0036', 'mail.smtp.password', '', '1');
INSERT INTO `sys_config` VALUES ('8a80868853a692ad0153a6ab869a0037', 'mail.period', '50000', '1');

-- ----------------------------
-- Table structure for sys_datadict
-- ----------------------------
DROP TABLE IF EXISTS `sys_datadict`;
CREATE TABLE `sys_datadict` (
  `id` varchar(50) NOT NULL,
  `category` varchar(50) DEFAULT NULL COMMENT '分类',
  `name` varchar(50) NOT NULL,
  `remarks` varchar(20) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_datadict
-- ----------------------------
INSERT INTO `sys_datadict` VALUES ('402847035ba98055015ba981b5a70004', '系统', 'sys_role_perm', '角色数据权限', 'superadmin', '2017-04-26 17:05:01');
INSERT INTO `sys_datadict` VALUES ('402881225ec261a2015ec2efc77b0026', '系统', 'a', 'a', 'superadmin', '2017-09-27 18:44:07');
INSERT INTO `sys_datadict` VALUES ('4028812856a0dfaa0156a12c4ab80017', '系统', 'sys_themes', '系统主题包', 'superadmin', '2016-08-19 13:00:49');
INSERT INTO `sys_datadict` VALUES ('402881ee52f2c20f0152f2fa36fb0008', '系统', 'sys_status', '状态', 'superadmin', '2016-02-18 14:03:50');
INSERT INTO `sys_datadict` VALUES ('402887d9535f625501535f73ff2b0009', '系统', 'sys_exception', '异常', 'superadmin', '2016-03-10 15:35:50');

-- ----------------------------
-- Table structure for sys_datadict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_datadict_item`;
CREATE TABLE `sys_datadict_item` (
  `id` varchar(50) NOT NULL,
  `did` varchar(50) NOT NULL,
  `k` varchar(100) DEFAULT NULL,
  `v` varchar(100) NOT NULL,
  `location` int(11) DEFAULT NULL,
  `create_user` varchar(50) NOT NULL,
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_datadict_item` (`did`) USING BTREE,
  CONSTRAINT `sys_datadict_item_ibfk_1` FOREIGN KEY (`did`) REFERENCES `sys_datadict` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_datadict_item
-- ----------------------------
INSERT INTO `sys_datadict_item` VALUES ('402847035ba98055015ba98231a00005', '402847035ba98055015ba981b5a70004', '1', '所有数据', '1', 'superadmin', '2017-04-26 17:05:33');
INSERT INTO `sys_datadict_item` VALUES ('402847035ba98055015ba98231a10006', '402847035ba98055015ba981b5a70004', '2', '所在机构及下属机构', '2', 'superadmin', '2017-04-26 17:05:33');
INSERT INTO `sys_datadict_item` VALUES ('402847035ba98055015ba98231a10007', '402847035ba98055015ba981b5a70004', '3', '仅所在机构', '3', 'superadmin', '2017-04-26 17:05:33');
INSERT INTO `sys_datadict_item` VALUES ('402847035ba98055015ba98231a10008', '402847035ba98055015ba981b5a70004', '4', '仅本人数据', '4', 'superadmin', '2017-04-26 17:05:33');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e94150018', '4028812856a0dfaa0156a12c4ab80017', '默认风格', 'palette', '1', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e94330019', '4028812856a0dfaa0156a12c4ab80017', '风格一', 'palette.2', '2', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e9434001a', '4028812856a0dfaa0156a12c4ab80017', '风格二', 'palette.3', '3', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e9434001b', '4028812856a0dfaa0156a12c4ab80017', '风格三', 'palette.4', '4', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e9435001c', '4028812856a0dfaa0156a12c4ab80017', '风格四', 'palette.5', '5', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e9435001d', '4028812856a0dfaa0156a12c4ab80017', '风格五', 'palette.6', '6', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e9435001e', '4028812856a0dfaa0156a12c4ab80017', '风格六', 'palette.7', '7', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('4028812856a0dfaa0156a12e9436001f', '4028812856a0dfaa0156a12c4ab80017', '风格七', 'palette.7', '8', 'superadmin', '2016-08-19 13:03:19');
INSERT INTO `sys_datadict_item` VALUES ('402887d9535f625501535f7419c0000e', '402887d9535f625501535f73ff2b0009', 'org.hibernate.exception.ConstraintViolationException', '存在关联数据，无法执行此操作！', '1', 'superadmin', '2016-03-10 15:35:57');
INSERT INTO `sys_datadict_item` VALUES ('40289f395911f93201591262dfa40027', '402881ee52f2c20f0152f2fa36fb0008', '0', '启用', '1', 'superadmin', '2016-12-18 22:43:06');
INSERT INTO `sys_datadict_item` VALUES ('40289f395911f93201591262dfa40028', '402881ee52f2c20f0152f2fa36fb0008', '1', '禁用', '2', 'superadmin', '2016-12-18 22:43:06');

-- ----------------------------
-- Table structure for sys_email
-- ----------------------------
DROP TABLE IF EXISTS `sys_email`;
CREATE TABLE `sys_email` (
  `id` varchar(50) NOT NULL,
  `recipients` varchar(100) NOT NULL DEFAULT '' COMMENT '多个收件人用逗号隔开',
  `title` varchar(100) NOT NULL,
  `subtitle` varchar(100) DEFAULT NULL,
  `content` text,
  `sendTime` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '1 待发送 2已发送 3 发送失败',
  `remarks` varchar(100) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_email
-- ----------------------------
INSERT INTO `sys_email` VALUES ('402838814a9031f1014a9035471d0001', '1077020759@qq.com,359921330@qq.com', 'title', 'subtitle', 'content', '2014-12-28 19:54:12', '2', 'remarks', '1', '4028388148e09a0d0148', '2014-12-28 17:23:38');
INSERT INTO `sys_email` VALUES ('402838814a90ea9b014a90ede26e0001', '359921330@qq.com', '测试程序 - 物业管理系统', null, '测试一下啊！！！', '2014-12-28 20:45:19', '2', null, '1', '4028388148e09a0d0148', '2014-12-28 20:45:16');
INSERT INTO `sys_email` VALUES ('402838814a9429da014a9430140a0000', '359921330@qq.com', '测试程序 - 物业管理系统', null, '测试一下啊！！！', '2014-12-29 12:07:47', '2', null, '1', '4028388148e09a0d0148', '2014-12-29 11:56:26');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '文件名',
  `path` varchar(200) NOT NULL COMMENT '保存路径',
  `absolute_path` varchar(200) DEFAULT NULL COMMENT '绝对路径',
  `prefix` varchar(200) DEFAULT NULL COMMENT '上传服务器前缀地址',
  `original` varchar(100) NOT NULL COMMENT '原文件名',
  `md5` varchar(32) NOT NULL COMMENT 'MD5值',
  `suffix` varchar(50) DEFAULT NULL COMMENT '后缀名',
  `type` varchar(50) NOT NULL COMMENT '文件类型',
  `size` bigint(50) NOT NULL COMMENT '文件大小',
  `display_size` varchar(50) NOT NULL COMMENT '显示大小',
  `create_user` varchar(32) NOT NULL COMMENT '上传人',
  `create_time` datetime NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` varchar(32) NOT NULL,
  `unit_id` varchar(32) NOT NULL COMMENT '所属机构',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `descript` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` varchar(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_group$unit` (`unit_id`) USING BTREE,
  CONSTRAINT `sys_group_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `sys_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('4028b8815a70a1e3015a70a4360f0006', '40283881493c2aff01493c32e2a70000', '管理员', '', '超级管理员', '2017-02-24 23:01:34');
INSERT INTO `sys_group` VALUES ('4028b8815a70a1e3015a70a454870007', '40283881493c2aff01493c32e2a70000', '测试组', '', '超级管理员', '2017-02-24 23:01:42');
INSERT INTO `sys_group` VALUES ('4028b8815a70a1e3015a70a465d80008', '40283881493c2aff01493c32e2a70000', '开发组', '', '超级管理员', '2017-02-24 23:01:46');

-- ----------------------------
-- Table structure for sys_group_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role` (
  `group_id` varchar(32) NOT NULL COMMENT '用户组ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  KEY `FK_sys_group_role$group` (`group_id`) USING BTREE,
  KEY `FK_sys_group_role$role` (`role_id`) USING BTREE,
  CONSTRAINT `sys_group_role_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`id`),
  CONSTRAINT `sys_group_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组与角色关联';

-- ----------------------------
-- Records of sys_group_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_group_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_user`;
CREATE TABLE `sys_group_user` (
  `group_id` varchar(32) NOT NULL COMMENT '用户组ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  KEY `FK_sys_group_user$group` (`group_id`) USING BTREE,
  KEY `FK_sys_group_user$user` (`user_id`) USING BTREE,
  CONSTRAINT `sys_group_user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`id`),
  CONSTRAINT `sys_group_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组与用户关联';

-- ----------------------------
-- Records of sys_group_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_ip_address
-- ----------------------------
DROP TABLE IF EXISTS `sys_ip_address`;
CREATE TABLE `sys_ip_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_ip` varchar(25) NOT NULL,
  `end_ip` varchar(25) NOT NULL,
  `country` varchar(50) NOT NULL,
  `local` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_ip_address
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(50) NOT NULL,
  `level` int(11) NOT NULL COMMENT '日志级别',
  `content` text COMMENT '日志内容',
  `user` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录用户',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '记录IP',
  `log_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '记录时间',
  `module` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块名',
  `source` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '记录来源',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器型号',
  `platform` varchar(50) DEFAULT NULL COMMENT '系统平台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('4028568161f8f71d0161f8f8ab570003', '1', '清除日志', 'superadmin', '', '2018-03-06 09:41:41 041', '数据初始化', 'LogService', 'Chrome/64.0.3282.186', 'Windows 10');
INSERT INTO `sys_log` VALUES ('4028568161f8f71d0161f8f8b1ee0004', '1', '清除历史通知', 'superadmin', '', '2018-03-06 09:41:43 043', '数据初始化', 'LogService', 'Chrome/64.0.3282.186', 'Windows 10');
INSERT INTO `sys_log` VALUES ('4028568161f8f71d0161f8f8b9010005', '1', '清除历史通知', 'superadmin', '', '2018-03-06 09:41:45 045', '数据初始化', 'LogService', 'Chrome/64.0.3282.186', 'Windows 10');
INSERT INTO `sys_log` VALUES ('4028568161f8f71d0161f8f8c1780006', '1', '查询系统参数', 'superadmin', '本地主机', '2018-03-06 09:41:47 047', 'sys-config', 'ConfigController', 'Chrome/64.0.3282.186', 'Windows 10');
INSERT INTO `sys_log` VALUES ('4028568161f8f71d0161f8f8e79d000f', '1', '配置系统参数', 'superadmin', '本地主机', '2018-03-06 09:41:57 057', 'sys-config', 'ConfigController', 'Chrome/64.0.3282.186', 'Windows 10');
INSERT INTO `sys_log` VALUES ('4028568161f8f71d0161f8f91adb0010', '1', '配置系统参数', 'superadmin', '本地主机', '2018-03-06 09:42:10 010', 'sys-config', 'ConfigController', 'Chrome/64.0.3282.186', 'Windows 10');
INSERT INTO `sys_log` VALUES ('4028568161f8f71d0161f8f925050011', '1', '配置系统参数', 'superadmin', '本地主机', '2018-03-06 09:42:12 012', 'sys-config', 'ConfigController', 'Chrome/64.0.3282.186', 'Windows 10');

-- ----------------------------
-- Table structure for sys_notify
-- ----------------------------
DROP TABLE IF EXISTS `sys_notify`;
CREATE TABLE `sys_notify` (
  `id` varchar(50) NOT NULL,
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `content` longtext,
  `create_user` varchar(32) NOT NULL,
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_notify
-- ----------------------------

-- ----------------------------
-- Table structure for sys_notify_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_notify_user`;
CREATE TABLE `sys_notify_user` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `notify_id` varchar(50) DEFAULT NULL,
  `business_id` varchar(50) DEFAULT NULL COMMENT '业务ID',
  `create_time` varchar(20) NOT NULL,
  `recread` tinyint(1) DEFAULT NULL,
  `read_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_notify$sys_notify_user` (`notify_id`) USING BTREE,
  KEY `FK_sys_user$sys_notify` (`user_id`) USING BTREE,
  CONSTRAINT `sys_notify_user_ibfk_1` FOREIGN KEY (`notify_id`) REFERENCES `sys_notify` (`id`),
  CONSTRAINT `sys_notify_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_notify_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_regions
-- ----------------------------
DROP TABLE IF EXISTS `sys_regions`;
CREATE TABLE `sys_regions` (
  `id` int(11) NOT NULL,
  `pid` int(11) NOT NULL DEFAULT '-1',
  `code` varchar(6) NOT NULL COMMENT '编码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_regions
-- ----------------------------
INSERT INTO `sys_regions` VALUES ('1', '-1', '01', '北京');
INSERT INTO `sys_regions` VALUES ('2', '-1', '02', '上海');
INSERT INTO `sys_regions` VALUES ('3', '-1', '03', '天津');
INSERT INTO `sys_regions` VALUES ('4', '-1', '04', '重庆');
INSERT INTO `sys_regions` VALUES ('5', '-1', '05', '河北');
INSERT INTO `sys_regions` VALUES ('6', '-1', '06', '山西');
INSERT INTO `sys_regions` VALUES ('7', '-1', '07', '河南');
INSERT INTO `sys_regions` VALUES ('8', '-1', '08', '辽宁');
INSERT INTO `sys_regions` VALUES ('9', '-1', '09', '吉林');
INSERT INTO `sys_regions` VALUES ('10', '-1', '10', '黑龙江');
INSERT INTO `sys_regions` VALUES ('11', '-1', '11', '内蒙古');
INSERT INTO `sys_regions` VALUES ('12', '-1', '12', '江苏');
INSERT INTO `sys_regions` VALUES ('13', '-1', '13', '山东');
INSERT INTO `sys_regions` VALUES ('14', '-1', '14', '安徽');
INSERT INTO `sys_regions` VALUES ('15', '-1', '15', '浙江');
INSERT INTO `sys_regions` VALUES ('16', '-1', '16', '福建');
INSERT INTO `sys_regions` VALUES ('17', '-1', '17', '湖北');
INSERT INTO `sys_regions` VALUES ('18', '-1', '18', '湖南');
INSERT INTO `sys_regions` VALUES ('19', '-1', '19', '广东');
INSERT INTO `sys_regions` VALUES ('20', '-1', '20', '广西');
INSERT INTO `sys_regions` VALUES ('21', '-1', '21', '江西');
INSERT INTO `sys_regions` VALUES ('22', '-1', '22', '四川');
INSERT INTO `sys_regions` VALUES ('23', '-1', '23', '海南');
INSERT INTO `sys_regions` VALUES ('24', '-1', '24', '贵州');
INSERT INTO `sys_regions` VALUES ('25', '-1', '25', '云南');
INSERT INTO `sys_regions` VALUES ('26', '-1', '26', '西藏');
INSERT INTO `sys_regions` VALUES ('27', '-1', '27', '陕西');
INSERT INTO `sys_regions` VALUES ('28', '-1', '28', '甘肃');
INSERT INTO `sys_regions` VALUES ('29', '-1', '29', '青海');
INSERT INTO `sys_regions` VALUES ('30', '-1', '30', '宁夏');
INSERT INTO `sys_regions` VALUES ('31', '-1', '31', '新疆');
INSERT INTO `sys_regions` VALUES ('32', '-1', '32', '台湾');
INSERT INTO `sys_regions` VALUES ('72', '1', '0101', '朝阳区');
INSERT INTO `sys_regions` VALUES ('78', '2', '0201', '黄浦区');
INSERT INTO `sys_regions` VALUES ('84', '-1', '33', '钓鱼岛');
INSERT INTO `sys_regions` VALUES ('88', '50950', '043201', '内环以内');
INSERT INTO `sys_regions` VALUES ('98', '51041', '030701', '全境');
INSERT INTO `sys_regions` VALUES ('103', '51026', '043801', '全境');
INSERT INTO `sys_regions` VALUES ('106', '50952', '043401', '内环以内');
INSERT INTO `sys_regions` VALUES ('111', '50954', '043601', '内环以内');
INSERT INTO `sys_regions` VALUES ('113', '4', '0401', '万州区');
INSERT INTO `sys_regions` VALUES ('114', '4', '0402', '涪陵区');
INSERT INTO `sys_regions` VALUES ('115', '4', '0403', '梁平县');
INSERT INTO `sys_regions` VALUES ('119', '4', '0404', '南川区');
INSERT INTO `sys_regions` VALUES ('123', '4', '0405', '潼南县');
INSERT INTO `sys_regions` VALUES ('126', '4', '0406', '大足区');
INSERT INTO `sys_regions` VALUES ('128', '4', '0407', '黔江区');
INSERT INTO `sys_regions` VALUES ('129', '4', '0408', '武隆县');
INSERT INTO `sys_regions` VALUES ('130', '4', '0409', '丰都县');
INSERT INTO `sys_regions` VALUES ('131', '4', '0410', '奉节县');
INSERT INTO `sys_regions` VALUES ('132', '4', '0411', '开县');
INSERT INTO `sys_regions` VALUES ('133', '4', '0412', '云阳县');
INSERT INTO `sys_regions` VALUES ('134', '4', '0413', '忠县');
INSERT INTO `sys_regions` VALUES ('135', '4', '0414', '巫溪县');
INSERT INTO `sys_regions` VALUES ('136', '4', '0415', '巫山县');
INSERT INTO `sys_regions` VALUES ('137', '4', '0416', '石柱县');
INSERT INTO `sys_regions` VALUES ('138', '4', '0417', '彭水县');
INSERT INTO `sys_regions` VALUES ('139', '4', '0418', '垫江县');
INSERT INTO `sys_regions` VALUES ('140', '4', '0419', '酉阳县');
INSERT INTO `sys_regions` VALUES ('141', '4', '0420', '秀山县');
INSERT INTO `sys_regions` VALUES ('142', '5', '0501', '石家庄市');
INSERT INTO `sys_regions` VALUES ('143', '142', '050101', '辛集市');
INSERT INTO `sys_regions` VALUES ('145', '142', '050102', '晋州市');
INSERT INTO `sys_regions` VALUES ('146', '142', '050103', '新乐市');
INSERT INTO `sys_regions` VALUES ('148', '5', '0502', '邯郸市');
INSERT INTO `sys_regions` VALUES ('153', '142', '050104', '井陉县');
INSERT INTO `sys_regions` VALUES ('154', '142', '050105', '栾城县');
INSERT INTO `sys_regions` VALUES ('156', '142', '050106', '行唐县');
INSERT INTO `sys_regions` VALUES ('157', '142', '050107', '灵寿县');
INSERT INTO `sys_regions` VALUES ('158', '142', '050108', '高邑县');
INSERT INTO `sys_regions` VALUES ('159', '142', '050109', '赵县');
INSERT INTO `sys_regions` VALUES ('160', '142', '050110', '赞皇县');
INSERT INTO `sys_regions` VALUES ('161', '142', '050111', '深泽县');
INSERT INTO `sys_regions` VALUES ('162', '142', '050112', '无极县');
INSERT INTO `sys_regions` VALUES ('163', '142', '050113', '元氏县');
INSERT INTO `sys_regions` VALUES ('164', '5', '0503', '邢台市');
INSERT INTO `sys_regions` VALUES ('167', '148', '050201', '邯郸县');
INSERT INTO `sys_regions` VALUES ('168', '148', '050202', '峰峰矿区');
INSERT INTO `sys_regions` VALUES ('169', '148', '050203', '曲周县');
INSERT INTO `sys_regions` VALUES ('170', '148', '050204', '馆陶县');
INSERT INTO `sys_regions` VALUES ('171', '148', '050205', '魏县');
INSERT INTO `sys_regions` VALUES ('172', '148', '050206', '成安县');
INSERT INTO `sys_regions` VALUES ('173', '148', '050207', '大名县');
INSERT INTO `sys_regions` VALUES ('174', '148', '050208', '涉县');
INSERT INTO `sys_regions` VALUES ('175', '148', '050209', '鸡泽县');
INSERT INTO `sys_regions` VALUES ('176', '148', '050210', '邱县');
INSERT INTO `sys_regions` VALUES ('177', '148', '050211', '广平县');
INSERT INTO `sys_regions` VALUES ('178', '148', '050212', '肥乡县');
INSERT INTO `sys_regions` VALUES ('180', '148', '050213', '磁县');
INSERT INTO `sys_regions` VALUES ('183', '164', '050301', '邢台县');
INSERT INTO `sys_regions` VALUES ('184', '164', '050302', '南宫市');
INSERT INTO `sys_regions` VALUES ('185', '164', '050303', '沙河市');
INSERT INTO `sys_regions` VALUES ('186', '164', '050304', '柏乡县');
INSERT INTO `sys_regions` VALUES ('187', '164', '050305', '任县');
INSERT INTO `sys_regions` VALUES ('188', '164', '050306', '清河县');
INSERT INTO `sys_regions` VALUES ('189', '164', '050307', '隆尧县');
INSERT INTO `sys_regions` VALUES ('190', '164', '050308', '临城县');
INSERT INTO `sys_regions` VALUES ('191', '164', '050309', '广宗县');
INSERT INTO `sys_regions` VALUES ('192', '164', '050310', '临西县');
INSERT INTO `sys_regions` VALUES ('193', '164', '050311', '内丘县');
INSERT INTO `sys_regions` VALUES ('194', '164', '050312', '平乡县');
INSERT INTO `sys_regions` VALUES ('195', '164', '050313', '巨鹿县');
INSERT INTO `sys_regions` VALUES ('196', '164', '050314', '新河县');
INSERT INTO `sys_regions` VALUES ('197', '164', '050315', '南和县');
INSERT INTO `sys_regions` VALUES ('199', '5', '0504', '保定市');
INSERT INTO `sys_regions` VALUES ('203', '199', '050401', '安国市');
INSERT INTO `sys_regions` VALUES ('205', '199', '050402', '满城县');
INSERT INTO `sys_regions` VALUES ('206', '199', '050403', '清苑县');
INSERT INTO `sys_regions` VALUES ('207', '199', '050404', '涞水县');
INSERT INTO `sys_regions` VALUES ('208', '199', '050405', '阜平县');
INSERT INTO `sys_regions` VALUES ('210', '199', '050406', '定兴县');
INSERT INTO `sys_regions` VALUES ('211', '199', '050407', '唐县');
INSERT INTO `sys_regions` VALUES ('212', '199', '050408', '高阳县');
INSERT INTO `sys_regions` VALUES ('213', '199', '050409', '容城县');
INSERT INTO `sys_regions` VALUES ('214', '199', '050410', '涞源县');
INSERT INTO `sys_regions` VALUES ('215', '199', '050411', '望都县');
INSERT INTO `sys_regions` VALUES ('217', '199', '050412', '易县');
INSERT INTO `sys_regions` VALUES ('218', '199', '050413', '曲阳县');
INSERT INTO `sys_regions` VALUES ('219', '199', '050414', '蠡县');
INSERT INTO `sys_regions` VALUES ('220', '199', '050415', '顺平县');
INSERT INTO `sys_regions` VALUES ('221', '199', '050416', '博野县');
INSERT INTO `sys_regions` VALUES ('222', '199', '050417', '雄县');
INSERT INTO `sys_regions` VALUES ('224', '5', '0505', '张家口市');
INSERT INTO `sys_regions` VALUES ('225', '224', '050501', '宣化县');
INSERT INTO `sys_regions` VALUES ('226', '224', '050502', '康保县');
INSERT INTO `sys_regions` VALUES ('227', '224', '050503', '张北县');
INSERT INTO `sys_regions` VALUES ('228', '224', '050504', '阳原县');
INSERT INTO `sys_regions` VALUES ('229', '224', '050505', '赤城县');
INSERT INTO `sys_regions` VALUES ('230', '224', '050506', '怀安县');
INSERT INTO `sys_regions` VALUES ('231', '224', '050507', '怀来县');
INSERT INTO `sys_regions` VALUES ('232', '224', '050508', '崇礼县');
INSERT INTO `sys_regions` VALUES ('233', '224', '050509', '尚义县');
INSERT INTO `sys_regions` VALUES ('234', '224', '050510', '蔚县');
INSERT INTO `sys_regions` VALUES ('235', '224', '050511', '涿鹿县');
INSERT INTO `sys_regions` VALUES ('236', '224', '050512', '万全县');
INSERT INTO `sys_regions` VALUES ('238', '224', '050513', '下花园区');
INSERT INTO `sys_regions` VALUES ('239', '5', '0506', '承德市');
INSERT INTO `sys_regions` VALUES ('241', '239', '050601', '兴隆县');
INSERT INTO `sys_regions` VALUES ('242', '239', '050602', '平泉县');
INSERT INTO `sys_regions` VALUES ('243', '239', '050603', '滦平县');
INSERT INTO `sys_regions` VALUES ('245', '239', '050604', '丰宁县');
INSERT INTO `sys_regions` VALUES ('246', '239', '050605', '围场县');
INSERT INTO `sys_regions` VALUES ('247', '239', '050606', '宽城县');
INSERT INTO `sys_regions` VALUES ('248', '5', '0507', '秦皇岛市');
INSERT INTO `sys_regions` VALUES ('257', '164', '050316', '宁晋县');
INSERT INTO `sys_regions` VALUES ('258', '5', '0508', '唐山市');
INSERT INTO `sys_regions` VALUES ('261', '248', '050701', '卢龙县');
INSERT INTO `sys_regions` VALUES ('262', '248', '050702', '青龙县');
INSERT INTO `sys_regions` VALUES ('263', '248', '050703', '昌黎县');
INSERT INTO `sys_regions` VALUES ('264', '5', '0509', '沧州市');
INSERT INTO `sys_regions` VALUES ('265', '264', '050901', '沧县');
INSERT INTO `sys_regions` VALUES ('266', '264', '050902', '泊头市');
INSERT INTO `sys_regions` VALUES ('268', '264', '050903', '河间市');
INSERT INTO `sys_regions` VALUES ('269', '264', '050904', '献县');
INSERT INTO `sys_regions` VALUES ('270', '264', '050905', '肃宁县');
INSERT INTO `sys_regions` VALUES ('271', '264', '050906', '青县');
INSERT INTO `sys_regions` VALUES ('272', '264', '050907', '东光县');
INSERT INTO `sys_regions` VALUES ('273', '264', '050908', '吴桥县');
INSERT INTO `sys_regions` VALUES ('274', '5', '0510', '廊坊市');
INSERT INTO `sys_regions` VALUES ('275', '5', '0511', '衡水市');
INSERT INTO `sys_regions` VALUES ('276', '264', '050909', '南皮县');
INSERT INTO `sys_regions` VALUES ('277', '264', '050910', '盐山县');
INSERT INTO `sys_regions` VALUES ('278', '264', '050911', '海兴县');
INSERT INTO `sys_regions` VALUES ('279', '264', '050912', '孟村县');
INSERT INTO `sys_regions` VALUES ('284', '274', '051001', '固安县');
INSERT INTO `sys_regions` VALUES ('285', '274', '051002', '永清县');
INSERT INTO `sys_regions` VALUES ('286', '274', '051003', '香河县');
INSERT INTO `sys_regions` VALUES ('287', '274', '051004', '大城县');
INSERT INTO `sys_regions` VALUES ('288', '274', '051005', '文安县');
INSERT INTO `sys_regions` VALUES ('289', '274', '051006', '大厂县');
INSERT INTO `sys_regions` VALUES ('291', '275', '051101', '冀州市');
INSERT INTO `sys_regions` VALUES ('292', '275', '051102', '深州市');
INSERT INTO `sys_regions` VALUES ('293', '275', '051103', '饶阳县');
INSERT INTO `sys_regions` VALUES ('294', '275', '051104', '枣强县');
INSERT INTO `sys_regions` VALUES ('295', '275', '051105', '故城县');
INSERT INTO `sys_regions` VALUES ('296', '275', '051106', '阜城县');
INSERT INTO `sys_regions` VALUES ('297', '275', '051107', '安平县');
INSERT INTO `sys_regions` VALUES ('298', '275', '051108', '武邑县');
INSERT INTO `sys_regions` VALUES ('299', '275', '051109', '景县');
INSERT INTO `sys_regions` VALUES ('300', '275', '051110', '武强县');
INSERT INTO `sys_regions` VALUES ('303', '6', '0601', '太原市');
INSERT INTO `sys_regions` VALUES ('304', '303', '060101', '阳曲县');
INSERT INTO `sys_regions` VALUES ('305', '303', '060102', '古交市');
INSERT INTO `sys_regions` VALUES ('306', '303', '060103', '娄烦县');
INSERT INTO `sys_regions` VALUES ('307', '303', '060104', '清徐县');
INSERT INTO `sys_regions` VALUES ('309', '6', '0602', '大同市');
INSERT INTO `sys_regions` VALUES ('310', '309', '060201', '大同县');
INSERT INTO `sys_regions` VALUES ('311', '309', '060202', '天镇县');
INSERT INTO `sys_regions` VALUES ('312', '309', '060203', '灵丘县');
INSERT INTO `sys_regions` VALUES ('313', '309', '060204', '阳高县');
INSERT INTO `sys_regions` VALUES ('314', '309', '060205', '左云县');
INSERT INTO `sys_regions` VALUES ('315', '309', '060206', '浑源县');
INSERT INTO `sys_regions` VALUES ('316', '309', '060207', '广灵县');
INSERT INTO `sys_regions` VALUES ('318', '6', '0603', '阳泉市');
INSERT INTO `sys_regions` VALUES ('319', '318', '060301', '盂县');
INSERT INTO `sys_regions` VALUES ('320', '318', '060302', '平定县');
INSERT INTO `sys_regions` VALUES ('321', '318', '060303', '郊区');
INSERT INTO `sys_regions` VALUES ('325', '6', '0604', '晋城市');
INSERT INTO `sys_regions` VALUES ('326', '325', '060401', '高平市');
INSERT INTO `sys_regions` VALUES ('327', '325', '060402', '阳城县');
INSERT INTO `sys_regions` VALUES ('328', '325', '060403', '沁水县');
INSERT INTO `sys_regions` VALUES ('329', '325', '060404', '陵川县');
INSERT INTO `sys_regions` VALUES ('330', '6', '0605', '朔州市');
INSERT INTO `sys_regions` VALUES ('331', '330', '060501', '山阴县');
INSERT INTO `sys_regions` VALUES ('332', '330', '060502', '右玉县');
INSERT INTO `sys_regions` VALUES ('333', '330', '060503', '应县');
INSERT INTO `sys_regions` VALUES ('334', '330', '060504', '怀仁县');
INSERT INTO `sys_regions` VALUES ('335', '330', '060505', '朔城区');
INSERT INTO `sys_regions` VALUES ('336', '6', '0606', '晋中市');
INSERT INTO `sys_regions` VALUES ('338', '336', '060601', '介休市');
INSERT INTO `sys_regions` VALUES ('339', '336', '060602', '昔阳县');
INSERT INTO `sys_regions` VALUES ('341', '336', '060603', '祁县');
INSERT INTO `sys_regions` VALUES ('342', '336', '060604', '左权县');
INSERT INTO `sys_regions` VALUES ('343', '336', '060605', '寿阳县');
INSERT INTO `sys_regions` VALUES ('344', '336', '060606', '太谷县');
INSERT INTO `sys_regions` VALUES ('345', '336', '060607', '和顺县');
INSERT INTO `sys_regions` VALUES ('346', '336', '060608', '灵石县');
INSERT INTO `sys_regions` VALUES ('347', '336', '060609', '平遥县');
INSERT INTO `sys_regions` VALUES ('348', '336', '060610', '榆社县');
INSERT INTO `sys_regions` VALUES ('350', '6', '0607', '忻州市');
INSERT INTO `sys_regions` VALUES ('351', '350', '060701', '原平市');
INSERT INTO `sys_regions` VALUES ('352', '350', '060702', '代县');
INSERT INTO `sys_regions` VALUES ('353', '350', '060703', '神池县');
INSERT INTO `sys_regions` VALUES ('354', '350', '060704', '五寨县');
INSERT INTO `sys_regions` VALUES ('358', '350', '060705', '五台县');
INSERT INTO `sys_regions` VALUES ('359', '350', '060706', '偏关县');
INSERT INTO `sys_regions` VALUES ('360', '350', '060707', '宁武县');
INSERT INTO `sys_regions` VALUES ('361', '350', '060708', '静乐县');
INSERT INTO `sys_regions` VALUES ('362', '350', '060709', '繁峙县');
INSERT INTO `sys_regions` VALUES ('363', '350', '060710', '河曲县');
INSERT INTO `sys_regions` VALUES ('364', '350', '060711', '保德县');
INSERT INTO `sys_regions` VALUES ('365', '350', '060712', '定襄县');
INSERT INTO `sys_regions` VALUES ('366', '350', '060713', '忻府区');
INSERT INTO `sys_regions` VALUES ('367', '350', '060714', '岢岚县');
INSERT INTO `sys_regions` VALUES ('368', '6', '0608', '吕梁市');
INSERT INTO `sys_regions` VALUES ('369', '368', '060801', '离石区');
INSERT INTO `sys_regions` VALUES ('370', '368', '060802', '孝义市');
INSERT INTO `sys_regions` VALUES ('371', '368', '060803', '汾阳市');
INSERT INTO `sys_regions` VALUES ('372', '368', '060804', '文水县');
INSERT INTO `sys_regions` VALUES ('373', '368', '060805', '中阳县');
INSERT INTO `sys_regions` VALUES ('374', '368', '060806', '兴县');
INSERT INTO `sys_regions` VALUES ('375', '368', '060807', '临县');
INSERT INTO `sys_regions` VALUES ('376', '368', '060808', '方山县');
INSERT INTO `sys_regions` VALUES ('377', '368', '060809', '柳林县');
INSERT INTO `sys_regions` VALUES ('378', '368', '060810', '岚县');
INSERT INTO `sys_regions` VALUES ('379', '6', '0609', '临汾市');
INSERT INTO `sys_regions` VALUES ('380', '379', '060901', '侯马市');
INSERT INTO `sys_regions` VALUES ('381', '379', '060902', '霍州市');
INSERT INTO `sys_regions` VALUES ('382', '379', '060903', '汾西县');
INSERT INTO `sys_regions` VALUES ('383', '379', '060904', '吉县');
INSERT INTO `sys_regions` VALUES ('384', '379', '060905', '安泽县');
INSERT INTO `sys_regions` VALUES ('386', '379', '060906', '浮山县');
INSERT INTO `sys_regions` VALUES ('387', '379', '060907', '大宁县');
INSERT INTO `sys_regions` VALUES ('388', '379', '060908', '古县');
INSERT INTO `sys_regions` VALUES ('389', '379', '060909', '隰县');
INSERT INTO `sys_regions` VALUES ('390', '379', '060910', '襄汾县');
INSERT INTO `sys_regions` VALUES ('391', '379', '060911', '翼城县');
INSERT INTO `sys_regions` VALUES ('392', '379', '060912', '永和县');
INSERT INTO `sys_regions` VALUES ('393', '379', '060913', '乡宁县');
INSERT INTO `sys_regions` VALUES ('395', '379', '060914', '洪洞县');
INSERT INTO `sys_regions` VALUES ('396', '379', '060915', '蒲县');
INSERT INTO `sys_regions` VALUES ('398', '6', '0610', '运城市');
INSERT INTO `sys_regions` VALUES ('399', '398', '061001', '河津市');
INSERT INTO `sys_regions` VALUES ('400', '398', '061002', '永济市');
INSERT INTO `sys_regions` VALUES ('402', '398', '061003', '新绛县');
INSERT INTO `sys_regions` VALUES ('403', '398', '061004', '平陆县');
INSERT INTO `sys_regions` VALUES ('404', '398', '061005', '垣曲县');
INSERT INTO `sys_regions` VALUES ('405', '398', '061006', '绛县');
INSERT INTO `sys_regions` VALUES ('406', '398', '061007', '稷山县');
INSERT INTO `sys_regions` VALUES ('407', '398', '061008', '芮城县');
INSERT INTO `sys_regions` VALUES ('408', '398', '061009', '夏县');
INSERT INTO `sys_regions` VALUES ('409', '398', '061010', '临猗县');
INSERT INTO `sys_regions` VALUES ('410', '398', '061011', '万荣县');
INSERT INTO `sys_regions` VALUES ('412', '7', '0701', '郑州市');
INSERT INTO `sys_regions` VALUES ('415', '412', '070101', '新密市');
INSERT INTO `sys_regions` VALUES ('416', '412', '070102', '登封市');
INSERT INTO `sys_regions` VALUES ('420', '7', '0702', '开封市');
INSERT INTO `sys_regions` VALUES ('421', '420', '070201', '开封县');
INSERT INTO `sys_regions` VALUES ('422', '420', '070202', '杞县');
INSERT INTO `sys_regions` VALUES ('423', '420', '070203', '兰考县');
INSERT INTO `sys_regions` VALUES ('425', '420', '070204', '尉氏县');
INSERT INTO `sys_regions` VALUES ('427', '7', '0703', '洛阳市');
INSERT INTO `sys_regions` VALUES ('428', '427', '070301', '偃师市');
INSERT INTO `sys_regions` VALUES ('429', '427', '070302', '孟津县');
INSERT INTO `sys_regions` VALUES ('430', '427', '070303', '汝阳县');
INSERT INTO `sys_regions` VALUES ('431', '427', '070304', '伊川县');
INSERT INTO `sys_regions` VALUES ('432', '427', '070305', '洛宁县');
INSERT INTO `sys_regions` VALUES ('434', '427', '070306', '宜阳县');
INSERT INTO `sys_regions` VALUES ('435', '427', '070307', '栾川县');
INSERT INTO `sys_regions` VALUES ('436', '427', '070308', '新安县');
INSERT INTO `sys_regions` VALUES ('438', '7', '0704', '平顶山市');
INSERT INTO `sys_regions` VALUES ('439', '438', '070401', '汝州市');
INSERT INTO `sys_regions` VALUES ('440', '438', '070402', '舞钢市');
INSERT INTO `sys_regions` VALUES ('441', '438', '070403', '郏县');
INSERT INTO `sys_regions` VALUES ('442', '438', '070404', '叶县');
INSERT INTO `sys_regions` VALUES ('443', '438', '070405', '鲁山县');
INSERT INTO `sys_regions` VALUES ('444', '438', '070406', '宝丰县');
INSERT INTO `sys_regions` VALUES ('446', '7', '0705', '焦作市');
INSERT INTO `sys_regions` VALUES ('447', '446', '070501', '沁阳市');
INSERT INTO `sys_regions` VALUES ('448', '446', '070502', '孟州市');
INSERT INTO `sys_regions` VALUES ('449', '446', '070503', '修武县');
INSERT INTO `sys_regions` VALUES ('450', '446', '070504', '温县');
INSERT INTO `sys_regions` VALUES ('451', '446', '070505', '武陟县');
INSERT INTO `sys_regions` VALUES ('452', '446', '070506', '博爱县');
INSERT INTO `sys_regions` VALUES ('453', '446', '070507', '山阳区');
INSERT INTO `sys_regions` VALUES ('454', '7', '0706', '鹤壁市');
INSERT INTO `sys_regions` VALUES ('455', '454', '070601', '浚县');
INSERT INTO `sys_regions` VALUES ('456', '454', '070602', '淇县');
INSERT INTO `sys_regions` VALUES ('457', '454', '070603', '鹤山区');
INSERT INTO `sys_regions` VALUES ('458', '7', '0707', '新乡市');
INSERT INTO `sys_regions` VALUES ('459', '458', '070701', '卫辉市');
INSERT INTO `sys_regions` VALUES ('460', '458', '070702', '辉县市');
INSERT INTO `sys_regions` VALUES ('461', '458', '070703', '新乡县');
INSERT INTO `sys_regions` VALUES ('462', '458', '070704', '获嘉县');
INSERT INTO `sys_regions` VALUES ('463', '458', '070705', '原阳县');
INSERT INTO `sys_regions` VALUES ('464', '458', '070706', '长垣县');
INSERT INTO `sys_regions` VALUES ('465', '458', '070707', '延津县');
INSERT INTO `sys_regions` VALUES ('466', '458', '070708', '封丘县');
INSERT INTO `sys_regions` VALUES ('468', '7', '0708', '安阳市');
INSERT INTO `sys_regions` VALUES ('469', '468', '070801', '林州市');
INSERT INTO `sys_regions` VALUES ('470', '468', '070802', '安阳县');
INSERT INTO `sys_regions` VALUES ('471', '468', '070803', '滑县');
INSERT INTO `sys_regions` VALUES ('472', '468', '070804', '汤阴县');
INSERT INTO `sys_regions` VALUES ('473', '468', '070805', '内黄县');
INSERT INTO `sys_regions` VALUES ('475', '7', '0709', '濮阳市');
INSERT INTO `sys_regions` VALUES ('476', '475', '070901', '濮阳县');
INSERT INTO `sys_regions` VALUES ('477', '475', '070902', '南乐县');
INSERT INTO `sys_regions` VALUES ('478', '475', '070903', '台前县');
INSERT INTO `sys_regions` VALUES ('479', '475', '070904', '清丰县');
INSERT INTO `sys_regions` VALUES ('480', '475', '070905', '范县');
INSERT INTO `sys_regions` VALUES ('481', '475', '070906', '华龙区');
INSERT INTO `sys_regions` VALUES ('482', '7', '0710', '许昌市');
INSERT INTO `sys_regions` VALUES ('483', '482', '071001', '禹州市');
INSERT INTO `sys_regions` VALUES ('484', '482', '071002', '长葛市');
INSERT INTO `sys_regions` VALUES ('485', '482', '071003', '许昌县');
INSERT INTO `sys_regions` VALUES ('486', '482', '071004', '鄢陵县');
INSERT INTO `sys_regions` VALUES ('487', '482', '071005', '襄城县');
INSERT INTO `sys_regions` VALUES ('488', '482', '071006', '魏都区');
INSERT INTO `sys_regions` VALUES ('489', '7', '0711', '漯河市');
INSERT INTO `sys_regions` VALUES ('490', '489', '071101', '郾城区');
INSERT INTO `sys_regions` VALUES ('492', '489', '071102', '临颍县');
INSERT INTO `sys_regions` VALUES ('493', '489', '071103', '召陵区');
INSERT INTO `sys_regions` VALUES ('494', '489', '071104', '舞阳县');
INSERT INTO `sys_regions` VALUES ('495', '7', '0712', '三门峡市');
INSERT INTO `sys_regions` VALUES ('496', '495', '071201', '义马市');
INSERT INTO `sys_regions` VALUES ('497', '495', '071202', '灵宝市');
INSERT INTO `sys_regions` VALUES ('498', '495', '071203', '陕县');
INSERT INTO `sys_regions` VALUES ('499', '495', '071204', '卢氏县');
INSERT INTO `sys_regions` VALUES ('502', '7', '0713', '南阳市');
INSERT INTO `sys_regions` VALUES ('503', '502', '071301', '邓州市');
INSERT INTO `sys_regions` VALUES ('504', '502', '071302', '桐柏县');
INSERT INTO `sys_regions` VALUES ('505', '502', '071303', '方城县');
INSERT INTO `sys_regions` VALUES ('506', '502', '071304', '淅川县');
INSERT INTO `sys_regions` VALUES ('507', '502', '071305', '镇平县');
INSERT INTO `sys_regions` VALUES ('508', '502', '071306', '唐河县');
INSERT INTO `sys_regions` VALUES ('509', '502', '071307', '南召县');
INSERT INTO `sys_regions` VALUES ('510', '502', '071308', '内乡县');
INSERT INTO `sys_regions` VALUES ('511', '502', '071309', '新野县');
INSERT INTO `sys_regions` VALUES ('512', '502', '071310', '社旗县');
INSERT INTO `sys_regions` VALUES ('515', '502', '071311', '西峡县');
INSERT INTO `sys_regions` VALUES ('517', '7', '0714', '商丘市');
INSERT INTO `sys_regions` VALUES ('518', '517', '071401', '永城市');
INSERT INTO `sys_regions` VALUES ('519', '517', '071402', '宁陵县');
INSERT INTO `sys_regions` VALUES ('520', '517', '071403', '虞城县');
INSERT INTO `sys_regions` VALUES ('521', '517', '071404', '民权县');
INSERT INTO `sys_regions` VALUES ('522', '517', '071405', '夏邑县');
INSERT INTO `sys_regions` VALUES ('523', '517', '071406', '柘城县');
INSERT INTO `sys_regions` VALUES ('524', '517', '071407', '睢县');
INSERT INTO `sys_regions` VALUES ('527', '7', '0715', '周口市');
INSERT INTO `sys_regions` VALUES ('529', '527', '071501', '项城市');
INSERT INTO `sys_regions` VALUES ('530', '527', '071502', '商水县');
INSERT INTO `sys_regions` VALUES ('531', '527', '071503', '淮阳县');
INSERT INTO `sys_regions` VALUES ('532', '527', '071504', '太康县');
INSERT INTO `sys_regions` VALUES ('533', '527', '071505', '鹿邑县');
INSERT INTO `sys_regions` VALUES ('534', '527', '071506', '西华县');
INSERT INTO `sys_regions` VALUES ('535', '527', '071507', '扶沟县');
INSERT INTO `sys_regions` VALUES ('536', '527', '071508', '沈丘县');
INSERT INTO `sys_regions` VALUES ('537', '527', '071509', '郸城县');
INSERT INTO `sys_regions` VALUES ('538', '7', '0716', '驻马店市');
INSERT INTO `sys_regions` VALUES ('540', '538', '071601', '确山县');
INSERT INTO `sys_regions` VALUES ('541', '538', '071602', '新蔡县');
INSERT INTO `sys_regions` VALUES ('542', '538', '071603', '上蔡县');
INSERT INTO `sys_regions` VALUES ('543', '538', '071604', '泌阳县');
INSERT INTO `sys_regions` VALUES ('544', '538', '071605', '西平县');
INSERT INTO `sys_regions` VALUES ('545', '538', '071606', '遂平县');
INSERT INTO `sys_regions` VALUES ('546', '538', '071607', '汝南县');
INSERT INTO `sys_regions` VALUES ('547', '538', '071608', '平舆县');
INSERT INTO `sys_regions` VALUES ('548', '538', '071609', '正阳县');
INSERT INTO `sys_regions` VALUES ('549', '7', '0717', '信阳市');
INSERT INTO `sys_regions` VALUES ('551', '549', '071701', '潢川县');
INSERT INTO `sys_regions` VALUES ('552', '549', '071702', '淮滨县');
INSERT INTO `sys_regions` VALUES ('553', '549', '071703', '息县');
INSERT INTO `sys_regions` VALUES ('554', '549', '071704', '新县');
INSERT INTO `sys_regions` VALUES ('556', '549', '071705', '固始县');
INSERT INTO `sys_regions` VALUES ('557', '549', '071706', '罗山县');
INSERT INTO `sys_regions` VALUES ('558', '549', '071707', '光山县');
INSERT INTO `sys_regions` VALUES ('560', '8', '0801', '沈阳市');
INSERT INTO `sys_regions` VALUES ('567', '560', '080101', '苏家屯区');
INSERT INTO `sys_regions` VALUES ('569', '560', '080102', '新民市');
INSERT INTO `sys_regions` VALUES ('570', '560', '080103', '法库县');
INSERT INTO `sys_regions` VALUES ('571', '560', '080104', '辽中县');
INSERT INTO `sys_regions` VALUES ('572', '560', '080105', '康平县');
INSERT INTO `sys_regions` VALUES ('573', '8', '0802', '大连市');
INSERT INTO `sys_regions` VALUES ('574', '573', '080201', '普兰店市');
INSERT INTO `sys_regions` VALUES ('575', '573', '080202', '瓦房店市');
INSERT INTO `sys_regions` VALUES ('576', '573', '080203', '庄河市');
INSERT INTO `sys_regions` VALUES ('577', '573', '080204', '长海县');
INSERT INTO `sys_regions` VALUES ('579', '8', '0803', '鞍山市');
INSERT INTO `sys_regions` VALUES ('580', '579', '080301', '台安县');
INSERT INTO `sys_regions` VALUES ('581', '579', '080302', '海城市');
INSERT INTO `sys_regions` VALUES ('583', '579', '080303', '岫岩县');
INSERT INTO `sys_regions` VALUES ('584', '8', '0804', '抚顺市');
INSERT INTO `sys_regions` VALUES ('585', '584', '080401', '抚顺县');
INSERT INTO `sys_regions` VALUES ('586', '584', '080402', '新宾县');
INSERT INTO `sys_regions` VALUES ('587', '584', '080403', '清原县');
INSERT INTO `sys_regions` VALUES ('589', '8', '0805', '本溪市');
INSERT INTO `sys_regions` VALUES ('591', '589', '080501', '桓仁县');
INSERT INTO `sys_regions` VALUES ('593', '8', '0806', '丹东市');
INSERT INTO `sys_regions` VALUES ('596', '593', '080601', '宽甸县');
INSERT INTO `sys_regions` VALUES ('598', '8', '0807', '锦州市');
INSERT INTO `sys_regions` VALUES ('599', '598', '080701', '义县');
INSERT INTO `sys_regions` VALUES ('600', '598', '080702', '凌海市');
INSERT INTO `sys_regions` VALUES ('601', '598', '080703', '北镇市');
INSERT INTO `sys_regions` VALUES ('602', '598', '080704', '黑山县');
INSERT INTO `sys_regions` VALUES ('604', '8', '0808', '葫芦岛市');
INSERT INTO `sys_regions` VALUES ('606', '604', '080801', '绥中县');
INSERT INTO `sys_regions` VALUES ('607', '604', '080802', '建昌县');
INSERT INTO `sys_regions` VALUES ('608', '604', '080803', '南票区');
INSERT INTO `sys_regions` VALUES ('609', '8', '0809', '营口市');
INSERT INTO `sys_regions` VALUES ('610', '609', '080901', '大石桥市');
INSERT INTO `sys_regions` VALUES ('611', '609', '080902', '盖州市');
INSERT INTO `sys_regions` VALUES ('613', '8', '0810', '盘锦市');
INSERT INTO `sys_regions` VALUES ('614', '613', '081001', '盘山县');
INSERT INTO `sys_regions` VALUES ('615', '613', '081002', '大洼县');
INSERT INTO `sys_regions` VALUES ('617', '8', '0811', '阜新市');
INSERT INTO `sys_regions` VALUES ('618', '617', '081101', '阜新县');
INSERT INTO `sys_regions` VALUES ('619', '617', '081102', '彰武县');
INSERT INTO `sys_regions` VALUES ('621', '8', '0812', '辽阳市');
INSERT INTO `sys_regions` VALUES ('623', '621', '081201', '辽阳县');
INSERT INTO `sys_regions` VALUES ('632', '8', '0813', '朝阳市');
INSERT INTO `sys_regions` VALUES ('633', '632', '081301', '凌源市');
INSERT INTO `sys_regions` VALUES ('634', '632', '081302', '北票市');
INSERT INTO `sys_regions` VALUES ('635', '632', '081303', '喀喇沁左翼县');
INSERT INTO `sys_regions` VALUES ('636', '632', '081304', '朝阳县');
INSERT INTO `sys_regions` VALUES ('637', '632', '081305', '建平县');
INSERT INTO `sys_regions` VALUES ('639', '9', '0901', '长春市');
INSERT INTO `sys_regions` VALUES ('640', '639', '090101', '榆树市');
INSERT INTO `sys_regions` VALUES ('641', '639', '090102', '九台市');
INSERT INTO `sys_regions` VALUES ('642', '639', '090103', '农安县');
INSERT INTO `sys_regions` VALUES ('644', '9', '0902', '吉林市');
INSERT INTO `sys_regions` VALUES ('645', '644', '090201', '舒兰市');
INSERT INTO `sys_regions` VALUES ('646', '644', '090202', '桦甸市');
INSERT INTO `sys_regions` VALUES ('647', '644', '090203', '蛟河市');
INSERT INTO `sys_regions` VALUES ('648', '644', '090204', '磐石市');
INSERT INTO `sys_regions` VALUES ('649', '644', '090205', '永吉县');
INSERT INTO `sys_regions` VALUES ('651', '9', '0903', '四平市');
INSERT INTO `sys_regions` VALUES ('652', '651', '090301', '公主岭市');
INSERT INTO `sys_regions` VALUES ('653', '651', '090302', '双辽市');
INSERT INTO `sys_regions` VALUES ('654', '651', '090303', '梨树县');
INSERT INTO `sys_regions` VALUES ('656', '651', '090304', '伊通县');
INSERT INTO `sys_regions` VALUES ('657', '9', '0904', '通化市');
INSERT INTO `sys_regions` VALUES ('658', '657', '090401', '梅河口市');
INSERT INTO `sys_regions` VALUES ('659', '657', '090402', '集安市');
INSERT INTO `sys_regions` VALUES ('660', '657', '090403', '通化县');
INSERT INTO `sys_regions` VALUES ('661', '657', '090404', '辉南县');
INSERT INTO `sys_regions` VALUES ('662', '657', '090405', '柳河县');
INSERT INTO `sys_regions` VALUES ('663', '657', '090406', '二道江区');
INSERT INTO `sys_regions` VALUES ('664', '9', '0905', '白山市');
INSERT INTO `sys_regions` VALUES ('665', '664', '090501', '临江市');
INSERT INTO `sys_regions` VALUES ('669', '664', '090502', '江源区');
INSERT INTO `sys_regions` VALUES ('671', '664', '090503', '靖宇县');
INSERT INTO `sys_regions` VALUES ('672', '664', '090504', '抚松县');
INSERT INTO `sys_regions` VALUES ('673', '664', '090505', '长白县');
INSERT INTO `sys_regions` VALUES ('674', '9', '0906', '松原市');
INSERT INTO `sys_regions` VALUES ('675', '674', '090601', '乾安县');
INSERT INTO `sys_regions` VALUES ('676', '674', '090602', '长岭县');
INSERT INTO `sys_regions` VALUES ('677', '674', '090603', '扶余县');
INSERT INTO `sys_regions` VALUES ('681', '9', '0907', '白城市');
INSERT INTO `sys_regions` VALUES ('682', '681', '090701', '大安市');
INSERT INTO `sys_regions` VALUES ('683', '681', '090702', '洮南市');
INSERT INTO `sys_regions` VALUES ('684', '681', '090703', '通榆县');
INSERT INTO `sys_regions` VALUES ('685', '681', '090704', '镇赉县');
INSERT INTO `sys_regions` VALUES ('686', '681', '090705', '洮北区');
INSERT INTO `sys_regions` VALUES ('687', '9', '0908', '延边州');
INSERT INTO `sys_regions` VALUES ('698', '10', '1001', '哈尔滨市');
INSERT INTO `sys_regions` VALUES ('699', '698', '100101', '阿城区');
INSERT INTO `sys_regions` VALUES ('700', '698', '100102', '尚志市');
INSERT INTO `sys_regions` VALUES ('701', '698', '100103', '双城市');
INSERT INTO `sys_regions` VALUES ('702', '698', '100104', '五常市');
INSERT INTO `sys_regions` VALUES ('704', '698', '100105', '方正县');
INSERT INTO `sys_regions` VALUES ('705', '698', '100106', '宾县');
INSERT INTO `sys_regions` VALUES ('706', '698', '100107', '依兰县');
INSERT INTO `sys_regions` VALUES ('707', '698', '100108', '巴彦县');
INSERT INTO `sys_regions` VALUES ('708', '698', '100109', '通河县');
INSERT INTO `sys_regions` VALUES ('709', '698', '100110', '木兰县');
INSERT INTO `sys_regions` VALUES ('710', '698', '100111', '延寿县');
INSERT INTO `sys_regions` VALUES ('712', '10', '1002', '齐齐哈尔市');
INSERT INTO `sys_regions` VALUES ('713', '712', '100201', '梅里斯区');
INSERT INTO `sys_regions` VALUES ('714', '712', '100202', '昂昂溪区');
INSERT INTO `sys_regions` VALUES ('715', '712', '100203', '富拉尔基区');
INSERT INTO `sys_regions` VALUES ('716', '712', '100204', '碾子山区');
INSERT INTO `sys_regions` VALUES ('717', '712', '100205', '讷河市');
INSERT INTO `sys_regions` VALUES ('718', '712', '100206', '富裕县');
INSERT INTO `sys_regions` VALUES ('719', '712', '100207', '拜泉县');
INSERT INTO `sys_regions` VALUES ('720', '712', '100208', '甘南县');
INSERT INTO `sys_regions` VALUES ('721', '712', '100209', '依安县');
INSERT INTO `sys_regions` VALUES ('722', '712', '100210', '克山县');
INSERT INTO `sys_regions` VALUES ('723', '712', '100211', '龙江县');
INSERT INTO `sys_regions` VALUES ('724', '712', '100212', '克东县');
INSERT INTO `sys_regions` VALUES ('725', '712', '100213', '泰来县');
INSERT INTO `sys_regions` VALUES ('727', '10', '1003', '鹤岗市');
INSERT INTO `sys_regions` VALUES ('728', '727', '100301', '萝北县');
INSERT INTO `sys_regions` VALUES ('729', '727', '100302', '绥滨县');
INSERT INTO `sys_regions` VALUES ('731', '10', '1004', '双鸭山市');
INSERT INTO `sys_regions` VALUES ('733', '731', '100401', '集贤县');
INSERT INTO `sys_regions` VALUES ('734', '731', '100402', '宝清县');
INSERT INTO `sys_regions` VALUES ('735', '731', '100403', '友谊县');
INSERT INTO `sys_regions` VALUES ('736', '731', '100404', '饶河县');
INSERT INTO `sys_regions` VALUES ('737', '10', '1005', '鸡西市');
INSERT INTO `sys_regions` VALUES ('739', '737', '100501', '密山市');
INSERT INTO `sys_regions` VALUES ('740', '737', '100502', '虎林市');
INSERT INTO `sys_regions` VALUES ('741', '737', '100503', '鸡东县');
INSERT INTO `sys_regions` VALUES ('742', '10', '1006', '大庆市');
INSERT INTO `sys_regions` VALUES ('744', '742', '100601', '萨尔图区');
INSERT INTO `sys_regions` VALUES ('745', '742', '100602', '龙凤区');
INSERT INTO `sys_regions` VALUES ('746', '742', '100603', '让胡路区');
INSERT INTO `sys_regions` VALUES ('747', '742', '100604', '红岗区');
INSERT INTO `sys_regions` VALUES ('748', '742', '100605', '大同区');
INSERT INTO `sys_regions` VALUES ('749', '742', '100606', '林甸县');
INSERT INTO `sys_regions` VALUES ('750', '742', '100607', '肇州县');
INSERT INTO `sys_regions` VALUES ('751', '742', '100608', '肇源县');
INSERT INTO `sys_regions` VALUES ('752', '742', '100609', '杜尔伯特县');
INSERT INTO `sys_regions` VALUES ('753', '10', '1007', '伊春市');
INSERT INTO `sys_regions` VALUES ('754', '753', '100701', '铁力市');
INSERT INTO `sys_regions` VALUES ('755', '753', '100702', '嘉荫县');
INSERT INTO `sys_regions` VALUES ('757', '10', '1008', '牡丹江市');
INSERT INTO `sys_regions` VALUES ('758', '757', '100801', '海林市');
INSERT INTO `sys_regions` VALUES ('760', '757', '100802', '宁安市');
INSERT INTO `sys_regions` VALUES ('761', '757', '100803', '穆棱市');
INSERT INTO `sys_regions` VALUES ('762', '757', '100804', '林口县');
INSERT INTO `sys_regions` VALUES ('763', '757', '100805', '东宁县');
INSERT INTO `sys_regions` VALUES ('765', '10', '1009', '佳木斯市');
INSERT INTO `sys_regions` VALUES ('766', '765', '100901', '同江市');
INSERT INTO `sys_regions` VALUES ('767', '765', '100902', '富锦市');
INSERT INTO `sys_regions` VALUES ('768', '765', '100903', '桦川县');
INSERT INTO `sys_regions` VALUES ('769', '765', '100904', '抚远县');
INSERT INTO `sys_regions` VALUES ('770', '765', '100905', '桦南县');
INSERT INTO `sys_regions` VALUES ('771', '765', '100906', '汤原县');
INSERT INTO `sys_regions` VALUES ('773', '10', '1010', '七台河市');
INSERT INTO `sys_regions` VALUES ('774', '773', '101001', '勃利县');
INSERT INTO `sys_regions` VALUES ('776', '10', '1011', '黑河市');
INSERT INTO `sys_regions` VALUES ('777', '776', '101101', '北安市');
INSERT INTO `sys_regions` VALUES ('778', '776', '101102', '五大连池市');
INSERT INTO `sys_regions` VALUES ('779', '776', '101103', '逊克县');
INSERT INTO `sys_regions` VALUES ('780', '776', '101104', '孙吴县');
INSERT INTO `sys_regions` VALUES ('782', '10', '1012', '绥化市');
INSERT INTO `sys_regions` VALUES ('784', '782', '101201', '安达市');
INSERT INTO `sys_regions` VALUES ('785', '782', '101202', '肇东市');
INSERT INTO `sys_regions` VALUES ('786', '782', '101203', '海伦市');
INSERT INTO `sys_regions` VALUES ('787', '782', '101204', '绥棱县');
INSERT INTO `sys_regions` VALUES ('788', '782', '101205', '兰西县');
INSERT INTO `sys_regions` VALUES ('789', '782', '101206', '明水县');
INSERT INTO `sys_regions` VALUES ('790', '782', '101207', '青冈县');
INSERT INTO `sys_regions` VALUES ('791', '782', '101208', '庆安县');
INSERT INTO `sys_regions` VALUES ('792', '782', '101209', '望奎县');
INSERT INTO `sys_regions` VALUES ('793', '10', '1013', '大兴安岭地区');
INSERT INTO `sys_regions` VALUES ('794', '793', '101301', '呼玛县');
INSERT INTO `sys_regions` VALUES ('795', '793', '101302', '塔河县');
INSERT INTO `sys_regions` VALUES ('796', '793', '101303', '漠河县');
INSERT INTO `sys_regions` VALUES ('799', '11', '1101', '呼和浩特市');
INSERT INTO `sys_regions` VALUES ('801', '799', '110101', '土默特左旗');
INSERT INTO `sys_regions` VALUES ('802', '799', '110102', '和林格尔县');
INSERT INTO `sys_regions` VALUES ('803', '799', '110103', '武川县');
INSERT INTO `sys_regions` VALUES ('804', '799', '110104', '托克托县');
INSERT INTO `sys_regions` VALUES ('805', '11', '1102', '包头市');
INSERT INTO `sys_regions` VALUES ('807', '805', '110201', '固阳县');
INSERT INTO `sys_regions` VALUES ('808', '805', '110202', '土默特右旗');
INSERT INTO `sys_regions` VALUES ('809', '805', '110203', '达茂联合旗');
INSERT INTO `sys_regions` VALUES ('810', '11', '1103', '乌海市');
INSERT INTO `sys_regions` VALUES ('811', '810', '110301', '海勃湾区');
INSERT INTO `sys_regions` VALUES ('812', '11', '1104', '赤峰市');
INSERT INTO `sys_regions` VALUES ('814', '812', '110401', '宁城县');
INSERT INTO `sys_regions` VALUES ('815', '812', '110402', '敖汉旗');
INSERT INTO `sys_regions` VALUES ('816', '812', '110403', '喀喇沁旗');
INSERT INTO `sys_regions` VALUES ('817', '812', '110404', '翁牛特旗');
INSERT INTO `sys_regions` VALUES ('818', '812', '110405', '巴林右旗');
INSERT INTO `sys_regions` VALUES ('819', '812', '110406', '林西县');
INSERT INTO `sys_regions` VALUES ('820', '812', '110407', '克什克腾旗');
INSERT INTO `sys_regions` VALUES ('821', '812', '110408', '巴林左旗');
INSERT INTO `sys_regions` VALUES ('822', '812', '110409', '阿鲁科尔沁旗');
INSERT INTO `sys_regions` VALUES ('823', '11', '1105', '乌兰察布市');
INSERT INTO `sys_regions` VALUES ('824', '823', '110501', '集宁区');
INSERT INTO `sys_regions` VALUES ('825', '823', '110502', '丰镇市');
INSERT INTO `sys_regions` VALUES ('826', '823', '110503', '兴和县');
INSERT INTO `sys_regions` VALUES ('827', '823', '110504', '卓资县');
INSERT INTO `sys_regions` VALUES ('828', '823', '110505', '商都县');
INSERT INTO `sys_regions` VALUES ('829', '823', '110506', '凉城县');
INSERT INTO `sys_regions` VALUES ('830', '823', '110507', '化德县');
INSERT INTO `sys_regions` VALUES ('831', '823', '110508', '察哈尔右翼前旗');
INSERT INTO `sys_regions` VALUES ('832', '823', '110509', '察哈尔右翼中旗');
INSERT INTO `sys_regions` VALUES ('833', '823', '110510', '察哈尔右翼后旗');
INSERT INTO `sys_regions` VALUES ('834', '823', '110511', '四子王旗');
INSERT INTO `sys_regions` VALUES ('835', '11', '1106', '锡林郭勒盟');
INSERT INTO `sys_regions` VALUES ('836', '835', '110601', '锡林浩特市');
INSERT INTO `sys_regions` VALUES ('837', '835', '110602', '二连浩特市');
INSERT INTO `sys_regions` VALUES ('838', '835', '110603', '多伦县');
INSERT INTO `sys_regions` VALUES ('839', '835', '110604', '阿巴嘎旗');
INSERT INTO `sys_regions` VALUES ('840', '835', '110605', '西乌珠穆沁旗');
INSERT INTO `sys_regions` VALUES ('841', '835', '110606', '东乌珠穆沁旗');
INSERT INTO `sys_regions` VALUES ('842', '835', '110607', '苏尼特右旗');
INSERT INTO `sys_regions` VALUES ('843', '835', '110608', '苏尼特左旗');
INSERT INTO `sys_regions` VALUES ('844', '835', '110609', '太仆寺旗');
INSERT INTO `sys_regions` VALUES ('845', '835', '110610', '正镶白旗');
INSERT INTO `sys_regions` VALUES ('846', '835', '110611', '正蓝旗');
INSERT INTO `sys_regions` VALUES ('847', '835', '110612', '镶黄旗');
INSERT INTO `sys_regions` VALUES ('848', '11', '1107', '呼伦贝尔市');
INSERT INTO `sys_regions` VALUES ('849', '848', '110701', '海拉尔区');
INSERT INTO `sys_regions` VALUES ('850', '848', '110702', '满洲里市');
INSERT INTO `sys_regions` VALUES ('851', '848', '110703', '牙克石市');
INSERT INTO `sys_regions` VALUES ('852', '848', '110704', '扎兰屯市');
INSERT INTO `sys_regions` VALUES ('853', '848', '110705', '根河市');
INSERT INTO `sys_regions` VALUES ('854', '848', '110706', '额尔古纳市');
INSERT INTO `sys_regions` VALUES ('855', '848', '110707', '陈巴尔虎旗');
INSERT INTO `sys_regions` VALUES ('856', '848', '110708', '阿荣旗');
INSERT INTO `sys_regions` VALUES ('857', '848', '110709', '新巴尔虎左旗');
INSERT INTO `sys_regions` VALUES ('858', '848', '110710', '新巴尔虎右旗');
INSERT INTO `sys_regions` VALUES ('859', '848', '110711', '鄂伦春旗');
INSERT INTO `sys_regions` VALUES ('860', '848', '110712', '莫力达瓦旗');
INSERT INTO `sys_regions` VALUES ('861', '848', '110713', '鄂温克族旗');
INSERT INTO `sys_regions` VALUES ('870', '11', '1108', '鄂尔多斯市');
INSERT INTO `sys_regions` VALUES ('871', '870', '110801', '东胜区');
INSERT INTO `sys_regions` VALUES ('872', '870', '110802', '准格尔旗');
INSERT INTO `sys_regions` VALUES ('874', '870', '110803', '伊金霍洛旗');
INSERT INTO `sys_regions` VALUES ('875', '870', '110804', '乌审旗');
INSERT INTO `sys_regions` VALUES ('876', '870', '110805', '杭锦旗');
INSERT INTO `sys_regions` VALUES ('877', '870', '110806', '鄂托克旗');
INSERT INTO `sys_regions` VALUES ('878', '870', '110807', '鄂托克前旗');
INSERT INTO `sys_regions` VALUES ('879', '870', '110808', '达拉特旗');
INSERT INTO `sys_regions` VALUES ('880', '11', '1109', '巴彦淖尔市');
INSERT INTO `sys_regions` VALUES ('881', '880', '110901', '临河区');
INSERT INTO `sys_regions` VALUES ('882', '880', '110902', '五原县');
INSERT INTO `sys_regions` VALUES ('883', '880', '110903', '磴口县');
INSERT INTO `sys_regions` VALUES ('884', '880', '110904', '杭锦后旗');
INSERT INTO `sys_regions` VALUES ('885', '880', '110905', '乌拉特中旗');
INSERT INTO `sys_regions` VALUES ('888', '880', '110906', '乌拉特后旗 ');
INSERT INTO `sys_regions` VALUES ('890', '880', '110907', '乌拉特前旗');
INSERT INTO `sys_regions` VALUES ('891', '11', '1110', '阿拉善盟');
INSERT INTO `sys_regions` VALUES ('892', '891', '111001', '阿拉善右旗');
INSERT INTO `sys_regions` VALUES ('893', '891', '111002', '阿拉善左旗');
INSERT INTO `sys_regions` VALUES ('894', '891', '111003', '额济纳旗');
INSERT INTO `sys_regions` VALUES ('895', '11', '1111', '兴安盟');
INSERT INTO `sys_regions` VALUES ('896', '895', '111101', '乌兰浩特市');
INSERT INTO `sys_regions` VALUES ('897', '895', '111102', '阿尔山市');
INSERT INTO `sys_regions` VALUES ('898', '895', '111103', '突泉县');
INSERT INTO `sys_regions` VALUES ('899', '895', '111104', '扎赉特旗');
INSERT INTO `sys_regions` VALUES ('900', '895', '111105', '科尔沁右翼前旗');
INSERT INTO `sys_regions` VALUES ('901', '895', '111106', '科尔沁右翼中旗');
INSERT INTO `sys_regions` VALUES ('902', '11', '1112', '通辽市');
INSERT INTO `sys_regions` VALUES ('904', '12', '1201', '南京市');
INSERT INTO `sys_regions` VALUES ('905', '904', '120101', '江宁区');
INSERT INTO `sys_regions` VALUES ('907', '904', '120102', '高淳区');
INSERT INTO `sys_regions` VALUES ('908', '904', '120103', '六合区');
INSERT INTO `sys_regions` VALUES ('911', '12', '1202', '徐州市');
INSERT INTO `sys_regions` VALUES ('914', '911', '120201', '铜山区');
INSERT INTO `sys_regions` VALUES ('915', '911', '120202', '睢宁县');
INSERT INTO `sys_regions` VALUES ('916', '911', '120203', '沛县');
INSERT INTO `sys_regions` VALUES ('917', '911', '120204', '丰县');
INSERT INTO `sys_regions` VALUES ('919', '12', '1203', '连云港市');
INSERT INTO `sys_regions` VALUES ('920', '919', '120301', '赣榆区');
INSERT INTO `sys_regions` VALUES ('921', '919', '120302', '灌云县');
INSERT INTO `sys_regions` VALUES ('922', '919', '120303', '东海县');
INSERT INTO `sys_regions` VALUES ('923', '919', '120304', '灌南县');
INSERT INTO `sys_regions` VALUES ('925', '12', '1204', '淮安市');
INSERT INTO `sys_regions` VALUES ('926', '925', '120401', '楚州区');
INSERT INTO `sys_regions` VALUES ('929', '925', '120402', '洪泽县');
INSERT INTO `sys_regions` VALUES ('930', '925', '120403', '金湖县');
INSERT INTO `sys_regions` VALUES ('931', '925', '120404', '盱眙县');
INSERT INTO `sys_regions` VALUES ('933', '12', '1205', '宿迁市');
INSERT INTO `sys_regions` VALUES ('934', '933', '120501', '宿豫区');
INSERT INTO `sys_regions` VALUES ('937', '933', '120502', '泗洪县');
INSERT INTO `sys_regions` VALUES ('939', '12', '1206', '盐城市');
INSERT INTO `sys_regions` VALUES ('940', '939', '120601', '东台市');
INSERT INTO `sys_regions` VALUES ('941', '939', '120602', '大丰区');
INSERT INTO `sys_regions` VALUES ('945', '939', '120603', '建湖县');
INSERT INTO `sys_regions` VALUES ('946', '939', '120604', '响水县');
INSERT INTO `sys_regions` VALUES ('948', '939', '120605', '阜宁县');
INSERT INTO `sys_regions` VALUES ('949', '939', '120606', '滨海县');
INSERT INTO `sys_regions` VALUES ('951', '12', '1207', '扬州市');
INSERT INTO `sys_regions` VALUES ('955', '951', '120701', '广陵区');
INSERT INTO `sys_regions` VALUES ('956', '951', '120702', '邗江区');
INSERT INTO `sys_regions` VALUES ('957', '951', '120703', '宝应县');
INSERT INTO `sys_regions` VALUES ('959', '12', '1208', '泰州市');
INSERT INTO `sys_regions` VALUES ('960', '959', '120801', '泰兴市');
INSERT INTO `sys_regions` VALUES ('962', '959', '120802', '靖江市');
INSERT INTO `sys_regions` VALUES ('963', '959', '120803', '兴化市');
INSERT INTO `sys_regions` VALUES ('965', '12', '1209', '南通市');
INSERT INTO `sys_regions` VALUES ('967', '965', '120901', '通州区');
INSERT INTO `sys_regions` VALUES ('970', '965', '120902', '如东县');
INSERT INTO `sys_regions` VALUES ('972', '12', '1210', '镇江市');
INSERT INTO `sys_regions` VALUES ('973', '972', '121001', '扬中市');
INSERT INTO `sys_regions` VALUES ('976', '972', '121002', '丹徒区');
INSERT INTO `sys_regions` VALUES ('978', '12', '1211', '常州市');
INSERT INTO `sys_regions` VALUES ('980', '978', '121101', '金坛区');
INSERT INTO `sys_regions` VALUES ('981', '978', '121102', '溧阳市');
INSERT INTO `sys_regions` VALUES ('984', '12', '1212', '无锡市');
INSERT INTO `sys_regions` VALUES ('988', '12', '1213', '苏州市');
INSERT INTO `sys_regions` VALUES ('993', '988', '121301', '常熟市');
INSERT INTO `sys_regions` VALUES ('994', '988', '121302', '张家港市');
INSERT INTO `sys_regions` VALUES ('1000', '13', '1301', '济南市');
INSERT INTO `sys_regions` VALUES ('1002', '1000', '130101', '长清区');
INSERT INTO `sys_regions` VALUES ('1003', '1000', '130102', '平阴县');
INSERT INTO `sys_regions` VALUES ('1004', '1000', '130103', '济阳县');
INSERT INTO `sys_regions` VALUES ('1005', '1000', '130104', '商河县');
INSERT INTO `sys_regions` VALUES ('1007', '13', '1302', '青岛市');
INSERT INTO `sys_regions` VALUES ('1014', '1007', '130201', '莱西市');
INSERT INTO `sys_regions` VALUES ('1016', '13', '1303', '淄博市');
INSERT INTO `sys_regions` VALUES ('1019', '1016', '130301', '高青县');
INSERT INTO `sys_regions` VALUES ('1020', '1016', '130302', '沂源县');
INSERT INTO `sys_regions` VALUES ('1021', '1016', '130303', '桓台县');
INSERT INTO `sys_regions` VALUES ('1022', '13', '1304', '枣庄市');
INSERT INTO `sys_regions` VALUES ('1025', '13', '1305', '东营市');
INSERT INTO `sys_regions` VALUES ('1026', '1025', '130501', '河口区');
INSERT INTO `sys_regions` VALUES ('1027', '1025', '130502', '广饶县');
INSERT INTO `sys_regions` VALUES ('1028', '1025', '130503', '利津县');
INSERT INTO `sys_regions` VALUES ('1029', '1025', '130504', '垦利区');
INSERT INTO `sys_regions` VALUES ('1032', '13', '1306', '潍坊市');
INSERT INTO `sys_regions` VALUES ('1033', '1032', '130601', '青州市');
INSERT INTO `sys_regions` VALUES ('1034', '1032', '130602', '诸城市');
INSERT INTO `sys_regions` VALUES ('1036', '1032', '130603', '安丘市');
INSERT INTO `sys_regions` VALUES ('1037', '1032', '130604', '高密市');
INSERT INTO `sys_regions` VALUES ('1038', '1032', '130605', '昌邑市');
INSERT INTO `sys_regions` VALUES ('1039', '1032', '130606', '昌乐县');
INSERT INTO `sys_regions` VALUES ('1041', '1032', '130607', '临朐县');
INSERT INTO `sys_regions` VALUES ('1042', '13', '1307', '烟台市');
INSERT INTO `sys_regions` VALUES ('1044', '1042', '130701', '莱阳市');
INSERT INTO `sys_regions` VALUES ('1047', '1042', '130702', '招远市');
INSERT INTO `sys_regions` VALUES ('1048', '1042', '130703', '蓬莱市');
INSERT INTO `sys_regions` VALUES ('1049', '1042', '130704', '栖霞市');
INSERT INTO `sys_regions` VALUES ('1050', '1042', '130705', '海阳市');
INSERT INTO `sys_regions` VALUES ('1051', '1042', '130706', '长岛县');
INSERT INTO `sys_regions` VALUES ('1053', '13', '1308', '威海市');
INSERT INTO `sys_regions` VALUES ('1054', '1053', '130801', '乳山市');
INSERT INTO `sys_regions` VALUES ('1058', '13', '1309', '莱芜市');
INSERT INTO `sys_regions` VALUES ('1059', '1058', '130901', '莱城区');
INSERT INTO `sys_regions` VALUES ('1060', '13', '1310', '德州市');
INSERT INTO `sys_regions` VALUES ('1061', '1060', '131001', '乐陵市');
INSERT INTO `sys_regions` VALUES ('1062', '1060', '131002', '禹城市');
INSERT INTO `sys_regions` VALUES ('1063', '1060', '131003', '陵县');
INSERT INTO `sys_regions` VALUES ('1064', '1060', '131004', '宁津县');
INSERT INTO `sys_regions` VALUES ('1066', '1060', '131005', '武城县');
INSERT INTO `sys_regions` VALUES ('1067', '1060', '131006', '庆云县');
INSERT INTO `sys_regions` VALUES ('1068', '1060', '131007', '平原县');
INSERT INTO `sys_regions` VALUES ('1069', '1060', '131008', '临邑县');
INSERT INTO `sys_regions` VALUES ('1071', '1060', '131009', '夏津县');
INSERT INTO `sys_regions` VALUES ('1072', '13', '1311', '临沂市');
INSERT INTO `sys_regions` VALUES ('1073', '1072', '131101', '沂南县');
INSERT INTO `sys_regions` VALUES ('1074', '1072', '131102', '沂水县');
INSERT INTO `sys_regions` VALUES ('1076', '1072', '131103', '费县');
INSERT INTO `sys_regions` VALUES ('1077', '1072', '131104', '平邑县');
INSERT INTO `sys_regions` VALUES ('1078', '1072', '131105', '蒙阴县');
INSERT INTO `sys_regions` VALUES ('1079', '1072', '131106', '临沭县');
INSERT INTO `sys_regions` VALUES ('1081', '13', '1312', '聊城市');
INSERT INTO `sys_regions` VALUES ('1082', '1081', '131201', '临清市');
INSERT INTO `sys_regions` VALUES ('1084', '1081', '131202', '阳谷县');
INSERT INTO `sys_regions` VALUES ('1085', '1081', '131203', '茌平县');
INSERT INTO `sys_regions` VALUES ('1086', '1081', '131204', '莘县');
INSERT INTO `sys_regions` VALUES ('1087', '1081', '131205', '东阿县');
INSERT INTO `sys_regions` VALUES ('1088', '1081', '131206', '冠县');
INSERT INTO `sys_regions` VALUES ('1090', '13', '1313', '滨州市');
INSERT INTO `sys_regions` VALUES ('1092', '1090', '131301', '邹平县');
INSERT INTO `sys_regions` VALUES ('1093', '1090', '131302', '沾化县');
INSERT INTO `sys_regions` VALUES ('1094', '1090', '131303', '惠民县');
INSERT INTO `sys_regions` VALUES ('1095', '1090', '131304', '博兴县');
INSERT INTO `sys_regions` VALUES ('1096', '1090', '131305', '阳信县');
INSERT INTO `sys_regions` VALUES ('1099', '13', '1314', '菏泽市');
INSERT INTO `sys_regions` VALUES ('1101', '1099', '131401', '单县');
INSERT INTO `sys_regions` VALUES ('1102', '1099', '131402', '曹县');
INSERT INTO `sys_regions` VALUES ('1103', '1099', '131403', '定陶县');
INSERT INTO `sys_regions` VALUES ('1104', '1099', '131404', '巨野县');
INSERT INTO `sys_regions` VALUES ('1105', '1099', '131405', '成武县');
INSERT INTO `sys_regions` VALUES ('1106', '1099', '131406', '东明县');
INSERT INTO `sys_regions` VALUES ('1107', '1099', '131407', '郓城县');
INSERT INTO `sys_regions` VALUES ('1108', '13', '1315', '日照市');
INSERT INTO `sys_regions` VALUES ('1112', '13', '1316', '泰安市');
INSERT INTO `sys_regions` VALUES ('1114', '14', '1401', '铜陵市');
INSERT INTO `sys_regions` VALUES ('1116', '14', '1402', '合肥市');
INSERT INTO `sys_regions` VALUES ('1119', '1116', '140201', '肥东县');
INSERT INTO `sys_regions` VALUES ('1121', '14', '1403', '淮南市');
INSERT INTO `sys_regions` VALUES ('1122', '1121', '140301', '凤台县');
INSERT INTO `sys_regions` VALUES ('1124', '14', '1404', '淮北市');
INSERT INTO `sys_regions` VALUES ('1127', '14', '1405', '芜湖市');
INSERT INTO `sys_regions` VALUES ('1128', '1127', '140501', '芜湖县');
INSERT INTO `sys_regions` VALUES ('1129', '1127', '140502', '繁昌县');
INSERT INTO `sys_regions` VALUES ('1130', '1127', '140503', '南陵县');
INSERT INTO `sys_regions` VALUES ('1132', '14', '1406', '蚌埠市');
INSERT INTO `sys_regions` VALUES ('1133', '1132', '140601', '怀远县');
INSERT INTO `sys_regions` VALUES ('1134', '1132', '140602', '固镇县');
INSERT INTO `sys_regions` VALUES ('1135', '1132', '140603', '五河县');
INSERT INTO `sys_regions` VALUES ('1137', '14', '1407', '马鞍山市');
INSERT INTO `sys_regions` VALUES ('1138', '1137', '140701', '当涂县');
INSERT INTO `sys_regions` VALUES ('1140', '14', '1408', '安庆市');
INSERT INTO `sys_regions` VALUES ('1141', '1140', '140801', '桐城市');
INSERT INTO `sys_regions` VALUES ('1142', '1140', '140802', '宿松县');
INSERT INTO `sys_regions` VALUES ('1144', '1140', '140803', '太湖县');
INSERT INTO `sys_regions` VALUES ('1145', '1140', '140804', '怀宁县');
INSERT INTO `sys_regions` VALUES ('1146', '1140', '140805', '岳西县');
INSERT INTO `sys_regions` VALUES ('1147', '1140', '140806', '望江县');
INSERT INTO `sys_regions` VALUES ('1148', '1140', '140807', '潜山县');
INSERT INTO `sys_regions` VALUES ('1151', '14', '1409', '黄山市');
INSERT INTO `sys_regions` VALUES ('1153', '1151', '140901', '休宁县');
INSERT INTO `sys_regions` VALUES ('1154', '1151', '140902', '歙县');
INSERT INTO `sys_regions` VALUES ('1155', '1151', '140903', '黟县');
INSERT INTO `sys_regions` VALUES ('1156', '1151', '140904', '祁门县');
INSERT INTO `sys_regions` VALUES ('1158', '15', '1501', '宁波市');
INSERT INTO `sys_regions` VALUES ('1159', '14', '1410', '滁州市');
INSERT INTO `sys_regions` VALUES ('1161', '1159', '141001', '明光市');
INSERT INTO `sys_regions` VALUES ('1162', '1159', '141002', '全椒县');
INSERT INTO `sys_regions` VALUES ('1163', '1159', '141003', '来安县');
INSERT INTO `sys_regions` VALUES ('1164', '1159', '141004', '定远县');
INSERT INTO `sys_regions` VALUES ('1165', '1159', '141005', '凤阳县');
INSERT INTO `sys_regions` VALUES ('1167', '14', '1411', '阜阳市');
INSERT INTO `sys_regions` VALUES ('1168', '1167', '141101', '界首市');
INSERT INTO `sys_regions` VALUES ('1169', '1167', '141102', '太和县');
INSERT INTO `sys_regions` VALUES ('1170', '1167', '141103', '阜南县');
INSERT INTO `sys_regions` VALUES ('1171', '1167', '141104', '颍上县');
INSERT INTO `sys_regions` VALUES ('1172', '1167', '141105', '临泉县');
INSERT INTO `sys_regions` VALUES ('1174', '14', '1412', '亳州市');
INSERT INTO `sys_regions` VALUES ('1176', '1174', '141201', '利辛县');
INSERT INTO `sys_regions` VALUES ('1177', '1174', '141202', '蒙城县');
INSERT INTO `sys_regions` VALUES ('1178', '1174', '141203', '涡阳县');
INSERT INTO `sys_regions` VALUES ('1180', '14', '1413', '宿州市');
INSERT INTO `sys_regions` VALUES ('1181', '1180', '141301', '灵璧县');
INSERT INTO `sys_regions` VALUES ('1182', '1180', '141302', '泗县');
INSERT INTO `sys_regions` VALUES ('1183', '1180', '141303', '萧县');
INSERT INTO `sys_regions` VALUES ('1184', '1180', '141304', '砀山县');
INSERT INTO `sys_regions` VALUES ('1187', '1137', '140702', '含山县');
INSERT INTO `sys_regions` VALUES ('1188', '1137', '140703', '和县');
INSERT INTO `sys_regions` VALUES ('1189', '1127', '140504', '无为县');
INSERT INTO `sys_regions` VALUES ('1190', '1116', '140202', '庐江县');
INSERT INTO `sys_regions` VALUES ('1201', '14', '1414', '池州市');
INSERT INTO `sys_regions` VALUES ('1202', '1201', '141401', '东至县');
INSERT INTO `sys_regions` VALUES ('1203', '1201', '141402', '石台县');
INSERT INTO `sys_regions` VALUES ('1204', '1201', '141403', '青阳县');
INSERT INTO `sys_regions` VALUES ('1206', '14', '1415', '六安市');
INSERT INTO `sys_regions` VALUES ('1208', '1206', '141501', '霍山县');
INSERT INTO `sys_regions` VALUES ('1209', '1206', '141502', '金寨县');
INSERT INTO `sys_regions` VALUES ('1210', '1206', '141503', '霍邱县');
INSERT INTO `sys_regions` VALUES ('1211', '1206', '141504', '舒城县');
INSERT INTO `sys_regions` VALUES ('1213', '15', '1502', '杭州市');
INSERT INTO `sys_regions` VALUES ('1214', '1213', '150201', '余杭区');
INSERT INTO `sys_regions` VALUES ('1215', '1213', '150202', '萧山区');
INSERT INTO `sys_regions` VALUES ('1217', '1213', '150203', '富阳区');
INSERT INTO `sys_regions` VALUES ('1218', '1213', '150204', '桐庐县');
INSERT INTO `sys_regions` VALUES ('1219', '1213', '150205', '建德市');
INSERT INTO `sys_regions` VALUES ('1220', '1213', '150206', '淳安县');
INSERT INTO `sys_regions` VALUES ('1224', '1158', '150101', '慈溪市');
INSERT INTO `sys_regions` VALUES ('1226', '1158', '150102', '奉化市');
INSERT INTO `sys_regions` VALUES ('1227', '1158', '150103', '宁海县');
INSERT INTO `sys_regions` VALUES ('1228', '1158', '150104', '象山县');
INSERT INTO `sys_regions` VALUES ('1233', '15', '1503', '温州市');
INSERT INTO `sys_regions` VALUES ('1237', '1233', '150301', '文成县');
INSERT INTO `sys_regions` VALUES ('1238', '1233', '150302', '平阳县');
INSERT INTO `sys_regions` VALUES ('1239', '1233', '150303', '泰顺县');
INSERT INTO `sys_regions` VALUES ('1240', '1233', '150304', '洞头区');
INSERT INTO `sys_regions` VALUES ('1241', '1233', '150305', '苍南县');
INSERT INTO `sys_regions` VALUES ('1243', '15', '1504', '嘉兴市');
INSERT INTO `sys_regions` VALUES ('1244', '1243', '150401', '海宁市');
INSERT INTO `sys_regions` VALUES ('1248', '1243', '150402', '海盐县');
INSERT INTO `sys_regions` VALUES ('1250', '15', '1505', '湖州市');
INSERT INTO `sys_regions` VALUES ('1251', '1250', '150501', '长兴县');
INSERT INTO `sys_regions` VALUES ('1252', '1250', '150502', '德清县');
INSERT INTO `sys_regions` VALUES ('1253', '1250', '150503', '安吉县');
INSERT INTO `sys_regions` VALUES ('1255', '15', '1506', '绍兴市');
INSERT INTO `sys_regions` VALUES ('1257', '1255', '150601', '诸暨市');
INSERT INTO `sys_regions` VALUES ('1258', '1255', '150602', '上虞区');
INSERT INTO `sys_regions` VALUES ('1259', '1255', '150603', '嵊州市');
INSERT INTO `sys_regions` VALUES ('1260', '1255', '150604', '新昌县');
INSERT INTO `sys_regions` VALUES ('1262', '15', '1507', '金华市');
INSERT INTO `sys_regions` VALUES ('1263', '1262', '150701', '金东区');
INSERT INTO `sys_regions` VALUES ('1264', '1262', '150702', '兰溪市');
INSERT INTO `sys_regions` VALUES ('1265', '1262', '150703', '婺城区');
INSERT INTO `sys_regions` VALUES ('1266', '1262', '150704', '义乌市');
INSERT INTO `sys_regions` VALUES ('1267', '1262', '150705', '东阳市');
INSERT INTO `sys_regions` VALUES ('1268', '1262', '150706', '永康市');
INSERT INTO `sys_regions` VALUES ('1269', '1262', '150707', '武义县');
INSERT INTO `sys_regions` VALUES ('1270', '1262', '150708', '浦江县');
INSERT INTO `sys_regions` VALUES ('1271', '1262', '150709', '磐安县');
INSERT INTO `sys_regions` VALUES ('1273', '15', '1508', '衢州市');
INSERT INTO `sys_regions` VALUES ('1275', '1273', '150801', '江山市');
INSERT INTO `sys_regions` VALUES ('1276', '1273', '150802', '常山县');
INSERT INTO `sys_regions` VALUES ('1277', '1273', '150803', '开化县');
INSERT INTO `sys_regions` VALUES ('1278', '1273', '150804', '龙游县');
INSERT INTO `sys_regions` VALUES ('1280', '15', '1509', '丽水市');
INSERT INTO `sys_regions` VALUES ('1281', '1280', '150901', '龙泉市');
INSERT INTO `sys_regions` VALUES ('1282', '1280', '150902', '缙云县');
INSERT INTO `sys_regions` VALUES ('1283', '1280', '150903', '遂昌县');
INSERT INTO `sys_regions` VALUES ('1284', '1280', '150904', '松阳县');
INSERT INTO `sys_regions` VALUES ('1285', '1280', '150905', '景宁县');
INSERT INTO `sys_regions` VALUES ('1286', '1280', '150906', '云和县');
INSERT INTO `sys_regions` VALUES ('1288', '1280', '150907', '青田县');
INSERT INTO `sys_regions` VALUES ('1290', '15', '1510', '台州市');
INSERT INTO `sys_regions` VALUES ('1291', '1290', '151001', '临海市');
INSERT INTO `sys_regions` VALUES ('1294', '1290', '151002', '三门县');
INSERT INTO `sys_regions` VALUES ('1295', '1290', '151003', '天台县');
INSERT INTO `sys_regions` VALUES ('1296', '1290', '151004', '仙居县');
INSERT INTO `sys_regions` VALUES ('1298', '15', '1511', '舟山市');
INSERT INTO `sys_regions` VALUES ('1300', '1298', '151101', '岱山县');
INSERT INTO `sys_regions` VALUES ('1301', '1298', '151102', '嵊泗县');
INSERT INTO `sys_regions` VALUES ('1303', '16', '1601', '福州市');
INSERT INTO `sys_regions` VALUES ('1305', '1303', '160101', '长乐市');
INSERT INTO `sys_regions` VALUES ('1308', '1303', '160102', '平潭县');
INSERT INTO `sys_regions` VALUES ('1309', '1303', '160103', '连江县');
INSERT INTO `sys_regions` VALUES ('1310', '84', '3301', '钓鱼岛');
INSERT INTO `sys_regions` VALUES ('1312', '1303', '160104', '罗源县');
INSERT INTO `sys_regions` VALUES ('1313', '1303', '160105', '永泰县');
INSERT INTO `sys_regions` VALUES ('1314', '1303', '160106', '闽清县');
INSERT INTO `sys_regions` VALUES ('1315', '16', '1602', '厦门市');
INSERT INTO `sys_regions` VALUES ('1316', '1315', '160201', '思明区');
INSERT INTO `sys_regions` VALUES ('1317', '16', '1603', '三明市');
INSERT INTO `sys_regions` VALUES ('1319', '1317', '160301', '永安市');
INSERT INTO `sys_regions` VALUES ('1320', '1317', '160302', '明溪县');
INSERT INTO `sys_regions` VALUES ('1321', '1317', '160303', '将乐县');
INSERT INTO `sys_regions` VALUES ('1322', '1317', '160304', '大田县');
INSERT INTO `sys_regions` VALUES ('1323', '1317', '160305', '宁化县');
INSERT INTO `sys_regions` VALUES ('1324', '1317', '160306', '建宁县');
INSERT INTO `sys_regions` VALUES ('1325', '1317', '160307', '沙县');
INSERT INTO `sys_regions` VALUES ('1326', '1317', '160308', '尤溪县');
INSERT INTO `sys_regions` VALUES ('1327', '1317', '160309', '清流县');
INSERT INTO `sys_regions` VALUES ('1328', '1317', '160310', '泰宁县');
INSERT INTO `sys_regions` VALUES ('1329', '16', '1604', '莆田市');
INSERT INTO `sys_regions` VALUES ('1331', '1329', '160401', '仙游县');
INSERT INTO `sys_regions` VALUES ('1332', '16', '1605', '泉州市');
INSERT INTO `sys_regions` VALUES ('1334', '1332', '160501', '石狮市');
INSERT INTO `sys_regions` VALUES ('1336', '1332', '160502', '南安市');
INSERT INTO `sys_regions` VALUES ('1337', '1332', '160503', '惠安县');
INSERT INTO `sys_regions` VALUES ('1338', '1332', '160504', '安溪县');
INSERT INTO `sys_regions` VALUES ('1339', '1332', '160505', '德化县');
INSERT INTO `sys_regions` VALUES ('1340', '1332', '160506', '永春县');
INSERT INTO `sys_regions` VALUES ('1341', '16', '1606', '漳州市');
INSERT INTO `sys_regions` VALUES ('1343', '1341', '160601', '龙海市');
INSERT INTO `sys_regions` VALUES ('1344', '1341', '160602', '平和县');
INSERT INTO `sys_regions` VALUES ('1345', '1341', '160603', '南靖县');
INSERT INTO `sys_regions` VALUES ('1346', '1341', '160604', '诏安县');
INSERT INTO `sys_regions` VALUES ('1347', '1341', '160605', '漳浦县');
INSERT INTO `sys_regions` VALUES ('1348', '1341', '160606', '华安县');
INSERT INTO `sys_regions` VALUES ('1349', '1341', '160607', '云霄县');
INSERT INTO `sys_regions` VALUES ('1350', '1341', '160608', '东山县');
INSERT INTO `sys_regions` VALUES ('1351', '1341', '160609', '长泰县');
INSERT INTO `sys_regions` VALUES ('1352', '16', '1607', '南平市');
INSERT INTO `sys_regions` VALUES ('1354', '1352', '160701', '建瓯市');
INSERT INTO `sys_regions` VALUES ('1355', '1352', '160702', '邵武市');
INSERT INTO `sys_regions` VALUES ('1356', '1352', '160703', '武夷山市');
INSERT INTO `sys_regions` VALUES ('1357', '1352', '160704', '建阳市');
INSERT INTO `sys_regions` VALUES ('1358', '1352', '160705', '松溪县');
INSERT INTO `sys_regions` VALUES ('1359', '1352', '160706', '顺昌县');
INSERT INTO `sys_regions` VALUES ('1360', '1352', '160707', '浦城县');
INSERT INTO `sys_regions` VALUES ('1361', '1352', '160708', '政和县');
INSERT INTO `sys_regions` VALUES ('1362', '16', '1608', '龙岩市');
INSERT INTO `sys_regions` VALUES ('1364', '1362', '160801', '漳平市');
INSERT INTO `sys_regions` VALUES ('1365', '1362', '160802', '长汀县');
INSERT INTO `sys_regions` VALUES ('1366', '1362', '160803', '武平县');
INSERT INTO `sys_regions` VALUES ('1367', '1362', '160804', '永定县');
INSERT INTO `sys_regions` VALUES ('1368', '1362', '160805', '上杭县');
INSERT INTO `sys_regions` VALUES ('1369', '1362', '160806', '连城县');
INSERT INTO `sys_regions` VALUES ('1370', '16', '1609', '宁德市');
INSERT INTO `sys_regions` VALUES ('1372', '1370', '160901', '福安市');
INSERT INTO `sys_regions` VALUES ('1373', '1370', '160902', '福鼎市');
INSERT INTO `sys_regions` VALUES ('1374', '1370', '160903', '寿宁县');
INSERT INTO `sys_regions` VALUES ('1375', '1370', '160904', '霞浦县');
INSERT INTO `sys_regions` VALUES ('1376', '1370', '160905', '柘荣县');
INSERT INTO `sys_regions` VALUES ('1377', '1370', '160906', '屏南县');
INSERT INTO `sys_regions` VALUES ('1378', '1370', '160907', '古田县');
INSERT INTO `sys_regions` VALUES ('1379', '1370', '160908', '周宁县');
INSERT INTO `sys_regions` VALUES ('1381', '17', '1701', '武汉市');
INSERT INTO `sys_regions` VALUES ('1386', '1381', '170101', '江岸区');
INSERT INTO `sys_regions` VALUES ('1387', '17', '1702', '黄石市');
INSERT INTO `sys_regions` VALUES ('1389', '1387', '170201', '黄石港区');
INSERT INTO `sys_regions` VALUES ('1392', '1387', '170202', '铁山区');
INSERT INTO `sys_regions` VALUES ('1393', '1387', '170203', '大冶市');
INSERT INTO `sys_regions` VALUES ('1394', '1387', '170204', '阳新县');
INSERT INTO `sys_regions` VALUES ('1396', '17', '1703', '襄阳市');
INSERT INTO `sys_regions` VALUES ('1397', '1396', '170301', '老河口市');
INSERT INTO `sys_regions` VALUES ('1398', '1396', '170302', '枣阳市');
INSERT INTO `sys_regions` VALUES ('1399', '1396', '170303', '宜城市');
INSERT INTO `sys_regions` VALUES ('1401', '1396', '170304', '南漳县');
INSERT INTO `sys_regions` VALUES ('1402', '1396', '170305', '保康县');
INSERT INTO `sys_regions` VALUES ('1403', '1396', '170306', '谷城县');
INSERT INTO `sys_regions` VALUES ('1405', '17', '1704', '十堰市');
INSERT INTO `sys_regions` VALUES ('1406', '1405', '170401', '丹江口市');
INSERT INTO `sys_regions` VALUES ('1407', '1405', '170402', '房县');
INSERT INTO `sys_regions` VALUES ('1408', '1405', '170403', '竹山县');
INSERT INTO `sys_regions` VALUES ('1409', '1405', '170404', '竹溪县');
INSERT INTO `sys_regions` VALUES ('1410', '1405', '170405', '郧县');
INSERT INTO `sys_regions` VALUES ('1411', '1405', '170406', '郧西县');
INSERT INTO `sys_regions` VALUES ('1413', '17', '1705', '荆州市');
INSERT INTO `sys_regions` VALUES ('1414', '1413', '170501', '江陵县');
INSERT INTO `sys_regions` VALUES ('1415', '1413', '170502', '洪湖市');
INSERT INTO `sys_regions` VALUES ('1416', '1413', '170503', '石首市');
INSERT INTO `sys_regions` VALUES ('1417', '1413', '170504', '松滋市');
INSERT INTO `sys_regions` VALUES ('1418', '1413', '170505', '监利县');
INSERT INTO `sys_regions` VALUES ('1419', '1413', '170506', '公安县');
INSERT INTO `sys_regions` VALUES ('1421', '17', '1706', '宜昌市');
INSERT INTO `sys_regions` VALUES ('1423', '1421', '170601', '当阳市');
INSERT INTO `sys_regions` VALUES ('1424', '1421', '170602', '枝江市');
INSERT INTO `sys_regions` VALUES ('1425', '1421', '170603', '夷陵区');
INSERT INTO `sys_regions` VALUES ('1426', '1421', '170604', '秭归县');
INSERT INTO `sys_regions` VALUES ('1427', '1421', '170605', '兴山县');
INSERT INTO `sys_regions` VALUES ('1428', '1421', '170606', '远安县');
INSERT INTO `sys_regions` VALUES ('1429', '1421', '170607', '五峰土家族自治县');
INSERT INTO `sys_regions` VALUES ('1430', '1421', '170608', '长阳土家族自治县');
INSERT INTO `sys_regions` VALUES ('1432', '17', '1707', '孝感市');
INSERT INTO `sys_regions` VALUES ('1435', '1432', '170701', '汉川市');
INSERT INTO `sys_regions` VALUES ('1437', '1432', '170702', '云梦县');
INSERT INTO `sys_regions` VALUES ('1438', '1432', '170703', '大悟县');
INSERT INTO `sys_regions` VALUES ('1439', '1432', '170704', '孝昌县');
INSERT INTO `sys_regions` VALUES ('1441', '17', '1708', '黄冈市');
INSERT INTO `sys_regions` VALUES ('1444', '1441', '170801', '红安县');
INSERT INTO `sys_regions` VALUES ('1445', '1441', '170802', '罗田县');
INSERT INTO `sys_regions` VALUES ('1447', '1441', '170803', '黄梅县');
INSERT INTO `sys_regions` VALUES ('1448', '1441', '170804', '英山县');
INSERT INTO `sys_regions` VALUES ('1449', '1441', '170805', '团风县');
INSERT INTO `sys_regions` VALUES ('1458', '17', '1709', '咸宁市');
INSERT INTO `sys_regions` VALUES ('1461', '1458', '170901', '嘉鱼县');
INSERT INTO `sys_regions` VALUES ('1462', '1458', '170902', '通山县');
INSERT INTO `sys_regions` VALUES ('1463', '1458', '170903', '崇阳县');
INSERT INTO `sys_regions` VALUES ('1464', '1458', '170904', '通城县');
INSERT INTO `sys_regions` VALUES ('1466', '17', '1710', '恩施州');
INSERT INTO `sys_regions` VALUES ('1467', '1466', '171001', '恩施市');
INSERT INTO `sys_regions` VALUES ('1468', '1466', '171002', '利川市');
INSERT INTO `sys_regions` VALUES ('1469', '1466', '171003', '建始县');
INSERT INTO `sys_regions` VALUES ('1470', '1466', '171004', '来凤县');
INSERT INTO `sys_regions` VALUES ('1471', '1466', '171005', '巴东县');
INSERT INTO `sys_regions` VALUES ('1472', '1466', '171006', '鹤峰县');
INSERT INTO `sys_regions` VALUES ('1473', '1466', '171007', '宣恩县');
INSERT INTO `sys_regions` VALUES ('1474', '1466', '171008', '咸丰县');
INSERT INTO `sys_regions` VALUES ('1475', '17', '1711', '鄂州市');
INSERT INTO `sys_regions` VALUES ('1477', '17', '1712', '荆门市');
INSERT INTO `sys_regions` VALUES ('1478', '1477', '171201', '京山县');
INSERT INTO `sys_regions` VALUES ('1479', '17', '1713', '随州市');
INSERT INTO `sys_regions` VALUES ('1482', '18', '1801', '长沙市');
INSERT INTO `sys_regions` VALUES ('1485', '1482', '180101', '望城区');
INSERT INTO `sys_regions` VALUES ('1488', '18', '1802', '株洲市');
INSERT INTO `sys_regions` VALUES ('1489', '1488', '180201', '醴陵市');
INSERT INTO `sys_regions` VALUES ('1490', '1488', '180202', '株洲县');
INSERT INTO `sys_regions` VALUES ('1491', '1488', '180203', '攸县');
INSERT INTO `sys_regions` VALUES ('1492', '1488', '180204', '茶陵县');
INSERT INTO `sys_regions` VALUES ('1493', '1488', '180205', '炎陵县');
INSERT INTO `sys_regions` VALUES ('1495', '18', '1803', '湘潭市');
INSERT INTO `sys_regions` VALUES ('1496', '1495', '180301', '湘乡市');
INSERT INTO `sys_regions` VALUES ('1497', '1495', '180302', '湘潭县');
INSERT INTO `sys_regions` VALUES ('1498', '1495', '180303', '韶山市');
INSERT INTO `sys_regions` VALUES ('1501', '18', '1804', '衡阳市');
INSERT INTO `sys_regions` VALUES ('1502', '1501', '180401', '常宁市');
INSERT INTO `sys_regions` VALUES ('1503', '1501', '180402', '衡阳县');
INSERT INTO `sys_regions` VALUES ('1504', '1501', '180403', '耒阳市');
INSERT INTO `sys_regions` VALUES ('1505', '1501', '180404', '衡东县');
INSERT INTO `sys_regions` VALUES ('1506', '1501', '180405', '衡南县');
INSERT INTO `sys_regions` VALUES ('1507', '1501', '180406', '衡山县');
INSERT INTO `sys_regions` VALUES ('1508', '1501', '180407', '祁东县');
INSERT INTO `sys_regions` VALUES ('1509', '1501', '180408', '南岳区');
INSERT INTO `sys_regions` VALUES ('1511', '18', '1805', '邵阳市');
INSERT INTO `sys_regions` VALUES ('1512', '1511', '180501', '武冈市');
INSERT INTO `sys_regions` VALUES ('1513', '1511', '180502', '邵东县');
INSERT INTO `sys_regions` VALUES ('1514', '1511', '180503', '洞口县');
INSERT INTO `sys_regions` VALUES ('1515', '1511', '180504', '新邵县');
INSERT INTO `sys_regions` VALUES ('1516', '1511', '180505', '绥宁县');
INSERT INTO `sys_regions` VALUES ('1517', '1511', '180506', '新宁县');
INSERT INTO `sys_regions` VALUES ('1518', '1511', '180507', '邵阳县');
INSERT INTO `sys_regions` VALUES ('1519', '1511', '180508', '隆回县');
INSERT INTO `sys_regions` VALUES ('1520', '1511', '180509', '城步县');
INSERT INTO `sys_regions` VALUES ('1522', '18', '1806', '岳阳市');
INSERT INTO `sys_regions` VALUES ('1523', '1522', '180601', '临湘市');
INSERT INTO `sys_regions` VALUES ('1524', '1522', '180602', '汨罗市');
INSERT INTO `sys_regions` VALUES ('1525', '1522', '180603', '岳阳县');
INSERT INTO `sys_regions` VALUES ('1526', '1522', '180604', '湘阴县');
INSERT INTO `sys_regions` VALUES ('1527', '1522', '180605', '华容县');
INSERT INTO `sys_regions` VALUES ('1528', '1522', '180606', '平江县');
INSERT INTO `sys_regions` VALUES ('1530', '18', '1807', '常德市');
INSERT INTO `sys_regions` VALUES ('1532', '1530', '180701', '津市市');
INSERT INTO `sys_regions` VALUES ('1533', '1530', '180702', '澧县');
INSERT INTO `sys_regions` VALUES ('1534', '1530', '180703', '临澧县');
INSERT INTO `sys_regions` VALUES ('1535', '1530', '180704', '桃源县');
INSERT INTO `sys_regions` VALUES ('1536', '1530', '180705', '汉寿县');
INSERT INTO `sys_regions` VALUES ('1537', '1530', '180706', '石门县');
INSERT INTO `sys_regions` VALUES ('1538', '1530', '180707', '安乡县');
INSERT INTO `sys_regions` VALUES ('1540', '18', '1808', '张家界市');
INSERT INTO `sys_regions` VALUES ('1541', '1540', '180801', '慈利县');
INSERT INTO `sys_regions` VALUES ('1542', '1540', '180802', '桑植县');
INSERT INTO `sys_regions` VALUES ('1543', '1540', '180803', '武陵源区');
INSERT INTO `sys_regions` VALUES ('1544', '18', '1809', '郴州市');
INSERT INTO `sys_regions` VALUES ('1545', '1544', '180901', '资兴市');
INSERT INTO `sys_regions` VALUES ('1546', '1544', '180902', '宜章县');
INSERT INTO `sys_regions` VALUES ('1547', '1544', '180903', '安仁县');
INSERT INTO `sys_regions` VALUES ('1548', '1544', '180904', '汝城县');
INSERT INTO `sys_regions` VALUES ('1549', '1544', '180905', '嘉禾县');
INSERT INTO `sys_regions` VALUES ('1550', '1544', '180906', '临武县');
INSERT INTO `sys_regions` VALUES ('1551', '1544', '180907', '桂东县');
INSERT INTO `sys_regions` VALUES ('1552', '1544', '180908', '永兴县');
INSERT INTO `sys_regions` VALUES ('1553', '1544', '180909', '桂阳县');
INSERT INTO `sys_regions` VALUES ('1555', '18', '1810', '益阳市');
INSERT INTO `sys_regions` VALUES ('1556', '1555', '181001', '南县');
INSERT INTO `sys_regions` VALUES ('1557', '1555', '181002', '桃江县');
INSERT INTO `sys_regions` VALUES ('1558', '1555', '181003', '安化县');
INSERT INTO `sys_regions` VALUES ('1560', '18', '1811', '永州市');
INSERT INTO `sys_regions` VALUES ('1563', '1560', '181101', '祁阳县');
INSERT INTO `sys_regions` VALUES ('1564', '1560', '181102', '双牌县');
INSERT INTO `sys_regions` VALUES ('1565', '1555', '181004', '沅江市');
INSERT INTO `sys_regions` VALUES ('1566', '1560', '181103', '道县');
INSERT INTO `sys_regions` VALUES ('1567', '1560', '181104', '江永县');
INSERT INTO `sys_regions` VALUES ('1568', '1560', '181105', '江华县');
INSERT INTO `sys_regions` VALUES ('1569', '1560', '181106', '宁远县');
INSERT INTO `sys_regions` VALUES ('1570', '1560', '181107', '新田县');
INSERT INTO `sys_regions` VALUES ('1571', '1560', '181108', '蓝山县');
INSERT INTO `sys_regions` VALUES ('1572', '1560', '181109', '东安县');
INSERT INTO `sys_regions` VALUES ('1573', '1560', '181110', '零陵区');
INSERT INTO `sys_regions` VALUES ('1574', '18', '1812', '怀化市');
INSERT INTO `sys_regions` VALUES ('1575', '1574', '181201', '洪江市');
INSERT INTO `sys_regions` VALUES ('1576', '1574', '181202', '会同县');
INSERT INTO `sys_regions` VALUES ('1578', '1574', '181203', '溆浦县');
INSERT INTO `sys_regions` VALUES ('1579', '1574', '181204', '辰溪县');
INSERT INTO `sys_regions` VALUES ('1580', '1574', '181205', '靖州县');
INSERT INTO `sys_regions` VALUES ('1581', '1574', '181206', '通道县');
INSERT INTO `sys_regions` VALUES ('1582', '1574', '181207', '芷江县');
INSERT INTO `sys_regions` VALUES ('1583', '1574', '181208', '新晃县');
INSERT INTO `sys_regions` VALUES ('1584', '1574', '181209', '麻阳县');
INSERT INTO `sys_regions` VALUES ('1586', '18', '1813', '娄底市');
INSERT INTO `sys_regions` VALUES ('1588', '1586', '181301', '冷水江市');
INSERT INTO `sys_regions` VALUES ('1589', '1586', '181302', '涟源市');
INSERT INTO `sys_regions` VALUES ('1590', '1586', '181303', '新化县');
INSERT INTO `sys_regions` VALUES ('1591', '1586', '181304', '双峰县');
INSERT INTO `sys_regions` VALUES ('1592', '18', '1814', '湘西州');
INSERT INTO `sys_regions` VALUES ('1593', '1592', '181401', '吉首市');
INSERT INTO `sys_regions` VALUES ('1594', '1592', '181402', '古丈县');
INSERT INTO `sys_regions` VALUES ('1595', '1592', '181403', '龙山县');
INSERT INTO `sys_regions` VALUES ('1596', '1592', '181404', '永顺县');
INSERT INTO `sys_regions` VALUES ('1597', '1592', '181405', '泸溪县');
INSERT INTO `sys_regions` VALUES ('1598', '1592', '181406', '凤凰县');
INSERT INTO `sys_regions` VALUES ('1599', '1592', '181407', '花垣县');
INSERT INTO `sys_regions` VALUES ('1600', '1592', '181408', '保靖县');
INSERT INTO `sys_regions` VALUES ('1601', '19', '1901', '广州市');
INSERT INTO `sys_regions` VALUES ('1607', '19', '1902', '深圳市');
INSERT INTO `sys_regions` VALUES ('1609', '19', '1903', '珠海市');
INSERT INTO `sys_regions` VALUES ('1611', '19', '1904', '汕头市');
INSERT INTO `sys_regions` VALUES ('1614', '1611', '190401', '南澳县');
INSERT INTO `sys_regions` VALUES ('1617', '19', '1905', '韶关市');
INSERT INTO `sys_regions` VALUES ('1618', '1617', '190501', '南雄市');
INSERT INTO `sys_regions` VALUES ('1619', '1617', '190502', '乐昌市');
INSERT INTO `sys_regions` VALUES ('1620', '1617', '190503', '仁化县');
INSERT INTO `sys_regions` VALUES ('1621', '1617', '190504', '始兴县');
INSERT INTO `sys_regions` VALUES ('1622', '1617', '190505', '翁源县');
INSERT INTO `sys_regions` VALUES ('1624', '1617', '190506', '新丰县');
INSERT INTO `sys_regions` VALUES ('1625', '1617', '190507', '乳源瑶族自治县');
INSERT INTO `sys_regions` VALUES ('1626', '1617', '190508', '曲江区');
INSERT INTO `sys_regions` VALUES ('1627', '19', '1906', '河源市');
INSERT INTO `sys_regions` VALUES ('1628', '1627', '190601', '和平县');
INSERT INTO `sys_regions` VALUES ('1629', '1627', '190602', '龙川县');
INSERT INTO `sys_regions` VALUES ('1630', '1627', '190603', '紫金县');
INSERT INTO `sys_regions` VALUES ('1631', '1627', '190604', '连平县');
INSERT INTO `sys_regions` VALUES ('1634', '19', '1907', '梅州市');
INSERT INTO `sys_regions` VALUES ('1635', '1634', '190701', '兴宁市');
INSERT INTO `sys_regions` VALUES ('1636', '1634', '190702', '梅县');
INSERT INTO `sys_regions` VALUES ('1637', '1634', '190703', '蕉岭县');
INSERT INTO `sys_regions` VALUES ('1638', '1634', '190704', '大埔县');
INSERT INTO `sys_regions` VALUES ('1639', '1634', '190705', '丰顺县');
INSERT INTO `sys_regions` VALUES ('1640', '1634', '190706', '五华县');
INSERT INTO `sys_regions` VALUES ('1641', '1634', '190707', '平远县');
INSERT INTO `sys_regions` VALUES ('1642', '1634', '190708', '梅江区');
INSERT INTO `sys_regions` VALUES ('1643', '19', '1908', '惠州市');
INSERT INTO `sys_regions` VALUES ('1647', '1643', '190801', '龙门县');
INSERT INTO `sys_regions` VALUES ('1650', '19', '1909', '汕尾市');
INSERT INTO `sys_regions` VALUES ('1653', '1650', '190901', '陆河县');
INSERT INTO `sys_regions` VALUES ('1655', '19', '1910', '东莞市');
INSERT INTO `sys_regions` VALUES ('1657', '19', '1911', '中山市');
INSERT INTO `sys_regions` VALUES ('1659', '19', '1912', '江门市');
INSERT INTO `sys_regions` VALUES ('1666', '19', '1913', '佛山市');
INSERT INTO `sys_regions` VALUES ('1669', '1666', '191301', '顺德区');
INSERT INTO `sys_regions` VALUES ('1672', '19', '1914', '阳江市');
INSERT INTO `sys_regions` VALUES ('1673', '1672', '191401', '阳春市');
INSERT INTO `sys_regions` VALUES ('1674', '1672', '191402', '阳西县');
INSERT INTO `sys_regions` VALUES ('1677', '19', '1915', '湛江市');
INSERT INTO `sys_regions` VALUES ('1679', '1677', '191501', '雷州市');
INSERT INTO `sys_regions` VALUES ('1680', '1677', '191502', '吴川市');
INSERT INTO `sys_regions` VALUES ('1682', '1677', '191503', '徐闻县');
INSERT INTO `sys_regions` VALUES ('1684', '19', '1916', '茂名市');
INSERT INTO `sys_regions` VALUES ('1687', '1684', '191601', '信宜市');
INSERT INTO `sys_regions` VALUES ('1690', '19', '1917', '肇庆市');
INSERT INTO `sys_regions` VALUES ('1693', '1690', '191701', '广宁县');
INSERT INTO `sys_regions` VALUES ('1694', '1690', '191702', '德庆县');
INSERT INTO `sys_regions` VALUES ('1695', '1690', '191703', '怀集县');
INSERT INTO `sys_regions` VALUES ('1696', '1690', '191704', '封开县');
INSERT INTO `sys_regions` VALUES ('1697', '1690', '191705', '鼎湖区');
INSERT INTO `sys_regions` VALUES ('1698', '19', '1918', '云浮市');
INSERT INTO `sys_regions` VALUES ('1700', '1698', '191801', '云安县');
INSERT INTO `sys_regions` VALUES ('1701', '1698', '191802', '新兴县');
INSERT INTO `sys_regions` VALUES ('1702', '1698', '191803', '郁南县');
INSERT INTO `sys_regions` VALUES ('1704', '19', '1919', '清远市');
INSERT INTO `sys_regions` VALUES ('1705', '19', '1920', '潮州市');
INSERT INTO `sys_regions` VALUES ('1707', '1705', '192001', '饶平县');
INSERT INTO `sys_regions` VALUES ('1709', '19', '1921', '揭阳市');
INSERT INTO `sys_regions` VALUES ('1712', '1709', '192101', '揭西县');
INSERT INTO `sys_regions` VALUES ('1713', '1709', '192102', '惠来县');
INSERT INTO `sys_regions` VALUES ('1715', '20', '2001', '南宁市');
INSERT INTO `sys_regions` VALUES ('1716', '1715', '200101', '武鸣区');
INSERT INTO `sys_regions` VALUES ('1720', '20', '2002', '柳州市');
INSERT INTO `sys_regions` VALUES ('1721', '1720', '200201', '柳江县');
INSERT INTO `sys_regions` VALUES ('1722', '1720', '200202', '柳城县');
INSERT INTO `sys_regions` VALUES ('1724', '1715', '200102', '邕宁区');
INSERT INTO `sys_regions` VALUES ('1725', '1720', '200203', '鹿寨县');
INSERT INTO `sys_regions` VALUES ('1726', '20', '2003', '桂林市');
INSERT INTO `sys_regions` VALUES ('1727', '1726', '200301', '阳朔县');
INSERT INTO `sys_regions` VALUES ('1728', '1726', '200302', '临桂县');
INSERT INTO `sys_regions` VALUES ('1729', '1726', '200303', '灵川县');
INSERT INTO `sys_regions` VALUES ('1730', '1726', '200304', '全州县');
INSERT INTO `sys_regions` VALUES ('1731', '1726', '200305', '平乐县');
INSERT INTO `sys_regions` VALUES ('1732', '1726', '200306', '兴安县');
INSERT INTO `sys_regions` VALUES ('1733', '1726', '200307', '灌阳县');
INSERT INTO `sys_regions` VALUES ('1734', '1726', '200308', '荔浦县');
INSERT INTO `sys_regions` VALUES ('1735', '1726', '200309', '资源县');
INSERT INTO `sys_regions` VALUES ('1736', '1726', '200310', '永福县');
INSERT INTO `sys_regions` VALUES ('1738', '1726', '200311', '龙胜县');
INSERT INTO `sys_regions` VALUES ('1740', '20', '2004', '梧州市');
INSERT INTO `sys_regions` VALUES ('1741', '1740', '200401', '岑溪市');
INSERT INTO `sys_regions` VALUES ('1742', '1740', '200402', '苍梧县');
INSERT INTO `sys_regions` VALUES ('1743', '1740', '200403', '藤县');
INSERT INTO `sys_regions` VALUES ('1744', '1740', '200404', '蒙山县');
INSERT INTO `sys_regions` VALUES ('1746', '20', '2005', '北海市');
INSERT INTO `sys_regions` VALUES ('1747', '1746', '200501', '合浦县');
INSERT INTO `sys_regions` VALUES ('1748', '1746', '200502', '铁山港区');
INSERT INTO `sys_regions` VALUES ('1749', '20', '2006', '防城港市');
INSERT INTO `sys_regions` VALUES ('1750', '1749', '200601', '东兴市');
INSERT INTO `sys_regions` VALUES ('1751', '1749', '200602', '上思县');
INSERT INTO `sys_regions` VALUES ('1753', '20', '2007', '钦州市');
INSERT INTO `sys_regions` VALUES ('1754', '1753', '200701', '浦北县');
INSERT INTO `sys_regions` VALUES ('1755', '1753', '200702', '灵山县');
INSERT INTO `sys_regions` VALUES ('1757', '20', '2008', '贵港市');
INSERT INTO `sys_regions` VALUES ('1758', '1757', '200801', '桂平市');
INSERT INTO `sys_regions` VALUES ('1759', '1757', '200802', '平南县');
INSERT INTO `sys_regions` VALUES ('1760', '1757', '200803', '覃塘区');
INSERT INTO `sys_regions` VALUES ('1761', '20', '2009', '玉林市');
INSERT INTO `sys_regions` VALUES ('1762', '1761', '200901', '北流市');
INSERT INTO `sys_regions` VALUES ('1763', '1761', '200902', '容县');
INSERT INTO `sys_regions` VALUES ('1764', '1761', '200903', '博白县');
INSERT INTO `sys_regions` VALUES ('1765', '1761', '200904', '陆川县');
INSERT INTO `sys_regions` VALUES ('1766', '1761', '200905', '兴业县');
INSERT INTO `sys_regions` VALUES ('1792', '20', '2010', '贺州市');
INSERT INTO `sys_regions` VALUES ('1795', '1704', '191901', '连州市');
INSERT INTO `sys_regions` VALUES ('1796', '1704', '191902', '佛冈县');
INSERT INTO `sys_regions` VALUES ('1797', '1704', '191903', '阳山县');
INSERT INTO `sys_regions` VALUES ('1798', '1704', '191904', '清新县');
INSERT INTO `sys_regions` VALUES ('1799', '1704', '191905', '连山县');
INSERT INTO `sys_regions` VALUES ('1800', '1704', '191906', '连南县');
INSERT INTO `sys_regions` VALUES ('1803', '1792', '201001', '钟山县');
INSERT INTO `sys_regions` VALUES ('1804', '1792', '201002', '昭平县');
INSERT INTO `sys_regions` VALUES ('1805', '1792', '201003', '富川县');
INSERT INTO `sys_regions` VALUES ('1806', '20', '2011', '百色市');
INSERT INTO `sys_regions` VALUES ('1807', '1806', '201101', '右江区');
INSERT INTO `sys_regions` VALUES ('1808', '1806', '201102', '平果县');
INSERT INTO `sys_regions` VALUES ('1809', '1806', '201103', '乐业县');
INSERT INTO `sys_regions` VALUES ('1810', '1806', '201104', '田阳县');
INSERT INTO `sys_regions` VALUES ('1811', '1806', '201105', '西林县');
INSERT INTO `sys_regions` VALUES ('1812', '1806', '201106', '田林县');
INSERT INTO `sys_regions` VALUES ('1813', '1806', '201107', '德保县');
INSERT INTO `sys_regions` VALUES ('1814', '1806', '201108', '靖西县');
INSERT INTO `sys_regions` VALUES ('1815', '1806', '201109', '田东县');
INSERT INTO `sys_regions` VALUES ('1816', '1806', '201110', '那坡县');
INSERT INTO `sys_regions` VALUES ('1817', '1806', '201111', '隆林县');
INSERT INTO `sys_regions` VALUES ('1818', '20', '2012', '河池市');
INSERT INTO `sys_regions` VALUES ('1820', '1818', '201201', '宜州市');
INSERT INTO `sys_regions` VALUES ('1821', '1818', '201202', '天峨县');
INSERT INTO `sys_regions` VALUES ('1822', '1818', '201203', '凤山县');
INSERT INTO `sys_regions` VALUES ('1823', '1818', '201204', '南丹县');
INSERT INTO `sys_regions` VALUES ('1824', '1818', '201205', '东兰县');
INSERT INTO `sys_regions` VALUES ('1825', '1818', '201206', '巴马县');
INSERT INTO `sys_regions` VALUES ('1826', '1818', '201207', '环江县');
INSERT INTO `sys_regions` VALUES ('1827', '21', '2101', '南昌市');
INSERT INTO `sys_regions` VALUES ('1828', '1827', '210101', '南昌县');
INSERT INTO `sys_regions` VALUES ('1829', '1827', '210102', '进贤县');
INSERT INTO `sys_regions` VALUES ('1830', '1827', '210103', '安义县');
INSERT INTO `sys_regions` VALUES ('1832', '21', '2102', '景德镇市');
INSERT INTO `sys_regions` VALUES ('1833', '1832', '210201', '乐平市');
INSERT INTO `sys_regions` VALUES ('1834', '1832', '210202', '浮梁县');
INSERT INTO `sys_regions` VALUES ('1836', '21', '2103', '萍乡市');
INSERT INTO `sys_regions` VALUES ('1837', '1836', '210301', '湘东区');
INSERT INTO `sys_regions` VALUES ('1838', '1836', '210302', '莲花县');
INSERT INTO `sys_regions` VALUES ('1839', '1836', '210303', '上栗县');
INSERT INTO `sys_regions` VALUES ('1840', '1836', '210304', '芦溪县');
INSERT INTO `sys_regions` VALUES ('1842', '21', '2104', '新余市');
INSERT INTO `sys_regions` VALUES ('1843', '1842', '210401', '分宜县');
INSERT INTO `sys_regions` VALUES ('1845', '21', '2105', '九江市');
INSERT INTO `sys_regions` VALUES ('1846', '1845', '210501', '九江县');
INSERT INTO `sys_regions` VALUES ('1847', '1845', '210502', '瑞昌市');
INSERT INTO `sys_regions` VALUES ('1848', '1845', '210503', '星子县');
INSERT INTO `sys_regions` VALUES ('1849', '1845', '210504', '武宁县');
INSERT INTO `sys_regions` VALUES ('1850', '1845', '210505', '彭泽县');
INSERT INTO `sys_regions` VALUES ('1851', '1845', '210506', '永修县');
INSERT INTO `sys_regions` VALUES ('1852', '1845', '210507', '修水县');
INSERT INTO `sys_regions` VALUES ('1853', '1845', '210508', '湖口县');
INSERT INTO `sys_regions` VALUES ('1854', '1845', '210509', '德安县');
INSERT INTO `sys_regions` VALUES ('1855', '1845', '210510', '都昌县');
INSERT INTO `sys_regions` VALUES ('1857', '21', '2106', '鹰潭市');
INSERT INTO `sys_regions` VALUES ('1858', '1857', '210601', '余江县');
INSERT INTO `sys_regions` VALUES ('1859', '1857', '210602', '贵溪市');
INSERT INTO `sys_regions` VALUES ('1860', '1857', '210603', '月湖区');
INSERT INTO `sys_regions` VALUES ('1861', '21', '2107', '上饶市');
INSERT INTO `sys_regions` VALUES ('1863', '1861', '210701', '德兴市');
INSERT INTO `sys_regions` VALUES ('1864', '1861', '210702', '广丰县');
INSERT INTO `sys_regions` VALUES ('1865', '1861', '210703', '鄱阳县');
INSERT INTO `sys_regions` VALUES ('1866', '1861', '210704', '婺源县');
INSERT INTO `sys_regions` VALUES ('1867', '1861', '210705', '余干县');
INSERT INTO `sys_regions` VALUES ('1868', '1861', '210706', '横峰县');
INSERT INTO `sys_regions` VALUES ('1869', '1861', '210707', '弋阳县');
INSERT INTO `sys_regions` VALUES ('1870', '1861', '210708', '铅山县');
INSERT INTO `sys_regions` VALUES ('1871', '1861', '210709', '玉山县');
INSERT INTO `sys_regions` VALUES ('1872', '1861', '210710', '万年县');
INSERT INTO `sys_regions` VALUES ('1874', '21', '2108', '宜春市');
INSERT INTO `sys_regions` VALUES ('1875', '1874', '210801', '丰城市');
INSERT INTO `sys_regions` VALUES ('1876', '1874', '210802', '樟树市');
INSERT INTO `sys_regions` VALUES ('1877', '1874', '210803', '高安市');
INSERT INTO `sys_regions` VALUES ('1878', '1874', '210804', '铜鼓县');
INSERT INTO `sys_regions` VALUES ('1879', '1874', '210805', '靖安县');
INSERT INTO `sys_regions` VALUES ('1880', '1874', '210806', '宜丰县');
INSERT INTO `sys_regions` VALUES ('1881', '1874', '210807', '奉新县');
INSERT INTO `sys_regions` VALUES ('1882', '1874', '210808', '万载县');
INSERT INTO `sys_regions` VALUES ('1883', '1874', '210809', '上高县');
INSERT INTO `sys_regions` VALUES ('1885', '21', '2109', '抚州市');
INSERT INTO `sys_regions` VALUES ('1887', '1885', '210901', '南丰县');
INSERT INTO `sys_regions` VALUES ('1888', '1885', '210902', '乐安县');
INSERT INTO `sys_regions` VALUES ('1889', '1885', '210903', '金溪县');
INSERT INTO `sys_regions` VALUES ('1890', '1885', '210904', '南城县');
INSERT INTO `sys_regions` VALUES ('1891', '1885', '210905', '东乡县');
INSERT INTO `sys_regions` VALUES ('1892', '1885', '210906', '资溪县');
INSERT INTO `sys_regions` VALUES ('1893', '1885', '210907', '宜黄县');
INSERT INTO `sys_regions` VALUES ('1894', '1885', '210908', '崇仁县');
INSERT INTO `sys_regions` VALUES ('1895', '1885', '210909', '黎川县');
INSERT INTO `sys_regions` VALUES ('1896', '1885', '210910', '广昌县');
INSERT INTO `sys_regions` VALUES ('1898', '21', '2110', '吉安市');
INSERT INTO `sys_regions` VALUES ('1899', '1898', '211001', '井冈山市');
INSERT INTO `sys_regions` VALUES ('1900', '1898', '211002', '吉安县');
INSERT INTO `sys_regions` VALUES ('1901', '1898', '211003', '永丰县');
INSERT INTO `sys_regions` VALUES ('1902', '1898', '211004', '永新县');
INSERT INTO `sys_regions` VALUES ('1903', '1898', '211005', '新干县');
INSERT INTO `sys_regions` VALUES ('1904', '1898', '211006', '泰和县');
INSERT INTO `sys_regions` VALUES ('1905', '1898', '211007', '峡江县');
INSERT INTO `sys_regions` VALUES ('1906', '1898', '211008', '遂川县');
INSERT INTO `sys_regions` VALUES ('1907', '1898', '211009', '安福县');
INSERT INTO `sys_regions` VALUES ('1908', '1898', '211010', '吉水县');
INSERT INTO `sys_regions` VALUES ('1909', '1898', '211011', '万安县');
INSERT INTO `sys_regions` VALUES ('1911', '21', '2111', '赣州市');
INSERT INTO `sys_regions` VALUES ('1912', '1911', '211101', '南康市');
INSERT INTO `sys_regions` VALUES ('1913', '1911', '211102', '瑞金市');
INSERT INTO `sys_regions` VALUES ('1914', '1911', '211103', '石城县');
INSERT INTO `sys_regions` VALUES ('1915', '1911', '211104', '安远县');
INSERT INTO `sys_regions` VALUES ('1916', '1911', '211105', '赣县');
INSERT INTO `sys_regions` VALUES ('1917', '1911', '211106', '宁都县');
INSERT INTO `sys_regions` VALUES ('1918', '1911', '211107', '寻乌县');
INSERT INTO `sys_regions` VALUES ('1919', '1911', '211108', '兴国县');
INSERT INTO `sys_regions` VALUES ('1920', '1911', '211109', '定南县');
INSERT INTO `sys_regions` VALUES ('1921', '1911', '211110', '上犹县');
INSERT INTO `sys_regions` VALUES ('1922', '1911', '211111', '于都县');
INSERT INTO `sys_regions` VALUES ('1923', '1911', '211112', '龙南县');
INSERT INTO `sys_regions` VALUES ('1924', '1911', '211113', '崇义县');
INSERT INTO `sys_regions` VALUES ('1925', '1911', '211114', '大余县');
INSERT INTO `sys_regions` VALUES ('1926', '1911', '211115', '信丰县');
INSERT INTO `sys_regions` VALUES ('1927', '1911', '211116', '全南县');
INSERT INTO `sys_regions` VALUES ('1928', '1911', '211117', '会昌县');
INSERT INTO `sys_regions` VALUES ('1930', '22', '2201', '成都市');
INSERT INTO `sys_regions` VALUES ('1946', '22', '2202', '自贡市');
INSERT INTO `sys_regions` VALUES ('1947', '1946', '220201', '荣县');
INSERT INTO `sys_regions` VALUES ('1948', '1946', '220202', '富顺县');
INSERT INTO `sys_regions` VALUES ('1949', '1946', '220203', '自流井区');
INSERT INTO `sys_regions` VALUES ('1950', '22', '2203', '攀枝花市');
INSERT INTO `sys_regions` VALUES ('1951', '1950', '220301', '米易县');
INSERT INTO `sys_regions` VALUES ('1952', '1950', '220302', '盐边县');
INSERT INTO `sys_regions` VALUES ('1953', '1950', '220303', '仁和区');
INSERT INTO `sys_regions` VALUES ('1954', '22', '2204', '泸州市');
INSERT INTO `sys_regions` VALUES ('1955', '1954', '220401', '泸县');
INSERT INTO `sys_regions` VALUES ('1956', '1954', '220402', '合江县');
INSERT INTO `sys_regions` VALUES ('1957', '1954', '220403', '叙永县');
INSERT INTO `sys_regions` VALUES ('1958', '1954', '220404', '古蔺县');
INSERT INTO `sys_regions` VALUES ('1960', '22', '2205', '绵阳市');
INSERT INTO `sys_regions` VALUES ('1962', '22', '2206', '德阳市');
INSERT INTO `sys_regions` VALUES ('1965', '1962', '220601', '罗江县');
INSERT INTO `sys_regions` VALUES ('1966', '1962', '220602', '中江县');
INSERT INTO `sys_regions` VALUES ('1970', '1960', '220501', '盐亭县');
INSERT INTO `sys_regions` VALUES ('1971', '1960', '220502', '三台县');
INSERT INTO `sys_regions` VALUES ('1972', '1960', '220503', '平武县');
INSERT INTO `sys_regions` VALUES ('1973', '1960', '220504', '北川县');
INSERT INTO `sys_regions` VALUES ('1974', '1960', '220505', '安县');
INSERT INTO `sys_regions` VALUES ('1975', '1960', '220506', '梓潼县');
INSERT INTO `sys_regions` VALUES ('1977', '22', '2207', '广元市');
INSERT INTO `sys_regions` VALUES ('1978', '1977', '220701', '青川县');
INSERT INTO `sys_regions` VALUES ('1979', '1977', '220702', '旺苍县');
INSERT INTO `sys_regions` VALUES ('1980', '1977', '220703', '剑阁县');
INSERT INTO `sys_regions` VALUES ('1981', '1977', '220704', '苍溪县');
INSERT INTO `sys_regions` VALUES ('1983', '22', '2208', '遂宁市');
INSERT INTO `sys_regions` VALUES ('1984', '1983', '220801', '射洪县');
INSERT INTO `sys_regions` VALUES ('1985', '1983', '220802', '蓬溪县');
INSERT INTO `sys_regions` VALUES ('1986', '1983', '220803', '大英县');
INSERT INTO `sys_regions` VALUES ('1987', '1983', '220804', '安居区');
INSERT INTO `sys_regions` VALUES ('1988', '22', '2209', '内江市');
INSERT INTO `sys_regions` VALUES ('1989', '1988', '220901', '资中县');
INSERT INTO `sys_regions` VALUES ('1990', '1988', '220902', '隆昌县');
INSERT INTO `sys_regions` VALUES ('1991', '1988', '220903', '威远县');
INSERT INTO `sys_regions` VALUES ('1992', '1988', '220904', '市中区');
INSERT INTO `sys_regions` VALUES ('1993', '22', '2210', '乐山市');
INSERT INTO `sys_regions` VALUES ('1994', '1993', '221001', '五通桥区');
INSERT INTO `sys_regions` VALUES ('1995', '1993', '221002', '沙湾区');
INSERT INTO `sys_regions` VALUES ('1996', '1993', '221003', '金口河区');
INSERT INTO `sys_regions` VALUES ('1998', '1993', '221004', '夹江县');
INSERT INTO `sys_regions` VALUES ('1999', '1993', '221005', '井研县');
INSERT INTO `sys_regions` VALUES ('2000', '1993', '221006', '犍为县');
INSERT INTO `sys_regions` VALUES ('2001', '1993', '221007', '沐川县');
INSERT INTO `sys_regions` VALUES ('2002', '1993', '221008', '峨边县');
INSERT INTO `sys_regions` VALUES ('2003', '1993', '221009', '马边县');
INSERT INTO `sys_regions` VALUES ('2005', '22', '2211', '宜宾市');
INSERT INTO `sys_regions` VALUES ('2006', '2005', '221101', '宜宾县');
INSERT INTO `sys_regions` VALUES ('2007', '2005', '221102', '南溪区');
INSERT INTO `sys_regions` VALUES ('2008', '2005', '221103', '江安县');
INSERT INTO `sys_regions` VALUES ('2009', '2005', '221104', '长宁县');
INSERT INTO `sys_regions` VALUES ('2010', '2005', '221105', '兴文县');
INSERT INTO `sys_regions` VALUES ('2011', '2005', '221106', '珙县');
INSERT INTO `sys_regions` VALUES ('2012', '2005', '221107', '高县');
INSERT INTO `sys_regions` VALUES ('2013', '2005', '221108', '屏山县');
INSERT INTO `sys_regions` VALUES ('2015', '2005', '221109', '筠连县');
INSERT INTO `sys_regions` VALUES ('2016', '22', '2212', '广安市');
INSERT INTO `sys_regions` VALUES ('2017', '2016', '221201', '岳池县');
INSERT INTO `sys_regions` VALUES ('2018', '2016', '221202', '武胜县');
INSERT INTO `sys_regions` VALUES ('2019', '2016', '221203', '邻水县');
INSERT INTO `sys_regions` VALUES ('2020', '2016', '221204', '广安区');
INSERT INTO `sys_regions` VALUES ('2021', '2016', '221205', '华蓥市');
INSERT INTO `sys_regions` VALUES ('2022', '22', '2213', '南充市');
INSERT INTO `sys_regions` VALUES ('2028', '2022', '221301', '仪陇县');
INSERT INTO `sys_regions` VALUES ('2029', '2022', '221302', '蓬安县');
INSERT INTO `sys_regions` VALUES ('2030', '2022', '221303', '营山县');
INSERT INTO `sys_regions` VALUES ('2033', '22', '2214', '达州市');
INSERT INTO `sys_regions` VALUES ('2034', '2033', '221401', '通川区');
INSERT INTO `sys_regions` VALUES ('2035', '2033', '221402', '达县');
INSERT INTO `sys_regions` VALUES ('2036', '2033', '221403', '大竹县');
INSERT INTO `sys_regions` VALUES ('2037', '2033', '221404', '渠县');
INSERT INTO `sys_regions` VALUES ('2038', '2033', '221405', '万源市');
INSERT INTO `sys_regions` VALUES ('2039', '2033', '221406', '宣汉县');
INSERT INTO `sys_regions` VALUES ('2040', '2033', '221407', '开江县');
INSERT INTO `sys_regions` VALUES ('2042', '22', '2215', '巴中市');
INSERT INTO `sys_regions` VALUES ('2044', '2042', '221501', '南江县');
INSERT INTO `sys_regions` VALUES ('2045', '2042', '221502', '平昌县');
INSERT INTO `sys_regions` VALUES ('2046', '2042', '221503', '通江县');
INSERT INTO `sys_regions` VALUES ('2047', '22', '2216', '雅安市');
INSERT INTO `sys_regions` VALUES ('2049', '2047', '221601', '芦山县');
INSERT INTO `sys_regions` VALUES ('2052', '2047', '221602', '石棉县');
INSERT INTO `sys_regions` VALUES ('2053', '2047', '221603', '名山区');
INSERT INTO `sys_regions` VALUES ('2054', '2047', '221604', '天全县');
INSERT INTO `sys_regions` VALUES ('2055', '2047', '221605', '荥经县');
INSERT INTO `sys_regions` VALUES ('2056', '2047', '221606', '汉源县');
INSERT INTO `sys_regions` VALUES ('2057', '2047', '221607', '宝兴县');
INSERT INTO `sys_regions` VALUES ('2058', '22', '2217', '眉山市');
INSERT INTO `sys_regions` VALUES ('2060', '2058', '221701', '仁寿县');
INSERT INTO `sys_regions` VALUES ('2061', '2058', '221702', '彭山区');
INSERT INTO `sys_regions` VALUES ('2062', '2058', '221703', '洪雅县');
INSERT INTO `sys_regions` VALUES ('2063', '2058', '221704', '丹棱县');
INSERT INTO `sys_regions` VALUES ('2064', '2058', '221705', '青神县');
INSERT INTO `sys_regions` VALUES ('2065', '22', '2218', '资阳市');
INSERT INTO `sys_regions` VALUES ('2068', '2065', '221801', '安岳县');
INSERT INTO `sys_regions` VALUES ('2069', '2065', '221802', '乐至县');
INSERT INTO `sys_regions` VALUES ('2070', '22', '2219', '阿坝州');
INSERT INTO `sys_regions` VALUES ('2071', '2070', '221901', '马尔康县');
INSERT INTO `sys_regions` VALUES ('2072', '2070', '221902', '九寨沟县');
INSERT INTO `sys_regions` VALUES ('2073', '2070', '221903', '红原县');
INSERT INTO `sys_regions` VALUES ('2075', '2070', '221904', '阿坝县');
INSERT INTO `sys_regions` VALUES ('2076', '2070', '221905', '理县');
INSERT INTO `sys_regions` VALUES ('2077', '2070', '221906', '若尔盖县');
INSERT INTO `sys_regions` VALUES ('2078', '2070', '221907', '金川县');
INSERT INTO `sys_regions` VALUES ('2079', '2070', '221908', '小金县');
INSERT INTO `sys_regions` VALUES ('2080', '2070', '221909', '黑水县');
INSERT INTO `sys_regions` VALUES ('2081', '2070', '221910', '松潘县');
INSERT INTO `sys_regions` VALUES ('2082', '2070', '221911', '壤塘县');
INSERT INTO `sys_regions` VALUES ('2083', '2070', '221912', '茂县');
INSERT INTO `sys_regions` VALUES ('2084', '22', '2220', '甘孜州');
INSERT INTO `sys_regions` VALUES ('2085', '2084', '222001', '康定县');
INSERT INTO `sys_regions` VALUES ('2086', '2084', '222002', '泸定县');
INSERT INTO `sys_regions` VALUES ('2087', '2084', '222003', '九龙县');
INSERT INTO `sys_regions` VALUES ('2088', '2084', '222004', '丹巴县');
INSERT INTO `sys_regions` VALUES ('2089', '2084', '222005', '道孚县');
INSERT INTO `sys_regions` VALUES ('2090', '2084', '222006', '炉霍县');
INSERT INTO `sys_regions` VALUES ('2091', '2084', '222007', '色达县');
INSERT INTO `sys_regions` VALUES ('2092', '2084', '222008', '甘孜县');
INSERT INTO `sys_regions` VALUES ('2093', '2084', '222009', '新龙县');
INSERT INTO `sys_regions` VALUES ('2094', '2084', '222010', '白玉县');
INSERT INTO `sys_regions` VALUES ('2095', '2084', '222011', '德格县');
INSERT INTO `sys_regions` VALUES ('2096', '2084', '222012', '石渠县');
INSERT INTO `sys_regions` VALUES ('2097', '2084', '222013', '雅江县');
INSERT INTO `sys_regions` VALUES ('2098', '2084', '222014', '理塘县');
INSERT INTO `sys_regions` VALUES ('2099', '2084', '222015', '巴塘县');
INSERT INTO `sys_regions` VALUES ('2100', '2084', '222016', '稻城县');
INSERT INTO `sys_regions` VALUES ('2101', '2084', '222017', '乡城县');
INSERT INTO `sys_regions` VALUES ('2102', '2084', '222018', '得荣县');
INSERT INTO `sys_regions` VALUES ('2103', '22', '2221', '凉山州');
INSERT INTO `sys_regions` VALUES ('2105', '2103', '222101', '美姑县');
INSERT INTO `sys_regions` VALUES ('2106', '2103', '222102', '昭觉县');
INSERT INTO `sys_regions` VALUES ('2107', '2103', '222103', '会理县');
INSERT INTO `sys_regions` VALUES ('2108', '2103', '222104', '会东县');
INSERT INTO `sys_regions` VALUES ('2109', '2103', '222105', '普格县');
INSERT INTO `sys_regions` VALUES ('2110', '2103', '222106', '宁南县');
INSERT INTO `sys_regions` VALUES ('2111', '2103', '222107', '德昌县');
INSERT INTO `sys_regions` VALUES ('2112', '2103', '222108', '冕宁县');
INSERT INTO `sys_regions` VALUES ('2113', '2103', '222109', '盐源县');
INSERT INTO `sys_regions` VALUES ('2114', '2103', '222110', '金阳县');
INSERT INTO `sys_regions` VALUES ('2115', '2103', '222111', '布拖县');
INSERT INTO `sys_regions` VALUES ('2116', '2103', '222112', '雷波县');
INSERT INTO `sys_regions` VALUES ('2117', '2103', '222113', '越西县');
INSERT INTO `sys_regions` VALUES ('2118', '2103', '222114', '喜德县');
INSERT INTO `sys_regions` VALUES ('2119', '2103', '222115', '甘洛县');
INSERT INTO `sys_regions` VALUES ('2120', '2103', '222116', '木里县');
INSERT INTO `sys_regions` VALUES ('2121', '23', '2301', '海口市');
INSERT INTO `sys_regions` VALUES ('2144', '24', '2401', '贵阳市');
INSERT INTO `sys_regions` VALUES ('2145', '2144', '240101', '清镇市');
INSERT INTO `sys_regions` VALUES ('2146', '2144', '240102', '开阳县');
INSERT INTO `sys_regions` VALUES ('2147', '2144', '240103', '修文县');
INSERT INTO `sys_regions` VALUES ('2148', '2144', '240104', '息烽县');
INSERT INTO `sys_regions` VALUES ('2149', '2144', '240105', '乌当区');
INSERT INTO `sys_regions` VALUES ('2150', '24', '2402', '六盘水市');
INSERT INTO `sys_regions` VALUES ('2151', '2150', '240201', '盘县');
INSERT INTO `sys_regions` VALUES ('2152', '2150', '240202', '六枝特区');
INSERT INTO `sys_regions` VALUES ('2153', '2150', '240203', '水城县');
INSERT INTO `sys_regions` VALUES ('2154', '2150', '240204', '钟山区');
INSERT INTO `sys_regions` VALUES ('2155', '24', '2403', '遵义市');
INSERT INTO `sys_regions` VALUES ('2156', '2155', '240301', '赤水市');
INSERT INTO `sys_regions` VALUES ('2157', '2155', '240302', '仁怀市');
INSERT INTO `sys_regions` VALUES ('2158', '2155', '240303', '遵义县');
INSERT INTO `sys_regions` VALUES ('2159', '2155', '240304', '桐梓县');
INSERT INTO `sys_regions` VALUES ('2160', '2155', '240305', '绥阳县');
INSERT INTO `sys_regions` VALUES ('2161', '2155', '240306', '习水县');
INSERT INTO `sys_regions` VALUES ('2162', '2155', '240307', '凤冈县');
INSERT INTO `sys_regions` VALUES ('2163', '2155', '240308', '正安县');
INSERT INTO `sys_regions` VALUES ('2164', '2155', '240309', '湄潭县');
INSERT INTO `sys_regions` VALUES ('2165', '2155', '240310', '余庆县');
INSERT INTO `sys_regions` VALUES ('2166', '2155', '240311', '道真县');
INSERT INTO `sys_regions` VALUES ('2167', '2155', '240312', '务川县');
INSERT INTO `sys_regions` VALUES ('2169', '24', '2404', '铜仁市');
INSERT INTO `sys_regions` VALUES ('2170', '2169', '240401', '碧江区');
INSERT INTO `sys_regions` VALUES ('2171', '2169', '240402', '德江县');
INSERT INTO `sys_regions` VALUES ('2172', '2169', '240403', '江口县');
INSERT INTO `sys_regions` VALUES ('2173', '2169', '240404', '思南县');
INSERT INTO `sys_regions` VALUES ('2174', '2169', '240405', '万山区');
INSERT INTO `sys_regions` VALUES ('2175', '2169', '240406', '石阡县');
INSERT INTO `sys_regions` VALUES ('2176', '2169', '240407', '玉屏侗族自治县');
INSERT INTO `sys_regions` VALUES ('2177', '2169', '240408', '松桃苗族自治县');
INSERT INTO `sys_regions` VALUES ('2178', '2169', '240409', '印江土家族苗族自治县');
INSERT INTO `sys_regions` VALUES ('2179', '2169', '240410', '沿河土家族自治县');
INSERT INTO `sys_regions` VALUES ('2180', '24', '2405', '毕节市');
INSERT INTO `sys_regions` VALUES ('2182', '2180', '240501', '黔西县');
INSERT INTO `sys_regions` VALUES ('2183', '2180', '240502', '大方县');
INSERT INTO `sys_regions` VALUES ('2184', '2180', '240503', '织金县');
INSERT INTO `sys_regions` VALUES ('2185', '2180', '240504', '金沙县');
INSERT INTO `sys_regions` VALUES ('2186', '2180', '240505', '赫章县');
INSERT INTO `sys_regions` VALUES ('2187', '2180', '240506', '纳雍县');
INSERT INTO `sys_regions` VALUES ('2188', '2180', '240507', '威宁彝族回族苗族自治县');
INSERT INTO `sys_regions` VALUES ('2189', '24', '2406', '安顺市');
INSERT INTO `sys_regions` VALUES ('2190', '2189', '240601', '西秀区');
INSERT INTO `sys_regions` VALUES ('2191', '2189', '240602', '普定县');
INSERT INTO `sys_regions` VALUES ('2192', '2189', '240603', '平坝县');
INSERT INTO `sys_regions` VALUES ('2193', '2189', '240604', '镇宁布依族苗族自治县');
INSERT INTO `sys_regions` VALUES ('2194', '2189', '240605', '关岭布依族苗族自治县');
INSERT INTO `sys_regions` VALUES ('2195', '2189', '240606', '紫云苗族布依族自治县');
INSERT INTO `sys_regions` VALUES ('2196', '24', '2407', '黔西南州');
INSERT INTO `sys_regions` VALUES ('2197', '2196', '240701', '兴义市');
INSERT INTO `sys_regions` VALUES ('2198', '2196', '240702', '望谟县');
INSERT INTO `sys_regions` VALUES ('2199', '2196', '240703', '兴仁县');
INSERT INTO `sys_regions` VALUES ('2200', '2196', '240704', '普安县');
INSERT INTO `sys_regions` VALUES ('2201', '2196', '240705', '册亨县');
INSERT INTO `sys_regions` VALUES ('2202', '2196', '240706', '晴隆县');
INSERT INTO `sys_regions` VALUES ('2203', '2196', '240707', '贞丰县');
INSERT INTO `sys_regions` VALUES ('2204', '2196', '240708', '安龙县');
INSERT INTO `sys_regions` VALUES ('2205', '24', '2408', '黔东南州');
INSERT INTO `sys_regions` VALUES ('2206', '2205', '240801', '凯里市');
INSERT INTO `sys_regions` VALUES ('2207', '2205', '240802', '施秉市');
INSERT INTO `sys_regions` VALUES ('2208', '2205', '240803', '从江县');
INSERT INTO `sys_regions` VALUES ('2209', '2205', '240804', '锦屏县');
INSERT INTO `sys_regions` VALUES ('2210', '2205', '240805', '镇远县');
INSERT INTO `sys_regions` VALUES ('2211', '2205', '240806', '麻江县');
INSERT INTO `sys_regions` VALUES ('2212', '2205', '240807', '台江县');
INSERT INTO `sys_regions` VALUES ('2213', '2205', '240808', '天柱县');
INSERT INTO `sys_regions` VALUES ('2214', '2205', '240809', '黄平县');
INSERT INTO `sys_regions` VALUES ('2215', '2205', '240810', '榕江县');
INSERT INTO `sys_regions` VALUES ('2216', '2205', '240811', '剑河县');
INSERT INTO `sys_regions` VALUES ('2217', '2205', '240812', '三穗县');
INSERT INTO `sys_regions` VALUES ('2218', '2205', '240813', '雷山县');
INSERT INTO `sys_regions` VALUES ('2219', '2205', '240814', '黎平县');
INSERT INTO `sys_regions` VALUES ('2220', '2205', '240815', '岑巩县');
INSERT INTO `sys_regions` VALUES ('2221', '2205', '240816', '丹寨县');
INSERT INTO `sys_regions` VALUES ('2222', '24', '2409', '黔南州');
INSERT INTO `sys_regions` VALUES ('2223', '2222', '240901', '都匀市');
INSERT INTO `sys_regions` VALUES ('2224', '2222', '240902', '福泉市');
INSERT INTO `sys_regions` VALUES ('2225', '2222', '240903', '贵定县');
INSERT INTO `sys_regions` VALUES ('2226', '2222', '240904', '惠水县');
INSERT INTO `sys_regions` VALUES ('2227', '2222', '240905', '罗甸县');
INSERT INTO `sys_regions` VALUES ('2228', '2222', '240906', '瓮安县');
INSERT INTO `sys_regions` VALUES ('2229', '2222', '240907', '荔波县');
INSERT INTO `sys_regions` VALUES ('2230', '2222', '240908', '龙里县');
INSERT INTO `sys_regions` VALUES ('2231', '2222', '240909', '平塘县');
INSERT INTO `sys_regions` VALUES ('2232', '2222', '240910', '长顺县');
INSERT INTO `sys_regions` VALUES ('2233', '2222', '240911', '独山县');
INSERT INTO `sys_regions` VALUES ('2234', '2222', '240912', '三都县');
INSERT INTO `sys_regions` VALUES ('2235', '25', '2501', '昆明市');
INSERT INTO `sys_regions` VALUES ('2236', '2235', '250101', '东川区');
INSERT INTO `sys_regions` VALUES ('2237', '2235', '250102', '安宁市');
INSERT INTO `sys_regions` VALUES ('2238', '2235', '250103', '富民县');
INSERT INTO `sys_regions` VALUES ('2239', '2235', '250104', '嵩明县');
INSERT INTO `sys_regions` VALUES ('2241', '2235', '250105', '晋宁县');
INSERT INTO `sys_regions` VALUES ('2242', '2235', '250106', '宜良县');
INSERT INTO `sys_regions` VALUES ('2243', '2235', '250107', '禄劝县');
INSERT INTO `sys_regions` VALUES ('2244', '2235', '250108', '石林县');
INSERT INTO `sys_regions` VALUES ('2245', '2235', '250109', '寻甸县');
INSERT INTO `sys_regions` VALUES ('2246', '2235', '250110', '盘龙区');
INSERT INTO `sys_regions` VALUES ('2247', '25', '2502', '曲靖市');
INSERT INTO `sys_regions` VALUES ('2249', '2247', '250201', '马龙县');
INSERT INTO `sys_regions` VALUES ('2250', '2247', '250202', '宣威市');
INSERT INTO `sys_regions` VALUES ('2251', '2247', '250203', '富源县');
INSERT INTO `sys_regions` VALUES ('2252', '2247', '250204', '会泽县');
INSERT INTO `sys_regions` VALUES ('2253', '2247', '250205', '陆良县');
INSERT INTO `sys_regions` VALUES ('2254', '2247', '250206', '师宗县');
INSERT INTO `sys_regions` VALUES ('2255', '2247', '250207', '罗平县');
INSERT INTO `sys_regions` VALUES ('2256', '2247', '250208', '沾益县');
INSERT INTO `sys_regions` VALUES ('2258', '25', '2503', '玉溪市');
INSERT INTO `sys_regions` VALUES ('2259', '2258', '250301', '红塔区');
INSERT INTO `sys_regions` VALUES ('2260', '2258', '250302', '华宁县');
INSERT INTO `sys_regions` VALUES ('2261', '2258', '250303', '澄江县');
INSERT INTO `sys_regions` VALUES ('2262', '2258', '250304', '易门县');
INSERT INTO `sys_regions` VALUES ('2263', '2258', '250305', '通海县');
INSERT INTO `sys_regions` VALUES ('2264', '2258', '250306', '江川县');
INSERT INTO `sys_regions` VALUES ('2265', '2258', '250307', '元江县');
INSERT INTO `sys_regions` VALUES ('2266', '2258', '250308', '新平县');
INSERT INTO `sys_regions` VALUES ('2267', '2258', '250309', '峨山县');
INSERT INTO `sys_regions` VALUES ('2270', '25', '2504', '昭通市');
INSERT INTO `sys_regions` VALUES ('2271', '2270', '250401', '昭阳区');
INSERT INTO `sys_regions` VALUES ('2272', '2270', '250402', '镇雄县');
INSERT INTO `sys_regions` VALUES ('2273', '2270', '250403', '永善县');
INSERT INTO `sys_regions` VALUES ('2274', '2270', '250404', '大关县');
INSERT INTO `sys_regions` VALUES ('2275', '2270', '250405', '盐津县');
INSERT INTO `sys_regions` VALUES ('2276', '2270', '250406', '彝良县');
INSERT INTO `sys_regions` VALUES ('2277', '2270', '250407', '水富县');
INSERT INTO `sys_regions` VALUES ('2278', '2270', '250408', '巧家县');
INSERT INTO `sys_regions` VALUES ('2279', '2270', '250409', '威信县');
INSERT INTO `sys_regions` VALUES ('2281', '25', '2505', '普洱市');
INSERT INTO `sys_regions` VALUES ('2282', '2281', '250501', '思茅区');
INSERT INTO `sys_regions` VALUES ('2283', '2281', '250502', '宁洱县');
INSERT INTO `sys_regions` VALUES ('2284', '2281', '250503', '景东县');
INSERT INTO `sys_regions` VALUES ('2285', '2281', '250504', '镇沅县');
INSERT INTO `sys_regions` VALUES ('2286', '2281', '250505', '景谷县');
INSERT INTO `sys_regions` VALUES ('2287', '2281', '250506', '墨江县');
INSERT INTO `sys_regions` VALUES ('2288', '2281', '250507', '澜沧县');
INSERT INTO `sys_regions` VALUES ('2289', '2281', '250508', '西盟县');
INSERT INTO `sys_regions` VALUES ('2290', '2281', '250509', '江城县');
INSERT INTO `sys_regions` VALUES ('2291', '25', '2506', '临沧市');
INSERT INTO `sys_regions` VALUES ('2292', '2291', '250601', '临翔区');
INSERT INTO `sys_regions` VALUES ('2293', '2291', '250602', '镇康县');
INSERT INTO `sys_regions` VALUES ('2294', '2291', '250603', '凤庆县');
INSERT INTO `sys_regions` VALUES ('2295', '2291', '250604', '云县');
INSERT INTO `sys_regions` VALUES ('2296', '2291', '250605', '永德县');
INSERT INTO `sys_regions` VALUES ('2297', '2291', '250606', '耿马县');
INSERT INTO `sys_regions` VALUES ('2298', '25', '2507', '保山市');
INSERT INTO `sys_regions` VALUES ('2299', '2298', '250701', '隆阳区');
INSERT INTO `sys_regions` VALUES ('2300', '2298', '250702', '施甸县');
INSERT INTO `sys_regions` VALUES ('2301', '2298', '250703', '昌宁县');
INSERT INTO `sys_regions` VALUES ('2302', '2298', '250704', '龙陵县');
INSERT INTO `sys_regions` VALUES ('2303', '2298', '250705', '腾冲县');
INSERT INTO `sys_regions` VALUES ('2304', '25', '2508', '丽江市');
INSERT INTO `sys_regions` VALUES ('2305', '2304', '250801', '玉龙县');
INSERT INTO `sys_regions` VALUES ('2306', '2304', '250802', '华坪县');
INSERT INTO `sys_regions` VALUES ('2307', '2304', '250803', '永胜县');
INSERT INTO `sys_regions` VALUES ('2308', '2304', '250804', '宁蒗县');
INSERT INTO `sys_regions` VALUES ('2309', '25', '2509', '文山州');
INSERT INTO `sys_regions` VALUES ('2310', '2309', '250901', '文山市');
INSERT INTO `sys_regions` VALUES ('2311', '2309', '250902', '麻栗坡县');
INSERT INTO `sys_regions` VALUES ('2312', '2309', '250903', '砚山县');
INSERT INTO `sys_regions` VALUES ('2313', '2309', '250904', '广南县');
INSERT INTO `sys_regions` VALUES ('2314', '2309', '250905', '马关县');
INSERT INTO `sys_regions` VALUES ('2315', '2309', '250906', '富宁县');
INSERT INTO `sys_regions` VALUES ('2316', '2309', '250907', '西畴县');
INSERT INTO `sys_regions` VALUES ('2317', '2309', '250908', '丘北县');
INSERT INTO `sys_regions` VALUES ('2318', '25', '2510', '红河州');
INSERT INTO `sys_regions` VALUES ('2319', '2318', '251001', '个旧市');
INSERT INTO `sys_regions` VALUES ('2320', '2318', '251002', '开远市');
INSERT INTO `sys_regions` VALUES ('2321', '2318', '251003', '弥勒县');
INSERT INTO `sys_regions` VALUES ('2322', '2318', '251004', '红河县');
INSERT INTO `sys_regions` VALUES ('2323', '2318', '251005', '绿春县');
INSERT INTO `sys_regions` VALUES ('2324', '2318', '251006', '蒙自市');
INSERT INTO `sys_regions` VALUES ('2325', '2318', '251007', '泸西县');
INSERT INTO `sys_regions` VALUES ('2326', '2318', '251008', '建水县');
INSERT INTO `sys_regions` VALUES ('2327', '2318', '251009', '元阳县');
INSERT INTO `sys_regions` VALUES ('2328', '2318', '251010', '石屏县');
INSERT INTO `sys_regions` VALUES ('2329', '2318', '251011', '金平县');
INSERT INTO `sys_regions` VALUES ('2330', '2318', '251012', '屏边县');
INSERT INTO `sys_regions` VALUES ('2331', '2318', '251013', '河口县');
INSERT INTO `sys_regions` VALUES ('2332', '25', '2511', '西双版纳州');
INSERT INTO `sys_regions` VALUES ('2333', '2332', '251101', '景洪市');
INSERT INTO `sys_regions` VALUES ('2334', '2332', '251102', '勐海县');
INSERT INTO `sys_regions` VALUES ('2335', '2332', '251103', '勐腊县');
INSERT INTO `sys_regions` VALUES ('2336', '25', '2512', '楚雄州');
INSERT INTO `sys_regions` VALUES ('2338', '2336', '251201', '元谋县');
INSERT INTO `sys_regions` VALUES ('2339', '2336', '251202', '南华县');
INSERT INTO `sys_regions` VALUES ('2340', '2336', '251203', '牟定县');
INSERT INTO `sys_regions` VALUES ('2341', '2336', '251204', '武定县');
INSERT INTO `sys_regions` VALUES ('2342', '2336', '251205', '大姚县');
INSERT INTO `sys_regions` VALUES ('2343', '2336', '251206', '双柏县');
INSERT INTO `sys_regions` VALUES ('2344', '2336', '251207', '禄丰县');
INSERT INTO `sys_regions` VALUES ('2345', '2336', '251208', '永仁县');
INSERT INTO `sys_regions` VALUES ('2347', '25', '2513', '大理州');
INSERT INTO `sys_regions` VALUES ('2349', '2347', '251301', '剑川县');
INSERT INTO `sys_regions` VALUES ('2350', '2347', '251302', '弥渡县');
INSERT INTO `sys_regions` VALUES ('2351', '2347', '251303', '云龙县');
INSERT INTO `sys_regions` VALUES ('2352', '2347', '251304', '洱源县');
INSERT INTO `sys_regions` VALUES ('2353', '2347', '251305', '鹤庆县');
INSERT INTO `sys_regions` VALUES ('2354', '2347', '251306', '宾川县');
INSERT INTO `sys_regions` VALUES ('2355', '2347', '251307', '祥云县');
INSERT INTO `sys_regions` VALUES ('2356', '2347', '251308', '永平县');
INSERT INTO `sys_regions` VALUES ('2357', '2347', '251309', '巍山县');
INSERT INTO `sys_regions` VALUES ('2358', '2347', '251310', '漾濞县');
INSERT INTO `sys_regions` VALUES ('2359', '2347', '251311', '南涧县');
INSERT INTO `sys_regions` VALUES ('2360', '25', '2514', '德宏州');
INSERT INTO `sys_regions` VALUES ('2361', '2360', '251401', '芒市');
INSERT INTO `sys_regions` VALUES ('2362', '2360', '251402', '瑞丽市');
INSERT INTO `sys_regions` VALUES ('2363', '2360', '251403', '盈江县');
INSERT INTO `sys_regions` VALUES ('2364', '2360', '251404', '梁河县');
INSERT INTO `sys_regions` VALUES ('2365', '2360', '251405', '陇川县');
INSERT INTO `sys_regions` VALUES ('2366', '25', '2515', '怒江州');
INSERT INTO `sys_regions` VALUES ('2367', '2366', '251501', '泸水县');
INSERT INTO `sys_regions` VALUES ('2368', '2366', '251502', '福贡县');
INSERT INTO `sys_regions` VALUES ('2369', '2366', '251503', '兰坪县');
INSERT INTO `sys_regions` VALUES ('2370', '2366', '251504', '贡山县');
INSERT INTO `sys_regions` VALUES ('2376', '27', '2701', '西安市');
INSERT INTO `sys_regions` VALUES ('2380', '2376', '270101', '高陵县');
INSERT INTO `sys_regions` VALUES ('2381', '2376', '270102', '蓝田县');
INSERT INTO `sys_regions` VALUES ('2382', '2376', '270103', '户县');
INSERT INTO `sys_regions` VALUES ('2383', '2376', '270104', '周至县');
INSERT INTO `sys_regions` VALUES ('2386', '27', '2702', '铜川市');
INSERT INTO `sys_regions` VALUES ('2387', '2386', '270201', '印台区');
INSERT INTO `sys_regions` VALUES ('2388', '2386', '270202', '宜君县');
INSERT INTO `sys_regions` VALUES ('2389', '2386', '270203', '王益区');
INSERT INTO `sys_regions` VALUES ('2390', '27', '2703', '宝鸡市');
INSERT INTO `sys_regions` VALUES ('2392', '2390', '270301', '岐山县');
INSERT INTO `sys_regions` VALUES ('2393', '2390', '270302', '太白县');
INSERT INTO `sys_regions` VALUES ('2394', '2390', '270303', '凤翔县');
INSERT INTO `sys_regions` VALUES ('2395', '2390', '270304', '陇县');
INSERT INTO `sys_regions` VALUES ('2396', '2390', '270305', '麟游县');
INSERT INTO `sys_regions` VALUES ('2397', '2390', '270306', '千阳县');
INSERT INTO `sys_regions` VALUES ('2398', '2390', '270307', '扶风县');
INSERT INTO `sys_regions` VALUES ('2399', '2390', '270308', '凤县');
INSERT INTO `sys_regions` VALUES ('2400', '2390', '270309', '眉县');
INSERT INTO `sys_regions` VALUES ('2401', '2390', '270310', '渭滨区');
INSERT INTO `sys_regions` VALUES ('2402', '27', '2704', '咸阳市');
INSERT INTO `sys_regions` VALUES ('2403', '2402', '270401', '兴平市');
INSERT INTO `sys_regions` VALUES ('2404', '2402', '270402', '礼泉县');
INSERT INTO `sys_regions` VALUES ('2405', '2402', '270403', '泾阳县');
INSERT INTO `sys_regions` VALUES ('2406', '2402', '270404', '永寿县');
INSERT INTO `sys_regions` VALUES ('2407', '2402', '270405', '三原县');
INSERT INTO `sys_regions` VALUES ('2408', '2402', '270406', '彬县');
INSERT INTO `sys_regions` VALUES ('2409', '2402', '270407', '旬邑县');
INSERT INTO `sys_regions` VALUES ('2411', '2402', '270408', '长武县');
INSERT INTO `sys_regions` VALUES ('2412', '2402', '270409', '乾县');
INSERT INTO `sys_regions` VALUES ('2413', '2402', '270410', '武功县');
INSERT INTO `sys_regions` VALUES ('2414', '2402', '270411', '淳化县');
INSERT INTO `sys_regions` VALUES ('2416', '27', '2705', '渭南市');
INSERT INTO `sys_regions` VALUES ('2417', '2416', '270501', '韩城市');
INSERT INTO `sys_regions` VALUES ('2418', '2416', '270502', '华阴市');
INSERT INTO `sys_regions` VALUES ('2419', '2416', '270503', '蒲城县');
INSERT INTO `sys_regions` VALUES ('2420', '2416', '270504', '华县');
INSERT INTO `sys_regions` VALUES ('2421', '2416', '270505', '潼关县');
INSERT INTO `sys_regions` VALUES ('2422', '2416', '270506', '大荔县');
INSERT INTO `sys_regions` VALUES ('2423', '2416', '270507', '澄城县');
INSERT INTO `sys_regions` VALUES ('2424', '2416', '270508', '合阳县');
INSERT INTO `sys_regions` VALUES ('2425', '2416', '270509', '白水县');
INSERT INTO `sys_regions` VALUES ('2426', '2416', '270510', '富平县');
INSERT INTO `sys_regions` VALUES ('2428', '27', '2706', '延安市');
INSERT INTO `sys_regions` VALUES ('2429', '2428', '270601', '安塞县');
INSERT INTO `sys_regions` VALUES ('2430', '2428', '270602', '洛川县');
INSERT INTO `sys_regions` VALUES ('2431', '2428', '270603', '子长县');
INSERT INTO `sys_regions` VALUES ('2432', '2428', '270604', '黄陵县');
INSERT INTO `sys_regions` VALUES ('2433', '2428', '270605', '延长县');
INSERT INTO `sys_regions` VALUES ('2434', '2428', '270606', '宜川县');
INSERT INTO `sys_regions` VALUES ('2435', '2428', '270607', '延川县');
INSERT INTO `sys_regions` VALUES ('2436', '2428', '270608', '甘泉县');
INSERT INTO `sys_regions` VALUES ('2437', '2428', '270609', '富县');
INSERT INTO `sys_regions` VALUES ('2438', '2428', '270610', '志丹县');
INSERT INTO `sys_regions` VALUES ('2439', '2428', '270611', '黄龙县');
INSERT INTO `sys_regions` VALUES ('2440', '2428', '270612', '吴起县');
INSERT INTO `sys_regions` VALUES ('2442', '27', '2707', '汉中市');
INSERT INTO `sys_regions` VALUES ('2443', '2442', '270701', '南郑县');
INSERT INTO `sys_regions` VALUES ('2444', '2442', '270702', '城固县');
INSERT INTO `sys_regions` VALUES ('2445', '2442', '270703', '洋县');
INSERT INTO `sys_regions` VALUES ('2446', '2442', '270704', '佛坪县');
INSERT INTO `sys_regions` VALUES ('2447', '2442', '270705', '留坝县');
INSERT INTO `sys_regions` VALUES ('2448', '2442', '270706', '镇巴县');
INSERT INTO `sys_regions` VALUES ('2449', '2442', '270707', '西乡县');
INSERT INTO `sys_regions` VALUES ('2450', '2442', '270708', '勉县');
INSERT INTO `sys_regions` VALUES ('2451', '2442', '270709', '略阳县');
INSERT INTO `sys_regions` VALUES ('2452', '2442', '270710', '宁强县');
INSERT INTO `sys_regions` VALUES ('2454', '27', '2708', '榆林市');
INSERT INTO `sys_regions` VALUES ('2456', '2454', '270801', '清涧县');
INSERT INTO `sys_regions` VALUES ('2457', '2454', '270802', '绥德县');
INSERT INTO `sys_regions` VALUES ('2459', '2454', '270803', '佳县');
INSERT INTO `sys_regions` VALUES ('2460', '2454', '270804', '神木县');
INSERT INTO `sys_regions` VALUES ('2461', '2454', '270805', '府谷县');
INSERT INTO `sys_regions` VALUES ('2462', '2454', '270806', '子洲县');
INSERT INTO `sys_regions` VALUES ('2464', '2454', '270807', '横山县');
INSERT INTO `sys_regions` VALUES ('2465', '2454', '270808', '米脂县');
INSERT INTO `sys_regions` VALUES ('2466', '2454', '270809', '吴堡县');
INSERT INTO `sys_regions` VALUES ('2467', '2454', '270810', '定边县');
INSERT INTO `sys_regions` VALUES ('2468', '27', '2709', '商洛市');
INSERT INTO `sys_regions` VALUES ('2469', '2468', '270901', '商州区');
INSERT INTO `sys_regions` VALUES ('2470', '2468', '270902', '镇安县');
INSERT INTO `sys_regions` VALUES ('2471', '2468', '270903', '山阳县');
INSERT INTO `sys_regions` VALUES ('2472', '2468', '270904', '洛南县');
INSERT INTO `sys_regions` VALUES ('2473', '2468', '270905', '商南县');
INSERT INTO `sys_regions` VALUES ('2474', '2468', '270906', '丹凤县');
INSERT INTO `sys_regions` VALUES ('2475', '2468', '270907', '柞水县');
INSERT INTO `sys_regions` VALUES ('2476', '27', '2710', '安康市');
INSERT INTO `sys_regions` VALUES ('2478', '2476', '271001', '紫阳县');
INSERT INTO `sys_regions` VALUES ('2479', '2476', '271002', '岚皋县');
INSERT INTO `sys_regions` VALUES ('2480', '2476', '271003', '旬阳县');
INSERT INTO `sys_regions` VALUES ('2481', '2476', '271004', '镇坪县');
INSERT INTO `sys_regions` VALUES ('2482', '2476', '271005', '平利县');
INSERT INTO `sys_regions` VALUES ('2483', '2476', '271006', '宁陕县');
INSERT INTO `sys_regions` VALUES ('2484', '2476', '271007', '汉阴县');
INSERT INTO `sys_regions` VALUES ('2485', '2476', '271008', '石泉县');
INSERT INTO `sys_regions` VALUES ('2486', '2476', '271009', '白河县');
INSERT INTO `sys_regions` VALUES ('2487', '28', '2801', '兰州市');
INSERT INTO `sys_regions` VALUES ('2488', '2487', '280101', '永登县');
INSERT INTO `sys_regions` VALUES ('2489', '2487', '280102', '榆中县');
INSERT INTO `sys_regions` VALUES ('2490', '2487', '280103', '皋兰县');
INSERT INTO `sys_regions` VALUES ('2492', '28', '2802', '金昌市');
INSERT INTO `sys_regions` VALUES ('2493', '2492', '280201', '永昌县');
INSERT INTO `sys_regions` VALUES ('2494', '2492', '280202', '金川区');
INSERT INTO `sys_regions` VALUES ('2495', '28', '2803', '白银市');
INSERT INTO `sys_regions` VALUES ('2496', '2495', '280301', '白银区');
INSERT INTO `sys_regions` VALUES ('2497', '2495', '280302', '平川区');
INSERT INTO `sys_regions` VALUES ('2498', '2495', '280303', '靖远县');
INSERT INTO `sys_regions` VALUES ('2499', '2495', '280304', '景泰县');
INSERT INTO `sys_regions` VALUES ('2500', '2495', '280305', '会宁县');
INSERT INTO `sys_regions` VALUES ('2501', '28', '2804', '天水市');
INSERT INTO `sys_regions` VALUES ('2504', '2501', '280401', '甘谷县');
INSERT INTO `sys_regions` VALUES ('2505', '2501', '280402', '武山县');
INSERT INTO `sys_regions` VALUES ('2506', '2501', '280403', '清水县');
INSERT INTO `sys_regions` VALUES ('2507', '2501', '280404', '秦安县');
INSERT INTO `sys_regions` VALUES ('2508', '2501', '280405', '张家川县');
INSERT INTO `sys_regions` VALUES ('2509', '28', '2805', '嘉峪关市');
INSERT INTO `sys_regions` VALUES ('2518', '28', '2806', '平凉市');
INSERT INTO `sys_regions` VALUES ('2519', '2518', '280601', '华亭县');
INSERT INTO `sys_regions` VALUES ('2520', '2518', '280602', '崇信县');
INSERT INTO `sys_regions` VALUES ('2521', '2518', '280603', '泾川县');
INSERT INTO `sys_regions` VALUES ('2522', '2518', '280604', '灵台县');
INSERT INTO `sys_regions` VALUES ('2524', '2518', '280605', '庄浪县');
INSERT INTO `sys_regions` VALUES ('2525', '28', '2807', '庆阳市');
INSERT INTO `sys_regions` VALUES ('2526', '2525', '280701', '西峰区');
INSERT INTO `sys_regions` VALUES ('2528', '2525', '280702', '镇原县');
INSERT INTO `sys_regions` VALUES ('2529', '2525', '280703', '合水县');
INSERT INTO `sys_regions` VALUES ('2530', '2525', '280704', '华池县');
INSERT INTO `sys_regions` VALUES ('2531', '2525', '280705', '环县');
INSERT INTO `sys_regions` VALUES ('2532', '2525', '280706', '宁县');
INSERT INTO `sys_regions` VALUES ('2533', '2525', '280707', '正宁县');
INSERT INTO `sys_regions` VALUES ('2534', '28', '2808', '陇南市');
INSERT INTO `sys_regions` VALUES ('2535', '2534', '280801', '成县');
INSERT INTO `sys_regions` VALUES ('2536', '2534', '280802', '礼县');
INSERT INTO `sys_regions` VALUES ('2537', '2534', '280803', '康县');
INSERT INTO `sys_regions` VALUES ('2538', '2534', '280804', '武都区');
INSERT INTO `sys_regions` VALUES ('2539', '2534', '280805', '文县');
INSERT INTO `sys_regions` VALUES ('2540', '2534', '280806', '两当县');
INSERT INTO `sys_regions` VALUES ('2541', '2534', '280807', '徽县');
INSERT INTO `sys_regions` VALUES ('2542', '2534', '280808', '宕昌县');
INSERT INTO `sys_regions` VALUES ('2543', '2534', '280809', '西和县');
INSERT INTO `sys_regions` VALUES ('2544', '28', '2809', '武威市');
INSERT INTO `sys_regions` VALUES ('2545', '2544', '280901', '凉州区');
INSERT INTO `sys_regions` VALUES ('2546', '2544', '280902', '古浪县');
INSERT INTO `sys_regions` VALUES ('2547', '2544', '280903', '天祝县');
INSERT INTO `sys_regions` VALUES ('2548', '2544', '280904', '民勤县');
INSERT INTO `sys_regions` VALUES ('2549', '28', '2810', '张掖市');
INSERT INTO `sys_regions` VALUES ('2550', '2549', '281001', '甘州区');
INSERT INTO `sys_regions` VALUES ('2551', '2549', '281002', '山丹县');
INSERT INTO `sys_regions` VALUES ('2552', '2549', '281003', '临泽县');
INSERT INTO `sys_regions` VALUES ('2553', '2549', '281004', '高台县');
INSERT INTO `sys_regions` VALUES ('2554', '2549', '281005', '肃南县');
INSERT INTO `sys_regions` VALUES ('2555', '2549', '281006', '民乐县');
INSERT INTO `sys_regions` VALUES ('2556', '28', '2811', '酒泉市');
INSERT INTO `sys_regions` VALUES ('2558', '2556', '281101', '玉门市');
INSERT INTO `sys_regions` VALUES ('2559', '2556', '281102', '敦煌市');
INSERT INTO `sys_regions` VALUES ('2560', '2556', '281103', '金塔县');
INSERT INTO `sys_regions` VALUES ('2562', '2556', '281104', '阿克塞县');
INSERT INTO `sys_regions` VALUES ('2563', '2556', '281105', '肃北县');
INSERT INTO `sys_regions` VALUES ('2564', '28', '2812', '甘南州');
INSERT INTO `sys_regions` VALUES ('2565', '2564', '281201', '合作市');
INSERT INTO `sys_regions` VALUES ('2566', '2564', '281202', '夏河县');
INSERT INTO `sys_regions` VALUES ('2567', '2564', '281203', '碌曲县');
INSERT INTO `sys_regions` VALUES ('2568', '2564', '281204', '舟曲县');
INSERT INTO `sys_regions` VALUES ('2569', '2564', '281205', '玛曲县');
INSERT INTO `sys_regions` VALUES ('2570', '2564', '281206', '迭部县');
INSERT INTO `sys_regions` VALUES ('2571', '2564', '281207', '临潭县');
INSERT INTO `sys_regions` VALUES ('2572', '2564', '281208', '卓尼县');
INSERT INTO `sys_regions` VALUES ('2573', '28', '2813', '临夏州');
INSERT INTO `sys_regions` VALUES ('2574', '2573', '281301', '临夏县');
INSERT INTO `sys_regions` VALUES ('2575', '2573', '281302', '康乐县');
INSERT INTO `sys_regions` VALUES ('2576', '2573', '281303', '永靖县');
INSERT INTO `sys_regions` VALUES ('2577', '2573', '281304', '和政县');
INSERT INTO `sys_regions` VALUES ('2578', '2573', '281305', '东乡族自治县');
INSERT INTO `sys_regions` VALUES ('2579', '2573', '281306', '积石山县');
INSERT INTO `sys_regions` VALUES ('2580', '29', '2901', '西宁市');
INSERT INTO `sys_regions` VALUES ('2581', '2580', '290101', '湟中县');
INSERT INTO `sys_regions` VALUES ('2582', '2580', '290102', '湟源县');
INSERT INTO `sys_regions` VALUES ('2583', '2580', '290103', '大通县');
INSERT INTO `sys_regions` VALUES ('2585', '29', '2902', '海东地区');
INSERT INTO `sys_regions` VALUES ('2586', '2585', '290201', '平安县');
INSERT INTO `sys_regions` VALUES ('2587', '2585', '290202', '乐都县');
INSERT INTO `sys_regions` VALUES ('2588', '2585', '290203', '民和县');
INSERT INTO `sys_regions` VALUES ('2589', '2585', '290204', '互助县');
INSERT INTO `sys_regions` VALUES ('2590', '2585', '290205', '化隆县');
INSERT INTO `sys_regions` VALUES ('2591', '2585', '290206', '循化县');
INSERT INTO `sys_regions` VALUES ('2592', '29', '2903', '海北州');
INSERT INTO `sys_regions` VALUES ('2593', '2592', '290301', '海晏县');
INSERT INTO `sys_regions` VALUES ('2594', '2592', '290302', '祁连县');
INSERT INTO `sys_regions` VALUES ('2595', '2592', '290303', '刚察县');
INSERT INTO `sys_regions` VALUES ('2596', '2592', '290304', '门源县');
INSERT INTO `sys_regions` VALUES ('2597', '29', '2904', '黄南州');
INSERT INTO `sys_regions` VALUES ('2598', '2597', '290401', '尖扎县');
INSERT INTO `sys_regions` VALUES ('2599', '2597', '290402', '同仁县');
INSERT INTO `sys_regions` VALUES ('2600', '2597', '290403', '泽库县');
INSERT INTO `sys_regions` VALUES ('2602', '2597', '290404', '河南县');
INSERT INTO `sys_regions` VALUES ('2603', '29', '2905', '海南州');
INSERT INTO `sys_regions` VALUES ('2605', '29', '2906', '果洛州');
INSERT INTO `sys_regions` VALUES ('2606', '2605', '290601', '玛沁县');
INSERT INTO `sys_regions` VALUES ('2607', '2605', '290602', '甘德县');
INSERT INTO `sys_regions` VALUES ('2608', '2605', '290603', '达日县');
INSERT INTO `sys_regions` VALUES ('2609', '2605', '290604', '班玛县');
INSERT INTO `sys_regions` VALUES ('2610', '2605', '290605', '久治县');
INSERT INTO `sys_regions` VALUES ('2611', '2605', '290606', '玛多县');
INSERT INTO `sys_regions` VALUES ('2612', '29', '2907', '玉树州');
INSERT INTO `sys_regions` VALUES ('2613', '2612', '290701', '玉树县');
INSERT INTO `sys_regions` VALUES ('2614', '2612', '290702', '称多县');
INSERT INTO `sys_regions` VALUES ('2615', '2612', '290703', '囊谦县');
INSERT INTO `sys_regions` VALUES ('2616', '2612', '290704', '杂多县');
INSERT INTO `sys_regions` VALUES ('2617', '2612', '290705', '治多县');
INSERT INTO `sys_regions` VALUES ('2618', '2612', '290706', '曲麻莱县');
INSERT INTO `sys_regions` VALUES ('2620', '29', '2908', '海西州');
INSERT INTO `sys_regions` VALUES ('2621', '2620', '290801', '德令哈市');
INSERT INTO `sys_regions` VALUES ('2622', '2620', '290802', '乌兰县');
INSERT INTO `sys_regions` VALUES ('2623', '2620', '290803', '天峻县');
INSERT INTO `sys_regions` VALUES ('2624', '2620', '290804', '都兰县');
INSERT INTO `sys_regions` VALUES ('2625', '2620', '290805', '大柴旦行委');
INSERT INTO `sys_regions` VALUES ('2626', '2620', '290806', '冷湖行委');
INSERT INTO `sys_regions` VALUES ('2627', '2620', '290807', '茫崖行委');
INSERT INTO `sys_regions` VALUES ('2628', '30', '3001', '银川市');
INSERT INTO `sys_regions` VALUES ('2629', '2628', '300101', '灵武市');
INSERT INTO `sys_regions` VALUES ('2630', '2628', '300102', '永宁县');
INSERT INTO `sys_regions` VALUES ('2631', '2628', '300103', '贺兰县');
INSERT INTO `sys_regions` VALUES ('2632', '30', '3002', '石嘴山市');
INSERT INTO `sys_regions` VALUES ('2633', '2632', '300201', '平罗县');
INSERT INTO `sys_regions` VALUES ('2635', '2632', '300202', '惠农区');
INSERT INTO `sys_regions` VALUES ('2636', '2632', '300203', '大武口区');
INSERT INTO `sys_regions` VALUES ('2637', '30', '3003', '吴忠市');
INSERT INTO `sys_regions` VALUES ('2638', '2637', '300301', '青铜峡市');
INSERT INTO `sys_regions` VALUES ('2641', '2637', '300302', '同心县');
INSERT INTO `sys_regions` VALUES ('2642', '2637', '300303', '盐池县');
INSERT INTO `sys_regions` VALUES ('2643', '2637', '300304', '红寺堡开发区');
INSERT INTO `sys_regions` VALUES ('2644', '30', '3004', '固原市');
INSERT INTO `sys_regions` VALUES ('2647', '2644', '300401', '西吉县');
INSERT INTO `sys_regions` VALUES ('2648', '2644', '300402', '隆德县');
INSERT INTO `sys_regions` VALUES ('2649', '2644', '300403', '泾源县');
INSERT INTO `sys_regions` VALUES ('2650', '2644', '300404', '彭阳县');
INSERT INTO `sys_regions` VALUES ('2651', '2644', '300405', '原州区');
INSERT INTO `sys_regions` VALUES ('2652', '31', '3101', '乌鲁木齐市');
INSERT INTO `sys_regions` VALUES ('2653', '2652', '310101', '乌鲁木齐县');
INSERT INTO `sys_regions` VALUES ('2654', '31', '3102', '克拉玛依市');
INSERT INTO `sys_regions` VALUES ('2655', '2654', '310201', '克拉玛依区');
INSERT INTO `sys_regions` VALUES ('2656', '31', '3103', '石河子市');
INSERT INTO `sys_regions` VALUES ('2657', '2656', '310301', '石河子市');
INSERT INTO `sys_regions` VALUES ('2658', '31', '3104', '吐鲁番地区');
INSERT INTO `sys_regions` VALUES ('2659', '2658', '310401', '吐鲁番市');
INSERT INTO `sys_regions` VALUES ('2660', '2658', '310402', '托克逊县');
INSERT INTO `sys_regions` VALUES ('2661', '2658', '310403', '鄯善县');
INSERT INTO `sys_regions` VALUES ('2662', '31', '3105', '哈密地区');
INSERT INTO `sys_regions` VALUES ('2663', '2662', '310501', '哈密市');
INSERT INTO `sys_regions` VALUES ('2664', '2662', '310502', '巴里坤县');
INSERT INTO `sys_regions` VALUES ('2665', '2662', '310503', '伊吾县');
INSERT INTO `sys_regions` VALUES ('2666', '31', '3106', '和田地区');
INSERT INTO `sys_regions` VALUES ('2667', '2666', '310601', '和田市');
INSERT INTO `sys_regions` VALUES ('2669', '2666', '310602', '墨玉县');
INSERT INTO `sys_regions` VALUES ('2670', '2666', '310603', '洛浦县');
INSERT INTO `sys_regions` VALUES ('2671', '2666', '310604', '策勒县');
INSERT INTO `sys_regions` VALUES ('2672', '2666', '310605', '于田县');
INSERT INTO `sys_regions` VALUES ('2673', '2666', '310606', '民丰县');
INSERT INTO `sys_regions` VALUES ('2674', '2666', '310607', '皮山县');
INSERT INTO `sys_regions` VALUES ('2675', '31', '3107', '阿克苏地区');
INSERT INTO `sys_regions` VALUES ('2676', '2675', '310701', '阿克苏市');
INSERT INTO `sys_regions` VALUES ('2678', '2675', '310702', '温宿县');
INSERT INTO `sys_regions` VALUES ('2679', '2675', '310703', '沙雅县');
INSERT INTO `sys_regions` VALUES ('2680', '2675', '310704', '拜城县');
INSERT INTO `sys_regions` VALUES ('2681', '2675', '310705', '阿瓦提县');
INSERT INTO `sys_regions` VALUES ('2682', '2675', '310706', '库车县');
INSERT INTO `sys_regions` VALUES ('2683', '2675', '310707', '柯坪县');
INSERT INTO `sys_regions` VALUES ('2684', '2675', '310708', '新和县');
INSERT INTO `sys_regions` VALUES ('2685', '2675', '310709', '乌什县');
INSERT INTO `sys_regions` VALUES ('2686', '31', '3108', '喀什地区');
INSERT INTO `sys_regions` VALUES ('2687', '2686', '310801', '喀什市');
INSERT INTO `sys_regions` VALUES ('2688', '2686', '310802', '巴楚县');
INSERT INTO `sys_regions` VALUES ('2689', '2686', '310803', '泽普县');
INSERT INTO `sys_regions` VALUES ('2690', '2686', '310804', '伽师县');
INSERT INTO `sys_regions` VALUES ('2691', '2686', '310805', '叶城县');
INSERT INTO `sys_regions` VALUES ('2692', '2686', '310806', '岳普湖县');
INSERT INTO `sys_regions` VALUES ('2693', '2686', '310807', '疏附县');
INSERT INTO `sys_regions` VALUES ('2694', '2686', '310808', '疏勒县');
INSERT INTO `sys_regions` VALUES ('2695', '2686', '310809', '英吉沙县');
INSERT INTO `sys_regions` VALUES ('2696', '2686', '310810', '麦盖提县');
INSERT INTO `sys_regions` VALUES ('2697', '2686', '310811', '莎车县');
INSERT INTO `sys_regions` VALUES ('2698', '2686', '310812', '塔什库尔干县');
INSERT INTO `sys_regions` VALUES ('2699', '31', '3109', '克孜勒苏州');
INSERT INTO `sys_regions` VALUES ('2700', '2699', '310901', '阿图什市');
INSERT INTO `sys_regions` VALUES ('2701', '2699', '310902', '阿合奇县');
INSERT INTO `sys_regions` VALUES ('2702', '2699', '310903', '乌恰县');
INSERT INTO `sys_regions` VALUES ('2703', '2699', '310904', '阿克陶县');
INSERT INTO `sys_regions` VALUES ('2704', '31', '3110', '巴音郭楞州');
INSERT INTO `sys_regions` VALUES ('2705', '2704', '311001', '库尔勒市');
INSERT INTO `sys_regions` VALUES ('2706', '2704', '311002', '尉犁县');
INSERT INTO `sys_regions` VALUES ('2707', '2704', '311003', '和静县');
INSERT INTO `sys_regions` VALUES ('2708', '2704', '311004', '博湖县');
INSERT INTO `sys_regions` VALUES ('2709', '2704', '311005', '和硕县');
INSERT INTO `sys_regions` VALUES ('2710', '2704', '311006', '轮台县');
INSERT INTO `sys_regions` VALUES ('2711', '2704', '311007', '若羌县');
INSERT INTO `sys_regions` VALUES ('2712', '2704', '311008', '且末县');
INSERT INTO `sys_regions` VALUES ('2713', '2704', '311009', '焉耆县');
INSERT INTO `sys_regions` VALUES ('2714', '31', '3111', '昌吉州');
INSERT INTO `sys_regions` VALUES ('2715', '2714', '311101', '昌吉市');
INSERT INTO `sys_regions` VALUES ('2716', '2714', '311102', '阜康市');
INSERT INTO `sys_regions` VALUES ('2718', '2714', '311103', '奇台县');
INSERT INTO `sys_regions` VALUES ('2719', '2714', '311104', '玛纳斯县');
INSERT INTO `sys_regions` VALUES ('2720', '2714', '311105', '吉木萨尔县');
INSERT INTO `sys_regions` VALUES ('2721', '2714', '311106', '呼图壁县');
INSERT INTO `sys_regions` VALUES ('2722', '2714', '311107', '木垒县');
INSERT INTO `sys_regions` VALUES ('2723', '31', '3112', '博尔塔拉州');
INSERT INTO `sys_regions` VALUES ('2724', '2723', '311201', '博乐市');
INSERT INTO `sys_regions` VALUES ('2725', '2723', '311202', '精河县');
INSERT INTO `sys_regions` VALUES ('2726', '2723', '311203', '温泉县');
INSERT INTO `sys_regions` VALUES ('2727', '31', '3113', '伊犁州');
INSERT INTO `sys_regions` VALUES ('2728', '2727', '311301', '伊宁市');
INSERT INTO `sys_regions` VALUES ('2729', '2727', '311302', '特克斯县');
INSERT INTO `sys_regions` VALUES ('2730', '2727', '311303', '尼勒克县');
INSERT INTO `sys_regions` VALUES ('2731', '2727', '311304', '昭苏县');
INSERT INTO `sys_regions` VALUES ('2732', '2727', '311305', '新源县');
INSERT INTO `sys_regions` VALUES ('2733', '2727', '311306', '霍城县');
INSERT INTO `sys_regions` VALUES ('2734', '2727', '311307', '察布查尔县');
INSERT INTO `sys_regions` VALUES ('2735', '2727', '311308', '巩留县');
INSERT INTO `sys_regions` VALUES ('2736', '31', '3114', '塔城地区');
INSERT INTO `sys_regions` VALUES ('2737', '2736', '311401', '塔城市');
INSERT INTO `sys_regions` VALUES ('2738', '2736', '311402', '乌苏市');
INSERT INTO `sys_regions` VALUES ('2739', '2736', '311403', '额敏县');
INSERT INTO `sys_regions` VALUES ('2740', '2736', '311404', '裕民县');
INSERT INTO `sys_regions` VALUES ('2741', '2736', '311405', '沙湾县');
INSERT INTO `sys_regions` VALUES ('2742', '2736', '311406', '托里县');
INSERT INTO `sys_regions` VALUES ('2743', '2736', '311407', '和布克赛尔县');
INSERT INTO `sys_regions` VALUES ('2744', '31', '3115', '阿勒泰地区');
INSERT INTO `sys_regions` VALUES ('2745', '2744', '311501', '阿勒泰市');
INSERT INTO `sys_regions` VALUES ('2746', '2744', '311502', '富蕴县');
INSERT INTO `sys_regions` VALUES ('2747', '2744', '311503', '青河县');
INSERT INTO `sys_regions` VALUES ('2748', '2744', '311504', '吉木乃县');
INSERT INTO `sys_regions` VALUES ('2749', '2744', '311505', '布尔津县');
INSERT INTO `sys_regions` VALUES ('2750', '2744', '311506', '福海县');
INSERT INTO `sys_regions` VALUES ('2751', '2744', '311507', '哈巴河县');
INSERT INTO `sys_regions` VALUES ('2756', '258', '050801', '遵化市');
INSERT INTO `sys_regions` VALUES ('2757', '258', '050802', '丰南区');
INSERT INTO `sys_regions` VALUES ('2759', '258', '050803', '迁西县');
INSERT INTO `sys_regions` VALUES ('2760', '258', '050804', '滦南县');
INSERT INTO `sys_regions` VALUES ('2762', '258', '050805', '玉田县');
INSERT INTO `sys_regions` VALUES ('2763', '258', '050806', '曹妃甸区');
INSERT INTO `sys_regions` VALUES ('2764', '258', '050807', '乐亭县');
INSERT INTO `sys_regions` VALUES ('2765', '258', '050808', '滦县');
INSERT INTO `sys_regions` VALUES ('2767', '239', '050607', '隆化县');
INSERT INTO `sys_regions` VALUES ('2768', '32', '3201', '台湾');
INSERT INTO `sys_regions` VALUES ('2769', '2768', '320101', '台湾市区内');
INSERT INTO `sys_regions` VALUES ('2772', '1090', '131306', '无棣县');
INSERT INTO `sys_regions` VALUES ('2773', '1099', '131408', '鄄城县');
INSERT INTO `sys_regions` VALUES ('2774', '965', '120903', '海安县');
INSERT INTO `sys_regions` VALUES ('2777', '1657', '191101', '南朗镇');
INSERT INTO `sys_regions` VALUES ('2780', '7', '0718', '济源市');
INSERT INTO `sys_regions` VALUES ('2782', '412', '070103', '上街区');
INSERT INTO `sys_regions` VALUES ('2799', '72', '010101', '三环以内');
INSERT INTO `sys_regions` VALUES ('2800', '1', '0102', '海淀区');
INSERT INTO `sys_regions` VALUES ('2801', '1', '0103', '西城区');
INSERT INTO `sys_regions` VALUES ('2802', '1', '0104', '东城区');
INSERT INTO `sys_regions` VALUES ('2803', '1', '0105', '崇文区');
INSERT INTO `sys_regions` VALUES ('2804', '1', '0106', '宣武区');
INSERT INTO `sys_regions` VALUES ('2805', '1', '0107', '丰台区');
INSERT INTO `sys_regions` VALUES ('2806', '1', '0108', '石景山区');
INSERT INTO `sys_regions` VALUES ('2807', '1', '0109', '门头沟');
INSERT INTO `sys_regions` VALUES ('2808', '1', '0110', '房山区');
INSERT INTO `sys_regions` VALUES ('2809', '1', '0111', '通州区');
INSERT INTO `sys_regions` VALUES ('2810', '1', '0112', '大兴区');
INSERT INTO `sys_regions` VALUES ('2812', '1', '0113', '顺义区');
INSERT INTO `sys_regions` VALUES ('2813', '2', '0202', '徐汇区');
INSERT INTO `sys_regions` VALUES ('2814', '1', '0114', '怀柔区');
INSERT INTO `sys_regions` VALUES ('2815', '2', '0203', '长宁区');
INSERT INTO `sys_regions` VALUES ('2816', '1', '0115', '密云区');
INSERT INTO `sys_regions` VALUES ('2817', '2', '0204', '静安区');
INSERT INTO `sys_regions` VALUES ('2819', '72', '010102', '三环到四环之间');
INSERT INTO `sys_regions` VALUES ('2820', '2', '0205', '闸北区');
INSERT INTO `sys_regions` VALUES ('2821', '2802', '010401', '内环到三环里');
INSERT INTO `sys_regions` VALUES ('2822', '2', '0206', '虹口区');
INSERT INTO `sys_regions` VALUES ('2823', '2', '0207', '杨浦区');
INSERT INTO `sys_regions` VALUES ('2824', '2', '0208', '宝山区');
INSERT INTO `sys_regions` VALUES ('2825', '2', '0209', '闵行区');
INSERT INTO `sys_regions` VALUES ('2826', '2', '0210', '嘉定区');
INSERT INTO `sys_regions` VALUES ('2827', '2801', '010301', '内环到二环里');
INSERT INTO `sys_regions` VALUES ('2828', '2804', '010601', '内环到三环里');
INSERT INTO `sys_regions` VALUES ('2829', '2803', '010501', '一环到二环');
INSERT INTO `sys_regions` VALUES ('2830', '2', '0211', '浦东新区');
INSERT INTO `sys_regions` VALUES ('2831', '2806', '010801', '四环到五环内');
INSERT INTO `sys_regions` VALUES ('2832', '2805', '010701', '四环到五环之间');
INSERT INTO `sys_regions` VALUES ('2833', '2', '0212', '青浦区');
INSERT INTO `sys_regions` VALUES ('2834', '2', '0213', '松江区');
INSERT INTO `sys_regions` VALUES ('2835', '2', '0214', '金山区');
INSERT INTO `sys_regions` VALUES ('2837', '2', '0215', '奉贤区');
INSERT INTO `sys_regions` VALUES ('2839', '72', '010103', '四环到五环之间');
INSERT INTO `sys_regions` VALUES ('2840', '72', '010104', '五环到六环之间');
INSERT INTO `sys_regions` VALUES ('2841', '2', '0216', '普陀区');
INSERT INTO `sys_regions` VALUES ('2842', '2803', '010502', '二环到三环');
INSERT INTO `sys_regions` VALUES ('2847', '2814', '011401', '郊区');
INSERT INTO `sys_regions` VALUES ('2848', '2800', '010201', '三环以内');
INSERT INTO `sys_regions` VALUES ('2849', '2800', '010202', '三环到四环之间');
INSERT INTO `sys_regions` VALUES ('2850', '2800', '010203', '四环到五环之间');
INSERT INTO `sys_regions` VALUES ('2851', '2800', '010204', '五环到六环之间');
INSERT INTO `sys_regions` VALUES ('2852', '2800', '010205', '六环以外');
INSERT INTO `sys_regions` VALUES ('2853', '2801', '010302', '二环到三环');
INSERT INTO `sys_regions` VALUES ('2854', '2805', '010702', '二环到三环');
INSERT INTO `sys_regions` VALUES ('2855', '2805', '010703', '三环到四环之间');
INSERT INTO `sys_regions` VALUES ('2862', '2816', '011501', '城区以外');
INSERT INTO `sys_regions` VALUES ('2900', '13', '1317', '济宁市');
INSERT INTO `sys_regions` VALUES ('2901', '1', '0116', '昌平区');
INSERT INTO `sys_regions` VALUES ('2902', '1657', '191102', '小榄镇');
INSERT INTO `sys_regions` VALUES ('2906', '2901', '011601', '城区以外');
INSERT INTO `sys_regions` VALUES ('2907', '51043', '030901', '全境');
INSERT INTO `sys_regions` VALUES ('2908', '2900', '131701', '梁山县');
INSERT INTO `sys_regions` VALUES ('2910', '2900', '131702', '兖州市');
INSERT INTO `sys_regions` VALUES ('2912', '2900', '131703', '微山县');
INSERT INTO `sys_regions` VALUES ('2913', '2900', '131704', '汶上县');
INSERT INTO `sys_regions` VALUES ('2914', '2900', '131705', '泗水县');
INSERT INTO `sys_regions` VALUES ('2915', '2900', '131706', '嘉祥县');
INSERT INTO `sys_regions` VALUES ('2916', '2900', '131707', '鱼台县');
INSERT INTO `sys_regions` VALUES ('2917', '2900', '131708', '金乡县');
INSERT INTO `sys_regions` VALUES ('2919', '2', '0217', '崇明县');
INSERT INTO `sys_regions` VALUES ('2922', '17', '1714', '潜江市');
INSERT INTO `sys_regions` VALUES ('2924', '1016', '130304', '周村区');
INSERT INTO `sys_regions` VALUES ('2926', '1072', '131107', '莒南县');
INSERT INTO `sys_regions` VALUES ('2927', '978', '121103', '新北区');
INSERT INTO `sys_regions` VALUES ('2930', '2654', '310202', '独山子区');
INSERT INTO `sys_regions` VALUES ('2934', '1108', '131501', '五莲县');
INSERT INTO `sys_regions` VALUES ('2950', '1655', '191001', '中堂镇');
INSERT INTO `sys_regions` VALUES ('2951', '26', '2601', '拉萨市');
INSERT INTO `sys_regions` VALUES ('2952', '2951', '260101', '城关区');
INSERT INTO `sys_regions` VALUES ('2953', '1', '0117', '平谷区');
INSERT INTO `sys_regions` VALUES ('2954', '2953', '011701', '城区以外');
INSERT INTO `sys_regions` VALUES ('2956', '1352', '160709', '光泽县');
INSERT INTO `sys_regions` VALUES ('2957', '1657', '191103', '古镇');
INSERT INTO `sys_regions` VALUES ('2958', '2281', '250510', '孟连县');
INSERT INTO `sys_regions` VALUES ('2962', '1016', '130305', '淄川区');
INSERT INTO `sys_regions` VALUES ('2963', '1213', '150207', '江干区');
INSERT INTO `sys_regions` VALUES ('2966', '2637', '300305', '利通区');
INSERT INTO `sys_regions` VALUES ('2967', '325', '060405', '泽州县');
INSERT INTO `sys_regions` VALUES ('2968', '1016', '130306', '博山区');
INSERT INTO `sys_regions` VALUES ('2969', '1016', '130307', '临淄区');
INSERT INTO `sys_regions` VALUES ('2970', '2509', '280501', '雄关区');
INSERT INTO `sys_regions` VALUES ('2971', '14', '1416', '宣城市');
INSERT INTO `sys_regions` VALUES ('2972', '2971', '141601', '泾县');
INSERT INTO `sys_regions` VALUES ('2973', '1477', '171202', '钟祥市');
INSERT INTO `sys_regions` VALUES ('2974', '1072', '131108', '郯城县');
INSERT INTO `sys_regions` VALUES ('2980', '17', '1715', '天门市');
INSERT INTO `sys_regions` VALUES ('2983', '17', '1716', '仙桃市');
INSERT INTO `sys_regions` VALUES ('2984', '51036', '030201', '全境');
INSERT INTO `sys_regions` VALUES ('2985', '51039', '030501', '全境');
INSERT INTO `sys_regions` VALUES ('2986', '51040', '030601', '全境');
INSERT INTO `sys_regions` VALUES ('2987', '51037', '030301', '全境');
INSERT INTO `sys_regions` VALUES ('2990', '248', '050704', '北戴河区');
INSERT INTO `sys_regions` VALUES ('2991', '1818', '201208', '罗城县');
INSERT INTO `sys_regions` VALUES ('2992', '9', '0909', '辽源市');
INSERT INTO `sys_regions` VALUES ('2993', '2992', '090901', '龙山区');
INSERT INTO `sys_regions` VALUES ('2994', '2992', '090902', '西安区');
INSERT INTO `sys_regions` VALUES ('2995', '2992', '090903', '东丰县');
INSERT INTO `sys_regions` VALUES ('2996', '2992', '090904', '东辽县');
INSERT INTO `sys_regions` VALUES ('2999', '1753', '200703', '钦北区');
INSERT INTO `sys_regions` VALUES ('3000', '51038', '030401', '全境');
INSERT INTO `sys_regions` VALUES ('3001', '1657', '191104', '坦洲镇');
INSERT INTO `sys_regions` VALUES ('3002', '2270', '250410', '鲁甸县');
INSERT INTO `sys_regions` VALUES ('3003', '2270', '250411', '绥江县');
INSERT INTO `sys_regions` VALUES ('3005', '1715', '200103', '宾阳县');
INSERT INTO `sys_regions` VALUES ('3006', '2654', '310203', '白碱滩区');
INSERT INTO `sys_regions` VALUES ('3007', '1657', '191105', '黄圃镇');
INSERT INTO `sys_regions` VALUES ('3016', '1657', '191106', '三乡镇');
INSERT INTO `sys_regions` VALUES ('3021', '2620', '290808', '格尔木市');
INSERT INTO `sys_regions` VALUES ('3022', '1329', '160402', '涵江区');
INSERT INTO `sys_regions` VALUES ('3023', '2518', '280606', '崆峒区');
INSERT INTO `sys_regions` VALUES ('3024', '904', '120104', '溧水区');
INSERT INTO `sys_regions` VALUES ('3034', '23', '2302', '儋州市');
INSERT INTO `sys_regions` VALUES ('3037', '1650', '190902', '海丰县');
INSERT INTO `sys_regions` VALUES ('3038', '1213', '150208', '滨江区');
INSERT INTO `sys_regions` VALUES ('3041', '1655', '191002', '东坑镇');
INSERT INTO `sys_regions` VALUES ('3044', '20', '2013', '来宾市');
INSERT INTO `sys_regions` VALUES ('3045', '1280', '150908', '庆元县');
INSERT INTO `sys_regions` VALUES ('3046', '3044', '201301', '兴宾区');
INSERT INTO `sys_regions` VALUES ('3047', '3044', '201302', '合山市');
INSERT INTO `sys_regions` VALUES ('3048', '3044', '201303', '忻城县');
INSERT INTO `sys_regions` VALUES ('3049', '3044', '201304', '武宣县');
INSERT INTO `sys_regions` VALUES ('3050', '3044', '201305', '象州县');
INSERT INTO `sys_regions` VALUES ('3051', '3044', '201306', '金秀县');
INSERT INTO `sys_regions` VALUES ('3055', '1477', '171203', '沙洋县');
INSERT INTO `sys_regions` VALUES ('3065', '1', '0118', '延庆县');
INSERT INTO `sys_regions` VALUES ('3067', '1657', '191107', '东凤镇');
INSERT INTO `sys_regions` VALUES ('3068', '1108', '131502', '莒县');
INSERT INTO `sys_regions` VALUES ('3070', '1574', '181210', '沅陵县');
INSERT INTO `sys_regions` VALUES ('3071', '30', '3005', '中卫市');
INSERT INTO `sys_regions` VALUES ('3072', '3071', '300501', '中宁县');
INSERT INTO `sys_regions` VALUES ('3073', '325', '060406', '城区');
INSERT INTO `sys_regions` VALUES ('3074', '6', '0611', '长治市');
INSERT INTO `sys_regions` VALUES ('3075', '3074', '061101', '长治县');
INSERT INTO `sys_regions` VALUES ('3077', '148', '050214', '临漳县');
INSERT INTO `sys_regions` VALUES ('3078', '1655', '191003', '道滘镇');
INSERT INTO `sys_regions` VALUES ('3079', '1381', '170102', '武昌区');
INSERT INTO `sys_regions` VALUES ('3080', '28', '2814', '定西市');
INSERT INTO `sys_regions` VALUES ('3081', '3080', '281401', '岷县');
INSERT INTO `sys_regions` VALUES ('3082', '988', '121303', '相城区');
INSERT INTO `sys_regions` VALUES ('3083', '988', '121304', '金阊区');
INSERT INTO `sys_regions` VALUES ('3085', '988', '121305', '虎丘区');
INSERT INTO `sys_regions` VALUES ('3087', '988', '121306', '平江区');
INSERT INTO `sys_regions` VALUES ('3088', '988', '121307', '沧浪区');
INSERT INTO `sys_regions` VALUES ('3092', '239', '050608', '承德县');
INSERT INTO `sys_regions` VALUES ('3096', '776', '101105', '嫩江县');
INSERT INTO `sys_regions` VALUES ('3097', '1655', '191004', '沙田镇');
INSERT INTO `sys_regions` VALUES ('3098', '164', '050317', '威县');
INSERT INTO `sys_regions` VALUES ('3100', '1655', '191005', '高埗镇');
INSERT INTO `sys_regions` VALUES ('3102', '1655', '191006', '石龙镇');
INSERT INTO `sys_regions` VALUES ('3104', '1655', '191007', '石排镇');
INSERT INTO `sys_regions` VALUES ('3105', '1655', '191008', '企石镇');
INSERT INTO `sys_regions` VALUES ('3107', '26', '2602', '那曲地区');
INSERT INTO `sys_regions` VALUES ('3108', '3107', '260201', '索县');
INSERT INTO `sys_regions` VALUES ('3109', '3074', '061102', '潞城市');
INSERT INTO `sys_regions` VALUES ('3111', '1655', '191009', '石碣镇');
INSERT INTO `sys_regions` VALUES ('3112', '1657', '191108', '横栏镇');
INSERT INTO `sys_regions` VALUES ('3113', '495', '071205', '渑池县');
INSERT INTO `sys_regions` VALUES ('3115', '23', '2303', '琼海市');
INSERT INTO `sys_regions` VALUES ('3116', '1655', '191010', '洪梅镇');
INSERT INTO `sys_regions` VALUES ('3117', '1332', '160507', '泉港区');
INSERT INTO `sys_regions` VALUES ('3118', '330', '060506', '平鲁区');
INSERT INTO `sys_regions` VALUES ('3119', '549', '071708', '商城县');
INSERT INTO `sys_regions` VALUES ('3120', '1655', '191011', '麻涌镇');
INSERT INTO `sys_regions` VALUES ('3121', '1988', '220905', '东兴区');
INSERT INTO `sys_regions` VALUES ('3123', '2951', '260102', '林周县');
INSERT INTO `sys_regions` VALUES ('3125', '3034', '230201', '那大镇');
INSERT INTO `sys_regions` VALUES ('3126', '1042', '130707', '芝罘区');
INSERT INTO `sys_regions` VALUES ('3127', '420', '070205', '通许县');
INSERT INTO `sys_regions` VALUES ('3128', '2971', '141602', '旌德县');
INSERT INTO `sys_regions` VALUES ('3129', '26', '2603', '山南地区');
INSERT INTO `sys_regions` VALUES ('3130', '3129', '260301', '贡嘎县');
INSERT INTO `sys_regions` VALUES ('3132', '1112', '131601', '东平县');
INSERT INTO `sys_regions` VALUES ('3133', '799', '110105', '清水河县');
INSERT INTO `sys_regions` VALUES ('3134', '1655', '191012', '桥头镇');
INSERT INTO `sys_regions` VALUES ('3136', '379', '060916', '曲沃县');
INSERT INTO `sys_regions` VALUES ('3137', '23', '2304', '万宁市');
INSERT INTO `sys_regions` VALUES ('3138', '26', '2604', '昌都地区');
INSERT INTO `sys_regions` VALUES ('3139', '3138', '260401', '昌都县');
INSERT INTO `sys_regions` VALUES ('3142', '902', '111201', '霍林郭勒市');
INSERT INTO `sys_regions` VALUES ('3143', '1657', '191109', '三角镇');
INSERT INTO `sys_regions` VALUES ('3144', '26', '2605', '日喀则地区');
INSERT INTO `sys_regions` VALUES ('3147', '2971', '141603', '宁国市');
INSERT INTO `sys_regions` VALUES ('3148', '3071', '300502', '海原县');
INSERT INTO `sys_regions` VALUES ('3151', '1655', '191013', '望牛墩镇');
INSERT INTO `sys_regions` VALUES ('3152', '1818', '201209', '大化县');
INSERT INTO `sys_regions` VALUES ('3154', '17', '1717', '神农架林区');
INSERT INTO `sys_regions` VALUES ('3155', '1607', '190201', '南山区');
INSERT INTO `sys_regions` VALUES ('3156', '224', '050514', '沽源县');
INSERT INTO `sys_regions` VALUES ('3160', '3144', '260501', '聂拉木县');
INSERT INTO `sys_regions` VALUES ('3163', '1479', '171301', '广水市');
INSERT INTO `sys_regions` VALUES ('3164', '1479', '171302', '曾都区');
INSERT INTO `sys_regions` VALUES ('3166', '3144', '260502', '昂仁县');
INSERT INTO `sys_regions` VALUES ('3168', '20', '2014', '崇左市');
INSERT INTO `sys_regions` VALUES ('3169', '3168', '201401', '江州区');
INSERT INTO `sys_regions` VALUES ('3171', '1655', '191014', '茶山镇');
INSERT INTO `sys_regions` VALUES ('3172', '639', '090104', '德惠市');
INSERT INTO `sys_regions` VALUES ('3173', '23', '2305', '东方市');
INSERT INTO `sys_regions` VALUES ('3175', '2573', '281307', '临夏市');
INSERT INTO `sys_regions` VALUES ('3176', '1657', '191110', '南头镇');
INSERT INTO `sys_regions` VALUES ('3182', '142', '050114', '井陉矿区');
INSERT INTO `sys_regions` VALUES ('3187', '148', '050215', '永年县');
INSERT INTO `sys_regions` VALUES ('3190', '199', '050418', '新市区');
INSERT INTO `sys_regions` VALUES ('3191', '199', '050419', '北市区');
INSERT INTO `sys_regions` VALUES ('3192', '199', '050420', '南市区');
INSERT INTO `sys_regions` VALUES ('3193', '199', '050421', '安新县');
INSERT INTO `sys_regions` VALUES ('3197', '239', '050609', '双滦区');
INSERT INTO `sys_regions` VALUES ('3198', '239', '050610', '鹰手营子矿区');
INSERT INTO `sys_regions` VALUES ('3199', '812', '110410', '元宝山区');
INSERT INTO `sys_regions` VALUES ('3202', '258', '050809', '古冶区');
INSERT INTO `sys_regions` VALUES ('3203', '258', '050810', '开平区');
INSERT INTO `sys_regions` VALUES ('3206', '274', '051007', '安次区');
INSERT INTO `sys_regions` VALUES ('3207', '274', '051008', '广阳区');
INSERT INTO `sys_regions` VALUES ('3214', '309', '060208', '新荣区');
INSERT INTO `sys_regions` VALUES ('3216', '309', '060209', '南郊区');
INSERT INTO `sys_regions` VALUES ('3217', '309', '060210', '矿区');
INSERT INTO `sys_regions` VALUES ('3219', '318', '060304', '矿区');
INSERT INTO `sys_regions` VALUES ('3222', '3074', '061103', '郊区');
INSERT INTO `sys_regions` VALUES ('3223', '3074', '061104', '襄垣县');
INSERT INTO `sys_regions` VALUES ('3224', '3074', '061105', '屯留县');
INSERT INTO `sys_regions` VALUES ('3225', '3074', '061106', '平顺县');
INSERT INTO `sys_regions` VALUES ('3226', '3074', '061107', '黎城县');
INSERT INTO `sys_regions` VALUES ('3227', '3074', '061108', '壶关县');
INSERT INTO `sys_regions` VALUES ('3228', '3074', '061109', '长子县');
INSERT INTO `sys_regions` VALUES ('3229', '3074', '061110', '武乡县');
INSERT INTO `sys_regions` VALUES ('3230', '3074', '061111', '沁县');
INSERT INTO `sys_regions` VALUES ('3231', '3074', '061112', '沁源县');
INSERT INTO `sys_regions` VALUES ('3233', '398', '061012', '闻喜县');
INSERT INTO `sys_regions` VALUES ('3235', '368', '060811', '交口县');
INSERT INTO `sys_regions` VALUES ('3236', '368', '060812', '交城县');
INSERT INTO `sys_regions` VALUES ('3237', '368', '060813', '石楼县');
INSERT INTO `sys_regions` VALUES ('3240', '799', '110106', '玉泉区');
INSERT INTO `sys_regions` VALUES ('3241', '799', '110107', '赛罕区');
INSERT INTO `sys_regions` VALUES ('3245', '805', '110204', '石拐区');
INSERT INTO `sys_regions` VALUES ('3246', '805', '110205', '白云矿区');
INSERT INTO `sys_regions` VALUES ('3248', '810', '110302', '海南区');
INSERT INTO `sys_regions` VALUES ('3249', '810', '110303', '乌达区');
INSERT INTO `sys_regions` VALUES ('3251', '812', '110411', '松山区');
INSERT INTO `sys_regions` VALUES ('3252', '902', '111202', '开鲁县');
INSERT INTO `sys_regions` VALUES ('3253', '902', '111203', '库伦旗');
INSERT INTO `sys_regions` VALUES ('3254', '902', '111204', '奈曼旗');
INSERT INTO `sys_regions` VALUES ('3255', '902', '111205', '扎鲁特旗');
INSERT INTO `sys_regions` VALUES ('3256', '902', '111206', '科尔沁左翼中旗');
INSERT INTO `sys_regions` VALUES ('3258', '902', '111207', '科尔沁左翼后旗');
INSERT INTO `sys_regions` VALUES ('3261', '573', '080205', '沙河口区');
INSERT INTO `sys_regions` VALUES ('3263', '573', '080206', '西岗区');
INSERT INTO `sys_regions` VALUES ('3264', '579', '080304', '铁东区');
INSERT INTO `sys_regions` VALUES ('3266', '579', '080305', '立山区');
INSERT INTO `sys_regions` VALUES ('3268', '584', '080404', '望花区');
INSERT INTO `sys_regions` VALUES ('3269', '584', '080405', '东洲区');
INSERT INTO `sys_regions` VALUES ('3270', '584', '080406', '新抚区');
INSERT INTO `sys_regions` VALUES ('3271', '584', '080407', '顺城区');
INSERT INTO `sys_regions` VALUES ('3275', '589', '080502', '南芬区');
INSERT INTO `sys_regions` VALUES ('3282', '609', '080903', '老边区');
INSERT INTO `sys_regions` VALUES ('3283', '609', '080904', '西市区');
INSERT INTO `sys_regions` VALUES ('3286', '617', '081103', '清河门区');
INSERT INTO `sys_regions` VALUES ('3288', '617', '081104', '新邱区');
INSERT INTO `sys_regions` VALUES ('3290', '621', '081202', '太子河区');
INSERT INTO `sys_regions` VALUES ('3291', '621', '081203', '弓长岭区');
INSERT INTO `sys_regions` VALUES ('3292', '621', '081204', '宏伟区');
INSERT INTO `sys_regions` VALUES ('3299', '632', '081306', '龙城区');
INSERT INTO `sys_regions` VALUES ('3300', '604', '080804', '龙港区');
INSERT INTO `sys_regions` VALUES ('3306', '639', '090105', '双阳区');
INSERT INTO `sys_regions` VALUES ('3311', '657', '090407', '东昌区');
INSERT INTO `sys_regions` VALUES ('3312', '687', '090801', '图们市');
INSERT INTO `sys_regions` VALUES ('3313', '687', '090802', '敦化市');
INSERT INTO `sys_regions` VALUES ('3314', '687', '090803', '珲春市');
INSERT INTO `sys_regions` VALUES ('3315', '687', '090804', '龙井市');
INSERT INTO `sys_regions` VALUES ('3316', '687', '090805', '和龙市');
INSERT INTO `sys_regions` VALUES ('3317', '687', '090806', '汪清县');
INSERT INTO `sys_regions` VALUES ('3318', '687', '090807', '安图县');
INSERT INTO `sys_regions` VALUES ('3329', '737', '100504', '恒山区');
INSERT INTO `sys_regions` VALUES ('3330', '737', '100505', '滴道区');
INSERT INTO `sys_regions` VALUES ('3331', '737', '100506', '梨树区');
INSERT INTO `sys_regions` VALUES ('3332', '737', '100507', '城子河区');
INSERT INTO `sys_regions` VALUES ('3333', '737', '100508', '麻山区');
INSERT INTO `sys_regions` VALUES ('3334', '727', '100303', '兴山区');
INSERT INTO `sys_regions` VALUES ('3335', '727', '100304', '向阳区');
INSERT INTO `sys_regions` VALUES ('3336', '727', '100305', '工农区');
INSERT INTO `sys_regions` VALUES ('3337', '727', '100306', '南山区');
INSERT INTO `sys_regions` VALUES ('3338', '727', '100307', '兴安区');
INSERT INTO `sys_regions` VALUES ('3339', '727', '100308', '东山区');
INSERT INTO `sys_regions` VALUES ('3340', '731', '100405', '尖山区');
INSERT INTO `sys_regions` VALUES ('3341', '731', '100406', '岭东区');
INSERT INTO `sys_regions` VALUES ('3342', '731', '100407', '四方台区');
INSERT INTO `sys_regions` VALUES ('3343', '731', '100408', '宝山区');
INSERT INTO `sys_regions` VALUES ('3344', '753', '100703', '伊春区');
INSERT INTO `sys_regions` VALUES ('3345', '753', '100704', '南岔区');
INSERT INTO `sys_regions` VALUES ('3346', '753', '100705', '友好区');
INSERT INTO `sys_regions` VALUES ('3347', '753', '100706', '西林区');
INSERT INTO `sys_regions` VALUES ('3348', '753', '100707', '翠峦区');
INSERT INTO `sys_regions` VALUES ('3349', '753', '100708', '新青区');
INSERT INTO `sys_regions` VALUES ('3350', '753', '100709', '美溪区');
INSERT INTO `sys_regions` VALUES ('3351', '753', '100710', '金山屯区');
INSERT INTO `sys_regions` VALUES ('3352', '753', '100711', '五营区');
INSERT INTO `sys_regions` VALUES ('3353', '753', '100712', '乌马河区');
INSERT INTO `sys_regions` VALUES ('3354', '753', '100713', '汤旺河区');
INSERT INTO `sys_regions` VALUES ('3355', '753', '100714', '带岭区');
INSERT INTO `sys_regions` VALUES ('3356', '753', '100715', '乌伊岭区');
INSERT INTO `sys_regions` VALUES ('3357', '753', '100716', '红星区');
INSERT INTO `sys_regions` VALUES ('3358', '753', '100717', '上甘岭区');
INSERT INTO `sys_regions` VALUES ('3364', '773', '101002', '桃山区');
INSERT INTO `sys_regions` VALUES ('3365', '773', '101003', '新兴区');
INSERT INTO `sys_regions` VALUES ('3366', '773', '101004', '茄子河区');
INSERT INTO `sys_regions` VALUES ('3367', '757', '100806', '爱民区');
INSERT INTO `sys_regions` VALUES ('3368', '757', '100807', '东安区');
INSERT INTO `sys_regions` VALUES ('3369', '757', '100808', '阳明区');
INSERT INTO `sys_regions` VALUES ('3370', '757', '100809', '西安区');
INSERT INTO `sys_regions` VALUES ('3371', '776', '101106', '爱辉区');
INSERT INTO `sys_regions` VALUES ('3373', '904', '120105', '玄武区');
INSERT INTO `sys_regions` VALUES ('3375', '904', '120106', '秦淮区');
INSERT INTO `sys_regions` VALUES ('3376', '904', '120107', '建邺区');
INSERT INTO `sys_regions` VALUES ('3377', '904', '120108', '鼓楼区');
INSERT INTO `sys_regions` VALUES ('3378', '904', '120109', '栖霞区');
INSERT INTO `sys_regions` VALUES ('3379', '904', '120110', '雨花台区');
INSERT INTO `sys_regions` VALUES ('3381', '984', '121201', '崇安区');
INSERT INTO `sys_regions` VALUES ('3382', '984', '121202', '南长区');
INSERT INTO `sys_regions` VALUES ('3383', '984', '121203', '北塘区');
INSERT INTO `sys_regions` VALUES ('3384', '984', '121204', '锡山区');
INSERT INTO `sys_regions` VALUES ('3385', '984', '121205', '惠山区');
INSERT INTO `sys_regions` VALUES ('3388', '911', '120205', '贾汪区');
INSERT INTO `sys_regions` VALUES ('3392', '978', '121104', '钟楼区');
INSERT INTO `sys_regions` VALUES ('3393', '978', '121105', '天宁区');
INSERT INTO `sys_regions` VALUES ('3394', '965', '120904', '港闸区');
INSERT INTO `sys_regions` VALUES ('3395', '965', '120905', '崇川区');
INSERT INTO `sys_regions` VALUES ('3403', '972', '121003', '润州区');
INSERT INTO `sys_regions` VALUES ('3404', '972', '121004', '京口区');
INSERT INTO `sys_regions` VALUES ('3405', '959', '120804', '高港区');
INSERT INTO `sys_regions` VALUES ('3406', '959', '120805', '海陵区');
INSERT INTO `sys_regions` VALUES ('3407', '933', '120503', '宿城区');
INSERT INTO `sys_regions` VALUES ('3408', '1213', '150209', '上城区');
INSERT INTO `sys_regions` VALUES ('3409', '1213', '150210', '下城区');
INSERT INTO `sys_regions` VALUES ('3410', '1213', '150211', '拱墅区');
INSERT INTO `sys_regions` VALUES ('3411', '1213', '150212', '西湖区');
INSERT INTO `sys_regions` VALUES ('3412', '1158', '150105', '海曙区');
INSERT INTO `sys_regions` VALUES ('3413', '1158', '150106', '江东区');
INSERT INTO `sys_regions` VALUES ('3416', '1233', '150306', '龙湾区');
INSERT INTO `sys_regions` VALUES ('3418', '1243', '150403', '南湖区');
INSERT INTO `sys_regions` VALUES ('3419', '1243', '150404', '秀洲区');
INSERT INTO `sys_regions` VALUES ('3431', '1116', '140203', '包河区');
INSERT INTO `sys_regions` VALUES ('3432', '1116', '140204', '蜀山区');
INSERT INTO `sys_regions` VALUES ('3433', '1116', '140205', '瑶海区');
INSERT INTO `sys_regions` VALUES ('3434', '1116', '140206', '庐阳区');
INSERT INTO `sys_regions` VALUES ('3438', '1127', '140505', '镜湖区');
INSERT INTO `sys_regions` VALUES ('3442', '1132', '140604', '蚌山区');
INSERT INTO `sys_regions` VALUES ('3444', '988', '121308', '工业园区');
INSERT INTO `sys_regions` VALUES ('3447', '1121', '140302', '田家庵区');
INSERT INTO `sys_regions` VALUES ('3448', '1121', '140303', '大通区');
INSERT INTO `sys_regions` VALUES ('3449', '1121', '140304', '谢家集区');
INSERT INTO `sys_regions` VALUES ('3450', '1121', '140305', '八公山区');
INSERT INTO `sys_regions` VALUES ('3451', '1121', '140306', '潘集区');
INSERT INTO `sys_regions` VALUES ('3464', '1151', '140905', '黄山区');
INSERT INTO `sys_regions` VALUES ('3467', '1159', '141006', '南谯区');
INSERT INTO `sys_regions` VALUES ('3477', '2971', '141604', '郎溪县');
INSERT INTO `sys_regions` VALUES ('3478', '2971', '141605', '广德县');
INSERT INTO `sys_regions` VALUES ('3479', '2971', '141606', '绩溪县');
INSERT INTO `sys_regions` VALUES ('3483', '1303', '160107', '台江区');
INSERT INTO `sys_regions` VALUES ('3484', '1303', '160108', '鼓楼区');
INSERT INTO `sys_regions` VALUES ('3486', '1315', '160202', '湖里区');
INSERT INTO `sys_regions` VALUES ('3489', '1315', '160203', '翔安区');
INSERT INTO `sys_regions` VALUES ('3492', '1329', '160403', '秀屿区');
INSERT INTO `sys_regions` VALUES ('3495', '1332', '160508', '金门县');
INSERT INTO `sys_regions` VALUES ('3498', '1332', '160509', '洛江区');
INSERT INTO `sys_regions` VALUES ('3499', '1341', '160610', '芗城区');
INSERT INTO `sys_regions` VALUES ('3500', '1341', '160611', '龙文区');
INSERT INTO `sys_regions` VALUES ('3502', '1827', '210104', '新建县');
INSERT INTO `sys_regions` VALUES ('3504', '1827', '210105', '湾里区');
INSERT INTO `sys_regions` VALUES ('3505', '1827', '210106', '青云谱区');
INSERT INTO `sys_regions` VALUES ('3506', '1827', '210107', '西湖区');
INSERT INTO `sys_regions` VALUES ('3507', '1827', '210108', '东湖区');
INSERT INTO `sys_regions` VALUES ('3508', '1832', '210203', '珠山区');
INSERT INTO `sys_regions` VALUES ('3519', '1007', '130202', '四方区');
INSERT INTO `sys_regions` VALUES ('3520', '1007', '130203', '市北区');
INSERT INTO `sys_regions` VALUES ('3521', '1007', '130204', '市南区');
INSERT INTO `sys_regions` VALUES ('3522', '1022', '130401', '山亭区');
INSERT INTO `sys_regions` VALUES ('3523', '1022', '130402', '台儿庄区');
INSERT INTO `sys_regions` VALUES ('3524', '1022', '130403', '峄城区');
INSERT INTO `sys_regions` VALUES ('3525', '1022', '130404', '薛城区');
INSERT INTO `sys_regions` VALUES ('3526', '1022', '130405', '市中区');
INSERT INTO `sys_regions` VALUES ('3528', '1042', '130708', '莱山区');
INSERT INTO `sys_regions` VALUES ('3530', '1032', '130608', '坊子区');
INSERT INTO `sys_regions` VALUES ('3533', '2900', '131709', '任城区');
INSERT INTO `sys_regions` VALUES ('3535', '1112', '131602', '宁阳县');
INSERT INTO `sys_regions` VALUES ('3539', '1058', '130902', '钢城区');
INSERT INTO `sys_regions` VALUES ('3540', '1072', '131109', '罗庄区');
INSERT INTO `sys_regions` VALUES ('3542', '1060', '131010', '德城区');
INSERT INTO `sys_regions` VALUES ('3543', '1099', '131409', '牡丹区');
INSERT INTO `sys_regions` VALUES ('3544', '412', '070104', '惠济区');
INSERT INTO `sys_regions` VALUES ('3545', '412', '070105', '金水区');
INSERT INTO `sys_regions` VALUES ('3546', '412', '070106', '管城区');
INSERT INTO `sys_regions` VALUES ('3547', '412', '070107', '二七区');
INSERT INTO `sys_regions` VALUES ('3548', '412', '070108', '中原区');
INSERT INTO `sys_regions` VALUES ('3555', '427', '070309', '吉利区');
INSERT INTO `sys_regions` VALUES ('3556', '427', '070310', '涧西区');
INSERT INTO `sys_regions` VALUES ('3557', '427', '070311', '瀍河区');
INSERT INTO `sys_regions` VALUES ('3558', '427', '070312', '老城区');
INSERT INTO `sys_regions` VALUES ('3559', '427', '070313', '西工区');
INSERT INTO `sys_regions` VALUES ('3560', '438', '070407', '石龙区');
INSERT INTO `sys_regions` VALUES ('3566', '446', '070508', '解放区');
INSERT INTO `sys_regions` VALUES ('3567', '454', '070604', '山城区');
INSERT INTO `sys_regions` VALUES ('3570', '458', '070709', '凤泉区');
INSERT INTO `sys_regions` VALUES ('3576', '489', '071105', '源汇区');
INSERT INTO `sys_regions` VALUES ('3582', '1381', '170103', '江汉区');
INSERT INTO `sys_regions` VALUES ('3583', '1381', '170104', '硚口区');
INSERT INTO `sys_regions` VALUES ('3593', '1413', '170507', '沙市区');
INSERT INTO `sys_regions` VALUES ('3594', '1421', '170609', '宜都市');
INSERT INTO `sys_regions` VALUES ('3595', '1421', '170610', '猇亭区');
INSERT INTO `sys_regions` VALUES ('3596', '1421', '170611', '点军区');
INSERT INTO `sys_regions` VALUES ('3597', '1421', '170612', '伍家岗区');
INSERT INTO `sys_regions` VALUES ('3598', '1421', '170613', '西陵区');
INSERT INTO `sys_regions` VALUES ('3599', '1477', '171204', '掇刀区');
INSERT INTO `sys_regions` VALUES ('3600', '1477', '171205', '东宝区');
INSERT INTO `sys_regions` VALUES ('3601', '1475', '171101', '梁子湖区');
INSERT INTO `sys_regions` VALUES ('3602', '1475', '171102', '华容区');
INSERT INTO `sys_regions` VALUES ('3606', '1482', '180102', '芙蓉区');
INSERT INTO `sys_regions` VALUES ('3619', '1522', '180607', '君山区');
INSERT INTO `sys_regions` VALUES ('3620', '1522', '180608', '云溪区');
INSERT INTO `sys_regions` VALUES ('3622', '1540', '180804', '永定区');
INSERT INTO `sys_regions` VALUES ('3626', '1574', '181211', '中方县');
INSERT INTO `sys_regions` VALUES ('3633', '1601', '190101', '天河区');
INSERT INTO `sys_regions` VALUES ('3634', '1601', '190102', '海珠区');
INSERT INTO `sys_regions` VALUES ('3635', '1601', '190103', '荔湾区');
INSERT INTO `sys_regions` VALUES ('3637', '1601', '190104', '越秀区');
INSERT INTO `sys_regions` VALUES ('3638', '1607', '190202', '罗湖区');
INSERT INTO `sys_regions` VALUES ('3639', '1607', '190203', '福田区');
INSERT INTO `sys_regions` VALUES ('3643', '1617', '190509', '武江区');
INSERT INTO `sys_regions` VALUES ('3644', '1617', '190510', '浈江区');
INSERT INTO `sys_regions` VALUES ('3646', '1677', '191504', '坡头区');
INSERT INTO `sys_regions` VALUES ('3650', '1715', '200104', '横县');
INSERT INTO `sys_regions` VALUES ('3651', '1715', '200105', '上林县');
INSERT INTO `sys_regions` VALUES ('3652', '1715', '200106', '隆安县');
INSERT INTO `sys_regions` VALUES ('3653', '1715', '200107', '马山县');
INSERT INTO `sys_regions` VALUES ('3659', '1720', '200204', '融安县');
INSERT INTO `sys_regions` VALUES ('3660', '1720', '200205', '三江县');
INSERT INTO `sys_regions` VALUES ('3661', '1720', '200206', '融水县');
INSERT INTO `sys_regions` VALUES ('3666', '1726', '200312', '恭城县');
INSERT INTO `sys_regions` VALUES ('3670', '1726', '200313', '象山区');
INSERT INTO `sys_regions` VALUES ('3678', '1806', '201112', '凌云县');
INSERT INTO `sys_regions` VALUES ('3679', '1818', '201210', '都安县');
INSERT INTO `sys_regions` VALUES ('3680', '1818', '201211', '金城江区');
INSERT INTO `sys_regions` VALUES ('3681', '3168', '201402', '凭祥市');
INSERT INTO `sys_regions` VALUES ('3682', '3168', '201403', '扶绥县');
INSERT INTO `sys_regions` VALUES ('3683', '3168', '201404', '大新县');
INSERT INTO `sys_regions` VALUES ('3684', '3168', '201405', '天等县');
INSERT INTO `sys_regions` VALUES ('3685', '3168', '201406', '宁明县');
INSERT INTO `sys_regions` VALUES ('3686', '3168', '201407', '龙州县');
INSERT INTO `sys_regions` VALUES ('3690', '23', '2306', '三亚市');
INSERT INTO `sys_regions` VALUES ('3693', '3690', '230601', '海棠湾镇');
INSERT INTO `sys_regions` VALUES ('3694', '3690', '230602', '吉阳镇');
INSERT INTO `sys_regions` VALUES ('3695', '3690', '230603', '凤凰镇');
INSERT INTO `sys_regions` VALUES ('3696', '3690', '230604', '天涯镇');
INSERT INTO `sys_regions` VALUES ('3697', '3690', '230605', '育才镇');
INSERT INTO `sys_regions` VALUES ('3698', '23', '2307', '文昌市');
INSERT INTO `sys_regions` VALUES ('3699', '23', '2308', '五指山市');
INSERT INTO `sys_regions` VALUES ('3701', '23', '2309', '临高县');
INSERT INTO `sys_regions` VALUES ('3702', '23', '2310', '澄迈县');
INSERT INTO `sys_regions` VALUES ('3703', '23', '2311', '定安县');
INSERT INTO `sys_regions` VALUES ('3704', '23', '2312', '屯昌县');
INSERT INTO `sys_regions` VALUES ('3705', '23', '2313', '昌江县');
INSERT INTO `sys_regions` VALUES ('3706', '23', '2314', '白沙县');
INSERT INTO `sys_regions` VALUES ('3707', '23', '2315', '琼中县');
INSERT INTO `sys_regions` VALUES ('3708', '23', '2316', '陵水县');
INSERT INTO `sys_regions` VALUES ('3709', '23', '2317', '保亭县');
INSERT INTO `sys_regions` VALUES ('3710', '23', '2318', '乐东县');
INSERT INTO `sys_regions` VALUES ('3711', '23', '2319', '三沙市');
INSERT INTO `sys_regions` VALUES ('3712', '3699', '230801', '通什镇');
INSERT INTO `sys_regions` VALUES ('3713', '3699', '230802', '南圣镇');
INSERT INTO `sys_regions` VALUES ('3714', '3699', '230803', '毛阳镇');
INSERT INTO `sys_regions` VALUES ('3715', '3699', '230804', '番阳镇');
INSERT INTO `sys_regions` VALUES ('3716', '3699', '230805', '畅好乡');
INSERT INTO `sys_regions` VALUES ('3717', '3699', '230806', '毛道乡');
INSERT INTO `sys_regions` VALUES ('3719', '3699', '230807', '水满乡');
INSERT INTO `sys_regions` VALUES ('3720', '3115', '230301', '嘉积镇');
INSERT INTO `sys_regions` VALUES ('3721', '3115', '230302', '万泉镇');
INSERT INTO `sys_regions` VALUES ('3722', '3115', '230303', '石壁镇');
INSERT INTO `sys_regions` VALUES ('3723', '3115', '230304', '中原镇');
INSERT INTO `sys_regions` VALUES ('3724', '3115', '230305', '博鳌镇');
INSERT INTO `sys_regions` VALUES ('3725', '3115', '230306', '阳江镇');
INSERT INTO `sys_regions` VALUES ('3727', '3115', '230307', '龙江镇');
INSERT INTO `sys_regions` VALUES ('3728', '3115', '230308', '潭门镇');
INSERT INTO `sys_regions` VALUES ('3729', '3115', '230309', '塔洋镇');
INSERT INTO `sys_regions` VALUES ('3730', '3115', '230310', '长坡镇');
INSERT INTO `sys_regions` VALUES ('3731', '3115', '230311', '大路镇');
INSERT INTO `sys_regions` VALUES ('3732', '3115', '230312', '会山镇');
INSERT INTO `sys_regions` VALUES ('3733', '3034', '230202', '和庆镇');
INSERT INTO `sys_regions` VALUES ('3734', '3034', '230203', '南丰镇');
INSERT INTO `sys_regions` VALUES ('3735', '3034', '230204', '大成镇');
INSERT INTO `sys_regions` VALUES ('3736', '3034', '230205', '雅星镇');
INSERT INTO `sys_regions` VALUES ('3737', '3034', '230206', '兰洋镇');
INSERT INTO `sys_regions` VALUES ('3738', '3034', '230207', '光村镇');
INSERT INTO `sys_regions` VALUES ('3739', '3034', '230208', '木棠镇');
INSERT INTO `sys_regions` VALUES ('3740', '3034', '230209', '海头镇');
INSERT INTO `sys_regions` VALUES ('3741', '3034', '230210', '峨蔓镇');
INSERT INTO `sys_regions` VALUES ('3742', '988', '121309', '高新区');
INSERT INTO `sys_regions` VALUES ('3743', '1657', '191111', '沙溪镇');
INSERT INTO `sys_regions` VALUES ('3744', '3034', '230211', '三都镇');
INSERT INTO `sys_regions` VALUES ('3745', '3034', '230212', '王五镇');
INSERT INTO `sys_regions` VALUES ('3746', '3034', '230213', '白马井镇');
INSERT INTO `sys_regions` VALUES ('3747', '3034', '230214', '中和镇');
INSERT INTO `sys_regions` VALUES ('3748', '3034', '230215', '排浦镇');
INSERT INTO `sys_regions` VALUES ('3749', '3034', '230216', '东成镇');
INSERT INTO `sys_regions` VALUES ('3750', '3034', '230217', '新州镇');
INSERT INTO `sys_regions` VALUES ('3751', '3034', '230218', '洋浦经济开发区');
INSERT INTO `sys_regions` VALUES ('3752', '3698', '230701', '文城镇');
INSERT INTO `sys_regions` VALUES ('3753', '3698', '230702', '重兴镇');
INSERT INTO `sys_regions` VALUES ('3754', '3698', '230703', '蓬莱镇');
INSERT INTO `sys_regions` VALUES ('3755', '3698', '230704', '会文镇');
INSERT INTO `sys_regions` VALUES ('3756', '3698', '230705', '东路镇');
INSERT INTO `sys_regions` VALUES ('3757', '3698', '230706', '潭牛镇');
INSERT INTO `sys_regions` VALUES ('3758', '3698', '230707', '东阁镇');
INSERT INTO `sys_regions` VALUES ('3759', '3698', '230708', '文教镇');
INSERT INTO `sys_regions` VALUES ('3760', '3698', '230709', '东郊镇');
INSERT INTO `sys_regions` VALUES ('3761', '3698', '230710', '龙楼镇');
INSERT INTO `sys_regions` VALUES ('3762', '3698', '230711', '昌洒镇');
INSERT INTO `sys_regions` VALUES ('3763', '3698', '230712', '翁田镇');
INSERT INTO `sys_regions` VALUES ('3764', '3698', '230713', '抱罗镇');
INSERT INTO `sys_regions` VALUES ('3765', '3698', '230714', '冯坡镇');
INSERT INTO `sys_regions` VALUES ('3766', '3698', '230715', '锦山镇');
INSERT INTO `sys_regions` VALUES ('3767', '3698', '230716', '铺前镇');
INSERT INTO `sys_regions` VALUES ('3768', '3137', '230401', '万城镇');
INSERT INTO `sys_regions` VALUES ('3769', '3137', '230402', '龙滚镇');
INSERT INTO `sys_regions` VALUES ('3770', '3137', '230403', '和乐镇');
INSERT INTO `sys_regions` VALUES ('3771', '3137', '230404', '后安镇');
INSERT INTO `sys_regions` VALUES ('3772', '3137', '230405', '大茂镇');
INSERT INTO `sys_regions` VALUES ('3773', '3137', '230406', '东澳镇');
INSERT INTO `sys_regions` VALUES ('3774', '3137', '230407', '礼纪镇');
INSERT INTO `sys_regions` VALUES ('3775', '3137', '230408', '长丰镇');
INSERT INTO `sys_regions` VALUES ('3776', '3137', '230409', '山根镇');
INSERT INTO `sys_regions` VALUES ('3777', '3137', '230410', '北大镇');
INSERT INTO `sys_regions` VALUES ('3778', '3137', '230411', '南桥镇');
INSERT INTO `sys_regions` VALUES ('3779', '3137', '230412', '三更罗镇');
INSERT INTO `sys_regions` VALUES ('3780', '3173', '230501', '八所镇');
INSERT INTO `sys_regions` VALUES ('3781', '3173', '230502', '东河镇');
INSERT INTO `sys_regions` VALUES ('3782', '3173', '230503', '大田镇');
INSERT INTO `sys_regions` VALUES ('3783', '3173', '230504', '感城镇');
INSERT INTO `sys_regions` VALUES ('3784', '3173', '230505', '板桥镇');
INSERT INTO `sys_regions` VALUES ('3785', '3173', '230506', '三家镇');
INSERT INTO `sys_regions` VALUES ('3786', '3173', '230507', '四更镇');
INSERT INTO `sys_regions` VALUES ('3787', '3173', '230508', '新龙镇');
INSERT INTO `sys_regions` VALUES ('3788', '3173', '230509', '天安乡');
INSERT INTO `sys_regions` VALUES ('3789', '3173', '230510', '江边乡');
INSERT INTO `sys_regions` VALUES ('3790', '3701', '230901', '临城镇');
INSERT INTO `sys_regions` VALUES ('3791', '3701', '230902', '波莲镇');
INSERT INTO `sys_regions` VALUES ('3792', '3701', '230903', '东英镇');
INSERT INTO `sys_regions` VALUES ('3793', '3701', '230904', '博厚镇');
INSERT INTO `sys_regions` VALUES ('3794', '3701', '230905', '皇桐镇');
INSERT INTO `sys_regions` VALUES ('3795', '3701', '230906', '多文镇');
INSERT INTO `sys_regions` VALUES ('3796', '3701', '230907', '和舍镇');
INSERT INTO `sys_regions` VALUES ('3797', '3701', '230908', '南宝镇');
INSERT INTO `sys_regions` VALUES ('3798', '3701', '230909', '新盈镇');
INSERT INTO `sys_regions` VALUES ('3799', '3701', '230910', '调楼镇');
INSERT INTO `sys_regions` VALUES ('3800', '3701', '230911', '加来镇');
INSERT INTO `sys_regions` VALUES ('3801', '3702', '231001', '金江镇');
INSERT INTO `sys_regions` VALUES ('3802', '3702', '231002', '老城镇');
INSERT INTO `sys_regions` VALUES ('3803', '3702', '231003', '瑞溪镇');
INSERT INTO `sys_regions` VALUES ('3804', '3702', '231004', '永发镇');
INSERT INTO `sys_regions` VALUES ('3805', '3702', '231005', '加乐镇');
INSERT INTO `sys_regions` VALUES ('3806', '3702', '231006', '文儒镇');
INSERT INTO `sys_regions` VALUES ('3807', '3702', '231007', '中兴镇');
INSERT INTO `sys_regions` VALUES ('3808', '3702', '231008', '仁兴镇');
INSERT INTO `sys_regions` VALUES ('3809', '3702', '231009', '福山镇');
INSERT INTO `sys_regions` VALUES ('3810', '3702', '231010', '桥头镇');
INSERT INTO `sys_regions` VALUES ('3811', '3703', '231101', '定城镇');
INSERT INTO `sys_regions` VALUES ('3812', '3703', '231102', '新竹镇');
INSERT INTO `sys_regions` VALUES ('3813', '3703', '231103', '龙湖镇');
INSERT INTO `sys_regions` VALUES ('3814', '3703', '231104', '雷鸣镇');
INSERT INTO `sys_regions` VALUES ('3815', '3703', '231105', '龙门镇');
INSERT INTO `sys_regions` VALUES ('3816', '3703', '231106', '龙河镇');
INSERT INTO `sys_regions` VALUES ('3817', '3703', '231107', '岭口镇');
INSERT INTO `sys_regions` VALUES ('3818', '3703', '231108', '翰林镇');
INSERT INTO `sys_regions` VALUES ('3819', '3703', '231109', '富文镇');
INSERT INTO `sys_regions` VALUES ('3820', '3704', '231201', '屯城镇');
INSERT INTO `sys_regions` VALUES ('3821', '3704', '231202', '新兴镇');
INSERT INTO `sys_regions` VALUES ('3822', '3704', '231203', '枫木镇');
INSERT INTO `sys_regions` VALUES ('3823', '3704', '231204', '乌坡镇');
INSERT INTO `sys_regions` VALUES ('3824', '3704', '231205', '南吕镇');
INSERT INTO `sys_regions` VALUES ('3825', '3704', '231206', '南坤镇');
INSERT INTO `sys_regions` VALUES ('3826', '3704', '231207', '坡心镇');
INSERT INTO `sys_regions` VALUES ('3827', '3704', '231208', '西昌镇');
INSERT INTO `sys_regions` VALUES ('3828', '3705', '231301', '石碌镇');
INSERT INTO `sys_regions` VALUES ('3829', '3705', '231302', '叉河镇');
INSERT INTO `sys_regions` VALUES ('3830', '3705', '231303', '十月田镇');
INSERT INTO `sys_regions` VALUES ('3831', '3705', '231304', '乌烈镇');
INSERT INTO `sys_regions` VALUES ('3832', '3705', '231305', '昌化镇');
INSERT INTO `sys_regions` VALUES ('3833', '3705', '231306', '海尾镇');
INSERT INTO `sys_regions` VALUES ('3834', '3706', '231401', '牙叉镇');
INSERT INTO `sys_regions` VALUES ('3835', '3706', '231402', '七坊镇');
INSERT INTO `sys_regions` VALUES ('3836', '3706', '231403', '邦溪镇');
INSERT INTO `sys_regions` VALUES ('3837', '3706', '231404', '打安镇');
INSERT INTO `sys_regions` VALUES ('3838', '3706', '231405', '细水乡');
INSERT INTO `sys_regions` VALUES ('3839', '3706', '231406', '元门乡');
INSERT INTO `sys_regions` VALUES ('3840', '3706', '231407', '南开乡');
INSERT INTO `sys_regions` VALUES ('3841', '3706', '231408', '阜龙乡');
INSERT INTO `sys_regions` VALUES ('3842', '3706', '231409', '青松乡');
INSERT INTO `sys_regions` VALUES ('3843', '3706', '231410', '金波乡');
INSERT INTO `sys_regions` VALUES ('3844', '3706', '231411', '荣邦乡');
INSERT INTO `sys_regions` VALUES ('3845', '3710', '231801', '抱由镇');
INSERT INTO `sys_regions` VALUES ('3846', '3710', '231802', '万冲镇');
INSERT INTO `sys_regions` VALUES ('3847', '3710', '231803', '大安镇');
INSERT INTO `sys_regions` VALUES ('3849', '3710', '231804', '志仲镇');
INSERT INTO `sys_regions` VALUES ('3851', '3710', '231805', '千家镇');
INSERT INTO `sys_regions` VALUES ('3852', '3710', '231806', '九所镇');
INSERT INTO `sys_regions` VALUES ('3853', '3710', '231807', '利国镇');
INSERT INTO `sys_regions` VALUES ('3854', '3710', '231808', '黄流镇');
INSERT INTO `sys_regions` VALUES ('3855', '3710', '231809', '佛罗镇');
INSERT INTO `sys_regions` VALUES ('3856', '3710', '231810', '尖峰镇');
INSERT INTO `sys_regions` VALUES ('3857', '3710', '231811', '莺歌海镇');
INSERT INTO `sys_regions` VALUES ('3858', '3708', '231601', '椰林镇');
INSERT INTO `sys_regions` VALUES ('3859', '3708', '231602', '光坡镇');
INSERT INTO `sys_regions` VALUES ('3860', '3708', '231603', '三才镇');
INSERT INTO `sys_regions` VALUES ('3861', '3708', '231604', '英州镇');
INSERT INTO `sys_regions` VALUES ('3862', '3708', '231605', '隆广镇');
INSERT INTO `sys_regions` VALUES ('3863', '3708', '231606', '文罗镇');
INSERT INTO `sys_regions` VALUES ('3864', '3708', '231607', '本号镇');
INSERT INTO `sys_regions` VALUES ('3865', '3708', '231608', '新村镇');
INSERT INTO `sys_regions` VALUES ('3866', '3708', '231609', '黎安镇');
INSERT INTO `sys_regions` VALUES ('3867', '3708', '231610', '提蒙乡');
INSERT INTO `sys_regions` VALUES ('3868', '3708', '231611', '群英乡');
INSERT INTO `sys_regions` VALUES ('3869', '3709', '231701', '保城镇');
INSERT INTO `sys_regions` VALUES ('3870', '3709', '231702', '什玲镇');
INSERT INTO `sys_regions` VALUES ('3871', '3709', '231703', '加茂镇');
INSERT INTO `sys_regions` VALUES ('3872', '3709', '231704', '响水镇');
INSERT INTO `sys_regions` VALUES ('3873', '3709', '231705', '新政镇');
INSERT INTO `sys_regions` VALUES ('3874', '3709', '231706', '三道镇');
INSERT INTO `sys_regions` VALUES ('3875', '3709', '231707', '六弓乡');
INSERT INTO `sys_regions` VALUES ('3876', '3709', '231708', '南林乡');
INSERT INTO `sys_regions` VALUES ('3877', '3709', '231709', '毛感乡');
INSERT INTO `sys_regions` VALUES ('3878', '3707', '231501', '营根镇');
INSERT INTO `sys_regions` VALUES ('3879', '3707', '231502', '湾岭镇');
INSERT INTO `sys_regions` VALUES ('3880', '3707', '231503', '黎母山镇');
INSERT INTO `sys_regions` VALUES ('3881', '3707', '231504', '和平镇');
INSERT INTO `sys_regions` VALUES ('3882', '3707', '231505', '长征镇');
INSERT INTO `sys_regions` VALUES ('3883', '3707', '231506', '红毛镇');
INSERT INTO `sys_regions` VALUES ('3884', '3707', '231507', '中平镇');
INSERT INTO `sys_regions` VALUES ('3885', '3707', '231508', '上安乡');
INSERT INTO `sys_regions` VALUES ('3886', '3707', '231509', '什运乡');
INSERT INTO `sys_regions` VALUES ('3887', '3711', '231901', '西沙群岛');
INSERT INTO `sys_regions` VALUES ('3888', '3711', '231902', '南沙群岛');
INSERT INTO `sys_regions` VALUES ('3895', '1946', '220204', '沿滩区');
INSERT INTO `sys_regions` VALUES ('3896', '1950', '220304', '西区');
INSERT INTO `sys_regions` VALUES ('3898', '1954', '220405', '纳溪区');
INSERT INTO `sys_regions` VALUES ('3901', '1977', '220705', '昭化区');
INSERT INTO `sys_regions` VALUES ('3902', '1977', '220706', '朝天区');
INSERT INTO `sys_regions` VALUES ('3904', '2042', '221504', '巴州区');
INSERT INTO `sys_regions` VALUES ('3905', '2065', '221803', '雁江区');
INSERT INTO `sys_regions` VALUES ('3906', '2144', '240106', '南明区');
INSERT INTO `sys_regions` VALUES ('3909', '2144', '240107', '白云区');
INSERT INTO `sys_regions` VALUES ('3912', '2235', '250111', '五华区');
INSERT INTO `sys_regions` VALUES ('3913', '2235', '250112', '官渡区');
INSERT INTO `sys_regions` VALUES ('3914', '2235', '250113', '西山区');
INSERT INTO `sys_regions` VALUES ('3915', '2291', '250607', '双江县');
INSERT INTO `sys_regions` VALUES ('3916', '2291', '250608', '沧源县');
INSERT INTO `sys_regions` VALUES ('3917', '2336', '251209', '姚安县');
INSERT INTO `sys_regions` VALUES ('3918', '2951', '260103', '当雄县');
INSERT INTO `sys_regions` VALUES ('3919', '2951', '260104', '尼木县');
INSERT INTO `sys_regions` VALUES ('3920', '2951', '260105', '曲水县');
INSERT INTO `sys_regions` VALUES ('3921', '2951', '260106', '堆龙德庆县');
INSERT INTO `sys_regions` VALUES ('3922', '2951', '260107', '达孜县');
INSERT INTO `sys_regions` VALUES ('3923', '2951', '260108', '墨竹工卡县');
INSERT INTO `sys_regions` VALUES ('3924', '3138', '260402', '江达县');
INSERT INTO `sys_regions` VALUES ('3925', '3138', '260403', '贡觉县');
INSERT INTO `sys_regions` VALUES ('3926', '3138', '260404', '类乌齐县');
INSERT INTO `sys_regions` VALUES ('3927', '3138', '260405', '丁青县');
INSERT INTO `sys_regions` VALUES ('3928', '3138', '260406', '察雅县');
INSERT INTO `sys_regions` VALUES ('3929', '3138', '260407', '八宿县');
INSERT INTO `sys_regions` VALUES ('3930', '3138', '260408', '左贡县');
INSERT INTO `sys_regions` VALUES ('3931', '3138', '260409', '芒康县');
INSERT INTO `sys_regions` VALUES ('3932', '3138', '260410', '洛隆县');
INSERT INTO `sys_regions` VALUES ('3933', '3138', '260411', '边坝县');
INSERT INTO `sys_regions` VALUES ('3934', '3129', '260302', '扎囊县');
INSERT INTO `sys_regions` VALUES ('3935', '3129', '260303', '乃东县');
INSERT INTO `sys_regions` VALUES ('3936', '3129', '260304', '桑日县');
INSERT INTO `sys_regions` VALUES ('3937', '3129', '260305', '琼结县');
INSERT INTO `sys_regions` VALUES ('3938', '3129', '260306', '曲松县');
INSERT INTO `sys_regions` VALUES ('3939', '3129', '260307', '措美县');
INSERT INTO `sys_regions` VALUES ('3940', '3129', '260308', '洛扎县');
INSERT INTO `sys_regions` VALUES ('3941', '3129', '260309', '加查县');
INSERT INTO `sys_regions` VALUES ('3942', '3129', '260310', '隆子县');
INSERT INTO `sys_regions` VALUES ('3943', '3129', '260311', '错那县');
INSERT INTO `sys_regions` VALUES ('3944', '3129', '260312', '浪卡子县');
INSERT INTO `sys_regions` VALUES ('3945', '3144', '260503', '日喀则市');
INSERT INTO `sys_regions` VALUES ('3946', '3144', '260504', '南木林县');
INSERT INTO `sys_regions` VALUES ('3947', '3144', '260505', '江孜县');
INSERT INTO `sys_regions` VALUES ('3948', '3144', '260506', '定日县');
INSERT INTO `sys_regions` VALUES ('3949', '3144', '260507', '萨迦县　');
INSERT INTO `sys_regions` VALUES ('3950', '3144', '260508', '拉孜县');
INSERT INTO `sys_regions` VALUES ('3951', '3144', '260509', '谢通门县');
INSERT INTO `sys_regions` VALUES ('3952', '3144', '260510', '白朗县');
INSERT INTO `sys_regions` VALUES ('3953', '3144', '260511', '仁布县');
INSERT INTO `sys_regions` VALUES ('3954', '3144', '260512', '康马县');
INSERT INTO `sys_regions` VALUES ('3955', '3144', '260513', '定结县');
INSERT INTO `sys_regions` VALUES ('3956', '3144', '260514', '仲巴县');
INSERT INTO `sys_regions` VALUES ('3957', '3144', '260515', '亚东县');
INSERT INTO `sys_regions` VALUES ('3958', '3144', '260516', '吉隆县');
INSERT INTO `sys_regions` VALUES ('3959', '3144', '260517', '萨嘎县');
INSERT INTO `sys_regions` VALUES ('3960', '3144', '260518', '岗巴县');
INSERT INTO `sys_regions` VALUES ('3961', '3107', '260202', '那曲县');
INSERT INTO `sys_regions` VALUES ('3962', '3107', '260203', '嘉黎县');
INSERT INTO `sys_regions` VALUES ('3963', '3107', '260204', '比如县');
INSERT INTO `sys_regions` VALUES ('3964', '3107', '260205', '聂荣县');
INSERT INTO `sys_regions` VALUES ('3965', '3107', '260206', '安多县');
INSERT INTO `sys_regions` VALUES ('3966', '3107', '260207', '申扎县');
INSERT INTO `sys_regions` VALUES ('3967', '3107', '260208', '班戈县');
INSERT INTO `sys_regions` VALUES ('3968', '3107', '260209', '巴青县');
INSERT INTO `sys_regions` VALUES ('3969', '3107', '260210', '尼玛县');
INSERT INTO `sys_regions` VALUES ('3970', '26', '2606', '阿里地区');
INSERT INTO `sys_regions` VALUES ('3971', '26', '2607', '林芝地区');
INSERT INTO `sys_regions` VALUES ('3972', '3970', '260601', '噶尔县');
INSERT INTO `sys_regions` VALUES ('3973', '3970', '260602', '普兰县');
INSERT INTO `sys_regions` VALUES ('3974', '3970', '260603', '札达县　');
INSERT INTO `sys_regions` VALUES ('3975', '3970', '260604', '日土县');
INSERT INTO `sys_regions` VALUES ('3976', '3970', '260605', '革吉县');
INSERT INTO `sys_regions` VALUES ('3977', '3970', '260606', '改则县');
INSERT INTO `sys_regions` VALUES ('3978', '3970', '260607', '措勤县');
INSERT INTO `sys_regions` VALUES ('3979', '3971', '260701', '林芝县');
INSERT INTO `sys_regions` VALUES ('3980', '3971', '260702', '工布江达县');
INSERT INTO `sys_regions` VALUES ('3981', '3971', '260703', '米林县');
INSERT INTO `sys_regions` VALUES ('3982', '3971', '260704', '墨脱县');
INSERT INTO `sys_regions` VALUES ('3983', '3971', '260705', '波密县');
INSERT INTO `sys_regions` VALUES ('3984', '3971', '260706', '察隅县');
INSERT INTO `sys_regions` VALUES ('3985', '3971', '260707', '朗县');
INSERT INTO `sys_regions` VALUES ('3989', '2386', '270204', '耀州区');
INSERT INTO `sys_regions` VALUES ('3990', '2390', '270311', '金台区');
INSERT INTO `sys_regions` VALUES ('3993', '2476', '271010', '汉滨区');
INSERT INTO `sys_regions` VALUES ('3995', '2487', '280104', '西固区');
INSERT INTO `sys_regions` VALUES ('3997', '2487', '280105', '红古区');
INSERT INTO `sys_regions` VALUES ('3998', '2518', '280607', '静宁县');
INSERT INTO `sys_regions` VALUES ('3999', '2556', '281106', '瓜州县');
INSERT INTO `sys_regions` VALUES ('4000', '2556', '281107', '肃州区');
INSERT INTO `sys_regions` VALUES ('4001', '2525', '280708', '庆城县');
INSERT INTO `sys_regions` VALUES ('4002', '3080', '281402', '安定区');
INSERT INTO `sys_regions` VALUES ('4003', '3080', '281403', '通渭县');
INSERT INTO `sys_regions` VALUES ('4004', '3080', '281404', '临洮县');
INSERT INTO `sys_regions` VALUES ('4005', '3080', '281405', '漳县');
INSERT INTO `sys_regions` VALUES ('4006', '3080', '281406', '渭源县');
INSERT INTO `sys_regions` VALUES ('4007', '3080', '281407', '陇西县');
INSERT INTO `sys_regions` VALUES ('4008', '2573', '281308', '广河县');
INSERT INTO `sys_regions` VALUES ('4012', '2603', '290501', '共和县');
INSERT INTO `sys_regions` VALUES ('4013', '2603', '290502', '同德县');
INSERT INTO `sys_regions` VALUES ('4014', '2603', '290503', '贵德县');
INSERT INTO `sys_regions` VALUES ('4015', '2603', '290504', '兴海县');
INSERT INTO `sys_regions` VALUES ('4016', '2603', '290505', '贵南县');
INSERT INTO `sys_regions` VALUES ('4020', '3071', '300503', '沙坡头区');
INSERT INTO `sys_regions` VALUES ('4024', '2652', '310102', '头屯河区');
INSERT INTO `sys_regions` VALUES ('4025', '2652', '310103', '达坂城区');
INSERT INTO `sys_regions` VALUES ('4026', '2652', '310104', '米东区');
INSERT INTO `sys_regions` VALUES ('4027', '2654', '310204', '乌尔禾区');
INSERT INTO `sys_regions` VALUES ('4028', '2727', '311309', '奎屯市');
INSERT INTO `sys_regions` VALUES ('4029', '984', '121206', '新区');
INSERT INTO `sys_regions` VALUES ('4039', '1827', '210109', '高新区');
INSERT INTO `sys_regions` VALUES ('4042', '1657', '191112', '五桂山镇');
INSERT INTO `sys_regions` VALUES ('4043', '1081', '131207', '高唐县');
INSERT INTO `sys_regions` VALUES ('4046', '224', '050515', '宣化区');
INSERT INTO `sys_regions` VALUES ('4076', '1657', '191113', '阜沙镇');
INSERT INTO `sys_regions` VALUES ('4078', '1413', '170508', '荆州区');
INSERT INTO `sys_regions` VALUES ('4080', '1657', '191114', '东升镇');
INSERT INTO `sys_regions` VALUES ('4081', '2454', '270811', '靖边县');
INSERT INTO `sys_regions` VALUES ('4087', '1655', '191015', '谢岗镇');
INSERT INTO `sys_regions` VALUES ('4093', '248', '050705', '抚宁县');
INSERT INTO `sys_regions` VALUES ('4097', '274', '051009', '开发区');
INSERT INTO `sys_regions` VALUES ('4101', '1827', '210110', '昌北区');
INSERT INTO `sys_regions` VALUES ('4102', '1657', '191115', '板芙镇');
INSERT INTO `sys_regions` VALUES ('4108', '25', '2516', '迪庆州');
INSERT INTO `sys_regions` VALUES ('4110', '31', '3116', '五家渠市');
INSERT INTO `sys_regions` VALUES ('4113', '1108', '131503', '岚山区');
INSERT INTO `sys_regions` VALUES ('4114', '793', '101304', '加格达奇区');
INSERT INTO `sys_regions` VALUES ('4115', '793', '101305', '松岭区');
INSERT INTO `sys_regions` VALUES ('4116', '793', '101306', '呼中区');
INSERT INTO `sys_regions` VALUES ('4122', '4110', '311601', '五家渠市');
INSERT INTO `sys_regions` VALUES ('4127', '1657', '191116', '神湾镇');
INSERT INTO `sys_regions` VALUES ('4130', '1250', '150504', '南浔区');
INSERT INTO `sys_regions` VALUES ('4134', '2800', '010206', '西三旗');
INSERT INTO `sys_regions` VALUES ('4135', '2901', '011602', '六环以内');
INSERT INTO `sys_regions` VALUES ('4136', '2901', '011603', '城区');
INSERT INTO `sys_regions` VALUES ('4137', '72', '010105', '管庄');
INSERT INTO `sys_regions` VALUES ('4139', '72', '010106', '北苑');
INSERT INTO `sys_regions` VALUES ('4141', '1657', '191117', '港口镇');
INSERT INTO `sys_regions` VALUES ('4147', '1655', '191016', '松山湖');
INSERT INTO `sys_regions` VALUES ('4148', '757', '100810', '绥芬河市');
INSERT INTO `sys_regions` VALUES ('4150', '427', '070314', '嵩县');
INSERT INTO `sys_regions` VALUES ('4158', '142', '050115', '平山县');
INSERT INTO `sys_regions` VALUES ('4161', '1845', '210511', '共青城市');
INSERT INTO `sys_regions` VALUES ('4164', '4', '0421', '城口县');
INSERT INTO `sys_regions` VALUES ('4172', '1127', '140506', '弋江区');
INSERT INTO `sys_regions` VALUES ('4173', '1116', '140207', '经济技术开发区');
INSERT INTO `sys_regions` VALUES ('4182', '3690', '230606', '崖城镇');
INSERT INTO `sys_regions` VALUES ('4187', '2806', '010802', '石景山城区');
INSERT INTO `sys_regions` VALUES ('4188', '2806', '010803', '八大处科技园区');
INSERT INTO `sys_regions` VALUES ('4190', '1657', '191118', '大涌镇');
INSERT INTO `sys_regions` VALUES ('4192', '1116', '140208', '高新技术开发区');
INSERT INTO `sys_regions` VALUES ('4194', '2810', '011201', '四环至五环之间');
INSERT INTO `sys_regions` VALUES ('4196', '1108', '131504', '新市区');
INSERT INTO `sys_regions` VALUES ('4205', '2810', '011202', '六环以外');
INSERT INTO `sys_regions` VALUES ('4209', '2800', '010207', '西二旗');
INSERT INTO `sys_regions` VALUES ('4211', '72', '010107', '定福庄');
INSERT INTO `sys_regions` VALUES ('4214', '3034', '230219', '富克镇');
INSERT INTO `sys_regions` VALUES ('4223', '911', '120206', '金山桥开发区');
INSERT INTO `sys_regions` VALUES ('4224', '911', '120207', '铜山经济技术开发区');
INSERT INTO `sys_regions` VALUES ('4228', '911', '120208', '八段工业园区');
INSERT INTO `sys_regions` VALUES ('4238', '1705', '192002', '枫溪区');
INSERT INTO `sys_regions` VALUES ('4248', '919', '120305', '连云区');
INSERT INTO `sys_regions` VALUES ('4253', '1158', '150107', '高新科技开发区');
INSERT INTO `sys_regions` VALUES ('4255', '1655', '191017', '莞城区');
INSERT INTO `sys_regions` VALUES ('4256', '1655', '191018', '南城区');
INSERT INTO `sys_regions` VALUES ('4277', '1000', '130105', '高新区');
INSERT INTO `sys_regions` VALUES ('4284', '1930', '220101', '高新西区');
INSERT INTO `sys_regions` VALUES ('4285', '1213', '150213', '下沙区');
INSERT INTO `sys_regions` VALUES ('4298', '50951', '043301', '内环以内');
INSERT INTO `sys_regions` VALUES ('4305', '925', '120405', '经济开发区');
INSERT INTO `sys_regions` VALUES ('4337', '412', '070109', '郑东新区');
INSERT INTO `sys_regions` VALUES ('4342', '1233', '150307', '茶山高教园区');
INSERT INTO `sys_regions` VALUES ('4343', '2376', '270105', '雁塔区');
INSERT INTO `sys_regions` VALUES ('4346', '988', '121310', '太仓市');
INSERT INTO `sys_regions` VALUES ('4385', '965', '120906', '南通经济技术开发区');
INSERT INTO `sys_regions` VALUES ('4424', '1381', '170105', '武汉经济技术开发区');
INSERT INTO `sys_regions` VALUES ('4429', '1243', '150405', '桐乡市');
INSERT INTO `sys_regions` VALUES ('4430', '1243', '150406', '平湖市');
INSERT INTO `sys_regions` VALUES ('4431', '1243', '150407', '嘉善县');
INSERT INTO `sys_regions` VALUES ('4457', '1726', '200314', '雁山区');
INSERT INTO `sys_regions` VALUES ('4459', '978', '121106', '武进区');
INSERT INTO `sys_regions` VALUES ('4468', '573', '080207', '中山区');
INSERT INTO `sys_regions` VALUES ('4498', '3703', '231110', '黄竹镇');
INSERT INTO `sys_regions` VALUES ('4499', '2727', '311310', '伊宁县');
INSERT INTO `sys_regions` VALUES ('4760', '1655', '191019', '长安镇');
INSERT INTO `sys_regions` VALUES ('4773', '1607', '190204', '宝安区');
INSERT INTO `sys_regions` VALUES ('4781', '1690', '191706', '端州区');
INSERT INTO `sys_regions` VALUES ('4832', '1167', '141106', '经济开发区');
INSERT INTO `sys_regions` VALUES ('4852', '1657', '191119', '火炬开发区');
INSERT INTO `sys_regions` VALUES ('4866', '1655', '191020', '寮步镇');
INSERT INTO `sys_regions` VALUES ('4871', '1655', '191021', '大岭山镇');
INSERT INTO `sys_regions` VALUES ('4886', '1655', '191022', '常平镇');
INSERT INTO `sys_regions` VALUES ('4909', '1007', '130205', '李沧区');
INSERT INTO `sys_regions` VALUES ('4910', '1655', '191023', '厚街镇');
INSERT INTO `sys_regions` VALUES ('4911', '1655', '191024', '万江区');
INSERT INTO `sys_regions` VALUES ('4912', '598', '080705', '古塔区');
INSERT INTO `sys_regions` VALUES ('4913', '598', '080706', '凌河区');
INSERT INTO `sys_regions` VALUES ('4914', '598', '080707', '太和区');
INSERT INTO `sys_regions` VALUES ('4916', '972', '121005', '镇江新区');
INSERT INTO `sys_regions` VALUES ('4932', '1655', '191025', '樟木头镇');
INSERT INTO `sys_regions` VALUES ('4960', '1121', '140307', '淮南高新技术开发区');
INSERT INTO `sys_regions` VALUES ('4961', '1983', '220805', '船山区');
INSERT INTO `sys_regions` VALUES ('4980', '1655', '191026', '大朗镇');
INSERT INTO `sys_regions` VALUES ('5457', '1655', '191027', '塘厦镇');
INSERT INTO `sys_regions` VALUES ('5473', '1655', '191028', '凤岗镇');
INSERT INTO `sys_regions` VALUES ('5484', '1709', '192103', '东山区');
INSERT INTO `sys_regions` VALUES ('5505', '1007', '130206', '黄岛区');
INSERT INTO `sys_regions` VALUES ('5864', '1709', '192104', '普宁市');
INSERT INTO `sys_regions` VALUES ('5869', '1655', '191029', '清溪镇');
INSERT INTO `sys_regions` VALUES ('5905', '1655', '191030', '横沥镇');
INSERT INTO `sys_regions` VALUES ('5909', '573', '080208', '甘井子区');
INSERT INTO `sys_regions` VALUES ('6006', '1180', '141305', '经济开发区');
INSERT INTO `sys_regions` VALUES ('6115', '2814', '011402', '城区以内');
INSERT INTO `sys_regions` VALUES ('6117', '1116', '140209', '北城新区');
INSERT INTO `sys_regions` VALUES ('6118', '1116', '140210', '滨湖新区');
INSERT INTO `sys_regions` VALUES ('6119', '1116', '140211', '政务文化新区');
INSERT INTO `sys_regions` VALUES ('6120', '1116', '140212', '新站综合开发试验区');
INSERT INTO `sys_regions` VALUES ('6501', '2810', '011203', '五环至六环之间');
INSERT INTO `sys_regions` VALUES ('6561', '573', '080209', '高新园区');
INSERT INTO `sys_regions` VALUES ('6627', '573', '080210', '大连开发区');
INSERT INTO `sys_regions` VALUES ('6628', '609', '080905', '站前区');
INSERT INTO `sys_regions` VALUES ('6641', '651', '090305', '铁东区');
INSERT INTO `sys_regions` VALUES ('6642', '651', '090306', '铁西区');
INSERT INTO `sys_regions` VALUES ('6646', '51050', '031601', '外环内');
INSERT INTO `sys_regions` VALUES ('6666', '2953', '011702', '城区');
INSERT INTO `sys_regions` VALUES ('6667', '2816', '011502', '城区');
INSERT INTO `sys_regions` VALUES ('6675', '1607', '190205', '光明新区');
INSERT INTO `sys_regions` VALUES ('6712', '782', '101210', '北林区');
INSERT INTO `sys_regions` VALUES ('6736', '1607', '190206', '坪山新区');
INSERT INTO `sys_regions` VALUES ('6737', '1607', '190207', '大鹏新区');
INSERT INTO `sys_regions` VALUES ('6790', '598', '080708', '经济技术开发区');
INSERT INTO `sys_regions` VALUES ('6822', '2744', '311508', '北屯市');
INSERT INTO `sys_regions` VALUES ('6823', '4108', '251601', '香格里拉县');
INSERT INTO `sys_regions` VALUES ('6824', '4108', '251602', '德钦县');
INSERT INTO `sys_regions` VALUES ('6825', '4108', '251603', '维西县');
INSERT INTO `sys_regions` VALUES ('6858', '8', '0814', '铁岭市');
INSERT INTO `sys_regions` VALUES ('6859', '6858', '081401', '银州区');
INSERT INTO `sys_regions` VALUES ('6860', '6858', '081402', '清河区');
INSERT INTO `sys_regions` VALUES ('6862', '6858', '081403', '开原市');
INSERT INTO `sys_regions` VALUES ('6863', '6858', '081404', '铁岭县');
INSERT INTO `sys_regions` VALUES ('6864', '6858', '081405', '西丰县');
INSERT INTO `sys_regions` VALUES ('6865', '6858', '081406', '昌图县');
INSERT INTO `sys_regions` VALUES ('6963', '1137', '140704', '博望区');
INSERT INTO `sys_regions` VALUES ('7357', '1479', '171303', '随县');
INSERT INTO `sys_regions` VALUES ('8540', '1657', '191120', '民众镇');
INSERT INTO `sys_regions` VALUES ('8545', '51049', '031501', '大港油田');
INSERT INTO `sys_regions` VALUES ('8546', '51049', '031502', '主城区内');
INSERT INTO `sys_regions` VALUES ('8547', '51049', '031503', '主城区外');
INSERT INTO `sys_regions` VALUES ('8558', '933', '120504', '沭阳县');
INSERT INTO `sys_regions` VALUES ('8559', '933', '120505', '泗阳县');
INSERT INTO `sys_regions` VALUES ('8891', '2180', '240508', '七星关区');
INSERT INTO `sys_regions` VALUES ('9756', '123', '040501', '柏梓镇');
INSERT INTO `sys_regions` VALUES ('9757', '123', '040502', '宝龙镇');
INSERT INTO `sys_regions` VALUES ('9758', '123', '040503', '崇龛镇');
INSERT INTO `sys_regions` VALUES ('9759', '123', '040504', '古溪镇');
INSERT INTO `sys_regions` VALUES ('9760', '123', '040505', '龙形镇');
INSERT INTO `sys_regions` VALUES ('9761', '123', '040506', '米心镇');
INSERT INTO `sys_regions` VALUES ('9762', '123', '040507', '群力镇');
INSERT INTO `sys_regions` VALUES ('9763', '123', '040508', '上和镇');
INSERT INTO `sys_regions` VALUES ('9764', '123', '040509', '双江镇');
INSERT INTO `sys_regions` VALUES ('9765', '123', '040510', '太安镇');
INSERT INTO `sys_regions` VALUES ('9766', '123', '040511', '塘坝镇');
INSERT INTO `sys_regions` VALUES ('9767', '123', '040512', '卧佛镇');
INSERT INTO `sys_regions` VALUES ('9768', '123', '040513', '五桂镇');
INSERT INTO `sys_regions` VALUES ('9769', '123', '040514', '小渡镇');
INSERT INTO `sys_regions` VALUES ('9770', '123', '040515', '新胜镇');
INSERT INTO `sys_regions` VALUES ('9771', '123', '040516', '玉溪镇');
INSERT INTO `sys_regions` VALUES ('9772', '123', '040517', '别口乡');
INSERT INTO `sys_regions` VALUES ('9773', '123', '040518', '田家乡');
INSERT INTO `sys_regions` VALUES ('9774', '123', '040519', '寿桥乡');
INSERT INTO `sys_regions` VALUES ('9786', '113', '040101', '白土镇');
INSERT INTO `sys_regions` VALUES ('9787', '113', '040102', '白羊镇');
INSERT INTO `sys_regions` VALUES ('9788', '113', '040103', '大周镇');
INSERT INTO `sys_regions` VALUES ('9789', '113', '040104', '弹子镇');
INSERT INTO `sys_regions` VALUES ('9790', '113', '040105', '分水镇');
INSERT INTO `sys_regions` VALUES ('9791', '113', '040106', '甘宁镇');
INSERT INTO `sys_regions` VALUES ('9792', '113', '040107', '高峰镇');
INSERT INTO `sys_regions` VALUES ('9793', '113', '040108', '高梁镇');
INSERT INTO `sys_regions` VALUES ('9794', '113', '040109', '后山镇');
INSERT INTO `sys_regions` VALUES ('9795', '113', '040110', '李河镇');
INSERT INTO `sys_regions` VALUES ('9796', '113', '040111', '龙驹镇');
INSERT INTO `sys_regions` VALUES ('9797', '113', '040112', '龙沙镇');
INSERT INTO `sys_regions` VALUES ('9798', '113', '040113', '罗田镇');
INSERT INTO `sys_regions` VALUES ('9799', '113', '040114', '孙家镇');
INSERT INTO `sys_regions` VALUES ('9800', '113', '040115', '太安镇');
INSERT INTO `sys_regions` VALUES ('9801', '113', '040116', '太龙镇');
INSERT INTO `sys_regions` VALUES ('9802', '113', '040117', '天城镇');
INSERT INTO `sys_regions` VALUES ('9803', '113', '040118', '武陵镇');
INSERT INTO `sys_regions` VALUES ('9804', '113', '040119', '响水镇');
INSERT INTO `sys_regions` VALUES ('9805', '113', '040120', '小周镇');
INSERT INTO `sys_regions` VALUES ('9806', '113', '040121', '新田镇');
INSERT INTO `sys_regions` VALUES ('9807', '113', '040122', '新乡镇');
INSERT INTO `sys_regions` VALUES ('9808', '113', '040123', '熊家镇');
INSERT INTO `sys_regions` VALUES ('9809', '113', '040124', '余家镇');
INSERT INTO `sys_regions` VALUES ('9810', '113', '040125', '长岭镇');
INSERT INTO `sys_regions` VALUES ('9811', '113', '040126', '长坪镇');
INSERT INTO `sys_regions` VALUES ('9812', '113', '040127', '长滩镇');
INSERT INTO `sys_regions` VALUES ('9813', '113', '040128', '走马镇');
INSERT INTO `sys_regions` VALUES ('9814', '113', '040129', '瀼渡镇');
INSERT INTO `sys_regions` VALUES ('9815', '113', '040130', '茨竹乡');
INSERT INTO `sys_regions` VALUES ('9816', '113', '040131', '柱山乡');
INSERT INTO `sys_regions` VALUES ('9817', '113', '040132', '燕山乡');
INSERT INTO `sys_regions` VALUES ('9818', '113', '040133', '溪口乡');
INSERT INTO `sys_regions` VALUES ('9819', '113', '040134', '普子乡');
INSERT INTO `sys_regions` VALUES ('9820', '113', '040135', '地宝乡');
INSERT INTO `sys_regions` VALUES ('9821', '113', '040136', '铁峰乡');
INSERT INTO `sys_regions` VALUES ('9822', '113', '040137', '黄柏乡');
INSERT INTO `sys_regions` VALUES ('9823', '113', '040138', '九池乡');
INSERT INTO `sys_regions` VALUES ('9824', '113', '040139', '梨树乡');
INSERT INTO `sys_regions` VALUES ('9825', '113', '040140', '郭村乡');
INSERT INTO `sys_regions` VALUES ('9826', '113', '040141', '恒合乡');
INSERT INTO `sys_regions` VALUES ('9831', '132', '041101', '九龙山镇');
INSERT INTO `sys_regions` VALUES ('9832', '132', '041102', '大进镇');
INSERT INTO `sys_regions` VALUES ('9833', '132', '041103', '敦好镇');
INSERT INTO `sys_regions` VALUES ('9834', '132', '041104', '高桥镇');
INSERT INTO `sys_regions` VALUES ('9835', '132', '041105', '郭家镇');
INSERT INTO `sys_regions` VALUES ('9836', '132', '041106', '和谦镇');
INSERT INTO `sys_regions` VALUES ('9837', '132', '041107', '河堰镇');
INSERT INTO `sys_regions` VALUES ('9838', '132', '041108', '厚坝镇');
INSERT INTO `sys_regions` VALUES ('9839', '132', '041109', '临江镇');
INSERT INTO `sys_regions` VALUES ('9840', '132', '041110', '南门镇');
INSERT INTO `sys_regions` VALUES ('9841', '132', '041111', '南雅镇');
INSERT INTO `sys_regions` VALUES ('9842', '132', '041112', '渠口镇');
INSERT INTO `sys_regions` VALUES ('9843', '132', '041113', '铁桥镇');
INSERT INTO `sys_regions` VALUES ('9844', '132', '041114', '温泉镇');
INSERT INTO `sys_regions` VALUES ('9845', '132', '041115', '义和镇');
INSERT INTO `sys_regions` VALUES ('9846', '132', '041116', '长沙镇');
INSERT INTO `sys_regions` VALUES ('9847', '132', '041117', '赵家镇');
INSERT INTO `sys_regions` VALUES ('9848', '132', '041118', '镇安镇');
INSERT INTO `sys_regions` VALUES ('9849', '132', '041119', '中和镇');
INSERT INTO `sys_regions` VALUES ('9850', '132', '041120', '竹溪镇');
INSERT INTO `sys_regions` VALUES ('9851', '132', '041121', '三汇口乡');
INSERT INTO `sys_regions` VALUES ('9852', '132', '041122', '白桥乡');
INSERT INTO `sys_regions` VALUES ('9853', '132', '041123', '大德乡');
INSERT INTO `sys_regions` VALUES ('9854', '132', '041124', '关面乡');
INSERT INTO `sys_regions` VALUES ('9855', '132', '041125', '金峰乡');
INSERT INTO `sys_regions` VALUES ('9856', '132', '041126', '麻柳乡');
INSERT INTO `sys_regions` VALUES ('9857', '132', '041127', '满月乡');
INSERT INTO `sys_regions` VALUES ('9858', '132', '041128', '谭家乡');
INSERT INTO `sys_regions` VALUES ('9859', '132', '041129', '天和乡');
INSERT INTO `sys_regions` VALUES ('9860', '132', '041130', '巫山镇');
INSERT INTO `sys_regions` VALUES ('9861', '132', '041131', '五通乡');
INSERT INTO `sys_regions` VALUES ('9862', '132', '041132', '紫水乡');
INSERT INTO `sys_regions` VALUES ('9898', '114', '040201', '李渡镇');
INSERT INTO `sys_regions` VALUES ('9899', '114', '040202', '白涛镇');
INSERT INTO `sys_regions` VALUES ('9900', '114', '040203', '百胜镇');
INSERT INTO `sys_regions` VALUES ('9901', '114', '040204', '堡子镇');
INSERT INTO `sys_regions` VALUES ('9902', '114', '040205', '焦石镇');
INSERT INTO `sys_regions` VALUES ('9903', '114', '040206', '蔺市镇');
INSERT INTO `sys_regions` VALUES ('9904', '114', '040207', '龙桥镇');
INSERT INTO `sys_regions` VALUES ('9905', '114', '040208', '龙潭镇');
INSERT INTO `sys_regions` VALUES ('9906', '114', '040209', '马武镇');
INSERT INTO `sys_regions` VALUES ('9907', '114', '040210', '南沱镇');
INSERT INTO `sys_regions` VALUES ('9908', '114', '040211', '青羊镇');
INSERT INTO `sys_regions` VALUES ('9909', '114', '040212', '清溪镇');
INSERT INTO `sys_regions` VALUES ('9910', '114', '040213', '石沱镇');
INSERT INTO `sys_regions` VALUES ('9911', '114', '040214', '新妙镇');
INSERT INTO `sys_regions` VALUES ('9912', '114', '040215', '义和镇');
INSERT INTO `sys_regions` VALUES ('9913', '114', '040216', '增福乡');
INSERT INTO `sys_regions` VALUES ('9914', '114', '040217', '珍溪镇');
INSERT INTO `sys_regions` VALUES ('9915', '114', '040218', '镇安镇');
INSERT INTO `sys_regions` VALUES ('9916', '114', '040219', '致韩镇');
INSERT INTO `sys_regions` VALUES ('9917', '114', '040220', '土地坡乡');
INSERT INTO `sys_regions` VALUES ('9918', '114', '040221', '武陵山乡');
INSERT INTO `sys_regions` VALUES ('9919', '114', '040222', '中峰乡');
INSERT INTO `sys_regions` VALUES ('9920', '114', '040223', '梓里乡');
INSERT INTO `sys_regions` VALUES ('9921', '114', '040224', '丛林乡');
INSERT INTO `sys_regions` VALUES ('9922', '114', '040225', '大木乡');
INSERT INTO `sys_regions` VALUES ('9923', '114', '040226', '惠民乡');
INSERT INTO `sys_regions` VALUES ('9924', '114', '040227', '酒店乡');
INSERT INTO `sys_regions` VALUES ('9925', '114', '040228', '聚宝乡');
INSERT INTO `sys_regions` VALUES ('9926', '114', '040229', '卷洞乡');
INSERT INTO `sys_regions` VALUES ('9927', '114', '040230', '两汇乡');
INSERT INTO `sys_regions` VALUES ('9928', '114', '040231', '罗云乡');
INSERT INTO `sys_regions` VALUES ('9929', '114', '040232', '明家乡');
INSERT INTO `sys_regions` VALUES ('9930', '114', '040233', '仁义乡');
INSERT INTO `sys_regions` VALUES ('9931', '114', '040234', '山窝乡');
INSERT INTO `sys_regions` VALUES ('9932', '114', '040235', '石和乡');
INSERT INTO `sys_regions` VALUES ('9933', '114', '040236', '石龙乡');
INSERT INTO `sys_regions` VALUES ('9934', '114', '040237', '太和乡');
INSERT INTO `sys_regions` VALUES ('9935', '114', '040238', '天台乡');
INSERT INTO `sys_regions` VALUES ('9936', '114', '040239', '同乐乡');
INSERT INTO `sys_regions` VALUES ('9937', '114', '040240', '新村乡');
INSERT INTO `sys_regions` VALUES ('9938', '115', '040301', '梁山镇');
INSERT INTO `sys_regions` VALUES ('9939', '115', '040302', '柏家镇');
INSERT INTO `sys_regions` VALUES ('9940', '115', '040303', '碧山镇');
INSERT INTO `sys_regions` VALUES ('9941', '115', '040304', '大观镇');
INSERT INTO `sys_regions` VALUES ('9942', '115', '040305', '福禄镇');
INSERT INTO `sys_regions` VALUES ('9943', '115', '040306', '合兴镇');
INSERT INTO `sys_regions` VALUES ('9944', '115', '040307', '和林镇');
INSERT INTO `sys_regions` VALUES ('9945', '115', '040308', '虎城镇');
INSERT INTO `sys_regions` VALUES ('9946', '115', '040309', '回龙镇');
INSERT INTO `sys_regions` VALUES ('9947', '115', '040310', '金带镇');
INSERT INTO `sys_regions` VALUES ('9948', '115', '040311', '聚奎镇');
INSERT INTO `sys_regions` VALUES ('9949', '115', '040312', '礼让镇');
INSERT INTO `sys_regions` VALUES ('9950', '115', '040313', '龙门镇');
INSERT INTO `sys_regions` VALUES ('9951', '115', '040314', '明达镇');
INSERT INTO `sys_regions` VALUES ('9952', '115', '040315', '蟠龙镇');
INSERT INTO `sys_regions` VALUES ('9953', '115', '040316', '屏锦镇');
INSERT INTO `sys_regions` VALUES ('9954', '115', '040317', '仁贤镇');
INSERT INTO `sys_regions` VALUES ('9955', '115', '040318', '石安镇');
INSERT INTO `sys_regions` VALUES ('9956', '115', '040319', '文化镇');
INSERT INTO `sys_regions` VALUES ('9957', '115', '040320', '新盛镇');
INSERT INTO `sys_regions` VALUES ('9958', '115', '040321', '荫平镇');
INSERT INTO `sys_regions` VALUES ('9959', '115', '040322', '袁驿镇');
INSERT INTO `sys_regions` VALUES ('9960', '115', '040323', '云龙镇');
INSERT INTO `sys_regions` VALUES ('9961', '115', '040324', '竹山镇');
INSERT INTO `sys_regions` VALUES ('9962', '115', '040325', '安胜乡');
INSERT INTO `sys_regions` VALUES ('9963', '115', '040326', '铁门乡');
INSERT INTO `sys_regions` VALUES ('9964', '115', '040327', '紫照乡');
INSERT INTO `sys_regions` VALUES ('9965', '115', '040328', '曲水乡');
INSERT INTO `sys_regions` VALUES ('9966', '115', '040329', '龙胜乡');
INSERT INTO `sys_regions` VALUES ('9967', '115', '040330', '城北乡');
INSERT INTO `sys_regions` VALUES ('9968', '115', '040331', '城东乡');
INSERT INTO `sys_regions` VALUES ('9969', '115', '040332', '复平乡');
INSERT INTO `sys_regions` VALUES ('9973', '119', '040401', '太平场镇');
INSERT INTO `sys_regions` VALUES ('9974', '119', '040402', '大观镇');
INSERT INTO `sys_regions` VALUES ('9975', '119', '040403', '大有镇');
INSERT INTO `sys_regions` VALUES ('9976', '119', '040404', '合溪镇');
INSERT INTO `sys_regions` VALUES ('9977', '119', '040405', '金山镇');
INSERT INTO `sys_regions` VALUES ('9978', '119', '040406', '鸣玉镇');
INSERT INTO `sys_regions` VALUES ('9979', '119', '040407', '南平镇');
INSERT INTO `sys_regions` VALUES ('9980', '119', '040408', '三泉镇');
INSERT INTO `sys_regions` VALUES ('9981', '119', '040409', '神童镇');
INSERT INTO `sys_regions` VALUES ('9982', '119', '040410', '石墙镇');
INSERT INTO `sys_regions` VALUES ('9983', '119', '040411', '水江镇');
INSERT INTO `sys_regions` VALUES ('9984', '119', '040412', '头渡镇');
INSERT INTO `sys_regions` VALUES ('9985', '119', '040413', '兴隆镇');
INSERT INTO `sys_regions` VALUES ('9986', '119', '040414', '冷水关乡');
INSERT INTO `sys_regions` VALUES ('9987', '119', '040415', '德隆乡');
INSERT INTO `sys_regions` VALUES ('9988', '119', '040416', '峰岩乡');
INSERT INTO `sys_regions` VALUES ('9989', '119', '040417', '福寿乡');
INSERT INTO `sys_regions` VALUES ('9990', '119', '040418', '古花乡');
INSERT INTO `sys_regions` VALUES ('9991', '119', '040419', '河图乡');
INSERT INTO `sys_regions` VALUES ('9992', '119', '040420', '民主乡');
INSERT INTO `sys_regions` VALUES ('9993', '119', '040421', '木凉乡');
INSERT INTO `sys_regions` VALUES ('9994', '119', '040422', '乾丰乡');
INSERT INTO `sys_regions` VALUES ('9995', '119', '040423', '庆元乡');
INSERT INTO `sys_regions` VALUES ('9996', '119', '040424', '石莲乡');
INSERT INTO `sys_regions` VALUES ('9997', '119', '040425', '石溪乡');
INSERT INTO `sys_regions` VALUES ('9998', '119', '040426', '铁村乡');
INSERT INTO `sys_regions` VALUES ('9999', '119', '040427', '土溪乡');
INSERT INTO `sys_regions` VALUES ('10000', '119', '040428', '鱼泉乡');
INSERT INTO `sys_regions` VALUES ('10001', '119', '040429', '中桥乡');
INSERT INTO `sys_regions` VALUES ('10005', '128', '040701', '正阳镇');
INSERT INTO `sys_regions` VALUES ('10006', '128', '040702', '舟白镇');
INSERT INTO `sys_regions` VALUES ('10007', '128', '040703', '阿蓬江镇');
INSERT INTO `sys_regions` VALUES ('10008', '128', '040704', '小南海镇');
INSERT INTO `sys_regions` VALUES ('10009', '128', '040705', '鹅池镇');
INSERT INTO `sys_regions` VALUES ('10010', '128', '040706', '冯家镇');
INSERT INTO `sys_regions` VALUES ('10011', '128', '040707', '黑溪镇');
INSERT INTO `sys_regions` VALUES ('10012', '128', '040708', '黄溪镇');
INSERT INTO `sys_regions` VALUES ('10013', '128', '040709', '金溪镇');
INSERT INTO `sys_regions` VALUES ('10014', '128', '040710', '黎水镇');
INSERT INTO `sys_regions` VALUES ('10015', '128', '040711', '邻鄂镇');
INSERT INTO `sys_regions` VALUES ('10016', '128', '040712', '马喇镇');
INSERT INTO `sys_regions` VALUES ('10017', '128', '040713', '石会镇');
INSERT INTO `sys_regions` VALUES ('10018', '128', '040714', '石家镇');
INSERT INTO `sys_regions` VALUES ('10019', '128', '040715', '濯水镇');
INSERT INTO `sys_regions` VALUES ('10020', '128', '040716', '白石乡');
INSERT INTO `sys_regions` VALUES ('10021', '128', '040717', '白土乡');
INSERT INTO `sys_regions` VALUES ('10022', '128', '040718', '金洞乡');
INSERT INTO `sys_regions` VALUES ('10023', '128', '040719', '蓬东乡');
INSERT INTO `sys_regions` VALUES ('10024', '128', '040720', '沙坝乡');
INSERT INTO `sys_regions` VALUES ('10025', '128', '040721', '杉岭乡');
INSERT INTO `sys_regions` VALUES ('10026', '128', '040722', '水市乡');
INSERT INTO `sys_regions` VALUES ('10027', '128', '040723', '水田乡');
INSERT INTO `sys_regions` VALUES ('10028', '128', '040724', '太极乡');
INSERT INTO `sys_regions` VALUES ('10029', '128', '040725', '五里乡');
INSERT INTO `sys_regions` VALUES ('10030', '128', '040726', '新华乡');
INSERT INTO `sys_regions` VALUES ('10031', '128', '040727', '中塘乡');
INSERT INTO `sys_regions` VALUES ('10032', '129', '040801', '仙女山镇');
INSERT INTO `sys_regions` VALUES ('10033', '129', '040802', '巷口镇');
INSERT INTO `sys_regions` VALUES ('10034', '129', '040803', '白马镇');
INSERT INTO `sys_regions` VALUES ('10035', '129', '040804', '火炉镇');
INSERT INTO `sys_regions` VALUES ('10036', '129', '040805', '江口镇');
INSERT INTO `sys_regions` VALUES ('10037', '129', '040806', '平桥镇');
INSERT INTO `sys_regions` VALUES ('10038', '129', '040807', '桐梓镇');
INSERT INTO `sys_regions` VALUES ('10039', '129', '040808', '土坎镇');
INSERT INTO `sys_regions` VALUES ('10040', '129', '040809', '鸭江镇');
INSERT INTO `sys_regions` VALUES ('10041', '129', '040810', '羊角镇');
INSERT INTO `sys_regions` VALUES ('10042', '129', '040811', '长坝镇');
INSERT INTO `sys_regions` VALUES ('10043', '129', '040812', '白云乡');
INSERT INTO `sys_regions` VALUES ('10044', '129', '040813', '沧沟乡');
INSERT INTO `sys_regions` VALUES ('10045', '129', '040814', '凤来乡');
INSERT INTO `sys_regions` VALUES ('10046', '129', '040815', '浩口乡');
INSERT INTO `sys_regions` VALUES ('10047', '129', '040816', '和顺乡');
INSERT INTO `sys_regions` VALUES ('10048', '129', '040817', '后坪乡');
INSERT INTO `sys_regions` VALUES ('10049', '129', '040818', '黄莺乡');
INSERT INTO `sys_regions` VALUES ('10050', '129', '040819', '接龙乡');
INSERT INTO `sys_regions` VALUES ('10051', '129', '040820', '庙垭乡');
INSERT INTO `sys_regions` VALUES ('10052', '129', '040821', '石桥乡');
INSERT INTO `sys_regions` VALUES ('10053', '129', '040822', '双河乡');
INSERT INTO `sys_regions` VALUES ('10054', '129', '040823', '铁矿乡');
INSERT INTO `sys_regions` VALUES ('10055', '129', '040824', '土地乡');
INSERT INTO `sys_regions` VALUES ('10056', '129', '040825', '文复乡');
INSERT INTO `sys_regions` VALUES ('10057', '129', '040826', '赵家乡');
INSERT INTO `sys_regions` VALUES ('10059', '130', '040901', '南天湖镇');
INSERT INTO `sys_regions` VALUES ('10060', '130', '040902', '许明寺镇');
INSERT INTO `sys_regions` VALUES ('10061', '130', '040903', '包鸾镇');
INSERT INTO `sys_regions` VALUES ('10062', '130', '040904', '董家镇');
INSERT INTO `sys_regions` VALUES ('10063', '130', '040905', '高家镇');
INSERT INTO `sys_regions` VALUES ('10064', '130', '040906', '虎威镇');
INSERT INTO `sys_regions` VALUES ('10065', '130', '040907', '江池镇');
INSERT INTO `sys_regions` VALUES ('10066', '130', '040908', '龙河镇');
INSERT INTO `sys_regions` VALUES ('10067', '130', '040909', '名山镇');
INSERT INTO `sys_regions` VALUES ('10068', '130', '040910', '三元镇');
INSERT INTO `sys_regions` VALUES ('10069', '130', '040911', '社坛镇');
INSERT INTO `sys_regions` VALUES ('10070', '130', '040912', '十直镇');
INSERT INTO `sys_regions` VALUES ('10071', '130', '040913', '树人镇');
INSERT INTO `sys_regions` VALUES ('10072', '130', '040914', '双路镇');
INSERT INTO `sys_regions` VALUES ('10073', '130', '040915', '武平镇');
INSERT INTO `sys_regions` VALUES ('10074', '130', '040916', '兴义镇');
INSERT INTO `sys_regions` VALUES ('10075', '130', '040917', '湛普镇');
INSERT INTO `sys_regions` VALUES ('10076', '130', '040918', '镇江镇');
INSERT INTO `sys_regions` VALUES ('10077', '130', '040919', '太平坝乡');
INSERT INTO `sys_regions` VALUES ('10078', '130', '040920', '双龙场乡');
INSERT INTO `sys_regions` VALUES ('10079', '130', '040921', '保合乡');
INSERT INTO `sys_regions` VALUES ('10080', '130', '040922', '崇兴乡');
INSERT INTO `sys_regions` VALUES ('10081', '130', '040923', '都督乡');
INSERT INTO `sys_regions` VALUES ('10082', '130', '040924', '暨龙乡');
INSERT INTO `sys_regions` VALUES ('10083', '130', '040925', '栗子乡');
INSERT INTO `sys_regions` VALUES ('10084', '130', '040926', '龙孔乡');
INSERT INTO `sys_regions` VALUES ('10085', '130', '040927', '青龙乡');
INSERT INTO `sys_regions` VALUES ('10086', '130', '040928', '仁沙乡');
INSERT INTO `sys_regions` VALUES ('10087', '130', '040929', '三坝乡');
INSERT INTO `sys_regions` VALUES ('10088', '130', '040930', '三建乡');
INSERT INTO `sys_regions` VALUES ('10091', '133', '041201', '云阳镇');
INSERT INTO `sys_regions` VALUES ('10092', '133', '041202', '巴阳镇');
INSERT INTO `sys_regions` VALUES ('10093', '133', '041203', '凤鸣镇');
INSERT INTO `sys_regions` VALUES ('10094', '133', '041204', '高阳镇');
INSERT INTO `sys_regions` VALUES ('10095', '133', '041205', '故陵镇');
INSERT INTO `sys_regions` VALUES ('10096', '133', '041206', '红狮镇');
INSERT INTO `sys_regions` VALUES ('10097', '133', '041207', '黄石镇');
INSERT INTO `sys_regions` VALUES ('10098', '133', '041208', '江口镇');
INSERT INTO `sys_regions` VALUES ('10099', '133', '041209', '龙角镇');
INSERT INTO `sys_regions` VALUES ('10100', '133', '041210', '路阳镇');
INSERT INTO `sys_regions` VALUES ('10101', '133', '041211', '南溪镇');
INSERT INTO `sys_regions` VALUES ('10102', '133', '041212', '农坝镇');
INSERT INTO `sys_regions` VALUES ('10103', '133', '041213', '盘龙镇');
INSERT INTO `sys_regions` VALUES ('10104', '133', '041214', '平安镇');
INSERT INTO `sys_regions` VALUES ('10105', '133', '041215', '渠马镇');
INSERT INTO `sys_regions` VALUES ('10106', '133', '041216', '人和镇');
INSERT INTO `sys_regions` VALUES ('10107', '133', '041217', '桑坪镇');
INSERT INTO `sys_regions` VALUES ('10108', '133', '041218', '沙市镇');
INSERT INTO `sys_regions` VALUES ('10109', '133', '041219', '双土镇');
INSERT INTO `sys_regions` VALUES ('10110', '133', '041220', '鱼泉镇');
INSERT INTO `sys_regions` VALUES ('10111', '133', '041221', '云安镇');
INSERT INTO `sys_regions` VALUES ('10112', '133', '041222', '洞鹿乡');
INSERT INTO `sys_regions` VALUES ('10113', '133', '041223', '后叶乡');
INSERT INTO `sys_regions` VALUES ('10114', '133', '041224', '龙洞乡');
INSERT INTO `sys_regions` VALUES ('10115', '133', '041225', '毛坝乡');
INSERT INTO `sys_regions` VALUES ('10116', '133', '041226', '泥溪乡');
INSERT INTO `sys_regions` VALUES ('10117', '133', '041227', '票草乡');
INSERT INTO `sys_regions` VALUES ('10118', '133', '041228', '普安乡');
INSERT INTO `sys_regions` VALUES ('10119', '133', '041229', '栖霞乡');
INSERT INTO `sys_regions` VALUES ('10120', '133', '041230', '清水乡');
INSERT INTO `sys_regions` VALUES ('10121', '133', '041231', '上坝乡');
INSERT INTO `sys_regions` VALUES ('10122', '133', '041232', '石门乡');
INSERT INTO `sys_regions` VALUES ('10123', '133', '041233', '双龙乡');
INSERT INTO `sys_regions` VALUES ('10124', '133', '041234', '水口乡');
INSERT INTO `sys_regions` VALUES ('10125', '133', '041235', '外郎乡');
INSERT INTO `sys_regions` VALUES ('10126', '133', '041236', '新津乡');
INSERT INTO `sys_regions` VALUES ('10127', '133', '041237', '堰坪乡');
INSERT INTO `sys_regions` VALUES ('10128', '133', '041238', '养鹿乡');
INSERT INTO `sys_regions` VALUES ('10129', '133', '041239', '耀灵乡');
INSERT INTO `sys_regions` VALUES ('10130', '133', '041240', '云硐乡');
INSERT INTO `sys_regions` VALUES ('10131', '134', '041301', '忠州镇');
INSERT INTO `sys_regions` VALUES ('10132', '134', '041302', '拔山镇');
INSERT INTO `sys_regions` VALUES ('10133', '134', '041303', '白石镇');
INSERT INTO `sys_regions` VALUES ('10134', '134', '041304', '东溪镇');
INSERT INTO `sys_regions` VALUES ('10135', '134', '041305', '复兴镇');
INSERT INTO `sys_regions` VALUES ('10136', '134', '041306', '官坝镇');
INSERT INTO `sys_regions` VALUES ('10137', '134', '041307', '花桥镇');
INSERT INTO `sys_regions` VALUES ('10138', '134', '041308', '黄金镇');
INSERT INTO `sys_regions` VALUES ('10139', '134', '041309', '金鸡镇');
INSERT INTO `sys_regions` VALUES ('10140', '134', '041310', '马灌镇');
INSERT INTO `sys_regions` VALUES ('10141', '134', '041311', '任家镇');
INSERT INTO `sys_regions` VALUES ('10142', '134', '041312', '汝溪镇');
INSERT INTO `sys_regions` VALUES ('10143', '134', '041313', '三汇镇');
INSERT INTO `sys_regions` VALUES ('10144', '134', '041314', '石宝镇');
INSERT INTO `sys_regions` VALUES ('10145', '134', '041315', '石黄镇');
INSERT INTO `sys_regions` VALUES ('10146', '134', '041316', '双桂镇');
INSERT INTO `sys_regions` VALUES ('10147', '134', '041317', '乌杨镇');
INSERT INTO `sys_regions` VALUES ('10148', '134', '041318', '新生镇');
INSERT INTO `sys_regions` VALUES ('10149', '134', '041319', '洋渡镇');
INSERT INTO `sys_regions` VALUES ('10150', '134', '041320', '野鹤镇');
INSERT INTO `sys_regions` VALUES ('10151', '134', '041321', '永丰镇');
INSERT INTO `sys_regions` VALUES ('10152', '134', '041322', '金声乡');
INSERT INTO `sys_regions` VALUES ('10153', '134', '041323', '磨子乡');
INSERT INTO `sys_regions` VALUES ('10154', '134', '041324', '善广乡');
INSERT INTO `sys_regions` VALUES ('10155', '134', '041325', '石子乡');
INSERT INTO `sys_regions` VALUES ('10156', '134', '041326', '涂井乡');
INSERT INTO `sys_regions` VALUES ('10157', '134', '041327', '兴峰乡');
INSERT INTO `sys_regions` VALUES ('10158', '135', '041401', '城厢镇');
INSERT INTO `sys_regions` VALUES ('10159', '135', '041402', '凤凰镇');
INSERT INTO `sys_regions` VALUES ('10160', '135', '041403', '古路镇');
INSERT INTO `sys_regions` VALUES ('10161', '135', '041404', '尖山镇');
INSERT INTO `sys_regions` VALUES ('10162', '135', '041405', '宁厂镇');
INSERT INTO `sys_regions` VALUES ('10163', '135', '041406', '上磺镇');
INSERT INTO `sys_regions` VALUES ('10164', '135', '041407', '文峰镇');
INSERT INTO `sys_regions` VALUES ('10165', '135', '041408', '下堡镇');
INSERT INTO `sys_regions` VALUES ('10166', '135', '041409', '徐家镇');
INSERT INTO `sys_regions` VALUES ('10167', '135', '041410', '朝阳洞乡');
INSERT INTO `sys_regions` VALUES ('10168', '135', '041411', '大河乡');
INSERT INTO `sys_regions` VALUES ('10169', '135', '041412', '峰灵乡');
INSERT INTO `sys_regions` VALUES ('10170', '135', '041413', '花台乡');
INSERT INTO `sys_regions` VALUES ('10171', '135', '041414', '兰英乡');
INSERT INTO `sys_regions` VALUES ('10172', '135', '041415', '菱角乡');
INSERT INTO `sys_regions` VALUES ('10173', '135', '041416', '蒲莲乡');
INSERT INTO `sys_regions` VALUES ('10174', '135', '041417', '胜利乡');
INSERT INTO `sys_regions` VALUES ('10175', '135', '041418', '双阳乡');
INSERT INTO `sys_regions` VALUES ('10176', '135', '041419', '塘坊乡');
INSERT INTO `sys_regions` VALUES ('10177', '135', '041420', '天星乡');
INSERT INTO `sys_regions` VALUES ('10178', '135', '041421', '天元乡');
INSERT INTO `sys_regions` VALUES ('10179', '135', '041422', '田坝乡');
INSERT INTO `sys_regions` VALUES ('10180', '135', '041423', '通城乡');
INSERT INTO `sys_regions` VALUES ('10181', '135', '041424', '土城乡');
INSERT INTO `sys_regions` VALUES ('10182', '135', '041425', '乌龙乡');
INSERT INTO `sys_regions` VALUES ('10183', '135', '041426', '鱼鳞乡');
INSERT INTO `sys_regions` VALUES ('10184', '135', '041427', '长桂乡');
INSERT INTO `sys_regions` VALUES ('10185', '135', '041428', '中岗乡');
INSERT INTO `sys_regions` VALUES ('10186', '135', '041429', '中梁乡');
INSERT INTO `sys_regions` VALUES ('10187', '136', '041501', '巫峡镇');
INSERT INTO `sys_regions` VALUES ('10188', '136', '041502', '大昌镇');
INSERT INTO `sys_regions` VALUES ('10189', '136', '041503', '福田镇');
INSERT INTO `sys_regions` VALUES ('10190', '136', '041504', '官渡镇');
INSERT INTO `sys_regions` VALUES ('10191', '136', '041505', '官阳镇');
INSERT INTO `sys_regions` VALUES ('10192', '136', '041506', '龙溪镇');
INSERT INTO `sys_regions` VALUES ('10193', '136', '041507', '骡坪镇');
INSERT INTO `sys_regions` VALUES ('10194', '136', '041508', '庙堂乡');
INSERT INTO `sys_regions` VALUES ('10195', '136', '041509', '庙宇镇');
INSERT INTO `sys_regions` VALUES ('10196', '136', '041510', '双龙镇');
INSERT INTO `sys_regions` VALUES ('10197', '136', '041511', '铜鼓镇');
INSERT INTO `sys_regions` VALUES ('10198', '136', '041512', '抱龙镇');
INSERT INTO `sys_regions` VALUES ('10199', '136', '041513', '大溪乡');
INSERT INTO `sys_regions` VALUES ('10200', '136', '041514', '当阳乡');
INSERT INTO `sys_regions` VALUES ('10201', '136', '041515', '邓家乡');
INSERT INTO `sys_regions` VALUES ('10202', '136', '041516', '笃坪乡');
INSERT INTO `sys_regions` VALUES ('10203', '136', '041517', '红椿乡');
INSERT INTO `sys_regions` VALUES ('10204', '136', '041518', '建平乡');
INSERT INTO `sys_regions` VALUES ('10205', '136', '041519', '金坪乡');
INSERT INTO `sys_regions` VALUES ('10206', '136', '041520', '两坪乡');
INSERT INTO `sys_regions` VALUES ('10207', '136', '041521', '龙井乡');
INSERT INTO `sys_regions` VALUES ('10208', '136', '041522', '培石乡');
INSERT INTO `sys_regions` VALUES ('10209', '136', '041523', '平河乡');
INSERT INTO `sys_regions` VALUES ('10210', '136', '041524', '曲尺乡');
INSERT INTO `sys_regions` VALUES ('10211', '136', '041525', '三溪乡');
INSERT INTO `sys_regions` VALUES ('10212', '136', '041526', '竹贤乡');
INSERT INTO `sys_regions` VALUES ('10213', '137', '041601', '南宾镇');
INSERT INTO `sys_regions` VALUES ('10214', '137', '041602', '黄水镇');
INSERT INTO `sys_regions` VALUES ('10215', '137', '041603', '临溪镇');
INSERT INTO `sys_regions` VALUES ('10216', '137', '041604', '龙沙镇');
INSERT INTO `sys_regions` VALUES ('10217', '137', '041605', '马武镇');
INSERT INTO `sys_regions` VALUES ('10218', '137', '041606', '沙子镇');
INSERT INTO `sys_regions` VALUES ('10219', '137', '041607', '王场镇');
INSERT INTO `sys_regions` VALUES ('10220', '137', '041608', '西沱镇');
INSERT INTO `sys_regions` VALUES ('10221', '137', '041609', '下路镇');
INSERT INTO `sys_regions` VALUES ('10222', '137', '041610', '沿溪镇');
INSERT INTO `sys_regions` VALUES ('10223', '137', '041611', '渔池镇');
INSERT INTO `sys_regions` VALUES ('10224', '137', '041612', '悦崃镇');
INSERT INTO `sys_regions` VALUES ('10225', '137', '041613', '大歇乡');
INSERT INTO `sys_regions` VALUES ('10226', '137', '041614', '枫木乡');
INSERT INTO `sys_regions` VALUES ('10227', '137', '041615', '河嘴乡');
INSERT INTO `sys_regions` VALUES ('10228', '137', '041616', '黄鹤乡');
INSERT INTO `sys_regions` VALUES ('10229', '137', '041617', '金铃乡');
INSERT INTO `sys_regions` VALUES ('10230', '137', '041618', '金竹乡');
INSERT INTO `sys_regions` VALUES ('10231', '137', '041619', '冷水乡');
INSERT INTO `sys_regions` VALUES ('10232', '137', '041620', '黎场乡');
INSERT INTO `sys_regions` VALUES ('10233', '137', '041621', '六塘乡');
INSERT INTO `sys_regions` VALUES ('10234', '137', '041622', '龙潭乡');
INSERT INTO `sys_regions` VALUES ('10235', '137', '041623', '桥头乡');
INSERT INTO `sys_regions` VALUES ('10236', '137', '041624', '三河乡');
INSERT INTO `sys_regions` VALUES ('10237', '137', '041625', '三益乡');
INSERT INTO `sys_regions` VALUES ('10238', '137', '041626', '石家乡');
INSERT INTO `sys_regions` VALUES ('10239', '137', '041627', '万朝乡');
INSERT INTO `sys_regions` VALUES ('10240', '137', '041628', '王家乡');
INSERT INTO `sys_regions` VALUES ('10241', '137', '041629', '洗新乡');
INSERT INTO `sys_regions` VALUES ('10242', '137', '041630', '新乐乡');
INSERT INTO `sys_regions` VALUES ('10243', '137', '041631', '中益乡');
INSERT INTO `sys_regions` VALUES ('10245', '138', '041701', '保家镇');
INSERT INTO `sys_regions` VALUES ('10246', '138', '041702', '高谷镇');
INSERT INTO `sys_regions` VALUES ('10247', '138', '041703', '黄家镇');
INSERT INTO `sys_regions` VALUES ('10248', '138', '041704', '连湖镇');
INSERT INTO `sys_regions` VALUES ('10249', '138', '041705', '龙射镇');
INSERT INTO `sys_regions` VALUES ('10250', '138', '041706', '鹿角镇');
INSERT INTO `sys_regions` VALUES ('10251', '138', '041707', '普子镇');
INSERT INTO `sys_regions` VALUES ('10252', '138', '041708', '桑柘镇');
INSERT INTO `sys_regions` VALUES ('10253', '138', '041709', '万足镇');
INSERT INTO `sys_regions` VALUES ('10254', '138', '041710', '郁山镇');
INSERT INTO `sys_regions` VALUES ('10255', '138', '041711', '梅子垭乡');
INSERT INTO `sys_regions` VALUES ('10256', '138', '041712', '鞍子乡');
INSERT INTO `sys_regions` VALUES ('10257', '138', '041713', '大垭乡');
INSERT INTO `sys_regions` VALUES ('10258', '138', '041714', '棣棠乡');
INSERT INTO `sys_regions` VALUES ('10259', '138', '041715', '靛水乡');
INSERT INTO `sys_regions` VALUES ('10260', '138', '041716', '朗溪乡');
INSERT INTO `sys_regions` VALUES ('10261', '138', '041717', '联合乡');
INSERT INTO `sys_regions` VALUES ('10262', '138', '041718', '龙塘乡');
INSERT INTO `sys_regions` VALUES ('10263', '138', '041719', '龙溪乡');
INSERT INTO `sys_regions` VALUES ('10264', '138', '041720', '芦塘乡');
INSERT INTO `sys_regions` VALUES ('10265', '138', '041721', '鹿鸣乡');
INSERT INTO `sys_regions` VALUES ('10266', '138', '041722', '平安乡');
INSERT INTO `sys_regions` VALUES ('10267', '138', '041723', '迁乔乡');
INSERT INTO `sys_regions` VALUES ('10268', '138', '041724', '乔梓乡');
INSERT INTO `sys_regions` VALUES ('10269', '138', '041725', '润溪乡');
INSERT INTO `sys_regions` VALUES ('10270', '138', '041726', '三义乡');
INSERT INTO `sys_regions` VALUES ('10271', '138', '041727', '善感乡');
INSERT INTO `sys_regions` VALUES ('10272', '138', '041728', '石柳乡');
INSERT INTO `sys_regions` VALUES ('10273', '138', '041729', '石盘乡');
INSERT INTO `sys_regions` VALUES ('10274', '138', '041730', '双龙乡');
INSERT INTO `sys_regions` VALUES ('10275', '138', '041731', '太原乡');
INSERT INTO `sys_regions` VALUES ('10276', '138', '041732', '桐楼乡');
INSERT INTO `sys_regions` VALUES ('10277', '138', '041733', '小厂乡');
INSERT INTO `sys_regions` VALUES ('10278', '138', '041734', '新田乡');
INSERT INTO `sys_regions` VALUES ('10279', '138', '041735', '岩东乡');
INSERT INTO `sys_regions` VALUES ('10280', '138', '041736', '长滩乡');
INSERT INTO `sys_regions` VALUES ('10281', '138', '041737', '诸佛乡');
INSERT INTO `sys_regions` VALUES ('10282', '138', '041738', '走马乡');
INSERT INTO `sys_regions` VALUES ('10283', '139', '041801', '桂溪镇');
INSERT INTO `sys_regions` VALUES ('10284', '139', '041802', '澄溪镇');
INSERT INTO `sys_regions` VALUES ('10285', '139', '041803', '高安镇');
INSERT INTO `sys_regions` VALUES ('10286', '139', '041804', '高峰镇');
INSERT INTO `sys_regions` VALUES ('10287', '139', '041805', '鹤游镇');
INSERT INTO `sys_regions` VALUES ('10288', '139', '041806', '普顺镇');
INSERT INTO `sys_regions` VALUES ('10289', '139', '041807', '沙坪镇');
INSERT INTO `sys_regions` VALUES ('10290', '139', '041808', '太平镇');
INSERT INTO `sys_regions` VALUES ('10291', '139', '041809', '五洞镇');
INSERT INTO `sys_regions` VALUES ('10292', '139', '041810', '新民镇');
INSERT INTO `sys_regions` VALUES ('10293', '139', '041811', '砚台镇');
INSERT INTO `sys_regions` VALUES ('10294', '139', '041812', '永安镇');
INSERT INTO `sys_regions` VALUES ('10295', '139', '041813', '周嘉镇');
INSERT INTO `sys_regions` VALUES ('10296', '139', '041814', '白家乡');
INSERT INTO `sys_regions` VALUES ('10297', '139', '041815', '包家乡');
INSERT INTO `sys_regions` VALUES ('10298', '139', '041816', '曹回乡');
INSERT INTO `sys_regions` VALUES ('10299', '139', '041817', '大石乡');
INSERT INTO `sys_regions` VALUES ('10300', '139', '041818', '杠家乡');
INSERT INTO `sys_regions` VALUES ('10301', '139', '041819', '黄沙乡');
INSERT INTO `sys_regions` VALUES ('10302', '139', '041820', '裴兴乡');
INSERT INTO `sys_regions` VALUES ('10303', '139', '041821', '三溪乡');
INSERT INTO `sys_regions` VALUES ('10304', '139', '041822', '沙河乡');
INSERT INTO `sys_regions` VALUES ('10305', '139', '041823', '永平乡');
INSERT INTO `sys_regions` VALUES ('10306', '139', '041824', '长龙乡');
INSERT INTO `sys_regions` VALUES ('10307', '140', '041901', '钟多镇');
INSERT INTO `sys_regions` VALUES ('10308', '140', '041902', '苍岭镇');
INSERT INTO `sys_regions` VALUES ('10309', '140', '041903', '车田乡');
INSERT INTO `sys_regions` VALUES ('10310', '140', '041904', '大溪镇');
INSERT INTO `sys_regions` VALUES ('10311', '140', '041905', '丁市镇');
INSERT INTO `sys_regions` VALUES ('10312', '140', '041906', '泔溪镇');
INSERT INTO `sys_regions` VALUES ('10313', '140', '041907', '龚滩镇');
INSERT INTO `sys_regions` VALUES ('10314', '140', '041908', '黑水镇');
INSERT INTO `sys_regions` VALUES ('10315', '140', '041909', '后溪镇');
INSERT INTO `sys_regions` VALUES ('10316', '140', '041910', '李溪镇');
INSERT INTO `sys_regions` VALUES ('10317', '140', '041911', '龙潭镇');
INSERT INTO `sys_regions` VALUES ('10318', '140', '041912', '麻旺镇');
INSERT INTO `sys_regions` VALUES ('10319', '140', '041913', '小河镇');
INSERT INTO `sys_regions` VALUES ('10320', '140', '041914', '兴隆镇');
INSERT INTO `sys_regions` VALUES ('10321', '140', '041915', '酉酬镇');
INSERT INTO `sys_regions` VALUES ('10322', '140', '041916', '南腰界乡');
INSERT INTO `sys_regions` VALUES ('10323', '140', '041917', '后坪坝乡');
INSERT INTO `sys_regions` VALUES ('10324', '140', '041918', '板溪乡');
INSERT INTO `sys_regions` VALUES ('10325', '140', '041919', '官清乡');
INSERT INTO `sys_regions` VALUES ('10326', '140', '041920', '花田乡');
INSERT INTO `sys_regions` VALUES ('10327', '140', '041921', '江丰乡');
INSERT INTO `sys_regions` VALUES ('10328', '140', '041922', '可大乡');
INSERT INTO `sys_regions` VALUES ('10329', '140', '041923', '浪坪乡');
INSERT INTO `sys_regions` VALUES ('10330', '140', '041924', '两罾乡');
INSERT INTO `sys_regions` VALUES ('10331', '140', '041925', '毛坝乡');
INSERT INTO `sys_regions` VALUES ('10332', '140', '041926', '庙溪乡');
INSERT INTO `sys_regions` VALUES ('10333', '140', '041927', '木叶乡');
INSERT INTO `sys_regions` VALUES ('10334', '140', '041928', '楠木乡');
INSERT INTO `sys_regions` VALUES ('10335', '140', '041929', '偏柏乡');
INSERT INTO `sys_regions` VALUES ('10336', '140', '041930', '清泉乡');
INSERT INTO `sys_regions` VALUES ('10337', '140', '041931', '双泉乡');
INSERT INTO `sys_regions` VALUES ('10338', '140', '041932', '天馆乡');
INSERT INTO `sys_regions` VALUES ('10339', '140', '041933', '铜鼓乡');
INSERT INTO `sys_regions` VALUES ('10340', '140', '041934', '涂市乡');
INSERT INTO `sys_regions` VALUES ('10341', '140', '041935', '万木乡');
INSERT INTO `sys_regions` VALUES ('10342', '140', '041936', '五福乡');
INSERT INTO `sys_regions` VALUES ('10343', '140', '041937', '宜居乡');
INSERT INTO `sys_regions` VALUES ('10344', '140', '041938', '腴地乡');
INSERT INTO `sys_regions` VALUES ('10345', '140', '041939', '板桥乡');
INSERT INTO `sys_regions` VALUES ('10346', '141', '042001', '清溪场镇');
INSERT INTO `sys_regions` VALUES ('10347', '141', '042002', '中和镇');
INSERT INTO `sys_regions` VALUES ('10348', '141', '042003', '隘口镇');
INSERT INTO `sys_regions` VALUES ('10349', '141', '042004', '峨溶镇');
INSERT INTO `sys_regions` VALUES ('10350', '141', '042005', '官庄镇');
INSERT INTO `sys_regions` VALUES ('10351', '141', '042006', '洪安镇');
INSERT INTO `sys_regions` VALUES ('10352', '141', '042007', '兰桥镇');
INSERT INTO `sys_regions` VALUES ('10353', '141', '042008', '龙池镇');
INSERT INTO `sys_regions` VALUES ('10354', '141', '042009', '梅江镇');
INSERT INTO `sys_regions` VALUES ('10355', '141', '042010', '平凯镇');
INSERT INTO `sys_regions` VALUES ('10356', '141', '042011', '溶溪镇');
INSERT INTO `sys_regions` VALUES ('10357', '141', '042012', '石堤镇');
INSERT INTO `sys_regions` VALUES ('10358', '141', '042013', '石耶镇');
INSERT INTO `sys_regions` VALUES ('10359', '141', '042014', '雅江镇');
INSERT INTO `sys_regions` VALUES ('10360', '141', '042015', '巴家乡');
INSERT INTO `sys_regions` VALUES ('10361', '141', '042016', '保安乡');
INSERT INTO `sys_regions` VALUES ('10362', '141', '042017', '岑溪乡');
INSERT INTO `sys_regions` VALUES ('10363', '141', '042018', '大溪乡');
INSERT INTO `sys_regions` VALUES ('10364', '141', '042019', '干川乡');
INSERT INTO `sys_regions` VALUES ('10365', '141', '042020', '膏田乡');
INSERT INTO `sys_regions` VALUES ('10366', '141', '042021', '官舟乡');
INSERT INTO `sys_regions` VALUES ('10367', '141', '042022', '海洋乡');
INSERT INTO `sys_regions` VALUES ('10368', '141', '042023', '里仁乡');
INSERT INTO `sys_regions` VALUES ('10369', '141', '042024', '妙泉乡');
INSERT INTO `sys_regions` VALUES ('10370', '141', '042025', '平马乡');
INSERT INTO `sys_regions` VALUES ('10371', '141', '042026', '宋农乡');
INSERT INTO `sys_regions` VALUES ('10372', '141', '042027', '溪口乡');
INSERT INTO `sys_regions` VALUES ('10373', '141', '042028', '孝溪乡');
INSERT INTO `sys_regions` VALUES ('10374', '141', '042029', '涌洞乡');
INSERT INTO `sys_regions` VALUES ('10375', '141', '042030', '中平乡');
INSERT INTO `sys_regions` VALUES ('10376', '141', '042031', '钟灵乡');
INSERT INTO `sys_regions` VALUES ('10377', '131', '041001', '永安镇');
INSERT INTO `sys_regions` VALUES ('10378', '131', '041002', '白帝镇');
INSERT INTO `sys_regions` VALUES ('10379', '131', '041003', '草堂镇');
INSERT INTO `sys_regions` VALUES ('10380', '131', '041004', '大树镇');
INSERT INTO `sys_regions` VALUES ('10381', '131', '041005', '汾河镇');
INSERT INTO `sys_regions` VALUES ('10382', '131', '041006', '公平镇');
INSERT INTO `sys_regions` VALUES ('10383', '131', '041007', '甲高镇');
INSERT INTO `sys_regions` VALUES ('10384', '131', '041008', '康乐镇');
INSERT INTO `sys_regions` VALUES ('10385', '131', '041009', '青龙镇');
INSERT INTO `sys_regions` VALUES ('10386', '131', '041010', '吐祥镇');
INSERT INTO `sys_regions` VALUES ('10387', '131', '041011', '新民镇');
INSERT INTO `sys_regions` VALUES ('10388', '131', '041012', '兴隆镇');
INSERT INTO `sys_regions` VALUES ('10389', '131', '041013', '羊市镇');
INSERT INTO `sys_regions` VALUES ('10390', '131', '041014', '朱衣镇');
INSERT INTO `sys_regions` VALUES ('10391', '131', '041015', '竹园镇');
INSERT INTO `sys_regions` VALUES ('10392', '131', '041016', '安坪乡');
INSERT INTO `sys_regions` VALUES ('10393', '131', '041017', '冯坪乡');
INSERT INTO `sys_regions` VALUES ('10394', '131', '041018', '鹤峰乡');
INSERT INTO `sys_regions` VALUES ('10395', '131', '041019', '红土乡');
INSERT INTO `sys_regions` VALUES ('10396', '131', '041020', '康坪乡');
INSERT INTO `sys_regions` VALUES ('10397', '131', '041021', '龙桥乡');
INSERT INTO `sys_regions` VALUES ('10398', '131', '041022', '平安乡');
INSERT INTO `sys_regions` VALUES ('10399', '131', '041023', '石岗乡');
INSERT INTO `sys_regions` VALUES ('10400', '131', '041024', '太和乡');
INSERT INTO `sys_regions` VALUES ('10401', '131', '041025', '五马乡');
INSERT INTO `sys_regions` VALUES ('10402', '131', '041026', '新政乡');
INSERT INTO `sys_regions` VALUES ('10403', '131', '041027', '岩湾乡');
INSERT INTO `sys_regions` VALUES ('10404', '131', '041028', '云雾乡');
INSERT INTO `sys_regions` VALUES ('10405', '131', '041029', '长安乡');
INSERT INTO `sys_regions` VALUES ('10406', '4164', '042101', '葛城镇');
INSERT INTO `sys_regions` VALUES ('10407', '4164', '042102', '巴山镇');
INSERT INTO `sys_regions` VALUES ('10408', '4164', '042103', '高观镇');
INSERT INTO `sys_regions` VALUES ('10409', '4164', '042104', '庙坝镇');
INSERT INTO `sys_regions` VALUES ('10410', '4164', '042105', '明通镇');
INSERT INTO `sys_regions` VALUES ('10411', '4164', '042106', '坪坝镇');
INSERT INTO `sys_regions` VALUES ('10412', '4164', '042107', '修齐镇');
INSERT INTO `sys_regions` VALUES ('10413', '4164', '042108', '北屏乡');
INSERT INTO `sys_regions` VALUES ('10414', '4164', '042109', '东安乡');
INSERT INTO `sys_regions` VALUES ('10415', '4164', '042110', '高楠乡');
INSERT INTO `sys_regions` VALUES ('10416', '4164', '042111', '高燕乡');
INSERT INTO `sys_regions` VALUES ('10417', '4164', '042112', '河鱼乡');
INSERT INTO `sys_regions` VALUES ('10418', '4164', '042113', '厚坪乡');
INSERT INTO `sys_regions` VALUES ('10419', '4164', '042114', '鸡鸣乡');
INSERT INTO `sys_regions` VALUES ('10420', '4164', '042115', '岚天乡');
INSERT INTO `sys_regions` VALUES ('10421', '4164', '042116', '蓼子乡');
INSERT INTO `sys_regions` VALUES ('10422', '4164', '042117', '龙田乡');
INSERT INTO `sys_regions` VALUES ('10423', '4164', '042118', '明中乡');
INSERT INTO `sys_regions` VALUES ('10424', '4164', '042119', '双河乡');
INSERT INTO `sys_regions` VALUES ('10425', '4164', '042120', '咸宜乡');
INSERT INTO `sys_regions` VALUES ('10426', '4164', '042121', '沿河乡');
INSERT INTO `sys_regions` VALUES ('10427', '4164', '042122', '治平乡');
INSERT INTO `sys_regions` VALUES ('10428', '4164', '042123', '周溪乡');
INSERT INTO `sys_regions` VALUES ('10429', '4164', '042124', '左岚乡');
INSERT INTO `sys_regions` VALUES ('11432', '793', '101307', '新林区');
INSERT INTO `sys_regions` VALUES ('12746', '3699', '230808', '畅好农场');
INSERT INTO `sys_regions` VALUES ('12747', '3115', '230313', '彬村山华侨农场');
INSERT INTO `sys_regions` VALUES ('12748', '3115', '230314', '东太农场');
INSERT INTO `sys_regions` VALUES ('12749', '3115', '230315', '东红农场');
INSERT INTO `sys_regions` VALUES ('12750', '3115', '230316', '东升农场');
INSERT INTO `sys_regions` VALUES ('12751', '3115', '230317', '南俸农场');
INSERT INTO `sys_regions` VALUES ('12752', '3034', '230220', '西培农场');
INSERT INTO `sys_regions` VALUES ('12753', '3034', '230221', '西联农场');
INSERT INTO `sys_regions` VALUES ('12754', '3034', '230222', '蓝洋农场');
INSERT INTO `sys_regions` VALUES ('12755', '3034', '230223', '八一农场');
INSERT INTO `sys_regions` VALUES ('12756', '3034', '230224', '西华农场');
INSERT INTO `sys_regions` VALUES ('12757', '3034', '230225', '西庆农场');
INSERT INTO `sys_regions` VALUES ('12758', '3034', '230226', '西流农场');
INSERT INTO `sys_regions` VALUES ('12759', '3034', '230227', '新盈农场');
INSERT INTO `sys_regions` VALUES ('12760', '3034', '230228', '龙山农场');
INSERT INTO `sys_regions` VALUES ('12761', '3034', '230229', '红岭农场');
INSERT INTO `sys_regions` VALUES ('12762', '3698', '230717', '公坡镇');
INSERT INTO `sys_regions` VALUES ('12763', '3698', '230718', '迈号镇');
INSERT INTO `sys_regions` VALUES ('12764', '3698', '230719', '清谰镇');
INSERT INTO `sys_regions` VALUES ('12765', '3698', '230720', '南阳镇');
INSERT INTO `sys_regions` VALUES ('12766', '3698', '230721', '新桥镇');
INSERT INTO `sys_regions` VALUES ('12767', '3698', '230722', '头苑镇');
INSERT INTO `sys_regions` VALUES ('12768', '3698', '230723', '宝芳乡');
INSERT INTO `sys_regions` VALUES ('12769', '3698', '230724', '龙马乡');
INSERT INTO `sys_regions` VALUES ('12770', '3698', '230725', '湖山乡');
INSERT INTO `sys_regions` VALUES ('12771', '3698', '230726', '东路农场');
INSERT INTO `sys_regions` VALUES ('12772', '3698', '230727', '南阳农场');
INSERT INTO `sys_regions` VALUES ('12773', '3698', '230728', '罗豆农场');
INSERT INTO `sys_regions` VALUES ('12774', '3698', '230729', '橡胶研究所');
INSERT INTO `sys_regions` VALUES ('12775', '3137', '230413', '六连林场');
INSERT INTO `sys_regions` VALUES ('12776', '3137', '230414', '东兴农场');
INSERT INTO `sys_regions` VALUES ('12777', '3137', '230415', '东和农场');
INSERT INTO `sys_regions` VALUES ('12778', '3137', '230416', '新中农场');
INSERT INTO `sys_regions` VALUES ('12779', '3137', '230417', '兴隆华侨农场');
INSERT INTO `sys_regions` VALUES ('12780', '3173', '230511', '广坝农场');
INSERT INTO `sys_regions` VALUES ('12781', '3173', '230512', '东方华侨农场');
INSERT INTO `sys_regions` VALUES ('12782', '3703', '231111', '金鸡岭农场');
INSERT INTO `sys_regions` VALUES ('12783', '3703', '231112', '中瑞农场');
INSERT INTO `sys_regions` VALUES ('12784', '3703', '231113', '南海农场');
INSERT INTO `sys_regions` VALUES ('12785', '3704', '231209', '中建农场');
INSERT INTO `sys_regions` VALUES ('12786', '3704', '231210', '中坤农场');
INSERT INTO `sys_regions` VALUES ('12787', '3702', '231011', '大丰镇');
INSERT INTO `sys_regions` VALUES ('12788', '3702', '231012', '红光农场');
INSERT INTO `sys_regions` VALUES ('12789', '3702', '231013', '西达农场');
INSERT INTO `sys_regions` VALUES ('12790', '3702', '231014', '金安农场');
INSERT INTO `sys_regions` VALUES ('12791', '3701', '230912', '红华农场');
INSERT INTO `sys_regions` VALUES ('12792', '3701', '230913', '加来农场');
INSERT INTO `sys_regions` VALUES ('12793', '3706', '231412', '白沙农场');
INSERT INTO `sys_regions` VALUES ('12794', '3706', '231413', '龙江农场');
INSERT INTO `sys_regions` VALUES ('12795', '3706', '231414', '邦溪农场');
INSERT INTO `sys_regions` VALUES ('12796', '3705', '231307', '七叉镇');
INSERT INTO `sys_regions` VALUES ('12797', '3705', '231308', '王下乡');
INSERT INTO `sys_regions` VALUES ('12798', '3705', '231309', '海南矿业公司');
INSERT INTO `sys_regions` VALUES ('12799', '3705', '231310', '霸王岭林场');
INSERT INTO `sys_regions` VALUES ('12800', '3705', '231311', '红林农场');
INSERT INTO `sys_regions` VALUES ('12801', '3710', '231812', '尖峰岭林业公司');
INSERT INTO `sys_regions` VALUES ('12802', '3710', '231813', '莺歌海盐场');
INSERT INTO `sys_regions` VALUES ('12803', '3710', '231814', '山荣农场');
INSERT INTO `sys_regions` VALUES ('12804', '3710', '231815', '乐光农场');
INSERT INTO `sys_regions` VALUES ('12805', '3710', '231816', '保国农场');
INSERT INTO `sys_regions` VALUES ('12806', '3708', '231612', '吊罗山林业公司');
INSERT INTO `sys_regions` VALUES ('12807', '3708', '231613', '岭门农场');
INSERT INTO `sys_regions` VALUES ('12808', '3708', '231614', '南平农场');
INSERT INTO `sys_regions` VALUES ('12809', '3709', '231710', '保亭研究所');
INSERT INTO `sys_regions` VALUES ('12810', '3709', '231711', '新星农场');
INSERT INTO `sys_regions` VALUES ('12811', '3709', '231712', '金江农场');
INSERT INTO `sys_regions` VALUES ('12812', '3709', '231713', '三道农场');
INSERT INTO `sys_regions` VALUES ('12813', '3707', '231510', '吊罗山乡');
INSERT INTO `sys_regions` VALUES ('12814', '3707', '231511', '黎母山林业公司');
INSERT INTO `sys_regions` VALUES ('12815', '3707', '231512', '阳江农场');
INSERT INTO `sys_regions` VALUES ('12816', '3707', '231513', '乌石农场');
INSERT INTO `sys_regions` VALUES ('12817', '3707', '231514', '加钗农场');
INSERT INTO `sys_regions` VALUES ('12818', '3707', '231515', '长征农场');
INSERT INTO `sys_regions` VALUES ('12819', '3711', '231903', '中沙群岛');
INSERT INTO `sys_regions` VALUES ('12824', '3034', '230230', '热作学院');
INSERT INTO `sys_regions` VALUES ('13520', '126', '040601', '龙滩子镇');
INSERT INTO `sys_regions` VALUES ('13521', '126', '040602', '龙水镇');
INSERT INTO `sys_regions` VALUES ('13522', '126', '040603', '智凤镇');
INSERT INTO `sys_regions` VALUES ('13523', '126', '040604', '宝顶镇');
INSERT INTO `sys_regions` VALUES ('13524', '126', '040605', '中敖镇');
INSERT INTO `sys_regions` VALUES ('13525', '126', '040606', '三驱镇');
INSERT INTO `sys_regions` VALUES ('13526', '126', '040607', '宝兴镇');
INSERT INTO `sys_regions` VALUES ('13527', '126', '040608', '玉龙镇');
INSERT INTO `sys_regions` VALUES ('13528', '126', '040609', '石马镇');
INSERT INTO `sys_regions` VALUES ('13529', '126', '040610', '拾万镇');
INSERT INTO `sys_regions` VALUES ('13530', '126', '040611', '回龙镇');
INSERT INTO `sys_regions` VALUES ('13531', '126', '040612', '金山镇');
INSERT INTO `sys_regions` VALUES ('13532', '126', '040613', '万古镇');
INSERT INTO `sys_regions` VALUES ('13533', '126', '040614', '国梁镇');
INSERT INTO `sys_regions` VALUES ('13534', '126', '040615', '雍溪镇');
INSERT INTO `sys_regions` VALUES ('13535', '126', '040616', '珠溪镇');
INSERT INTO `sys_regions` VALUES ('13536', '126', '040617', '龙石镇');
INSERT INTO `sys_regions` VALUES ('13537', '126', '040618', '邮亭镇');
INSERT INTO `sys_regions` VALUES ('13538', '126', '040619', '铁山镇');
INSERT INTO `sys_regions` VALUES ('13539', '126', '040620', '高升镇');
INSERT INTO `sys_regions` VALUES ('13540', '126', '040621', '季家镇');
INSERT INTO `sys_regions` VALUES ('13541', '126', '040622', '古龙镇');
INSERT INTO `sys_regions` VALUES ('13542', '126', '040623', '高坪镇');
INSERT INTO `sys_regions` VALUES ('13543', '126', '040624', '双路镇');
INSERT INTO `sys_regions` VALUES ('13544', '126', '040625', '通桥镇');
INSERT INTO `sys_regions` VALUES ('13989', '984', '121207', '江阴市');
INSERT INTO `sys_regions` VALUES ('14848', '2666', '310608', '和田县');
INSERT INTO `sys_regions` VALUES ('15943', '984', '121208', '宜兴市');
INSERT INTO `sys_regions` VALUES ('15944', '1255', '150605', '柯桥区');
INSERT INTO `sys_regions` VALUES ('15945', '31', '3117', '阿拉尔市');
INSERT INTO `sys_regions` VALUES ('15946', '31', '3118', '图木舒克市');
INSERT INTO `sys_regions` VALUES ('15947', '15946', '311801', '图木舒克市');
INSERT INTO `sys_regions` VALUES ('15948', '15945', '311701', '阿拉尔市');
INSERT INTO `sys_regions` VALUES ('16899', '2509', '280502', '长城区');
INSERT INTO `sys_regions` VALUES ('16923', '2509', '280503', '镜铁区');
INSERT INTO `sys_regions` VALUES ('18317', '1836', '210305', '安源区');
INSERT INTO `sys_regions` VALUES ('18374', '870', '110809', '康巴什新区');
INSERT INTO `sys_regions` VALUES ('18375', '1140', '140808', '大观区');
INSERT INTO `sys_regions` VALUES ('18376', '1140', '140809', '宜秀区');
INSERT INTO `sys_regions` VALUES ('18377', '1140', '140810', '迎江区');
INSERT INTO `sys_regions` VALUES ('18549', '1132', '140605', '淮上区');
INSERT INTO `sys_regions` VALUES ('18550', '1132', '140606', '龙子湖区');
INSERT INTO `sys_regions` VALUES ('18551', '1132', '140607', '禹会区');
INSERT INTO `sys_regions` VALUES ('18627', '1174', '141204', '谯城区');
INSERT INTO `sys_regions` VALUES ('18714', '1201', '141404', '贵池区');
INSERT INTO `sys_regions` VALUES ('18715', '1159', '141007', '琅琊区');
INSERT INTO `sys_regions` VALUES ('18716', '1159', '141008', '天长市');
INSERT INTO `sys_regions` VALUES ('18912', '1206', '141505', '金安区');
INSERT INTO `sys_regions` VALUES ('18913', '1206', '141506', '裕安区');
INSERT INTO `sys_regions` VALUES ('19158', '1167', '141107', '颍泉区');
INSERT INTO `sys_regions` VALUES ('19159', '1167', '141108', '颍州区');
INSERT INTO `sys_regions` VALUES ('19160', '1167', '141109', '颍东区');
INSERT INTO `sys_regions` VALUES ('19223', '1124', '140401', '杜集区');
INSERT INTO `sys_regions` VALUES ('19224', '1124', '140402', '烈山区');
INSERT INTO `sys_regions` VALUES ('19225', '1124', '140403', '濉溪县');
INSERT INTO `sys_regions` VALUES ('19226', '1124', '140404', '相山区');
INSERT INTO `sys_regions` VALUES ('19227', '1151', '140906', '徽州区');
INSERT INTO `sys_regions` VALUES ('19228', '1151', '140907', '屯溪区');
INSERT INTO `sys_regions` VALUES ('19377', '1677', '191505', '赤坎区');
INSERT INTO `sys_regions` VALUES ('19378', '1677', '191506', '霞山区');
INSERT INTO `sys_regions` VALUES ('19379', '1677', '191507', '经济技术开发区');
INSERT INTO `sys_regions` VALUES ('19380', '1677', '191508', '麻章区');
INSERT INTO `sys_regions` VALUES ('19381', '1677', '191509', '遂溪县');
INSERT INTO `sys_regions` VALUES ('19382', '1677', '191510', '廉江市');
INSERT INTO `sys_regions` VALUES ('19465', '1684', '191602', '茂南区');
INSERT INTO `sys_regions` VALUES ('19466', '1684', '191603', '电白县');
INSERT INTO `sys_regions` VALUES ('19467', '1684', '191604', '高州市');
INSERT INTO `sys_regions` VALUES ('19468', '1684', '191605', '化州市');
INSERT INTO `sys_regions` VALUES ('19469', '1684', '191606', '茂港区');
INSERT INTO `sys_regions` VALUES ('19575', '1180', '141306', '埇桥区');
INSERT INTO `sys_regions` VALUES ('19684', '2971', '141607', '宣州区');
INSERT INTO `sys_regions` VALUES ('19784', '1114', '140101', '郊区');
INSERT INTO `sys_regions` VALUES ('19786', '1114', '140102', '义安区');
INSERT INTO `sys_regions` VALUES ('19827', '1672', '191403', '江城区');
INSERT INTO `sys_regions` VALUES ('19828', '1672', '191404', '阳东县');
INSERT INTO `sys_regions` VALUES ('19829', '1698', '191804', '云城区');
INSERT INTO `sys_regions` VALUES ('19830', '1698', '191805', '罗定市');
INSERT INTO `sys_regions` VALUES ('19915', '134', '041328', '新立镇');
INSERT INTO `sys_regions` VALUES ('19916', '1611', '190402', '龙湖区');
INSERT INTO `sys_regions` VALUES ('19917', '1611', '190403', '金平区');
INSERT INTO `sys_regions` VALUES ('19918', '1611', '190404', '澄海区');
INSERT INTO `sys_regions` VALUES ('19919', '1611', '190405', '潮阳区');
INSERT INTO `sys_regions` VALUES ('19920', '1611', '190406', '潮南区');
INSERT INTO `sys_regions` VALUES ('19921', '1611', '190407', '濠江区');
INSERT INTO `sys_regions` VALUES ('19991', '1705', '192003', '湘桥区');
INSERT INTO `sys_regions` VALUES ('19992', '1705', '192004', '潮安县');
INSERT INTO `sys_regions` VALUES ('20051', '1650', '190903', '城区');
INSERT INTO `sys_regions` VALUES ('20052', '1650', '190904', '陆丰市');
INSERT INTO `sys_regions` VALUES ('20093', '1709', '192105', '榕城区');
INSERT INTO `sys_regions` VALUES ('20094', '1709', '192106', '揭东县');
INSERT INTO `sys_regions` VALUES ('20171', '593', '080602', '元宝区');
INSERT INTO `sys_regions` VALUES ('20172', '593', '080603', '振兴区');
INSERT INTO `sys_regions` VALUES ('20173', '593', '080604', '振安区');
INSERT INTO `sys_regions` VALUES ('20174', '593', '080605', '东港市');
INSERT INTO `sys_regions` VALUES ('20175', '593', '080606', '凤城市');
INSERT INTO `sys_regions` VALUES ('20183', '609', '080906', '鲅鱼圈区');
INSERT INTO `sys_regions` VALUES ('20348', '632', '081307', '双塔区');
INSERT INTO `sys_regions` VALUES ('20524', '604', '080805', '连山区');
INSERT INTO `sys_regions` VALUES ('20525', '604', '080806', '兴城市');
INSERT INTO `sys_regions` VALUES ('20658', '617', '081105', '海州区');
INSERT INTO `sys_regions` VALUES ('20659', '617', '081106', '太平区');
INSERT INTO `sys_regions` VALUES ('20660', '617', '081107', '细河区');
INSERT INTO `sys_regions` VALUES ('20661', '613', '081003', '兴隆台区');
INSERT INTO `sys_regions` VALUES ('20662', '613', '081004', '双台子区');
INSERT INTO `sys_regions` VALUES ('20817', '2336', '251210', '楚雄市');
INSERT INTO `sys_regions` VALUES ('20818', '2347', '251312', '大理市');
INSERT INTO `sys_regions` VALUES ('21033', '2304', '250805', '古城区');
INSERT INTO `sys_regions` VALUES ('21034', '2247', '250209', '麒麟区');
INSERT INTO `sys_regions` VALUES ('21035', '2155', '240313', '红花岗区');
INSERT INTO `sys_regions` VALUES ('21036', '2155', '240314', '汇川区');
INSERT INTO `sys_regions` VALUES ('21037', '2144', '240108', '云岩区');
INSERT INTO `sys_regions` VALUES ('21038', '2144', '240109', '花溪区');
INSERT INTO `sys_regions` VALUES ('21039', '2144', '240110', '小河区');
INSERT INTO `sys_regions` VALUES ('21644', '2501', '280406', '麦积区');
INSERT INTO `sys_regions` VALUES ('21645', '2501', '280407', '秦州区');
INSERT INTO `sys_regions` VALUES ('21646', '2487', '280106', '七里河区');
INSERT INTO `sys_regions` VALUES ('21647', '2487', '280107', '安宁区');
INSERT INTO `sys_regions` VALUES ('21648', '2487', '280108', '城关区');
INSERT INTO `sys_regions` VALUES ('21649', '2628', '300104', '兴庆区');
INSERT INTO `sys_regions` VALUES ('21650', '2628', '300105', '金凤区');
INSERT INTO `sys_regions` VALUES ('21651', '2628', '300106', '西夏区');
INSERT INTO `sys_regions` VALUES ('21652', '2580', '290104', '城中区');
INSERT INTO `sys_regions` VALUES ('21653', '2580', '290105', '城东区');
INSERT INTO `sys_regions` VALUES ('21654', '2580', '290106', '城西区');
INSERT INTO `sys_regions` VALUES ('21655', '2580', '290107', '城北区');
INSERT INTO `sys_regions` VALUES ('22043', '1280', '150909', '莲都区');
INSERT INTO `sys_regions` VALUES ('22044', '1273', '150805', '柯城区');
INSERT INTO `sys_regions` VALUES ('22045', '1273', '150806', '衢江区');
INSERT INTO `sys_regions` VALUES ('22046', '1290', '151005', '黄岩区');
INSERT INTO `sys_regions` VALUES ('22047', '1290', '151006', '椒江区');
INSERT INTO `sys_regions` VALUES ('22048', '1290', '151007', '路桥区');
INSERT INTO `sys_regions` VALUES ('22049', '1290', '151008', '温岭市');
INSERT INTO `sys_regions` VALUES ('22050', '1290', '151009', '玉环县');
INSERT INTO `sys_regions` VALUES ('22463', '1317', '160311', '梅列区');
INSERT INTO `sys_regions` VALUES ('22464', '1317', '160312', '三元区');
INSERT INTO `sys_regions` VALUES ('22465', '1352', '160710', '延平区');
INSERT INTO `sys_regions` VALUES ('22466', '2121', '230101', '秀英区');
INSERT INTO `sys_regions` VALUES ('22467', '2121', '230102', '龙华区');
INSERT INTO `sys_regions` VALUES ('22468', '2121', '230103', '琼山区');
INSERT INTO `sys_regions` VALUES ('22469', '2121', '230104', '美兰区');
INSERT INTO `sys_regions` VALUES ('22470', '3690', '230607', '河西区');
INSERT INTO `sys_regions` VALUES ('22471', '3690', '230608', '河东区');
INSERT INTO `sys_regions` VALUES ('22503', '3690', '230609', '南田农场');
INSERT INTO `sys_regions` VALUES ('22504', '3690', '230610', '南新农场');
INSERT INTO `sys_regions` VALUES ('22505', '3690', '230611', '南岛农场');
INSERT INTO `sys_regions` VALUES ('22506', '3690', '230612', '立才农场');
INSERT INTO `sys_regions` VALUES ('22507', '3690', '230613', '南滨农场');
INSERT INTO `sys_regions` VALUES ('22846', '51046', '031201', '杨村镇、下朱庄内');
INSERT INTO `sys_regions` VALUES ('22847', '51046', '031202', '其它地区');
INSERT INTO `sys_regions` VALUES ('22848', '51051', '031701', '城关镇、马家店开发区、天宝工业园');
INSERT INTO `sys_regions` VALUES ('22849', '51051', '031702', '其它地区');
INSERT INTO `sys_regions` VALUES ('22850', '1792', '201004', '平桂管理区');
INSERT INTO `sys_regions` VALUES ('22851', '1746', '200503', '海城区');
INSERT INTO `sys_regions` VALUES ('22852', '1746', '200504', '银海区');
INSERT INTO `sys_regions` VALUES ('22883', '1726', '200315', '秀峰区');
INSERT INTO `sys_regions` VALUES ('22884', '1726', '200316', '叠彩区');
INSERT INTO `sys_regions` VALUES ('22885', '1726', '200317', '七星区');
INSERT INTO `sys_regions` VALUES ('22906', '1720', '200207', '鱼峰区');
INSERT INTO `sys_regions` VALUES ('22907', '1720', '200208', '城中区');
INSERT INTO `sys_regions` VALUES ('22908', '1720', '200209', '柳南区');
INSERT INTO `sys_regions` VALUES ('22909', '1720', '200210', '柳北区');
INSERT INTO `sys_regions` VALUES ('23037', '1740', '200405', '万秀区');
INSERT INTO `sys_regions` VALUES ('23038', '1740', '200406', '蝶山区');
INSERT INTO `sys_regions` VALUES ('23039', '1740', '200407', '长洲区');
INSERT INTO `sys_regions` VALUES ('23040', '1792', '201005', '八步区');
INSERT INTO `sys_regions` VALUES ('23282', '1396', '170307', '樊城区');
INSERT INTO `sys_regions` VALUES ('23283', '1396', '170308', '襄城区');
INSERT INTO `sys_regions` VALUES ('23284', '1396', '170309', '襄州区');
INSERT INTO `sys_regions` VALUES ('23429', '1405', '170407', '茅箭区');
INSERT INTO `sys_regions` VALUES ('23430', '1405', '170408', '张湾区');
INSERT INTO `sys_regions` VALUES ('23585', '2922', '171401', '园林');
INSERT INTO `sys_regions` VALUES ('23586', '2922', '171402', '杨市');
INSERT INTO `sys_regions` VALUES ('23587', '2922', '171403', '周矶');
INSERT INTO `sys_regions` VALUES ('23588', '2922', '171404', '广华');
INSERT INTO `sys_regions` VALUES ('23589', '2922', '171405', '泰丰');
INSERT INTO `sys_regions` VALUES ('23590', '2922', '171406', '竹根滩镇');
INSERT INTO `sys_regions` VALUES ('23591', '2922', '171407', '高石碑镇');
INSERT INTO `sys_regions` VALUES ('23592', '2922', '171408', '积玉口镇');
INSERT INTO `sys_regions` VALUES ('23593', '2922', '171409', '渔洋镇');
INSERT INTO `sys_regions` VALUES ('23594', '2922', '171410', '王场镇');
INSERT INTO `sys_regions` VALUES ('23595', '2922', '171411', '熊口镇');
INSERT INTO `sys_regions` VALUES ('23596', '2922', '171412', '老新镇');
INSERT INTO `sys_regions` VALUES ('23597', '2922', '171413', '浩口镇');
INSERT INTO `sys_regions` VALUES ('23598', '2922', '171414', '张金镇');
INSERT INTO `sys_regions` VALUES ('23599', '2922', '171415', '龙湾镇');
INSERT INTO `sys_regions` VALUES ('23600', '2922', '171416', '江汉石油管理局');
INSERT INTO `sys_regions` VALUES ('23601', '2922', '171417', '潜江经济开发区');
INSERT INTO `sys_regions` VALUES ('23602', '2922', '171418', '西大垸管理区');
INSERT INTO `sys_regions` VALUES ('23603', '2922', '171419', '运粮湖管理区');
INSERT INTO `sys_regions` VALUES ('23604', '2922', '171420', '周矶管理区');
INSERT INTO `sys_regions` VALUES ('23605', '2922', '171421', '后湖管理区');
INSERT INTO `sys_regions` VALUES ('23606', '2922', '171422', '熊口管理区');
INSERT INTO `sys_regions` VALUES ('23607', '2922', '171423', '总口管理区');
INSERT INTO `sys_regions` VALUES ('23608', '2922', '171424', '高场原种场');
INSERT INTO `sys_regions` VALUES ('23609', '2922', '171425', '浩口原种场');
INSERT INTO `sys_regions` VALUES ('23610', '3154', '171701', '松柏镇');
INSERT INTO `sys_regions` VALUES ('23611', '3154', '171702', '阳日镇');
INSERT INTO `sys_regions` VALUES ('23612', '3154', '171703', '木鱼镇');
INSERT INTO `sys_regions` VALUES ('23613', '3154', '171704', '红坪镇');
INSERT INTO `sys_regions` VALUES ('23614', '3154', '171705', '新华镇');
INSERT INTO `sys_regions` VALUES ('23615', '3154', '171706', '宋洛乡');
INSERT INTO `sys_regions` VALUES ('23616', '3154', '171707', '九湖乡');
INSERT INTO `sys_regions` VALUES ('23617', '3154', '171708', '下谷坪乡');
INSERT INTO `sys_regions` VALUES ('23618', '2980', '171501', '侨乡街道开发区');
INSERT INTO `sys_regions` VALUES ('23619', '2980', '171502', '竟陵街道');
INSERT INTO `sys_regions` VALUES ('23620', '2980', '171503', '杨林街道');
INSERT INTO `sys_regions` VALUES ('23621', '2980', '171504', '佛子山镇');
INSERT INTO `sys_regions` VALUES ('23622', '2980', '171505', '多宝镇');
INSERT INTO `sys_regions` VALUES ('23623', '2980', '171506', '拖市镇');
INSERT INTO `sys_regions` VALUES ('23624', '2980', '171507', '张港镇');
INSERT INTO `sys_regions` VALUES ('23625', '2980', '171508', '蒋场镇');
INSERT INTO `sys_regions` VALUES ('23626', '2980', '171509', '汪场镇');
INSERT INTO `sys_regions` VALUES ('23627', '2980', '171510', '渔薪镇');
INSERT INTO `sys_regions` VALUES ('23628', '2980', '171511', '黄潭镇');
INSERT INTO `sys_regions` VALUES ('23629', '2980', '171512', '岳口镇');
INSERT INTO `sys_regions` VALUES ('23630', '2980', '171513', '横林镇');
INSERT INTO `sys_regions` VALUES ('23631', '2980', '171514', '彭市镇');
INSERT INTO `sys_regions` VALUES ('23632', '2980', '171515', '麻洋镇');
INSERT INTO `sys_regions` VALUES ('23633', '2980', '171516', '多祥镇');
INSERT INTO `sys_regions` VALUES ('23634', '2980', '171517', '干驿镇');
INSERT INTO `sys_regions` VALUES ('23635', '2980', '171518', '马湾镇');
INSERT INTO `sys_regions` VALUES ('23636', '2980', '171519', '卢市镇');
INSERT INTO `sys_regions` VALUES ('23637', '2980', '171520', '小板镇');
INSERT INTO `sys_regions` VALUES ('23638', '2980', '171521', '九真镇');
INSERT INTO `sys_regions` VALUES ('23639', '2980', '171522', '皂市镇');
INSERT INTO `sys_regions` VALUES ('23640', '2980', '171523', '胡市镇');
INSERT INTO `sys_regions` VALUES ('23641', '2980', '171524', '石河镇');
INSERT INTO `sys_regions` VALUES ('23642', '2980', '171525', '净潭乡');
INSERT INTO `sys_regions` VALUES ('23643', '2980', '171526', '蒋湖农场');
INSERT INTO `sys_regions` VALUES ('23644', '2980', '171527', '白茅湖农场');
INSERT INTO `sys_regions` VALUES ('23645', '2980', '171528', '沉湖管委会');
INSERT INTO `sys_regions` VALUES ('23649', '2983', '171601', '郑场镇');
INSERT INTO `sys_regions` VALUES ('23650', '2983', '171602', '毛嘴镇');
INSERT INTO `sys_regions` VALUES ('23651', '2983', '171603', '豆河镇');
INSERT INTO `sys_regions` VALUES ('23652', '2983', '171604', '三伏潭镇');
INSERT INTO `sys_regions` VALUES ('23653', '2983', '171605', '胡场镇');
INSERT INTO `sys_regions` VALUES ('23654', '2983', '171606', '长埫口镇');
INSERT INTO `sys_regions` VALUES ('23655', '2983', '171607', '西流河镇');
INSERT INTO `sys_regions` VALUES ('23656', '2983', '171608', '沙湖镇');
INSERT INTO `sys_regions` VALUES ('23657', '2983', '171609', '杨林尾镇');
INSERT INTO `sys_regions` VALUES ('23658', '2983', '171610', '彭场镇');
INSERT INTO `sys_regions` VALUES ('23659', '2983', '171611', '张沟镇');
INSERT INTO `sys_regions` VALUES ('23660', '2983', '171612', '郭河镇');
INSERT INTO `sys_regions` VALUES ('23661', '2983', '171613', '沔城镇');
INSERT INTO `sys_regions` VALUES ('23662', '2983', '171614', '通海口镇');
INSERT INTO `sys_regions` VALUES ('23663', '2983', '171615', '陈场镇');
INSERT INTO `sys_regions` VALUES ('23664', '2983', '171616', '工业园区');
INSERT INTO `sys_regions` VALUES ('23665', '2983', '171617', '九合垸原种场');
INSERT INTO `sys_regions` VALUES ('23666', '2983', '171618', '沙湖原种场');
INSERT INTO `sys_regions` VALUES ('23667', '2983', '171619', '排湖渔场');
INSERT INTO `sys_regions` VALUES ('23668', '2983', '171620', '五湖渔场');
INSERT INTO `sys_regions` VALUES ('23669', '2983', '171621', '赵西垸林场');
INSERT INTO `sys_regions` VALUES ('23670', '2983', '171622', '刘家垸林场');
INSERT INTO `sys_regions` VALUES ('23671', '2983', '171623', '畜禽良种场');
INSERT INTO `sys_regions` VALUES ('23672', '51048', '031401', '汉沽区街里、汉沽开发区');
INSERT INTO `sys_regions` VALUES ('23673', '51048', '031402', '其它地区');
INSERT INTO `sys_regions` VALUES ('23674', '51052', '031801', '芦台镇、经济开发区、贸易开发区');
INSERT INTO `sys_regions` VALUES ('23675', '51052', '031802', '其它地区');
INSERT INTO `sys_regions` VALUES ('23678', '1845', '210512', '经济技术开发区');
INSERT INTO `sys_regions` VALUES ('23679', '1845', '210513', '八里湖新区');
INSERT INTO `sys_regions` VALUES ('23680', '1845', '210514', '庐山风景名胜区');
INSERT INTO `sys_regions` VALUES ('23681', '939', '120607', '射阳县');
INSERT INTO `sys_regions` VALUES ('23682', '939', '120608', '亭湖区');
INSERT INTO `sys_regions` VALUES ('23683', '939', '120609', '盐都区');
INSERT INTO `sys_regions` VALUES ('23684', '919', '120306', '海州区');
INSERT INTO `sys_regions` VALUES ('23686', '911', '120209', '鼓楼区');
INSERT INTO `sys_regions` VALUES ('23687', '911', '120210', '邳州市');
INSERT INTO `sys_regions` VALUES ('23688', '911', '120211', '泉山区');
INSERT INTO `sys_regions` VALUES ('23689', '911', '120212', '新沂市');
INSERT INTO `sys_regions` VALUES ('23690', '911', '120213', '云龙区');
INSERT INTO `sys_regions` VALUES ('24069', '644', '090206', '昌邑区');
INSERT INTO `sys_regions` VALUES ('24070', '644', '090207', '龙潭区');
INSERT INTO `sys_regions` VALUES ('24071', '644', '090208', '船营区');
INSERT INTO `sys_regions` VALUES ('24072', '644', '090209', '丰满区');
INSERT INTO `sys_regions` VALUES ('24073', '687', '090808', '延吉市');
INSERT INTO `sys_regions` VALUES ('24074', '664', '090506', '浑江区');
INSERT INTO `sys_regions` VALUES ('24075', '674', '090604', '宁江区');
INSERT INTO `sys_regions` VALUES ('24076', '674', '090605', '前郭县');
INSERT INTO `sys_regions` VALUES ('24463', '2144', '240111', '观山湖区');
INSERT INTO `sys_regions` VALUES ('24946', '1885', '210911', '临川区');
INSERT INTO `sys_regions` VALUES ('24947', '1832', '210204', '昌江区');
INSERT INTO `sys_regions` VALUES ('25188', '1761', '200906', '玉州区');
INSERT INTO `sys_regions` VALUES ('25189', '1753', '200704', '钦南区');
INSERT INTO `sys_regions` VALUES ('25190', '1749', '200603', '防城区');
INSERT INTO `sys_regions` VALUES ('25191', '1749', '200604', '港口区');
INSERT INTO `sys_regions` VALUES ('25192', '1757', '200804', '港南区');
INSERT INTO `sys_regions` VALUES ('25193', '1757', '200805', '港北区');
INSERT INTO `sys_regions` VALUES ('25481', '1845', '210515', '庐山区');
INSERT INTO `sys_regions` VALUES ('25482', '1845', '210516', '浔阳区');
INSERT INTO `sys_regions` VALUES ('25704', '51047', '031301', '咸水沽镇、海河教育园，海河科技园');
INSERT INTO `sys_regions` VALUES ('25708', '51044', '031001', '全境');
INSERT INTO `sys_regions` VALUES ('25711', '51045', '031101', '其它地区');
INSERT INTO `sys_regions` VALUES ('25712', '51045', '031102', '杨柳青,中北,精武,大寺镇,环外海泰及外环内');
INSERT INTO `sys_regions` VALUES ('25713', '2900', '131710', '邹城市');
INSERT INTO `sys_regions` VALUES ('25714', '2900', '131711', '市中区');
INSERT INTO `sys_regions` VALUES ('25715', '2900', '131712', '曲阜市');
INSERT INTO `sys_regions` VALUES ('25728', '2900', '131713', '高新区');
INSERT INTO `sys_regions` VALUES ('25877', '1090', '131307', '北海新区');
INSERT INTO `sys_regions` VALUES ('25878', '1090', '131308', '滨城区');
INSERT INTO `sys_regions` VALUES ('25879', '1060', '131011', '齐河县');
INSERT INTO `sys_regions` VALUES ('25880', '1081', '131208', '东昌府区');
INSERT INTO `sys_regions` VALUES ('26449', '1861', '210711', '信州区');
INSERT INTO `sys_regions` VALUES ('26450', '1861', '210712', '上饶县');
INSERT INTO `sys_regions` VALUES ('26451', '1911', '211118', '章贡区');
INSERT INTO `sys_regions` VALUES ('26452', '1874', '210810', '袁州区');
INSERT INTO `sys_regions` VALUES ('26453', '1898', '211012', '青原区');
INSERT INTO `sys_regions` VALUES ('26454', '1898', '211013', '吉州区');
INSERT INTO `sys_regions` VALUES ('26455', '1842', '210402', '渝水区');
INSERT INTO `sys_regions` VALUES ('27497', '2235', '250114', '呈贡区');
INSERT INTO `sys_regions` VALUES ('27498', '2070', '221913', '汶川县');
INSERT INTO `sys_regions` VALUES ('27499', '1977', '220707', '利州区');
INSERT INTO `sys_regions` VALUES ('27500', '2103', '222117', '西昌市');
INSERT INTO `sys_regions` VALUES ('27502', '1950', '220305', '东区');
INSERT INTO `sys_regions` VALUES ('28920', '1108', '131505', '东港区');
INSERT INTO `sys_regions` VALUES ('28921', '1032', '130609', '潍城区');
INSERT INTO `sys_regions` VALUES ('28922', '1032', '130610', '奎文区');
INSERT INTO `sys_regions` VALUES ('28923', '1032', '130611', '高新区');
INSERT INTO `sys_regions` VALUES ('28924', '1032', '130612', '寒亭区');
INSERT INTO `sys_regions` VALUES ('28925', '1032', '130613', '寿光市');
INSERT INTO `sys_regions` VALUES ('28926', '1053', '130802', '环翠区');
INSERT INTO `sys_regions` VALUES ('28928', '1053', '130803', '荣成市');
INSERT INTO `sys_regions` VALUES ('28929', '1053', '130804', '文登市');
INSERT INTO `sys_regions` VALUES ('28930', '1072', '131110', '兰山区');
INSERT INTO `sys_regions` VALUES ('28931', '1072', '131111', '河东区');
INSERT INTO `sys_regions` VALUES ('28932', '1022', '130406', '滕州市');
INSERT INTO `sys_regions` VALUES ('29444', '1488', '180206', '天元区');
INSERT INTO `sys_regions` VALUES ('29445', '1488', '180207', '石峰区');
INSERT INTO `sys_regions` VALUES ('29446', '1488', '180208', '芦淞区');
INSERT INTO `sys_regions` VALUES ('29447', '1488', '180209', '荷塘区');
INSERT INTO `sys_regions` VALUES ('29448', '1495', '180304', '雨湖区');
INSERT INTO `sys_regions` VALUES ('29449', '1495', '180305', '岳塘区');
INSERT INTO `sys_regions` VALUES ('29450', '1501', '180409', '蒸湘区');
INSERT INTO `sys_regions` VALUES ('29451', '1501', '180410', '石鼓区');
INSERT INTO `sys_regions` VALUES ('29452', '1501', '180411', '珠晖区');
INSERT INTO `sys_regions` VALUES ('29453', '1501', '180412', '雁峰区');
INSERT INTO `sys_regions` VALUES ('29454', '1560', '181111', '冷水滩区');
INSERT INTO `sys_regions` VALUES ('29455', '1574', '181212', '鹤城区');
INSERT INTO `sys_regions` VALUES ('29456', '1586', '181305', '娄星区');
INSERT INTO `sys_regions` VALUES ('29457', '1511', '180510', '大祥区');
INSERT INTO `sys_regions` VALUES ('29458', '1511', '180511', '双清区');
INSERT INTO `sys_regions` VALUES ('29459', '1511', '180512', '北塔区');
INSERT INTO `sys_regions` VALUES ('29460', '1522', '180609', '岳阳楼区');
INSERT INTO `sys_regions` VALUES ('29461', '1530', '180708', '鼎城区');
INSERT INTO `sys_regions` VALUES ('29462', '1530', '180709', '武陵区');
INSERT INTO `sys_regions` VALUES ('29463', '1555', '181005', '赫山区');
INSERT INTO `sys_regions` VALUES ('29464', '1555', '181006', '资阳区');
INSERT INTO `sys_regions` VALUES ('29465', '1544', '180910', '北湖区');
INSERT INTO `sys_regions` VALUES ('29466', '1544', '180911', '苏仙区');
INSERT INTO `sys_regions` VALUES ('31523', '2428', '270613', '宝塔区');
INSERT INTO `sys_regions` VALUES ('31680', '2454', '270812', '榆阳区');
INSERT INTO `sys_regions` VALUES ('31864', '2442', '270711', '汉台区');
INSERT INTO `sys_regions` VALUES ('32060', '139', '041825', '坪山镇');
INSERT INTO `sys_regions` VALUES ('32061', '309', '060211', '城区');
INSERT INTO `sys_regions` VALUES ('32206', '379', '060917', '尧都区');
INSERT INTO `sys_regions` VALUES ('32360', '398', '061013', '盐湖区');
INSERT INTO `sys_regions` VALUES ('32505', '3074', '061113', '城区');
INSERT INTO `sys_regions` VALUES ('32652', '799', '110108', '回民区');
INSERT INTO `sys_regions` VALUES ('32653', '799', '110109', '新城区');
INSERT INTO `sys_regions` VALUES ('32769', '902', '111208', '科尔沁区');
INSERT INTO `sys_regions` VALUES ('32937', '812', '110412', '红山区');
INSERT INTO `sys_regions` VALUES ('33163', '737', '100509', '鸡冠区');
INSERT INTO `sys_regions` VALUES ('33269', '765', '100907', '前进区');
INSERT INTO `sys_regions` VALUES ('33270', '765', '100908', '向阳区');
INSERT INTO `sys_regions` VALUES ('33271', '765', '100909', '东风区');
INSERT INTO `sys_regions` VALUES ('33272', '765', '100910', '郊区');
INSERT INTO `sys_regions` VALUES ('33404', '712', '100214', '建华区');
INSERT INTO `sys_regions` VALUES ('33405', '712', '100215', '龙沙区');
INSERT INTO `sys_regions` VALUES ('33406', '712', '100216', '铁锋区');
INSERT INTO `sys_regions` VALUES ('34049', '148', '050216', '丛台区');
INSERT INTO `sys_regions` VALUES ('34050', '148', '050217', '邯山区');
INSERT INTO `sys_regions` VALUES ('34051', '148', '050218', '复兴区');
INSERT INTO `sys_regions` VALUES ('34052', '148', '050219', '武安市');
INSERT INTO `sys_regions` VALUES ('34298', '224', '050516', '桥西区');
INSERT INTO `sys_regions` VALUES ('34299', '224', '050517', '桥东区');
INSERT INTO `sys_regions` VALUES ('34544', '2805', '010704', '五环到六环之间');
INSERT INTO `sys_regions` VALUES ('34545', '2805', '010705', '六环之外');
INSERT INTO `sys_regions` VALUES ('34548', '549', '071709', '平桥区');
INSERT INTO `sys_regions` VALUES ('34549', '549', '071710', '浉河区');
INSERT INTO `sys_regions` VALUES ('34751', '517', '071408', '睢阳区');
INSERT INTO `sys_regions` VALUES ('34752', '517', '071409', '梁园区');
INSERT INTO `sys_regions` VALUES ('34926', '527', '071510', '东新区');
INSERT INTO `sys_regions` VALUES ('34927', '527', '071511', '经济开发区');
INSERT INTO `sys_regions` VALUES ('35108', '527', '071512', '川汇区');
INSERT INTO `sys_regions` VALUES ('35178', '2780', '071801', '五龙口镇');
INSERT INTO `sys_regions` VALUES ('35179', '2780', '071802', '下冶镇');
INSERT INTO `sys_regions` VALUES ('35180', '2780', '071803', '轵城镇');
INSERT INTO `sys_regions` VALUES ('35181', '2780', '071804', '王屋镇');
INSERT INTO `sys_regions` VALUES ('35182', '2780', '071805', '思礼镇');
INSERT INTO `sys_regions` VALUES ('35183', '2780', '071806', '邵原镇');
INSERT INTO `sys_regions` VALUES ('35184', '2780', '071807', '坡头镇');
INSERT INTO `sys_regions` VALUES ('35185', '2780', '071808', '梨林镇');
INSERT INTO `sys_regions` VALUES ('35186', '2780', '071809', '克井镇');
INSERT INTO `sys_regions` VALUES ('35187', '2780', '071810', '大峪镇');
INSERT INTO `sys_regions` VALUES ('35188', '2780', '071811', '承留镇');
INSERT INTO `sys_regions` VALUES ('35189', '538', '071610', '驿城区');
INSERT INTO `sys_regions` VALUES ('35470', '468', '070806', '龙安区');
INSERT INTO `sys_regions` VALUES ('35471', '468', '070807', '殷都区');
INSERT INTO `sys_regions` VALUES ('35472', '468', '070808', '文峰区');
INSERT INTO `sys_regions` VALUES ('35473', '468', '070809', '开发区');
INSERT INTO `sys_regions` VALUES ('35474', '468', '070810', '北关区');
INSERT INTO `sys_regions` VALUES ('35591', '454', '070605', '淇滨区');
INSERT INTO `sys_regions` VALUES ('35637', '495', '071206', '湖滨区');
INSERT INTO `sys_regions` VALUES ('35751', '502', '071312', '卧龙区');
INSERT INTO `sys_regions` VALUES ('35752', '502', '071313', '宛城区');
INSERT INTO `sys_regions` VALUES ('35965', '438', '070408', '湛河区');
INSERT INTO `sys_regions` VALUES ('35966', '438', '070409', '卫东区');
INSERT INTO `sys_regions` VALUES ('35967', '438', '070410', '新华区');
INSERT INTO `sys_regions` VALUES ('36102', '1655', '191031', '东城区');
INSERT INTO `sys_regions` VALUES ('36157', '51042', '030801', '全境');
INSERT INTO `sys_regions` VALUES ('36167', '51050', '031602', '外环外双街镇，河北工大新校，屈店工业园');
INSERT INTO `sys_regions` VALUES ('36168', '51050', '031603', '外环外其它地区');
INSERT INTO `sys_regions` VALUES ('36171', '51047', '031302', '双港，辛庄');
INSERT INTO `sys_regions` VALUES ('36172', '51047', '031303', '其他地区');
INSERT INTO `sys_regions` VALUES ('36173', '1116', '140213', '肥西县');
INSERT INTO `sys_regions` VALUES ('36174', '1643', '190802', '惠阳区');
INSERT INTO `sys_regions` VALUES ('36175', '1643', '190803', '大亚湾区');
INSERT INTO `sys_regions` VALUES ('36176', '1643', '190804', '惠城区');
INSERT INTO `sys_regions` VALUES ('36177', '1643', '190805', '惠东县');
INSERT INTO `sys_regions` VALUES ('36178', '1643', '190806', '博罗县');
INSERT INTO `sys_regions` VALUES ('36264', '1666', '191302', '禅城区');
INSERT INTO `sys_regions` VALUES ('36265', '1666', '191303', '高明区');
INSERT INTO `sys_regions` VALUES ('36266', '1666', '191304', '三水区');
INSERT INTO `sys_regions` VALUES ('36267', '1666', '191305', '南海区');
INSERT INTO `sys_regions` VALUES ('36315', '2005', '221110', '翠屏区');
INSERT INTO `sys_regions` VALUES ('36560', '925', '120406', '清河区');
INSERT INTO `sys_regions` VALUES ('36561', '925', '120407', '淮阴区');
INSERT INTO `sys_regions` VALUES ('36562', '925', '120408', '清浦区');
INSERT INTO `sys_regions` VALUES ('36563', '925', '120409', '涟水县');
INSERT INTO `sys_regions` VALUES ('36684', '2652', '310105', '天山区');
INSERT INTO `sys_regions` VALUES ('36685', '2652', '310106', '新市区');
INSERT INTO `sys_regions` VALUES ('36686', '2652', '310107', '沙依巴克区');
INSERT INTO `sys_regions` VALUES ('36687', '2652', '310108', '水磨沟区');
INSERT INTO `sys_regions` VALUES ('36780', '303', '060105', '小店区');
INSERT INTO `sys_regions` VALUES ('36781', '303', '060106', '迎泽区');
INSERT INTO `sys_regions` VALUES ('36782', '303', '060107', '晋源区');
INSERT INTO `sys_regions` VALUES ('36783', '303', '060108', '万柏林区');
INSERT INTO `sys_regions` VALUES ('36784', '303', '060109', '尖草坪区');
INSERT INTO `sys_regions` VALUES ('36785', '303', '060110', '敦化坊街道');
INSERT INTO `sys_regions` VALUES ('36884', '1025', '130505', '东营区');
INSERT INTO `sys_regions` VALUES ('36936', '2022', '221304', '南部县');
INSERT INTO `sys_regions` VALUES ('36953', '1601', '190105', '番禺区');
INSERT INTO `sys_regions` VALUES ('36983', '1993', '221010', '市中区');
INSERT INTO `sys_regions` VALUES ('36984', '1993', '221011', '峨眉山市');
INSERT INTO `sys_regions` VALUES ('37258', '1659', '191201', '台山市');
INSERT INTO `sys_regions` VALUES ('37259', '1659', '191202', '新会区');
INSERT INTO `sys_regions` VALUES ('37260', '1659', '191203', '鹤山市');
INSERT INTO `sys_regions` VALUES ('37261', '1659', '191204', '江海区');
INSERT INTO `sys_regions` VALUES ('37262', '1659', '191205', '蓬江区');
INSERT INTO `sys_regions` VALUES ('37263', '1659', '191206', '开平市');
INSERT INTO `sys_regions` VALUES ('37264', '1659', '191207', '恩平市');
INSERT INTO `sys_regions` VALUES ('37371', '446', '070509', '马村区');
INSERT INTO `sys_regions` VALUES ('37372', '446', '070510', '中站区');
INSERT INTO `sys_regions` VALUES ('37456', '458', '070710', '牧野区');
INSERT INTO `sys_regions` VALUES ('37457', '458', '070711', '红旗区');
INSERT INTO `sys_regions` VALUES ('37458', '458', '070712', '卫滨区');
INSERT INTO `sys_regions` VALUES ('37581', '579', '080306', '铁西区');
INSERT INTO `sys_regions` VALUES ('37582', '579', '080307', '千山区');
INSERT INTO `sys_regions` VALUES ('37734', '1704', '191907', '清城区');
INSERT INTO `sys_regions` VALUES ('37735', '1704', '191908', '英德市');
INSERT INTO `sys_regions` VALUES ('37864', '1627', '190605', '源城区');
INSERT INTO `sys_regions` VALUES ('37865', '1627', '190606', '东源县');
INSERT INTO `sys_regions` VALUES ('37916', '1007', '130207', '即墨市');
INSERT INTO `sys_regions` VALUES ('37917', '1007', '130208', '城阳区');
INSERT INTO `sys_regions` VALUES ('37918', '1007', '130209', '崂山区');
INSERT INTO `sys_regions` VALUES ('37919', '1007', '130210', '胶州市');
INSERT INTO `sys_regions` VALUES ('37920', '1007', '130211', '平度市');
INSERT INTO `sys_regions` VALUES ('38094', '2416', '270511', '临渭区');
INSERT INTO `sys_regions` VALUES ('38251', '805', '110206', '东河区');
INSERT INTO `sys_regions` VALUES ('38252', '805', '110207', '九原区');
INSERT INTO `sys_regions` VALUES ('38253', '805', '110208', '青山区');
INSERT INTO `sys_regions` VALUES ('38254', '805', '110209', '昆都仑区');
INSERT INTO `sys_regions` VALUES ('38364', '965', '120907', '如皋市');
INSERT INTO `sys_regions` VALUES ('38365', '965', '120908', '海门市');
INSERT INTO `sys_regions` VALUES ('38366', '965', '120909', '启东市');
INSERT INTO `sys_regions` VALUES ('38517', '972', '121006', '丹阳市');
INSERT INTO `sys_regions` VALUES ('38518', '972', '121007', '句容市');
INSERT INTO `sys_regions` VALUES ('38573', '1960', '220507', '江油市');
INSERT INTO `sys_regions` VALUES ('38574', '1960', '220508', '涪城区');
INSERT INTO `sys_regions` VALUES ('38575', '1960', '220509', '游仙区');
INSERT INTO `sys_regions` VALUES ('38576', '1960', '220510', '高新区');
INSERT INTO `sys_regions` VALUES ('38577', '1960', '220511', '经开区');
INSERT INTO `sys_regions` VALUES ('38630', '639', '090106', '朝阳区');
INSERT INTO `sys_regions` VALUES ('38631', '639', '090107', '南关区');
INSERT INTO `sys_regions` VALUES ('38632', '639', '090108', '宽城区');
INSERT INTO `sys_regions` VALUES ('38633', '639', '090109', '二道区');
INSERT INTO `sys_regions` VALUES ('38634', '639', '090110', '绿园区');
INSERT INTO `sys_regions` VALUES ('38635', '639', '090111', '净月区');
INSERT INTO `sys_regions` VALUES ('38636', '639', '090112', '汽车产业开发区');
INSERT INTO `sys_regions` VALUES ('38637', '639', '090113', '高新技术开发区');
INSERT INTO `sys_regions` VALUES ('38638', '639', '090114', '经济技术开发区');
INSERT INTO `sys_regions` VALUES ('39010', '1962', '220603', '广汉市');
INSERT INTO `sys_regions` VALUES ('39011', '1962', '220604', '什邡市');
INSERT INTO `sys_regions` VALUES ('39012', '1962', '220605', '旌阳区');
INSERT INTO `sys_regions` VALUES ('39013', '1962', '220606', '绵竹市');
INSERT INTO `sys_regions` VALUES ('39014', '1954', '220406', '江阳区');
INSERT INTO `sys_regions` VALUES ('39015', '1954', '220407', '龙马潭区');
INSERT INTO `sys_regions` VALUES ('39461', '1655', '191032', '黄江镇');
INSERT INTO `sys_regions` VALUES ('39462', '1655', '191033', '虎门镇');
INSERT INTO `sys_regions` VALUES ('39620', '51035', '030101', '全境');
INSERT INTO `sys_regions` VALUES ('39628', '988', '121311', '吴江区');
INSERT INTO `sys_regions` VALUES ('39653', '1657', '191121', '沙朗镇');
INSERT INTO `sys_regions` VALUES ('39680', '115', '040333', '县城内');
INSERT INTO `sys_regions` VALUES ('39688', '123', '040520', '县城内');
INSERT INTO `sys_regions` VALUES ('39692', '129', '040827', '县城内');
INSERT INTO `sys_regions` VALUES ('39694', '130', '040931', '县城内');
INSERT INTO `sys_regions` VALUES ('39698', '131', '041030', '县城内');
INSERT INTO `sys_regions` VALUES ('39699', '132', '041133', '县城内');
INSERT INTO `sys_regions` VALUES ('39701', '133', '041241', '县城内');
INSERT INTO `sys_regions` VALUES ('39702', '134', '041329', '县城内');
INSERT INTO `sys_regions` VALUES ('39704', '135', '041430', '县城内');
INSERT INTO `sys_regions` VALUES ('39706', '136', '041527', '县城内');
INSERT INTO `sys_regions` VALUES ('39710', '137', '041632', '县城内');
INSERT INTO `sys_regions` VALUES ('39711', '138', '041739', '县城内');
INSERT INTO `sys_regions` VALUES ('39712', '139', '041826', '县城内');
INSERT INTO `sys_regions` VALUES ('39714', '140', '041940', '县城内');
INSERT INTO `sys_regions` VALUES ('39716', '141', '042032', '县城内');
INSERT INTO `sys_regions` VALUES ('39717', '4164', '042125', '县城内');
INSERT INTO `sys_regions` VALUES ('39723', '1690', '191707', '四会市');
INSERT INTO `sys_regions` VALUES ('39725', '1690', '191708', '高要市');
INSERT INTO `sys_regions` VALUES ('39884', '3701', '230914', '城区');
INSERT INTO `sys_regions` VALUES ('39886', '3702', '231015', '城区');
INSERT INTO `sys_regions` VALUES ('39887', '3703', '231114', '城区');
INSERT INTO `sys_regions` VALUES ('39889', '3704', '231211', '县城内');
INSERT INTO `sys_regions` VALUES ('39890', '3705', '231312', '城区');
INSERT INTO `sys_regions` VALUES ('39892', '3706', '231415', '城区');
INSERT INTO `sys_regions` VALUES ('39893', '3707', '231516', '城区');
INSERT INTO `sys_regions` VALUES ('39895', '3708', '231615', '城区');
INSERT INTO `sys_regions` VALUES ('39897', '3710', '231817', '城区');
INSERT INTO `sys_regions` VALUES ('40034', '988', '121312', '吴中区');
INSERT INTO `sys_regions` VALUES ('40035', '984', '121209', '滨湖区');
INSERT INTO `sys_regions` VALUES ('40152', '1607', '190208', '龙岗区');
INSERT INTO `sys_regions` VALUES ('40174', '959', '120806', '姜堰区');
INSERT INTO `sys_regions` VALUES ('40488', '1000', '130106', '历城区');
INSERT INTO `sys_regions` VALUES ('40489', '1000', '130107', '天桥区');
INSERT INTO `sys_regions` VALUES ('40490', '1000', '130108', '槐荫区');
INSERT INTO `sys_regions` VALUES ('40491', '1000', '130109', '历下区');
INSERT INTO `sys_regions` VALUES ('40492', '1000', '130110', '市中区');
INSERT INTO `sys_regions` VALUES ('40493', '1000', '130111', '章丘市');
INSERT INTO `sys_regions` VALUES ('40649', '933', '120506', '宿迁经济开发区');
INSERT INTO `sys_regions` VALUES ('40650', '2390', '270312', '陈仓区');
INSERT INTO `sys_regions` VALUES ('40846', '1827', '210111', '青山湖区');
INSERT INTO `sys_regions` VALUES ('40847', '1827', '210112', '红谷滩新区');
INSERT INTO `sys_regions` VALUES ('41028', '2047', '221608', '雨城区');
INSERT INTO `sys_regions` VALUES ('41029', '2058', '221706', '东坡区');
INSERT INTO `sys_regions` VALUES ('41341', '589', '080503', '本溪县');
INSERT INTO `sys_regions` VALUES ('41342', '589', '080504', '平山区');
INSERT INTO `sys_regions` VALUES ('41343', '589', '080505', '溪湖区');
INSERT INTO `sys_regions` VALUES ('41344', '589', '080506', '明山区');
INSERT INTO `sys_regions` VALUES ('41497', '258', '050811', '路北区');
INSERT INTO `sys_regions` VALUES ('41499', '258', '050812', '路南区');
INSERT INTO `sys_regions` VALUES ('41500', '258', '050813', '迁安市');
INSERT INTO `sys_regions` VALUES ('41502', '258', '050814', '丰润区');
INSERT INTO `sys_regions` VALUES ('41510', '275', '051111', '桃城区');
INSERT INTO `sys_regions` VALUES ('41653', '1609', '190301', '斗门区');
INSERT INTO `sys_regions` VALUES ('41654', '1609', '190302', '金湾区');
INSERT INTO `sys_regions` VALUES ('41655', '1609', '190303', '香洲区');
INSERT INTO `sys_regions` VALUES ('41907', '1475', '171103', '鄂城区');
INSERT INTO `sys_regions` VALUES ('41908', '1441', '170806', '黄州区');
INSERT INTO `sys_regions` VALUES ('41909', '1441', '170807', '蕲春县');
INSERT INTO `sys_regions` VALUES ('41910', '1441', '170808', '麻城市');
INSERT INTO `sys_regions` VALUES ('41911', '1441', '170809', '武穴市');
INSERT INTO `sys_regions` VALUES ('41912', '1441', '170810', '浠水县');
INSERT INTO `sys_regions` VALUES ('42218', '951', '120704', '仪征市');
INSERT INTO `sys_regions` VALUES ('42219', '951', '120705', '高邮市');
INSERT INTO `sys_regions` VALUES ('42220', '951', '120706', '江都区');
INSERT INTO `sys_regions` VALUES ('42321', '1233', '150308', '瑞安市');
INSERT INTO `sys_regions` VALUES ('42322', '1233', '150309', '乐清市');
INSERT INTO `sys_regions` VALUES ('42323', '1233', '150310', '鹿城区');
INSERT INTO `sys_regions` VALUES ('42324', '1233', '150311', '瓯海区');
INSERT INTO `sys_regions` VALUES ('42325', '1233', '150312', '永嘉县');
INSERT INTO `sys_regions` VALUES ('42540', '142', '050116', '藁城市');
INSERT INTO `sys_regions` VALUES ('42541', '142', '050117', '鹿泉市');
INSERT INTO `sys_regions` VALUES ('42542', '142', '050118', '正定县');
INSERT INTO `sys_regions` VALUES ('42543', '142', '050119', '新华区');
INSERT INTO `sys_regions` VALUES ('42544', '142', '050120', '桥西区');
INSERT INTO `sys_regions` VALUES ('42545', '142', '050121', '桥东区');
INSERT INTO `sys_regions` VALUES ('42546', '142', '050122', '裕华区');
INSERT INTO `sys_regions` VALUES ('42547', '142', '050123', '长安区');
INSERT INTO `sys_regions` VALUES ('42565', '1298', '151103', '普陀区');
INSERT INTO `sys_regions` VALUES ('42566', '1298', '151104', '定海区');
INSERT INTO `sys_regions` VALUES ('42930', '1332', '160510', '鲤城区');
INSERT INTO `sys_regions` VALUES ('42931', '1332', '160511', '丰泽区');
INSERT INTO `sys_regions` VALUES ('42932', '1332', '160512', '晋江市');
INSERT INTO `sys_regions` VALUES ('43114', '1715', '200108', '良庆区');
INSERT INTO `sys_regions` VALUES ('43115', '1715', '200109', '江南区');
INSERT INTO `sys_regions` VALUES ('43116', '1715', '200110', '兴宁区');
INSERT INTO `sys_regions` VALUES ('43117', '1715', '200111', '青秀区');
INSERT INTO `sys_regions` VALUES ('43118', '1715', '200112', '西乡塘区');
INSERT INTO `sys_regions` VALUES ('43224', '1946', '220205', '大安区');
INSERT INTO `sys_regions` VALUES ('43225', '1946', '220206', '贡井区');
INSERT INTO `sys_regions` VALUES ('43226', '2022', '221305', '顺庆区');
INSERT INTO `sys_regions` VALUES ('43227', '2022', '221306', '高坪区');
INSERT INTO `sys_regions` VALUES ('43228', '2022', '221307', '嘉陵区');
INSERT INTO `sys_regions` VALUES ('43229', '2022', '221308', '西充县');
INSERT INTO `sys_regions` VALUES ('43230', '2022', '221309', '阆中市');
INSERT INTO `sys_regions` VALUES ('43272', '1387', '170205', '下陆区');
INSERT INTO `sys_regions` VALUES ('43273', '1387', '170206', '西塞山区');
INSERT INTO `sys_regions` VALUES ('43291', '1387', '170207', '经济技术开发区');
INSERT INTO `sys_regions` VALUES ('43387', '1458', '170905', '咸安区');
INSERT INTO `sys_regions` VALUES ('43388', '1458', '170906', '赤壁市');
INSERT INTO `sys_regions` VALUES ('43963', '621', '081205', '白塔区');
INSERT INTO `sys_regions` VALUES ('43964', '621', '081206', '文圣区');
INSERT INTO `sys_regions` VALUES ('43965', '621', '081207', '灯塔市');
INSERT INTO `sys_regions` VALUES ('44027', '6858', '081407', '调兵山市');
INSERT INTO `sys_regions` VALUES ('44144', '318', '060305', '城区');
INSERT INTO `sys_regions` VALUES ('44145', '336', '060611', '榆次区');
INSERT INTO `sys_regions` VALUES ('44188', '1255', '150606', '越城区');
INSERT INTO `sys_regions` VALUES ('44189', '1250', '150505', '吴兴区');
INSERT INTO `sys_regions` VALUES ('44319', '1362', '160807', '新罗区');
INSERT INTO `sys_regions` VALUES ('44320', '2402', '270412', '秦都区');
INSERT INTO `sys_regions` VALUES ('44321', '2402', '270413', '渭城区');
INSERT INTO `sys_regions` VALUES ('44342', '2065', '221804', '简阳市');
INSERT INTO `sys_regions` VALUES ('44514', '2402', '270414', '杨陵区');
INSERT INTO `sys_regions` VALUES ('45215', '1432', '170705', '孝南区');
INSERT INTO `sys_regions` VALUES ('45216', '1432', '170706', '应城市');
INSERT INTO `sys_regions` VALUES ('45217', '1432', '170707', '安陆市');
INSERT INTO `sys_regions` VALUES ('45531', '427', '070315', '伊滨区');
INSERT INTO `sys_regions` VALUES ('45532', '427', '070316', '洛龙区');
INSERT INTO `sys_regions` VALUES ('45533', '420', '070206', '金明区');
INSERT INTO `sys_regions` VALUES ('45534', '420', '070207', '龙亭区');
INSERT INTO `sys_regions` VALUES ('45535', '420', '070208', '顺河区');
INSERT INTO `sys_regions` VALUES ('45536', '420', '070209', '鼓楼区');
INSERT INTO `sys_regions` VALUES ('45537', '420', '070210', '禹王台区');
INSERT INTO `sys_regions` VALUES ('45814', '698', '100112', '呼兰区');
INSERT INTO `sys_regions` VALUES ('45815', '698', '100113', '松北区');
INSERT INTO `sys_regions` VALUES ('45816', '698', '100114', '道里区');
INSERT INTO `sys_regions` VALUES ('45817', '698', '100115', '南岗区');
INSERT INTO `sys_regions` VALUES ('45818', '698', '100116', '道外区');
INSERT INTO `sys_regions` VALUES ('45819', '698', '100117', '香坊区');
INSERT INTO `sys_regions` VALUES ('45820', '698', '100118', '平房区');
INSERT INTO `sys_regions` VALUES ('46145', '1370', '160909', '蕉城区');
INSERT INTO `sys_regions` VALUES ('46146', '1329', '160404', '城厢区');
INSERT INTO `sys_regions` VALUES ('46147', '1329', '160405', '荔城区');
INSERT INTO `sys_regions` VALUES ('46164', '1370', '160910', '东侨开发区');
INSERT INTO `sys_regions` VALUES ('46341', '1158', '150108', '北仑区');
INSERT INTO `sys_regions` VALUES ('46342', '1158', '150109', '镇海区');
INSERT INTO `sys_regions` VALUES ('46343', '1158', '150110', '鄞州区');
INSERT INTO `sys_regions` VALUES ('46344', '1158', '150111', '江北区');
INSERT INTO `sys_regions` VALUES ('46345', '1158', '150112', '余姚市');
INSERT INTO `sys_regions` VALUES ('46504', '1042', '130709', '福山区');
INSERT INTO `sys_regions` VALUES ('46505', '1042', '130710', '牟平区');
INSERT INTO `sys_regions` VALUES ('46506', '1042', '130711', '龙口市');
INSERT INTO `sys_regions` VALUES ('46507', '1042', '130712', '莱州市');
INSERT INTO `sys_regions` VALUES ('46665', '1112', '131603', '岱岳区');
INSERT INTO `sys_regions` VALUES ('46666', '1112', '131604', '泰山区');
INSERT INTO `sys_regions` VALUES ('46667', '1112', '131605', '肥城市');
INSERT INTO `sys_regions` VALUES ('46668', '1112', '131606', '新泰市');
INSERT INTO `sys_regions` VALUES ('46763', '1315', '160204', '海沧区');
INSERT INTO `sys_regions` VALUES ('46764', '1315', '160205', '集美区');
INSERT INTO `sys_regions` VALUES ('46765', '1315', '160206', '同安区');
INSERT INTO `sys_regions` VALUES ('46820', '412', '070110', '新郑市');
INSERT INTO `sys_regions` VALUES ('46821', '412', '070111', '巩义市');
INSERT INTO `sys_regions` VALUES ('46822', '412', '070112', '荥阳市');
INSERT INTO `sys_regions` VALUES ('46823', '412', '070113', '中牟县');
INSERT INTO `sys_regions` VALUES ('46824', '573', '080211', '金州区');
INSERT INTO `sys_regions` VALUES ('46825', '573', '080212', '旅顺口区');
INSERT INTO `sys_regions` VALUES ('47166', '1016', '130308', '张店区');
INSERT INTO `sys_regions` VALUES ('47213', '199', '050422', '涿州市');
INSERT INTO `sys_regions` VALUES ('47214', '199', '050423', '定州市');
INSERT INTO `sys_regions` VALUES ('47215', '199', '050424', '徐水县');
INSERT INTO `sys_regions` VALUES ('47216', '199', '050425', '高碑店市');
INSERT INTO `sys_regions` VALUES ('47300', '412', '070114', '经济开发区');
INSERT INTO `sys_regions` VALUES ('47301', '412', '070115', '高新技术开发区');
INSERT INTO `sys_regions` VALUES ('47387', '1607', '190209', '盐田区');
INSERT INTO `sys_regions` VALUES ('47388', '1607', '190210', '龙华新区');
INSERT INTO `sys_regions` VALUES ('47712', '164', '050318', '桥西区');
INSERT INTO `sys_regions` VALUES ('47713', '164', '050319', '桥东区');
INSERT INTO `sys_regions` VALUES ('47821', '988', '121313', '昆山市');
INSERT INTO `sys_regions` VALUES ('48131', '4', '0422', '璧山县');
INSERT INTO `sys_regions` VALUES ('48132', '4', '0423', '荣昌县');
INSERT INTO `sys_regions` VALUES ('48133', '4', '0424', '铜梁县');
INSERT INTO `sys_regions` VALUES ('48134', '48133', '042401', '县城内');
INSERT INTO `sys_regions` VALUES ('48138', '48133', '042402', '土桥镇');
INSERT INTO `sys_regions` VALUES ('48139', '48133', '042403', '二坪镇');
INSERT INTO `sys_regions` VALUES ('48140', '48133', '042404', '水口镇');
INSERT INTO `sys_regions` VALUES ('48141', '48133', '042405', '安居镇');
INSERT INTO `sys_regions` VALUES ('48142', '48133', '042406', '白羊镇');
INSERT INTO `sys_regions` VALUES ('48143', '48133', '042407', '平滩镇');
INSERT INTO `sys_regions` VALUES ('48144', '48133', '042408', '石鱼镇');
INSERT INTO `sys_regions` VALUES ('48145', '48133', '042409', '福果镇');
INSERT INTO `sys_regions` VALUES ('48146', '48133', '042410', '维新镇');
INSERT INTO `sys_regions` VALUES ('48147', '48133', '042411', '高楼镇');
INSERT INTO `sys_regions` VALUES ('48148', '48133', '042412', '大庙镇');
INSERT INTO `sys_regions` VALUES ('48149', '48133', '042413', '围龙镇');
INSERT INTO `sys_regions` VALUES ('48150', '48133', '042414', '华兴镇');
INSERT INTO `sys_regions` VALUES ('48151', '48133', '042415', '永嘉镇');
INSERT INTO `sys_regions` VALUES ('48152', '48133', '042416', '安溪镇');
INSERT INTO `sys_regions` VALUES ('48153', '48133', '042417', '西河镇');
INSERT INTO `sys_regions` VALUES ('48154', '48133', '042418', '太平镇');
INSERT INTO `sys_regions` VALUES ('48155', '48133', '042419', '旧县镇');
INSERT INTO `sys_regions` VALUES ('48156', '48133', '042420', '虎峰镇');
INSERT INTO `sys_regions` VALUES ('48157', '48133', '042421', '少云镇');
INSERT INTO `sys_regions` VALUES ('48158', '48133', '042422', '蒲吕镇');
INSERT INTO `sys_regions` VALUES ('48159', '48133', '042423', '侣俸镇');
INSERT INTO `sys_regions` VALUES ('48160', '48133', '042424', '小林乡');
INSERT INTO `sys_regions` VALUES ('48161', '48133', '042425', '双山乡');
INSERT INTO `sys_regions` VALUES ('48162', '48133', '042426', '庆隆乡');
INSERT INTO `sys_regions` VALUES ('48163', '48132', '042301', '县城内');
INSERT INTO `sys_regions` VALUES ('48166', '48132', '042302', '广顺镇');
INSERT INTO `sys_regions` VALUES ('48167', '48132', '042303', '安富镇');
INSERT INTO `sys_regions` VALUES ('48168', '48132', '042304', '峰高镇');
INSERT INTO `sys_regions` VALUES ('48169', '48132', '042305', '双河镇');
INSERT INTO `sys_regions` VALUES ('48170', '48132', '042306', '直升镇');
INSERT INTO `sys_regions` VALUES ('48171', '48132', '042307', '路孔镇');
INSERT INTO `sys_regions` VALUES ('48172', '48132', '042308', '清江镇');
INSERT INTO `sys_regions` VALUES ('48173', '48132', '042309', '仁义镇');
INSERT INTO `sys_regions` VALUES ('48174', '48132', '042310', '河包镇');
INSERT INTO `sys_regions` VALUES ('48175', '48132', '042311', '古昌镇');
INSERT INTO `sys_regions` VALUES ('48176', '48132', '042312', '吴家镇');
INSERT INTO `sys_regions` VALUES ('48177', '48132', '042313', '观胜镇');
INSERT INTO `sys_regions` VALUES ('48178', '48132', '042314', '铜鼓镇');
INSERT INTO `sys_regions` VALUES ('48179', '48132', '042315', '清流镇');
INSERT INTO `sys_regions` VALUES ('48180', '48132', '042316', '盘龙镇');
INSERT INTO `sys_regions` VALUES ('48181', '48132', '042317', '远觉镇');
INSERT INTO `sys_regions` VALUES ('48182', '48132', '042318', '清升镇');
INSERT INTO `sys_regions` VALUES ('48183', '48132', '042319', '荣隆镇');
INSERT INTO `sys_regions` VALUES ('48184', '48132', '042320', '龙集镇');
INSERT INTO `sys_regions` VALUES ('48185', '48131', '042201', '县城内');
INSERT INTO `sys_regions` VALUES ('48188', '48131', '042202', '青杠镇');
INSERT INTO `sys_regions` VALUES ('48189', '48131', '042203', '来凤镇');
INSERT INTO `sys_regions` VALUES ('48190', '48131', '042204', '丁家镇');
INSERT INTO `sys_regions` VALUES ('48191', '48131', '042205', '大路镇');
INSERT INTO `sys_regions` VALUES ('48192', '48131', '042206', '八塘镇');
INSERT INTO `sys_regions` VALUES ('48193', '48131', '042207', '七塘镇');
INSERT INTO `sys_regions` VALUES ('48194', '48131', '042208', '河边镇');
INSERT INTO `sys_regions` VALUES ('48195', '48131', '042209', '福禄镇');
INSERT INTO `sys_regions` VALUES ('48196', '48131', '042210', '大兴镇');
INSERT INTO `sys_regions` VALUES ('48197', '48131', '042211', '正兴镇');
INSERT INTO `sys_regions` VALUES ('48198', '48131', '042212', '广普镇');
INSERT INTO `sys_regions` VALUES ('48199', '48131', '042213', '三合镇');
INSERT INTO `sys_regions` VALUES ('48200', '48131', '042214', '健龙镇');
INSERT INTO `sys_regions` VALUES ('48201', '4', '0425', '合川区');
INSERT INTO `sys_regions` VALUES ('48202', '4', '0426', '巴南区');
INSERT INTO `sys_regions` VALUES ('48203', '4', '0427', '北碚区');
INSERT INTO `sys_regions` VALUES ('48204', '4', '0428', '江津区');
INSERT INTO `sys_regions` VALUES ('48205', '4', '0429', '渝北区');
INSERT INTO `sys_regions` VALUES ('48206', '4', '0430', '长寿区');
INSERT INTO `sys_regions` VALUES ('48207', '4', '0431', '永川区');
INSERT INTO `sys_regions` VALUES ('48213', '48204', '042801', '四面山镇');
INSERT INTO `sys_regions` VALUES ('48214', '48204', '042802', '支坪镇');
INSERT INTO `sys_regions` VALUES ('48215', '48204', '042803', '白沙镇');
INSERT INTO `sys_regions` VALUES ('48216', '48204', '042804', '珞璜镇');
INSERT INTO `sys_regions` VALUES ('48217', '48204', '042805', '柏林镇');
INSERT INTO `sys_regions` VALUES ('48218', '48204', '042806', '蔡家镇');
INSERT INTO `sys_regions` VALUES ('48219', '48204', '042807', '慈云镇');
INSERT INTO `sys_regions` VALUES ('48220', '48204', '042808', '杜市镇');
INSERT INTO `sys_regions` VALUES ('48221', '48204', '042809', '广兴镇');
INSERT INTO `sys_regions` VALUES ('48222', '48204', '042810', '嘉平镇');
INSERT INTO `sys_regions` VALUES ('48223', '48204', '042811', '贾嗣镇');
INSERT INTO `sys_regions` VALUES ('48224', '48204', '042812', '李市镇');
INSERT INTO `sys_regions` VALUES ('48225', '48204', '042813', '龙华镇');
INSERT INTO `sys_regions` VALUES ('48226', '48204', '042814', '石蟆镇');
INSERT INTO `sys_regions` VALUES ('48227', '48204', '042815', '石门镇');
INSERT INTO `sys_regions` VALUES ('48228', '48204', '042816', '塘河镇');
INSERT INTO `sys_regions` VALUES ('48229', '48204', '042817', '吴滩镇');
INSERT INTO `sys_regions` VALUES ('48230', '48204', '042818', '西湖镇');
INSERT INTO `sys_regions` VALUES ('48231', '48204', '042819', '夏坝镇');
INSERT INTO `sys_regions` VALUES ('48232', '48204', '042820', '先锋镇');
INSERT INTO `sys_regions` VALUES ('48233', '48204', '042821', '永兴镇');
INSERT INTO `sys_regions` VALUES ('48234', '48204', '042822', '油溪镇');
INSERT INTO `sys_regions` VALUES ('48235', '48204', '042823', '中山镇');
INSERT INTO `sys_regions` VALUES ('48236', '48204', '042824', '朱杨镇');
INSERT INTO `sys_regions` VALUES ('48240', '48203', '042701', '东阳镇');
INSERT INTO `sys_regions` VALUES ('48242', '48203', '042702', '蔡家岗镇');
INSERT INTO `sys_regions` VALUES ('48243', '48203', '042703', '童家溪镇');
INSERT INTO `sys_regions` VALUES ('48244', '48203', '042704', '施家梁镇');
INSERT INTO `sys_regions` VALUES ('48245', '48203', '042705', '金刀峡镇');
INSERT INTO `sys_regions` VALUES ('48246', '48203', '042706', '澄江镇');
INSERT INTO `sys_regions` VALUES ('48247', '48203', '042707', '水土镇');
INSERT INTO `sys_regions` VALUES ('48248', '48203', '042708', '歇马镇');
INSERT INTO `sys_regions` VALUES ('48249', '48203', '042709', '天府镇');
INSERT INTO `sys_regions` VALUES ('48250', '48203', '042710', '复兴镇');
INSERT INTO `sys_regions` VALUES ('48251', '48203', '042711', '静观镇');
INSERT INTO `sys_regions` VALUES ('48252', '48203', '042712', '柳荫镇');
INSERT INTO `sys_regions` VALUES ('48253', '48203', '042713', '三圣镇');
INSERT INTO `sys_regions` VALUES ('48257', '48207', '043101', '双竹镇');
INSERT INTO `sys_regions` VALUES ('48258', '48207', '043102', '三教镇');
INSERT INTO `sys_regions` VALUES ('48259', '48207', '043103', '大安镇');
INSERT INTO `sys_regions` VALUES ('48260', '48207', '043104', '陈食镇');
INSERT INTO `sys_regions` VALUES ('48261', '48207', '043105', '板桥镇');
INSERT INTO `sys_regions` VALUES ('48262', '48207', '043106', '宝峰镇');
INSERT INTO `sys_regions` VALUES ('48263', '48207', '043107', '临江镇');
INSERT INTO `sys_regions` VALUES ('48264', '48207', '043108', '红炉镇');
INSERT INTO `sys_regions` VALUES ('48265', '48207', '043109', '吉安镇');
INSERT INTO `sys_regions` VALUES ('48266', '48207', '043110', '金龙镇');
INSERT INTO `sys_regions` VALUES ('48267', '48207', '043111', '来苏镇');
INSERT INTO `sys_regions` VALUES ('48268', '48207', '043112', '青峰镇');
INSERT INTO `sys_regions` VALUES ('48270', '48207', '043113', '双石镇');
INSERT INTO `sys_regions` VALUES ('48271', '48207', '043114', '松溉镇');
INSERT INTO `sys_regions` VALUES ('48272', '48207', '043115', '五间镇');
INSERT INTO `sys_regions` VALUES ('48273', '48207', '043116', '仙龙镇');
INSERT INTO `sys_regions` VALUES ('48274', '48207', '043117', '永荣镇');
INSERT INTO `sys_regions` VALUES ('48275', '48207', '043118', '朱沱镇');
INSERT INTO `sys_regions` VALUES ('48276', '48207', '043119', '何埂镇');
INSERT INTO `sys_regions` VALUES ('48281', '48206', '043001', '长寿湖镇');
INSERT INTO `sys_regions` VALUES ('48282', '48206', '043002', '邻封镇');
INSERT INTO `sys_regions` VALUES ('48283', '48206', '043003', '但渡镇');
INSERT INTO `sys_regions` VALUES ('48284', '48206', '043004', '云集镇');
INSERT INTO `sys_regions` VALUES ('48285', '48206', '043005', '双龙镇');
INSERT INTO `sys_regions` VALUES ('48286', '48206', '043006', '龙河镇');
INSERT INTO `sys_regions` VALUES ('48287', '48206', '043007', '石堰镇');
INSERT INTO `sys_regions` VALUES ('48288', '48206', '043008', '云台镇');
INSERT INTO `sys_regions` VALUES ('48289', '48206', '043009', '海棠镇');
INSERT INTO `sys_regions` VALUES ('48290', '48206', '043010', '葛兰镇');
INSERT INTO `sys_regions` VALUES ('48291', '48206', '043011', '新市镇');
INSERT INTO `sys_regions` VALUES ('48292', '48206', '043012', '八颗镇');
INSERT INTO `sys_regions` VALUES ('48293', '48206', '043013', '洪湖镇');
INSERT INTO `sys_regions` VALUES ('48294', '48206', '043014', '万顺镇');
INSERT INTO `sys_regions` VALUES ('48298', '48201', '042501', '草街镇');
INSERT INTO `sys_regions` VALUES ('48299', '48201', '042502', '盐井镇');
INSERT INTO `sys_regions` VALUES ('48300', '48201', '042503', '云门镇');
INSERT INTO `sys_regions` VALUES ('48301', '48201', '042504', '大石镇');
INSERT INTO `sys_regions` VALUES ('48302', '48201', '042505', '沙鱼镇');
INSERT INTO `sys_regions` VALUES ('48303', '48201', '042506', '官渡镇');
INSERT INTO `sys_regions` VALUES ('48304', '48201', '042507', '涞滩镇');
INSERT INTO `sys_regions` VALUES ('48305', '48201', '042508', '肖家镇');
INSERT INTO `sys_regions` VALUES ('48306', '48201', '042509', '古楼镇');
INSERT INTO `sys_regions` VALUES ('48307', '48201', '042510', '三庙镇');
INSERT INTO `sys_regions` VALUES ('48308', '48201', '042511', '二郎镇');
INSERT INTO `sys_regions` VALUES ('48309', '48201', '042512', '龙凤镇');
INSERT INTO `sys_regions` VALUES ('48310', '48201', '042513', '隆兴镇');
INSERT INTO `sys_regions` VALUES ('48311', '48201', '042514', '铜溪镇');
INSERT INTO `sys_regions` VALUES ('48312', '48201', '042515', '双凤镇');
INSERT INTO `sys_regions` VALUES ('48313', '48201', '042516', '狮滩镇');
INSERT INTO `sys_regions` VALUES ('48314', '48201', '042517', '清平镇');
INSERT INTO `sys_regions` VALUES ('48315', '48201', '042518', '土场镇');
INSERT INTO `sys_regions` VALUES ('48316', '48201', '042519', '小沔镇');
INSERT INTO `sys_regions` VALUES ('48317', '48201', '042520', '三汇镇');
INSERT INTO `sys_regions` VALUES ('48318', '48201', '042521', '香龙镇');
INSERT INTO `sys_regions` VALUES ('48319', '48201', '042522', '钱塘镇');
INSERT INTO `sys_regions` VALUES ('48320', '48201', '042523', '龙市镇');
INSERT INTO `sys_regions` VALUES ('48321', '48201', '042524', '燕窝镇');
INSERT INTO `sys_regions` VALUES ('48322', '48201', '042525', '太和镇');
INSERT INTO `sys_regions` VALUES ('48323', '48201', '042526', '渭沱镇');
INSERT INTO `sys_regions` VALUES ('48324', '48201', '042527', '双槐镇');
INSERT INTO `sys_regions` VALUES ('48332', '48205', '042901', '礼嘉镇');
INSERT INTO `sys_regions` VALUES ('48337', '48205', '042902', '两路镇');
INSERT INTO `sys_regions` VALUES ('48338', '48205', '042903', '王家镇');
INSERT INTO `sys_regions` VALUES ('48339', '48205', '042904', '悦来镇');
INSERT INTO `sys_regions` VALUES ('48340', '48205', '042905', '玉峰山镇');
INSERT INTO `sys_regions` VALUES ('48341', '48205', '042906', '茨竹镇');
INSERT INTO `sys_regions` VALUES ('48342', '48205', '042907', '大盛镇');
INSERT INTO `sys_regions` VALUES ('48343', '48205', '042908', '大塆镇');
INSERT INTO `sys_regions` VALUES ('48344', '48205', '042909', '古路镇');
INSERT INTO `sys_regions` VALUES ('48345', '48205', '042910', '龙兴镇');
INSERT INTO `sys_regions` VALUES ('48346', '48205', '042911', '洛碛镇');
INSERT INTO `sys_regions` VALUES ('48347', '48205', '042912', '木耳镇');
INSERT INTO `sys_regions` VALUES ('48348', '48205', '042913', '石船镇');
INSERT INTO `sys_regions` VALUES ('48349', '48205', '042914', '统景镇');
INSERT INTO `sys_regions` VALUES ('48350', '48205', '042915', '兴隆镇');
INSERT INTO `sys_regions` VALUES ('48355', '48202', '042601', '南泉镇');
INSERT INTO `sys_regions` VALUES ('48356', '48202', '042602', '一品镇');
INSERT INTO `sys_regions` VALUES ('48357', '48202', '042603', '南彭镇');
INSERT INTO `sys_regions` VALUES ('48358', '48202', '042604', '惠民镇');
INSERT INTO `sys_regions` VALUES ('48359', '48202', '042605', '麻柳嘴镇');
INSERT INTO `sys_regions` VALUES ('48360', '48202', '042606', '天星寺镇');
INSERT INTO `sys_regions` VALUES ('48361', '48202', '042607', '双河口镇');
INSERT INTO `sys_regions` VALUES ('48362', '48202', '042608', '界石镇');
INSERT INTO `sys_regions` VALUES ('48363', '48202', '042609', '安澜镇');
INSERT INTO `sys_regions` VALUES ('48364', '48202', '042610', '跳石镇');
INSERT INTO `sys_regions` VALUES ('48365', '48202', '042611', '木洞镇');
INSERT INTO `sys_regions` VALUES ('48366', '48202', '042612', '丰盛镇');
INSERT INTO `sys_regions` VALUES ('48367', '48202', '042613', '二圣镇');
INSERT INTO `sys_regions` VALUES ('48368', '48202', '042614', '东泉镇');
INSERT INTO `sys_regions` VALUES ('48369', '48202', '042615', '姜家镇');
INSERT INTO `sys_regions` VALUES ('48370', '48202', '042616', '接龙镇');
INSERT INTO `sys_regions` VALUES ('48371', '48202', '042617', '石滩镇');
INSERT INTO `sys_regions` VALUES ('48372', '48202', '042618', '石龙镇');
INSERT INTO `sys_regions` VALUES ('48377', '248', '050706', '海港区');
INSERT INTO `sys_regions` VALUES ('48378', '248', '050707', '山海关区');
INSERT INTO `sys_regions` VALUES ('48379', '239', '050611', '双桥区');
INSERT INTO `sys_regions` VALUES ('48712', '1303', '160109', '晋安区');
INSERT INTO `sys_regions` VALUES ('48713', '1303', '160110', '仓山区');
INSERT INTO `sys_regions` VALUES ('48714', '1303', '160111', '马尾区');
INSERT INTO `sys_regions` VALUES ('48715', '1303', '160112', '福清市');
INSERT INTO `sys_regions` VALUES ('48716', '1303', '160113', '闽侯县');
INSERT INTO `sys_regions` VALUES ('48936', '1482', '180103', '岳麓区');
INSERT INTO `sys_regions` VALUES ('48937', '1482', '180104', '雨花区');
INSERT INTO `sys_regions` VALUES ('48938', '1482', '180105', '开福区');
INSERT INTO `sys_regions` VALUES ('48939', '1482', '180106', '天心区');
INSERT INTO `sys_regions` VALUES ('48941', '1482', '180107', '浏阳市');
INSERT INTO `sys_regions` VALUES ('48942', '1482', '180108', '长沙县');
INSERT INTO `sys_regions` VALUES ('48943', '1482', '180109', '宁乡县');
INSERT INTO `sys_regions` VALUES ('49137', '1127', '140507', '鸠江区');
INSERT INTO `sys_regions` VALUES ('49138', '1127', '140508', '三山区');
INSERT INTO `sys_regions` VALUES ('49253', '1137', '140705', '花山区');
INSERT INTO `sys_regions` VALUES ('49254', '1137', '140706', '雨山区');
INSERT INTO `sys_regions` VALUES ('49314', '1930', '220102', '新都区');
INSERT INTO `sys_regions` VALUES ('49315', '1930', '220103', '温江区');
INSERT INTO `sys_regions` VALUES ('49316', '1930', '220104', '龙泉驿区');
INSERT INTO `sys_regions` VALUES ('49317', '1930', '220105', '青白江区');
INSERT INTO `sys_regions` VALUES ('49318', '1930', '220106', '彭州市');
INSERT INTO `sys_regions` VALUES ('49319', '1930', '220107', '崇州市');
INSERT INTO `sys_regions` VALUES ('49320', '1930', '220108', '邛崃市');
INSERT INTO `sys_regions` VALUES ('49321', '1930', '220109', '都江堰市');
INSERT INTO `sys_regions` VALUES ('49322', '1930', '220110', '郫县');
INSERT INTO `sys_regions` VALUES ('49323', '1930', '220111', '新津县');
INSERT INTO `sys_regions` VALUES ('49324', '1930', '220112', '双流县');
INSERT INTO `sys_regions` VALUES ('49325', '1930', '220113', '大邑县');
INSERT INTO `sys_regions` VALUES ('49326', '1930', '220114', '蒲江县');
INSERT INTO `sys_regions` VALUES ('49327', '1930', '220115', '金堂县');
INSERT INTO `sys_regions` VALUES ('49576', '264', '050913', '运河区');
INSERT INTO `sys_regions` VALUES ('49577', '264', '050914', '新华区');
INSERT INTO `sys_regions` VALUES ('49578', '264', '050915', '任丘市');
INSERT INTO `sys_regions` VALUES ('49579', '264', '050916', '黄骅市');
INSERT INTO `sys_regions` VALUES ('49707', '274', '051010', '三河市');
INSERT INTO `sys_regions` VALUES ('49708', '274', '051011', '霸州市');
INSERT INTO `sys_regions` VALUES ('49709', '1116', '140214', '巢湖市');
INSERT INTO `sys_regions` VALUES ('49710', '1116', '140215', '长丰县');
INSERT INTO `sys_regions` VALUES ('49711', '1213', '150214', '临安市');
INSERT INTO `sys_regions` VALUES ('50230', '2376', '270106', '未央区');
INSERT INTO `sys_regions` VALUES ('50231', '2376', '270107', '长安区');
INSERT INTO `sys_regions` VALUES ('50232', '2376', '270108', '灞桥区');
INSERT INTO `sys_regions` VALUES ('50233', '2376', '270109', '碑林区');
INSERT INTO `sys_regions` VALUES ('50235', '2376', '270110', '莲湖区');
INSERT INTO `sys_regions` VALUES ('50236', '2376', '270111', '临潼区');
INSERT INTO `sys_regions` VALUES ('50237', '2376', '270112', '阎良区');
INSERT INTO `sys_regions` VALUES ('50256', '1601', '190106', '花都区');
INSERT INTO `sys_regions` VALUES ('50257', '1601', '190107', '萝岗区');
INSERT INTO `sys_regions` VALUES ('50258', '1601', '190108', '白云区');
INSERT INTO `sys_regions` VALUES ('50259', '1601', '190109', '南沙区');
INSERT INTO `sys_regions` VALUES ('50283', '1601', '190110', '黄埔区');
INSERT INTO `sys_regions` VALUES ('50284', '1601', '190111', '增城区');
INSERT INTO `sys_regions` VALUES ('50285', '1601', '190112', '从化区');
INSERT INTO `sys_regions` VALUES ('50647', '904', '120111', '浦口区');
INSERT INTO `sys_regions` VALUES ('50712', '1381', '170106', '蔡甸区');
INSERT INTO `sys_regions` VALUES ('50713', '1381', '170107', '江夏区');
INSERT INTO `sys_regions` VALUES ('50714', '1381', '170108', '新洲区');
INSERT INTO `sys_regions` VALUES ('50715', '1381', '170109', '黄陂区');
INSERT INTO `sys_regions` VALUES ('50716', '1381', '170110', '汉阳区');
INSERT INTO `sys_regions` VALUES ('50717', '1381', '170111', '青山区');
INSERT INTO `sys_regions` VALUES ('50718', '1381', '170112', '洪山区');
INSERT INTO `sys_regions` VALUES ('50719', '1381', '170113', '汉南区');
INSERT INTO `sys_regions` VALUES ('50720', '1381', '170114', '东西湖区');
INSERT INTO `sys_regions` VALUES ('50779', '2919', '021701', '堡镇');
INSERT INTO `sys_regions` VALUES ('50780', '2919', '021702', '庙镇');
INSERT INTO `sys_regions` VALUES ('50781', '2919', '021703', '陈家镇');
INSERT INTO `sys_regions` VALUES ('50782', '2919', '021704', '城桥镇');
INSERT INTO `sys_regions` VALUES ('50783', '2919', '021705', '东平镇');
INSERT INTO `sys_regions` VALUES ('50784', '2919', '021706', '港西镇');
INSERT INTO `sys_regions` VALUES ('50785', '2919', '021707', '港沿镇');
INSERT INTO `sys_regions` VALUES ('50786', '2919', '021708', '建设镇');
INSERT INTO `sys_regions` VALUES ('50787', '2919', '021709', '绿华镇');
INSERT INTO `sys_regions` VALUES ('50788', '2919', '021710', '三星镇');
INSERT INTO `sys_regions` VALUES ('50789', '2919', '021711', '竖新镇');
INSERT INTO `sys_regions` VALUES ('50790', '2919', '021712', '向化镇');
INSERT INTO `sys_regions` VALUES ('50791', '2919', '021713', '新海镇');
INSERT INTO `sys_regions` VALUES ('50792', '2919', '021714', '新河镇');
INSERT INTO `sys_regions` VALUES ('50793', '2919', '021715', '中兴镇');
INSERT INTO `sys_regions` VALUES ('50794', '2919', '021716', '长兴乡');
INSERT INTO `sys_regions` VALUES ('50795', '2919', '021717', '横沙乡');
INSERT INTO `sys_regions` VALUES ('50796', '2919', '021718', '新村乡');
INSERT INTO `sys_regions` VALUES ('50819', '560', '080106', '皇姑区');
INSERT INTO `sys_regions` VALUES ('50820', '560', '080107', '铁西区');
INSERT INTO `sys_regions` VALUES ('50821', '560', '080108', '大东区');
INSERT INTO `sys_regions` VALUES ('50822', '560', '080109', '沈河区');
INSERT INTO `sys_regions` VALUES ('50823', '560', '080110', '东陵区');
INSERT INTO `sys_regions` VALUES ('50824', '560', '080111', '于洪区');
INSERT INTO `sys_regions` VALUES ('50825', '560', '080112', '和平区');
INSERT INTO `sys_regions` VALUES ('50826', '560', '080113', '浑南新区');
INSERT INTO `sys_regions` VALUES ('50827', '560', '080114', '沈北新区');
INSERT INTO `sys_regions` VALUES ('50944', '1930', '220116', '青羊区');
INSERT INTO `sys_regions` VALUES ('50945', '1930', '220117', '锦江区');
INSERT INTO `sys_regions` VALUES ('50946', '1930', '220118', '金牛区');
INSERT INTO `sys_regions` VALUES ('50947', '1930', '220119', '武侯区');
INSERT INTO `sys_regions` VALUES ('50948', '1930', '220120', '成华区');
INSERT INTO `sys_regions` VALUES ('50949', '1930', '220121', '高新区');
INSERT INTO `sys_regions` VALUES ('50950', '4', '0432', '江北区');
INSERT INTO `sys_regions` VALUES ('50951', '4', '0433', '南岸区');
INSERT INTO `sys_regions` VALUES ('50952', '4', '0434', '九龙坡区');
INSERT INTO `sys_regions` VALUES ('50953', '4', '0435', '沙坪坝区');
INSERT INTO `sys_regions` VALUES ('50954', '4', '0436', '大渡口区');
INSERT INTO `sys_regions` VALUES ('50955', '51028', '044001', '全境');
INSERT INTO `sys_regions` VALUES ('50956', '51027', '043901', '全境');
INSERT INTO `sys_regions` VALUES ('50957', '50950', '043202', '寸滩镇');
INSERT INTO `sys_regions` VALUES ('50958', '50950', '043203', '郭家沱镇');
INSERT INTO `sys_regions` VALUES ('50959', '50950', '043204', '铁山坪镇');
INSERT INTO `sys_regions` VALUES ('50960', '50950', '043205', '鱼嘴镇');
INSERT INTO `sys_regions` VALUES ('50961', '50950', '043206', '复盛镇');
INSERT INTO `sys_regions` VALUES ('50962', '50950', '043207', '五宝镇');
INSERT INTO `sys_regions` VALUES ('50963', '50951', '043302', '茶园新区');
INSERT INTO `sys_regions` VALUES ('50964', '50951', '043303', '鸡冠石镇');
INSERT INTO `sys_regions` VALUES ('50965', '50951', '043304', '长生桥镇');
INSERT INTO `sys_regions` VALUES ('50966', '50951', '043305', '峡口镇');
INSERT INTO `sys_regions` VALUES ('50967', '50951', '043306', '广阳镇');
INSERT INTO `sys_regions` VALUES ('50968', '50951', '043307', '迎龙镇');
INSERT INTO `sys_regions` VALUES ('50969', '50952', '043402', '白市驿镇');
INSERT INTO `sys_regions` VALUES ('50970', '50952', '043403', '铜罐驿镇');
INSERT INTO `sys_regions` VALUES ('50971', '50952', '043404', '华岩镇');
INSERT INTO `sys_regions` VALUES ('50972', '50952', '043405', '巴福镇');
INSERT INTO `sys_regions` VALUES ('50973', '50952', '043406', '含谷镇');
INSERT INTO `sys_regions` VALUES ('50974', '50952', '043407', '金凤镇');
INSERT INTO `sys_regions` VALUES ('50975', '50952', '043408', '石板镇');
INSERT INTO `sys_regions` VALUES ('50976', '50952', '043409', '陶家镇');
INSERT INTO `sys_regions` VALUES ('50977', '50952', '043410', '西彭镇');
INSERT INTO `sys_regions` VALUES ('50978', '50952', '043411', '走马镇');
INSERT INTO `sys_regions` VALUES ('50979', '50953', '043501', '内环以内');
INSERT INTO `sys_regions` VALUES ('50980', '50953', '043502', '陈家桥镇');
INSERT INTO `sys_regions` VALUES ('50981', '50953', '043503', '歌乐山镇');
INSERT INTO `sys_regions` VALUES ('50982', '50953', '043504', '青木关镇');
INSERT INTO `sys_regions` VALUES ('50983', '50953', '043505', '回龙坝镇');
INSERT INTO `sys_regions` VALUES ('50984', '50953', '043506', '大学城');
INSERT INTO `sys_regions` VALUES ('50985', '50953', '043507', '虎溪镇');
INSERT INTO `sys_regions` VALUES ('50986', '50953', '043508', '西永镇');
INSERT INTO `sys_regions` VALUES ('50987', '50953', '043509', '土主镇');
INSERT INTO `sys_regions` VALUES ('50988', '50953', '043510', '井口镇');
INSERT INTO `sys_regions` VALUES ('50989', '50953', '043511', '曾家镇');
INSERT INTO `sys_regions` VALUES ('50990', '50953', '043512', '凤凰镇');
INSERT INTO `sys_regions` VALUES ('50991', '50953', '043513', '中梁镇');
INSERT INTO `sys_regions` VALUES ('50992', '50954', '043602', '茄子溪镇');
INSERT INTO `sys_regions` VALUES ('50993', '50954', '043603', '建胜镇');
INSERT INTO `sys_regions` VALUES ('50994', '50954', '043604', '跳磴镇');
INSERT INTO `sys_regions` VALUES ('50995', '4', '0437', '綦江区');
INSERT INTO `sys_regions` VALUES ('51000', '50995', '043701', '三江镇');
INSERT INTO `sys_regions` VALUES ('51001', '50995', '043702', '安稳镇');
INSERT INTO `sys_regions` VALUES ('51002', '50995', '043703', '打通镇');
INSERT INTO `sys_regions` VALUES ('51003', '50995', '043704', '丁山镇');
INSERT INTO `sys_regions` VALUES ('51004', '50995', '043705', '东溪镇');
INSERT INTO `sys_regions` VALUES ('51005', '50995', '043706', '扶欢镇');
INSERT INTO `sys_regions` VALUES ('51006', '50995', '043707', '赶水镇');
INSERT INTO `sys_regions` VALUES ('51007', '50995', '043708', '郭扶镇');
INSERT INTO `sys_regions` VALUES ('51008', '50995', '043709', '横山镇');
INSERT INTO `sys_regions` VALUES ('51009', '50995', '043710', '隆盛镇');
INSERT INTO `sys_regions` VALUES ('51010', '50995', '043711', '三角镇');
INSERT INTO `sys_regions` VALUES ('51011', '50995', '043712', '石壕镇');
INSERT INTO `sys_regions` VALUES ('51012', '50995', '043713', '石角镇');
INSERT INTO `sys_regions` VALUES ('51013', '50995', '043714', '新盛镇');
INSERT INTO `sys_regions` VALUES ('51014', '50995', '043715', '永城镇');
INSERT INTO `sys_regions` VALUES ('51015', '50995', '043716', '永新镇');
INSERT INTO `sys_regions` VALUES ('51016', '50995', '043717', '中峰镇');
INSERT INTO `sys_regions` VALUES ('51017', '50995', '043718', '篆塘镇');
INSERT INTO `sys_regions` VALUES ('51018', '50995', '043719', '丛林镇');
INSERT INTO `sys_regions` VALUES ('51019', '50995', '043720', '关坝镇');
INSERT INTO `sys_regions` VALUES ('51020', '50995', '043721', '黑山镇');
INSERT INTO `sys_regions` VALUES ('51021', '50995', '043722', '金桥镇');
INSERT INTO `sys_regions` VALUES ('51022', '50995', '043723', '南桐镇');
INSERT INTO `sys_regions` VALUES ('51023', '50995', '043724', '青年镇');
INSERT INTO `sys_regions` VALUES ('51024', '50995', '043725', '石林镇');
INSERT INTO `sys_regions` VALUES ('51025', '50995', '043726', '万东镇');
INSERT INTO `sys_regions` VALUES ('51026', '4', '0438', '渝中区');
INSERT INTO `sys_regions` VALUES ('51027', '4', '0439', '高新区');
INSERT INTO `sys_regions` VALUES ('51028', '4', '0440', '北部新区');
INSERT INTO `sys_regions` VALUES ('51029', '1042', '130713', '开发区');
INSERT INTO `sys_regions` VALUES ('51035', '3', '0301', '东丽区');
INSERT INTO `sys_regions` VALUES ('51036', '3', '0302', '和平区');
INSERT INTO `sys_regions` VALUES ('51037', '3', '0303', '河北区');
INSERT INTO `sys_regions` VALUES ('51038', '3', '0304', '河东区');
INSERT INTO `sys_regions` VALUES ('51039', '3', '0305', '河西区');
INSERT INTO `sys_regions` VALUES ('51040', '3', '0306', '红桥区');
INSERT INTO `sys_regions` VALUES ('51041', '3', '0307', '蓟县');
INSERT INTO `sys_regions` VALUES ('51042', '3', '0308', '静海县');
INSERT INTO `sys_regions` VALUES ('51043', '3', '0309', '南开区');
INSERT INTO `sys_regions` VALUES ('51044', '3', '0310', '塘沽区');
INSERT INTO `sys_regions` VALUES ('51045', '3', '0311', '西青区');
INSERT INTO `sys_regions` VALUES ('51046', '3', '0312', '武清区');
INSERT INTO `sys_regions` VALUES ('51047', '3', '0313', '津南区');
INSERT INTO `sys_regions` VALUES ('51048', '3', '0314', '汉沽区');
INSERT INTO `sys_regions` VALUES ('51049', '3', '0315', '大港区');
INSERT INTO `sys_regions` VALUES ('51050', '3', '0316', '北辰区');
INSERT INTO `sys_regions` VALUES ('51051', '3', '0317', '宝坻区');
INSERT INTO `sys_regions` VALUES ('51052', '3', '0318', '宁河县');
INSERT INTO `sys_regions` VALUES ('51081', '2810', '011204', '亦庄经济开发区');
INSERT INTO `sys_regions` VALUES ('51091', '1601', '190113', '广州大学城');
INSERT INTO `sys_regions` VALUES ('51125', '2812', '011301', '北石槽镇');
INSERT INTO `sys_regions` VALUES ('51126', '2812', '011302', '北务镇');
INSERT INTO `sys_regions` VALUES ('51127', '2812', '011303', '北小营镇');
INSERT INTO `sys_regions` VALUES ('51128', '2812', '011304', '大孙各庄镇');
INSERT INTO `sys_regions` VALUES ('51129', '2812', '011305', '高丽营镇');
INSERT INTO `sys_regions` VALUES ('51130', '2812', '011306', '光明街道');
INSERT INTO `sys_regions` VALUES ('51131', '2812', '011307', '后沙峪地区');
INSERT INTO `sys_regions` VALUES ('51132', '2812', '011308', '空港街道');
INSERT INTO `sys_regions` VALUES ('51133', '2812', '011309', '李桥镇');
INSERT INTO `sys_regions` VALUES ('51134', '2812', '011310', '李遂镇');
INSERT INTO `sys_regions` VALUES ('51135', '2812', '011311', '龙湾屯镇');
INSERT INTO `sys_regions` VALUES ('51136', '2812', '011312', '马坡地区');
INSERT INTO `sys_regions` VALUES ('51137', '2812', '011313', '木林镇');
INSERT INTO `sys_regions` VALUES ('51138', '2812', '011314', '南彩镇');
INSERT INTO `sys_regions` VALUES ('51139', '2812', '011315', '南法信地区');
INSERT INTO `sys_regions` VALUES ('51140', '2812', '011316', '牛栏山地区');
INSERT INTO `sys_regions` VALUES ('51141', '2812', '011317', '仁和地区');
INSERT INTO `sys_regions` VALUES ('51142', '2812', '011318', '胜利街道');
INSERT INTO `sys_regions` VALUES ('51143', '2812', '011319', '石园街道');
INSERT INTO `sys_regions` VALUES ('51144', '2812', '011320', '双丰街道');
INSERT INTO `sys_regions` VALUES ('51145', '2812', '011321', '天竺地区');
INSERT INTO `sys_regions` VALUES ('51146', '2812', '011322', '旺泉街道');
INSERT INTO `sys_regions` VALUES ('51147', '2812', '011323', '杨镇地区');
INSERT INTO `sys_regions` VALUES ('51148', '2812', '011324', '张镇');
INSERT INTO `sys_regions` VALUES ('51149', '2812', '011325', '赵全营镇');
INSERT INTO `sys_regions` VALUES ('51180', '972', '121008', '丹徒新区');
INSERT INTO `sys_regions` VALUES ('51198', '50950', '043208', '大石坝镇');
INSERT INTO `sys_regions` VALUES ('51202', '132', '041134', '白桥镇');
INSERT INTO `sys_regions` VALUES ('51203', '132', '041135', '大德镇');
INSERT INTO `sys_regions` VALUES ('51204', '132', '041136', '金峰镇');
INSERT INTO `sys_regions` VALUES ('51205', '132', '041137', '谭家镇');
INSERT INTO `sys_regions` VALUES ('51206', '132', '041138', '天和镇');
INSERT INTO `sys_regions` VALUES ('51207', '132', '041139', '白泉乡');
INSERT INTO `sys_regions` VALUES ('51216', '2809', '011101', '六环内（马驹桥镇）');
INSERT INTO `sys_regions` VALUES ('51217', '2809', '011102', '六环外（马驹桥镇）');
INSERT INTO `sys_regions` VALUES ('51218', '2809', '011103', '永顺镇');
INSERT INTO `sys_regions` VALUES ('51219', '2809', '011104', '梨园镇');
INSERT INTO `sys_regions` VALUES ('51220', '2809', '011105', '宋庄镇');
INSERT INTO `sys_regions` VALUES ('51221', '2809', '011106', '漷县镇');
INSERT INTO `sys_regions` VALUES ('51222', '2809', '011107', '张家湾镇');
INSERT INTO `sys_regions` VALUES ('51223', '2809', '011108', '西集镇');
INSERT INTO `sys_regions` VALUES ('51224', '2809', '011109', '永乐店镇');
INSERT INTO `sys_regions` VALUES ('51225', '2809', '011110', '潞城镇');
INSERT INTO `sys_regions` VALUES ('51226', '2809', '011111', '台湖镇');
INSERT INTO `sys_regions` VALUES ('51227', '2809', '011112', '于家务乡');
INSERT INTO `sys_regions` VALUES ('51228', '2809', '011113', '中仓街道');
INSERT INTO `sys_regions` VALUES ('51229', '2809', '011114', '新华街道');
INSERT INTO `sys_regions` VALUES ('51230', '2809', '011115', '玉桥街道');
INSERT INTO `sys_regions` VALUES ('51231', '2809', '011116', '北苑街道');
INSERT INTO `sys_regions` VALUES ('51232', '2809', '011117', '次渠镇');
INSERT INTO `sys_regions` VALUES ('51245', '1857', '210604', '龙虎山风景旅游区');
INSERT INTO `sys_regions` VALUES ('51505', '3065', '011801', '延庆镇');
INSERT INTO `sys_regions` VALUES ('51506', '3065', '011802', '城区');
INSERT INTO `sys_regions` VALUES ('51507', '3065', '011803', '康庄镇');
INSERT INTO `sys_regions` VALUES ('51508', '3065', '011804', '八达岭镇');
INSERT INTO `sys_regions` VALUES ('51509', '3065', '011805', '永宁镇');
INSERT INTO `sys_regions` VALUES ('51510', '3065', '011806', '旧县镇');
INSERT INTO `sys_regions` VALUES ('51511', '3065', '011807', '张山营镇');
INSERT INTO `sys_regions` VALUES ('51512', '3065', '011808', '四海镇');
INSERT INTO `sys_regions` VALUES ('51513', '3065', '011809', '千家店镇');
INSERT INTO `sys_regions` VALUES ('51514', '3065', '011810', '沈家营镇');
INSERT INTO `sys_regions` VALUES ('51515', '3065', '011811', '大榆树镇');
INSERT INTO `sys_regions` VALUES ('51516', '3065', '011812', '井庄镇');
INSERT INTO `sys_regions` VALUES ('51517', '3065', '011813', '大庄科乡');
INSERT INTO `sys_regions` VALUES ('51518', '3065', '011814', '刘斌堡乡');
INSERT INTO `sys_regions` VALUES ('51519', '3065', '011815', '香营乡');
INSERT INTO `sys_regions` VALUES ('51520', '3065', '011816', '珍珠泉乡');
INSERT INTO `sys_regions` VALUES ('51528', '2808', '011001', '城区');
INSERT INTO `sys_regions` VALUES ('51529', '2808', '011002', '大安山乡');
INSERT INTO `sys_regions` VALUES ('51530', '2808', '011003', '大石窝镇');
INSERT INTO `sys_regions` VALUES ('51531', '2808', '011004', '窦店镇');
INSERT INTO `sys_regions` VALUES ('51532', '2808', '011005', '佛子庄乡');
INSERT INTO `sys_regions` VALUES ('51534', '2808', '011006', '韩村河镇');
INSERT INTO `sys_regions` VALUES ('51535', '2808', '011007', '河北镇');
INSERT INTO `sys_regions` VALUES ('51536', '2808', '011008', '良乡镇');
INSERT INTO `sys_regions` VALUES ('51537', '2808', '011009', '琉璃河镇');
INSERT INTO `sys_regions` VALUES ('51538', '2808', '011010', '南窖乡');
INSERT INTO `sys_regions` VALUES ('51539', '2808', '011011', '蒲洼乡');
INSERT INTO `sys_regions` VALUES ('51540', '2808', '011012', '青龙湖镇');
INSERT INTO `sys_regions` VALUES ('51541', '2808', '011013', '十渡镇');
INSERT INTO `sys_regions` VALUES ('51542', '2808', '011014', '石楼镇');
INSERT INTO `sys_regions` VALUES ('51543', '2808', '011015', '史家营乡');
INSERT INTO `sys_regions` VALUES ('51544', '2808', '011016', '霞云岭乡');
INSERT INTO `sys_regions` VALUES ('51545', '2808', '011017', '新镇');
INSERT INTO `sys_regions` VALUES ('51546', '2808', '011018', '阎村镇');
INSERT INTO `sys_regions` VALUES ('51547', '2808', '011019', '燕山地区');
INSERT INTO `sys_regions` VALUES ('51548', '2808', '011020', '张坊镇');
INSERT INTO `sys_regions` VALUES ('51549', '2808', '011021', '长沟镇');
INSERT INTO `sys_regions` VALUES ('51550', '2808', '011022', '长阳镇');
INSERT INTO `sys_regions` VALUES ('51551', '2808', '011023', '周口店镇');
INSERT INTO `sys_regions` VALUES ('51552', '2807', '010901', '城区');
INSERT INTO `sys_regions` VALUES ('51553', '2807', '010902', '龙泉镇');
INSERT INTO `sys_regions` VALUES ('51554', '2807', '010903', '永定镇');
INSERT INTO `sys_regions` VALUES ('51555', '2807', '010904', '大台镇');
INSERT INTO `sys_regions` VALUES ('51556', '2807', '010905', '潭柘寺镇');
INSERT INTO `sys_regions` VALUES ('51557', '2807', '010906', '王平镇');
INSERT INTO `sys_regions` VALUES ('51558', '2807', '010907', '军庄镇');
INSERT INTO `sys_regions` VALUES ('51559', '2807', '010908', '妙峰山镇');
INSERT INTO `sys_regions` VALUES ('51560', '2807', '010909', '雁翅镇');
INSERT INTO `sys_regions` VALUES ('51561', '2807', '010910', '斋堂镇');
INSERT INTO `sys_regions` VALUES ('51562', '2807', '010911', '清水镇');
INSERT INTO `sys_regions` VALUES ('51706', '131', '041031', '永乐镇');
INSERT INTO `sys_regions` VALUES ('51800', '2830', '021101', '城区');
INSERT INTO `sys_regions` VALUES ('51801', '2830', '021102', '川沙新镇');
INSERT INTO `sys_regions` VALUES ('51802', '2830', '021103', '高桥镇');
INSERT INTO `sys_regions` VALUES ('51803', '2830', '021104', '北蔡镇');
INSERT INTO `sys_regions` VALUES ('51804', '2830', '021105', '合庆镇');
INSERT INTO `sys_regions` VALUES ('51805', '2830', '021106', '唐镇');
INSERT INTO `sys_regions` VALUES ('51806', '2830', '021107', '曹路镇');
INSERT INTO `sys_regions` VALUES ('51807', '2830', '021108', '金桥镇');
INSERT INTO `sys_regions` VALUES ('51808', '2830', '021109', '高行镇');
INSERT INTO `sys_regions` VALUES ('51809', '2830', '021110', '高东镇');
INSERT INTO `sys_regions` VALUES ('51810', '2830', '021111', '张江镇');
INSERT INTO `sys_regions` VALUES ('51811', '2830', '021112', '三林镇');
INSERT INTO `sys_regions` VALUES ('51812', '2830', '021113', '南汇新城镇');
INSERT INTO `sys_regions` VALUES ('51822', '2830', '021114', '祝桥镇');
INSERT INTO `sys_regions` VALUES ('51823', '2830', '021115', '新场镇');
INSERT INTO `sys_regions` VALUES ('51824', '2830', '021116', '惠南镇');
INSERT INTO `sys_regions` VALUES ('51825', '2830', '021117', '康桥镇');
INSERT INTO `sys_regions` VALUES ('51826', '2830', '021118', '宣桥镇');
INSERT INTO `sys_regions` VALUES ('51827', '2830', '021119', '书院镇');
INSERT INTO `sys_regions` VALUES ('51828', '2830', '021120', '大团镇');
INSERT INTO `sys_regions` VALUES ('51829', '2830', '021121', '周浦镇');
INSERT INTO `sys_regions` VALUES ('51830', '2830', '021122', '芦潮港镇');
INSERT INTO `sys_regions` VALUES ('51831', '2830', '021123', '泥城镇');
INSERT INTO `sys_regions` VALUES ('51832', '2830', '021124', '航头镇');
INSERT INTO `sys_regions` VALUES ('51833', '2830', '021125', '万祥镇');
INSERT INTO `sys_regions` VALUES ('51834', '2830', '021126', '老港镇');
INSERT INTO `sys_regions` VALUES ('51881', '2376', '270113', '新城区');
INSERT INTO `sys_regions` VALUES ('51911', '2824', '020801', '罗店镇');
INSERT INTO `sys_regions` VALUES ('51912', '2824', '020802', '大场镇');
INSERT INTO `sys_regions` VALUES ('51913', '2824', '020803', '杨行镇');
INSERT INTO `sys_regions` VALUES ('51914', '2824', '020804', '月浦镇');
INSERT INTO `sys_regions` VALUES ('51915', '2824', '020805', '罗泾镇');
INSERT INTO `sys_regions` VALUES ('51916', '2824', '020806', '顾村镇');
INSERT INTO `sys_regions` VALUES ('51917', '2824', '020807', '高境镇');
INSERT INTO `sys_regions` VALUES ('51918', '2824', '020808', '庙行镇');
INSERT INTO `sys_regions` VALUES ('51919', '2824', '020809', '淞南镇');
INSERT INTO `sys_regions` VALUES ('51920', '2824', '020810', '宝山城市工业园区');
INSERT INTO `sys_regions` VALUES ('51921', '2824', '020811', '城区');
INSERT INTO `sys_regions` VALUES ('51928', '2837', '021501', '南桥镇');
INSERT INTO `sys_regions` VALUES ('51929', '2837', '021502', '奉城镇');
INSERT INTO `sys_regions` VALUES ('51930', '2837', '021503', '四团镇');
INSERT INTO `sys_regions` VALUES ('51931', '2825', '020901', '城区');
INSERT INTO `sys_regions` VALUES ('51932', '2825', '020902', '莘庄镇');
INSERT INTO `sys_regions` VALUES ('51933', '2825', '020903', '七宝镇');
INSERT INTO `sys_regions` VALUES ('51934', '2825', '020904', '浦江镇');
INSERT INTO `sys_regions` VALUES ('51935', '2825', '020905', '梅陇镇');
INSERT INTO `sys_regions` VALUES ('51936', '2825', '020906', '虹桥镇');
INSERT INTO `sys_regions` VALUES ('51937', '2825', '020907', '马桥镇');
INSERT INTO `sys_regions` VALUES ('51938', '2825', '020908', '吴泾镇');
INSERT INTO `sys_regions` VALUES ('51939', '2825', '020909', '华漕镇');
INSERT INTO `sys_regions` VALUES ('51940', '2825', '020910', '颛桥镇');
INSERT INTO `sys_regions` VALUES ('51941', '2826', '021001', '城区');
INSERT INTO `sys_regions` VALUES ('51942', '2826', '021002', '南翔镇');
INSERT INTO `sys_regions` VALUES ('51943', '2826', '021003', '马陆镇');
INSERT INTO `sys_regions` VALUES ('51944', '2826', '021004', '华亭镇');
INSERT INTO `sys_regions` VALUES ('51945', '2826', '021005', '江桥镇');
INSERT INTO `sys_regions` VALUES ('51946', '2826', '021006', '菊园新区');
INSERT INTO `sys_regions` VALUES ('51947', '2826', '021007', '安亭镇');
INSERT INTO `sys_regions` VALUES ('51948', '2826', '021008', '徐行镇');
INSERT INTO `sys_regions` VALUES ('51949', '2826', '021009', '外冈镇');
INSERT INTO `sys_regions` VALUES ('51950', '2826', '021010', '嘉定工业区');
INSERT INTO `sys_regions` VALUES ('51951', '2833', '021201', '朱家角镇');
INSERT INTO `sys_regions` VALUES ('51952', '2833', '021202', '赵巷镇');
INSERT INTO `sys_regions` VALUES ('51953', '2833', '021203', '徐泾镇');
INSERT INTO `sys_regions` VALUES ('51954', '2833', '021204', '华新镇');
INSERT INTO `sys_regions` VALUES ('51955', '2833', '021205', '重固镇');
INSERT INTO `sys_regions` VALUES ('51956', '2833', '021206', '白鹤镇');
INSERT INTO `sys_regions` VALUES ('51957', '2833', '021207', '练塘镇');
INSERT INTO `sys_regions` VALUES ('51958', '2833', '021208', '金泽镇');
INSERT INTO `sys_regions` VALUES ('51959', '2833', '021209', '城区');
INSERT INTO `sys_regions` VALUES ('51960', '2835', '021401', '城区');
INSERT INTO `sys_regions` VALUES ('51961', '2835', '021402', '金山工业区');
INSERT INTO `sys_regions` VALUES ('51962', '2835', '021403', '朱泾镇');
INSERT INTO `sys_regions` VALUES ('51963', '2835', '021404', '枫泾镇');
INSERT INTO `sys_regions` VALUES ('51964', '2835', '021405', '张堰镇');
INSERT INTO `sys_regions` VALUES ('51965', '2835', '021406', '亭林镇');
INSERT INTO `sys_regions` VALUES ('51966', '2835', '021407', '吕巷镇');
INSERT INTO `sys_regions` VALUES ('51967', '2835', '021408', '廊下镇');
INSERT INTO `sys_regions` VALUES ('51968', '2835', '021409', '金山卫镇');
INSERT INTO `sys_regions` VALUES ('51970', '2835', '021410', '漕泾镇');
INSERT INTO `sys_regions` VALUES ('51971', '2835', '021411', '山阳镇');
INSERT INTO `sys_regions` VALUES ('51972', '2820', '020501', '城区');
INSERT INTO `sys_regions` VALUES ('51973', '2817', '020401', '城区');
INSERT INTO `sys_regions` VALUES ('51974', '2823', '020701', '城区');
INSERT INTO `sys_regions` VALUES ('51975', '2815', '020301', '城区');
INSERT INTO `sys_regions` VALUES ('51976', '2813', '020201', '城区');
INSERT INTO `sys_regions` VALUES ('51978', '78', '020101', '城区');
INSERT INTO `sys_regions` VALUES ('51979', '2822', '020601', '城区');
INSERT INTO `sys_regions` VALUES ('51980', '2841', '021601', '城区');
INSERT INTO `sys_regions` VALUES ('51982', '2834', '021301', '城区');
INSERT INTO `sys_regions` VALUES ('51983', '2834', '021302', '泗泾镇');
INSERT INTO `sys_regions` VALUES ('51984', '2834', '021303', '佘山镇');
INSERT INTO `sys_regions` VALUES ('51985', '2834', '021304', '车墩镇');
INSERT INTO `sys_regions` VALUES ('51986', '2834', '021305', '新桥镇');
INSERT INTO `sys_regions` VALUES ('51987', '2834', '021306', '洞泾镇');
INSERT INTO `sys_regions` VALUES ('51988', '2834', '021307', '九亭镇');
INSERT INTO `sys_regions` VALUES ('51989', '2834', '021308', '泖港镇');
INSERT INTO `sys_regions` VALUES ('51990', '2834', '021309', '石湖荡镇');
INSERT INTO `sys_regions` VALUES ('51991', '2834', '021310', '新浜镇');
INSERT INTO `sys_regions` VALUES ('51992', '2834', '021311', '叶榭镇');
INSERT INTO `sys_regions` VALUES ('51993', '2834', '021312', '小昆山镇');
INSERT INTO `sys_regions` VALUES ('51994', '2837', '021504', '柘林镇');
INSERT INTO `sys_regions` VALUES ('51995', '2837', '021505', '庄行镇');
INSERT INTO `sys_regions` VALUES ('51996', '2837', '021506', '金汇镇');
INSERT INTO `sys_regions` VALUES ('51997', '2837', '021507', '青村镇');
INSERT INTO `sys_regions` VALUES ('51998', '2837', '021508', '海湾镇');
INSERT INTO `sys_regions` VALUES ('52023', '1072', '131112', '兰陵县');
INSERT INTO `sys_regions` VALUES ('52075', '2376', '270114', '西安武警工程学院');
INSERT INTO `sys_regions` VALUES ('52083', '132', '041140', '岳溪镇');
INSERT INTO `sys_regions` VALUES ('52093', '1657', '191122', '城区');
INSERT INTO `sys_regions` VALUES ('52305', '2780', '071812', '城区');
INSERT INTO `sys_regions` VALUES ('52306', '2983', '171624', '城区');
INSERT INTO `sys_regions` VALUES ('52484', '113', '040142', '城区');
INSERT INTO `sys_regions` VALUES ('52485', '114', '040241', '城区');
INSERT INTO `sys_regions` VALUES ('52486', '119', '040430', '城区');
INSERT INTO `sys_regions` VALUES ('52487', '126', '040626', '城区');
INSERT INTO `sys_regions` VALUES ('52488', '128', '040728', '城区');
INSERT INTO `sys_regions` VALUES ('52489', '48201', '042528', '城区');
INSERT INTO `sys_regions` VALUES ('52490', '48202', '042619', '城区');
INSERT INTO `sys_regions` VALUES ('52491', '48203', '042714', '城区');
INSERT INTO `sys_regions` VALUES ('52492', '48204', '042825', '城区');
INSERT INTO `sys_regions` VALUES ('52493', '48205', '042916', '城区');
INSERT INTO `sys_regions` VALUES ('52494', '48206', '043015', '城区');
INSERT INTO `sys_regions` VALUES ('52495', '48207', '043120', '城区');
INSERT INTO `sys_regions` VALUES ('52496', '50951', '043308', '城区');
INSERT INTO `sys_regions` VALUES ('52497', '50995', '043727', '城区');
INSERT INTO `sys_regions` VALUES ('52607', '2016', '221206', '前锋区');
INSERT INTO `sys_regions` VALUES ('52623', '2042', '221505', '恩阳区');
INSERT INTO `sys_regions` VALUES ('52790', '2723', '311204', '阿拉山口市');
INSERT INTO `sys_regions` VALUES ('52830', '1114', '140103', '铜官区');
INSERT INTO `sys_regions` VALUES ('52831', '1121', '140308', '寿县');
INSERT INTO `sys_regions` VALUES ('52832', '1114', '140104', '枞阳县');
INSERT INTO `sys_regions` VALUES ('52993', '-1', '34', '港澳');
INSERT INTO `sys_regions` VALUES ('52994', '52993', '3401', '香港特别行政区');
INSERT INTO `sys_regions` VALUES ('52995', '52993', '3402', '澳门特别行政区');
INSERT INTO `sys_regions` VALUES ('52996', '52994', '340101', '中西区');
INSERT INTO `sys_regions` VALUES ('52997', '52994', '340102', '东区');
INSERT INTO `sys_regions` VALUES ('52998', '52994', '340103', '九龙城区');
INSERT INTO `sys_regions` VALUES ('52999', '52994', '340104', '观塘区');
INSERT INTO `sys_regions` VALUES ('53000', '52994', '340105', '深水埗区');
INSERT INTO `sys_regions` VALUES ('53001', '52994', '340106', '湾仔区');
INSERT INTO `sys_regions` VALUES ('53002', '52994', '340107', '黄大仙区');
INSERT INTO `sys_regions` VALUES ('53003', '52994', '340108', '油尖旺区');
INSERT INTO `sys_regions` VALUES ('53004', '52994', '340109', '离岛区');
INSERT INTO `sys_regions` VALUES ('53005', '52994', '340110', '葵青区');
INSERT INTO `sys_regions` VALUES ('53006', '52994', '340111', '北区');
INSERT INTO `sys_regions` VALUES ('53007', '52994', '340112', '西贡区');
INSERT INTO `sys_regions` VALUES ('53008', '52994', '340113', '沙田区');
INSERT INTO `sys_regions` VALUES ('53009', '52994', '340114', '屯门区');
INSERT INTO `sys_regions` VALUES ('53010', '52994', '340115', '大埔区');
INSERT INTO `sys_regions` VALUES ('53011', '52994', '340116', '荃湾区');
INSERT INTO `sys_regions` VALUES ('53012', '52994', '340117', '元朗区');
INSERT INTO `sys_regions` VALUES ('53013', '52994', '340118', '香港');
INSERT INTO `sys_regions` VALUES ('53014', '52994', '340119', '九龙');
INSERT INTO `sys_regions` VALUES ('53015', '52994', '340120', '新界');
INSERT INTO `sys_regions` VALUES ('53016', '52995', '340201', '澳门特别行政区');
INSERT INTO `sys_regions` VALUES ('53017', '52995', '340202', '澳门半岛');
INSERT INTO `sys_regions` VALUES ('53018', '52995', '340203', '凼仔');
INSERT INTO `sys_regions` VALUES ('53019', '52995', '340204', '路凼城');
INSERT INTO `sys_regions` VALUES ('53020', '52995', '340205', '路环');

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res` (
  `id` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `url` varchar(50) DEFAULT NULL,
  `icon` varchar(30) DEFAULT NULL,
  `location` int(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `descript` tinytext,
  `parent_id` varchar(50) DEFAULT NULL,
  `has_children` tinyint(1) NOT NULL DEFAULT '0',
  `beta` tinyint(1) NOT NULL DEFAULT '0',
  `display` tinyint(1) NOT NULL DEFAULT '1',
  `new_window` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_res
-- ----------------------------
INSERT INTO `sys_res` VALUES ('4028388148ac878b0148ac8820090000', '系统管理', '012', '/system/index', 'glyphicon glyphicon-cog', '12', '1', '管理系统中的用户、角色、菜单等', '-1', '1', '0', '1', '0', '2014-09-25 19:17:54');
INSERT INTO `sys_res` VALUES ('4028388148acb42f0148acccd58d0001', '角色管理', '012004', '/system/role', 'fa fa-user', '4', '1', '', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2014-09-25 20:32:58');
INSERT INTO `sys_res` VALUES ('4028388148acb42f0148acd9a6170005', '用户管理', '012002', '/system/user', 'ti-user', '2', '1', '', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2014-09-25 20:46:58');
INSERT INTO `sys_res` VALUES ('4028388148b2fe810148b32e9036002e', '高级管理', '012009', '/system/senior', 'fa fa-gavel', '9', '1', '涉及系统的关键性数据，请在客服人员指导下谨慎操作', '4028388148ac878b0148ac8820090000', '1', '0', '1', '0', '2014-09-27 02:17:26');
INSERT INTO `sys_res` VALUES ('4028388148b2fe810148b32ebc0d002f', '组织架构', '012001', '/system/unit', 'fa fa-sitemap', '1', '1', '软件内部的组织结构，也可以是公司内部的组织结构', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2014-09-27 02:17:37');
INSERT INTO `sys_res` VALUES ('4028388148b2fe810148b32ef7120030', '菜单管理', '012005', '/system/res', 'ti-menu-alt', '5', '1', '', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2014-09-27 02:17:52');
INSERT INTO `sys_res` VALUES ('40283881494264460149428484610000', '仪表板', '001', '/welcome', 'glyphicon glyphicon-home', '1', '0', '集中显示各种数据，报表等。', '-1', '0', '0', '1', '0', '2014-10-24 22:17:01');
INSERT INTO `sys_res` VALUES ('4028388149428faf0149429c02770000', '系统日志', '012008', '/system/log', 'fa fa-history', '8', '1', '查看系统日志信息，包含所有功能的操作日志。', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2014-10-24 22:42:40');
INSERT INTO `sys_res` VALUES ('4028388149ebc54f0149ebcbf9e90000', '插件管理', '012009001', '/system/plugin', 'fa fa-puzzle-piece', '1', '1', '系统插件管理', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2014-11-26 19:10:53');
INSERT INTO `sys_res` VALUES ('402838814a192f8c014a1995ace60000', '通知中心', '012006', '/system/notify', 'fa fa-bell-o', '6', '1', '集中系统的各种通知', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2014-12-05 16:34:07');
INSERT INTO `sys_res` VALUES ('402838814a3c7b87014a3c82625e0000', '数据初始化', '012009008', '/system/senior/init', 'fa fa-rotate-left', '8', '1', '', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2014-12-12 11:19:45');
INSERT INTO `sys_res` VALUES ('402838814a512e0b014a5135c3720000', '数据字典', '012007', '/system/datadict', 'fa fa-database', '7', '1', '系统数据字典，公共数据配置/存储中心', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2014-12-16 11:48:05');
INSERT INTO `sys_res` VALUES ('402838814a534de9014a5355b4260000', 'SQL执行器', '012009007', '/system/senior/database', 'fa fa-terminal', '7', '1', '数据库管理', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2014-12-16 21:42:13');
INSERT INTO `sys_res` VALUES ('402838814ab3af30014ab3eaa8ee0000', '文件管理', '012009004', '/system/senior/sysfile', 'ti-files', '4', '1', '', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2015-01-04 15:48:28');
INSERT INTO `sys_res` VALUES ('402838814abaefca014abb31cf6b0011', '短信中心', '012009003', '/system/sms', 'fa fa-comments', '3', '1', '短信中心功能，包含已发短信，群发短信等功能', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2015-01-06 01:43:31');
INSERT INTO `sys_res` VALUES ('402847035e674fdc015e677823990011', '选项卡', '003005', '/doc/layout-tabs', '', '5', '1', '', '402881225e282634015e28300242000a', '0', '0', '1', '0', '2017-09-10 00:28:00');
INSERT INTO `sys_res` VALUES ('402847035e674fdc015e678073040018', '面板', '003004', '/doc/layout-panel', '', '4', '1', '', '402881225e282634015e28300242000a', '0', '0', '1', '0', '2017-09-10 00:37:04');
INSERT INTO `sys_res` VALUES ('402847035e674fdc015e679fd0c40020', '进度提示', '004003', '/doc/tip-progress', '', '3', '1', '', '402881225e282634015e283000c00006', '0', '0', '1', '0', '2017-09-10 01:11:20');
INSERT INTO `sys_res` VALUES ('402847035e711eb8015e71572fba000a', '业务相关', '010', '/doc/common', 'fa fa-briefcase', '10', '1', '', '-1', '1', '0', '1', '0', '2017-09-11 22:28:12');
INSERT INTO `sys_res` VALUES ('402847035e711eb8015e7157f4bf000e', '用户选择', '010001', '/doc/common-user', '', '1', '1', '', '402847035e711eb8015e71572fba000a', '0', '0', '1', '0', '2017-09-11 22:29:03');
INSERT INTO `sys_res` VALUES ('402847035e711eb8015e7157f5b00010', '角色选择', '010002', '/doc/common-role', '', '2', '1', '', '402847035e711eb8015e71572fba000a', '0', '0', '1', '0', '2017-09-11 22:29:03');
INSERT INTO `sys_res` VALUES ('402847035e711eb8015e7157f6910012', '机构选择', '010003', '/doc/common-unit', '', '3', '1', '', '402847035e711eb8015e71572fba000a', '0', '0', '1', '0', '2017-09-11 22:29:03');
INSERT INTO `sys_res` VALUES ('402847035e8626b0015e865c1d180004', '图表', '008', '/doc/charts', 'fa fa-line-chart', '8', '1', '', '-1', '1', '0', '1', '0', '2017-09-16 00:25:37');
INSERT INTO `sys_res` VALUES ('402847035e8626b0015e865d4a240008', 'chartjs', '008001', '/doc/charts-chartjs', '', '1', '1', '', '402847035e8626b0015e865c1d180004', '0', '0', '1', '0', '2017-09-16 00:26:54');
INSERT INTO `sys_res` VALUES ('402847035e8626b0015e865d4b30000a', 'echarts', '008002', '/doc/charts-echarts', '', '2', '1', '', '402847035e8626b0015e865c1d180004', '0', '0', '1', '0', '2017-09-16 00:26:54');
INSERT INTO `sys_res` VALUES ('402847035e8626b0015e865d4c4e000c', 'highcharts', '008003', '/doc/charts-highcharts', '', '3', '1', '', '402847035e8626b0015e865c1d180004', '0', '0', '1', '0', '2017-09-16 00:26:54');
INSERT INTO `sys_res` VALUES ('402847035e8626b0015e865d4d58000e', 'flot', '008004', '/doc/charts-flot', '', '4', '1', '', '402847035e8626b0015e865c1d180004', '0', '0', '1', '0', '2017-09-16 00:26:55');
INSERT INTO `sys_res` VALUES ('402847035eaf1dd8015eaf38ca77000a', '动态添加行', '009001', '/doc/ext-dynamicrow', '', '1', '1', '', '402881225ec261a2015ec2d4dae50015', '0', '0', '1', '0', '2017-09-23 22:51:28');
INSERT INTO `sys_res` VALUES ('402847035eaf1dd8015eaf38cb98000c', 'Excel导入/导出', '011001', '/doc/common-excel', '', '1', '1', '', '402847035eaf1dd8015eaf3cb7b50019', '0', '0', '1', '0', '2017-09-23 22:51:28');
INSERT INTO `sys_res` VALUES ('402847035eaf1dd8015eaf3cb7b50019', '后端相关', '011', '/doc/server', 'fa fa-cloud', '11', '1', '', '-1', '1', '0', '1', '0', '2017-09-23 22:55:45');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283000c00006', '提示', '004', '/doc/tip', 'fa fa-bell-o', '4', '1', '', '-1', '1', '0', '1', '0', '2017-08-28 17:33:08');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283001930008', '表单', '005', '/doc/form', 'fa fa-window-maximize', '5', '1', '', '-1', '1', '0', '1', '0', '2017-08-28 17:33:08');
INSERT INTO `sys_res` VALUES ('402881225e282634015e28300242000a', '布局', '003', '/doc/layout', 'fa fa-th-large', '3', '1', '', '-1', '1', '0', '1', '0', '2017-08-28 17:33:08');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283002ea000c', '元素', '006', '/doc/element', 'fa fa-tag', '6', '1', '', '-1', '1', '0', '1', '0', '2017-08-28 17:33:08');
INSERT INTO `sys_res` VALUES ('402881225e282634015e28300385000e', '模块', '007', '/doc/module', 'fa fa-cube', '7', '1', '', '-1', '1', '0', '1', '0', '2017-08-28 17:33:08');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2831a6300018', '弹窗式', '004001', '/doc/tip-modal', '', '1', '1', '', '402881225e282634015e283000c00006', '0', '0', '1', '0', '2017-08-28 17:34:55');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2831a71f001a', '内嵌式', '004002', '/doc/tip-inline', '', '2', '1', '', '402881225e282634015e283000c00006', '0', '0', '1', '0', '2017-08-28 17:34:56');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2832bb34001d', '左右布局', '003002', '/doc/layout-left', '', '2', '1', '', '402881225e282634015e28300242000a', '0', '0', '1', '0', '2017-08-28 17:36:06');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2832bd100021', '综合布局', '003006', '/doc/layout-summary', '', '6', '1', '', '402881225e282634015e28300242000a', '0', '0', '1', '0', '2017-08-28 17:36:07');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2834447f0026', '复选框/单选框', '005003', '/doc/form-checkbox', '', '3', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:37:47');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283445630028', '下拉表格', '005006', '/doc/form-combogrid', '', '6', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:37:47');
INSERT INTO `sys_res` VALUES ('402881225e282634015e28344646002a', '下拉树', '005007', '/doc/form-combotree', '', '7', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:37:48');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2834471a002c', '日期选择', '005008', '/doc/form-date', '', '8', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:37:48');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283447ef002e', '下拉选择', '005004', '/doc/form-select', '', '4', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:37:48');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283448cd0030', '下拉选择（多选）', '005005', '/doc/form-multiselect', '', '5', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:37:48');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283449b30032', '输入格式化', '005011', '/doc/form-mask', '', '11', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:37:48');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283577e00035', '颜色', '006001', '/doc/element-color', '', '1', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:39:06');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283578c00037', '图标', '006002', '/doc/element-icon', '', '2', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:39:06');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283579a70039', '动画', '006003', '/doc/element-animation', '', '3', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:39:06');
INSERT INTO `sys_res` VALUES ('402881225e282634015e28357a87003b', '进度条', '006004', '/doc/element-progress', '', '4', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:39:06');
INSERT INTO `sys_res` VALUES ('402881225e282634015e28357b6a003d', '时间线', '006005', '/doc/element-timeline', '', '5', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:39:07');
INSERT INTO `sys_res` VALUES ('402881225e282634015e28357c47003f', '表格', '006006', '/doc/element-table', '', '6', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:39:07');
INSERT INTO `sys_res` VALUES ('402881225e282634015e28357d750041', '辅助元素', '006007', '/doc/element-helper', '', '7', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:39:07');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2836ed9f0044', '上传组件', '007002', '/doc/module-upload', '', '2', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:40:41');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2836ee990046', '富文本编辑器', '007005', '/doc/module-editor', '', '5', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:40:42');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2836ef8c0048', '表单验证', '007007', '/doc/module-validator', '', '7', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:40:42');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2836f070004a', '树形菜单', '007008', '/doc/module-tree', '', '8', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:40:42');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2836f149004c', '树形表格', '007009', '/doc/module-treegrid', '', '9', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:40:42');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2836f230004e', '数据表格', '007010', '/doc/module-datagrid', '', '10', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:40:43');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2836f2ff0050', '代码高亮', '007011', '/doc/module-highlight', '', '11', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:40:43');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283728360053', '表单布局', '003003', '/doc/layout-form', '', '3', '1', '', '402881225e282634015e28300242000a', '0', '0', '1', '0', '2017-08-28 17:40:56');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2838fa590057', '按钮', '006008', '/doc/element-button', '', '8', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:42:56');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2838fb350059', '按钮组', '006009', '/doc/element-button-group', '', '9', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:42:56');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2838fc26005b', '按钮式下拉菜单', '006010', '/doc/element-button-contextmenu', '', '10', '1', '', '402881225e282634015e283002ea000c', '0', '0', '1', '0', '2017-08-28 17:42:56');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2838fd09005d', '输入框组', '005002', '/doc/form-input-group', '', '2', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-08-28 17:42:56');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283925790060', '弹窗', '007001', '/doc/module-modal', '', '1', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:43:07');
INSERT INTO `sys_res` VALUES ('402881225e282634015e2839fa900068', '入门指南', '002', '/doc/guide', 'fa fa-book', '2', '1', '', '-1', '0', '0', '1', '0', '2017-08-28 17:44:01');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283d3882006e', '栅格系统', '003001', '/doc/layout-grid', '', '1', '1', '', '402881225e282634015e28300242000a', '0', '0', '1', '0', '2017-08-28 17:47:34');
INSERT INTO `sys_res` VALUES ('402881225e282634015e283e82430076', '快速编辑', '007012', '/doc/module-xedit', '', '12', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-08-28 17:48:58');
INSERT INTO `sys_res` VALUES ('402881225e7400c1015e74fd973a0008', '时间选择', '005009', '/doc/form-time', '', '9', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-09-12 15:28:49');
INSERT INTO `sys_res` VALUES ('402881225e7400c1015e74fdd6f1000a', '日期时间选择', '005010', '/doc/form-datetime', '', '10', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-09-12 15:29:06');
INSERT INTO `sys_res` VALUES ('402881225e752fe5015e755174880004', '输入框', '005001', '/doc/form-input', '', '1', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-09-12 17:00:26');
INSERT INTO `sys_res` VALUES ('402881225e752fe5015e756948db000d', '表单验证', '005012', '/doc/form-validator', '', '12', '1', '', '402881225e282634015e283001930008', '0', '0', '1', '0', '2017-09-12 17:26:27');
INSERT INTO `sys_res` VALUES ('402881225ea8bc2f015ea901f3530004', '上传附件', '007003', '/doc/module-upload-attach', '', '3', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-09-22 17:53:50');
INSERT INTO `sys_res` VALUES ('402881225ea8bc2f015ea901f4430006', '上传图片', '007004', '/doc/module-upload-image', '', '4', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-09-22 17:53:51');
INSERT INTO `sys_res` VALUES ('402881225ec261a2015ec2d4dae50015', '扩展', '009', '/doc/ext', 'fa fa-code', '9', '1', '', '-1', '1', '0', '1', '0', '2017-09-27 18:14:43');
INSERT INTO `sys_res` VALUES ('402881225f00608f015f00616e530004', '富文本编辑器(NEditor)', '007006', '/doc/module-neditor', '', '6', '1', '', '402881225e282634015e28300385000e', '0', '0', '1', '0', '2017-10-09 17:05:06');
INSERT INTO `sys_res` VALUES ('4028b8815852021101585214bca00002', '参数配置', '012009002', '/system/config', 'fa fa-cogs', '2', '1', '', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2016-11-11 14:30:40');
INSERT INTO `sys_res` VALUES ('4028b8815885ab62015885bec7ac000a', '在线用户监控', '012009009', '/system/senior/monitor/online', 'fa fa-eye', '9', '1', '查看实时在线用户', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2016-11-21 15:17:02');
INSERT INTO `sys_res` VALUES ('4028b881596861ad0159686645d30002', 'IP黑名单', '012009005', '/system/senior/black/list', 'fa fa-expeditedssl', '5', '1', '符合规则的全部重定向到IP禁止访问页面', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2017-01-04 15:34:10');
INSERT INTO `sys_res` VALUES ('4028b881596861ad01596867de1e0004', 'URL路由', '012009006', '/system/senior/route', 'fa fa-link', '6', '1', '自定义路由，可以显性/隐性的跳转', '4028388148b2fe810148b32e9036002e', '0', '0', '1', '0', '2017-01-04 15:35:54');
INSERT INTO `sys_res` VALUES ('4028b8815a1c2a9c015a1c2c9b1e0002', '用户组管理', '012003', '/system/group', 'fa fa-group', '3', '1', '用户组', '4028388148ac878b0148ac8820090000', '0', '0', '1', '0', '2017-02-08 13:22:49');

-- ----------------------------
-- Table structure for sys_res_btn
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_btn`;
CREATE TABLE `sys_res_btn` (
  `id` varchar(32) NOT NULL,
  `res_id` varchar(32) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(10) NOT NULL,
  `create_user` varchar(32) NOT NULL,
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_res_btn$sys_res` (`res_id`) USING BTREE,
  CONSTRAINT `sys_res_btn_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `sys_res` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_res_btn
-- ----------------------------
INSERT INTO `sys_res_btn` VALUES ('4028b8815a691b59015a691ce3b60002', '4028388148ac878b0148ac8820090000', 'btnAdd', '新增', 'superadmin', '2017-02-23 11:56:25');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a6a07e5015a6a09b8730003', '4028388148b2fe810148b32ebc0d002f', 'btnAdd', '新增', 'superadmin', '2017-02-23 16:15:06');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a6a07e5015a6a09b8730004', '4028388148b2fe810148b32ebc0d002f', 'btnEdit', '修改', 'superadmin', '2017-02-23 16:15:06');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a6a07e5015a6a09b8730005', '4028388148b2fe810148b32ebc0d002f', 'btnRemove', '删除', 'superadmin', '2017-02-23 16:15:06');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cc7fb30005', '4028388148acb42f0148acd9a6170005', 'btnAdd', '新增用户', 'superadmin', '2017-02-24 23:45:34');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cc7fb30006', '4028388148acb42f0148acd9a6170005', 'btnEdit', '修改用户', 'superadmin', '2017-02-24 23:45:34');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cc7fb30007', '4028388148acb42f0148acd9a6170005', 'btnRemove', '删除用户', 'superadmin', '2017-02-24 23:45:34');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cc7fb30008', '4028388148acb42f0148acd9a6170005', 'btnEnable', '启用', 'superadmin', '2017-02-24 23:45:34');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cc7fb30009', '4028388148acb42f0148acd9a6170005', 'btnDisable', '禁用', 'superadmin', '2017-02-24 23:45:34');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ccf5b8000a', '4028b8815a1c2a9c015a1c2c9b1e0002', 'btnAdd', '添加用户组', 'superadmin', '2017-02-24 23:46:04');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ccf5b9000b', '4028b8815a1c2a9c015a1c2c9b1e0002', 'btnEdit', '修改用户组', 'superadmin', '2017-02-24 23:46:04');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ccf5b9000c', '4028b8815a1c2a9c015a1c2c9b1e0002', 'btnRemove', '删除用户组', 'superadmin', '2017-02-24 23:46:04');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce0267000d', '4028388148acb42f0148acccd58d0001', 'btnAdd', '新增角色', 'superadmin', '2017-02-24 23:47:13');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce0267000e', '4028388148acb42f0148acccd58d0001', 'btnEdit', '修改角色', 'superadmin', '2017-02-24 23:47:13');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce0267000f', '4028388148acb42f0148acccd58d0001', 'btnRemove', '删除角色', 'superadmin', '2017-02-24 23:47:13');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce02670010', '4028388148acb42f0148acccd58d0001', 'btnEnable', '启用', 'superadmin', '2017-02-24 23:47:13');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce02680011', '4028388148acb42f0148acccd58d0001', 'btnDisable', '禁用', 'superadmin', '2017-02-24 23:47:13');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce59890012', '4028388148b2fe810148b32ef7120030', 'btnAdd', '添加菜单', 'superadmin', '2017-02-24 23:47:35');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce59890013', '4028388148b2fe810148b32ef7120030', 'btnEdit', '修改菜单', 'superadmin', '2017-02-24 23:47:35');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce59890014', '4028388148b2fe810148b32ef7120030', 'btnRemove', '删除菜单', 'superadmin', '2017-02-24 23:47:35');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70ce9e9f0015', '402838814a192f8c014a1995ace60000', 'btnRead', '标记为已读', 'superadmin', '2017-02-24 23:47:53');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cf2d2d0016', '402838814a512e0b014a5135c3720000', 'btnAdd', '添加数据字典', 'superadmin', '2017-02-24 23:48:30');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cf2d2d0017', '402838814a512e0b014a5135c3720000', 'btnEdit', '修改数据字典', 'superadmin', '2017-02-24 23:48:30');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cf2d2d0018', '402838814a512e0b014a5135c3720000', 'btnRemove', '删除数据字典', 'superadmin', '2017-02-24 23:48:30');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cf70f70019', '4028388149428faf0149429c02770000', 'btnSearch', '查询', 'superadmin', '2017-02-24 23:48:47');
INSERT INTO `sys_res_btn` VALUES ('4028b8815a70c97d015a70cfbc15001a', '4028388149ebc54f0149ebcbf9e90000', 'btnUpload', '上传新插件', 'superadmin', '2017-02-24 23:49:06');

-- ----------------------------
-- Table structure for sys_res_column
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_column`;
CREATE TABLE `sys_res_column` (
  `id` varchar(32) NOT NULL,
  `res_id` varchar(32) DEFAULT NULL COMMENT '关联资源表',
  `code` varchar(20) NOT NULL COMMENT '编码',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` varchar(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_res_column$sys_res` (`res_id`) USING BTREE,
  CONSTRAINT `sys_res_column_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `sys_res` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源对应的字段';

-- ----------------------------
-- Records of sys_res_column
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  `perm` smallint(6) NOT NULL DEFAULT '1',
  `unit_id` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `descript` text,
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_role$unit` (`unit_id`) USING BTREE,
  CONSTRAINT `sys_role_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `sys_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('4028388148d681830148d6857c71000d', '系统管理员', '1', '40283881493c2aff01493c32e2a70000', '1', '拥有系统最高权限，所有资源权限，所有按钮权限', '2014-10-03 22:59:05');
INSERT INTO `sys_role` VALUES ('40283881497b0b0201497b2a16350004', '普通角色', '1', '40283881493c2aff01493c32e2a70000', '1', '具有一般权限', '2014-11-04 22:16:36');
INSERT INTO `sys_role` VALUES ('402881ee54eff98b0154f01a1b890059', '项目测试组', '1', '40283881493c2aff01493c32e2a70000', '1', '项目管理方面', '2016-05-27 10:45:23');

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `role_id` varchar(50) NOT NULL,
  `res_id` varchar(50) NOT NULL,
  KEY `FK_sys_role_res_role` (`role_id`) USING BTREE,
  KEY `FK_sys_role_res_res` (`res_id`) USING BTREE,
  CONSTRAINT `sys_role_res_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `sys_res` (`id`),
  CONSTRAINT `sys_role_res_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388148ac878b0148ac8820090000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388148acb42f0148acccd58d0001');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388148acb42f0148acd9a6170005');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388148b2fe810148b32e9036002e');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388148b2fe810148b32ebc0d002f');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388148b2fe810148b32ef7120030');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '40283881494264460149428484610000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388149428faf0149429c02770000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '4028388149ebc54f0149ebcbf9e90000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '402838814a192f8c014a1995ace60000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '402838814a3c7b87014a3c82625e0000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '402838814a512e0b014a5135c3720000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '402838814a534de9014a5355b4260000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '402838814ab3af30014ab3eaa8ee0000');
INSERT INTO `sys_role_res` VALUES ('4028388148d681830148d6857c71000d', '402838814abaefca014abb31cf6b0011');
INSERT INTO `sys_role_res` VALUES ('402881ee54eff98b0154f01a1b890059', '40283881494264460149428484610000');
INSERT INTO `sys_role_res` VALUES ('40283881497b0b0201497b2a16350004', '40283881494264460149428484610000');
INSERT INTO `sys_role_res` VALUES ('40283881497b0b0201497b2a16350004', '4028388148b2fe810148b32ebc0d002f');
INSERT INTO `sys_role_res` VALUES ('40283881497b0b0201497b2a16350004', '4028388148ac878b0148ac8820090000');
INSERT INTO `sys_role_res` VALUES ('40283881497b0b0201497b2a16350004', '4028388148acb42f0148acd9a6170005');

-- ----------------------------
-- Table structure for sys_role_res_btn
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res_btn`;
CREATE TABLE `sys_role_res_btn` (
  `id` varchar(50) NOT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `res_id` varchar(50) DEFAULT NULL,
  `btn_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_res_btn_role` (`role_id`) USING BTREE,
  KEY `FK_sys_role_res_btn_res` (`res_id`) USING BTREE,
  KEY `FK_sys_role_res_btn` (`btn_id`) USING BTREE,
  CONSTRAINT `sys_role_res_btn_ibfk_1` FOREIGN KEY (`btn_id`) REFERENCES `sys_res_btn` (`id`),
  CONSTRAINT `sys_role_res_btn_ibfk_2` FOREIGN KEY (`res_id`) REFERENCES `sys_res` (`id`),
  CONSTRAINT `sys_role_res_btn_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_res_btn
-- ----------------------------
INSERT INTO `sys_role_res_btn` VALUES ('4028b8815a6a2764015a6a30e41b001a', '40283881497b0b0201497b2a16350004', '4028388148b2fe810148b32ebc0d002f', '4028b8815a6a07e5015a6a09b8730003');
INSERT INTO `sys_role_res_btn` VALUES ('4028b8815a6a2764015a6a30e41b001b', '40283881497b0b0201497b2a16350004', '4028388148b2fe810148b32ebc0d002f', '4028b8815a6a07e5015a6a09b8730004');
INSERT INTO `sys_role_res_btn` VALUES ('4028b8815a6a2764015a6a30e41b001c', '40283881497b0b0201497b2a16350004', '4028388148b2fe810148b32ebc0d002f', '4028b8815a6a07e5015a6a09b8730005');
INSERT INTO `sys_role_res_btn` VALUES ('4028b8815a6a2764015a6a30e41c001d', '40283881497b0b0201497b2a16350004', '4028388148ac878b0148ac8820090000', '4028b8815a691b59015a691ce3b60002');

-- ----------------------------
-- Table structure for sys_role_res_column
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res_column`;
CREATE TABLE `sys_role_res_column` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL COMMENT '角色',
  `res_id` varchar(32) NOT NULL COMMENT '资源',
  `column_id` varchar(32) NOT NULL COMMENT '字段',
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_res_column$sys_res` (`res_id`) USING BTREE,
  KEY `FK_sys_role_res_column$sys_res_column` (`column_id`) USING BTREE,
  KEY `FK_sys_role_res_column$sys_role` (`role_id`) USING BTREE,
  CONSTRAINT `sys_role_res_column_ibfk_1` FOREIGN KEY (`res_id`) REFERENCES `sys_res` (`id`),
  CONSTRAINT `sys_role_res_column_ibfk_2` FOREIGN KEY (`column_id`) REFERENCES `sys_res_column` (`id`),
  CONSTRAINT `sys_role_res_column_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_res_column
-- ----------------------------

-- ----------------------------
-- Table structure for sys_route
-- ----------------------------
DROP TABLE IF EXISTS `sys_route`;
CREATE TABLE `sys_route` (
  `id` varchar(32) NOT NULL,
  `source_url` varchar(100) NOT NULL COMMENT '来源URL',
  `target_url` varchar(100) NOT NULL COMMENT '转向URL',
  `redirect` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否重定向',
  `internal` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否是内部链接',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_route
-- ----------------------------
INSERT INTO `sys_route` VALUES ('40289f395969874401596989bf0e0001', '/sohu', 'http://sohu.com', '1', '0', '0', '超级管理员', '2017-01-04 20:52:32');
INSERT INTO `sys_route` VALUES ('4028b8815968c11a015968d2f5040001', '/baidu/*', 'http://www.baidu.com', '1', '0', '0', '超级管理员', '2017-01-04 17:32:52');

-- ----------------------------
-- Table structure for sys_scheduler
-- ----------------------------
DROP TABLE IF EXISTS `sys_scheduler`;
CREATE TABLE `sys_scheduler` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '定时任务名称',
  `cron` varchar(50) NOT NULL COMMENT '任务执行时间',
  `job_class` varchar(255) NOT NULL COMMENT '任务类',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '任务状态',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `last_result` varchar(50) DEFAULT NULL COMMENT '最后一次执行结果',
  `last_exec_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次执行时间',
  `last_run_times` varchar(20) DEFAULT NULL COMMENT '最后一次运行耗时',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_scheduler
-- ----------------------------

-- ----------------------------
-- Table structure for sys_scheduler_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_scheduler_log`;
CREATE TABLE `sys_scheduler_log` (
  `id` varchar(32) NOT NULL,
  `scheduler_id` varchar(32) NOT NULL COMMENT '关联任务',
  `status` smallint(6) NOT NULL COMMENT '状态',
  `msg` varchar(255) DEFAULT NULL COMMENT '消息',
  `exec_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '执行时间',
  `times` varchar(255) NOT NULL COMMENT '运行时长',
  PRIMARY KEY (`id`),
  KEY `FK_sys_scheduler$sys_scheduler_log` (`scheduler_id`) USING BTREE,
  CONSTRAINT `sys_scheduler_log_ibfk_1` FOREIGN KEY (`scheduler_id`) REFERENCES `sys_scheduler` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_scheduler_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_sms
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms`;
CREATE TABLE `sys_sms` (
  `id` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `receiver` varchar(20) NOT NULL,
  `send_time` varchar(20) DEFAULT NULL,
  `state` int(11) NOT NULL COMMENT '1 待发送 2已发送 3 发送失败',
  `remarks` varchar(100) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_sms
-- ----------------------------

-- ----------------------------
-- Table structure for sys_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_test`;
CREATE TABLE `sys_test` (
  `a` char(1) NOT NULL,
  PRIMARY KEY (`a`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_test
-- ----------------------------

-- ----------------------------
-- Table structure for sys_unit
-- ----------------------------
DROP TABLE IF EXISTS `sys_unit`;
CREATE TABLE `sys_unit` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `contact` varchar(20) DEFAULT NULL COMMENT '联系人',
  `mobile` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` text COMMENT '地址',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `web` varchar(50) DEFAULT NULL COMMENT '网站',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '上级机构',
  `has_children` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有子机构',
  `system` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是系统机构',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `leader_id` varchar(32) DEFAULT NULL COMMENT '部门领导',
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_unit
-- ----------------------------
INSERT INTO `sys_unit` VALUES ('40283881493c2aff01493c32e2a70000', '内置组织', '001', '曾超', '18955185893', '安徽·合肥', 'zengc@nethsoft.com', 'http://www.nethsoft.com', '-1', '0', '1', '1', null, '2014-10-23 16:50:07');
INSERT INTO `sys_unit` VALUES ('402847035b9a4f3d015b9a59480f0017', '产品部', '002004', '', '', '安徽省合肥市高新区', '', '', '40288128575590470157562acf5401ba', '0', '0', '1', null, '2017-04-23 18:26:33');
INSERT INTO `sys_unit` VALUES ('402881225ec0ed46015ec22a2674000e', 'T', '002', '', '', '', '', '', '-1', '1', '0', '1', null, '2017-09-27 15:08:15');
INSERT INTO `sys_unit` VALUES ('402881225ec0ed46015ec22a42e10012', 'T1', '002001', '', '', '', '', '', '402881225ec0ed46015ec22a2674000e', '0', '0', '1', null, '2017-09-27 15:08:22');

-- ----------------------------
-- Table structure for sys_unit_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_unit_res`;
CREATE TABLE `sys_unit_res` (
  `unit_id` varchar(50) NOT NULL,
  `res_id` varchar(50) NOT NULL,
  KEY `FK_sys_res_unit` (`unit_id`) USING BTREE,
  CONSTRAINT `sys_unit_res_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `sys_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_unit_res
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(50) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `real_name` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `qq` varchar(15) DEFAULT NULL,
  `page_style` varchar(10) DEFAULT NULL,
  `layout` varchar(10) DEFAULT NULL COMMENT '页面布局',
  `enabled` tinyint(1) DEFAULT '0',
  `online` tinyint(1) DEFAULT '0',
  `create_time` varchar(20) NOT NULL,
  `last_login_time` varchar(20) DEFAULT NULL,
  `last_login_ip` varchar(30) DEFAULT NULL,
  `last_login_type` int(11) DEFAULT NULL,
  `head_image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `NewIndex1` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('402881ee547f564d01547f7dd49b002e', 'superadmin', 'C4CA4238A0B923820DCC509A6F75849B', '超级管理员', '', '', '', 'palette.6', '', '1', '1', '2016-05-05 13:57:13', '2018-03-06 09:40:26', '本地主机', '1', null);

-- ----------------------------
-- Table structure for sys_user_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_config`;
CREATE TABLE `sys_user_config` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `k` varchar(50) NOT NULL,
  `v` varchar(50) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL COMMENT '1启用 0不启用',
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_userconfig_user` (`user_id`) USING BTREE,
  CONSTRAINT `sys_user_config_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_email
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_email`;
CREATE TABLE `sys_user_email` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `mail_type` varchar(10) NOT NULL,
  `mail_host` varchar(50) NOT NULL,
  `mail_port` int(11) NOT NULL,
  `ssl_enable` tinyint(1) NOT NULL,
  `mail_user` varchar(50) NOT NULL,
  `mail_pass` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_email
-- ----------------------------
INSERT INTO `sys_user_email` VALUES ('402838814c217b07014c2197f5940000', '402838814acc869e014accac95bf000c', 'imap', 'imap.exmail.qq.com', '993', '1', 'zengc@nethsoft.com', 'Zengchao1992');
INSERT INTO `sys_user_email` VALUES ('402838814c277f4d014c27a376ca0000', '4028388148e09a0d0148e0d0af7d0002', 'pop', 'pop.qq.com', '110', '0', 'service@nethsoft.com', 'nethsoft2014');

-- ----------------------------
-- Table structure for sys_user_quick_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_quick_menu`;
CREATE TABLE `sys_user_quick_menu` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `name` varchar(10) NOT NULL COMMENT '菜单名',
  `url` varchar(500) NOT NULL COMMENT '链接地址',
  PRIMARY KEY (`id`),
  KEY `FK_sys_user_quick_menu$user` (`user_id`) USING BTREE,
  CONSTRAINT `sys_user_quick_menu_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户快捷菜单';

-- ----------------------------
-- Records of sys_user_quick_menu
-- ----------------------------
INSERT INTO `sys_user_quick_menu` VALUES ('402847035c3a2e6d015c3a84fedb0074', '402881ee547f564d01547f7dd49b002e', '仪表板', 'http://localhost:8080/jrelax-system/welcome');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `role_id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  KEY `FK_sys_user_role_user` (`user_id`) USING BTREE,
  KEY `FK_sys_user_role_role` (`role_id`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('4028388148d681830148d6857c71000d', '402881ee547f564d01547f7dd49b002e');

-- ----------------------------
-- Table structure for sys_user_unit
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_unit`;
CREATE TABLE `sys_user_unit` (
  `user_id` varchar(50) NOT NULL,
  `unit_id` varchar(50) NOT NULL,
  KEY `FK_sys_user_unit` (`user_id`) USING BTREE,
  KEY `FK_sys_user_unit_unit` (`unit_id`) USING BTREE,
  CONSTRAINT `sys_user_unit_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_unit_ibfk_2` FOREIGN KEY (`unit_id`) REFERENCES `sys_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_unit
-- ----------------------------
INSERT INTO `sys_user_unit` VALUES ('402881ee547f564d01547f7dd49b002e', '40283881493c2aff01493c32e2a70000');

-- ----------------------------
-- Table structure for sys_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_version`;
CREATE TABLE `sys_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(50) DEFAULT NULL,
  `build` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL,
  `update_server` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_version
-- ----------------------------
INSERT INTO `sys_version` VALUES ('1', '1.3', '49', '2016-05-25 22:10', 'svn://svn.nethsoft.com/alpaca/trunk/jrelax');
INSERT INTO `sys_version` VALUES ('2', '1.4', '50', '2016-05-26 17:46', 'svn://svn.nethsoft.com/alpaca/trunk/jrelax');
INSERT INTO `sys_version` VALUES ('3', '1.4.1', '50', '2016-09-02 11:45', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('4', '1.5', '98', '2016-10-25 11:45', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('5', '1.6', '121', '2016-11-21 15:40', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('6', '2.0', '138', '2016-12-08 20:40', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('7', '2.1', '138', '2016-12-08 20:40', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('8', '2.2', '176', '2017-01-10 00:00', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('9', '2.3', '206', '2017-02-09 16:15', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('10', '2.3.1', '208', '2017-02-10 14:00', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('11', '2.4', '222', '2017-02-23 21:48', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('12', '2.4.1', '230', '2017-02-27 16:44', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('14', '2.5', '234', '2017-03-04 00:12', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('15', '2.5.1', '251', '2017-03-12 16:51', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('16', '2.5.3', '258', '2017-03-16 11:11', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('17', '2.5.4', '302', '2017-05-24 20:58', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('18', '2.5.5', '345', '2017-07-04 14:11', 'svn://svn.nethsoft.com/jrelax/trunk');
INSERT INTO `sys_version` VALUES ('19', '2.6.0', '362', '2017-07-19 21:31', 'svn://svn.nethsoft.com/jrelax/trunk');
