#!/bin/bash

set -e # Stops the execution if an error occurs

# Clean up all target folders
mvn clean -DskipTests

# Build MQ wrapper dependency
pushd messaging-utilities-3.4
chmod +x build.sh
./build.sh
popd

# Build and run each microservice
pushd account
chmod +x build.sh
./build.sh
popd

pushd client
chmod +x build.sh
./build.sh
popd

pushd messaging-utilities-3.4
chmod +x build.sh
./build.sh
popd

pushd payment
chmod +x build.sh
./build.sh
popd

pushd token
chmod +x build.sh
./build.sh
popd


pushd customer
chmod +x build.sh
./build.sh
popd

pushd manager
chmod +x build.sh
./build.sh
popd

pushd merchant
chmod +x build.sh
./build.sh
popd

# Build and start Docker containers
#
# -d is detached mode, allows the service to continue even after the shell process is complete
# --build is to build images before starting containers
#
docker-compose up -d --build
sleep 5

#run test for all microservices
mvn test
