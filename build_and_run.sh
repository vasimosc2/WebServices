#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status

echo "Current directory: $(pwd)"

./SimpleDtuPay/launch_service.sh
./customer-service/launch_service.sh
./merchant-service/launch_service.sh
./payment-service/launch_service.sh
./token-service/launch_service.sh
./reporting-service/launch_service.sh


# Step 1: Build the Docker image using docker-compose
echo "Building Docker image..."
docker compose build

# Step 2: Run the Docker containers
echo "Starting Docker containers..."
docker compose up -d

# Step 3: Clean up unused Docker resources
echo "Cleaning up unused Docker resources..."
docker system prune -f

# Set environment variable for testing
./CucumberExample/launch_client.sh