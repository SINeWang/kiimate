SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `km_i_ins`
-- ----------------------------
DROP TABLE IF EXISTS `km_i_ins`;
CREATE TABLE `km_i_ins` (
  `id` varchar(160) NOT NULL,
  `commit` varchar(160) NOT NULL,
  `owner_id` varchar(160) NOT NULL,
  `sub_id` varchar(160) NOT NULL,
  `ext_id` varchar(160) NOT NULL,
  `int_id` varchar(160) NOT NULL,
  `field` varchar(64) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `value_set` varchar(160) DEFAULT NULL,
  `s_sub_id` varchar(160) DEFAULT NULL,
  `operator_id` varchar(160) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`begin_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `km_s_pub`
-- ----------------------------
DROP TABLE IF EXISTS `km_s_pub`;
CREATE TABLE `km_s_pub` (
  `id` varchar(160) NOT NULL,
  `pub_set` varchar(160) NOT NULL,
  `provider_id` varchar(160) NOT NULL,
  `m_sub_id` varchar(160) NOT NULL,
  `ins_id` varchar(160) NOT NULL,
  `version` varchar(32) NOT NULL,
  `visibility` varchar(32) NOT NULL,
  `stability` varchar(32) NOT NULL,
  `operator_id` varchar(32) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `km_s_sub`
-- ----------------------------
DROP TABLE IF EXISTS `km_s_sub`;
CREATE TABLE `km_s_sub` (
  `id` varchar(160) NOT NULL,
  `subscriber_id` varchar(32) NOT NULL,
  `sub_set` varchar(160) NOT NULL,
  `operator_id` varchar(32) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
