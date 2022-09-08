#!/bin/bash

cd kafka
./bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
sleep 10
./bin/kafka-server-start.sh -daemon config/server.properties
