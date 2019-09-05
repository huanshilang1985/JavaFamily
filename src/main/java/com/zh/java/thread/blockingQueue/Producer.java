package com.zh.java.thread.blockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author zhanghe
 * Desc:  测试阻塞队列的生产者
 * Date 2019/7/26 17:17
 */
@Slf4j
public class Producer implements Runnable {

    private volatile boolean isRunning = true; //是否在运行状态

    private BlockingQueue queue;  //阻塞队列

    private static AtomicInteger count = new AtomicInteger(); //自动更新的值

    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

    /**
     * 构造函数
     */
    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        String data = null;
        Random r = new Random();
        System.out.println(threadName + "启动生产者线程！");
        try {
            while (isRunning) {
                //取0~DEFAULT_RANGE_FOR_SLEEP值的一个随机数
                Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

                data = "data: " + count.incrementAndGet(); // 以原子方式将count当前值+1
                System.out.println(threadName + "正在生产数据...将数据：" + data + "放入队列...");
                //设定的等待时间为2s，如果超过2s还没加进去返回true
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("放入数据失败：" + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            //设置线程中断
            Thread.currentThread().interrupt();
            System.out.println("ppppppppppppp");
        } finally {
            System.out.println(threadName + "退出生产者线程！");
        }

    }

    public void stop() {
        isRunning = false;
    }

}
