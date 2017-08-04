package com.haska.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpHandler extends SocketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(TcpHandler.class);
    
    public TcpHandler(IOThread io_thr, IOAdapter io_adp){
    	super(io_thr, io_adp);
    }
	public boolean connect(String hostname, int port) throws IOException{		
		sc_ = SocketChannel.open();
		sc_.configureBlocking(false);
		if (sc_.connect(new InetSocketAddress(hostname, port))){
			LOG.info("Nonblocking connect success 1 times {}:{}", hostname, port);
			return true;
		}
		
		// Register non-blocking connect events
		register(SelectionKey.OP_CONNECT);
		return false;
    }

	public void handle_connected() {
		try {
			if (sc_.finishConnect()) {
				io_adp_.onConnected();
				register(SelectionKey.OP_READ);
			}
		}catch(IOException e){
			LOG.warn("Handle connect meets error", e);;
		}
	}
	
	public void Accept(SocketChannel sc) throws IOException,ClosedChannelException {
		sc_ = sc;
		register(SelectionKey.OP_READ);
	}
}
