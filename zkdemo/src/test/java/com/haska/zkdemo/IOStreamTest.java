package com.haska.zkdemo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IOStreamTest extends TestCase {
	  /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IOStreamTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( IOStreamTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	String str = "HelloWorld!";
    	
    	ByteArrayInputStream byteinput = new ByteArrayInputStream(str.getBytes());
    	int rc=-1;
    	while((rc=byteinput.read()) != -1){
    		System.out.println("byte:"+Integer.toHexString(rc));
    	}
    	byteinput.reset();
    	DataInputStream dis = new DataInputStream(byteinput);
    	//while(true){
    		try{
    		    System.out.println("data byte:"+Integer.toHexString(dis.readChar()));
    		    System.out.println("data char:"+Integer.toHexString(dis.readChar()));
    		    System.out.println("data int:"+Integer.toHexString(dis.readChar()));
    		    //dis.readLong();
    		}catch(EOFException e){
    			System.out.println("Read eof");
    			//break;
    		}catch(IOException e){
    			e.printStackTrace();
    		}
    	//}
    }
}
