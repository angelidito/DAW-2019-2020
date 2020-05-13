#!/bin/bash
nombre=Apuesta
nombrepaquete=angelidito.laruleta
rutapaquete=$(echo $nombrepaquete | sed 's/\./\//g')

CLASSPATH=bin
JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

echo $nombre
echo ---
echo

doxygen

tree -f  dox
echo