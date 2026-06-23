FROM eclipse-temurin:21-jre-ubi9-minimal

# Set the current working directory inside the image
WORKDIR /app

COPY ./target/*.jar /app/app.jar
ADD ./src/main/resources /app/config

EXPOSE 80

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=${ENV:dev}","-Djasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:}"]