package com.zachary.springboot.blog.pushlian.mrdatasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author zachary
 * @desc 
 * 1.获取上下文中环境变量
 * 2.数据源构建
 * 3.数据源注册
 *
 */
public class DynamicDataSourceRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {

	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
	/**
	 *  主数据源、从数据源
	 */
	private final String msDataSource="master";
	private final String msDataSourceNode="spring.datasource.master";
	private final String msDataSourceNodeType="spring.datasource.master.type";
	private final String csDataSourceNode="spring.datasource.cluster";

	/**
	 * 配置上下文（也可以理解为配置文件的获取工具）
	 */
	private Environment evn;

	/**
	 * 别名
	 */
	private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

	/**
	 * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
	 */
	static {
		aliases.addAliases("url", new String[] { "jdbc-url" });
		aliases.addAliases("username", new String[] { "user" });
		//aliases.addAliases("initialSize", new String[] { "initial-size" });
		//aliases.addAliases("initial-size", new String[] { "initial-size" });
	}

	/**
	 * 存储我们注册的数据源
	 */
	private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();

	/**
	 * 参数绑定工具 springboot2.0新推出
	 */
	private Binder binder;

	/**
	 * ImportBeanDefinitionRegistrar接口的实现方法，通过该方法可以按照自己的方式注册bean
	 *
	 * @param annotationMetadata
	 * @param beanDefinitionRegistry
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
			BeanDefinitionRegistry beanDefinitionRegistry) {
		// 获取所有数据源配置
		Map<String,Object> config, defauleDataSourceProperties;
		defauleDataSourceProperties = binder.bind(msDataSourceNode, Map.class).get();
		// 获取数据源类型
		String typeStr = evn.getProperty(msDataSourceNodeType);
		// 获取数据源类型
		Class<? extends DataSource> clazz = getDataSourceType(typeStr);
		// 绑定默认数据源参数 也就是主数据源
		DataSource consumerDatasource, defaultDatasource = this.bind(clazz, defauleDataSourceProperties);
		DynamicDataSourceContextHolder.dataSourceIds.add(msDataSource);
		logger.info("注册主数据源成功,type:{}",msDataSource);
		// 获取其他数据源配置
		List<Map> configs = binder.bind(csDataSourceNode, Bindable.listOf(Map.class)).get();
		// 遍历从数据源
		for (int i = 0; i < configs.size(); i++) {
			config = configs.get(i);
			clazz = getDataSourceType((String) config.get("type"));
			defauleDataSourceProperties = config;
			// 绑定参数
			consumerDatasource = bind(clazz, defauleDataSourceProperties);
			// 获取数据源的key，以便通过该key可以定位到数据源
			String key = config.get("key").toString();
			customDataSources.put(key, consumerDatasource);
			// 数据源上下文，用于管理数据源与记录已经注册的数据源key
			DynamicDataSourceContextHolder.dataSourceIds.add(key);
			logger.info("注册数据源{}成功", key);
		}
		// bean定义类
		GenericBeanDefinition define = new GenericBeanDefinition();
		// 设置bean的类型，此处DynamicRoutingDataSource是继承AbstractRoutingDataSource的实现类
		define.setBeanClass(DynamicRoutingDataSource.class);
		// 需要注入的参数
		MutablePropertyValues mpv = define.getPropertyValues();
		// 添加默认数据源主库，避免key不存在的情况没有数据源可用
		mpv.add("defaultTargetDataSource", defaultDatasource);
		// 添加其他数据源
		mpv.add("targetDataSources", customDataSources);
		// 将该bean注册为datasource，不使用springboot自动生成的datasource
		beanDefinitionRegistry.registerBeanDefinition("datasource", define);
		logger.info("注册数据源成功，一共注册{}个数据源", customDataSources.keySet().size() + 1);
	}

	/**
	 * 通过字符串获取数据源class对象
	 *
	 * @param typeStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Class<? extends DataSource> getDataSourceType(String typeStr) {
		Class<? extends DataSource> type;
		try {
			if (!StringUtils.isBlank(typeStr)) {
				// 字符串不为空则通过反射获取class对象
				type = (Class<? extends DataSource>) Class.forName(typeStr);
			} else {
				// 默认为hikariCP数据源，与springboot默认数据源保持一致
				type = HikariDataSource.class;
			}
			return type;
		} catch (Exception e) {
			throw new IllegalArgumentException("can not resolve class with type: " + typeStr); // 无法通过反射获取class对象的情况则抛出异常，该情况一般是写错了，所以此次抛出一个runtimeexception
		}
	}

	/**
	 * 绑定参数，以下三个方法都是参考DataSourceBuilder的bind方法实现的，目的是尽量保证我们自己添加的数据源构造过程与springboot保持一致
	 *
	 * @param result
	 * @param properties
	 */
	/*
	 * @SuppressWarnings("unused") private void bind(DataSource result,
	 * Map<String,Object> properties) { ConfigurationPropertySource source = new
	 * MapConfigurationPropertySource(properties); Binder binder = new Binder(new
	 * ConfigurationPropertySource[] { source.withAliases(aliases) }); // 将参数绑定到对象
	 * binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result)); }
	 */

