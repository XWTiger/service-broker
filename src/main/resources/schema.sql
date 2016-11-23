/*
MySQL Data Transfer
Source Host: localhost
Source Database: mir-service-broker
Target Host: localhost
Target Database: mir-service-broker
Date: 2016/7/8 9:44:03
*/
create table if not exists TaskStack
(
   id                       varchar(36) not null,
   requestUrl               text not null,
   eventType                varchar(255),
   eventId                  varchar(255),
   requestMethod            varchar(255) not null,
   params                   text not null,
   callBackUrl              varchar(255) not null,
   lockTask                     tinyint,
   addTime                  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   repeatTimes int(10) unsigned zerofill NOT NULL DEFAULT '0000000000',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists TaskResult
(
   id                       varchar(36) not null,
   eventType                varchar(255),
   eventId            varchar(255),
   oracleConnUrl             text   COMMENT 'oracle 连接地址',
   oracleDBAUser             varchar(255)  COMMENT 'oracle 连接用户',
   oracleDBAPassword         varchar(255)  COMMENT 'oracle 连接用户密码',
   userName                  varchar(255)  COMMENT '实例用户',
   userPassword              varchar(255)  COMMENT '实例用户密码',
   tableSpaceName            varchar(255)  COMMENT '表空间名字',
   tableSpaceSize            varchar(255)  COMMENT '表空间大小',
   tableSpaceMaxSize        varchar(255)  COMMENT '表空间最大值',
   tableSpaceRiseNumber     varchar(255)  COMMENT '表空间增加值',
   tableSpaceLocation        text  COMMENT '表空间dbf 存放位置',
   requestMethod            varchar(255) not null,
   requestUrl               text not null,
   eventParams                     text COMMENT '事件相关信息',
   resultStatus             varchar(64) not null,
   toWhParams                     text,
   addTime                  timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
   whReturnedParams text  COMMENT '回调Whitehole 返回的结果',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

