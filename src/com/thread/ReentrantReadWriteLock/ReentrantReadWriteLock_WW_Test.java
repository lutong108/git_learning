package com.thread.ReentrantReadWriteLock;

public class ReentrantReadWriteLock_WW_Test {
	
	public static void main(String[] args) {
        final ReentrantReadWriteLock_WW myTask = new ReentrantReadWriteLock_WW();
        
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.write();
            }
        });
        t1.setName("t1");
        
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.write();
            }
        });
        t2.setName("t2");
        
        t1.start();
        t2.start();
    }
}
