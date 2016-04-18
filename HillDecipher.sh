#!/usr/bin/env bash
if [ -z "$3" ]; then
    echo 'Usage: HillCipher <DFileName> <messageFileName> <saveFileName>'
else
    mkdir -p out
    javac -cp 'lib/jscience.jar' -d 'out/' src/HillDecipher.java src/Util.java
    java -cp 'lib/jscience.jar:out/' HillDecipher $1 $2 $3
fi

