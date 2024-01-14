#!/bin/bash
set -e # Stops the execution if an error occurs

# Build and install Maven projects
mvn clean install -DskipTests
