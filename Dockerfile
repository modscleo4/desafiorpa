FROM maven:3.9.8-amazoncorretto-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build the JAR file
RUN mvn clean package spring-boot:repackage -DskipTests

# ======================

FROM openjdk:17-bullseye

# Install Chrome
run apt update && \
    apt install -y curl apt-transport-https && \
    curl -O https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt install -y ./google-chrome-stable_current_amd64.deb && \
    rm google-chrome-stable_current_amd64.deb && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/desafiorpa-1.0.jar .

CMD ["java", "-jar", "desafiorpa-1.0.jar"]
