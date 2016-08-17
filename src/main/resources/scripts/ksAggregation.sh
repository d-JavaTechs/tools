#!/bin/bash
 
   DIRECTORYMASTER=/home/weihu/production-logs/service-master/ksservices
   DIRECTORYSLAVE=/home/weihu/production-logs/service-slave/ksservices
   DIRECTORYGRAY=/home/weihu/production-logs/gray-1/ksservices
echo "aggregate $DIRECTORYMASTER/detail-ksservices.$1.log  $DIRECTORYSLAVE/detail-ksservices.$1.log  $DIRECTORYGRAY/detail-ksservices.$1.log"

grep response $DIRECTORYMASTER/detail-ksservices.$1.log  |grep '[0-9]ms' > temp/master.service.$1.log

grep response $DIRECTORYSLAVE/detail-ksservices.$1.log  |grep '[0-9]ms' > temp/slave.service.$1.log

grep response $DIRECTORYGRAY/detail-ksservices.$1.log  |grep '[0-9]ms' > temp/gray.service.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KsLogAggregation.jar  temp/master.service.$1.log temp/slave.service.$1.log  temp/gray.service.$1.log
