package com.thread.block;

import java.util.Vector;

//以下代码来源于【参考 1】

public class VolatileExample {

	static int x=1;
	
	public static void calc(){

		// 创建两个线程，执行 add() 操作

		Thread th1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(x);
			}
		});

		// 启动两个线程
		th1.start();

	}
	
	public static void main(String[] args) {
		x=23;
		calc();
	}
}
