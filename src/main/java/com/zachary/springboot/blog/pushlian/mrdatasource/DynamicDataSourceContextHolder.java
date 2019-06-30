package com.zachary.springboot.blog.pushlian.mrdatasource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: 
 * @Date: 
 * @Description: ����Դ������
 */
public class DynamicDataSourceContextHolder {

	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

	/**
	 * �洢�Ѿ�ע�������Դ��key
	 */
	public static List<String> dataSourceIds = new ArrayList<>();

	/**
	 * �̼߳����˽�б���
	 */
	private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

	public static String getDataSourceRouterKey() {
		return HOLDER.get();
	}

	public static void setDataSourceRouterKey(String dataSourceRouterKey) {
		logger.info("�л���{}����Դ", dataSourceRouterKey);
		HOLDER.set(dataSourceRouterKey);
	}

	/**
	 * ��������Դ֮ǰһ��Ҫ���Ƴ�
	 */
	public static void removeDataSourceRouterKey() {
		HOLDER.remove();
	}

	/**
	 * �ж�ָ��DataSrouce��ǰ�Ƿ����
	 *
	 * @param dataSourceId
	 * @return
	 */
	public static boolean containsDataSource(String dataSourceId) {
		return dataSourceIds.contains(dataSourceId);
	}
}
