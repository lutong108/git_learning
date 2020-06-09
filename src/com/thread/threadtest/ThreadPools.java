package com.thread.threadtest;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
	
	 public static void main(String[] args) throws Exception {
		 ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1,
                 0L, TimeUnit.MILLISECONDS,
                 new LinkedBlockingQueue<Runnable>());
		 threadPool.execute(null);
		 
        long startTime = System.currentTimeMillis();

        // 创建总任务，范围是从1到两亿(包含)，阈值为10的7次方，所以最终至少会有10个任务进行for循环的累加
        AccumulateTask forkJoinTask = new AccumulateTask(1, (int) 2e8+1, (long) 1e7);
        
        // 使用一个新创建的ForkJoinPool任务池运行ForkJoin任务
        new ForkJoinPool().submit(forkJoinTask);

        // 打印任务结果
        System.out.println("count = " + forkJoinTask.get());

        // 计算程序耗时并打印
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总耗时：%.2fs", (endTime - startTime) / 1e3));
    }

	 
	/**
	 * 任务
	 */
	 private static class AccumulateTask extends RecursiveTask<Long>{
		private static final long serialVersionUID = 1L;
		
		private long start;
	    private long end;
	    private long threshold;
	    
	    public AccumulateTask(long start, long end, long threshold){
	    	 this.start = start;
	         this.end = end;
	         this.threshold = threshold;
	    }
	    /**
	     * 计算
	     */
		@Override
		protected Long compute() {
			long left = start;
	        long right = end;
	        // 终止条件：如果当前处理的范围小于等于阈值(threshold)，
            // 那么就直接通过循环执行累加操作
            if (right - left <= (int) threshold) {
                long result = 0;
                for (long i = left; i < right; ++i) {
                    result += 1;
                }
                return result;
            }
            
           // 获取当前处理范围的中心点
           long mid = (start + end) / 2;
           
           // 拆分出两个子任务，一个从start到mid，一个从mid到end
           ForkJoinTask<Long> leftTask = new AccumulateTask(start, mid, threshold);
           ForkJoinTask<Long> rightTask = new AccumulateTask(mid, end, threshold);
            
           // 通过当前线程池运行两个子任务
           leftTask.fork();
           rightTask.fork();
           
           try {
               // 获取两个子任务的结果并返回
               return leftTask.get() + rightTask.get();
           } catch (Exception e) {
               return 0L;
           }
		}
	}
}
