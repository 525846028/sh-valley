// Kafka Producer Example
// Author: cocowool@qq.com<https://www.github.com/cocowool>
package cn.edulinks;

import java.util.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducer {
    private final static String TOPIC = "test";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";

    public void run(){
        System.out.println("Kafka Producer Running.");
    }
}