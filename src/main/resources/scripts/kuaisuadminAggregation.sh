#!/bin/bash
  DIRECTORYMASTER=/home/weihu/production-logs/web-master/tomcat-kuaisuadmin
  DIRECTORYSLAVE=/home/weihu/production-logs/web-slave/tomcat-kuaisuadmin
grep "毫秒" $DIRECTORYMASTER/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  temp/master.kuaisuadmin.$1.log
grep "毫秒" $DIRECTORYSLAVE/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  temp/slave.kuaisuadmin.$1.log
/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAdmAnalyser-jar-with-dependencies.jar  temp/master.kuaisuadmin.$1.log temp/slave.kuaisuadmin.$1.log 
