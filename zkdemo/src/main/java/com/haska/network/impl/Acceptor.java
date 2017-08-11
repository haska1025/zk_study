package com.haska.network.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haska.network.EventObject;
import com.haska.network.IOAdapter;
import com.haska.network.Listener;

public class Acceptor extends SocketHandler implements Listener{
    private static final Logger LOG = LoggerFactory.getLogger(Acceptor.class);
	
    private ServerSocketChannel ssc_;
    
    public Acceptor(IOThread io_thr, IOAdapter io_adp){
    	super(io_thr, io_adp);
    }
     
    /**
     * Open the server socket
     * @param port the listen port
     * @return
     */
    public void start(int port) throws IOException{
    	this.start(null, port);
    }

    /**
     * Open the server socket
     * @param hostname the host name
     * @param port the listen port
     * @return 1 if success, otherwise -1.
     */
    public void start(String hostname, int port) throws IOException{
    	ssc_ = ServerSocketChannel.open();
		InetSocketAddress addr;
		if (hostname == null || hostname.isEmpty()) {
			addr = new InetSocketAddress(port);
		} else {
			addr = new InetSocketAddress(hostname, port);
		}
		ssc_.socket().bind(addr);
		ssc_.socket().setReuseAddress(true);
		ssc_.configureBlocking(false);		

		// Add listener to plug
		io_thr().addListener(new EventObject("plug", this));
    }
    
    public void stop(){
        try{
        	ssc_.close();        	
        } catch (IOException ie){
        	LOG.warn("Ignore the io exception", ie);
        } catch (Exception e){
        	LOG.warn("Ignore the exception", e);
        }    	
    }
    
    public void handle_accept()
    {
		try {
			SocketChannel asc = ssc_.accept();

			IOAdapter ioadp = io_adp_.onAccept();
			SocketHandler sh = new TcpHandler(io_thr(), ioadp);
			asc.configureBlocking(false);
			sh.accept(asc);
			ioadp.setSocketHandler(sh);
			LOG.debug("Accept a new connection");
		} catch (IOException e) {
            LOG.warn("Handle accept msg failed", e);
		} catch (Exception e){
			LOG.warn("Handle accept msg meet errors", e);
		}
    }
    
    public AbstractSelectableChannel getSocketChannel()
    {
    	return ssc_;
    }

	@Override
	public void process(EventObject eo) {
		try {
			if (eo.type().equals("plug")) {
				registerEvent(SelectionKey.OP_ACCEPT);
				LOG.info("Register accept event success");
			}
		} catch (ClosedChannelException e) {
			LOG.warn("Register accept failed", e);
		}
	}
    
}
