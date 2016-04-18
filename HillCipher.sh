#!/usr/bin/env bash
if [ -z "$3" ]; then
    echo 'Usage: HillCipher <KFileName> <messageFileName> <saveFileName>'
else
    mkdir -p out
    javac -cp 'lib/jscience.jar' -d 'out/' src/HillCipher.java src/Util.java
    java -cp 'lib/jscience.jar:out/' HillCipher $1 $2 $3
fi

