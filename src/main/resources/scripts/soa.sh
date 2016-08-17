#!/bin/bash

 DIRECTORY=""

 if [ "$2" = "master" ]
 then
    DIRECTORY=/home/weihu/production-logs/service-master/dapeng
 elif [ "$2" = "slave" ]
 then 
    DIRECTORY=/home/weihu/production-logs/service-slave/dapeng
 else
    DIRECTORY=/home/weihu/production-logs/gray-1/dapeng
 fi

FILE=simple-dapeng-container.$1".log"

echo "analysis log file $DIRECTORY/$FILE"

less  $DIRECTORY/$FILE| awk '{print $11" "$17}'> temp/$2.soa.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/SoaLogAnalyser.jar  temp/$2.soa.$1.log $2
