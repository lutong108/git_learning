package com.thread;

/**
 * count值将小于20000
 */
public class ThreadTest {
	private static int count = 0;
	
    public static void main(String[] args) throws Exception {
        Runnable task = new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; ++i) {
                	synchronized (ThreadTest.class) {
                        count += 1;
                    }
                }
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("count = " + count);
    }
}