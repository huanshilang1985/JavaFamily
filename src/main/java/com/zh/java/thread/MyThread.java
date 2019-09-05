package com.zh.java.thread;

/**
 * @Author zhanghe
 * @Desc:  多线程方法2：继承 thread类
 *         Thread其实也实现了Runnable接口
 * @Date 2019/3/19 18:00
 */
public class MyThread extends Thread {

    public MyThread(String name){
        super(name);
    }

    public void run(){
        for(int i=0;i<10;i++) {
            System.out.println(Thread.currentThread().getName()+"--->"+i);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MyThread myThread=new MyThread("线程1");
        MyThread myThread2=new MyThread("线程2");
        //直接调用run方法 无法成功开启线程，相当于调用了一个普通方法
        myThread.start();
        myThread2.start();

    }

}
