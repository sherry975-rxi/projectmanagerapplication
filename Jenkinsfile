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
    }
    
    docker.image("mysql").withRun('-e "MYSQL_ROOT_PASSWORD=switchgroup3" -e "MYSQL_DATABASE=project_management" -p 3306:3306'){
        
        stage('Integration Tests') {
            sh 'mvn failsafe:integration-test --spring.profiles.active=dbmysql'
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