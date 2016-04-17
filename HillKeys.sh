#!/usr/bin/env bash
mkdir -p out
javac -cp 'lib/jscience.jar' -d 'out/' src/HillKeys.java
java -cp 'lib/jscience.jar:out/' HillKeys
