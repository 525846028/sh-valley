#!/bin/bash

# 连接到单台ES
docker run --name kibana6.6.2 -e ELASTICSEARCH_URL=http://192.168.1.1:9200 -p 5601:5601 -d 
