#!/bin/bash

 DIRECTORYSLAVE=/home/weihu/production-logs/service-slave/services/

 FILE=detail-ksservices.$1".log"

 echo "analysis log file $FILE"
 grep response $DIRECTORYSLAVE/$FILE |grep SESSIONID | sort -k15nr > $DIRECTORYSLAVE/response1.log

 less $DIRECTORYSLAVE/response1.log| awk '{print $2" "$9" "$10" "$17}'> $DIRECTORYSLAVE/service1.log

 grep response $DIRECTORYSLAVE/$FILE |grep -v SESSIONID | sort -k15nr > $DIRECTORYSLAVE/response2.log

 less $DIRECTORYSLAVE/response2.log| awk '{print $2" "$8" "$9" "$16}'> $DIRECTORYSLAVE/service2.log

 cat $DIRECTORYSLAVE/service2.log>>$DIRECTORYSLAVE/service1.log

 grep request $DIRECTORYSLAVE/$FILE | awk '{print $8" "$9}' > $DIRECTORYSLAVE/request.log

 grep -A 1 ERROR $DIRECTORYSLAVE/$FILE > $DIRECTORYSLAVE/errors.log

 /home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar log-analysiser.jar  $DIRECTORYSLAVE/service1.log $DIRECTORYSLAVE/request.log $DIRECTORYSLAVE/errors.log