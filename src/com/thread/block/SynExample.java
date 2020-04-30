package com.thread.block;

import static java.lang.Thread.sleep;

public class SynExample {

	private  long count = 0l;
	
	private long getValue(){
		System.out.println("当前线程："+Thread.currentThread().getName()+count);
		return count;
	}

	private synchronized void add10K(){

		int idx = 0;

		while (idx++ < 1000) {
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count += 1;
			System.out.println(Thread.currentThread().getName()+":"+count);
		}

	}

	public SynExample calc() throws InterruptedException {

		final SynExample test = new SynExample();

		// 创建两个线程，执行 add() 操作

		Thread th1 = new Thread(new Runnable() {
			@Override
			public void run() {
				test.add10K();
			}
		},"th1");

		Thread th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				test.add10K();
			}

		},"th2");

		// 启动两个线程

		th1.start();
		
		th2.start();

		// 等待两个线程执行结束

		//th1.join();

		//th2.join();

		return test;

	}

	public static void main(String[] args) throws InterruptedException {
		SynExample synExample = new SynExample();
		SynExample synExample1 =synExample.calc();
		sleep(5);
		synExample1.getValue();
	}
}
