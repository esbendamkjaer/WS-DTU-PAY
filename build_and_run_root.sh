#!/bin/bash

set -e # Stops the execution if an error occurs

# Build and install Maven projects
mvn clean install

# Build and start Docker containers
docker-compose up -d --build

# Wait for a few seconds to ensure services are up
sleep 3

# Run Maven tests
mvn test

# Clean up unused Docker images
docker image prune -f
