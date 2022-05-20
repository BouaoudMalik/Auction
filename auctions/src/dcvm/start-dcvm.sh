#!/bin/bash
if [ -z "$1" ]; then
  echo usage: $0 jvmName
  exit
 fi
java -ea -Xms2m -cp 'jars/*' -Djava.security.manager -Djava.security.policy=dcvm.policy $1 $2 config.xml
