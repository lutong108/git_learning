package com.thread.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreService2 extends SemaphoreService { // 之所以继承 SemaphoreService，仅仅是为了使用父类的打印时间的方法 0.0

    private Semaphore semaphore = new Semaphore(6);// 6表示总共有6个通路

    public void doSomething() {
        try {
            semaphore.acquire(2); // 2 表示进入此代码，就会消耗2个通路，2个通路从6个中扣除
            System.out.println(Thread.currentThread().getName() + ":doSomething start-" + getFormatTimeStr());
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + ":doSomething end-" + getFormatTimeStr());
            semaphore.release(2); // 释放占用的 2 个通路
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int availablePermits() {    // 查看可用通路数
        return semaphore.availablePermits();
    }
}