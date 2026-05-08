#!/bin/bash

echo "Starting port-forwards..."

kubectl port-forward svc/postgres 5432:5432 -n algasensors > /dev/null 2>&1 &
echo "Postgres -> localhost:5432"

kubectl port-forward svc/broker 9092:29092 -n algasensors > /dev/null 2>&1 &
echo "Kafka -> localhost:9092"

kubectl port-forward svc/pgadmin 8090:80 -n algasensors > /dev/null 2>&1 &
echo "PgAdmin -> localhost:8090"

kubectl port-forward svc/kafka-ui 8080:8080 -n algasensors > /dev/null 2>&1 &
echo "Kafka UI -> localhost:8080"

echo ""
echo "Port-forwards started."