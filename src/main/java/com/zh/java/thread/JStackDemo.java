package com.zh.java.thread;

/**
 * Author zhanghe
 * Desc: 死锁Demo，使用jstack、Java visual VM 排查问题
 * Date 2019/8/1 20:56
 */
public class JStackDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLockTest(true));  //创建一个线程
        Thread t2 = new Thread(new DeadLockTest(false)); //闯将
        t1.setName("thread-test-1");
        t2.setName("thread-test-2");
        t1.start();
        t2.start();
    }

}

class DeadLockTest implements Runnable {

    public boolean flag; //控制线程

    DeadLockTest(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        // 如果flag的值为true则调用t1线程
        if(flag){
            while (true){
                synchronized (Demo.o1) {
                    System.out.println("o1" + Thread.currentThread().getName());
                    synchronized (Demo.o2) {
                        System.out.println("o2" + Thread.currentThread().getName());
                    }
                }
            }
        } else {
            // 如果flag的值为false则调用t2线程
            while (true) {
                synchronized (Demo.o2) {
                    System.out.println("o2" + Thread.currentThread().getName());
                    synchronized (Demo.o1) {
                        System.out.println("o2" + Thread.currentThread().getName());
                    }
                }
            }
        }
    }
}

class Demo {
    static Object o1 = new Object();
    static Object o2 = new Object();
}