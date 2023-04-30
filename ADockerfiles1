FROM ubuntu:latest as build
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN ./gradlew bootJar -no-deamon

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /build/libs/ecommerce.jar app.jar

ENTRYPOINT ["java", ".jar", "app.jar"]