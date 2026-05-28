#!/bin/bash

set -e

BASE_DIR="$(cd "$(dirname "$0")/.." && pwd)"

echo "Deploying Postgres..."

kubectl apply -f "$BASE_DIR/infra/postgres/postgres.yml"

echo "Deploying PgAdmin..."

kubectl apply -f "$BASE_DIR/infra/postgres/pgadmin.yml"

echo "Deploying Kafka..."

kubectl apply -f "$BASE_DIR/infra/kafka/"

echo "Infrastructure deployed!"