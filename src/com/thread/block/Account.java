package com.thread.block;

public class Account {
	private int balance;

	// 转账

	public void transfer(Account target, int amt) {

		// 锁定转出账户
		/**
		 	对于“占用且等待”这个条件，我们可以一次性申请所有的资源，这样就不存在等待了。
			对于“不可抢占”这个条件，占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放它占有的资源，这样不可抢占这个条件就破坏掉了。
			对于“循环等待”这个条件，可以靠按序申请资源来预防。所谓按序申请，是指资源是有线性顺序的，申请的时候可以先申请资源序号小的，再申请资源序号大的，
			这样线性化后自然就不存在循环了。
		 */
		synchronized (this) {

			// 锁定转入账户

			synchronized (target) {

				if (this.balance > amt) {

					this.balance -= amt;

					target.balance += amt;

				}
				System.out.println(this.balance+":"+target.balance);
			}

		}

	}
	public static void main(String[] args) {
		final Account test = new Account();
		final Account testB = new Account();
		final Account testA = new Account();
		Thread th1 = new Thread(new Runnable() {
			@Override
			public void run() {
				test.transfer(testB,100);
			}
		},"th1");

		Thread th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				test.transfer(testA,100);
			}

		},"th2");
		th1.start();
		
		th2.start();
	}

}