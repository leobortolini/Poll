FROM openjdk:17-oracle
WORKDIR /opt
COPY target/*.jar /opt/app.jar
COPY root.crt /opt
ENTRYPOINT [ "java", "-jar", "app.jar" ]