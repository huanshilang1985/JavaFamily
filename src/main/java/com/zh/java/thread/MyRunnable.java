package com.zh.java.thread;

/**
 * @Author zhanghe
 * @Desc: 多线程方法1：实现Runnable接口
 * @Date 2019/3/19 17:58
 */
public class MyRunnable implements Runnable {

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "--->" + i);
        }
    }

    public static void main(String[] args) {
		/*
		 *   a、建立一个类实现runnable的接口
   		 *   b、使用参数为Runnable对象的Thread构造方法。
         *   c、 调用 start方法 开启线程。
		 *
		 * */
        MyRunnable myRunnable=new MyRunnable();
        Thread thread=new Thread(myRunnable);
        thread.setName("线程1");
        thread.start();

        Thread thread2=new Thread(myRunnable);
        thread2.setName("线程2");
        thread2.start();
    }

}
