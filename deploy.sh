#!/bin/bash

# BEFORE DEPLOYING MAKE SURE ALL YOUR DOCKER SWARM NODES HAVE ACCESS TO DOCKER HUB PRIVATE REPOSITORY

# Prompt for inputs
read -p "Enter Docker Context name: " CONTEXT_NAME
read -p "Enter SSH username: " SSH_USER
read -p "Enter Remote Host/IP: " REMOTE_HOST
read -p "Enter SSH Private Key Path (default: ~/.ssh/id_rsa): " SSH_KEY_PATH

# Use default key if none provided
SSH_KEY_PATH=${SSH_KEY_PATH:-$HOME/.ssh/id_rsa}

read -p "Enter Docker Stack name: " STACK_NAME
read -p "Enter Compose file name (default: docker-stack.yml): " COMPOSE_FILE

# Use default compose file if none provided
COMPOSE_FILE=${COMPOSE_FILE:-docker-stack.yml}

# Prompt for SSH passphrase (hidden input)
read -s -p "Enter SSH Key Passphrase: " PASSPHRASE
echo ""

# Load SSH key into ssh-agent using expect
expect << EOF
spawn ssh-add $SSH_KEY_PATH
expect "Enter passphrase"
send "$PASSPHRASE\r"
expect eof
EOF

# Create Docker Context (if it doesn't exist)
docker context inspect $CONTEXT_NAME >/dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "Creating Docker Context: $CONTEXT_NAME"
    docker context create $CONTEXT_NAME --docker "host=ssh://$SSH_USER@$REMOTE_HOST"
else
    echo "Using existing Docker Context: $CONTEXT_NAME"
fi

# Use the Docker Context
docker context use $CONTEXT_NAME

read -p "Docker Hub Username: " DOCKER_USER
read -s -p "Docker Hub Password: " DOCKER_PASS
echo ""

echo "Logging into Docker Hub..."
echo "$DOCKER_PASS" | docker --context $CONTEXT_NAME login --username "$DOCKER_USER" --password-stdin
if [ $? -ne 0 ]; then
    echo "Docker login failed. Exiting."
    exit 1
fi

# Deploy the Docker Stack
docker stack deploy --with-registry-auth -c $COMPOSE_FILE $STACK_NAME

if [ $? -ne 0 ]; then
    echo "Deploy failed. Exiting."
    exit 1
fi

echo "✅ Stack '$STACK_NAME' deployed to remote context '$CONTEXT_NAME' at $REMOTE_HOST"
