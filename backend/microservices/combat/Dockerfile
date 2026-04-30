FROM maven:3.9.15-amazoncorretto-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2-native-headless

WORKDIR /app

COPY --from=build /app/target/combat.jar .

CMD ["java", "-jar", "combat.jar"]