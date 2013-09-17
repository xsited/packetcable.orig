#!/bin/sh 

pushd src/main/java/
make
popd

make



java -classpath .:src/main/java/pcmm.jar Main &

java -classpath .:src/main/java/pcmm.jar Test


