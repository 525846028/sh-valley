#!/bin/bash

SPARK_HOME=/Users/rousseau/Projects/tools/spark-2.4.1-bin-hadoop2.7
APP_HOME=/Users/rousseau/Projects/sh-valley/java/spark-1-simpleApp
PID_NAME=SimpleApp
MAIN_NAME=SimpleApp
SPARK_LIB=$APP_HOME/lib
APP_JAR=/Users/rousseau/Projects/sh-valley/java/spark-1-simpleApp/target/simple-project-1.0.jar

$SPARK_HOME/bin/spark-submit \
--conf spark.executor.extraClassPath=$SPARK_LIB \
--conf spark.driver.extraClassPath=$SPARK_LIB \
--master spark://localhost:7077 \
--deploy-mode cluster \
--class SimpleApp $APP_JAR
