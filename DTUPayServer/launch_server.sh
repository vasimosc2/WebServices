#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status

# Navigate to the script's directory to ensure correct context
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

mvn package

# Step 1: Build the Docker image using docker-compose
echo "Building Docker image..."
docker compose build

# Step 2: Run the Docker containers
echo "Starting Docker containers..."
docker compose up -d

# Step 3: Clean up unused Docker resources
echo "Cleaning up unused Docker resources..."
docker system prune -f