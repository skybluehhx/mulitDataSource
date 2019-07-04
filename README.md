# mulitDataSource


创建两个数据库名字分别为dataSourceTwo,dataSourceOne

在dataSourceTwo中执行以下数据语句
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `datasource`
-- ----------------------------
DROP TABLE IF EXISTS `datasource`;
CREATE TABLE `datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '???id',
  `name` varchar(255) NOT NULL COMMENT '?????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `datasource`
-- ----------------------------
BEGIN;
INSERT INTO `datasource` VALUES ('1', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
数据库此时默认数据库配置库为dataSourceOne

浏览器访问 "http://localhost:8080/test" 正常返回结果，因为该库中有表datasource

浏览器访问 http://localhost:8080/test?dataSource=dataSourceTwo 抛出异常，因为该库中无表datasource


