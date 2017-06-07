SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE mm_m_ext
(
  id         VARCHAR(160) NOT NULL,
  `group`    VARCHAR(64)  NOT NULL,
  name       VARCHAR(64)  NOT NULL,
  tree       VARCHAR(64)  NOT NULL,
  owner_id   VARCHAR(160) NOT NULL,
  visibility VARCHAR(16)  NOT NULL
  COMMENT 'the visibility of scope',
  begin_time DATETIME     NOT NULL,
  end_time   DATETIME     NULL,
  commit     VARCHAR(160) NOT NULL,
  PRIMARY KEY (id, begin_time)
)
  COMMENT 'extension of concept';

CREATE TABLE mm_m_int
(
  id            VARCHAR(160)           NOT NULL,
  ext_id        VARCHAR(160)           NOT NULL,
  field         VARCHAR(64) DEFAULT '' NOT NULL
  COMMENT 'the alias name of ref_id ',
  is_single     TINYINT(1)             NOT NULL,
  visibility    VARCHAR(16)            NOT NULL
  COMMENT 'the visibility of scope',
  structure     VARCHAR(16)            NULL,
  ref_m_pub_set VARCHAR(160)           NULL,
  is_required   TINYINT(1)             NOT NULL,
  begin_time    DATETIME               NOT NULL,
  end_time      DATETIME               NULL,
  commit        VARCHAR(160)           NOT NULL,
  operator_id   VARCHAR(32)            NOT NULL,
  PRIMARY KEY (id, begin_time)
)
  COMMENT 'record of concept';

CREATE TABLE mm_m_pub
(
  id          VARCHAR(160) NOT NULL
    PRIMARY KEY,
  pub_set     VARCHAR(160) NULL
  COMMENT ' hash all pubs id(sorted) at once',
  provider_id VARCHAR(160) NOT NULL,
  ext_id      VARCHAR(160) NOT NULL,
  int_id      VARCHAR(160) NOT NULL,
  version     VARCHAR(64)  NOT NULL,
  stability   VARCHAR(64)  NOT NULL,
  operator_id VARCHAR(160) NOT NULL,
  begin_time  DATETIME     NOT NULL,
  end_time    DATETIME     NULL
)
  COMMENT 'model extensionPublication';

CREATE TABLE mm_m_sub
(
  id            VARCHAR(160) NOT NULL,
  sub_set       VARCHAR(160) NOT NULL
  COMMENT 'pub_set_hash',
  subscriber_id VARCHAR(160) NOT NULL,
  `group`       VARCHAR(64)  NOT NULL,
  name          VARCHAR(64)  NOT NULL,
  tree          VARCHAR(64)  NOT NULL,
  operator_id   VARCHAR(64)  NOT NULL,
  begin_time    DATETIME     NOT NULL,
  end_time      DATETIME     NULL,
  PRIMARY KEY (id, begin_time)
)
  COMMENT 'model subscribe';


SET FOREIGN_KEY_CHECKS = 1;
