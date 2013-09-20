#!/bin/sh 

pushd src/main/java/
make
popd
make


java -classpath .:src/main/java/jcops.jar  Main &
java -classpath .:src/main/java/jcops.jar Test


