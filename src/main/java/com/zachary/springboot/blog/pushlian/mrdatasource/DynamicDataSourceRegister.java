/*
 * package com.zachary.springboot.blog.pushlian.mrdatasource;
 * 
 * import java.util.HashMap; import java.util.List; import java.util.Map;
 * 
 * import javax.sql.DataSource;
 * 
 * import org.apache.commons.lang3.StringUtils; import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory; import
 * org.springframework.beans.MutablePropertyValues; import
 * org.springframework.beans.factory.support.BeanDefinitionRegistry; import
 * org.springframework.beans.factory.support.GenericBeanDefinition; import
 * org.springframework.boot.context.properties.bind.Bindable; import
 * org.springframework.boot.context.properties.bind.Binder; import
 * org.springframework.boot.context.properties.source.ConfigurationPropertyName;
 * import org.springframework.boot.context.properties.source.
 * ConfigurationPropertyNameAliases; import
 * org.springframework.boot.context.properties.source.
 * ConfigurationPropertySource; import
 * org.springframework.boot.context.properties.source.
 * MapConfigurationPropertySource; import
 * org.springframework.context.EnvironmentAware; import
 * org.springframework.context.annotation.ImportBeanDefinitionRegistrar; import
 * org.springframework.core.env.Environment; import
 * org.springframework.core.type.AnnotationMetadata;
 * 
 * import com.zaxxer.hikari.HikariDataSource;
 * 
 *//**
	 * ��̬����Դע�� ʵ�� ImportBeanDefinitionRegistrar ʵ������Դע�� ʵ�� EnvironmentAware
	 * ���ڶ�ȡapplication.yml����
	 * 
	 * @param <V>
	 */
/*
 * public class DynamicDataSourceRegister<V> implements
 * ImportBeanDefinitionRegistrar, EnvironmentAware {
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(DynamicDataSourceRegister.class);
 * 
 *//**
	 * ���������ģ�Ҳ�������Ϊ�����ļ��Ļ�ȡ���ߣ�
	 */
/*
 * private Environment evn;
 * 
 *//**
	 * ����
	 */
/*
 * private final static ConfigurationPropertyNameAliases aliases = new
 * ConfigurationPropertyNameAliases();
 * 
 *//**
	 * ���ڲ�������Դ���ò�ͬ�������ڴ˴���ӱ����������л�����Դ����ĳЩ�����޷�ע������
	 */
/*
 * static { aliases.addAliases("url", new String[] { "jdbc-url" });
 * aliases.addAliases("username", new String[] { "user" }); }
 * 
 *//**
	 * �洢����ע�������Դ
	 */
/*
 * private Map<String, DataSource> customDataSources = new HashMap<String,
 * DataSource>();
 * 
 * 
 *//**
	 * ImportBeanDefinitionRegistrar�ӿڵ�ʵ�ַ�����ͨ���÷������԰����Լ��ķ�ʽע��bean
	 *
	 * @param annotationMetadata
	 * @param beanDefinitionRegistry
	 */
