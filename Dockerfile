# ---------------------------------------------------------
# Stage 1: Build (Maven + JDK)
# ---------------------------------------------------------
FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

# Copia somente o pom.xml primeiro (melhora cache)
COPY pom.xml .

# Baixa dependências sem copiar código ainda
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src ./src

# Compila e cria o JAR
RUN mvn clean package -DskipTests


# ---------------------------------------------------------
# Stage 2: Runtime (Leve - JRE)
# ---------------------------------------------------------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia o JAR gerado no Stage 1
# Vai pegar automaticamente o único JAR gerado pelo build
COPY --from=build /app/target/*.jar app.jar

# Expõe porta da API
EXPOSE 8080

# Permite passar parâmetros de execução no Azure (ex: JAVA_OPTS="-Xms512m -Xmx1g")
ENV JAVA_OPTS=""

# Executa a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
