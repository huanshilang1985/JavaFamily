package com.zh.java.data.question;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Author zhanghe
 * Desc: 陌生词汇
 *  传入2个字符串，字符串包括小写单词和空格，找出两个字符串里不重复的单词
 *  字符串单词个数都是 0 <=  n <= 200
 * Date 2019/8/27 13:07
 */
public class strange_word {

    public static void main(String[] args) {
        String str1 = "who are you";
        String str2 = "who are tom";
        String[] deal = strange_word.deal(str1, str2);
        for(String str : deal){
            System.out.println(str);
        }
    }

    public static String[] deal(String str1, String str2){
        if(StringUtils.isBlank(str1) && StringUtils.isBlank(str2)){
            return new String[0];
        }
        if(StringUtils.isNotBlank(str1) && StringUtils.isBlank(str2)){
            return str1.split(" ");
        }
        if(StringUtils.isBlank(str1) && StringUtils.isNotBlank(str2)){
            return str2.split(" ");
        }

        String[] arr1 = str1.split(" ");
        String[] arr2 = str2.split(" ");

        Map<String, String> map = new HashMap<>(); //记录所有单词
        Set<String> set = new HashSet<>(); //记录重复单词
        for (String str : arr1){
            if(map.containsKey(str)){
                set.add(str);
            } else {
                map.put(str, str);
            }
        }
        for (String str : arr2){
            if(map.containsKey(str)){
                set.add(str);
            } else {
                map.put(str, str);
            }
        }
        //从map中删除重复值
        set.forEach(map::remove);

        Set<String> keySet = map.keySet();
        String[] arr = keySet.toArray(new String[keySet.size()]);
        return arr;
    }

}