/*
 * @Override public void registerBeanDefinitions(AnnotationMetadata
 * annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) { //
 * ��ȡ��������Դ���� Map config, defauleDataSourceProperties;
 * defauleDataSourceProperties = binder.bind("spring.datasource.master",
 * Map.class).get(); // ��ȡ����Դ���� String typeStr =
 * evn.getProperty("spring.datasource.master.type"); // ��ȡ����Դ���� Class<? extends
 * DataSource> clazz = getDataSourceType(typeStr); // ��Ĭ������Դ���� Ҳ����������Դ
 * DataSource consumerDatasource, defaultDatasource = bind(clazz,
 * defauleDataSourceProperties);
 * DynamicDataSourceContextHolder.dataSourceIds.add("master");
 * logger.info("ע��Ĭ������Դ�ɹ�"); // ��ȡ��������Դ���� List<Map> configs =
 * binder.bind("spring.datasource.cluster", Bindable.listOf(Map.class)).get();
 * // ����������Դ for (int i = 0; i < configs.size(); i++) { config = configs.get(i);
 * clazz = getDataSourceType((String) config.get("type"));
 * defauleDataSourceProperties = config; // �󶨲��� consumerDatasource =
 * bind(clazz, defauleDataSourceProperties); // ��ȡ����Դ��key���Ա�ͨ����key���Զ�λ������Դ
 * String key = config.get("key").toString(); customDataSources.put(key,
 * consumerDatasource); // ����Դ�����ģ����ڹ�������Դ���¼�Ѿ�ע�������Դkey
 * DynamicDataSourceContextHolder.dataSourceIds.add(key);
 * logger.info("ע������Դ{}�ɹ�", key); } // bean������ GenericBeanDefinition define =
 * new GenericBeanDefinition(); //
 * ����bean�����ͣ��˴�DynamicRoutingDataSource�Ǽ̳�AbstractRoutingDataSource��ʵ����
 * define.setBeanClass(DynamicRoutingDataSource.class); // ��Ҫע��Ĳ���
 * MutablePropertyValues mpv = define.getPropertyValues(); //
 * ���Ĭ������Դ������key�����ڵ����û������Դ���� mpv.add("defaultTargetDataSource",
 * defaultDatasource); // �����������Դ mpv.add("targetDataSources",
 * customDataSources); // ����beanע��Ϊdatasource����ʹ��springboot�Զ����ɵ�datasource
 * beanDefinitionRegistry.registerBeanDefinition("datasource", define);
 * logger.info("ע������Դ�ɹ���һ��ע��{}������Դ", customDataSources.keySet().size() + 1); }
 * 
 *//**
	 * ͨ���ַ�����ȡ����Դclass����
	 *
	 * @param typeStr
	 * @return
	 */
/*
 * @SuppressWarnings("unchecked") private Class<? extends DataSource>
 * getDataSourceType(String typeStr) { Class<? extends DataSource> type; try {
 * if (!StringUtils.isBlank(typeStr)) { //��Ϊ�գ���ȡ��ȡ���� type = (Class<? extends
 * DataSource>) Class.forName(typeStr); } else { //
 * Ĭ��ΪhikariCP����Դ����springbootĬ������Դ����һ�� type = HikariDataSource.class; } return
 * type; } catch (Exception e) { throw new
 * IllegalArgumentException("can not resolve class with type: " + typeStr);
 * //�޷�ͨ�������ȡclass�����������׳��쳣�������һ����д���ˣ����Դ˴��׳�һ��runtimeexception } }
 * 
 *//**
	 * �󶨲��������������������ǲο�DataSourceBuilder��bind����ʵ�ֵģ�Ŀ���Ǿ�����֤�����Լ���ӵ�����Դ���������springboot����һ��
	 *
	 * @param result
	 * @param properties
	 */
/*
 * @SuppressWarnings("unused") private void bind(DataSource result, Map
 * properties) { ConfigurationPropertySource source = new
 * MapConfigurationPropertySource(properties); Binder binder = new Binder(new
 * ConfigurationPropertySource[]{source.withAliases(aliases)}); // �������󶨵�����
 * binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result)); }
 * 
 * private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
 * ConfigurationPropertySource source = new
 * MapConfigurationPropertySource(properties); Binder binder = new Binder(new
 * ConfigurationPropertySource[]{source.withAliases(aliases)}); //
 * ͨ�����Ͱ󶨲��������ʵ������ return binder.bind(ConfigurationPropertyName.EMPTY,
 * Bindable.of(clazz)).get(); }
 * 
 *//**
	 * @param clazz
	 * @param sourcePath ����·������Ӧ�����ļ��е�ֵ����: spring.datasource
	 * @param <T>
	 * @return
	 */
/*
 * private <T extends DataSource> T bind(Class<T> clazz, String sourcePath) {
 * Map properties = binder.bind(sourcePath, Map.class).get(); return bind(clazz,
 * properties); }
 * 
 * 
 * 
 * 
 *//**
	 * �����󶨹��� springboot2.0���Ƴ�
	 *//*
		 * private Binder binder;
		 * 
		 * @Override public void setEnvironment(Environment environment) {
		 * logger.info("��ʼע������Դ"); this.evn = environment; // �������� binder =
		 * Binder.get(evn); }
		 * 
		 * }
		 */