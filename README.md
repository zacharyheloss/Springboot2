
项目构建编译 ： Springboot2.2 + gradle3

持久层 ：mybatis+ + druid + mysql5.7/兼容H2内存数据库

搜索引擎： ES+JPA

spring容器特性 ： 拦截器+ 过滤器+全局异常处理

多数据源： JDK动态代理

前后端交互 跨域+自定义header

集成jsp-jstl、编译成War单独部署tomcat、jetty可正常访问

 集成redis集群 （底层通过lettuce 连接客户端，lettuce基于netty多线程并发性能高）


CREATE TABLE `roncoo_user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(36) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
