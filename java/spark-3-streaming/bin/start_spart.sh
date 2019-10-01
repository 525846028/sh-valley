#!/bin/bash

SPARK_HOME=/Users/rousseau/Projects/tools/spark-2.4.1-bin-hadoop2.7
APP_HOME=/Users/rousseau/Projects/sh-valley/java/spark-4-kafka
PID_NAME=StreamingProject
MAIN_NAME=StreamingProject
SPARK_LIB=$APP_HOME/lib
APP_JAR=/Users/rousseau/Projects/sh-valley/java/spark-3-streaming/target/streaming-project-1.0.jar

# 使用前需使用nc命令在另一个Term窗口执行 nc -lk 3333 命令
$SPARK_HOME/bin/spark-submit \
--master spark://localhost:7077 \
--conf spark.executor.extraClassPath=$SPARK_LIB \
--conf spark.driver.extraClassPath=$SPARK_LIB \
--class StreamingProject $APP_JAR localhost 3333

