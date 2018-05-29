# pull base image.
FROM maven:3-jdk-8-slim

# update packages and install maven
RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y git && \
    apt-get install -y mysql-server

RUN mysql_install_db --user=root --datadir=/var/lib/mysql

RUN su mysql -c /usr/bin/mysqld_safe & /usr/bin/mysqladmin -u root password 'switchgroup3'

# cloning git repository and build
RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout master && \ 
    mvn install -DskipTests
