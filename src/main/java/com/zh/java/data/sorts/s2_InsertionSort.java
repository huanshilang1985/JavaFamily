package com.zh.java.data.sorts;

import java.util.Arrays;

/**
 * @Author zhanghe
 * @Desc: 插入排序（比冒泡好用，因为交换数据次数少）
 * 时间复杂度：O(N2)
 * 空间复杂度O(1) 是原地算法 ，基于比较
 * 稳定的排序
 * <p>
 * 把数据分为已排序区间，未排序区间，对比后，把数据往前插入
 * @Date 2019/5/6 16:58
 */
public class s2_InsertionSort {

    public static void main(String[] args) {
        int[] a = {4, 3, 5, 2, 6, 1};
        insertionSort(a, a.length);
        System.out.println(Arrays.toString(a));
    }

    /**
     * @param a 数组
     * @param n 数组大小
     */
    public static void insertionSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 1; i < n; ++i) {
            int value = a[i]; //记录要处理的值
            int j = i - 1;
            //查询要插入的位置并移动数据
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j]; //向后移动一位
                } else {
                    break;
                }
            }
            //退出循环后，j+1正好是空出来的位置。如果没有变动，j+1=i
            a[j + 1] = value;
        }

    }

}
