package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
 
@SuppressWarnings("serial")
public final class $Proxy0 extends Proxy implements IHello // 继承了Proxy类和实现自定义的IHello接口
{
  //变量，都是private static Method
  private static Method m3;
 //静态代码块初始化变量，m3就是要增强方法的method对象
 static{
	  try {
			m3 = Class.forName("com.jpeony.spring.proxy.jdk.IHello").getMethod("sayHello", new Class[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} 
 	}
  // 代理类的构造函数，其参数正是我们传入的InvocationHandler实例
  public $Proxy0(InvocationHandler invocationHandler){
    super(invocationHandler);
  }
  //代理方法,代理对象执行的就是这个增强方法
  public final void sayHello(){
	  try {
		/**
		 * this:调用的那个对象，即代理对象
		 * h:父类Proxy的成员变量，即我们传递的invocationHandler对象。
		 * invoke:调用的我们实现InvocationHandler接口时重写的invoke方法
		 */
		this.h.invoke(this, m3, null);
	} catch (Throwable e) {
		e.printStackTrace();
	}
  }

}
