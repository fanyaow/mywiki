DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`(
    `id`       BIGINT NOT NULL COMMENT 'id',
    `name`     VARCHAR(32) COMMENT '名称',
    `password` VARCHAR(32) COMMENT '密码',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT '测试';

DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo`(
    `id`       BIGINT NOT NULL COMMENT 'id',
    `name`     VARCHAR(32) COMMENT '名称',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT 'demo';