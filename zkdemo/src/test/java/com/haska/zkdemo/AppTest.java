package com.haska.zkdemo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import com.haska.network.impl.AbstractAdapter;
import com.haska.network.impl.Acceptor;
import com.haska.network.EventObject;
import com.haska.network.IOAdapter;
import com.haska.network.impl.IOThread;
import com.haska.network.Listener;
import com.haska.network.impl.TcpHandler;
import com.haska.network.impl.ThreadPool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        try{
        	// IOThread test case
            IOThread iothr = new IOThread();
            iothr.start();
            iothr.addListener(new EventObject("test", new Listener(){
            	public void process(EventObject eo){
            		System.out.println("Test listener");
            	}
            }));
            
            Thread.sleep(1000);
            
            iothr.stop();
            // ThreadPool test case
            ThreadPool.getInstance().initialize(4);
            Acceptor accept = new Acceptor(ThreadPool.getInstance().getIOThread(), new AbstractAdapter(){            	
            	public IOAdapter onAccept() {
            		System.out.println("Create a new adapter");
            		// TODO Auto-generated method stub
                    return new AbstractAdapter(){
                    	ByteBuffer sendbuff_ = ByteBuffer.allocate(25);
                    	boolean new_write = true;
                        ByteBuffer recvbuff_ = ByteBuffer.allocate(25);
                        public void sendMsg(){                	
                    		try{                    			
                    			if (new_write){
                    				sendbuff_.put("Hello client!".getBytes());
                    				sendbuff_.flip();
                    				new_write = false;
                    			}
                    		    int need_sendbytes = sendbuff_.remaining();
                    		    int sbytes = sockhandler.send(sendbuff_);
                    		    if (sbytes < need_sendbytes){
                    		    	sockhandler.registerEvent(SelectionKey.OP_WRITE);
                    		    	System.out.println("Need register write events");			            		    	
                    		    }else{
                    		    	// Send finish
                    		    	sendbuff_.clear();
                    		    	new_write = true;
                    		    }
                    		}catch(IOException e){
                    			e.printStackTrace();			            			
                    			sockhandler.close();			            			
                    		}
                        }
                        public void onRead(){
							try{
								int rbytes = sockhandler.recv(recvbuff_);
								recvbuff_.flip();
								byte[] rmsg = new byte[rbytes];
								recvbuff_.get(rmsg);
								System.out.println("server rcv msg:"+new String(rmsg));
								recvbuff_.clear();
								// Send msg to client
								sendMsg();
							}catch(IOException e){
								sockhandler.close();
							}
						}
					    public void onWrite(){
					    	System.out.println("Can send by write events");
					    	sendMsg();
					    }
					    public void onClose(){
					    	sockhandler.close();
					    }
                    };                    
            	}
            });
            
            IOThread cli_thr = ThreadPool.getInstance().getIOThread();
            cli_thr.addListener(new EventObject("connect", new Listener(){
                private TcpHandler th;
                ByteBuffer sendbuff_ = null;
                boolean new_write = true;
                ByteBuffer recvbuff_ = null;
                public void sendMsg(){                	
            		try{            			
            			
            			if (new_write){
            				sendbuff_.put("Hello server!".getBytes());
            				sendbuff_.flip();
            				new_write = false;
            			}
            			int need_sendbytes = sendbuff_.remaining();
            		    int sbytes = th.send(sendbuff_);
            		    if (sbytes < need_sendbytes){
            		    	th.registerEvent(SelectionKey.OP_WRITE);
            		    	System.out.println("Need register write events");			            		    	
            		    }else{
            		    	// Send finish
            		    	sendbuff_.clear();
            		    	new_write = true;
            		    }
            		}catch(IOException e){
            			e.printStackTrace();
            			th.close();
            		}
                }
				@Override
				public void process(EventObject eo) {
					// TODO Auto-generated method stub
					if (eo.type() == "connect"){
						th = new TcpHandler(cli_thr, new AbstractAdapter(){
							public void onConnected() {
			            		System.out.println("Async connect success and ready to send data.");
			            		sendbuff_ = ByteBuffer.allocate(25);
			            		recvbuff_ = ByteBuffer.allocate(25);
			            		sendMsg();
			            	}
							public void onRead(){
								try{
									int rbytes = th.recv(recvbuff_);
									recvbuff_.flip();
									byte[] rmsg = new byte[rbytes];
									recvbuff_.get(rmsg);
									System.out.println("client rcv msg:" + new String(rmsg));
									recvbuff_.clear();
									
									sendMsg();
								}catch(IOException e){
									th.close();
								}
							}
						    public void onWrite(){
						    	System.out.println("Can send by write events");
						    	sendMsg();
						    }
						    public void onClose(){
						    	th.close();
						    }
						});
						try{
						    // Connect the local host.						    
						    if (th.connect("127.0.0.1", 8000)){
						    	System.out.println("Connect one times");
						    }
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
            	
            }));
            accept.start(8000);
            Thread.sleep(5*1000);
            ThreadPool.getInstance().destroy();
            System.out.println("Test case exit");
        }catch(IOException e){
        	e.printStackTrace();
        }catch(InterruptedException e){
        	e.printStackTrace();
        }
        assertTrue( true );
    }    
   
}
