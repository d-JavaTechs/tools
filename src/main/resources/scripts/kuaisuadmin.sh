#!/bin/bash
DIRECTORY=""
   
      if [ "$2" = "master" ]
        then
             DIRECTORY=/home/weihu/production-logs/web-master/kuaisuadmin
      elif [ "$2" = "slave" ]
       then 
            DIRECTORY=/home/weihu/production-logs/web-slave/kuaisuadmin
      else
	     DIRECTORY=/home/weihu/production-logs/gray-1/kuaisuadmin
      fi


grep "毫秒" $DIRECTORY/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  temp/$2.kuaisuadmin.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAdmAnalyser.jar  temp/$2.kuaisuadmin.$1.log $2
