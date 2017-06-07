SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE km_s_pub
(
  id          VARCHAR(160) NOT NULL,
  pub_set     VARCHAR(160) NOT NULL,
  provider_id VARCHAR(160) NOT NULL,
  m_sub_id    VARCHAR(160) NOT NULL,
  ins_id      VARCHAR(160) NOT NULL,
  version     VARCHAR(32)  NOT NULL,
  visibility  VARCHAR(32)  NOT NULL,
  stability   VARCHAR(32)  NOT NULL,
  operator_id VARCHAR(32)  NOT NULL,
  begin_time  DATETIME     NOT NULL,
  end_time    DATETIME     NULL
);

CREATE TABLE km_s_sub
(
  id            VARCHAR(160) NOT NULL,
  subscriber_id VARCHAR(32)  NOT NULL,
  sub_set       VARCHAR(160) NOT NULL,
  operator_id   VARCHAR(32)  NOT NULL,
  begin_time    DATETIME     NOT NULL,
  end_time      DATETIME     NULL
);

CREATE TABLE mm_i_ins
(
  id               VARCHAR(160) NOT NULL,
  owner_id         VARCHAR(160) NOT NULL,
  sub_id           VARCHAR(160) NOT NULL,
  ext_id           VARCHAR(160) NOT NULL,
  int_id           VARCHAR(160) NOT NULL,
  field            VARCHAR(64)  NOT NULL,
  value            VARCHAR(255) NULL,
  value_set        VARCHAR(160) NULL,
  value_ref_path   VARCHAR(160) NULL,
  value_ref_policy VARCHAR(64)  NULL,
  operator_id      VARCHAR(160) NOT NULL,
  begin_time       DATETIME     NOT NULL,
  end_time         DATETIME     NULL,
  commit           VARCHAR(160) NOT NULL,
  PRIMARY KEY (id, begin_time)
);


SET FOREIGN_KEY_CHECKS = 1;
