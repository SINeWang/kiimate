SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `km_m_crf`
-- ----------------------------
DROP TABLE IF EXISTS `km_m_crf`;
CREATE TABLE `km_m_crf` (
  `id` varchar(160) NOT NULL COMMENT 'id = hash(int_id, exc_field, inc_field)',
  `int_id` varchar(160) NOT NULL,
  `exc_field` varchar(64) DEFAULT NULL,
  `inc_field` varchar(64) DEFAULT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cross-reference of subscription';

-- ----------------------------
--  Table structure for `km_m_ext`
-- ----------------------------
DROP TABLE IF EXISTS `km_m_ext`;
CREATE TABLE `km_m_ext` (
  `id` varchar(160) NOT NULL,
  `commit` varchar(160) NOT NULL,
  `group` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `tree` varchar(64) NOT NULL,
  `owner_id` varchar(160) NOT NULL,
  `visibility` varchar(16) NOT NULL COMMENT 'the visibility of scope',
  `operator_id` varchar(160) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`begin_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='subscription of concept';

-- ----------------------------
--  Table structure for `km_m_int`
-- ----------------------------
DROP TABLE IF EXISTS `km_m_int`;
CREATE TABLE `km_m_int` (
  `id` varchar(160) NOT NULL,
  `commit` varchar(160) NOT NULL,
  `ext_id` varchar(160) NOT NULL,
  `field` varchar(64) NOT NULL DEFAULT '' COMMENT 'the alias name of ref_id ',
  `is_single` tinyint(1) NOT NULL,
  `visibility` varchar(16) NOT NULL COMMENT 'the visibility of scope',
  `structure` varchar(16) DEFAULT NULL,
  `ref_m_pub_set` varchar(160) DEFAULT NULL,
  `is_required` tinyint(1) NOT NULL,
  `operator_id` varchar(160) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`begin_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='subscription of concept';

-- ----------------------------
--  Table structure for `km_m_pub`
-- ----------------------------
DROP TABLE IF EXISTS `km_m_pub`;
CREATE TABLE `km_m_pub` (
  `id` varchar(160) NOT NULL,
  `pub_set` varchar(160) DEFAULT NULL COMMENT ' hash all pubs id(sorted) at once',
  `provider_id` varchar(160) NOT NULL,
  `ext_id` varchar(160) NOT NULL,
  `int_id` varchar(160) NOT NULL,
  `version` varchar(64) NOT NULL,
  `stability` varchar(64) NOT NULL,
  `operator_id` varchar(160) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='model extensionPublication';

-- ----------------------------
--  Table structure for `km_m_sub`
-- ----------------------------
DROP TABLE IF EXISTS `km_m_sub`;
CREATE TABLE `km_m_sub` (
  `id` varchar(160) NOT NULL,
  `sub_set` varchar(160) NOT NULL,
  `subscriber_id` varchar(160) NOT NULL,
  `group` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `tree` varchar(64) NOT NULL,
  `operator_id` varchar(64) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`begin_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='model subscribe';

SET FOREIGN_KEY_CHECKS = 1;
