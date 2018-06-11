# pull base image.
FROM node:latest
    
# cloning git repository...
RUN git clone https://1171476:EMEaTGmvs8cAEQkaVTAN@bitbucket.org/lei-isep/switch-2017-g003.git && \
    cd switch-2017-g003 && \
    git checkout master

# opening react app directory...
WORKDIR /switch-2017-g003/group3_frontend

# preparing react app environment...
RUN npm install && \
    npm install react-country-region-selector --save && \
    npm install react-skillbars --save && \
    npm install react-redux --save && \
    npm install redux --save && \
    npm install redux-thunk --save && \
    npm install progressbar.js --save && \
    npm install dateformat --save && \
    npm install -g json

# setting proxy in package.json for backend service...
RUN cat package.json && \
    json -I -f package.json -e 'this.proxy="http://backend:8080/"' && \
    cat package.json


# prepare image to start react application when running image
ENTRYPOINT npm start

# exposing frontend in port...
EXPOSE 3000