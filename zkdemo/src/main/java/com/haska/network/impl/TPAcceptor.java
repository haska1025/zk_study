package com.haska.network.impl;

import java.io.IOException;

import com.haska.network.AcceptorCallback;
import com.haska.network.IOAdapter;

public class TPAcceptor {
    private Acceptor acceptor = null;
    private AcceptorCallback cb = null;
    
    public TPAcceptor(AcceptorCallback cb){
    	this.cb = cb;
    }
    public void start(int port) throws IOException{
    	start(null, port);
    }
    public void start(String hostname, int port) throws IOException{
    	acceptor = new Acceptor(ThreadPool.getInstance().getIOThread(), new AbstractAdapter(){            	
        	public IOAdapter onAccept() {
        		System.out.println("Create a new adapter");
        		// TODO Auto-generated method stub
                return new TPSession(cb);
        	}
        });
    }
    
    public void stop(){
    	acceptor.stop();
    }
}
