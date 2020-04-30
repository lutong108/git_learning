package com.thread.block;
import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Blank
 * Date: 14-3-28
 * Time: 下午7:49
 */
public class TestJoin implements Runnable {

	static int x=1;
    public static void main(String[] sure) throws InterruptedException {
        Thread t = new Thread(new TestJoin());
        long start = System.currentTimeMillis();
        t.start();
        sleep(2000);
        //t.join();//等待线程t 1000毫秒
        System.out.println(System.currentTimeMillis()-start);//打印出时间间隔
        System.out.println("Main finished");//打印主线程结束
        System.out.println(x);
    }

    @Override
    public void run() {
        //synchronized (currentThread()) {
            for (int i = 1; i <= 5; i++) {
                try {
                	x=x+2;
                    sleep(1000);//睡眠5秒，循环是为了方便输出信息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("睡眠" + i);
            }
            System.out.println("TestJoin finished");//t线程结束
        //}
    }
}