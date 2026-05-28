#!/bin/bash

set -e

BASE_DIR="$(cd "$(dirname "$0")/.." && pwd)"

echo "Deleting cluster..."

kind delete cluster --name algasensors

echo "Cluster removed!"