package com.thread.future;

import java.util.Map;
import java.util.concurrent.Callable;

@SuppressWarnings("rawtypes")
public class MyTask implements Callable{

	private Map<String, Object> params;

	public MyTask(Map<String, Object> params) {
		super();
		this.params = params;
	}

	@Override
	public Object call() throws Exception {
		Thread.sleep(1000);
		System.out.println(params);
		return "Y";
	}
	
	
	
}
