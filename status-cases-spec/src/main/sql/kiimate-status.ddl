SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mm_i_ins`
-- ----------------------------
DROP TABLE IF EXISTS `mm_i_ins`;
CREATE TABLE `mm_i_ins` (
  `id` varchar(160) NOT NULL,
  `owner_id` varchar(160) NOT NULL,
  `sub_id` varchar(160) NOT NULL,
  `ext_id` varchar(160) NOT NULL,
  `int_id` varchar(160) NOT NULL,
  `field` varchar(64) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `value_set_hash` varchar(160) DEFAULT NULL,
  `value_ref_id` varchar(160) DEFAULT NULL,
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

SET FOREIGN_KEY_CHECKS = 1;
