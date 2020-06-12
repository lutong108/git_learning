package com.proxy;

/**
 * 
 * TODO
 *
 * 2018年10月10日下午6:41:24
 */
public class CustomerServiceImpl implements ICustomerService {
 
	@Override
	public void save() {
		System.out.println("客户保存了。。。。。");
	}
 
	@Override
	public int find() {
		System.out.println("客户查询数量了。。。。。");
		return 100;
	}

}
