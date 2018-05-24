node {

    // define a name for the release image
    def RELEASE_IMAGE_NAME = '1171476/sprint-review'

    stage('Clone repository') {

        checkout scm
    }

    stage('Create image with project dependencies') {
        def releaseImage = docker.build("$RELEASE_IMAGE_NAME", "-f Dockerfile .")

        withCredentials([usernamePassword(credentialsId: 'inesDockerHub', usernameVariable: 'DOCKERHUBUSERNAME', passwordVariable: 'DOCKERHUBPASS')]) {
            sh """
                echo $DOCKERHUBPASS | docker login -u $DOCKERHUBUSERNAME --password-stdin
                docker push $RELEASE_IMAGE_NAME
            """               
        }

    }

    docker.image('maven:3-jdk-8-slim').inside(){

        stage('Unit Tests') {
            sh 'mvn test'
        } 
        
        stage('Integration Tests') {
            sh 'mvn verify -P integration-test'
        }  

        stage('Package') {
           
           sh '''
                mvn package
                cp target/project-management-1.0-SNAPSHOT.jar ./release.jar
            '''
        }   
    }

}