package com.thread.future;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

public class FutureDemo {
	/*private Map<String, Connection> connectionPool = new HashMap<String, Connection>();
    private ReentrantLock lock = new ReentrantLock();
    
    public Connection getConnection(String key){
        try{
            lock.lock();
            if(connectionPool.containsKey(key)){
                return connectionPool.get(key);
            }
            else{
                //创建 Connection
                Connection conn = createConnection();
                connectionPool.put(key, conn);
                return conn;
            }
        }
        finally{
            lock.unlock();
        }
    }
    
    //创建Connection
    private Connection createConnection(){
        return null;
    }*/
	
	
	 private ConcurrentHashMap<String,FutureTask<Connection>> connectionPool = 
			 new ConcurrentHashMap<String, FutureTask<Connection>>();
	    
	    public Connection getConnection(String key) throws Exception{
	    	
	        FutureTask<Connection> connectionTask = connectionPool.get(key);
	        
	        if(connectionTask != null){
	        	
	            return connectionTask.get();
	        }else{
	        	
	            Callable<Connection> callable = new Callable<Connection>(){
	                @Override
	                public Connection call() throws Exception {
	                    return createConnection();
	                }
	            };
	            
	            FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
	            
	            connectionTask = connectionPool.putIfAbsent(key, newTask);
	            
	            if(connectionTask==null){
	                connectionTask = newTask;
	                connectionTask.run();
	            }
	            
	            return connectionTask.get();
	        }
	    }
	    
	    //创建Connection
	    private Connection createConnection(){
	        return null;
	    }
}
