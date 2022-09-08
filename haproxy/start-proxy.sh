#!/bin/bash

#sudo docker run --network analyzer-network --privileged --restart on-failure -d --name ha-proxy-container \
# -p 9000:9000 -p 10000:10000 ha-proxy

sudo docker run --privileged --restart on-failure -d --name ha-proxy-container \
 -p 9000:9000 -p 10000:10000 ha-proxy
