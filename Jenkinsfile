pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build') {
            steps {

                sh 'mvn -B -DskipTests clean package'
                sh 'docker build -f DTU-PAY-API/src/main/docker/Dockerfile.jvm -t dtu-pay-api ./DTU-PAY-API'
            }
        }
    }
}