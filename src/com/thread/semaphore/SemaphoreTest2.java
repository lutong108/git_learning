package com.thread.semaphore;

public class SemaphoreTest2 {
    public static void main(String args[]) {
        SemaphoreService2 service = new SemaphoreService2(); // 使用总 6 通路，每个线程占用2通路
        for (int i = 0; i < 10; i++) {
            MyThread t = new MyThread("thread" + (i + 1), service);
            t.start();// 这里使用 t.run() 也可以运行，但是不是并发执行了 
            System.out.println("可用通路数：" + service.availablePermits());
        }
    }
}