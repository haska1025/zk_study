package com.haska.network;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

public class SocketHandler implements IOHandler{
	protected SocketChannel sc_;
    protected IOThread io_thr_;
    protected IOAdapter io_adp_;
    
    IOThread io_thr(){return io_thr_;}   
    
	public SocketHandler(IOThread io_thr, IOAdapter io_adp){
		io_thr_ = io_thr;
		io_adp_ = io_adp;
	}

    public void handle_read()
    {
    	
    }
    public void handle_write()
    {
    	
    }
    public void handle_timeout(long timerid)
    {
    	
    }
    public void handle_connected()
    {
    	
    }
    public void handle_accept()
    {
    	
    }
    
    public AbstractSelectableChannel getSocketChannel()
    {
    	return sc_;
    }
    
    public void register(int ops) throws ClosedChannelException{
    	io_thr_.getPoller().register(this, ops);
    }
    
    public void Accept(SocketChannel sc) throws IOException,ClosedChannelException {}
}
