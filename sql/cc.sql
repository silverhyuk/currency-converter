CREATE DATABASE /*!32312 IF NOT EXISTS*/cc /*!40100 DEFAULT CHARACTER SET utf8 */;

USE cc;

/*Table structure for table currency_api_history */

DROP TABLE IF EXISTS currency_api_history;

CREATE TABLE currency_api_history (
  currency_api_history_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '통화정보 기록 아이디',
  currency_api_datetime datetime NOT NULL COMMENT '통화 api 응답 데이터 시간',
  exchange_rates text NOT NULL COMMENT '환율정보(Json)',
  reg_datetime datetime NOT NULL COMMENT '기록시간',
  PRIMARY KEY (currency_api_history_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS currency_code;

CREATE TABLE currency_code (
 currency_code_id int(11) NOT NULL AUTO_INCREMENT,
 currency_code varchar(4) NOT NULL,
 currency_number int(11) DEFAULT NULL,
 currency_name varchar(128) DEFAULT NULL,
 reg_datetime datetime NOT NULL,
 PRIMARY KEY (currency_code_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

