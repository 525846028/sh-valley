#!/bin/bash

docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" --name es-single -p 9200:9200 -p 9300:9300 -v /Users/shiqiang/Projects/sh-valley/docker-conf/elasticstack/single-es/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -d docker.elastic.co/elasticsearch/elasticsearch:6.3.1
