package com.haska.zkdemo;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * bin/kafka-topics.sh --zookeeper 192.168.32.107:2181 --create --topic joinconf --partitions 6 --replication-factor 1
 */
public class Producer extends Thread {
	private final Logger logger = LoggerFactory.getLogger(Producer.class);
	
    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final Boolean isAsync;
    private final Integer []ids = {234,300127,2343412,866344,2452253,123414};

    public Producer(String topic, Boolean isAsync) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.32.107:9092");
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    public void run() {
        int messageNo = 1;
        while (true) {
			for (int i = 0; i < ids.length; ++i) {
				String messageStr = "Message_" + ids[i].toString() + "_" + messageNo;
				long startTime = System.currentTimeMillis();
				if (isAsync) { // Send asynchronously
					producer.send(new ProducerRecord<>(topic, ids[i].intValue() % ids.length, messageNo, messageStr),
							new DemoCallBack(startTime, messageNo, messageStr));
					logger.debug("Producer aync send message");
				} else { // Send synchronously
					try {
						producer.send(new ProducerRecord<>(topic, ids[i].intValue() % ids.length, messageNo, messageStr)).get();
						// System.out.println("Sent message: (" + messageNo + ",
						// " + messageStr + ")");
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
				++messageNo;
			}
			try{
			    Thread.sleep(800);
			}catch(Exception e){
				e.printStackTrace();
			}
        }
    }
}

class DemoCallBack implements Callback {

    private final long startTime;
    private final int key;
    private final String message;

    public DemoCallBack(long startTime, int key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    /**
     * A callback method the user can implement to provide asynchronous handling of request completion. This method will
     * be called when the record sent to the server has been acknowledged. Exactly one of the arguments will be
     * non-null.
     *
     * @param metadata  The metadata for the record that was sent (i.e. the partition and offset). Null if an error
     *                  occurred.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println(
                "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                    "), " +
                    "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
        } else {
            exception.printStackTrace();
        }
    }
}