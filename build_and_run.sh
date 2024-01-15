#!/bin/bash

set -e # Stops the execution if an error occurs

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

# Build and start Docker containers
# -d is detached mode, allows the service to continue even after the shell process is complete
# --build is to build images before starting containers
# --wait is to wait for the startup of the containers, used instead of a sleep with a defined timer
docker-compose up -d --build --wait

#run test for all microservices
#mvn test

# Clean up unused Docker images and resources
docker system prune -a -f
