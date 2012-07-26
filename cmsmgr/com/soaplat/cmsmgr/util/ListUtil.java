/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author：Bunco E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-10-12 下午04:46:52
 */
public class ListUtil {

	/**
	 * 从List<?>集合中获取对象指定属性的集合
	 * 
	 * @param list
	 * @param property
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T, E> List<T> getPropertiesList(List<E> list, String property)
			throws SecurityException, NoSuchFieldException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		List<T> propertiesList = new ArrayList<T>();
		String methodName = "get" + property.substring(0, 1).toUpperCase()
				+ property.substring(1);
		for (Object o : list) {
			if (null == o) {
				continue;
			}
			Class clazz = o.getClass();
			Method method = clazz.getMethod(methodName);
			propertiesList.add((T) method.invoke(o));
		}
		return propertiesList;
	}

	/**
	 * 根据集合中对象的属性来排序集合元素
	 * @param <E>
	 * @param list
	 * @param property
	 */
	public static <E> void sortByProperty(List<E> list, String property) {
//		if (null == list || 1 > list.size()) {
//			throw new NullPointerException("集合元素为空!");
//		}
		
		final String methodName = "get"
				+ property.substring(0, 1).toUpperCase()
				+ property.substring(1);
		Collections.sort(list, new Comparator<E>() {
			@SuppressWarnings({ "unchecked" })
			public int compare(E o1, E o2) {
				Class clazz1 = o1.getClass();
				Class clazz2 = o2.getClass();

				Method method1;
				Method method2;
				try {
					method1 = clazz1.getMethod(methodName);
					method2 = clazz2.getMethod(methodName);
					Object result1 = method1.invoke(o1);
					Object result2 = method2.invoke(o2);
					
					if (result1 instanceof Date) {
						Date date1 = (Date) result1;
						Date date2 = (Date) result2;
						return String.valueOf(date1.getTime()).compareTo(
								String.valueOf(date2.getTime()));
					} else {
						return result1.toString().compareTo(result2.toString());
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}

	/**
	 * 根据对象属性对集合中的元素分组
	 * @param <K> 对象属性类型
	 * @param <V> 集合元素类型
	 * @param list 集合对象
	 * @param property 对象属性名
	 * @return 返回根据指定属性分组后的Map集合, 键为对象的属性值, 值为集合中的对象
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked" })
	public static <K, V> Map<K, List<V>> groupByProperty(List<V> list, String property) 
			throws SecurityException, NoSuchMethodException, 
			IllegalArgumentException, IllegalAccessException, 
			InvocationTargetException {
		Map<K, List<V>> map = new HashMap<K, List<V>>();
		List<V> groupVs = null;
		String methodName = "get" + property.substring(0, 1).toUpperCase()
				+ property.substring(1);
		for (V v : list) {
			if (null == v) {
				continue;
			}
			Class clazz = v.getClass();
			Method method = clazz.getMethod(methodName);
			K k = (K) method.invoke(v);
			if (map.containsKey(k)) {
				groupVs = map.get(k);
				groupVs.add(v);
			} else {
				groupVs = new ArrayList<V>();
				groupVs.add(v);
				map.put(k, groupVs);
			}
		}
		
		return map;
	}

	/**
	 * 去除List集合重复元素
	 * @param <E>
	 * @param list
	 */
	public static <E> void removeDuplicate(List<E> list) {
		Set<E> set = new HashSet<E>(list);
		list.clear();
		list.addAll(set);
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, List<V>> toMap(V[] vs, int index) {
		Map<K, List<V>> map = new HashMap<K, List<V>>(vs.length);
		for (V v : vs) {
			if (v instanceof Object[]) {
				Object[] objects = (Object[]) v;
				List<V> list = null; 
				if (map.containsKey((K)objects[index])) {
					list = map.get(objects[index]);
					list.add(v);
				} else {
					list = new ArrayList<V>();
					list.add(v);
					map.put((K)objects[index], list);
				}
			} else if (v instanceof Map<?, ?>) {
				Map.Entry<K, List<V>> entry = (Entry<K, List<V>>) v;
				map.put(entry.getKey(), entry.getValue());
			} else {
				throw new IllegalArgumentException("数组元素" + v.toString() + "不是Map集合或数组!");
			}
		}
		return map;
	}
	
	/**
	 * 从List<T[]>对象中取得第索引个数组元素, 并返回去除重复的集合
	 * @param <E>
	 * @param listsEs
	 * @param index
	 * @return
	 */
	public static <E> Set<E> getElementList(List<E[]> listsEs, int index) {
		Set<E> set = new HashSet<E>();
		for (E[] es : listsEs) {
			set.add(es[index]);
		}
		return set;
	}
	

	public static String toString(List<String> list) {
		if (0 < list.size()) {
			String temp = "";
			for (String string : list) {
				temp += string + ",";
			}
			return temp.substring(0, temp.length() - 1);
		} 
		return "";
	}
	
//	public static String toString(Object[] objects) {
//		if (0 < objects.length) {
//			String temp = "";
//			for (Object object : objects) {
//				temp += object + ": ";
//			}
//			return temp.substring(0, temp.length() - 2);
//		} 
//		return "";
//	}
}
