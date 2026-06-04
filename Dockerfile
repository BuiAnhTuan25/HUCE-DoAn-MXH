FROM eclipse-temurin:21-jre-ubi9-minimal

# Set the current working directory inside the image
WORKDIR /app

COPY ./target/*.jar app.jar

ADD ./src/main/resources /app/config
COPY ./fonts /usr/share/fonts

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=${ENV:dev}","-Djasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:}"]

EXPOSE 80
