import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;

public class SimpleApp {
    public static void main(String[] args){
        String logFile = "/Users/shiqiang/Desktop/shakespeare.txt";

        SparkSession spark = SparkSession.builder().appName("Simple Application").getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();

        long numNos = logData.filter(s -> s.contains("no")).count();
        long numYes = logData.filter(s -> s.contains("yes")).count();

        System.out.println("Lines with no : " + numNos + ", with yes : " + numYes);

        spark.stop();
    }
}