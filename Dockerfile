# Étape 1 : Build de l'application
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copier le wrapper Maven et les fichiers de config
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Télécharger les dépendances avant de copier le code (cache plus efficace)
RUN ./mvnw dependency:go-offline

# Copier le code source et builder
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Étape 2 : Image finale plus légère
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier uniquement le JAR généré
COPY --from=build /app/target/*.jar app.jar

# Exposer le port dynamique (Render fournira PORT)
EXPOSE 8080

# Lancer Spring Boot
ENTRYPOINT ["java","-jar","app.jar"]
