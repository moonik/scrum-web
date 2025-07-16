#!/bin/bash

read -p "Docker Hub Username: " DOCKER_USER

read -s -p "Docker Hub Password: " DOCKER_PASS
echo ""

echo "Logging into Docker Hub..."
echo "$DOCKER_PASS" | docker login --username "$DOCKER_USER" --password-stdin

echo "Building Docker images with Compose..."
docker compose build

echo "Pushing images to Docker Hub..."
docker compose push

echo "Done! Images pushed to Docker Hub."
