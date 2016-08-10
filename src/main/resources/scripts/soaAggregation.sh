#!/bin/bash
 DIRECTORYMASTER=/home/weihu/production-logs/service-master/soa
 DIRECTORYSLAVE=/home/weihu/production-logs/service-slave/soa
FILE=simple-dapeng-container.$1".log"

echo "analysis log file $DIRECTORY/$FILE"

less  $DIRECTORYMASTER/$FILE| awk '{print $11" "$17}'> temp/master.soa.$1.log
less  $DIRECTORYSLAVE/$FILE| awk '{print $11" "$17}'> temp/slave.soa.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/SoaLogAggregation-jar-with-dependencies.jar   temp/master.soa.$1.log  temp/slave.soa.$1.log
