package com.thread.block;

import static java.lang.Thread.sleep;

public class SeeExample {

	private static volatile long count = 0;

	private static void add10K(){

		int idx = 0;

		while (idx++ < 900000) {
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count += 1;

		}

	}

	public static long calc() throws InterruptedException {

		final SeeExample test = new SeeExample();

		// 创建两个线程，执行 add() 操作

		Thread th1 = new Thread(new Runnable() {
			@Override
			public void run() {
				test.add10K();
			}
		});

		Thread th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(count);
			}

		});

		// 启动两个线程

		th1.start();
		sleep(1000);
		th2.start();

		// 等待两个线程执行结束

		th1.join();

		th2.join();

		return count;

	}

	public static void main(String[] args) throws InterruptedException {
		SeeExample.calc();
	}
}
