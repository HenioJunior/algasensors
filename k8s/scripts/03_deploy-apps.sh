#!/bin/bash

set -e

BASE_DIR="$(cd "$(dirname "$0")/.." && pwd)"

echo "Deploying temperature-processing..."

kubectl apply -f $BASE_DIR/apps/temperature-processing/

echo "Deploying sensor-simulator..."

kubectl apply -f $BASE_DIR/apps/sensor-simulator/

echo "Applications deployed!"