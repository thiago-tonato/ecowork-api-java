#!/bin/bash
set -e

#####################################
# Infraestrutura fixa do projeto
#####################################

# 🔧 Valores fixos – altere aqui se precisar
RG="ecowork-rg"
LOCATION="eastus"
ACR_NAME="ecoworkacr"
DB_IMAGE_NAME="ecowork-postgres"
DB_IMAGE_TAG="v1"
POSTGRES_BASE_IMAGE="postgres:15"
DB_CONTAINER_NAME="ecoworkdb"
DB_ADMIN_USER="postgres"
DB_ADMIN_PASSWORD="ecoworkFIAP!"
DB_NAME="ecoworkdb"
DNS_LABEL="ecowork-api"

#####################################
# Criação de recursos no Azure
#####################################

echo "🚀 Criando Resource Group..."
az group create --name $RG --location $LOCATION

echo "📦 Criando Azure Container Registry..."
az acr create \
    --resource-group $RG \
    --name $ACR_NAME \
    --sku Basic \
    --admin-enabled true

echo "📄 Construindo imagem do PostgreSQL e enviando para o ACR..."
az acr build \
    --registry $ACR_NAME \
    --image "$DB_IMAGE_NAME:$DB_IMAGE_TAG" \
    --file infra/postgres/Dockerfile \
    --build-arg POSTGRES_BASE_IMAGE=$POSTGRES_BASE_IMAGE \
    infra/postgres

echo "🐳 Criando Container Instance com PostgreSQL..."
az container create \
    --resource-group $RG \
    --name $DB_CONTAINER_NAME \
    --image "$ACR_NAME.azurecr.io/$DB_IMAGE_NAME:$DB_IMAGE_TAG" \
    --registry-login-server "$ACR_NAME.azurecr.io" \
    --registry-username $(az acr credential show --name $ACR_NAME --query username -o tsv) \
    --registry-password $(az acr credential show --name $ACR_NAME --query passwords[0].value -o tsv) \
    --ip-address Public \
    --environment-variables POSTGRES_PASSWORD=$DB_ADMIN_PASSWORD POSTGRES_DB=$DB_NAME POSTGRES_USER=$DB_ADMIN_USER \
    --dns-name-label $DNS_LABEL \
    --ports 5432 \
    --os-type Linux \
    --cpu 1 \
    --memory 1.5

echo "🎉 Infraestrutura criada com sucesso!"
