package com.haska.zkdemo;

import org.I0Itec.zkclient.*;
import java.util.Set;
import java.util.HashSet;
import org.codehaus.jackson.annotate.*;
/**
 * Support all functions about zookeeper.
 * 1. Elect leader for cluster
 * 2. Elect leader for all broker when the conference held in more than one broker
 * 4. The broker need to connect zookeeper when it started
 * 5. 
 * 
 * All znode:
 * /controller
 * /broker/ids/0[1,2,3]
 * /conference/256[247,222]
 * @author Administrator
 *
 */
public class ZKUtils {
    public static final String CONTROLLER_PATH = "/controller";
    public static final String BROKER_IDS_PATH = "/broker/ids";
    public static final String CONFERENCE_PATH = "/conference";
    
    private ZkClient __zkcli = null;
    private Set<String> __zkpath = new HashSet<>();
    
    public ZKUtils(){    	
    	//__zkpath.add(CONTROLLER_PATH);
    	__zkpath.add(BROKER_IDS_PATH);
    	__zkpath.add(CONFERENCE_PATH);
    }
    
    public void init(String zkservers){
    	if (__zkcli == null){
    	    __zkcli = new ZkClient(zkservers);
    	}
    	
    	// Setup all persistent path
    	for (String path : __zkpath){
    		if (!__zkcli.exists(path)){
    		    __zkcli.createPersistent(path, true);
    		}
    	}
    }
    
    public ZkClient zkcli()
    {
    	return __zkcli;
    }
}

/***************** All zookeeper data structure *************************/
@JsonIgnoreProperties
class LeaderElectData
{
	int version;
	int brokerid;
	long timestamp;
	
	LeaderElectData(int v, int id, long ts)
	{
		version=v;
		brokerid=id;
		timestamp=ts;
	}
	/*
	int getVersion(){return version;}
	void setVersion(int v){version = v;}
	
	int getBrokerid(){return brokerid;}
	void setBrokerid(int id){brokerid = id;}
	
	long getTimestamp(){return timestamp;}
	void setTimestamp(long ts){timestamp = ts;}
	*/
}