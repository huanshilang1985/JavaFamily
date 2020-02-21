package com.zh.java.generic;

/**
 * @Author: he.zhang
 * @Date: 2019/9/26 10:47
 *
 * 多重边界
 */
public class MultiLimit implements MultiLimitInterfaceA, MultiLimitInterfaceB {

    /**
     * 使用"&"符号设定多重边界 (Multi Bounds)
     */
    public static<T extends MultiLimitInterfaceA & MultiLimitInterfaceB> void test(T t){

    }
}

interface MultiLimitInterfaceA {}

interface MultiLimitInterfaceB {}


