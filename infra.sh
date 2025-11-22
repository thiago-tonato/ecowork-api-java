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
DB_CONTAINER_NAME="ecowork_user"
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

echo "🔐 Obtendo token para o ACR..."
TOKEN=$(az acr login -n $ACR_NAME --expose-token --query accessToken -o tsv)

echo "🔐 Fazendo login no ACR com token..."
docker login $ACR_NAME.azurecr.io -u 00000000-0000-0000-0000-000000000000 -p $TOKEN

echo "📄 Construindo imagem do banco de dados..."
az acr build \
    --registry $ACR_NAME \
    --image "$DB_IMAGE_NAME:$DB_IMAGE_TAG" \
    .

echo "🐳 Criando Container Instance com PostgreSQL..."
az container create \
    --resource-group $RG \
    --name $DB_CONTAINER_NAME \
    --image "$ACR_NAME.azurecr.io/$DB_IMAGE_NAME:$DB_IMAGE_TAG" \
    --registry-login-server "$ACR_NAME.azurecr.io" \
    --registry-username $(az acr credential show --name $ACR_NAME --query username -o tsv) \
    --registry-password $(az acr credential show --name $ACR_NAME --query passwords[0].value -o tsv) \
    --environment-variables POSTGRES_PASSWORD=$DB_ADMIN_PASSWORD POSTGRES_DB=$DB_NAME \
    --dns-name-label $DNS_LABEL \
    --ports 5432

echo "🎉 Infraestrutura criada com sucesso!"
