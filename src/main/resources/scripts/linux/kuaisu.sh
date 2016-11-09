#!/bin/bash
DIRECTORY=""
if [ "$2" = "master" ]
   then
       DIRECTORY=/home/weihu/production-logs/web-master/kuaisu
   elif [ "$2" = "slave" ]
   then 
      DIRECTORY=/home/weihu/production-logs/web-slave/kuaisu
   else
       DIRECTORY=/home/weihu/production-logs/gray-1/kuaisu
   fi
FILE=$DIRECTORY/detail-kuaisu."$1".log

grep "response:" $FILE|grep -v "ksService-response" |awk -F '] "' '{print $2}'>temp/$2.kuaisu.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAnalyser.jar  temp/$2.kuaisu.$1.log $2
