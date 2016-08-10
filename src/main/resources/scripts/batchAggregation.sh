#!/bin/bash
./ksAggregation.sh $1 > temp/ks.aggregation.$1.log 
./kuaisuAggregation.sh $1 > temp/kuaisu.aggregation.$1.log
./kuaisuadminAggregation.sh $1 > temp/kuaisuadmin.aggregation.$1.log
./soaAggregation.sh $1 > temp/soa.aggregation.$1.log
