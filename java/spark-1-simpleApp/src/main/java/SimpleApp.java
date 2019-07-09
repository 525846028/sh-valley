
/**
 * SimpleApp.java
 */
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;

public class SimpleApp {
    public static void main(String[] args){
        String logFile = "/Users/shiqiang/Desktop/shakespeare.txt";

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