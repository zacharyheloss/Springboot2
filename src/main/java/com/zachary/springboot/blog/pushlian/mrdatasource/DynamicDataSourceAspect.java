package com.zachary.springboot.blog.pushlian.mrdatasource;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {
	
	private static final Logger logger=LoggerFactory.getLogger(DynamicDataSourceAspect.class);
	private static final String dataSource="master";
	
	@Pointcut("execution(* com.zachary.springboot.blog.pushlian.rest..*.*(..))")
	public void rest() {
	}
	
	@Pointcut("execution(* com.zachary.springboot.blog.pushlian.dao..*.*(..))")
	public void dao() {
	}
	
	@Before("@annotation(ds)")
	public void log(JoinPoint joinpoint,DataSource ds) {
		if(ds!=null) {
			String dsId = ds.value();
	        if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
	            logger.debug("数据源配置正常 ,数据源: "+dsId);
	            DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
	        } else {
	            logger.info("数据源配置错误，ds:{},默认走主库", dsId, joinpoint.getSignature());
	            DynamicDataSourceContextHolder.setDataSourceRouterKey(dataSource);
	        }
		}else {
			 logger.warn("AOP切换获取数据源为空；默认走主库");
			 DynamicDataSourceContextHolder.setDataSourceRouterKey(dataSource);
		}

	}
	
	@After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSource ds) {
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
    }


}
