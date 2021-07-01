pipeline {
    agent any
    tools {
        maven 'mavenGTC'
    }
    environment {
        dockerImage = ''
        registry = 'localhost:5000/user-service'
    }
    stages {
        stage('checkout') {
            steps {
                echo 'checking out...'
                git credentialsId: 'github', url: 'https://github.com/shadabbahadara/user-service.git'
            }
        }
        stage('build') {
            steps {
                echo 'building...'
                sh "mvn -Dmaven.test.skip clean package"
            }
        }
        stage('build docker image') {
            steps {
                echo 'building docker image...'
                script {
                    dockerImage = docker.build registry
                }
            }
        }
        stage('push docker image') {
            steps {
                echo 'pushing docker image to docker registry...'
                script {
                    dockerImage.push()
                }
            }
        }
    }
    post {
        success {
            echo 'success message...'
        }
        failure {
            echo 'failure message...'
        }
    }
}