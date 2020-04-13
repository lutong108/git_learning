package com.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreService3 extends SemaphoreService { // 之所以继承 SemaphoreService，仅仅是为了使用父类的打印时间的方法 0.0

    private Semaphore semaphore = new Semaphore(6, true);// 6表示总共有6个通路，true 表示公平

    public void doSomething() {
        try {
            if (semaphore.tryAcquire(2, 3, TimeUnit.SECONDS)) { // 在 3秒 内 尝试获取 2 个通路
                System.out.println(Thread.currentThread().getName() + ":doSomething start-" + getFormatTimeStr());
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ":doSomething end-" + getFormatTimeStr()
                		+ "，当前是否有进程等待：" + semaphore.hasQueuedThreads() + "，等待进程数：" + semaphore.getQueueLength());
                semaphore.release(2); // 释放占用的 2 个通路
            } else {
                System.out.println(Thread.currentThread().getName() + ":doSomething 没有获取到锁-准备退出-" + getFormatTimeStr());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int availablePermits() {
        return semaphore.availablePermits();
    }
}