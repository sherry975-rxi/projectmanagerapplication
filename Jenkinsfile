node {

    stage('Clone repository') {

        checkout scm
    }

    docker.image('maven:3-jdk-8-slim').inside(){
        
        stage('Unit Tests') {
            sh 'mvn test'
        } 
        
        stage('Integration Tests') {
            sh 'mvn verify -P integration-test'
        }    
    }

}