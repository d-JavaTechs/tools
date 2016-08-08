 #!/bin/bash

   DIRECTORY=/home/weihu/production-logs/web-slave/tomcat-kuaisuadmin

   grep "毫秒" $DIRECTORY/kuaisuadmin."$1".log | awk '{print $10" "$15" "$16}'>  $DIRECTORY/url.log

   /home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar KuaisuAdmAnalyser-jar-with-dependencies.jar  $DIRECTORY/url.log