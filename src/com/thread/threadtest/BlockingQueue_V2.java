package com.thread.threadtest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class BlockingQueue_V2 {

	 /** 存放元素的数组 */
    private final Object[] items;

    /** 弹出元素的位置 */
    private int takeIndex;

    /** 插入元素的位置 */
    private int putIndex;

    /** 队列中的元素总数 */
    private int count;

    /** 显式锁 */
    private final ReentrantLock lock = new ReentrantLock();

    /** 锁对应的条件变量 */
    private final Condition condition = lock.newCondition();
    
    /**
     * 指定队列大小的构造器
     *
     * @param capacity  队列大小
     */
    public BlockingQueue_V2(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        items = new Object[capacity];
    }

    /**
     * 入队操作
     *
     * @param e 待插入的对象
     */
    private void enqueue(Object e) {
        // 将对象e放入putIndex指向的位置
        items[putIndex] = e;

        // putIndex向后移一位，如果已到末尾则返回队列开头(位置0)
        if (++putIndex == items.length)
            putIndex = 0;

        // 增加元素总数
        count++;
    }

    /**
     * 出队操作
     *
     * @return  被弹出的元素
     */
    private Object dequeue() {
        // 取出takeIndex指向位置中的元素
        // 并将该位置清空
        Object e = items[takeIndex];
        items[takeIndex] = null;

        // takeIndex向后移一位，如果已到末尾则返回队列开头(位置0)
        if (++takeIndex == items.length)
            takeIndex = 0;

        // 减少元素总数
        count--;

        // 返回之前代码中取出的元素e
        return e;
    }

    /**
     * 将指定元素插入队列
     *
     * @param e 待插入的对象
     */
    public void put(Object e) throws InterruptedException {
        /*while (true) {
        	 synchronized (this) {
	            // 直到队列未满时才执行入队操作并跳出循环
	            if (count != items.length) {
	                // 执行入队操作，将对象e实际放入队列中
	                enqueue(e);
	                break;
	            }
        	 }
            // 队列已满的情况下休眠200ms
            Thread.sleep(200L);
        }*/
  		 
       	 /*synchronized (this) {
       		while(count == items.length) {
            	this.wait();
            }
       	 System.out.println("put count:"+count);
            enqueue(e);
            this.notifyAll();
       	 }*/
    	lock.lockInterruptibly();
    	try {
    		while(count == items.length) {
    			condition.await();
            }
            enqueue(e);
            condition.signalAll();
		} catch (Exception e2) {
			e2.printStackTrace();
		}finally{
			lock.unlock();
		}
    	
    }

    /**
     * 从队列中弹出一个元素
     *
     * @return  被弹出的元素
     * @throws InterruptedException 
     */
    public Object take() throws InterruptedException{
        /*while (true) {
        	synchronized (this) {
	            // 直到队列非空时才继续执行后续的出队操作并返回弹出的元素
	            if (count != 0) {
	                // 执行出队操作，将队列中的第一个元素弹出
	                return dequeue();
	            }
        	}
            // 队列为空的情况下休眠200ms
            Thread.sleep(200L);
        }*/

    	/*synchronized (this) {
    		while(count == 0) {
            	this.wait();
            }
    		 System.out.println("take count:"+count);
            Object e = dequeue();
            
            this.notifyAll();
            return e;
    	}*/
    	lock.lockInterruptibly();
        try {
            while (count == 0) {
                // 队列为空时进入休眠
                // 使用与显式锁对应的条件变量
                condition.await();
            }

            // 执行出队操作，将队列中的第一个元素弹出
            Object e = dequeue();

            // 通过条件变量唤醒休眠线程
            condition.signalAll();

            return e;
        } finally {
            lock.unlock();
        }
    	
    }
}
