package com.zh.java.thread.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 不可重入锁
 * 利用原子操作，在同一个线程里，加锁2次，就会造成死锁
 *
 * @author he.zhang
 * @date 2020/2/21 17:06
 */
public class UnReentrantLock {

    /**
     * 原子操作
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        //这句是很经典的“自旋”语法，AtomicInteger中也有
        for (;;) {
            boolean b = owner.compareAndSet(null, current);
            if (b) {
                return;
            }
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        owner.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        UnReentrantLock lock = new UnReentrantLock();
        lock.lock();
        lock.lock();
    }

}