	/**
	   *  绑定参数，以下三个方法都是参考DataSourceBuilder的bind方法实现的，目的是尽量保证我们自己添加的数据源构造过程与springboot保持一致
	 *
	 * @param result
	 * @param properties
	 */
	@SuppressWarnings({ "rawtypes" })
	private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
		ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
		Binder binder = new Binder(new ConfigurationPropertySource[] { source.withAliases(aliases) });
		// 通过类型绑定参数并获得实例对象
		T t=binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
		if(t instanceof DruidDataSource) {//由于不同数据源未自动装配参数，故需要手动设置
			this.rmdDataSourceParams(t);
		}
		return t;
	}
	
	/**
	 * @desc 初始化 数据库参数到 DruidDataSource 实例对象
	 * @date 
	 */
	public void rmdDataSourceParams(DataSource dataSource) {
		DruidDataSource druidDataSource=(DruidDataSource)dataSource; 
		String initialSize=evn.getProperty("spring.datasource.druid.initial-size");
		String maxActive=evn.getProperty("spring.datasource.druid.max-active");
		String maxWait=evn.getProperty("spring.datasource.druid.max-wait");
		String minIdle=evn.getProperty("spring.datasource.druid.min-idle");
		String timeBetweenEvictionRunsMillis=evn.getProperty("spring.datasource.druid.time-between-eviction-runs-millis");
		String minEvictableIdleTimeMillis=evn.getProperty("spring.datasource.druid.min-evictable-idle-time-millis");
		String validationQuery=evn.getProperty("spring.datasource.druid.validation-query");
		String testWhileIdle=evn.getProperty("spring.datasource.druid.test-while-idle");
		String testOnBorrow=evn.getProperty("spring.datasource.druid.test-on-borrow");
		String testOnReturn=evn.getProperty("spring.datasource.druid.test-on-return");
		String poolPreparedStatements=evn.getProperty("spring.datasource.druid.pool-prepared-statements");
		String maxOpenPreparedStatements=evn.getProperty("spring.datasource.druid.max-open-prepared-statements");
		String maxPoolPreparedStatementPerConnectionSize=evn.getProperty("spring.datasource.druid.max-pool-prepared-statement-per-connection-size");
		//设置
		if(!StringUtils.isBlank(initialSize)) {
			druidDataSource.setInitialSize(Integer.parseInt(initialSize)); 
		} 
		if(!StringUtils.isBlank(maxActive)) {
			druidDataSource.setMaxActive(Integer.parseInt(maxActive)); 
		} 
		if(!StringUtils.isBlank(maxWait)) {
			druidDataSource.setMaxWait(Long.parseLong(maxWait)); 
		} 
		if(!StringUtils.isBlank(minIdle)) {
			druidDataSource.setMinIdle(Integer.parseInt(minIdle)); 
		} 
		if(!StringUtils.isBlank(timeBetweenEvictionRunsMillis)) {
			druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis)); 
		} 
		if(!StringUtils.isBlank(minEvictableIdleTimeMillis)) {
			druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis)); 
		} 
		if(!StringUtils.isBlank(validationQuery)) {
			druidDataSource.setValidationQuery(validationQuery); 
		} 
		if(!StringUtils.isBlank(testWhileIdle)) {
			druidDataSource.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle)); 
		} 
		if(!StringUtils.isBlank(testOnBorrow)) {
			druidDataSource.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow)); 
		} 
		if(!StringUtils.isBlank(testOnReturn)) {
			druidDataSource.setTestOnReturn(Boolean.parseBoolean(testOnReturn)); 
		} 
		if(!StringUtils.isBlank(poolPreparedStatements)) {
			druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(poolPreparedStatements)); 
		} 
		if(!StringUtils.isBlank(maxOpenPreparedStatements)) {
			druidDataSource.setMaxOpenPreparedStatements(Integer.parseInt(initialSize)); 
		} 
		if(!StringUtils.isBlank(maxPoolPreparedStatementPerConnectionSize)) {
			druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(maxPoolPreparedStatementPerConnectionSize)); 
		} 
	}

	/**
	 * @param clazz
	 * @param sourcePath 参数路径，对应配置文件中的值，如: spring.datasource
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private <T extends DataSource> T bind(Class<T> clazz, String sourcePath) {
		Map properties = binder.bind(sourcePath, Map.class).get();
		return this.bind(clazz, properties);
	}

	/**
	 * EnvironmentAware接口的实现方法，通过aware的方式注入，此处是environment对象
	 *
	 * @param environment
	 */
	@Override
	public void setEnvironment(Environment environment) {
		logger.info("开始注册数据源");
		this.evn = environment;
		// 绑定配置器
		binder = Binder.get(evn);
	}
}
