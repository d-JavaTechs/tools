#!/bin/bash
  DIRECTORYMASTER=/home/weihu/production-logs/web-master/kuaisuadmin
  DIRECTORYSLAVE=/home/weihu/production-logs/web-slave/kuaisuadmin
  DIRECTORYGRAY=/home/weihu/production-logs/gray-1/kuaisuadmin
 echo "aggregate $DIRECTORYMASTER/kuaisuadmin.$1.log  $DIRECTORYSLAVE/kuaisuadmin.$1.log $DIRECTORYGRAY/kuaisuadmin.$1.log"

grep "毫秒" $DIRECTORYMASTER/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  temp/master.kuaisuadmin.$1.log
grep "毫秒" $DIRECTORYSLAVE/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  temp/slave.kuaisuadmin.$1.log
 grep "毫秒" $DIRECTORYGRAY/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  temp/gray.kuaisuadmin.$1.log
/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar libs/KuaisuAdmAggregation.jar  temp/master.kuaisuadmin.$1.log temp/slave.kuaisuadmin.$1.log temp/gray.kuaisuadmin.$1.log
