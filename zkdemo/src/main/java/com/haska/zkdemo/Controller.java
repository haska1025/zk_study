package com.haska.zkdemo;

import java.util.Collections;
import java.util.Properties;

import org.I0Itec.zkclient.IZkDataListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import org.apache.zookeeper.CreateMode;

public class Controller
{
	private ControllerLeaderElector __leader_elector = new ControllerLeaderElector();
	private Broker __broker = null;
	private Consumer __consumer = new Consumer("joinconf");
	
	public Controller(Broker broker)
	{
		__broker = broker;
	}
    public void startup()
    {
        // Elect leader, register a ephemeral
    	__leader_elector.startup();
    	
    	// Start consumer
    	__consumer.start();
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
	
	public class Consumer extends Thread {
	    private final KafkaConsumer<Integer, String> consumer;
	    private final String topic;

	    public Consumer(String topic) {
	        //super("KafkaConsumerExample", false);
	        Properties props = new Properties();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.32.107:9092");
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
	        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
	        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
	        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

	        consumer = new KafkaConsumer<>(props);
	        this.topic = topic;
	    }

	    @Override
	    public void run() {
	    	System.out.println("Enter consumer thread....");
			while (true) {
				//consumer.subscribe(Collections.singletonList(this.topic));
				consumer.assign(Collections.singletonList(new TopicPartition(this.topic, 866344%6)));
				ConsumerRecords<Integer, String> records = consumer.poll(1000);
				for (ConsumerRecord<Integer, String> record : records) {
					System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset "
							+ record.offset());
				}
			}
	    }	  
	}
}
