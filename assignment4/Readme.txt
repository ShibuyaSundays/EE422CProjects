EE 422C Assignment 4
Isaac Lee (itl96)
31 October 2019

Components in this zip file "Project4_itl96":
-bin
-src
-lib
readme.txt


To compile from root (containing bin, src, lib directories):
javac -cp
lib/commons-codec-1.11.jar:lib/commons-logging-1.2.jar:lib/httpclient-4.5.10.jar:lib/httpcore-4.4.12.jar:lib/jackson-annotations-2.10.0.0.jar:lib/jackson-core-2.10.0.jar:lib/jackson-databind-2.9.0.jar
src/assignment4/*.java -d bin

To run from root:
java -cp
bin:lib/commons-codec-1.11.jar:lib/commons-logging-1.2.jar:lib/httpclient-4.5.10.jar:lib/httpcore-4.4.12.jar:lib/jackson-annotations-2.10.0.0.jar:lib/jackson-core-2.10.0.jar:lib/jackson-databind-2.9.0.jar
assignment4.Main

