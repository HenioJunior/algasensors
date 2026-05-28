#!/bin/bash

set -e

K8S_DIR="$(cd "$(dirname "$0")/.." && pwd)"

echo "Creating kind cluster if not exists..."

if kind get clusters | grep -q "^algasensors$"; then
  echo "Cluster algasensors already exists."
else
  kind create cluster --name algasensors
fi

echo "Applying namespace..."

kubectl apply -f "$K8S_DIR/namespace.yml"

echo "Cluster ready!"