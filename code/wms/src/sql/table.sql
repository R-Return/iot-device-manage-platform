DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '名字',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `role_id` int(11) DEFAULT 1 COMMENT '角色 0超级管理员，1管理员，2普通账号',
  `isValid` varchar(4) DEFAULT 'Y' COMMENT '是否有效，Y有效，其他无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '111', '111', 'sa@qq.com', 1, 'Y');
COMMIT;

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `menuCode` varchar(8) DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
  `menuComponent` varchar(200) DEFAULT NULL,
  `menuIcon` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


BEGIN;
INSERT INTO `menu` VALUES (1, '001', '设备配置', '1', NULL, 'Config', '0,1', 'main/DeviceConfig.vue', 'el-icon-edit-outline');
INSERT INTO `menu` VALUES (2, '002', '数据查询', '1', NULL, 'Query', '0,1', 'main/DataQuery.vue', 'el-icon-s-data');
INSERT INTO `menu` VALUES (3, '003', '地图显示', '1', NULL, 'Map', '0', 'main/Map.vue', 'el-icon-location');
COMMIT;

DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
    `id` int(11) NOT NULL AUTO_INCREMENT primary key ,
    `name` varchar(100) NOT NULL COMMENT '设备名称',
    `description` varchar(200) DEFAULT NULL COMMENT '设备描述',
    `userid` int(11) NOT NULL COMMENT '用户id',
    `type` int NOT NULL default 4 COMMENT '设备类型,默认为其他设备（4）车载设备（1）、智能家居（2）、可穿戴设备（3）',
    `active` int NOT NULL DEFAULT 1 COMMENT '是否活跃'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO `device` VALUES (1, 'device0001', '设备1描述', 1, 1, 1);
INSERT INTO `device` VALUES (2, 'device0002', '设备2描述', 1, 2, 1);
INSERT INTO `device` VALUES (3, 'device0003', '设备3描述', 1, 3, 1);
INSERT INTO `device` VALUES (4, 'device0004', '设备4描述', 1, 4, 1);
INSERT INTO `device` VALUES (5, 'device0005', '设备5描述', 1, 1, 1);
COMMIT;

DROP TABLE IF EXISTS `message_form`;
CREATE TABLE `message_form`(
    `id` int(11) NOT NULL AUTO_INCREMENT primary key,
    `deviceId` int(11) NOT NULL COMMENT '设备id',
    `alert` int NOT NULL COMMENT '是否触发警报，0表示正常，1表示触发警报',
    `info` varchar(128) default null COMMENT '警报信息',
    `lat` double NOT NULL default 0 COMMENT '纬度',
    `lng` double NOT NULL default 0 COMMENT '经度',
    `timeStamp` VARCHAR(128) NOT NULL COMMENT '时间戳',
    `value` int default 0 COMMENT '设备发送的值'
#     foreign key (deviceId) references device(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
