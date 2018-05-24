node {

    // define a name for the release image
    def DEPENDENCIES_IMAGE_NAME = '1171476/project-dependencies'
    def dependenciesImage
    def RELEASE_IMAGE_NAME = '1171476/project-management-g3' 
    def releaseImage

    stage('Clone repository') {

        checkout scm
    }

    stage('Create image with project dependencies') {
        dependenciesImage = docker.build("$DEPENDENCIES_IMAGE_NAME", "-f Dockerfile .")
    
    }

    stage('Push image to dockerHub') {

        withCredentials([usernamePassword(credentialsId: 'inesDockerHub', usernameVariable: 'DOCKERHUBUSERNAME', passwordVariable: 'DOCKERHUBPASS')]) {
            sh """
                echo $DOCKERHUBPASS | docker login -u $DOCKERHUBUSERNAME --password-stdin
                docker push $DEPENDENCIES_IMAGE_NAME
                docker tag ${dependenciesImage.id} 1171476/project-dependencies:${env.BUILD_NUMBER}
            """               
        }
    }
    

    docker.image("$DEPENDENCIES_IMAGE_NAME:latest").inside(){

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

        stage('Create project image') {
            
            releaseImage = docker.build("$RELEASE_IMAGE_NAME", "-f Dockerfile.release .")
    
        }

        stage('Push image to dockerHub') {
            withCredentials([usernamePassword(credentialsId: 'inesDockerHub', usernameVariable: 'DOCKERHUBUSERNAME', passwordVariable: 'DOCKERHUBPASS')]) {
                sh """
                    echo $DOCKERHUBPASS | docker login -u $DOCKERHUBUSERNAME --password-stdin
                    docker push $RELEASE_IMAGE_NAME
                    docker tag ${releaseImage.id} 1171476/project-management-g3:${env.BUILD_NUMBER}
                """               
            }
        }
    }
}