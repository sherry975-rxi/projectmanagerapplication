# pull base image.
FROM maven:3-jdk-8-slim

# update packages and install git
RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y git
    
# cloning git repository and build
RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout master && \
    mvn package

# prepare image to start application when running image
ENTRYPOINT cd switch-2017-g003 && \
    java -jar target/project-management-1.0-SNAPSHOT.jar --spring.profiles.active=dbh2

