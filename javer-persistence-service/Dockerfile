FROM openjdk:22-jdk-slim
WORKDIR /javer-persistence-service

RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

RUN ls -l target/

RUN cp target/*.jar javer-persistence-service.jar

ENTRYPOINT ["java", "-jar", "javer-persistence-service.jar"]
