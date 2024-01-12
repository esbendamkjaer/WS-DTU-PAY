#!/bin/bash

set -e # Stops the execution if an error occurs

# Build and run each microservice
pushd account
chmod +x build.sh
./build.sh
popd

pushd client
./build.sh
popd

pushd messaging-utilities-3.4
./build.sh
popd

pushd payment
./build.sh
popd

pushd token
./build.sh
popd

# Build and start Docker containers
docker-compose up -d --build

# Wait for a few seconds to ensure services are up
sleep 3

#run test for all microservices
mvn test

# Clean up unused Docker images and resources
docker system prune -a -f
