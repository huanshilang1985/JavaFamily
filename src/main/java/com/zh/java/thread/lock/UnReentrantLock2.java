package com.zh.java.thread.lock;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重复锁
 * 把UnReentrantLock改造一下，记录状态
 * 使用计数器，判断进入的是否是当前线程，如果是就在计数器+1
 *
 * @author he.zhang
 * @date 2020/2/21 17:19
 */
public class UnReentrantLock2 {

    /**
     * 原子操作
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();

    /**
     * 计数器
     */
    private int state = 0;

    public void lock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            state++;
            return;
        }
        //这句是很经典的“自旋”式语法，AtomicInteger中也有
        for (; ; ) {
            if (owner.compareAndSet(null, current)) {
                return;
            }
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            if (state != 0) {
                state--;
            } else {
                owner.compareAndSet(current, null);
            }
        }
    }

    public static void main(String[] args) {
        UnReentrantLock2 lock = new UnReentrantLock2();
        lock.lock();
        lock.lock();

//        ReentrantLock
    }

}
