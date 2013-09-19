#!/bin/sh 

pushd src/main/java/
cd cops
make
cd ..
make -f Makefile.jcops
make -f Makefile.protocol-labs
popd

make

CLASSPATH=".:src/main/java/protocol-labs.jar:src/main/java/jcops.jar:src/main/java/ara.jar"

java -classpath .:src/main/java/protocol-labs.jar:src/main/java/jcops.jar:src/main/java/ara.jar  Main &

java -classpath .:src/main/java/protocol-labs.jar:src/main/java/jcops.jar:src/main/java/ara.jar Test


