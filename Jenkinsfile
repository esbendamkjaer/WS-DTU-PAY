pipeline {
    agent any

    stages {
        stage('Build and Run') {
            steps {
                sh 'chmod +x ./build_and_run.sh'
                sh './build_and_run.sh'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }
}