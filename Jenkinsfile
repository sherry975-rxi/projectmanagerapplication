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

        stage('Package') {
           // package and copy resulting jar to the root of the workspace
           sh '''
                mvn package
                cp target/jenkins-assignment-jar-with-dependencies.jar ./release.jar
            '''
        }   
    }

}