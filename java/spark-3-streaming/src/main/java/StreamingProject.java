/**
 * Spark Streaming Project
 * @author Shiqiang
 * 
 */
import java.util.Arrays;
import java.util.regex.Pattern;

import scala.Tuple2;

// import org.apache.spark.*;
// import org.apache.spark.streaming.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public final class StreamingProject {

    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.err.println("Usage: NetworkWordCount <hostname> <port>");
            System.exit(1);
        }
      
        // Create the context with a 1 second batch size
        SparkConf sparkConf = new SparkConf().setMaster("local[4]").setAppName("StreamingProject");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));


        // Create a JavaReceiverInputDStream on target ip:port and count the
        // words in input stream of \n delimited text (eg. generated by 'nc')
        // Note that no duplication in storage level only for running locally.
        // Replication necessary in distributed scenario for fault tolerance.
        JavaReceiverInputDStream<String> lines = ssc.socketTextStream(
                args[0], Integer.parseInt(args[1]), StorageLevels.MEMORY_AND_DISK_SER);
        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(SPACE.split(x)).iterator());
        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(s -> new Tuple2<>(s, 1))
            .reduceByKey((i1, i2) -> i1 + i2);

        wordCounts.print();
        ssc.start();
        ssc.awaitTermination();

        // // Create a DStream that will connect to hostname:port, like localhost:9999
        // JavaReceiveInputDStream<String> lines = ssc.socketTextStream("localhost", 9999);

        // // Split each line into words
        // JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")).iterator());

        // // Count each word in each batch
        // JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        // JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey((i1, i2) -> i1 + i2);

        // // Print the first ten elements of each RDD generated in this DStream to the console
        // wordCounts.print();

        // jsc.start();              // Start the computation
        // jsc.awaitTermination();   // Wait for the computation to terminate
    }
}