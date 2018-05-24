node {

    // define a name for the release image
    def RELEASE_IMAGE_NAME = '1171476/project-dependencies'
    def releaseImage 

    stage('Clone repository') {

        checkout scm
    }

    stage('Create image with project dependencies') {
        releaseImage = docker.build("$RELEASE_IMAGE_NAME", "-f Dockerfile .")
    
    }

    stage('Push image to dockerHub') {

        withCredentials([usernamePassword(credentialsId: 'inesDockerHub', usernameVariable: 'DOCKERHUBUSERNAME', passwordVariable: 'DOCKERHUBPASS')]) {
                    sh """
                        echo $DOCKERHUBPASS | docker login -u $DOCKERHUBUSERNAME --password-stdin
                        docker push $RELEASE_IMAGE_NAME
                        docker tag ${releaseImage.id} $RELEASE_IMAGE_NAME:${env.BUILD_ID}
                    """               
                }
        }

    docker.image("$RELEASE_IMAGE_NAME:latest").inside(){

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