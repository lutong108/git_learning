package com.thread.ReentrantReadWriteLock;

public class ReentrantReadWriteLock_RR_Test {
	 public static void main(String[] args) {
	        final ReentrantReadWriteLock_RR myTask = new ReentrantReadWriteLock_RR();
	        
	        Thread t1 = new Thread(new Runnable() {

	            @Override
	            public void run() {
	                myTask.read();
	            }
	        });
	        t1.setName("t1");
	        
	        Thread t2 = new Thread(new Runnable() {

	            @Override
	            public void run() {
	                myTask.read();
	            }
	        });
	        t2.setName("t2");
	        
	        t1.start();
	        t2.start();
	    }
}
