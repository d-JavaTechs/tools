#!/bin/bash
./ksAggregation.sh $1 > aggregation/ks.aggregation.$1.log 
./kuaisuAggregation.sh $1 > aggregation/kuaisu.aggregation.$1.log
./kuaisuadminAggregation.sh $1 > aggregation/kuaisuadmin.aggregation.$1.log
./soaAggregation.sh $1 > aggregation/soa.aggregation.$1.log
