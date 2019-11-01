import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.properties;

public class FlinkKafka {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("bootstrap.server", "192.168.65.2");
        properties.put("group.id", "flink_kafka_consumer");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "5000");

        FlinkKafkaConsumer010 consumer010 = new FlinkKafkaConsumer010(
            "test_flink_input",
            new SimpleStringSchema(),
            properties
        );

        final int port;
        final String host;
        port = 9008;
        host = "192.168.65.2";

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        DataStream<String> text = env.socketTextStream(host, port, "\n");

        DataStream<WordWithCount> windowCounts = text
            .flatMap(new FlatMapFunction<String, WordWithCount>(){
                @Override
                public void flatMap(String value, Collector<WordWithCount> out){
                    for(String word : value.split("\\s")){
                        out.collect(new WordWithCount(word, 1L));
                    }
                }
            })
            .keyBy("word")
            .timeWindow(Time.seconds(5), Time.seconds(1))
            .reduce(new ReduceFunction<WordWithCount>(){
                @Override
                public WordWithCount reduce(WordWithCount a, WordWithCount b){
                    return new WordWithCount(a.word, a.count+b.count);
                }
            });

        windowCounts.print().setParallelism(1);
        env.execute("Socket Window WordCount");
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