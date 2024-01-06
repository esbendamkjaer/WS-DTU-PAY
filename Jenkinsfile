pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true

            }
        }

        stage ('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage ('Deploy') {
            steps {
                sh 'docker compose up --build'
                // hvorfor up i CI? den skal vel bygges, tagges også køres udenfor CI
            }
        }
    }
}