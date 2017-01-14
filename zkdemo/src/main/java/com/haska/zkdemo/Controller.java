package com.haska.zkdemo;

import org.I0Itec.zkclient.IZkDataListener;
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

        		}

        		public void handleDataDeleted(String dataPath) {

        		}
            });
			// Elect leader
			elect();
		}

		void elect() {

		}
	}

}
