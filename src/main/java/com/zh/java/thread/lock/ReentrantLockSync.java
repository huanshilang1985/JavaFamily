package com.zh.java.thread.lock;

/**
 * 在同一个类里，synchronized是可重入锁
 *
 * @author he.zhang
 * @date 2020/2/21 17:00
 */
public class ReentrantLockSync {

    synchronized void setA() throws Exception {
        Thread.sleep(1000);
        System.out.println("A");
        setB();
        System.out.println("C");
    }

    synchronized void setB() throws Exception {
        Thread.sleep(1000);
        System.out.println("B");
    }

    public static void main(String[] args) throws Exception {
        ReentrantLockSync lock = new ReentrantLockSync();
        lock.setA();
    }

}
