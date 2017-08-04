package com.haska.network;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOThread {
	private static final Logger LOG = LoggerFactory.getLogger(IOThread.class);
	
    private Poller poller_ = new Poller();
    private boolean stop_ = false;
    private Worker worker = null;
    private ConcurrentLinkedQueue<EventObject> listener = new ConcurrentLinkedQueue<EventObject>();
    
    public Poller getPoller(){return poller_;}
    
    public void start() throws IOException{
    	poller_.start();
    	
    	// Start worker
    	worker = new Worker();
    	worker.start();
    }
    
    public void addListener(EventObject e){
    	listener.offer(e);
    	poller_.wakeup();
    }
    
    public void stop(){
		try {
			stop_ = true;
			poller_.wakeup();
			worker.join();
		} catch (InterruptedException e) {
			LOG.warn("Stop iothread met interrupted exception", e);
		}
    }
    
    void dispatchListeners(){
    	while (!listener.isEmpty()){
    		EventObject e = listener.poll();
    		e.listener.process(e);
    	}
    }
    class Worker extends Thread{
		@Override
		public void run() {
            LOG.info("Enter the worker thread");
  
			while(!stop_){
				try{
					dispatchListeners();
					poller_.poll();
				} catch (IOException e){
					LOG.warn("Worker met io exception", e);
				}
			}
			
			LOG.warn("The worker thread exit!!!");
		}
	}
}
