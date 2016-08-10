#!/bin/bash
 
   DIRECTORYMASTER=/home/weihu/production-logs/service-master/services 
   DIRECTORYSLAVE=/home/weihu/production-logs/service-slave/services 

echo "aggregate $DIRECTORYMASTER/detail-ksservices.$1.log  $DIRECTORYSLAVE/detail-ksservices.$1.log"

grep response $DIRECTORYMASTER/detail-ksservices.$1.log  |grep '[0-9]ms' > temp/master.service.$1.log

grep response $DIRECTORYSLAVE/detail-ksservices.$1.log  |grep '[0-9]ms' > temp/slave.service.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KsLogAggregation-jar-with-dependencies.jar  temp/master.service.$1.log temp/slave.service.$1.log 
