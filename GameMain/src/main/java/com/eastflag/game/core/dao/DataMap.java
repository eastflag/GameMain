package com.eastflag.game.core.dao;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eastflag.game.core.utils.StringUtil;

public class DataMap extends HashMap<String, Object> {

	public DataMap() {
		// TODO Auto-generated constructor stub
	}

	public String getPageCommand() {
		return getString("pageCommand");
	}

	public String getString(String key) {
		return getString(key, "");
	}

	public String getString(String key, String defalutValue) {
		if (containsKey(key)) {
			Object obj = get(key);

			if (obj == null) {
				return defalutValue;
			}

			Class clazz = obj.getClass();

			if (clazz.isArray()) {
				int arrayLength = Array.getLength(obj);
				if (arrayLength == 0) {
					return defalutValue;
				}

				Object item = Array.get(obj, 0);
				if (item == null) {
					return defalutValue;
				}

				return item.toString();
			}
			return obj.toString();
		}
		return defalutValue;
	}

	public double getDouble(String key) {
		String value = getString(key);
		if (StringUtil.isEmpty(value)) {
			return 0.0D;
		}

		if (value.indexOf(",") > -1) {
			value = value.replace(",", "");
		}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException nfex) {
		}
		return 0.0D;
	}

	public float getFloat(String key) {
		double value = getDouble(key);
		return (float) value;
	}

	public int getInt(String key) {
		double value = getDouble(key);
		return (int) value;
	}

	public long getLong(String key) {
		String value = getString(key);
		if (StringUtil.isEmpty(value)) {
			return 0L;
		}

		if (value.indexOf(",") > -1) {
			value = value.replace(",", "");
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException nfex) {
		}
		return 0L;
	}

	public String[] getArray(String key) {
		if (containsKey(key)) {
			Object obj = get(key);
			if (obj == null) {
				return StringUtil.EMPTY_ARRAY;
			}

			Class clazz = obj.getClass();
			List list = new ArrayList();
			if (clazz.isArray()) {
				Object[] objArray = (Object[]) obj;
				for (Object arrayItem : objArray)
					list.add(arrayItem.toString());
			} else {
				list.add(obj.toString());
			}

			String[] array = new String[list.size()];
			list.toArray(array);
			return array;
		}
		return StringUtil.EMPTY_ARRAY;
	}

	public String[] getArray(String key, String delimiter) {
		String value = getString(key);
		if (StringUtil.isEmpty(value)) {
			return StringUtil.EMPTY_ARRAY;
		}
		return value.split(delimiter);
	}

	public String getUrlParameter() {
		return getUrlParameter(null);
	}

	public String getUrlParameter(String url) {
		String key = "";
		StringBuffer sb = new StringBuffer();

		if ((url != null) && (!"".equals(url))) {
			sb.append(url);
			sb.append("?");
		}

		for (Map.Entry map : entrySet()) {
			key = (String) map.getKey();

			if (!key.startsWith("session_")) {
				sb.append((String) map.getKey());
				sb.append("=");
				sb.append(map.getValue());
				sb.append("&");
			}
		}

		return sb.toString();
	}

//	public void setDateTimeParam(String key, String datetimePattern) {
//		String dateStr = getString(key);
//
//		PropertyService propertiesService = null;
//		try {
//			propertiesService = (PropertyService) ControllerUtil.getBean("propertiesService");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if ((!"".equals(dateStr)) && (!"".equals(datetimePattern))) {
//			if (datetimePattern.indexOf("HH") > -1) {
//				dateStr = dateStr + " " + getString(new StringBuilder().append(key).append("_HH").toString());
//			}
//
//			if (datetimePattern.indexOf("mm") > -1) {
//				dateStr = dateStr + ":" + getString(new StringBuilder().append(key).append("_mm").toString());
//			}
//
//			if (datetimePattern.indexOf("ss") > -1) {
//				dateStr = dateStr + ":" + getString(new StringBuilder().append(key).append("_ss").toString());
//			}
//
//			if ("cubrid".equals(propertiesService.getString("db.type"))) {
//				SimpleDateFormat formatDateTime = new SimpleDateFormat(datetimePattern);
//				Date dateTime = null;
//				try {
//					dateTime = formatDateTime.parse(dateStr);
//				} catch (ParseException pe) {
//					pe.printStackTrace();
//					dateTime = null;
//				}
//
//				put(key, dateTime);
//			} else if ("mysql".equals(propertiesService.getString("db.type"))) {
//				put(key, dateStr);
//			} else {
//				put(key, dateStr);
//			}
//		} else {
//			put(key, null);
//		}
//	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		Set set = keySet();
		Iterator iter = set.iterator();

		String key = "";
		String delim = ", ";

		while (iter.hasNext()) {
			key = (String) iter.next();
			Object o = get(key);

			if (o == null) {
				sb.append(delim).append(key).append("=").append("");
			} else {
				Class c = o.getClass();
				if (c.isArray()) {
					int length = Array.getLength(o);
					if (length == 0)
						sb.append(delim).append(key).append("=").append("");
					else
						for (int i = 0; i < length; i++)
							sb.append(delim).append(key).append("=").append(Array.get(o, i).toString());
				} else {
					sb.append(delim).append(key).append("=").append(o.toString());
				}
			}
		}
		String tmp = sb.toString();
		if (tmp.length() > delim.length()) {
			tmp = tmp.substring(delim.length(), tmp.length());
		}
		tmp = "{" + tmp + "}";
		return tmp;
	}

}
