#!/bin/bash
set -e

#####################################
# Infraestrutura fixa do projeto
#####################################

# 🔧 Valores fixos – altere aqui se precisar
RG="ecowork-rg"
LOCATION="eastus"
ACR_NAME="ecoworkacr"
POSTGRES_IMAGE="postgres:15"
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

echo "🐳 Criando Container Instance com PostgreSQL..."
az container create \
    --resource-group $RG \
    --name $DB_CONTAINER_NAME \
    --image $POSTGRES_IMAGE \
    --ip-address Public \
    --environment-variables POSTGRES_PASSWORD=$DB_ADMIN_PASSWORD POSTGRES_DB=$DB_NAME POSTGRES_USER=$DB_ADMIN_USER \
    --dns-name-label $DNS_LABEL \
    --ports 5432 \
    --os-type Linux \
    --cpu 1 \
    --memory 1.5

echo "🎉 Infraestrutura criada com sucesso!"
