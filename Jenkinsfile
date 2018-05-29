node {

    // define a name for the release image
    def RELEASE_IMAGE_NAME = '1171476/project-management-g3' 
    def releaseImage

    stage('Clone repository') {

        checkout scm
    }

    docker.image("1171476/project-dependencies:latest").inside(){

        stage('Unit Tests') {
            sh 'mvn test'
        } 
        
        stage('Integration Tests') {
            sh 'mvn failsafe:integration-test -Dspring.profiles.active=dbmysql'
        }  

        stage('Package') {
           
           sh '''
                mvn clean package -DskipTests
                cp target/project-management-1.0-SNAPSHOT.jar ./release.jar
            '''
        }
    }

    stage('Create project image') {
            
        releaseImage = docker.build("$RELEASE_IMAGE_NAME", "-f Dockerfile.release .")

        }

    stage('Push image to dockerHub') {
        withCredentials([usernamePassword(credentialsId: 'inesDockerHub', usernameVariable: 'DOCKERHUBUSERNAME', passwordVariable: 'DOCKERHUBPASS')]) {
            sh """
                echo $DOCKERHUBPASS | docker login -u $DOCKERHUBUSERNAME --password-stdin
                docker tag ${releaseImage.id} 1171476/project-management-g3:build-${env.BUILD_NUMBER}
                docker push $RELEASE_IMAGE_NAME
            """                 
            } 
    } 
}