#!/bin/bash

#检查参数的数量
if [ $# -ne 2 ]
then
    echo "Usage: ./kafka_offset_count.sh topic brokerlist"
    exit 1
fi

if [ -n "$1" ]
then
    TOPIC=$1
else
    echo "Please input the topic name"
    exit 1
fi

if [ -n "$2" ]
then
    BROKERLIST=$2
else
    echo "Please input the broker list"
    exit 1
fi

while :
do
    ~/Projects/tools/kafka_2.11-2.1.0/bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list $BROKERLIST --topic $TOPIC
    echo "-----------------"
    sleep 5
done
