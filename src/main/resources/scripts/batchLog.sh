#!/bin/bash
./kuaisuadmin.sh $1  master > standlone/kuaisuadmin.master.$1.log
./kuaisuadmin.sh $1 slave > standlone/kuaisuadmin.slave.$1.log
./kuaisu.sh $1 slave > standlone/kuaisu.slave.$1.log
./kuaisu.sh $1 master > standlone/kuaisu.master.$1.log
./soa.sh $1 master > standlone/soa.master.$1.log
./soa.sh $1 slave > standlone/soa.slave.$1.log
./kslogAnalyzer.sh  $1  slave > standlone/ksservice.slave.$1.log
./kslogAnalyzer.sh  $1 master > standlone/ksservice.master.$1.log

