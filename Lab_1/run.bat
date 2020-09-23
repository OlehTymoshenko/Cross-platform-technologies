echo off
:: Set pathes for JDK 
set path=F:\Programs\JDK\bin
set include=F:\Programs\JDK\include
set lib=F:\Programs\JDK\lib
set link=F:\Programs\JDK\bin
:: compile java code
javac -version Main.java
:: decompile java app, and saving byte-code in file
javap -c Main > decompailed_Main.txt
:: create docmentation for app
:: javadoc Main.java -d doc\
:: Run compiled app
java Main

pause