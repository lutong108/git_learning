package com.thread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SynTest {
    /*public static void main(String[] args) throws InterruptedException {
        AtomicInteger ai = new AtomicInteger();

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Accumlator(ai), "thread-" + i);
            list.add(t);
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.out.println(ai.get());
    }*/
	public static void main(String[] args) throws InterruptedException {
        Integer ai = 0;

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Accumlator2(ai), "thread-" + i);
            list.add(t);
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.out.println(ai);
    }
	

    static class Accumlator implements Runnable {
        private AtomicInteger ai;

        Accumlator(AtomicInteger ai) {
            this.ai = ai;
        }

        @Override
        public void run() {
            for (int i = 0, len = 1000; i < len; i++) {
                ai.incrementAndGet();
            }
        }
    }
    
    static class Accumlator2 implements Runnable {
        private Integer ai;

        Accumlator2(Integer ai) {
            this.ai = ai;
        }

        @Override
        public void run() {
            for (int i = 0, len = 1000; i < len; i++) {
                ai++;
            }
        }
    }
}