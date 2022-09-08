#!/bin/bash

scp -r src analyzer.dockerfile compose.yaml mvnw mvnw.cmd pom.xml .mvn ansible haproxy kafka setup-analyzer.sh start-analyzer.sh stop-analyzer.sh rozpr:~/a2/analyzer
