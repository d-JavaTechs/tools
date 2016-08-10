#!/bin/bash
DIRECTORY=""
   
      if [ "$2" = "master" ]
        then
             DIRECTORY=/home/weihu/production-logs/web-master/tomcat-kuaisuadmin
	    else
	     DIRECTORY=/home/weihu/production-logs/web-slave/tomcat-kuaisuadmin
       fi


grep "毫秒" $DIRECTORY/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  temp/$2.kuaisuadmin.$1.log

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAdmAnalyser-jar-with-dependencies.jar  temp/$2.kuaisuadmin.$1.log
