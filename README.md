
项目构建编译 ： Springboot2.2 + gradle3

持久层 ：mybatis+ + druid + mysql5.7/兼容H2内存数据库

搜索引擎： ES+JPA

spring容器特性 ： 拦截器+ 过滤器+全局异常处理

多数据源： JDK动态代理

前后端交互 跨域+自定义header


CREATE TABLE `roncoo_user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(36) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
