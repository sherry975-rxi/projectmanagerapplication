# pull base image.
FROM maven:3-jdk-8-slim

# update packages and install maven
RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y git

RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout master && \ 
    mvn install