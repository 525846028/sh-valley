/**
 * Spark Streaming Example
 * Connect to Kafka
 * 
 * @author shiqiang 2019-9-4
 * 
 */

//  package com.edulinks.spark.example;
import java.util.*;

import scala.Tuple2;

import org.apache.spark.SparkConf;
import org.apache.spark.TaskContext;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

public final class StreamingKafka {
    // private static final Pattern SPACE = Pattern.compile(" ");


    public static void main(String[] args) throws Exception {
        String brokers = "localhost:9092";
        String topics = "test";
        String groupid = "kafkaConsumeGroupTest";

        if (args.length < 2) {
            System.err.println("Usage: StreamingKafka <brokerlist> <topic>");
            System.exit(1);
        }else{
            brokers = args[0];
            topics = args[1];
        }

        SparkConf sparkConf = new SparkConf().setAppName("StreamingKafka");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        sc.setLogLevel("DEBUG");
        JavaStreamingContext jssc = new JavaStreamingContext(sc, Durations.seconds(2));

        //需要消费的TOPIC列表
        Collection<String> topicSet = new HashSet<>(Arrays.asList(topics.split(",")));

        //Kafka初始化连接参数
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", brokers);
        kafkaParams.put("bootstrap.servers", brokers);
        kafkaParams.put("group.id", groupid);
        kafkaParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("enable.auto.commit", false);

        JavaInputDStream<ConsumerRecord<Object,Object>> lines = KafkaUtils.createDirectStream(
            jssc,
            LocationStrategies.PreferConsistent(),
            ConsumerStrategies.Subscribe(topicSet, kafkaParams)
            );

        JavaPairDStream<String, Integer> counts = lines.flatMap(x -> Arrays.asList(x.value().toString().split(" ")).iterator())
            .mapToPair(x -> new Tuple2<String, Integer>(x, 1))
            .reduceByKey((x,y) -> x + y);

        counts.print();

        jssc.start();
        jssc.awaitTermination();
        jssc.close();

    }
}