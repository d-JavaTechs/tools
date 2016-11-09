#!/bin/bash
 DIRECTORYMASTER=/home/weihu/production-logs/service-master/dapeng
 DIRECTORYSLAVE=/home/weihu/production-logs/service-slave/dapeng
 DIRECTORYGRAY=/home/weihu/production-logs/gray-1/dapeng

 FILE=simple-dapeng-container.$1".log"

echo "aggregate $DIRECTORYMASTER/$FILE  $DIRECTORYSLAVE/$FILE $DIRECTORYGRAY/$FILE"

less  $DIRECTORYMASTER/$FILE| awk '{print $11" "$17}'> temp/master.soa.$1.log
less  $DIRECTORYSLAVE/$FILE| awk '{print $11" "$17}'> temp/slave.soa.$1.log
less  $DIRECTORYGRAY/$FILE| awk '{print $11" "$17}'> temp/gray.soa.$1.log
/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/SoaLogAggregation.jar   temp/master.soa.$1.log  temp/slave.soa.$1.log  temp/gray.soa.$1.log
