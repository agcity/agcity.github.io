package com.agcity.swan.cglib.demo;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统支持的数据类型枚举类
 * Package : com.yixin.ndss.enums
 * 
 * @author YixinCapital -- shaoml
 *		   2017年6月29日 下午3:09:22
 *
 */
public enum DataType {

	/**
	 * 字符串
	 */
	STR(0, "字符串", String.class),
	/**
	 * 整数型
	 */
	INT(1, "整数型", Integer.class), 
	/**
	 * 浮点型
	 */
	FLOAT(2, "浮点型", Float.class),
	/**
	 * 双精度浮点型
	 */
	DOUBLE(3, "双精度浮点型", Double.class),
	/**
	 * 布尔型
	 */
	BOOLEAN(4, "布尔型", Boolean.class),
	/**
	 * 长整型
	 */
	LONG(5, "长整型", Long.class),
	/**
	 * 字符串数组
	 */
	STR_ARRAY(6, "字符串数组", String[].class),
	/**
	 * 双精度浮点型数组
	 */
	DOUBLE_ARRAY(7, "双精度浮点型数组", Double[].class),
	/**
	 * list
	 */
	LIST(8, "list", List.class),
	/**
	 * map
	 */
	MAP(9, "map", Map.class),
	/**
	 * 整型数组
	 */
	INT_ARRAY(10, "整型数组", Integer[].class);
	
	/**
	 * 数据类型list
	 */
	public static List<DataType> list;
	
	private Integer value;
	private String name;
	private Class clazz;
	
	/**
	 * 构造方法
	 *  
	 * @author YixinCapital -- shaoml
	 *	       2017年6月29日 下午3:18:50
	 */
	private DataType() {
	}
	
	/**
	 * 
	 *  
	 * @param value value
	 * @param name name
	 * @param clazz clazz
	 * @author YixinCapital -- shaoml
	 *	       2017年6月29日 下午3:19:19
	 */
	private DataType(Integer value, String name, Class clazz) {
		this.name = name;
		this.value = value;
		this.clazz = clazz;
	}
	
	/**
	 * 获取名称
	 * 
	 * @param value value
	 * @return 名称
	 * @author YixinCapital -- shaoml
	 *	       2017年6月29日 下午3:11:24
	 */
	public static String getDisplayNameByValue(Integer value) {
		for (DataType type : DataType.values()) {
			if (value.equals(type.getValue())) {
				return type.getName();
			}
		}
		return "";
	}
	/**
	 * 获取数据类型
	 * 
	 * @param value value
	 * @return  DataType
	 * @author YixinCapital -- shaoml
	 *	       2017年6月29日 下午3:12:12
	 */
	public static DataType getDataTypeByValue(Integer value) {
		for (DataType type : DataType.values()) {
			if (value.equals(type.getValue())) {
				return type;
			}
		}
		return null;
	}
	/**
	 * 获取对象
	 * @param valueStr valueStr
	 * @return Object
	 * @author YixinCapital -- shaoml
	 *	       2017年6月29日 下午3:12:41
	 */
	public Object getObjectByValue(String valueStr) {
		if (StringUtils.isEmpty(valueStr)) {
			if (this.clazz.equals(String.class)) {
				return null;
			} else if (this.clazz.equals(Double.class)) {
//				return 0.0d;
				return null;
			} else if (this.clazz.equals(Map.class)) {
				Map<String, String> rs = new HashMap<String, String>();
				return rs;
			} else if (this.clazz.equals(List.class)) {
				List<Object> rs = new ArrayList<Object>();
				return rs;
			} else {
				return null;
			}
		}
		Object obj = null;
		if (this.clazz.equals(String.class)) {
			obj = String.valueOf(valueStr);
		} else if (this.clazz.equals(Integer.class)) {
			obj = Integer.valueOf(valueStr);
		} else if (this.clazz.equals(Float.class)) {
			obj = Float.valueOf(valueStr);
		} else if (this.clazz.equals(Double.class)) {
			obj = Double.valueOf(valueStr);
		} else if (this.clazz.equals(Boolean.class)) {
			obj = valueStr.toString().equals("1") ? Boolean.TRUE : Boolean.FALSE;
		} else if (this.clazz.equals(Long.class)) {
			obj = Long.valueOf(valueStr);
		} else if (this.clazz.equals(String[].class)) {
			return valueStr.split(",");
		} else if (this.clazz.equals(Double[].class)) {
			Double[] rs = new Double[valueStr.split(",").length];
			int count = 0;
			for (String val : valueStr.split(",")) {
				rs[count++] = Double.valueOf(val);
			}
			return rs;
		} else if (this.clazz.equals(Integer[].class)) {
			Integer[] rs = new Integer[valueStr.split(",").length];
			int count = 0;
			for (String val : valueStr.split(",")) {
				rs[count++] = Integer.valueOf(val);
			}
			return rs;
		} else if (this.clazz.equals(Map.class)) {
			if (!StringUtils.isEmpty(valueStr)) {
				Map<String, String> rs = new HashMap<String, String>();
				for (String str : valueStr.split(",")) {
					if (!StringUtils.isEmpty(str) && str.contains(":")) {
						rs.put(str.split(":")[0], str.split(":")[1]);
					}
				}
				return rs;
			}
		}
		return obj;
	}

	static {
		list = new ArrayList<>();
		DataType[] dataTypes = DataType.values();
		for (DataType dataType : dataTypes) {
			list.add(dataType);
		}
	}

	public String getName() {
		return name;
	}

	public Integer getValue() {
		return value;
	}

	public Class getClazz() {
		return clazz;
	}

}
