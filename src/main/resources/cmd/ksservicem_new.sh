 #!/bin/bash

 DIRECTORYMASTER=/home/weihu/production-logs/service-master/services
 FILE=detail-ksservices.$1".log"

 echo "analysis log file $FILE"
 grep response $DIRECTORYMASTER/$FILE |grep SESSIONID | sort -k15nr > $DIRECTORYMASTER/response1.log

 less $DIRECTORYMASTER/response1.log| awk '{print $2" "$9" "$10" "$17}'> $DIRECTORYMASTER/service1.log

 grep response $DIRECTORYMASTER/$FILE |grep -v SESSIONID | sort -k15nr > $DIRECTORYMASTER/response2.log

 less $DIRECTORYMASTER/response2.log| awk '{print $2" "$8" "$9" "$16}'> $DIRECTORYMASTER/service2.log

 cat $DIRECTORYMASTER/service2.log>>$DIRECTORYMASTER/service1.log

 grep request $DIRECTORYMASTER/$FILE | awk '{print $8" "$9}' > $DIRECTORYMASTER/request.log

 grep -A 1 ERROR $DIRECTORYMASTER/$FILE > $DIRECTORYMASTER/errors.log

 /home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar log-analysiser.jar  $DIRECTORYMASTER/service1.log $DIRECTORYMASTER/request.log $DIRECTORYMASTER/errors.log