package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 
 * TODO 通过工厂生成代理对象
 */
public class JdkProxyFactory {
	//被代理的对象
	private Object target;
	//使用有参数的构造方法设置代理对象 
	public JdkProxyFactory(Object target){
		this.target = target;
	}
	public Object getProxyObject(){
		
	/**
	 * 通过Proxy.newProxyInstance()方法获得target的代理对象,需要三个参数：
	 * 1,target.getClass().getClassLoader()：被代理对象的类加载器
	 * 2,target.getClass().getInterfaces()：存放了被代理对象实现的接口的字节码对象数组
	 * 3,new InvocationHandler(){...}:InvocationHandler的实现对象
	 */
	 Object newProxyInstance = Proxy.newProxyInstance(target.getClass().getClassLoader(),
			 	target.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				System.out.println("开始代理"); //增强实现
				Object result = method.invoke(target, args);
				System.out.println("代理结束"); //增强实现
				return result;
			}
		});
		return newProxyInstance;
	}
	
}
