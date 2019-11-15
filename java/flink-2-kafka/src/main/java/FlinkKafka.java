import java.util.Properties;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.HashMap;
import java.util.Map;

public class FlinkKafka {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        properties.setProperty("bootstrap.server", "192.168.65.2:9092");
        properties.setProperty("zookeeper.connect", "192.168.65.2:2181");
        properties.setProperty("group.id", "flink_kafka_consumer");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.commit.interval.ms", "5000");

        FlinkKafkaConsumer010 consumer010 = new FlinkKafkaConsumer010(
            "test_flink_input",
            new SimpleStringSchema(),
            properties
        );

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> ds = env.addSource(consumer010);

        // consumer010.assignTimestampsAndWatermarks(new CustomWatermarkEmitter());
        // // 使用EventTime进行数据处理
        // env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        ds.rebalance().map(new MapFunction<String, String>(){
            private static final long serialVersionUID = 1L;

            @Override
            public String map(String value) throws Exception {
                return value;
            }
        });

        ds.print();

        env.execute("Flink Streaming Get Kafka Data");
        
    }

    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount(){}

        public WordWithCount(String word, long count){
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString(){
            return word + ":" + count;
        }
    }
}