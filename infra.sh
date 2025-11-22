#!/bin/bash

# ---------------------------------------------------------
# INFRASTRUCTURE CREATION SCRIPT (SAFE VERSION)
# PARAMETERS RECEIVED FROM AZURE DEVOPS LIBRARY
# ---------------------------------------------------------

# Required environment variables:
# RG, LOCATION, ACR_NAME, DB_IMAGE_NAME, DB_IMAGE_TAG,
# DB_CONTAINER_NAME, DB_ADMIN_PASSWORD, DB_NAME, DNS_LABEL

# ---------------------------------------------------------
echo "📦 Creating Resource Group..."
az group create \
  --name "$RG" \
  --location "$LOCATION" \
  --output none

# ---------------------------------------------------------
echo "📦 Creating ACR..."
az acr create \
  --resource-group "$RG" \
  --name "$ACR_NAME" \
  --sku Basic \
  --admin-enabled true \
  --output none

echo "🔐 Logging into ACR..."
az acr login --name "$ACR_NAME" --output none

# ---------------------------------------------------------
echo "📝 Creating Postgres Dockerfile..."
cat <<EOF > Dockerfile.db
FROM postgres:15
ENV POSTGRES_PASSWORD=${DB_ADMIN_PASSWORD}
ENV POSTGRES_DB=${DB_NAME}
EXPOSE 5432
EOF

# ---------------------------------------------------------
echo "🔧 Building DB image..."
docker build -t ${ACR_NAME}.azurecr.io/${DB_IMAGE_NAME}:${DB_IMAGE_TAG} -f Dockerfile.db .

echo "⬆️ Pushing DB image to ACR..."
docker push ${ACR_NAME}.azurecr.io/${DB_IMAGE_NAME}:${DB_IMAGE_TAG}

# ---------------------------------------------------------
echo "🔍 Retrieving ACR credentials..."
ACR_USERNAME=$(az acr credential show --name "$ACR_NAME" --query "username" -o tsv)
ACR_PASSWORD=$(az acr credential show --name "$ACR_NAME" --query "passwords[0].value" -o tsv)

# ---------------------------------------------------------
echo "🐳 Deploying PostgreSQL Container Instance..."
az container create \
  --resource-group "$RG" \
  --name "$DB_CONTAINER_NAME" \
  --image ${ACR_NAME}.azurecr.io/${DB_IMAGE_NAME}:${DB_IMAGE_TAG} \
  --dns-name-label "$DNS_LABEL" \
  --ports 5432 \
  --ip-address Public \
  --registry-login-server ${ACR_NAME}.azurecr.io \
  --registry-username "$ACR_USERNAME" \
  --registry-password "$ACR_PASSWORD" \
  --environment-variables \
      POSTGRES_PASSWORD="$DB_ADMIN_PASSWORD" \
      POSTGRES_DB="$DB_NAME" \
  --output none

# ---------------------------------------------------------
echo "📡 Getting DB IP Address..."
DB_IP=$(az container show \
  --resource-group "$RG" \
  --name "$DB_CONTAINER_NAME" \
  --query "ipAddress.ip" -o tsv)

# ---------------------------------------------------------
echo ""
echo "🎉 Infrastructure deployed!"
echo "PostgreSQL Connection Info:"
echo "Host: $DB_IP"
echo "Port: 5432"
echo "Database: $DB_NAME"
echo "User: postgres"
echo ""
echo "Connection String:"
echo "postgresql://postgres:${DB_ADMIN_PASSWORD}@${DNS_LABEL}.${LOCATION}.azurecontainer.io:5432/${DB_NAME}"
echo ""
echo "⚠️ Store these values as SECRET VARIABLES in Azure DevOps Library."
