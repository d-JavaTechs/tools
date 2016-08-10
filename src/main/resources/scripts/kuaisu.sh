#!/bin/bash
DIRECTORY=""
if [ "$2" = "master" ]
   then
       DIRECTORY=/home/weihu/production-logs/web-master/tomcat-kuaisu
   else
       DIRECTORY=/home/weihu/production-logs/web-slave/tomcat-kuaisu
fi
FILE=$DIRECTORY/detail-kuaisu."$1".log

grep "response:" $FILE|grep -v "ksService-response" |awk -F '] "' '{print $2}'>temp/$2.kuaisu.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAnalyser-jar-with-dependencies.jar  temp/$2.kuaisu.$1.log
