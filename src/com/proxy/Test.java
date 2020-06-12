package com.proxy;


public class Test {

	public static void main(String[] args) {
		ICustomerService customerService = new CustomerServiceImpl();
		customerService.save();
		//利用代理对象工厂生成一个代理对象
		JdkProxyFactory factory = new JdkProxyFactory(customerService);
		ICustomerService proxy = (ICustomerService)factory.getProxyObject();
		proxy.save();
		
	}

}
