package com.thread.semaphore;

public class SemaphoreTest3 {
    public static void main(String args[]) {
        SemaphoreService3 service = new SemaphoreService3(); // 使用总 6 通路，每个线程占用2通路，尝试获取锁
        for (int i = 0; i < 10; i++) {
            MyThread t = new MyThread("thread" + (i + 1), service);
            t.start();
        }
    }
}