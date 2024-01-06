pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true

            }
        }

        stage ('Deploy') {
            steps {
                sh 'docker-compose up -d --build'
                sleep 2s
            }
        }

        stage ('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}