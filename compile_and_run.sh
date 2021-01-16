#!/bin/bash
cd ~8sudol/BD_PROJEKT/SRC/
rm *.class
javac Main.java
java -cp .:./postgresql-9.4.1212.jre7.jar Main