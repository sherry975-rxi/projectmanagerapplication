# pull base image.
#FROM maven:3-jdk-8-slim
FROM node:carbon
#FROM openjdk:8
# update packages and install maven
RUN  \
    export DEBIAN_FRONTEND=noninteractive && \
    sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
    apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y vim wget curl git
# RUN apt-get install -y maven
# RUN curl -sL https://deb.nodesource.com/setup_8.x | bash -
# RUN apt-get update -qqy && apt-get -qqyy install \
#     nodejs \
#   && rm -rf /var/lib/apt/lists/*
RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout sprint5-s2-devops
# WORKDIR /switch-2017-g003 
# RUN mvn package
    
#COPY . /switch-2017-g003
#RUN mkdir -p /test/app
#COPY /group3_frontend/package.json /test/app/
#COPY /group3_frontend/package-lock.json /test/app/
# WORKDIR /switch-2017-g003
#RUN apt-get install npm
# RUN curl --silent --location https://rpm.nodesource.com/setup_10.x |  bash -
# RUN yum -y install nodejs
# RUN yum install gcc-c++ make
#RUN dnf install nodejs
# RUN apt-get install -g -y nodejs
# RUN apt-get install -y build-essential
# RUN nodejs -v
# RUN npm install npm@latest -g
# RUN apt-get install -y npm
WORKDIR /switch-2017-g003/group3_frontend
    #&& \
    #mvn package
#FROM node:6.0.1
#FROM node:10.1.0 
#COPY . .
#RUN cd switch-2017-g003/group3_frontend && \
RUN npm install && \
    npm install react-country-region-selector --save
#WORKDIR group3_frontend
#RUN apt-get update
#RUN npm run build
   
#Vai buscar a chave privada ssh para ter acesso ao repositorio
#RUN mkdir /root/.ssh/
#
## Copy over private key, and set permissions
#ADD ~/.ssh/id_rsa /root/.ssh/id_rsa
#
## Create known_hosts
#RUN touch /root/.ssh/known_hosts
## Add bitbuckets key
#RUN ssh-keyscan bitbucket.org >> /root/.ssh/known_hosts
#ENTRYPOINT cd switch-2017-g003/group3_frontend && \
 #   npm start
ENTRYPOINT npm start
    #mvn install && \
    #cp target/project-management-1.0-SNAPSHOT.jar / && \
    #cd / && \
    #rm -rf switch-2017-g003 && \
    #java -jar target/project-management-1.0-SNAPSHOT.jar 
#RUN npm start
#EXPOSE 5000