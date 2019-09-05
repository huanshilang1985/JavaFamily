package com.zh.java.data.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author zhanghe
 * @Desc: 给闺女生成的小学加减法数据题
 * @Date 2019/1/28 17:51
 */
public class MathQuestion {

    static final Random random = new Random();

    public static int getNumber(int i) {
        return random.nextInt(i);
    }

    public static void math20() {

    }

    public static void main(String[] args) {
        int[] addNumber = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int[] subtractionNumber = {11, 12, 13, 14, 15, 16, 17, 18, 19};
        String[] mothods = {"+", "-"};


        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            while (true) {
                String mothod = mothods[getNumber(2)];
                if (mothod.equals("+")) {
                    int k1 = addNumber[getNumber(15)];
                    int k2 = addNumber[getNumber(15)];
                    if ((k1 + k2) > 11 && (k1+k2) <= 20 && k1 != k2) {
                        list.add(k1 + mothod + k2 + "=");
                        break;
                    }
                }
                if (mothod.equals("-")) {
                    int k1 = addNumber[getNumber(9)];
                    int k2 = subtractionNumber[getNumber(9)];
                    String k2Str = Integer.valueOf(k2).toString();
                    int k2Str_temp = Integer.parseInt(k2Str.substring(1, 2));
                    if (k1 > k2Str_temp) {
                        list.add(k2 + mothod + k1 + "=");
                        break;
                    }
                }
            }
        }

        if (list != null && list.size() > 0) {
            List<String> tempList = new ArrayList<String>();
            int i = 0;
            StringBuffer buffer = new StringBuffer();
            for (String str : list) {
                if (i <= 4) {
                    buffer.append(str + "    ");
                    i++;
                }
                if (i == 5) {
                    tempList.add(buffer.toString());
                    buffer = new StringBuffer();
                    i = 0;
                }
            }
            if (tempList != null && tempList.size() > 0) {
                for (String str : tempList) {
                    System.out.println(str);
                }
            }
        }
    }

}
