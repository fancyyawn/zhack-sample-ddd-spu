CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `category_id` bigint(20) unsigned NOT NULL DEFAULT '0' ,
  `shop_id` bigint(20) unsigned NOT NULL DEFAULT '0' ,
  `parent_id` bigint(20) NOT NULL DEFAULT '0' ,
  `name` varchar(200)  NOT NULL DEFAULT '',
  `level` int(11) DEFAULT '1',
  `score` bigint(20) DEFAULT '0',
  `version` int(11) DEFAULT '1' ,
  `deleted` tinyint(4) DEFAULT '0',
  `created_at` datetime NOT NULL ,
  `updated_at` datetime NOT NULL ,
  PRIMARY KEY (`id`)
);


CREATE TABLE  IF NOT EXISTS `sale` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sale_id` bigint(20) NOT NULL,
  `shop_id` bigint(20) NOT NULL,
  `spu_id` bigint(20) NOT NULL,
  `sale_channel` varchar(16) NOT NULL DEFAULT '',
  `sku_sales` varchar(1024) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;


CREATE TABLE  IF NOT EXISTS `sku` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `concurrency_version` int(11) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `spu_id` bigint(20) DEFAULT NULL,
  `sku_id` bigint(20) DEFAULT NULL,
  `spec_tuple` varchar(512) DEFAULT NULL,
  `no` varchar(20) DEFAULT NULL,
  `bar_codes` varchar(256) DEFAULT NULL,
  `retail_price` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE  IF NOT EXISTS `spu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `concurrency_version` int(11) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `spu_id` bigint(20) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `no` varchar(20) DEFAULT NULL,
  `bar_codes` varchar(256) DEFAULT NULL,
  `photos` varchar(1024) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `unit_id` bigint(20) DEFAULT NULL,
  `spec_define_tuple` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE  IF NOT EXISTS `tbl_dispatcher_last_event` (
  `event_id` bigint(20) NOT NULL,
  PRIMARY KEY (`event_id`)
);


CREATE TABLE  IF NOT EXISTS `tbl_es_event_store` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_body` varchar(6500) NOT NULL,
  `event_type` varchar(250) NOT NULL,
  `stream_name` varchar(250) NOT NULL,
  `stream_version` int(11) NOT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `stream_name_2` (`stream_name`,`stream_version`)
) ;


CREATE TABLE  IF NOT EXISTS `tbl_stored_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_body` text NOT NULL,
  `occurred_on` datetime NOT NULL,
  `type_name` varchar(200) NOT NULL,
  PRIMARY KEY (`event_id`)
) ;


CREATE TABLE  IF NOT EXISTS `unit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) DEFAULT NULL,
  `unit_id` bigint(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  `concurrency_version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
