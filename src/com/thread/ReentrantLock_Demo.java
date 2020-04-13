package com.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock_Demo {
	private static Lock lock=new ReentrantLock();
    public static void inc(){
    	for (int i = 0; i < 2; i++) {
    		lock.lock();
    		try {
    			Thread.sleep(200);
    			System.out.println(Thread.currentThread().getName());
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}finally{
    			lock.unlock();
    		}
		}
    }
    
    public void execute() throws InterruptedException {
        Thread threadA = new Thread(
	        new Runnable(){
		        @Override public void run(){
		            	inc();
		        }
        },"线程A");

        Thread threadB = new Thread(
        	new Runnable(){
		        @Override public void run(){
		            	inc();
		        }
        },"线程B");

        //启动线程
        threadA.start();
        threadB.start();
    }
    public static void main(String[] args) throws InterruptedException {
    	ReentrantLock_Demo threadTest = new ReentrantLock_Demo();
        threadTest.execute();
    }
}
