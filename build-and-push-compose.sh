#!/bin/bash

read -p "Docker Hub Username: " DOCKER_USER

read -s -p "Docker Hub Password: " DOCKER_PASS
echo ""

echo "Logging into Docker Hub..."
echo "$DOCKER_PASS" | docker login --username "$DOCKER_USER" --password-stdin

FILE_TO_BUILD="docker-compose.yml"

echo "Building Docker images with Compose..."
docker compose -f "$FILE_TO_BUILD" build

echo "Pushing images to Docker Hub..."
docker compose -f $FILE_TO_BUILD" push

echo "Done! Images pushed to Docker Hub."
