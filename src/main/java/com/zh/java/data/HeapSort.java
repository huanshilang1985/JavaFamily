package com.zh.java.data;

import java.util.Arrays;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/19 15:47
 */
public class HeapSort {

    public static void main(String[] args) {
        int arr[] = {8, 7, 4, 1, 2, 3, 6, 5, 9};
        sortASC(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sortASC(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustBigHeap(arr, i, arr.length);//调整最初状态
        }
        for (int j = arr.length - 1; j >= 0; j--) {//对数组的每个元素进行调整，j就是调整的长度
            swap(arr, 0, j);//调换0与j位置上的数，在调整的时候能够将j的值调到最高的位置
            adjustBigHeap(arr, 0, j);//，然后对堆进行调整
        }
    }

    /**
     * 调整数组的以第i个为根节点的树
     *
     * @param arr    数组
     * @param i      第几个元素
     * @param length 数组长度
     */
    private static void adjustBigHeap(int[] arr, int i, int length) {
        int temp = arr[i];
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++;
            }
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        arr[i] = temp;
    }

    /**
     * 调换位置
     *
     * @param arr 数组
     * @param i   位置i
     * @param j   位置j
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
