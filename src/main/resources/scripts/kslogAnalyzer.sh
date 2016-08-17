#!/bin/bash

 DIRECTORY=""

 rm -rf service-$2.log
 
 if [ "$2" = "master" ]
 then

   DIRECTORY=/home/weihu/production-logs/service-master/ksservices
 
 elif [ "$2" = "slave" ]
 then  
   DIRECTORY=/home/weihu/production-logs/service-slave/ksservices
 
 else
   DIRECTORY=/home/weihu/production-logs/gray-1/ksservices
 fi

echo "$DIRECTORY.$1.log"

grep response $DIRECTORY/detail-ksservices.$1.log  | grep '[0-9]ms' >temp/service-$1.$2.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KsLogAnalyser.jar  temp/service-$1.$2.log $2
