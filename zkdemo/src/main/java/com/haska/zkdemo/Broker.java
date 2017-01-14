package com.haska.zkdemo;

import java.util.concurrent.ConcurrentLinkedDeque;
public class Broker {
    private ConcurrentLinkedDeque<Node> __inner_brokers = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<Node> __outer_brokers = new ConcurrentLinkedDeque<>();
  
    // Zookeeper wrapper
    private ZKUtils __zkutil = new ZKUtils();
    private Controller __controller = new Controller(this);
    
    public void startup()
    {
    	__zkutil.init("");
    	__controller.startup();
    }
    
    ZKUtils zkutil()
    {
    	return __zkutil;
    }
    
    public void addToInnerBroker(Node n){
    	__inner_brokers.add(n);
    }
}
