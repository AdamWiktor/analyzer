#!/bin/bash

./mvnw clean package
sudo docker build . -t analyzer-web
