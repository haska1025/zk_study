package com.haska.zkdemo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello zkdemo!" );
        Broker broker = new Broker("192.168.32.107:2181/tang");
        broker.startup();
    }
}
