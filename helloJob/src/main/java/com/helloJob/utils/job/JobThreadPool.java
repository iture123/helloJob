package com.helloJob.utils.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class JobThreadPool {
    private static ExecutorService pool; 
    static{
    	pool = Executors.newFixedThreadPool(10);
    }
    public static ExecutorService getInstance(){
    	return pool;
    }
}
