#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status

echo "Current directory: $(pwd)"

./DTUPayServer/launch_server.sh
./DTUPayClient/launch_client.sh


# echo "running tests..."

# ./DTUPayServer/launch_client.sh

# mvn clean test
