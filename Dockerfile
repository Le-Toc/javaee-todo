FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -e -B -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/javaee-todo-microbundle.jar /app/app.jar
EXPOSE 8080
VOLUME ["/data"]
CMD ["java",
     "-DJDBC_DATABASE_URL=${JDBC_DATABASE_URL}",
     "-DJDBC_DATABASE_USER=${JDBC_DATABASE_USER}",
     "-DJDBC_DATABASE_PASSWORD=${JDBC_DATABASE_PASSWORD}",
     "-jar","/app/app.jar","--noCluster","--rootDir","/data"]