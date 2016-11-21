@echo off

SET CLASSPATH=.;.\lib\antlr-4.5.3-complete.jar;%CLASSPATH%
java org.antlr.v4.Tool %*