#!/bin/bash

 DIRECTORYSLAVE=/home/weihu/production-logs/service-slave/soa/

 FILE=detail-dapeng-container.$1".log"

 echo "analysis log file $FILE"

 grep '耗时:'  $DIRECTORYSLAVE/$FILE | grep -v qbScheduler>soa-response.log

 less $DIRECTORYSLAVE/soa-response.log|awk '{print $2" "$7" "$9" "$11}'> service1.log

 /home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar tools-1.0-SNAPSHOT-jar-with-dependencies.jar  $DIRECTORYSLAVE/service1.log