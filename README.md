# mulitDataSource

#
修改resources目录下application-env.yml配置文件中的数据库密码
#
创建两个数据库名字分别为dataSourceTwo,dataSourceOne.

#
在dataSourceOne中执行以下数据语句：
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


#
在dataSourceTwo库中执行以下数据库语句：

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `datasource2`
-- ----------------------------
DROP TABLE IF EXISTS `datasource2`;
CREATE TABLE `datasource2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '???id',
  `name` varchar(255) NOT NULL COMMENT '?????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `datasource2`
-- ----------------------------
BEGIN;
INSERT INTO `datasource2` VALUES ('1', 'dataSourceTwo');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


#
yml文件中配置的默认数据库配置库为dataSourceOne

#
浏览器访问 http://localhost:8080/one 正常返回结果，因为dataSourceOne库中有表datasource

#
浏览器访问 http://localhost:8080/two 正常返回结果，因为dataSourceTwo库中有表datasource2

#
浏览器访问 http://localhost:8080/dao 抛出异常，因为dataSourceTwo中无datasource表

#
浏览器访问 http://localhost:8080/test 观察控制台输出，可观察多层嵌套时，选择的数据源(遵守就近原则)



