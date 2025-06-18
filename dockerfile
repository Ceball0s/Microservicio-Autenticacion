# Etapa 1: Build de Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos pom.xml y descargamos dependencias (caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera de Java
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/ofertaya-login-app.jar app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
