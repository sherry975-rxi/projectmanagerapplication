node {

    stage('Clone repository') {

        checkout scm
    }

    stage('unit tests'){

        docker.image('maven:3-jdk-8-slim').inside('mvn test')
    }
}