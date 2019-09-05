package com.zh.java.data.sorts;

import java.util.Arrays;

/**
 * @Author zhanghe
 * @Desc: 快速排序
 * 时间复杂度：O(nLogn)   基于比较
 *
 * 分治思想：把数据中下标从p到r之间的一组数据，任意选择其中一点作为pivot（分区点）
 *  遍历p到r之间的数据，将小于pivot的放到右边，大于pivot的放在右边，pivot放到中间
 *  递归排序下标从p到q-1之间的数据和下标q+1到r之间的数据，直到区间缩小为1，就说明所有的数据都是有序的了。
 *
 * 递推公式：quick_sort(p...r) = quick_sort(p...q-1) + qick_sort(q+1...r)
 * 终止条件 p >= r
 *
 * @Date 2019/5/6 18:02
 */
public class s7_QuickSort {

    public static void main(String[] args) {
        int[] data = new int[]{4, 6, 5, 3, 7, 1, 2};
        quickSort(data, data.length);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 快速排序
     * @param a 数组
     * @param n 数据大小
     */
    public static void quickSort(int[] a, int n) {
        quickSortInternally(a, 0, n-1);
    }

    /**
     *  快速排序递归函数
     * @param a 数组
     * @param p 数组上标
     * @param r 数组下标
     */
    private static void quickSortInternally(int[] a, int p, int r) {
        if (p >= r) return;

        int q = partition(a, p, r); // 获取分区点
        quickSortInternally(a, p, q-1);
        quickSortInternally(a, q+1, r);
    }

    /**
     * 分区函数
     * @param a 数组
     * @param p 上标
     * @param r 下标
     * @return 中间点
     */
    private static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p;
        for(int j = p; j < r; ++j) {
            if (a[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int tmp = a[i];
                    a[i++] = a[j];
                    a[j] = tmp;
                }
            }
        }

        int tmp = a[i];
        a[i] = a[r];
        a[r] = tmp;

        System.out.println("i=" + i);
        return i;
    }

}
