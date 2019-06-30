package com.zachary.springboot.blog.pushlian.mrdatasource;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DynamicDataSourceAspect {
	
	private static final org.slf4j.Logger logger=LoggerFactory.getLogger(DynamicDataSourceAspect.class);
	
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
	            logger.debug("数据源配置正常 "+dsId);
	        } else {
	            logger.info("数据源配置错误，ds:{},point:{}", dsId, joinpoint.getSignature());
	        }
		}else {
			 DynamicDataSourceContextHolder.setDataSourceRouterKey("master");
		}

	}
	
	@After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSource ds) {
		if(ds!=null) {
			logger.debug("Revert DataSource : " + ds.value() + " > " + point.getSignature());
		}
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();

    }


}
