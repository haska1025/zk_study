package com.haska.network;

public class EventObject {    
    // The string type
    protected String type;
    
    // The process listener
    protected Listener listener;
    
    public EventObject(String t, Listener l){
    	this.type = t;
    	this.listener = l;
    }
    
    public Listener listen(){return listener;}
    public String type(){return type;}
}
