#!/bin/bash

SPARK_HOME=/Users/rousseau/Projects/tools/spark-2.4.1-bin-hadoop2.7
APP_HOME=/Users/rousseau/Projects/sh-valley/java/spark-4-kafka
PID_NAME=StreamingKafka
MAIN_NAME=StreamingKafka
SPARK_LIB=$APP_HOME/lib
APP_JAR=/Users/rousseau/Projects/sh-valley/java/spark-4-kafka/target/KafkaTest-0.1.jar

$SPARK_HOME/bin/spark-submit \
--conf spark.executor.extraClassPath=$SPARK_LIB \
--conf spark.driver.extraClassPath=$SPARK_LIB \
--class StreamingKafka $APP_JAR localhost:9092 test001

