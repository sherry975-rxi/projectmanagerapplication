# pull backend image created in project-dependencies pipeline.
FROM 1171473/switch-2017-g003-projectmanagement-dependencies:latest

# defining current working directory
WORKDIR /switch-2017-g003

# building application...
RUN mvn package

# prepare image to start application when running image
ENTRYPOINT java -jar target/project-management-1.0-SNAPSHOT.jar --spring.profiles.active=dbh2

# exposing backend in port...
EXPOSE 8080