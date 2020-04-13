package com.thread;



/**
 * @author binghe
 * @version 1.0.0
 * @description 测试可见性
 */
public class ThreadTest {

    private int count = 0;
    private void  addCount(){
       count ++;
    }

    public long execute() throws InterruptedException {
        Thread threadA = new Thread(
	        new Runnable(){
		        @Override public void run(){
		            for(int i = 0; i < 1000; i++){
		                addCount();
		            }
		        }
        });

        Thread threadB = new Thread(
        	new Runnable(){
		        @Override public void run(){
		            for(int i = 0; i < 1000; i++){
		                addCount();
		            }
		        }
        });

        //启动线程
        threadA.start();
        threadB.start();

        //等待线程执行完成
        threadA.join();
        threadB.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        long count = threadTest.execute();
        System.out.println("结果："+count);
    }
}