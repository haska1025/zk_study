package com.haska.network.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haska.network.AcceptorCallback;
import com.haska.network.Session;
import com.haska.network.SessionCallback;
import com.haska.tpbuff.Command;

public class TPSession extends AbstractAdapter implements Session{
	private static final Logger LOG = LoggerFactory.getLogger(TPSession.class);

	private SessionCallback cb = null;	
	private ByteBuffer sendbuff_ = ByteBuffer.allocate(25);
	private boolean new_write = true;
	private ByteBuffer recvbuff_ = ByteBuffer.allocate(25);
    
	public TPSession(AcceptorCallback acb){		
		this.cb = acb.onAccept(this);		
	}
	public TPSession(SessionCallback cb){
		this.cb = cb;
	}
	// Session's method
	public boolean connnect(String hostname, int port) throws IOException{
		if (sockhandler == null){
			sockhandler = new TcpHandler(ThreadPool.getInstance().getIOThread(), this);
		}
		return sockhandler.connect(hostname, port);
	}
	
	@Override
	public void sendCommand(Command cmd) throws IOException {
		try{
			if (new_write){
                // Serialize to ByteBuffer
				new_write = false;
			}
		    int need_sendbytes = sendbuff_.remaining();
		    int sbytes = sockhandler.send(sendbuff_);
		    if (sbytes < need_sendbytes){
		    	sockhandler.registerEvent(SelectionKey.OP_WRITE);		    	
		    }else{
		    	// Send finish
		    	sendbuff_.clear();
		    	new_write = true;
		    }
		}catch(IOException e){
			LOG.warn("Send command meets io exception", e);
			close();					
		}
	}


	@Override
	public void close() {
		if (sockhandler != null)
			sockhandler.close();
		if (cb != null)
			cb.onClose();
		
		sendbuff_ = null;
		recvbuff_ = null;
	}

    // Adaptor's message
	public void onConnected() {		
        if (cb != null)
        	cb.onConnected();
	}
    public void onRead(){
		try{
			int rbytes = sockhandler.recv(recvbuff_);
			recvbuff_.flip();
            //Deserialize the ByteBuffer to Command

			recvbuff_.clear();
			// Send msg to client
			//sendMsg();
		}catch(IOException e){
			sockhandler.close();
		}
	}
    public void onWrite(){
    	System.out.println("Can send by write events");
    	try{
    	    this.sendCommand(null);
    	}catch(IOException e){
    		
    	}
    }
    public void onClose(){
    	sockhandler.close();
    }
}
