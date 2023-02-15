FROM eclipse-temurin:17-jdk-alpine as builder
ADD target/BudgetAppv2-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar BudgetAppv2-0.0.1-SNAPSHOT.jar