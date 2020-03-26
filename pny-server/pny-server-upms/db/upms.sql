CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `accountId` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码，密文',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `phone` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `status` int(1) DEFAULT '1' COMMENT '状态 1：启用2：禁用',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色id',
  `createPersonId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人Id',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建日期',
  `updatedPersonId` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人id',
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_roleId` (`roleId`) USING BTREE,
  KEY `idx_accountId` (`accountId`) USING BTREE,
  KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='upms用户表';



