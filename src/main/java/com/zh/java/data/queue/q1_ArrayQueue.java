package com.zh.java.data.queue;

/**
 * @Author zhanghe
 * @Desc: 用数组实现的队列
 * 这是最简单的一种实现，如果把数组内容全部出队之后，数据就是空的了，不能再加新数据了
 * @Date 2019/5/6 15:57
 */
public class q1_ArrayQueue {

    private String[] items;  //数组
    private int n = 0;       //数组大小
    private int head = 0; //表示队头下标
    private int tail = 0; //表示队尾下标

    /**
     * 申请一个大小是capacity的数据
     * @param capacity  数组容量
     */
    public q1_ArrayQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * 入队操作
     * @param item 入队信息
     * @return boolean
     */
    public boolean enqueue(String item) {
        // 如果tail == n 表示队列已经满了
        if (tail == n) return false;
        items[tail] = item;
        ++tail;
        return true;
    }

    /**
     * 出队操作
     * @return 出队信息
     */
    public String dequeue() {
        // 如果head == tail 表示队列为空
        if (head == tail) return null;
        // 为了让其他语言的同学看的更加明确，把--操作放到单独一行来写了
        String ret = items[head];
        ++head;
        return ret;
    }

    public void printAll() {
        for (int i = head; i < tail; ++i) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}
