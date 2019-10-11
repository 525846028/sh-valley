[TOC]

## 交互式分析
Spark支持交互式分析数据，但是仅支持`scala`和`python`语言。
下面是使用python来分析一段莎士比亚小说文字的示例。
```python
$ ./bin/pyspark 
>>> textFile = spark.read.text("/Users/shiqiang/Desktop/shakespeare.txt")
>>> textFile.count()
1995                                                                            
>>> textFile.first()
Row(value=u"Scanner's Notes: What this is and isn't. This was taken from")
>>> lineWithNo = textFile.filter(textFile.value.contains("no"))
>>> lineWithNo.count()
331
```

但是这种使用方式感觉和pandas也没什么区别，没办法深入到Spark的精髓。

## Java
一个简单的在本地运行的处理本地文件的演示程序。
```java
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;

public class SimpleApp {
    public static void main(String[] args){
        String logFile = "/Users/shiqiang/Desktop/shakespeare.txt";

        SparkSession spark = SparkSession.builder().appName("Simple Application").getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();

        long numNos = logData.filter((FilterFunction<String>) x -> x.contains("no")).count();   

        long numYes = logData.filter((FilterFunction<String>) s->s.contains("yes")).count();
        System.out.println("Lines with no : " + numNos + ", with yes : " + numYes);


        spark.stop();
    }
}
```

## 参考
1. [quick start spark](http://spark.apache.org/docs/latest/quick-start.html)
2. [Java Spark 简单示例（五）Spark Streaming](https://www.jianshu.com/p/c72cc55d7af5)
3. []()
4. []()
5. []()
6. []()
7. []()
8. []()
9. []()
10. []()