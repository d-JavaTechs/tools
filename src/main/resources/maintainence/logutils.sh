#!/bin/bash

record_file=~/logmaintenance.log
# zip Logs dir1 dir2 ...
zip() {
  echo zipLogs $@
  yestoday=`date -d "1 day ago" +%Y-%m-%d`
  while [ $# \> 0 ]
  do
    directory=$1
    for file in $(find "$directory" -name "*.$yestoday.log")
    do
      echo "gzip file $file">>${record_file}
      gzip -f $file
    done
    shift
  done
}

# clean logs before 30days
# clean dir1 dir2
clean(){
  echo "clean $@"
  while [ $# \> 0 ]
  do
    directory=$1
    for file in $(find "$directory" -mtime +30 -name "*.log*")
    do
      echo "rm -rf  $file">>${record_file}
      rm -rf $file
    done
    shift
  done
}

# sync dir1,dir1_to dir2,dir2_to
# only sync *.log(not *.gz, *.txt) etc.
sync(){
  echo "sync $@"
  while [ $# \> 0 ]
  do
    source=`echo $1|cut -d "," -f 1`
    destination=`echo $1|cut -d "," -f 2`
    echo "sync $source $destination start">>${record_file}
    rsync -zvar --chmod=ug=rwx --chmod=o=rx --progress --append --include="*.log" $source $destination
    echo "sync $source $destination end">>${record_file}
    shift
  done
}

case $1 in
  "zip" | "clean" | "sync") eval $@ ;;
   *) echo "invalid command $1" ;;
esac

