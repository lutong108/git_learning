package com.thread.block;

import java.util.ArrayList;
import java.util.List;

/**
 * 破坏不可抢占条件看上去很简单，核心是要能够主动释放它占有的资源，这一点 synchronized 是做不到的。原因是 synchronized
 * 申请资源的时候， 如果申请不到，线程直接进入阻塞状态了，而线程进入阻塞状态，啥都干不了，也释放不了线程已经占有的资源。 你可能会质疑，“Java
 * 作为排行榜第一的语言，这都解决不了？”你的怀疑很有道理，Java 在语言层次确实没有解决这个问题，不过在 SDK 层面还是解决了的，
 * java.util.concurrent 这个包下面提供的 Lock 是可以轻松解决这个问题的。关于这个话题，咱们后面会详细讲。
 * 
 * @author Administrator
 * 
 */
public class Allocator {

	private List<Object> als = new ArrayList<>();

	// 一次性申请所有资源
	synchronized boolean apply(Object from, Object to) {

		if (als.contains(from) || als.contains(to)) {

			return false;

		} else {

			als.add(from);

			als.add(to);

		}

		return true;

	}

	// 归还资源
	synchronized void free(Object from, Object to) {

		als.remove(from);

		als.remove(to);

	}

	/*
	 * class Account {
	 * 
	 * // actr 应该为单例
	 * 
	 * private Allocator actr;
	 * 
	 * private int balance;
	 * 
	 * // 转账
	 * 
	 * void transfer(Account target, int amt) {
	 * 
	 * // 一次性申请转出账户和转入账户，直到成功
	 * 
	 * while (!actr.apply(this, target));
	 * 
	 * try {
	 * 
	 * // 锁定转出账户
	 * 
	 * synchronized (this) {
	 * 
	 * // 锁定转入账户
	 * 
	 * synchronized (target) {
	 * 
	 * if (this.balance > amt) {
	 * 
	 * this.balance -= amt;
	 * 
	 * target.balance += amt;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } finally {
	 * 
	 * actr.free(this, target);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

	class Account {

		private int id;

		private int balance;

		// 转账

		void transfer(Account target, int amt) {

			Account left = this; // ①

			Account right = target; // ②

			if (this.id > target.id) { // ③

				left = target; // ④

				right = this; // ⑤

			} // ⑥

			// 锁定序号小的账户

			synchronized (left) {

				// 锁定序号大的账户

				synchronized (right) {

					if (this.balance > amt) {

						this.balance -= amt;

						target.balance += amt;

					}

				}

			}

		}

	}
}