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
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

public final class StreamingKafka {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception {
        if( args.length < 3){
            System.out.println("Please enter the right parameters.");

            System.exit(1);
        }

        String brokers = args[0];
        String groupId = args[1];
        String topics = args[2];

        SparkConf sparkConf = new SparkConf().setAppName("StreamingKafka");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put();
    }
}