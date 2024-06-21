FROM openjdk:17-oracle
EXPOSE 8080
WORKDIR /app
ADD target/fitness-backend.jar fitness-backend.jar
ENTRYPOINT ["java","-jar","fitness-backend.jar"]