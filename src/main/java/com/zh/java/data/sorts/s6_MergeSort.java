package com.zh.java.data.sorts;

import java.util.Arrays;

/**
 * @Author zhanghe
 * @Desc: 归并排序
 * 时间复杂度：O(nLogn)   基于比较
 * 空间复杂度：O(n)
 * 非原地排序算法
 *
 * 分治思想：把数据从中间分成前后两部分，再对前后两部分的数据分别排序，再将排序好的两部分合并在一起。
 * <p>
 * 递推公式：merge_sort(p...r) = merge(merge_sort(p...q),merge_sort(q+1...r))
 * 终止条件： p >= r 不用再继续分解
 * @Date 2019/5/6 17:39
 */
public class s6_MergeSort {

    public static void main(String[] args) {
        int[] data = new int[]{4, 6, 5, 3, 7, 1, 2};
        mergeSort(data, data.length);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 归并排序算法
     *
     * @param a 数组
     * @param n 数组大小
     */
    public static void mergeSort(int[] a, int n) {
        mergeSortInternally(a, 0, n - 1);
    }

    /**
     * @param a 数组
     * @param p 头坐标
     * @param r 尾坐标
     */
    public static void mergeSortInternally(int[] a, int p, int r) {
        //递归终止条件
        if (p >= r) return;

        //取p到r之间的中间位置q，防止（p+r）的和超过int类型最大值
        int q = p + (r - p) / 2;

        //分支递归
        mergeSortInternally(a, p, q);
        mergeSortInternally(a, q + 1, r);

        //将Array[p...q] 和Array[q+1...r] 合并为Array[p...r]
        merge(a, p, q, r);
    }

    /**
     * 合并函数
     *
     * @param a 数组
     * @param p 头坐标
     * @param q 中间坐标
     * @param r 尾坐标
     */
    private static void merge(int[] a, int p, int q, int r) {
        int i = p;
        int j = q + 1;
        int k = 0;

        //申请一个大小跟a[p...r]一样的临时数组
        int[] tmp = new int[r - p + 1];
        while (i <= q && j <= r) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++]; // i++等于i:=i+1
            } else {
                tmp[k++] = a[j++];
            }
        }

        // 判断哪个子数组中有剩余的数据
        int start = i;
        int end = q;
        if (j <= r) {
            start = j;
            end = r;
        }

        // 将剩余的数据拷贝到临时数组tmp
        while (start <= end) {
            tmp[k++] = a[start++];
        }

        // 将tmp中的数组拷贝回a[p...r]
        for (i = 0; i <= r - p; ++i) {
            a[p + i] = tmp[i];
        }
    }

}
