
package com.zachary.springboot.blog.pushlian.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zachary
 * @desc 集合排序
 *
 */
public class ListComparator implements Comparator<Object> {
	private final Logger logger = LoggerFactory.getLogger(ListComparator.class);
	private String[] properties;
	private boolean[] asc;
	private int length;

	public ListComparator(String[] properties, boolean[] asc) {
		this.properties = null;
		this.length = 0;
		this.properties = properties;
		this.length = Math.min(properties.length, asc.length);
		this.asc = asc;
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public int compare(Object o1, Object o2) {
		int result = 0;
		if ((o1 != null) && (o2 == null)) {
			result = 1;
		} else if ((o1 == null) && (o2 != null)) {
			result = -1;
		} else if (o1 != null) {
			for (int i = 0; i < this.length; i++) {
				try {
					Comparable<Object> p1 = (Comparable) PropertyUtils.getProperty(o1, this.properties[i]);

					Comparable<Object> p2 = (Comparable) PropertyUtils.getProperty(o2, this.properties[i]);
					if ((p1 == null) && (p2 != null)) {
						result = -1;
					} else if ((p2 == null) && (p1 != null)) {
						result = 1;
					} else if (p1 != null) {
						result = p1.compareTo(p2);
					}
					if (result != 0) {
						return this.asc[i] != true ? result : -result;
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					this.logger.error("集合排序异常！", e);
				}
			}
		}
		return 0;
	}
}
