import java.util.Properties;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
// import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.HashMap;
import java.util.Map;

public class FlinkKafka {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Map properties = new HashMap();

        properties.put("bootstrap.servers", "172.18.0.5:9092");
        properties.put("zookeeper.connect", "172.18.0.4:2181");
        properties.put("group.id", "flink_kafka_consumer");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "5000");
        properties.put("topic", "test-flink-input");

        ParameterTool parameterTool = ParameterTool.fromMap(properties);

        FlinkKafkaConsumer010 consumer010 = new FlinkKafkaConsumer010(
            parameterTool.getRequired("topic"),
            new SimpleStringSchema(),
            parameterTool.getProperties()
        );

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