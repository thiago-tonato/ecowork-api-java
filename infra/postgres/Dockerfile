ARG POSTGRES_BASE_IMAGE=postgres:15
FROM ${POSTGRES_BASE_IMAGE}

LABEL maintainer="EcoWork Team" \
      org.opencontainers.image.source="https://github.com/<org>/ecowork" \
      org.opencontainers.image.description="Imagem customizada do PostgreSQL utilizada pela EcoWork"

# Nada adicional é necessário aqui; mantemos a imagem oficial
# Apenas garantimos que futuras customizações (scripts, extensões etc.)
# possam ser versionadas no repositório.

EXPOSE 5432
