package com.zh.java.reflect;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author zhanghe
 * @Desc: 反射
 * @Date 2019/3/19 17:02
 */
public class Reflect_demo {

    /**
     * 获取Java对象指定字段的值
     *
     * @param property 字段名称
     * @param object   Java对象
     * @return 字段值
     */
    public static Object getPropertyValue(String property, Object object) {
        if (object == null)
            return null;
        Object objectReturn = null; //返回的值
        String methodName = "get" + StringUtils.capitalize(property);// 得到javabean的get方法
        String booleanMethodName = "is" + StringUtils.capitalize(property);// 得到boolean类型方法
        try {
            objectReturn = object.getClass().getMethod(methodName, null).invoke(object, null);
        } catch (Exception e1) {
            try {
                objectReturn = object.getClass().getMethod(booleanMethodName, null).invoke(object, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objectReturn;
    }


}
