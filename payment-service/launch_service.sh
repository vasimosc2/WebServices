#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status

# Navigate to the script's directory to ensure correct context
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

mvn package

