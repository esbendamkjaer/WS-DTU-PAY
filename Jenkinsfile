pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                saveArtifacts '**/target/*.jar' 
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