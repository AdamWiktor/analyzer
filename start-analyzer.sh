#!/bin/bash

#sudo docker run --network analyzer-network --restart on-failure -d --name analyzer-web-container analyzer-web
sudo docker run --restart on-failure -d --name analyzer-web-container \
 -p 8080:8080 analyzer-web
