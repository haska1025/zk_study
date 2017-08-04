package com.haska.network;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPool {
	private static final Logger LOG = LoggerFactory.getLogger(ThreadPool.class);
    private static ThreadPool instance_ = new ThreadPool();
    
    private IOThread[] thrs_;
    
    private ThreadPool(){}
    
    public void initialize(int thr_num){
    	thrs_ = new IOThread[thr_num];
		try {
			for (int i = 0; i < thr_num; i++) {
				thrs_[i] = new IOThread();
				thrs_[i].start();
			}
		} catch (IOException e) {
			LOG.error("Start iothread failed", e);
		}
    }
    
    public void destroy(){
    	for(IOThread iothr: thrs_){
    		iothr.stop();
    	}
    }
    public static ThreadPool getInstance(){
    	return instance_;
    }
    
    public IOThread getIOThread(){
    	return thrs_[0];
    }
}
