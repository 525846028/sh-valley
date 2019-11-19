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
    private final static String TOPIC = "test001";
    private final static String BOOTSTRAP_SERVERS = "172.20.10.2:9092";

    //生成APM样例的报文数据
    public String getApmLog(){
        //Sample: app<1> trn<123> st<timestamp> tc<A001> cos<105>
        Map <Integer, String> app_map = new HashMap<>();
        app_map.put(1, "APM");
        app_map.put(2, "FASTPAY");
        app_map.put(3, "N-SDD");
        app_map.put(4, "N-CCSVC");
        app_map.put(5, "N-RCC");

        Map <Integer, String> tc_map = new HashMap<>();
        tc_map.put(1, "TC0001");
        tc_map.put(2, "TC0002");
        tc_map.put(3, "TC0003");
        tc_map.put(4, "TC0004");
        tc_map.put(5, "TC0005");

        //获取随机数
        Random r = new Random();
        r.nextInt(10);

        return "app<" + app_map.get(r.nextInt(5)+1) + "> trn<" + r.nextInt(100000) + "> st<" 
            + System.currentTimeMillis() + "> tc<" + tc_map.get(r.nextInt(5)+1) + "> cos<" 
            + r.nextInt(2000) + ">";
    }

    // 同步发送数据到Kafka
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
                final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, index, this.getApmLog());
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

    // 每10秒生成100条数据
    public void async_send10s(){
        System.out.println("Kafka Async Producer Running.");

        Runnable runnable = new Runnable(){
            public void run(){
                Properties props = new Properties();
                props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
                props.put(ProducerConfig.CLIENT_ID_CONFIG, "ProducerExample");
                props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
                props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

                Producer<Long, String> producer = new KafkaProducer<>(props);
                long time = System.currentTimeMillis();
                final CountDownLatch countDownLatch = new CountDownLatch(100);
                ApmLogGenerator log_generator = new ApmLogGenerator();

                try {
                    while(true){
                        try {
                            Thread.sleep(10000);
                            for (long index = time; index < time + 100; index++){
                                final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, index, log_generator.getLog());
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
                        }
                    }
                }catch(Exception e){
                    producer.flush();
                    producer.close();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    // 异步发送数据到Kafka
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