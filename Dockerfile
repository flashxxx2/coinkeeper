FROM maven:3-openjdk-15-slim AS build
WORKDIR /app/build
COPY . .
RUN mvn package -B
RUN mv backend/target/backend-1.0-jar-with-dependencies.jar target/back.jar
RUN mv frontend/target/frontend-1.0-jar-with-dependencies.jar target/front.jar

FROM openjdk:17-slim
WORKDIR /app/bin
COPY --from=build /app/build/target/back.jar .
COPY --from=build /app/build/target/front.jar .
CMD ["java", "-jar", "back.jar"]
CMD ["java", "-jar", "front.jar"]
