FROM openjdk:17-oracle
WORKDIR /opt
COPY target/*.jar /opt/app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]