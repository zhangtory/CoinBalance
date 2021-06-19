CREATE TABLE `record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `exchange` varchar(32) NOT NULL COMMENT '交易所',
  `account` varchar(32) NOT NULL COMMENT '账户类型',
  `currency` varchar(16) NOT NULL COMMENT '币种',
  `amount` decimal(20,10) NOT NULL COMMENT '总额',
  `usd` decimal(20,3) NOT NULL COMMENT '美元计价',
  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



CREATE TABLE `lock_balance` (
  `id` int(10) NOT NULL,
  `exchange` varchar(32) NOT NULL COMMENT '交易所',
  `account` varchar(32) NOT NULL COMMENT '账户类型',
  `currency` varchar(16) NOT NULL COMMENT '币种',
  `amount` decimal(20,10) NOT NULL COMMENT '总额',
  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



