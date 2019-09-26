#!/bin/bash

while :
do
    ~/Projects/tools/kafka_2.11-2.1.0/bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic test
    echo "-----------------"
    sleep 5
done
