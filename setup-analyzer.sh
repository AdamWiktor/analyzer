#!/bin/bash

./mvnw clean package
sudo docker build -f analyzer.dockerfile . -t analyzer-web
