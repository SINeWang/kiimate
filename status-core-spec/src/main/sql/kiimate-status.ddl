SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mm_i_ins`
-- ----------------------------
DROP TABLE IF EXISTS `mm_i_ins`;
CREATE TABLE `mm_i_ins` (
  `id` varchar(160) NOT NULL,
  `commit` varchar(160) NOT NULL,
  `owner_id` varchar(160) NOT NULL,
  `sub_id` varchar(160) NOT NULL,
  `ext_id` varchar(160) NOT NULL,
  `int_id` varchar(160) NOT NULL,
  `field` varchar(64) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `value_set` varchar(160) DEFAULT NULL,
  `value_ref_path` varchar(160) DEFAULT NULL,
  `value_ref_policy` varchar(64) DEFAULT NULL,
  `operator_id` varchar(160) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`begin_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# -- ----------------------------
# --  Table structure for `mm_i_tag`
# -- ----------------------------
# DROP TABLE IF EXISTS `mm_i_tag`;
# CREATE TABLE `mm_i_tag` (
#   `tag_id` varchar(160) NOT NULL,
#   `owner_id` varchar(160) NOT NULL,
#   `ext_id` varchar(160) NOT NULL,
#   `visibility` varchar(16) NOT NULL,
#   `operator_id` varchar(160) NOT NULL,
#   `begin_time` datetime NOT NULL,
#   `end_time` datetime DEFAULT NULL,
#   PRIMARY KEY (`tag_id`,`owner_id`,`ext_id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `km_s_pub`;
CREATE TABLE km_s_pub
(
	id VARCHAR(160) NOT NULL,
	pub_set VARCHAR(160) NOT NULL,
	provider_id VARCHAR(160) NOT NULL,
	m_sub_id VARCHAR(160) NOT NULL,
  ins_id VARCHAR(160) NOT NULL,
	version VARCHAR(32) NOT NULL,
	visibility VARCHAR(32) NOT NULL,
	stability VARCHAR(32) NOT NULL,
  operator_id VARCHAR(32) NOT NULL,
	begin_time datetime NOT NULL,
	end_time datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `km_s_sub`;
CREATE TABLE km_s_sub
(
  id VARCHAR(160) NOT NULL,
  subscriber_id VARCHAR(32) NOT NULL,
  sub_set VARCHAR(160) NOT NULL,
  `group` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `tree` varchar(64) NOT NULL,
  operator_id VARCHAR(32) NOT NULL,
  begin_time datetime NOT NULL,
  end_time datetime null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
