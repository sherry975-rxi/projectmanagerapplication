node {

    // define a name for the release image
    def RELEASE_IMAGE_NAME = '1171476/sprint-review'
    def releaseImage

    stage('Clone repository') {

        checkout scm
    }

    stage('Create image with project dependencies') {
        releaseImage = docker.build("$RELEASE_IMAGE_NAME", "-f Dockerfile .")
    }

    stage('Push image to dockerHub') {
        docker.withRegistry('https://registry.hub.docker.com', 'inesDockerHub') {
            
            releaseImage.push(${env.BUILD_NUMBER})
            
    }
    }

    docker.image('sprint-review:latest').inside(){

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