# pull base image.
FROM node:carbon

# update packages and install git
RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get install -y git
    
# cloning git repository
RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout master

# opening react app directory
WORKDIR /switch-2017-g003/group3_frontend

# preparing react app environment
RUN npm install && \
    npm install react-country-region-selector --save

# prepare image to start react application when running image
ENTRYPOINT npm start

