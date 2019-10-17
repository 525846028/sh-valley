
/**
 * SimpleApp.java
 */
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;

public class SimpleApp {
    public static void main(String[] args){
        //文件路径要修改为本地对应路径
        String logFile = "/Users/rousseau/Projects/sh-valley/java/spark-1-simpleApp/shakespear.txt";

        SparkSession spark = SparkSession.builder().appName("Simple Application").getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();

        long numNos = logData.filter((FilterFunction<String>) x -> x.contains("no")).count();
        // long numNos = logData.filter(new FilterFunction<String>(){
        //     public boolean call(String x){
        //         return x.contains("no");
        //     }
        // }).count();

        

        long numYes = logData.filter((FilterFunction<String>) s->s.contains("yes")).count();
        System.out.println("Lines with no : " + numNos + ", with yes : " + numYes);


        spark.stop();
    }
}