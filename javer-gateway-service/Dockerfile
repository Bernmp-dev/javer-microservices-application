FROM openjdk:22-jdk-slim
WORKDIR /javer-gateway-service

RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

RUN ls -l target/

RUN cp target/*.jar javer-gateway-service.jar

ENTRYPOINT ["java", "-jar", "javer-gateway-service.jar"]
