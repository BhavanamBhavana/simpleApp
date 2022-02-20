FROM openjdk:8
WORKDIR /apps
COPY  /target/simpleApp.jar ./
ENTRYPOINT ["java", "-jar", "simpleApp.jar"]