# pull base image.
FROM maven:3-jdk-8-slim

# update packages and install maven
RUN  \
    export DEBIAN_FRONTEND=noninteractive && \
    sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
    apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y vim wget curl git

RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout master && \
    mvn install

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

ENTRYPOINT cd switch-2017-g003 && \
    mvn install && \
    cp target/project-management-1.0-SNAPSHOT.jar / && \
    cd / && \
    rm -rf switch-2017-g003 && \
    java -jar project-management-1.0-SNAPSHOT.jar