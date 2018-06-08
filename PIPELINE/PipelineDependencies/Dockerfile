# pull base image.
FROM openjdk:slim

# update packages, install git and maven
RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y git && \
    apt-get install -y maven

# cloning git repository and build
RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout master && \ 
    mvn install -DskipTests
