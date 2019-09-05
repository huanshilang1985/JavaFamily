package com.zh.java.data.queue;

/**
 * @Author zhanghe
 * @Desc: 动态数据迁移的数组队列
 *
 * @Date 2019/5/6 14:47
 */
public class q2_DynamicArrayQueue {

    private String[] items;  //数组
    private int n = 0;       //数组大小
    private int head = 0; //表示队头下标
    private int tail = 0; //表示队尾下标

    /**
     * 申请一个容量是capacity的数据
     * @param capacity  数组容量
     */
    public q2_DynamicArrayQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * 入队操作
     * @param item 入队信息
     * @return boolean
     */
    public boolean enqueue(String item){
        //如果tail == n 表示队列尾部没有空间了
        if(tail == n) {
            // tail==n && head==0, 表示整个队列已经占满了
            if(head==0) return false;

            //数据搬移(之前有出队操作，表示数组前有空位置了)
            for(int i = head; i < tail; ++ i){
                items[i - head] = items[i];
            }
            //搬移完之后更新head和tail
            tail -= head;
            head = 0;
        }
        //从队尾位置入队，并更新队尾下标
        items[tail] = item;
        ++tail;
        return true;
    }

    /**
     * 出队操作
     * @return 出队信息
     */
    public String dequeue(){
        //如果head == tail 表示队列为空
        if(head == tail){
            return null;
        }
        String result = items[head];
        ++head;
        return result;
    }

    public void printAll() {
        for (int i = head; i < tail; ++i) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

}
