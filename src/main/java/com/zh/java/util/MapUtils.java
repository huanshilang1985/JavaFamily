package com.zh.java.util;

import com.zh.java.reflect.Reflect_demo;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/19 16:38
 */
public class MapUtils {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "AA");
        map.put("2", "BB");
        map.put("3", "CC");
        map.put("4", "DD");
        mapValues2ArrayList(map);

    }

    /**
     * Map值转换为Array
     *
     * @param map
     */
    public static String[] mapValues2Array(Map<String, String> map) {
        Collection<String> values = map.values();
        int count = values.size();
        String[] arr = new String[count];
        map.values().toArray(arr);
        for (String str : arr) {
            System.out.println(str);  //输出数据
        }
        return arr;
    }

    /**
     * Map值转换为ArrayList
     *
     * @param map
     *///输出数据
    public static List<String> mapValues2ArrayList(Map<String, String> map) {
        Collection<String> values = map.values();
        List<String> list = new ArrayList<String>(values);
        for (String str : list) {
            System.out.println(str);  //输出数据
        }
        return list;
    }

    /**
     * 把集合类转成Map  （字段值没有重复的情况）
     *
     * @param cols 集合类，如：ArrayList
     * @param prop 集合中对象的字段名
     * @return Map
     */
    public static Map putCollectionToMap(Collection cols, String prop) {
        Map tempMap = new HashMap();
        if (CollectionUtils.isNotEmpty(cols)) {
            for (Object tempObject : cols) {
                tempMap.put(Reflect_demo.getPropertyValue(prop, tempObject), tempObject);
            }
        }
        return tempMap;
    }

    /**
     * 把集合类转成Map<String,List> （字段值有重复的情况）
     *
     * @param colls 集合类
     * @param prop  集合中对象的字段名
     * @return Map
     */
    public static Map<String, List> getGroupToMap(Collection colls, String prop) {
        Map<String, List> result = new HashMap<String, List>();
        if (CollectionUtils.isNotEmpty(colls)) {
            for (Object temp : colls) {
                List tempList = result.get(Reflect_demo.getPropertyValue(prop, temp));
                if (tempList == null) {
                    tempList = new ArrayList();
                    tempList.add(temp);
                    result.put(Reflect_demo.getPropertyValue(prop, temp) + "", tempList);
                } else {
                    tempList.add(temp);
                }
            }
        }
        return result;
    }

    /**
     * 把集合类转成Map<String,List> （集成了Vector， 字段值有重复的情况）
     *
     * @param colls 集合类
     * @param prop  集合中对象的字段名
     * @return Map
     */
    public static Map<String, List> getGroupToMapTable(Collection colls, String prop) {
        Map<String, List> result = new Hashtable<String, List>();
        if (CollectionUtils.isNotEmpty(colls)) {
            for (Object temp : colls) {
                List tempList = result.get(Reflect_demo.getPropertyValue(prop, temp));
                if (tempList == null) {
                    tempList = new Vector();
                    tempList.add(temp);
                    result.put(Reflect_demo.getPropertyValue(prop, temp) + "", tempList);
                } else {
                    tempList.add(temp);
                }
            }
        }
        return result;
    }
}
