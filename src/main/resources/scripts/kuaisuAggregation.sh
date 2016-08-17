#!/bin/bash
  DIRECTORYMASTER=/home/weihu/production-logs/web-master/kuaisu
  DIRECTORYSLAVE=/home/weihu/production-logs/web-slave/kuaisu
  DIRECTORYSGRAY=/home/weihu/production-logs/gray-1/kuaisu
echo "aggregate $DIRECTORYMASTER/detail-kuaisu.$1.log  $DIRECTORYSLAVE/detail-kuaisu.$1.log $DIRECTORYSGRAY/detail-kuaisu.$1.log"

grep "response:" $DIRECTORYMASTER/detail-kuaisu."$1".log | grep -v "ksService-response" |awk -F '] "' '{print $2}' > temp/master.kuaisu.$1.log

grep "response:" $DIRECTORYSLAVE/detail-kuaisu."$1".log | grep -v "ksService-response" |awk -F '] "' '{print $2}' > temp/slave.kuaisu.$1.log

grep "response:" $DIRECTORYSGRAY/detail-kuaisu."$1".log | grep -v "ksService-response" |awk -F '] "' '{print $2}' > temp/gray.kuaisu.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAggregation.jar temp/master.kuaisu.$1.log temp/slave.kuaisu.$1.log temp/gray.kuaisu.$1.log
