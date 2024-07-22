FROM openjdk:22-jdk-slim
WORKDIR /javer-eureka-server

RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

RUN ls -l target/

RUN cp target/*.jar javer-eureka-server.jar

ENTRYPOINT ["java", "-jar", "javer-eureka-server.jar"]
