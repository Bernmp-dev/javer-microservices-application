FROM openjdk:22-jdk-slim
WORKDIR /javer-proxy-service

RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

RUN ls -l target/

RUN cp target/*.jar javer-proxy-service.jar

ENTRYPOINT ["java", "-jar", "javer-proxy-service.jar"]
