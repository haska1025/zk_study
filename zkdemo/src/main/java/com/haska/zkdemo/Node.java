package com.haska.zkdemo;

public class Node {
    private final int id;
    private final String host;
    private final short port;
    
    public Node(int id, String host, short port)
    {
    	this.id = id;
    	this.host = host;
    	this.port = port;
    }
    
    public int id(){return id;}
    public String host(){return host;}
    public short port(){return port;}
}
