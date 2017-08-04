package com.haska.zkdemo;

import java.io.IOException;

import com.haska.network.AbstractAdapter;
import com.haska.network.Acceptor;
import com.haska.network.EventObject;
import com.haska.network.IOAdapter;
import com.haska.network.IOThread;
import com.haska.network.Listener;
import com.haska.network.TcpHandler;
import com.haska.network.ThreadPool;

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
                    
                    };
                    
            	}
            });
            
            IOThread cli_thr = ThreadPool.getInstance().getIOThread();
            cli_thr.addListener(new EventObject("connect", new Listener(){
                private TcpHandler th;
				@Override
				public void process(EventObject eo) {
					// TODO Auto-generated method stub
					if (eo.type() == "connect"){
						th = new TcpHandler(cli_thr, new AbstractAdapter(){
							public void onConnected() {
			            		System.out.println("Async connect success");
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
            Thread.sleep(60*60*1000);
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
