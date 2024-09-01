FROM mirror.gcr.io/maven:3.8.1-openjdk-17-slim AS build

WORKDIR /arm/src/creditservice

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM mirror.gcr.io/openjdk:17-alpine

WORKDIR /app

COPY --from=build /arm/src/creditservice/target/ArmCreditService*.jar /app/ArmCreditservice.jar

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "/app/ArmCreditservice.jar"]