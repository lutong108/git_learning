package com.thread.future;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTest {
Map<String, Object> params = new HashMap<String,Object>(); 
	
	ExecutorService executorService = Executors.newFixedThreadPool(3);
	
	public void test1(){
		params.put("clsId","clsId-001");
		params.put("ids", "001,002,003,004");
		MyTask myTask = new MyTask(params);
		FutureTask<Object> futureTask = new FutureTask<Object>(myTask);  
		
 
		executorService.submit(futureTask); 
		System.out.println("干别的事！！！");
		executorService.submit(getCollectJob(futureTask));
		
		System.out.println("HERE!!!");
	} 
	
	
	/**
	 * 返回收集工作
	 * @param futureTask
	 * @return
	 */
	public Runnable getCollectJob(final FutureTask<Object> futureTask) {
		return new Runnable() {
			public void run() {
				while(true){
					try {
						Thread.sleep(100); 
						if(futureTask.isDone()){
							Object resp = futureTask.get();
							System.out.println("2返回值：" + resp);
							break;
						}		
						System.out.println("2Not finished.");
					} catch (Exception e) {
						e.printStackTrace();
					} 
				} 
			}
		};
	}
	
	
	/**
	 * 关闭线程池
	 */
	public void closeThreadPool(){
		executorService.shutdown();
	}  
	
	
	/**
	 * 测试主入口
	 * @param args
	 */
	public static void main(String[] args) {
		FutureTest ft = new FutureTest();
		ft.test1();
 
		System.out.println("Do other things...");
		ft.closeThreadPool();
	}
}
