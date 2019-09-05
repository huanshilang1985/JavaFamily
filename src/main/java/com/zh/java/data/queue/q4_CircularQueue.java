package com.zh.java.data.queue;

/**
 * @Author zhanghe
 * @Desc: 循环队列
 *   这个队列是个环形，这个避免数据迁移的操作
 *   当队列满是，tail指向的位置没有数据的，浪费了一个数据的存储空间
 * @Date 2019/5/6 15:59
 */
public class q4_CircularQueue {

    private String[] items; //数据
    private int n = 0;      //数据大小
    private int head = 0;  //head表示队头下标
    private int tail = 0;  //tail表示队尾下标

    /**
     * 申请一个大小是capacity的数据
     * @param capacity  数组容量
     */
    public q4_CircularQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * 入队
     * @param item 入队数据
     */
    public boolean enqueue(String item) {
        // 队列满了
        if ((tail + 1) % n == head) return false;  //这个公式判断队列已满
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    /**
     * 出队
     */
    public String dequeue() {
        // 如果head == tail 表示队列为空
        if (head == tail) return null;
        String ret = items[head];
        head = (head + 1) % n;
        return ret;
    }

    public void printAll() {
        if (0 == n) return;
        for (int i = head; i % n != tail; ++i) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }


}
