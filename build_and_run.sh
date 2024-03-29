#!/bin/bash

set -e # Stops the execution if an error occurs

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


pushd reporting
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
# --remove-orphans removes containers for services not defined in the Compose file
# --wait makes the command return/exit only after all containers have been started and are in healthy state
#
docker-compose up -d --build --remove-orphans --wait

#run test for all microservices
mvn test
