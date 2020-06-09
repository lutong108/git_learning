package com.thread.threadtest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class BlockingQueue {

	 /** 存放元素的数组 */
    private final Object[] items;

    /** 弹出元素的位置 */
    private int takeIndex;

    /** 插入元素的位置 */
    private int putIndex;

    /** 队列中的元素总数 */
    private AtomicInteger count = new AtomicInteger(0);

    /** 显式锁 */
    private final ReentrantLock lock = new ReentrantLock();
    
    /** 插入锁 */
    private final ReentrantLock putLock = new ReentrantLock();
    
    /** 弹出锁 */
    private final ReentrantLock takeLock = new ReentrantLock();
    
    /** 队列未满的条件变量 */
    private final Condition notFull = lock.newCondition();

    /** 队列非空的条件变量 */
    private final Condition notEmpty = lock.newCondition();
    
    /**
     * 指定队列大小的构造器
     *
     * @param capacity  队列大小
     */
    public BlockingQueue(int capacity) {
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
        count.getAndIncrement();
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
        count.getAndDecrement();

        // 返回之前代码中取出的元素e
        return e;
    }

    /**
     * 将指定元素插入队列
     *
     * @param e 待插入的对象
     */
    public void put(Object e) throws InterruptedException {
    	/*putLock.lockInterruptibly();
    	try {
    		while(count.get() == items.length) {
    			notFull.await();
            }
            enqueue(e);
            notEmpty.signal();
		} catch (Exception e2) {
			e2.printStackTrace();
		}finally{
			lock.unlock();
		}*/
    	  putLock.lockInterruptibly();
          try {
              while (count.get() == items.length) {
                  // 队列已满时进入休眠
                  // 等待队列未满条件得到满足
                  notFull.await();
              }

              // 执行入队操作，将对象e实际放入队列中
              enqueue(e);
          } finally {
              putLock.unlock();
          }

          // 唤醒等待队列非空条件的线程
          // 为了防止死锁，不能在释放putLock之前获取takeLock
          signalNotEmpty();
    }

    /**
     * 从队列中弹出一个元素
     *
     * @return  被弹出的元素
     * @throws InterruptedException 
     */
    public Object take() throws InterruptedException{
    	/*takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                // 队列为空时进入休眠
                // 使用与显式锁对应的条件变量
            	notEmpty.await();
            }

            // 执行出队操作，将队列中的第一个元素弹出
            Object e = dequeue();

            // 通过条件变量唤醒休眠线程
            notFull.signal();

            return e;
        } finally {
            lock.unlock();
        }*/
    	Object e;
    	takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                // 队列为空时进入休眠
                // 等待队列非空条件得到满足
                notEmpty.await();
            }

            // 执行出队操作，将队列中的第一个元素弹出
            e = dequeue();
        } finally {
            takeLock.unlock();
        }

        // 唤醒等待队列未满条件的线程
        // 为了防止死锁，不能在释放takeLock之前获取putLock
        signalNotFull();

        return e;
    }
    
    /**
     * 唤醒等待队列非空条件的线程
     */
    private void signalNotEmpty() {
        // 为了唤醒等待队列非空条件的线程，需要先获取对应的takeLock
        takeLock.lock();
        try {
            // 唤醒一个等待非空条件的线程
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }
    
    /**
     * 唤醒等待队列未满条件的线程
     */
    private void signalNotFull() {
        // 为了唤醒等待队列未满条件的线程，需要先获取对应的putLock
        putLock.lock();
        try {
            // 唤醒一个等待队列未满条件的线程
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }
}
