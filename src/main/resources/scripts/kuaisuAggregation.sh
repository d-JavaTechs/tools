#!/bin/bash
  DIRECTORYMASTER=/home/weihu/production-logs/web-master/tomcat-kuaisu
  DIRECTORYSLAVE=/home/weihu/production-logs/web-slave/tomcat-kuaisu


grep "response:" $DIRECTORYMASTER/detail-kuaisu."$1".log | grep -v "ksService-response" |awk -F '] "' '{print $2}' > temp/master.kuaisu.$1.log

grep "response:" $DIRECTORYSLAVE/detail-kuaisu."$1".log | grep -v "ksService-response" |awk -F '] "' '{print $2}' > temp/slave.kuaisu.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAggregation-jar-with-dependencies.jar temp/master.kuaisu.$1.log temp/slave.kuaisu.$1.log
