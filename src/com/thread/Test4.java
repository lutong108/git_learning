package com.thread;


/**
 * @author binghe
 * @version 1.0.0
 * @description 测试原子性
 */
public class Test4 {

    private Long count;

    public Long getCount(){
        return count;
    }

    public void incrementCount(){
        count++;
    }
}
