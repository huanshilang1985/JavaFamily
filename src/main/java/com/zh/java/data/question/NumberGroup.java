package com.zh.java.data.question;

import java.util.Arrays;

/**
 * Author zhanghe
 * Desc: 传入一个字符串，第一个作为总数，其他数字的和等于第一位数字的组合打印出来，没有打印0
 * 如：10,2,1,3,4,5,6,
 * 结果：2,3,5
 *      4,6
 *      1,3,6
 *      1,4,5
 * Date 2019/8/27 18:59
 */
public class NumberGroup {

    public static void main(String[] args) {
        String str = "10,2,1,3,4,5,6";
        NumberGroup.deal(str);
    }

    public static void deal(String str){
        String[] split = str.split(",");
        //总计的数字
        int number = Integer.parseInt(split[0]);
        //对数字排序
        int[] intArr = new int[split.length];
        for(int i = 1; i < split.length; i++){
            intArr[i] = Integer.valueOf(split[i]);
        }
        Arrays.sort(intArr);

        //递归判断
        for(int i = 0; i < intArr.length; i++){
            int i1 = intArr[i];
            for(int j = i + 1; j < intArr.length; j++){
                if((i1 + intArr[j]) == number){
                    System.out.println();
                }
            }

        }


    }

    private static void suBeal(){

    }

}
