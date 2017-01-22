package com.haska.zkdemo;

import org.I0Itec.zkclient.IZkDataListener;
import org.apache.zookeeper.CreateMode;

public class Controller
{
	private ControllerLeaderElector __leader_elector = new ControllerLeaderElector();
	private Broker __broker = null;
	
	public Controller(Broker broker)
	{
		__broker = broker;
	}
    public void startup()
    {
        // Elect leader, register a ephemeral
    	__leader_elector.startup();
    	
    }

	class ControllerLeaderElector {
		void startup() {
			// Register data change listener
            __broker.zkutil().zkcli().subscribeDataChanges(ZKUtils.CONTROLLER_PATH, new IZkDataListener(){
            	public void handleDataChange(String dataPath, Object data) {
                    System.out.println("Data changed");
        		}

        		public void handleDataDeleted(String dataPath) {

        		}
            });
			// Elect leader
			elect();
		}

		void elect()
		{
			LeaderElectData led = new LeaderElectData(1,					
					__broker.getBrokerId(),
					System.currentTimeMillis());
            try{
            	String ledData = JsonUtil.encodeAsString(led);
            	String path = __broker.zkutil().zkcli().create(ZKUtils.CONTROLLER_PATH, ledData, CreateMode.EPHEMERAL);
            	if (path != null){
            		System.out.printf("The broker(%d) is elected leader", __broker.getBrokerId());
            	}
            }catch(Exception e){
            	e.printStackTrace();
            }
		}
	}

}
