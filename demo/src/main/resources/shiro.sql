SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `privilege`
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL COMMENT '上级权限id',
  `name` varchar(128) NOT NULL COMMENT '权限名称',
  `code` varchar(128) NOT NULL COMMENT '权限code',
  `level` int(11) NOT NULL COMMENT '级别：1，2，3',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `privilege_parent_id_index` (`parent_id`),
  KEY `privilege_code_index` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6081 DEFAULT CHARSET=utf8 COMMENT='权限表';

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `role_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `role_privilege`;
CREATE TABLE `role_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `privilege_id` bigint(20) NOT NULL COMMENT '权限id',
  `privilege_code` varchar(128) NOT NULL COMMENT '权限码',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `role_privilege_role_id_index` (`role_id`),
  KEY `role_privilege_privilege_code_index` (`privilege_code`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '角色名称',
  `description` varchar(1024) NOT NULL COMMENT '角色描述',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user_role`

-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_user_id_index` (`user_id`),
  KEY `user_role_role_id_index` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `status` int(11) NOT NULL COMMENT '用户状态：0-正常，1-禁用',
  `name` varchar(128) NOT NULL COMMENT '姓名',
  `phone` varchar(128) NOT NULL COMMENT '手机号',
  `email` varchar(128) NOT NULL COMMENT '电子邮箱',
  `identity` varchar(128) NOT NULL COMMENT '身份证',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `salt` varchar(128) NOT NULL COMMENT '盐值',
  `police_no` varchar(128) NOT NULL COMMENT '警号',
  `ip_limit` varchar(512) DEFAULT NULL COMMENT 'ip限制',
  `mac_limit` varchar(512) DEFAULT NULL COMMENT 'mac限制',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_identity_index` (`identity`),
  KEY `user_department_id_index` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;