package com.zh.java.data.sorts;

import java.util.Arrays;

/**
 * @Author zhanghe
 * @Desc: 冒泡排序
 * 时间复杂度：O(N2)
 * 空间复杂度O(1) 是原地算法 ，基于比较
 * 稳定的排序
 * <p>
 * 冒泡操作就是每次对比相邻的两个元素，如果不满足大小关系，就互换位置
 * @Date 2019/5/6 16:46
 * <p>
 * <p>
 * 判断一个排序算法的好坏，要考虑：
 * 1. 最好情况、最坏情况、平均情况的时间复杂度
 * 2. 时间复杂度的系数、常数、低阶
 * 3. 比较次数和交换（移动）次数
 * <p>
 * 原地排序，特指空间复杂度O(1)的排序算法
 * 稳定性：待排序的序列中存在值相等的元素，经过排序后，相等元素之间原有的先后顺序不变。
 *
 * 冒泡、插入、选择   时间复杂度：O(N2)   基于比较
 * 快排、归并      时间复杂度：O(nLogn)    基于比较
 * 桶排序，计数，基数    O(n)    不基于比较
 */
public class s1_BubbleSort {

    public static void main(String[] args) {
        int[] a = {3, 4, 5, 2, 6, 1};
        bulbleSort(a, a.length);
        System.out.println(Arrays.toString(a));
    }

    /**
     * @param a 数组
     * @param n 数组大小
     */
    public static void bulbleSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 0; i < n; ++i) {
            //提前退出冒泡循环的标志位
            boolean flag = false;
            for (int j = 0; j < n - i - 1; ++j) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    flag = true; //表示有数据交换
                }
            }
            //没有数据交换，提前退出
            if (!flag) break;
        }
    }

}
