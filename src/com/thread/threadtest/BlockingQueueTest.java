package com.thread.threadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class BlockingQueueTest {
	/**
	 * 经过分析，我们看到，在调用this.wait()后，如果线程被this.notifyAll()方法唤醒，那么就会直接开始直接入队/出队操作，
	 * 而不会再次检查count的值是否满足条件。而在我们的程序中，当队列为空时，可能会有很多消费者线程在等待插入元素。
	 * 此时，如果有一个生产者线程插入了一个元素并调用了this.notifyAll()，则所有消费者线程都会被唤醒，然后依次执行出队操作，
	 * 那么第一个消费者线程之后的所有线程拿到的都将是null值。而且同时，在这种情况下，每一个执行完出队操作的消费者线程也同样会调用this.notifyAll()方法，
	 * 这样即使队列中已经没有元素了，后续进入等待的消费者线程仍然会被自己的同类所唤醒，消费根本不存在的元素，最终只能返回null。
	 * 
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

        // 创建一个大小为2的阻塞队列
        final BlockingQueue q = new BlockingQueue(2);

       /* // 创建2个线程
        final int threads = 2;
        // 每个线程执行10次
        final int times = 10;*/
        // 创建400个线程
        final int threads = 400;
        // 每个线程执行100次
        final int times = 100;

        // 线程列表，用于等待所有线程完成
        List<Thread> threadList = new ArrayList<>(threads * 2);
        long startTime = System.currentTimeMillis();

        // 创建2个生产者线程，向队列中并发放入数字0到19，每个线程放入10个数字
        for (int i = 0; i < threads; ++i) {
            final int offset = i * times;
            Thread producer = new Thread(
            	new Runnable(){
     		        @Override public void run(){
     		        	try {
     	                    for (int j = 0; j < times; ++j) {
     	                        q.put(new Integer(offset + j));
     	                    }
     	                } catch (Exception e) {
     	                    e.printStackTrace();
     	                }
     		        }
                 },"producer"+i);

            threadList.add(producer);
            producer.start();
        }

        // 创建2个消费者线程，从队列中弹出20次数字并打印弹出的数字
        for (int i = 0; i < threads; ++i) {
            Thread consumer = new Thread(
            		new Runnable(){
         		        @Override public void run(){
         		        	try {
         	                    for (int j = 0; j < times; ++j) {
         	                        Integer element = (Integer) q.take();
         	                        System.out.println(element);
         	                    }
         	                } catch (Exception e) {
         	                    e.printStackTrace();
         	                }
         		        }
                     },"consumer"+i);

            threadList.add(consumer);
            consumer.start();
        }

        // 等待所有线程执行完成
        for (Thread thread : threadList) {
            thread.join();
        }

        // 打印运行耗时
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总耗时：%.2fs", (endTime - startTime) / 1e3));
    }
}
