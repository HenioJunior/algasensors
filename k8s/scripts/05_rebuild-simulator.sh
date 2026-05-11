#!/bin/bash

set -e

K8S_DIR="$(cd "$(dirname "$0")/.." && pwd)"
PROJECT_DIR="$(cd "$K8S_DIR/.." && pwd)"

echo "Building sensor-simulator..."

"$PROJECT_DIR/gradlew" \
  -p "$PROJECT_DIR" \
  :microservices:sensor-simulator:clean \
  :microservices:sensor-simulator:build

echo "Building Docker image..."

docker build \
  -t algasensors/sensor-simulator:dev \
  "$PROJECT_DIR/microservices/sensor-simulator"

echo "Loading image into kind..."

kind load docker-image \
  algasensors/sensor-simulator:dev \
  --name algasensors

echo "Restarting deployment..."

kubectl rollout restart deployment/sensor-simulator \
  -n algasensors

echo "Applying Kubernetes manifests..."

kubectl apply -f "$K8S_DIR/apps/sensor-simulator/"

echo "Restarting deployment..."

kubectl rollout restart deployment/sensor-simulator \
  -n algasensors

echo "Waiting rollout..."

echo "Done!"