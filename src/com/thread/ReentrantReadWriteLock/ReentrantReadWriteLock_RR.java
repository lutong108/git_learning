package com.thread.ReentrantReadWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读读共享
 * @author Administrator
 *
 */
public class ReentrantReadWriteLock_RR {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public void read() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
}