#!/usr/bin/env bash
if [ -z "$2" ]; then
    echo 'Usage: HillKeys <fileName>'
else
    mkdir -p out
    javac -cp 'lib/jscience.jar' -d 'out/' src/HillKeys.java src/Util.java
    java -cp 'lib/jscience.jar:out/' HillKeys $1 $2
fi

