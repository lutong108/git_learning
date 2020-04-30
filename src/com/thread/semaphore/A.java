package com.thread.semaphore;

import java.util.concurrent.Semaphore;
public class A {
    static int count;
    static final Semaphore s = new Semaphore(2);
    static void addOne() throws InterruptedException {
        //只会有一个线程将信号量中的计数器减为1，而另外一个线程只能将信号量中计数器减为-1，导致被阻塞
        s.acquire();  
        try {
            count +=1;
            System.out.println("Now thread is " + Thread.currentThread() + "   and count is " + count);
        }finally {
            //进入临界区的线程在执行完临界区代码后将信号量中计数器的值加1然后，此时信号量中计数器的值为0，则从阻塞队列中唤醒被阻塞的进程
            s.release();   
        }
    }

    public static void main(String[] args) {
        // 创建两个线程运行
        MyThreadTest thread1 = new MyThreadTest();
        MyThreadTest thread2 = new MyThreadTest();

        thread1.start();
        thread2.start();
        System.out.println("main thread");

    }
}
class MyThreadTest extends Thread{
    @Override
    public void run() {
        super.run();
        for(int i=0; i<10; i++) {                   
            try {
                A.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}