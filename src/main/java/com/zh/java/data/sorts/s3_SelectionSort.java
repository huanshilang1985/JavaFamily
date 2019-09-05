package com.zh.java.data.sorts;

import java.util.Arrays;

/**
 * @Author zhanghe
 * @Desc: 选择排序
 * 时间复杂度：O(N2)
 * 空间复杂度O(1) 原地算法 ，基于比较
 * 非稳定排序
 * <p>
 * 把数据分为已排序区间，未排序区间。从未排序元素中找最小值，并和前面的元素交换。
 * @Date 2019/5/6 17:21
 */
public class s3_SelectionSort {

    public static void main(String[] args) {
        int[] a = {4, 3, 5, 2, 6, 1};
        selectSort(a, a.length);
        System.out.println(Arrays.toString(a));
    }

    /**
     * @param a 数组
     * @param n 数组大小
     */
    public static void selectSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 0; i < n - 1; ++i) {
            //查找最小值
            int minIndex = i;
            for (int j = i + 1; j < n; ++j) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }

            //交换
            int temp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = temp;
        }
    }

}
