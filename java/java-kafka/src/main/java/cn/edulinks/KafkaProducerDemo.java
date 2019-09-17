// Kafka Producer Example
// Author: cocowool@qq.com<https://www.github.com/cocowool>
package cn.edulinks;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerDemo {
    private final static String TOPIC = "tst";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";

    public void run(){
        System.out.println("Kafka Producer Running.");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "ProducerExample");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<Long, String> producer = new KafkaProducer<>(props);
        long time = System.currentTimeMillis();

        try {
            for (long index = time; index < time + 100; index++){
                final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, index, "Hello i'am " + index);
                RecordMetadata metadata = producer.send(record).get();
                
                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                              "meta(partition=%d, offset=%d) time=%d\n",
                      record.key(), record.value(), metadata.partition(),
                      metadata.offset(), elapsedTime);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            producer.flush();
            producer.close();
        }
    }

    // 异步发送数据到
    public void async_send(){
        System.out.println("Kafka Async Producer Running.");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "ProducerExample");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<Long, String> producer = new KafkaProducer<>(props);
        long time = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(100);

        try {
            for (long index = time; index < time + 100; index++){
                final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, index, "Hello i'am " + index);
                producer.send(record, (metadata, exception) -> {
                    long elapsedTime = System.currentTimeMillis() - time;
                    if(metadata != null){
                        System.out.printf("sent record(key=%s value=%s) " +
                        "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);
                    }else{
                        exception.printStackTrace();
                    }
                    countDownLatch.countDown();
                });               
            }
            countDownLatch.await(25, TimeUnit.SECONDS);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            producer.flush();
            producer.close();
        }

    }
}