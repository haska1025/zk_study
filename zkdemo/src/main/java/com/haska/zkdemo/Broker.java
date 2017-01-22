package com.haska.zkdemo;

import java.util.concurrent.ConcurrentLinkedDeque;
public class Broker {
    private ConcurrentLinkedDeque<Node> __inner_brokers = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<Node> __outer_brokers = new ConcurrentLinkedDeque<>();
  
    // Zookeeper wrapper
    private ZKUtils __zkutil = new ZKUtils();
    private Controller __controller = new Controller(this);
    private int __brokerid;
    private String __zkstr;
    
    public Broker(String zkstr)
    {
    	__zkstr = zkstr;
    }
    
    public void startup()
    {
    	__zkutil.init(__zkstr);
    	__controller.startup();
    }

    public void addToInnerBroker(Node n){
    	__inner_brokers.add(n);
    }
    
    ZKUtils zkutil(){return __zkutil;}    
    public int getBrokerId(){return __brokerid;}
    public void setBrokerId(int id){__brokerid = id;}
}
