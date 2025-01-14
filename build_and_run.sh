#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status

cd DTUPayServer

# Step 1: Build the Docker image using docker-compose
echo "Building Docker image..."
docker compose build

# Step 2: Run the Docker containers
echo "Starting Docker containers..."
docker compose up -d

echo "Cleaning up unused Docker resources..."
docker system prune -f

echo "Build and deployment successful!"



echo "running tests..."
cd ..
cd DTUPayClient

mvn clean test
