#!/bin/bash

 rm -rf temp/*
 rm -rf standlone/*
 rm -rf aggregation

/home/weihu/production-logs/jdk1.8.0_73/bin/java  -jar  libs/LoggerTimer.jar $1  -Djavax.net.debug=ssl,handshake
