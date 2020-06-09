package com.thread;

public class SynchronizedTest {
    public void method1(){
    	synchronized (this) {
        System.out.println("Method 1 start");
        try {

	            System.out.println("Method 1 execute"+":"+Thread.currentThread().getName());
	            Thread.sleep(3000);
        	
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");}
    }

    public void method2(){
    	synchronized (this) {
        System.out.println("Method 2 start");
        try {
        	
	            System.out.println("Method 2 execute"+":"+Thread.currentThread().getName());
	            Thread.sleep(1000);
        	
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");}
    }

    public static void main(String[] args) {
        final SynchronizedTest test = new SynchronizedTest();
        final SynchronizedTest test2 = new SynchronizedTest();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test2.method1();
            }
        }).start();
    }
}