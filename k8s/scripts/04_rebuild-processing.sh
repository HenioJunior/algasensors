#!/bin/bash

set -e

K8S_DIR="$(cd "$(dirname "$0")/.." && pwd)"
PROJECT_DIR="$(cd "$K8S_DIR/.." && pwd)"

echo "K8S_DIR=$K8S_DIR"
echo "PROJECT_DIR=$PROJECT_DIR"

echo "Building temperature-processing..."

"$PROJECT_DIR/gradlew" \
  -p "$PROJECT_DIR" \
  :microservices:temperature-processing:clean \
  :microservices:temperature-processing:build

echo "Building Docker image..."

docker build \
  -t algasensors/temperature-processing:dev \
  "$PROJECT_DIR/microservices/temperature-processing"

echo "Loading image into kind..."

kind load docker-image \
  algasensors/temperature-processing:dev \
  --name algasensors

echo "Restarting deployment..."

kubectl rollout restart deployment/temperature-processing \
  -n algasensors

echo "Applying Kubernetes manifests..."

kubectl apply -f "$K8S_DIR/apps/temperature-processing/"

echo "Restarting deployment..."

kubectl rollout restart deployment/temperature-processing \
  -n algasensors

echo "Waiting rollout..."

echo "Done!"