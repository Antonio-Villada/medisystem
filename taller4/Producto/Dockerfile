# =========================
# Etapa de construcción
# =========================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src

# Compilar y empaquetar sin ejecutar tests
RUN mvn clean package -DskipTests

# =========================
# Etapa de ejecución
# =========================
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar el jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
