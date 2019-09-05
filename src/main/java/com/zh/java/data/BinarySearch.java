package com.zh.java.data;

/**
 * @Author zhanghe
 * @Desc: 二分查找（Binary Search）
 * 二分查找是针对有序数据集合的查找算法，也叫折半查找算法
 * 时间复杂度：O(logn) 对数时间复杂度，是非常高霞偶读，比O(1)还高效
 * <p>
 * 被查找的区间的大小变化：n，n/2, 2/4, n/8,..., n/2k （这里表示的是2的k次方，k的值就是总共缩小的次数）
 * @Date 2019/4/26 14:46
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {8, 11, 19, 23, 27, 33, 45, 55, 67, 98};
        int i = 54;

        int location = bSearch1_3(arr, arr.length, i);
        System.out.println(location == -1 ? "您要查找的数据不存在" : i + "所在的位置是第" + (location + 1) + "个");
    }

    /**
     * 查找数据所在的位置，循环方式
     *
     * @param arr   数据
     * @param n     数组长度
     * @param value 要找的值
     * @return 返回位置，不存在返回-1
     */
    public static int bSearch1(int[] arr, int n, int value) {
        int low = 0;     //截取数据最低位置
        int high = n - 1;//截取数组最高位置
        while (low <= high) {
            int mid = (low + high) / 2;  //取中间位置，最优写法是 int mid = low+((high-low)>>1)
            if (arr[mid] == value) {
                //等于要找的值，返回
                return mid;
            } else if (arr[mid] < value) {
                //当前值小于要找的值，更新最低位置，找后半段数组里找(因为mid肯定不是要找的值，所以直接+1)
                low = mid + 1;
            } else {
                // 当前值大于要找的值，更新最高位置，在前半段数组里找(因为mid肯定不是要找的值，所以直接-1)
                high = mid - 1;

            }
        }
        return -1;
    }

    /**
     * 查找数据所在的位置，循环方式。变体1，查找数字第一次出现的位置
     *
     * @param arr   数据
     * @param n     数组长度
     * @param value 要找的值
     * @return 返回位置，不存在返回-1
     */
    public static int bSearch1_1(int[] arr, int n, int value) {
        int low = 0;     //截取数据最低位置
        int high = n - 1;//截取数组最高位置
        while (low <= high) {
            int mid = (low + high) / 2;  //取中间位置，最优写法是 int mid = low+((high-low)>>1)
            if (arr[mid] < value) {
                //当前值小于要找的值，更新最低位置，找后半段数组里找(因为mid肯定不是要找的值，所以直接+1)
                low = mid + 1;
            } else if (arr[mid] > value) {
                // 当前值大于要找的值，更新最高位置，在前半段数组里找(因为mid肯定不是要找的值，所以直接-1)
                high = mid - 1;
            } else {
                //这个是放重复的代码，验证是否是第一次出现位置
                // mid ==0 如果mid已经是第一个了，直接返回；再看他的前一位是否等于value
                if (mid == 0 || arr[mid - 1] != value) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找数据所在的位置，循环方式。变体2，查找数字最后一次出现的位置
     *
     * @param arr   数据
     * @param n     数组长度
     * @param value 要找的值
     * @return 返回位置，不存在返回-1
     */
    public static int bSearch1_2(int[] arr, int n, int value) {
        int low = 0;     //截取数据最低位置
        int high = n - 1;//截取数组最高位置
        while (low <= high) {
            int mid = low + ((high - low) >> 1);  //取中间位置
            if (arr[mid] < value) {
                //当前值小于要找的值，更新最低位置，找后半段数组里找(因为mid肯定不是要找的值，所以直接+1)
                low = mid + 1;
            } else if (arr[mid] > value) {
                // 当前值大于要找的值，更新最高位置，在前半段数组里找(因为mid肯定不是要找的值，所以直接-1)
                high = mid - 1;
            } else {
                //这个是放重复的代码，验证是否是第一次出现位置，
                // mid == n-1 如果mid已经是最后一个了，就直接返回，再判断后一位是否等于value
                if (mid == n - 1 || arr[mid + 1] != value) return mid;
                else high = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 查找数据所在的位置，循环方式。变体3，查找第一个大于等于给定值的元素
     *
     * @param arr   数据
     * @param n     数组长度
     * @param value 要找的值
     * @return 返回位置
     */
    public static int bSearch1_3(int[] arr, int n, int value) {
        int low = 0;     //截取数据最低位置
        int high = n - 1;//截取数组最高位置
        while (low <= high) {
            int mid = low + ((high - low) >> 1);  //取中间位置
            if (arr[mid] >= value) {
                if (mid == 0 || arr[mid - 1] < value) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找数据所在的位置，循环方式。变体4，查找最后一个小于等于给定值的元素
     *
     * @param arr   数据
     * @param n     数组长度
     * @param value 要找的值
     * @return 返回位置
     */
    public static int bSearch1_4(int[] arr, int n, int value) {
        int low = 0;     //截取数据最低位置
        int high = n - 1;//截取数组最高位置
        while (low <= high) {
            int mid = low + ((high - low) >> 1);  //取中间位置
            if (arr[mid] > value) {
                high = mid - 1;
            } else {
                if ((mid == n - 1) || (arr[mid + 1] > value)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找数据所在的位置，递归方式
     *
     * @param arr   数据
     * @param n     数组长度
     * @param value 要找的值
     * @return 返回位置，不存在返回-1
     */
    private static int bSearch2(int[] arr, int n, int value) {
        return bSearchInternally(arr, 0, n - 1, value);
    }

    private static int bSearchInternally(int[] arr, int low, int high, int value) {
        System.out.println("low=" + low + ", high=" + high + ", value=" + value);
        if (low > high) return -1;

        int mid = low + ((high - low) >> 1);
        if (arr[mid] == value) {
            return mid;
        } else if (arr[mid] > value) {
            return bSearchInternally(arr, low, mid + 1, value);
        } else {
            return bSearchInternally(arr, mid - 1, high, value);
        }
    }


}
